package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 风控订单信息
 */

@Setter
@Getter
@ToString
public class CbPayOrderRiskControlDo {

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 伪造订单标识：0-真实订单；1-伪造订单   2:初始
     */
    private Integer fakeFlag;

    /**
     * 运单订单标识：0-真实运单；1-伪造运单；2-无运单信息 3初始
     */
    private Integer wayBillFlag;

    /**
     * 人工处理状态：0-待处理；1-伪造订单；2-真实订单
     */
    private Integer artifiStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 更新人
     */
    private String updateBy;


}
