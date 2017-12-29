package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 渠道成本计算业务逻辑处理请求参数
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class ChannelCostCalBo {

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 请求批次号
     */
    private String batchNo;
}
