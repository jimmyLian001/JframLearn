package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：计算用户提现手续费请求参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserWithdrawFeeDto implements Serializable {

    private static final long serialVersionUID = 2630588497291169049L;

    /**
     * 用户号
     */
    @NotNull(message = "用户号[userNo]不能为空")
    private Long userNo;

    /**
     * 提现明细
     */
    private List<UserWithdrawFeeDetailDto> userWithdrawFeeDetailDtoList;
}
