package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * User: yangjian  Date: 2017-05-24 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class MemberFeeReqBo {

    /**
     * 交易日志
     */
    private String transLogId;
    /**
     * 交易码
     */
    private String transSysCode;
    /**
     * id
     */
    private String id;
    /**
     * 日志ID
     */
    private String localLogId = UUID.randomUUID().toString();
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 功能ID
     */
    private String functionId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 会员号
     */
    private String memberId;
    /**
     * 订单号
     */
    private String orderId;

}
