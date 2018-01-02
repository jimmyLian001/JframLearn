package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 身份认证响应信息
 *
 * @author : hetao Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class IdCardAuthResBo {

    /**
     * 认证成功标识
     */
    private Boolean authFlag;

    /**
     * 响应码 0：认证成功 1：认证不一致 -1：其他异常
     */
    private int code;

    /**
     * 响应信息
     */
    private String desc;

    /**
     * 商户订单号
     */
    private String transId;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 响应流水号
     */
    private String transNo;

    /**
     * 收费标志 Y:收费 N:不收费
     */
    private String feeFlag;
}
