package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * api代理报关接口入参
 * <p>
 * User: 不良人 Date:2017/1/11 ProjectName: cbpaygate Version: 1.0
 */

@Setter
@Getter
@ToString
public class ApiProxyCustomsDto implements Serializable {

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private long memberId;

    /**
     * 终端编号
     */
    @NotBlank(message = "终端编号不能为空")
    private Long terminalId;

    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号为空")
    private String memberTransId;

    /**
     * 订单金额
     */
    @NotBlank(message = "订单金额不能为空")
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    @NotBlank(message = "订单币种不能为空")
    private String orderCcy;

    /**
     * 交易金额
     */
    @NotBlank(message = "交易金额不能为空")
    private BigDecimal transAmt;

    /**
     * 交易币种
     */
    @NotBlank(message = "交易币种不能为空")
    private String transCcy;

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
     * 商品信息集合
     */
    @NotNull(message = "商品信息集合不能为空")
    private List<CbpayGoodsItemDto> cbpayGoodsItemDtos;

    /**
     * 客户端id地址
     */
    private String clientIp;

    /**
     * 产品编号
     */
    private Integer productId;

    /**
     * 功能编号
     */
    private Integer functionId;

    /**
     * 持卡人手机号码
     */
    private String mobile;
}
