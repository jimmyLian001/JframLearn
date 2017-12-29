package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class CbPaySettleOrderValidateBo {

    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号不能为空")
    @Length(max = 50, message = "商户订单号长度不能大于50")
    private String memberTransId;

    /**
     * 收款人证件类型：1-身份证
     */
    @NotBlank(message = "收款人证件类型不能为空")
    @Pattern(regexp = "(01)|(1)|(02)|(2)", message = "付款人证件类型只能填写01或02")
    private String payeeIdType;

    /**
     * 收款人证件号码
     */
    //@NotBlank(message = "收款人证件号码不能为空")
    //@Pattern(regexp = Constants.ID_REGX+"|"+Constants.OC_REGX, message = "收款人证件号码填写异常")
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    @NotBlank(message = "收款人证件姓名不能为空")
    @Length(max = 128, message = "收款人证件姓名长度能大于128")
    private String payeeName;

    /**
     * 收款人帐号
     */
    @NotBlank(message = "收款人帐号不能为空")
    @Length(max = 32, message = "收款人帐号长度不能大于32")
    private String payeeAccNo;

    /**
     * 商户交易时间
     */
    @NotBlank(message = "交易时间不能为空")
    @Pattern(regexp = Constants.YYYY_MM_DD_HH_MM_SS + "|" + Constants.YYYY_MM_DD + "|" +
            Constants.YYYYMMDDHHMMSS + "|" + Constants.YYYYMMDD + "|" +
            Constants.YYYY_MM_DD_HH_MM_SS_SLANT + "|" + Constants.YYYY_MM_DD_SLANT,
            message = "交易时间填写异常")
    private String memberTransDate;

    /**
     * 交易币种，一般是外币（交易币种）
     */
    @NotBlank(message = "交易币种不能为空")
//    @Pattern(regexp = Constants.SETTLE_CCY,message = "交易币种填写错误")
    private String orderCcy;

    /**
     * 交易金额（交易金额）
     */
    @NotBlank(message = "交易金额不能为空")
    @Digits(integer = 17, fraction = 2, message = "交易金额填写不正确")
    @DecimalMin(value = Constants.ZERO, message = "交易金额必须大于0", inclusive = false)
    @DecimalMax(value = Constants.MAX_QUATO, message = "交易金额不能大于99999999999999999.99")
    private String orderAmt;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @Length(max = 10240, message = "商品名称长度超过10240")
    @Pattern(regexp = "[^；]*", message = "商品名称不能含有中文分号")
    private String goodsName;

    /**
     * 商品单价
     */
    @NotBlank(message = "商品单价不能为空")
    private String goodsPrice;

    /**
     * 商品数量
     */
    @NotBlank(message = "商品数量不能为空")
    private String goodsNum;

    /**
     * 商户编号
     */
    private String memberId;

    /**
     * 宝付订单号
     */
    private String orderId;

    /**
     * 跨境订单运单信息校验对象
     */
    private OrderLogisticsValidationBo orderLogisticsValidationBo;
}