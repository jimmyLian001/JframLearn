package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台审核参数
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ReceiveAuditBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 收汇到账通知编号
     */
    private Long orderId;

    /**
     * 审核状态
     */
    private int cmAuditStatus;
}
