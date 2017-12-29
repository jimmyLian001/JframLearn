package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPaySettlePrepaymentBo;

/**
 * 结汇垫资接口
 * <p>
 * 1,结汇垫资申请
 * 2，结汇垫资审核
 * 3，是否已垫资
 * 4，结汇垫资返还状态修改
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
public interface CbPaySettlePrepaymentBiz {

    /**
     * 结汇垫资申请
     *
     * @param incomeNo 汇入汇款编号
     */
    Long prepaymentApply(String incomeNo, Integer autoFlag);

    /**
     * 结汇垫资审核
     *
     * @param applyId 垫资申请编号
     * @param status  垫资状态
     * @param remarks 备注
     * @return
     */
    void prepaymentVerify(Long applyId, Integer status, String remarks);

    /**
     * 自动结汇垫资
     *
     * @param memberId 商户号
     * @param incomeNo 汇入汇款编号
     */
    void autoSettlePrepay(Long memberId, String incomeNo);

    /**
     * @param incomeNo 银行汇入汇款编号
     * @return boolean 是否已垫资
     */
    Boolean isPrepayment(String incomeNo);

    /**
     * 计算垫资结汇金额
     *
     * @param incomeNo 汇入汇款编号
     * @return CbPaySettlePrepaymentBo 垫资信息
     */
    CbPaySettlePrepaymentBo calculateSettleAmt(String incomeNo);

}
