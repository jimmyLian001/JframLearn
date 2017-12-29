package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

/**
 * 酒店校验对象
 * <p>
 * User: feng_jiang Date:2017/7/7
 */
@Getter
@Setter
@ToString
public class CbPayOrderHotelValidateBo {

    /**
     * 商户请求流水号
     */
    @NotBlank(message = "商户订单号不能为空")
    @Pattern(regexp = Constants.NO_SPACE, message = "商户订单号填写异常")
    @Length(max = 50, message = "商户订单号长度异常")
    private String memberTransId;

    /**
     * 订单币种
     */
    @NotBlank(message = "订单币种不能为空")
    @Pattern(regexp = Constants.CNY_CURRENCY, message = "订单币种填写异常")
    private String orderCcy;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    @Digits(integer = 14, fraction = 2, message = "订单金额填写异常")
    @DecimalMin(value = Constants.ZERO, message = "订单金额必须大于0",inclusive = false)
    @DecimalMax(value = Constants.MAX_QUATO, message = "订单金额不能大于99999999999999999.99")
    private String orderMoney;

    /**
     * 交易时间
     */
    @NotBlank(message = "交易时间不能为空")
    @Pattern(regexp = Constants.YYYY_MM_DD_HH_MM_SS + "|" + Constants.YYYY_MM_DD + "|" +
            Constants.YYYYMMDDHHMMSS + "|" + Constants.YYYYMMDD + "|" +
            Constants.YYYY_MM_DD_HH_MM_SS_SLANT + "|" + Constants.YYYY_MM_DD_SLANT,
            message = "交易时间填写异常")
    private String tradeDate;

    /**
     * 付款人证件类型
     */
    @NotBlank(message = " 付款人证件类型不能为空")
    @Pattern(regexp = "(01)|(1)|(02)|(2)", message = "付款人证件类型只能填写01或02")
    private String payeeIdType;

    /**
     * 付款人姓名
     */
    @NotBlank(message = "付款人姓名不能为空")
    @Length(max = 32, message = "付款人姓名长度异常")
    private String idHolder;

    /**
     * 付款人证件号码
     */
    //@NotBlank(message = "付款人证件号码不能为空")
    //@Pattern(regexp = Constants.ID_REGX+"|"+Constants.OC_REGX, message = "付款人证件号码填写异常")
    private String idCardNo;

    /**
     * 付款人账户
     */
    @NotBlank(message = "付款人账户不能为空")
    @Length(max = 32, message = "付款人账户长度异常")
    private String bankCardNo;

    /**
     * 预留手机号
     */
    @Length(max = 11, message = "预留手机号长度异常")
    private String mobile;

    /**
     * 酒店所在国家
     */
    private String hotelCountryCode;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 入住日期
     */
    private String checkInDate;

    /**
     * 间夜数
     */
    private String nightCount;

}
