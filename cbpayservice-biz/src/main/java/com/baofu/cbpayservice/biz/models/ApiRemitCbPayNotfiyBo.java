package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 航旅校验对象
 * <p>
 * User: 莫小阳 Date:2017/09/29 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ApiRemitCbPayNotfiyBo {

    /**
     * 宝付提供给商户的唯一编号
     */
    private Long memberId;

    /**
     * 宝付提供给商户的终端编号
     */
    private Long terminalId;

    /**
     * 同商户请求宝付系统时的申请流水号一致
     */
    private String remitApplyNo;

    /**
     * 由宝付生成的汇款批次号
     */
    private String remitNo;

    /**
     * 原始币种，默认都为CNY，此币种应与订单明细中币种一致
     */
    private String originalCcy;

    /**
     * 原始金额，此金额应与订单明细汇总金额一致
     */
    private BigDecimal originalAmt;

    /**
     * 银行开户名
     */
    private String bankAccName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账户编号
     */
    private String bankAccNo;

    /**
     * 汇款币种，实际需要付汇的币种
     */
    private String remitCcy;

    /**
     * 汇款金额，汇款币种为CNY时，此金额同原始金额，非CNY时，用原始金额 / 汇款汇率
     * 注：汇款成功时必填
     */
    private BigDecimal remitAmt;

    /**
     * 汇款汇率，原始金额转换为外币时的汇率，采用银行时时汇率。
     * 1、汇款币种为CNY时，汇率为100；2、汇率使用100作为单位
     * 注：汇款成功时必填
     */
    private BigDecimal remitRate;

    /**
     * 汇款手续费
     * 注：汇款成功时必填
     */
    private BigDecimal remitFee;

    /**
     * 汇款成功时间，时间格式为：YYYYMMDDHHmmss
     * 注：汇款成功时必填
     */
    private String successTime;

    /**
     * 汇款状态：
     * 3-汇款失败
     * 4-汇款成功
     */
    private int remitStatus;

    /**
     * 汇款失败原因
     */
    private String remitFailMsg;


}
