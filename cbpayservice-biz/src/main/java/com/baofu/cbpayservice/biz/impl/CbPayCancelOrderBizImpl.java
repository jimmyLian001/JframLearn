package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayCancelOrderBiz;
import com.baofu.cbpayservice.biz.dispatch.CancelOrderRunnable;
import com.baofu.cbpayservice.biz.models.CbPayCancelOrderBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 订单取消操作
 * </p>
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service  Version: 1.0
 */
@Slf4j
@Component
public class CbPayCancelOrderBizImpl implements CbPayCancelOrderBiz {

    /**
     * 线程服务
     */
    private static final ExecutorService EXECUTORSERVICE = Executors.newFixedThreadPool(3);
    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;
    /**
     * 订单管理
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;
    /**
     * 上传文件相关操作
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;
    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 取消订单
     *
     * @param cbPayCancelOrderBo 文件批次号
     */
    @Override
    public void cancelOrder(CbPayCancelOrderBo cbPayCancelOrderBo) {

        //1、判断文件批次上传的状态
        FiCbPayFileUploadDo fileUploadDo = proxyCustomsManager.queryByBatchId(cbPayCancelOrderBo.getFileBatchNo());
        cbPayCancelOrderBo.setMemberId(fileUploadDo.getMemberId());
        if (!Constants.BATCH_UPLOAD_TRUE_STATUS.equals(fileUploadDo.getStatus())) {
            log.error("文件批次状态非待审核状态");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00146, "文件批次状态非待审核状态");
        }
        //redis key
        String redisKey = Constants.CBPAY_ORDER_BATCH_NO + cbPayCancelOrderBo.getFileBatchNo();
        //2、判断是否锁住
        if (FlagEnum.TRUE.getCode().equals(redisManager.queryObjectByKey(redisKey))) {
            log.error("该文件正在取消中请勿重复操作");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00147, "该文件正在取消中请勿重复操作");
        }
        //3、启动线程
        CancelOrderRunnable cancelOrderRunnable = new CancelOrderRunnable(cbPayCancelOrderBo, proxyCustomsManager,
                cbPayOrderManager, redisManager, transactionTemplate);
        EXECUTORSERVICE.execute(cancelOrderRunnable);
    }
}
