package com.baofu.cbpayservice.biz.dispatch;

import com.baofu.cbpayservice.biz.convert.CbPayCancelOrderBizConvert;
import com.baofu.cbpayservice.biz.models.CbPayCancelOrderBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <p>
 * 执行取消订单线程
 * </p>
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service   Version: 1.0
 */
@Slf4j
public class CancelOrderRunnable implements Runnable {

    /**
     * 缓存服务
     */
    private RedisManager redisManager;

    /**
     * 文件订单上传状态
     */
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 插入参数对象
     */
    private CbPayCancelOrderBo cbPayCancelOrderBo;

    /**
     * 上传文件相关操作
     */
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 事务模板
     */
    private TransactionTemplate transactionTemplate;

    /**
     * API订单取消实例化
     *
     * @param cbPayCancelOrderBo  取消订单业务逻辑处理model对象
     * @param proxyCustomsManager 代理报关数据层交互manager服务
     * @param cbPayOrderManager   跨境支付订单数据层交互Manager服务
     * @param redisManager        redis缓存统一交互服务
     */
    public CancelOrderRunnable(CbPayCancelOrderBo cbPayCancelOrderBo, ProxyCustomsManager proxyCustomsManager,
                               CbPayOrderManager cbPayOrderManager, RedisManager redisManager, TransactionTemplate transactionTemplate) {
        this.redisManager = redisManager;
        this.cbPayOrderManager = cbPayOrderManager;
        this.cbPayCancelOrderBo = cbPayCancelOrderBo;
        this.proxyCustomsManager = proxyCustomsManager;
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * 线程方法
     */
    @Override
    public void run() {

        Boolean redisLock = Boolean.FALSE;
        //1、判断是否锁住
        String key = Constants.CBPAY_ORDER_BATCH_NO + cbPayCancelOrderBo.getFileBatchNo();
        try {
            //1、redis锁住批次号
            redisLock = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
            if (!redisLock) {
                log.error("该文件:{}正在取消中请勿重复操作", cbPayCancelOrderBo.getFileBatchNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00147);
            }
            //2、查询文件批次状态
            log.info("文件批次号：{}", cbPayCancelOrderBo.getFileBatchNo());
            FiCbPayFileUploadDo fileUploadDo = proxyCustomsManager.queryByBatchId(cbPayCancelOrderBo.getFileBatchNo());
            log.info("文件上传状态：{}", fileUploadDo.getStatus());
            if (!FlagEnum.TRUE.getCode().equals(fileUploadDo.getStatus())) {
                log.error("文件批次:{}状态非待审核状态", cbPayCancelOrderBo.getFileBatchNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00146, "文件批次状态非待审核状态");
            }
            //3、更新状态
            updateStatus();
        } catch (Exception e) {
            log.error("线程更新状态异常:{}", e);
        } finally {
            //4、释放redis锁,只能是本次锁住的情况才会去释放
            if (redisLock) {
                redisManager.deleteObject(key);
            }
        }
    }


    /**
     * 取消上传文件更新表
     * 1、更新文件批次表状态
     * 2、更新跨境支付订单表中确认状态，以便商户能再次发起申请，更新时使用商户编号 + 文件批次号作为条件更新
     * 3、必须保证两个更新在同一个事物中完成
     */
    private void updateStatus() {

        //3、更新状态
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                long startTime = System.currentTimeMillis();
                //2、更新批次状态为已经取消
                FiCbPayFileUploadDo cancelFileUploadDo = CbPayCancelOrderBizConvert.paramConvert(cbPayCancelOrderBo);
                log.info("更新文件批次状态参数为：批次号{}", cancelFileUploadDo.getFileBatchNo());
                proxyCustomsManager.updateFilestatus(cancelFileUploadDo);

                //3、更新订单表中的确认状态
                FiCbPayOrderDo fiCbPayOrderDo = CbPayCancelOrderBizConvert.orderParamConvert(cbPayCancelOrderBo);
                log.info("更新订单状态参数,批次号{}", fiCbPayOrderDo.getBatchFileId());
                cbPayOrderManager.modifyACKStatus(fiCbPayOrderDo);

                log.info("取消订单执行时间：{}s", System.currentTimeMillis() - startTime);
            }
        });
    }
}
