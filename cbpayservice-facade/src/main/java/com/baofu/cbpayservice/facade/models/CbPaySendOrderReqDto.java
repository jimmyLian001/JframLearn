package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 不良人 on 2016/12/13.
 */
@Setter
@Getter
@ToString
public class CbPaySendOrderReqDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号不能为空")
    private String memberTransId;

    /**
     * 终端编号
     */
    @NotNull(message = "终端编号不能为空")
    private Long terminalId;

    /**
     * 商品信息集合
     */
    @NotNull(message = "商品信息集合不能为空")
    private List<CbpayGoodsItemDto> cbpayGoodsItemDtos;


    /**
     * 订单支付类型(quick-快捷支付、auth-认证支付、b2c-网银支付、wxs-微信扫码、alis-支付宝扫码)
     */
    @NotBlank(message = "订单支付类型不能为空")
    private String orderPayType;

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
     * 客户端IP
     */
    @NotBlank(message = "客户端IP不能为空")
    private String clientIp;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 功能编号
     */
    private Integer functionId;
}
