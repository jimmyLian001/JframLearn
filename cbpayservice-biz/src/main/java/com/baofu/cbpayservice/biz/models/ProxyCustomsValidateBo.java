package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.util.List;

/**
 * excel和api内容 对象
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsValidateBo {

    /**
     * 商品集合
     */
    private List<CbpayGoodsItemBo> cbpayGoodsItemBos;

    /**
     * 商户请求流水号
     */
    @NotBlank(message = "商户请求流水号不能为空")
    @Pattern(regexp = Constants.NO_SPACE, message = "商户请求流水号填写异常")
    @Length(max = 50, message = "商户请求流水号长度异常")
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
    @DecimalMin(value = Constants.ZERO, message = "订单金额必须大于0", inclusive = false)
    @DecimalMax(value = Constants.MAX_QUATO, message = "订单金额不能大于99999999999999999.99")
    private String orderMoney;

    /**
     * 付款人证件类型
     */
    @NotBlank(message = " 付款人证件类型不能为空")
    @Pattern(regexp = "(01)|(1)|(02)|(2)", message = "付款人证件类型只能填写01或02")
    private String payeeIdType;

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
     * 持卡人姓名
     */
    @NotBlank(message = "持卡人姓名不能为空")
    @Length(max = 32, message = "持卡人姓名长度异常")
    private String idHolder;

    /**
     * 身份证号或组织机构代码
     */
    //@NotBlank(message = "身份证号或组织机构代码不能为空")
    //@Pattern(regexp = Constants.ID_REGX+"|"+Constants.OC_REGX, message = "身份证号或组织机构代码填写异常")
    private String idCardNo;

    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    @Length(max = 32, message = "银行卡号长度异常")
    private String bankCardNo;

    /**
     * 预留手机号
     */
    @Length(max = 11, message = "预留手机号长度异常")
    private String mobile;

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
     * 支付货款
     */
    private String payGoodsAmount;

    /**
     * 支付税款
     */
    private String payTaxAmount;

    /**
     * 支付运费
     */
    private String payFeeAmount;

    /**
     * 支付保费
     */
    private String payPreAmount;

    /**
     * 口岸代码
     */
    @Digits(integer = 6, fraction = 0, message = "口岸代码填写异常")
    private String functionId;

    /**
     * 商户编号
     */
    private String memberId;

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 产品编号
     */
    private String productId;

    /**
     * 是否需要实名认证
     */
    @Pattern(regexp = "N|Y|n|y|\\B", message = "是否需要实名认证填写异常")
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
     * 是否推送支付单
     */
    private String pushFlag;

    /**
     * 运单信息
     */
    private OrderLogisticsValidationBo orderLogisticsValidationBo;
}
