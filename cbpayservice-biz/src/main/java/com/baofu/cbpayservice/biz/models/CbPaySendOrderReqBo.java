package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 描述
 * <p>
 * </p>
 * User: 不良人 Date:2016/12/14 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPaySendOrderReqBo {
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
    private Long terminalId;

    /**
     * 商品信息集合
     */
    private List<CbpayGoodsItemBo> cbpayGoodsItemBos;

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
     * 订单支付类型(quick-快捷支付、auth-认证支付、b2c-网银支付、wxs-微信扫码、alis-支付宝扫码)
     */
    private String orderPayType;

    /**
     * 客户端IP
     */
    private String clientIp;

}
