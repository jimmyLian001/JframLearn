package com.baofu.international.global.account.core.facade.model;

import com.baofu.international.global.account.core.common.constant.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：用户提现请求明细
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserWithdrawDetailDto implements Serializable {

    /**
     * 提现金额
     */
    @NotNull(message = "提现金额[withdrawAmt]不能为空")
    @DecimalMin(value = Constants.MIN_AMT, message = "提现金额填写不正确")
    @DecimalMax(value = Constants.MAX_QUATO, message = "提现金额填写不正确")
    private BigDecimal withdrawAmt;

    /**
     * 提现币种
     */
    @NotBlank(message = "提现币种[withdrawCcy]不能为空")
    private String withdrawCcy;

    /**
     * 提现账户(内部使用)
     */
    @NotNull(message = "提现账号[accountNo]不能为空")
    private Long accountNo;

    /**
     * 店铺编号
     */
    @NotNull(message = "店铺编号[storeNo]不能为空")
    private Long storeNo;
}
