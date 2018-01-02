package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：用户分发请求参数
 * User: feng_jiang Date:2017/11/ ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserSettleApplyDto implements Serializable {

    /**
     * 商户汇款流水号
     */
    @NotNull(message = "商户汇款流水号[remitReqNo]不能为空")
    private String remitReqNo;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号[memberId]不能为空")
    private Long memberId;

    /**
     * 状态
     */
    @NotBlank(message = "状态[status]不能为空")
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 文件处理失败原因
     */
    private String errorFileName;

    /**
     * 真实结汇金额 *****宝付系统结汇金额，币种同订单币种，此金额理论和订单金额一致，订单明细出现反洗钱未通过时，可能不一致
     */
    @NotNull(message = "真实结汇金额[realSettleAmt]不能为空")
    private BigDecimal realSettleAmt;

    /**
     * 结汇汇率 *****结汇金额转换人民币时的汇率，汇率以100计算
     */
    @NotNull(message = "结汇汇率[exchangeRate]不能为空")
    private BigDecimal exchangeRate;

    /**
     * 结汇金额（结汇金额转换人民币之后的金额，币种为人民币,小数位两位）
     */
    @NotNull(message = "结汇金额[exchangeAmt]不能为空")
    private BigDecimal exchangeAmt;

    /**
     * 手续费 *****结汇完成之后宝付收取商户手续费,小数位两位
     */
    @NotNull(message = "手续费[settleFee]不能为空")
    private BigDecimal settleFee;


    /**
     * 结算余额 *****结汇完成之后结汇金额减去宝付收取商户手续费,小数位两位
     */
    @NotNull(message = "结算余额[settleAmt]不能为空")
    private BigDecimal settleAmt;
}
