package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 跨境人民币订单信息
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayVerifyDo extends BaseDo {

    /**
     * 商户订单号
     */
    private String transId;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 交易金额
     */
    private String transDate;

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String idName;

    /**
     * 响应流水号
     */
    private String transNo;

    /**
     * 响应流水号
     */
    private Integer code;

    /**
     * 响应描述
     */
    private String desc;

    /**
     * 是否收费
     */
    private String feeFlag;

    /**
     * 备注
     */
    private String remarks;


    /**
     * 订单来源
     */
    private int source;

}