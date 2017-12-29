package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商户汇入申请运营确认
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class OperateConfirmBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 申请单号
     */
    private Long applyNo;

    /**
     * 汇入汇款编号（银行到账通知填写）
     */
    private String incomeNo;

    /**
     * 审核状态
     */
    private int matchingStatus;

    /**
     * 审核之前的状态
     */
    private int beforeMatchingStatus;
    /**
     * 备注
     */
    private String remarks;
}
