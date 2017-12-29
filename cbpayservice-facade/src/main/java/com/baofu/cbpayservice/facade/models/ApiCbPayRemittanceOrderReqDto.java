package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 创建汇款订单申请实体类
 * <p>
 * Created by 莫小阳 on 2017/9/28.
 */
@Setter
@Getter
@ToString
public class ApiCbPayRemittanceOrderReqDto implements Serializable {


    private static final long serialVersionUID = 5650202999283043282L;
    /**
     * 商户在宝付系统开通的唯一商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 商户请求宝付系统的申请订单号，由商户保证此单号唯一
     */
    @NotBlank(message = "申请订单号不能为空")
    @Length(max = 64, message = "申请流水号不能超过64位")
    private String remitApplyNo;

    /**
     * 原始币种，默认都为CNY，此币种应与订单明细中币种一致
     */
    @NotBlank(message = "原始币种不能为空")
    private String originalCcy;

    /**
     * 原始金额，此金额应与订单明细汇总金额一致
     */
    @NotNull(message = "原始金额不能为空")
    private BigDecimal originalAmt;

    /**
     * 银行开户名
     */
    @NotBlank(message = "银行开户名不能为空")
    @Length(max = 64, message = "银行开户名不能超过64位")
    private String bankAccName;

    /**
     * 银行名称
     */
    @NotBlank(message = "银行名称不能为空")
    @Length(max = 128, message = "银行名称不能超过128位")
    private String bankName;

    /**
     * 银行账户编号
     */
    @NotBlank(message = "银行账户编号不能为空")
    private String bankAccNo;

    /**
     * 汇款币种，实际需要付汇的币种
     */
    @NotBlank(message = "汇款币种不能为空")
    private String remitCcy;

    /**
     * 文件批次号集合，以”,”英文逗号分隔开
     */
    @NotBlank(message = "文件批次号不能为空")
    private String fileBatchNo;

    /**
     * 跨境汇款申请结果通知的接口地址
     */
    @NotBlank(message = "商户通知接口地址不能为空")
    @Length(max = 256, message = "商户通知接口地址不能超过256位")
    private String notifyUrl;

    /**
     * 商户终端号
     */
    @NotNull(message = "商户终端号不能为空")
    private Long terminalId;

}
