package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * excel和api内容 对象
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsV2Bo {

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 持卡人姓名
     */
    private String idHolder;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 预留手机号
     */
    private String mobile;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Long terminalId;

    /**
     * 请求Ip
     */
    private String clientIp;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 额外信息
     */
    private String goodsInfo;

    /**
     * 付款人证件类型
     */
    private String idType;

    /**
     * 行业类型
     */
    private String industryType;
}
