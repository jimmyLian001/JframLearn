package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayCreateRemittanceOrderBiz;
import com.baofu.cbpayservice.biz.CbPayRemittanceBiz;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.biz.convert.ApiCbPayRemitNotifyConvert;
import com.baofu.cbpayservice.biz.models.ApiRemitCbPayNotfiyBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.FiCbPayBatchFileUploadDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 人民币创建汇款订单处理
 * <p>
 * 1、创建汇款订单
 * </p>
 * User: wanght Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayCreateRemittanceOrderBizImpl implements CbPayCreateRemittanceOrderBiz {

    /**
     * 支付服务
     */
    @Autowired
    private CbPayRemittanceBiz cbPayRemittanceBiz;

    /**
     * 汇款明细路径
     */
    @Value("${remitOrder_download_path}")
    private String remitOrderDownloadPath;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 订单信息rManage
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 通知商户服务类
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 创建汇款订单
     *
     * @param cbPayRemittanceReqBo 请求参数
     */
    @Override
    public Long createRemittanceOrderV2(CbPayRemittanceReqBo cbPayRemittanceReqBo) {

        Long orderId;
        Boolean lockFlag = Boolean.FALSE;
        FiCbPayBatchFileUploadDo fiCbPayBatchFileUploadDo = new FiCbPayBatchFileUploadDo();
        fiCbPayBatchFileUploadDo.setStatus(UploadFileStatus.COMPLETE.getCode());
        fiCbPayBatchFileUploadDo.setUpdateBy(cbPayRemittanceReqBo.getCreateBy());
        fiCbPayBatchFileUploadDo.setMemberId(cbPayRemittanceReqBo.getMemberId());
        fiCbPayBatchFileUploadDo.setAmlCcy(cbPayRemittanceReqBo.getTargetCcy());
        fiCbPayBatchFileUploadDo.setBatchFileIdList(cbPayRemittanceReqBo.getBatchFileIdList());

        try {
            // 判定商户是否锁住
            lockFlag = isLock(cbPayRemittanceReqBo);
            log.info("call 商户号：{}，是否锁住：{}", cbPayRemittanceReqBo.getMemberId(), lockFlag);
            if (lockFlag) {
                log.info("call 商户{}已锁住，暂不支持创建汇款订单！", cbPayRemittanceReqBo.getMemberId());
                return null;
            }
            lockFlag = lock(cbPayRemittanceReqBo);
            if (!lockFlag) {
                log.info("call 商户{}锁住失败，暂不支持创建汇款订单！", cbPayRemittanceReqBo.getMemberId());
                return null;
            }

            //统计跨境汇款订单总金额
            List<Long> orderList = new ArrayList<>();
            BigDecimal sumAmt = getSumAmt(cbPayRemittanceReqBo, orderList);
            //创建汇款批次
            orderId = cbPayRemittanceBiz.createRemittanceOrderV2(cbPayRemittanceReqBo, sumAmt);

            //汇款批次号
            fiCbPayBatchFileUploadDo.setBatchNo(String.valueOf(orderId));
            proxyCustomsManager.batchUpdateFileStatus(fiCbPayBatchFileUploadDo);
            log.info("call 更新文件批次完成：文件批次:{},文件批次状态:{}", cbPayRemittanceReqBo.getBatchFileIdList(),
                    UploadFileStatus.COMPLETE.getCode());
            return orderId;
        } catch (Exception e) {
            fiCbPayBatchFileUploadDo.setStatus(UploadFileStatus.PASS.getCode());
            proxyCustomsManager.batchUpdateFileStatus(fiCbPayBatchFileUploadDo);
            log.error("call 异步创建汇款订单异常", e);
            //判断是否需要通知商户：API发起的需要通知商户
            if (!StringUtil.isBlank(cbPayRemittanceReqBo.getRemitApplyNo())) {
                log.error("call API异步创建汇款订单异常,通知商户开始···");
                ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                        cbPayRemittanceReqBo, 3, "创建汇款订单失败");
                notifyBiz.notifyMember(String.valueOf(cbPayRemittanceReqBo.getMemberId()), String.valueOf(cbPayRemittanceReqBo.getTerminalId()),
                        String.valueOf(cbPayRemittanceReqBo.getNotifyUrl()), apiRemitCbPayNotfiyBo,
                        cbPayRemittanceReqBo.getRemitApplyNo(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());
                log.error("call API异步创建汇款订单异常,通知商户结束···");
            }
            return null;
        } finally {
            if (lockFlag) {
                unLock(cbPayRemittanceReqBo);
                log.info("call 商户{}创建汇款订单结束，释放锁完成", cbPayRemittanceReqBo.getMemberId());
            }
        }
    }

    private boolean isLock(CbPayRemittanceReqBo cbPayRemittanceReqBo) {
        List<Long> fileIdList = cbPayRemittanceReqBo.getBatchFileIdList();
        for (Long fileId : fileIdList) {
            String value = redisManager.queryObjectByKey(Constants.CBPAY_CREATE_REMITTANCE_KEY +
                    cbPayRemittanceReqBo.getMemberId() + ":" + fileId);
            if (FlagEnum.TRUE.getCode().equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private boolean lock(CbPayRemittanceReqBo cbPayRemittanceReqBo) {
        List<Long> fileIdList = cbPayRemittanceReqBo.getBatchFileIdList();
        for (Long fileId : fileIdList) {
            Boolean lockFlag = redisManager.lockRedis(Constants.CBPAY_CREATE_REMITTANCE_KEY + cbPayRemittanceReqBo.getMemberId()
                    + ":" + fileId, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
            if (!lockFlag) {
                log.info("商户：{},文件批次：{}锁住失败，无法创建汇款订单，请稍后重试", cbPayRemittanceReqBo.getMemberId(), fileId);
                return Boolean.FALSE;
            }
        }
        log.info("商户：{}锁定成功", cbPayRemittanceReqBo.getMemberId());
        return Boolean.TRUE;
    }

    private void unLock(CbPayRemittanceReqBo cbPayRemittanceReqBo) {
        List<Long> fileIdList = cbPayRemittanceReqBo.getBatchFileIdList();
        for (Long fileId : fileIdList) {
            redisManager.deleteObject(Constants.CBPAY_CREATE_REMITTANCE_KEY + cbPayRemittanceReqBo.getMemberId() + ":" + fileId);
        }
    }

    /**
     * 统计跨境汇款订单总金额
     *
     * @param cbPayRemittanceReqBo 跨境人民币汇款信息请求参数
     * @param orderList            跨境订单集合
     * @return 统计跨境汇款订单总金额
     */
    private BigDecimal getSumAmt(CbPayRemittanceReqBo cbPayRemittanceReqBo, List<Long> orderList) {

        BigDecimal sumAmt = BigDecimal.ZERO;
        for (Long fileId : cbPayRemittanceReqBo.getBatchFileIdList()) {
            List<Long> queryList = cbPayOrderManager.batchQueryOrder(cbPayRemittanceReqBo.getMemberId(), fileId);
            if (CollectionUtils.isEmpty(queryList)) {
                log.info("未查询到该文件批次数据：{}", fileId);
                continue;
            }
            log.info("文件批次号：{}，订单数量：{}", fileId, queryList.size());
            orderList.addAll(queryList);
            BigDecimal amount = cbPayOrderManager.sumSuccessAmt(fileId, cbPayRemittanceReqBo.getMemberId());
            sumAmt = sumAmt.add(amount);
            log.info("批次：{}总金额：{}", fileId, amount);
        }
        log.info("跨境订单大小：{}，总金额：{}", orderList.size(), sumAmt);
        cbPayRemittanceReqBo.setOrderIdList(orderList);
        return sumAmt;
    }
}
