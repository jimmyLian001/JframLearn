package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 跨境人民币渠道成本信息
 * User: wanght Date:2016/11/30 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayChannelCostDo extends BaseDo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商户编号
     */
    private Long memberNo;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 汇款渠道
     */
    private Long channelId;

    /**
     * 渠道成本费用
     */
    private BigDecimal channelFee;

    /**
     * 币种
     */
    private String currency;

    /**
     * 备注
     */
    private String remarks;
}