package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * api代理报关接口入参
 * <p>
 * User: 不良人 Date:2017/1/11 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class ApiProxyCustomsV2Dto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private long memberId;

    /**
     * 终端编号
     */
    @NotNull(message = "终端编号不能为空")
    private Long terminalId;

    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号为空")
    private String memberTransId;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    @NotBlank(message = "订单币种不能为空")
    private String orderCcy;

    /**
     * 身份证卡号
     */
    @NotBlank(message = "身份证卡号不能为空")
    private String idCardNo;

    /**
     * 身份证姓名
     */
    @NotBlank(message = "身份证姓名不能为空")
    private String idName;

    /**
     * 银行卡号
     */
    @NotBlank(message = "银行账号或其他账号信息不能为空")
    private String bankCardNo;

    /**
     * 额外信息
     */
    @NotBlank(message = "额外信息不能为空")
    private String goodsInfo;

    /**
     * 付款人证件类型
     */
    @NotBlank(message = "付款人证件类型不能为空")
    private String idType;

    /**
     * 行业类型
     */
    @NotBlank(message = "行业类型不能为空")
    private String industryType;

    /**
     * 交易时间
     */
    @NotNull(message = "交易时间不能为空")
    private Date transTime;

    /**
     * 客户端id地址
     */
    private String clientIp;

    /**
     * 持卡人手机号码
     */
    private String mobile;
}
