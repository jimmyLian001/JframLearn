package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: suqier Date:2016/10/26 ProjectName: asias-icpaygate Version: 1.0
 **/
@Setter
@Getter
@ToString
public class CbPayOrderRespDto implements Serializable {

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
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 交易金额
     */
    private BigDecimal transMoney;

    /**
     * 交易币种
     */
    private String transCcy;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 功能编号
     */
    private Integer functionId;

    /**
     * 支付编号
     */
    private Integer payId;

    /**
     * 渠道编号
     */
    private Integer channelId;

    /**
     * 支付状态：INIT:未支付，TRUE:支付成功，FALSE:支付失败
     */
    private String payStatus;

    /**
     * 订单完成时间
     */
    private Date orderCompleteTime;

    /**
     * 订单汇率
     */
    private BigDecimal transRate;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 交易手续费
     */
    private BigDecimal transFee;

    /**
     * 订单类型：B2C:b2c订单，WEIXI:微信订单，ALIPAY:支付宝订单
     */
    private String orderType;

    /**
     * 通知状态：INIT:未通知，TRUE:通知成功，FALSE:通知失败
     */
    private String notifyStatus;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 通知方式：SERVER-服务端通知/PAGE-页面通知/SERVER_PAGE-服务端和页面通知
     */
    private String notifyType;

    /**
     * 服务端通知地址
     */
    private String serverNotifyUrl;

    /**
     * 页面通知地址
     */
    private String pageNotifyUrl;

    /**
     * 附加信息
     */
    private String additionInfo;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 持卡人姓名
     */
    private String idHolder;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 预留手机号
     */
    private String mobile;
}

