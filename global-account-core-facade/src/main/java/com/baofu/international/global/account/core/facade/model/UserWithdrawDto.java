package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：用户提现请求参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserWithdrawDto implements Serializable {

    private static final long serialVersionUID = 5903764019319361153L;

    /**
     * 用户号
     */
    @NotNull(message = "用户号[userNo]不能为空")
    private Long userNo;

    /**
     * 提现明细
     */
    private List<UserWithdrawDetailDto> userWithdrawDetailDtoList;

    /**
     * 记录编号
     */
    @NotNull(message = "银行卡记录编号[bankCardRecordNo]不能为空")
    private Long bankCardRecordNo;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人[createBy]不能为空")
    private String createBy;
}
