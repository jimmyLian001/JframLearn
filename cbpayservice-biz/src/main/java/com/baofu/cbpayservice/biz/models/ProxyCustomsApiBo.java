package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代理跨境结算mq消息内容对象
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsApiBo {

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * orderId 宝付订单号
     */
    private Long orderId;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 结算币种
     */
    private String paymentCcy;

    /**
     * 结算金额
     */
    private BigDecimal paymentMoney;

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
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品单价
     */
    private String goodsPrice;

    /**
     * 商品数量
     */
    private String goodsNum;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 产品ID
     */
    private int productId;

    /**
     * 业务流水号
     */
    private String businessNo;

    /**
     * 请求Ip
     */
    private String clientIp;

    /**
     * 商品结果集
     */
    private List<CbPayOrderItemBo> cbPayOrderItemBos;

}
