package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceBankFeeReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemtStatusChangeReqBo;

import java.math.BigDecimal;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * 4、汇款订单自动创建
 * 5、调用清算系统，查询订单
 * 6、手动创建汇款订单
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayRemittanceBiz {

    /**
     * 创建汇款订单V2,根据文件批次创建汇款订单
     *
     * @param cbPayRemittanceReqBo 请求参数
     * @param sumAmt               总金额
     */
    Long createRemittanceOrderV2(CbPayRemittanceReqBo cbPayRemittanceReqBo, BigDecimal sumAmt);

    /**
     * 汇款订单审核
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     */
    void auditRemittanceOrder(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo);

    /**
     * 汇款订单状态更新
     *
     * @param cbPayRemtStatusChangeReqBo 请求参数
     */
    void changeRemittanceOrderStatus(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo);

    /**
     * 汇款订单线下，后台审核接口
     *
     * @param cbPayRemtStatusChangeReqBo 状态更新请求参数
     */
    void backgroundAudit(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo);

    /**
     * 银行返回成功，从用户在途账户扣除汇款金额到备付金
     *
     * @param cbPayRemtStatusChangeReqBo 审核参数对象
     */
    void deductionAmount(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo);

    /**
     * 银行返回失败，从用户在途账户扣除汇款金额到用户基本账户，并且返回汇款手续费
     *
     * @param cbPayRemtStatusChangeReqBo 审核参数对象
     */
    void returnAmount(CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo);

    /**
     * 汇款订单发往渠道
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     */
    void batchRemit(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo);

    /**
     * 批量购汇
     *
     * @param cbPayRemittanceAuditReqBo 请求参数
     * @param sumAmt                    总金额
     */
    void batchPurchase(CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo, BigDecimal sumAmt);

    /**
     * 付汇入账费用更新
     *
     * @param cbPayRemittanceBankFeeReqBo 请求参数
     */
    void updateBankFee(CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo);

    /**
     * 付汇入账费用审核
     *
     * @param cbPayRemittanceBankFeeReqBo 请求参数
     */
    void bankFeeAudit(CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo);

    /**
     * 汇款异常重发
     *
     * @param batchNo  批次号
     * @param memberId 商户号
     * @param updateBy 操作人
     */
    void remittanceResend(String batchNo, Long memberId, String updateBy);
}
