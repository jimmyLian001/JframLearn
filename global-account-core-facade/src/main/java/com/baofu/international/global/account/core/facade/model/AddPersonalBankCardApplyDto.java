package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户申请添加个人银行卡对象信息
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class AddPersonalBankCardApplyDto extends BaseDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userNo;

    /**
     * 账户类型 1-对公，2-对私
     */
    @NotNull(message = "账户类型不能为空")
    private String accType;

    /**
     * 银行卡持有人
     */
    @NotNull(message = "银行卡持有人不能为空")
    private String cardHolder;

    /**
     * 银行账号
     */
    @Pattern(regexp = "[0-9]*", message = "银行卡号输入错误")
    @NotNull(message = "银行账号不能为空")
    private String cardNo;

}
