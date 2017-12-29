package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class CbPayOrderReqDto implements Serializable {

    /**
     * 订单币种
     */
    @NotBlank(message = "订单币种不能为空")
    private String orderCcy;

    /**
     * 支付编号
     */
    @NotNull(message = "支付编号不能为空")
    private Integer payId;

    /**
     * 渠道编号
     */
    @NotNull(message = "渠道编号不能为空")
    private Integer channelId;

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
    private Integer terminalId;

    /**
     * 功能编号
     */
    @NotNull(message = "功能编号不能为空")
    private Integer functionId;

    /**
     * 产品编号
     */
    @NotNull(message = "产品编号不能为空")
    private Integer productId;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    private BigDecimal orderMoney;

    /**
     * 交易时间
     */
    @NotNull(message = "交易时间不能为空")
    private Date transTime;

    /**
     * 通知类型
     */
    @Pattern(regexp = "(PAGE)|(SERVICE)|(PAGE_SERVER)", message = "通知类型异常")
    @NotBlank(message = "通知类型不能为空")
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
     * 订单类型：B2C:b2c订单，WEIXIN:微信订单，WXAPP:微信APP订单，ALIPAY:支付宝订单
     */
    @Pattern(regexp = "(B2C)|(WEIXIN)|(WXAPP)|(ALIPAY)", message = "订单类型异常")
    @NotBlank(message = "订单类型不能为空")
    private String orderType;

    @NotNull(message = "交易金额不能为空")
    private BigDecimal transMoney;

    /**
     * 交易币种
     */
    @NotNull(message = "交易币种不能为空")
    private String transCcy;

    /**
     * 订单汇率
     */
    @NotNull(message = "订单汇率不能为空")
    private BigDecimal transRate;

    /**
     * 商品信息集合
     */
    @NotNull(message = "商品信息集合不能为空")
    private List<CbPayOrderItemReqDto> cbPayOrderItemReqDtoList;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 客户端
     */
    @NotNull(message = "客户端不能为空")
    private String client;
}
