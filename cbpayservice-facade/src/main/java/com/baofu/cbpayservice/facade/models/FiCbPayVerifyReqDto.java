package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 单笔实名认证
 * <p>
 * User: 王东江 Date:2017/06/05 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayVerifyReqDto implements Serializable {

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long memberId;


    /**
     * 商户名
     */
    @NotNull(message = "商户名称不能为空")
    private String memberName;


    /**
     * 文件批次号
     */
    @NotNull(message = "文件批次号不能为空")
    private Long fileBathNo;


    /**
     * 订单类型  0:购付汇  1： 结汇   2：海关支付单
     */
    @NotNull(message = "订单类型不能为空")
    private String orderType;

    /**
     * 宝付订单号
     */
    @NotNull(message = "宝付订单号不能为空")
    private Long orderId;

    /**
     * 商户订单号
     */
    @NotNull(message = "商户订单号不能为空")
    private String memberTransId;

    /**
     * 身份证号   明文传输  无需加密
     */
    @NotNull(message = "身份证不能为空")
    private String idCard;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    private String idName;

}
