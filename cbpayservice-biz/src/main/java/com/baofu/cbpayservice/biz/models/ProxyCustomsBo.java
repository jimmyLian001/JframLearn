package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * excel和api内容 对象
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsBo {

    /**
     * 商品集合
     */
    private List<CbpayGoodsItemBo> cbpayGoodsItemBos;

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
     * 结算币种
     */
    private String paymentCcy;

    /**
     * 结算金额
     */
    private BigDecimal paymentMoney;

    /**
     * 是否推送支付单
     */
    private String pushFlag;

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
     * 支付货款
     */
    private BigDecimal payGoodsAmount;

    /**
     * 支付税款
     */
    private BigDecimal payTaxAmount;

    /**
     * 支付运费
     */
    private BigDecimal payFeeAmount;

    /**
     * 支付保费
     */
    private BigDecimal payPreAmount;

    /**
     * 口岸代码
     */
    private Integer functionId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Long terminalId;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 是否需要实名认证
     */
    private String authFlag;

    /**
     * 电商报关编号，需要报关时必填
     */
    private String companyOrderNo;

    /**
     * 请求Ip
     */
    private String clientIp;

    /**
     * 交易时间
     */
    private Date tradeDate;

    /**
     * 文件批次号
     */
    private Long batchFileId;
}
