package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 结汇结果查询
 * <p>
 * User: 康志光 Date:2017/07/19 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleQueryReqParamBo {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 汇款流水号
     */
    private String incomeNo;

    /**
     * 汇入申请编号
     */
    private Long applyNo;

    /**
     * 结汇宝付订单号（settle的orderId）
     */
    private Long settleOrderId;

    /**
     * 查询类型
     */
    private String searchType;
}
