package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 添加跨境订单信息请求参数信息
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayOrderReqBo implements Serializable {


    /**
     * 宝付订单号
     */
    private Long orderId;

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
     * 支付编号
     */
    private Integer payId;

    /**
     * 渠道编号
     */
    private Integer channelId;

    /**
     * 支付状态
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
     * 清算标识
     */
    private String cmFlag;

    /**
     * 交易手续费
     */
    private BigDecimal transFee;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 功能编号
     */
    private Integer functionId;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 通知类型
     */
    private String notifyType;

    /**
     * 通知地址
     */
    private String returnUrl;
    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private String commodityAmount;

    /**
     * 页面通知地址
     */
    private String pageReturnUrl;

    /**
     * 附加信息
     */
    private String additionalInfo;

    /**
     * 订单类型：B2C:b2c订单，WEIXI:微信订单，ALIPAY:支付宝订单
     */
    private String orderType;

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

    /**
     * 商品信息集合
     */
    private List<CbPayOrderItemBo> cbPayOrderItemBos;

    /**
     * 请求IP地址
     */
    private String clientIp;

    /**
     * 客户端
     */
    private String client;

    /**
     * 文件批次号
     */
    private Long batchNo;

    /**
     * 行业类型
     */
    private String careerType;

    /**
     * 收款人证件类型：1-身份证
     */
    private Integer payeeIdType;
}
