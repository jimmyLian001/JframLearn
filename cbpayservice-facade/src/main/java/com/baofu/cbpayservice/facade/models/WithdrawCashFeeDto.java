package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算wyre提现手续费请求参数
 * <p>
 * User: 不良人 Date:2017/9/12 ProjectName: cbpayservice Version: 1.0
 **/
@Getter
@Setter
@ToString
public class WithdrawCashFeeDto implements Serializable {

    private static final long serialVersionUID = 2630588497291169049L;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号[memberId]不能为空")
    private Long memberId;

    /**
     * 转账金额
     */
    @NotNull(message = "转账金额[transferAmt]不能为空")
    @DecimalMin(value = Constants.MIN_AMT, message = "转账金额填写不正确")
    @DecimalMax(value = Constants.MAX_QUATO, message = "转账金额填写不正确")
    private BigDecimal transferAmt;


}
