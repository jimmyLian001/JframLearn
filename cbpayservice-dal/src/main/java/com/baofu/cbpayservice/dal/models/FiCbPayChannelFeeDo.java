package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 跨境人民币渠道成本配置信息
 * User: wanght Date:2016/11/30 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayChannelFeeDo extends BaseDo {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 汇款渠道
     */
    private Long channelId;

    /**
     * 国外固定金额手续费
     */
    private BigDecimal abroadFixedMoney;

    /**
     * 港澳台固定金额手续费
     */
    private BigDecimal fixedMoney;

    /**
     * 计费类型,RATIO：比率
     */
    private String chargeType;

    /**
     * 计费值
     */
    private BigDecimal chargeValue;

    /**
     * 最低手续费
     */
    private BigDecimal minMoney;

    /**
     * 最高手续费
     */
    private BigDecimal maxMoney;

    /**
     * 状态：INIT:初始，TRUE:启用，FALSE:失效
     */
    private String status;
}