package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户申请添加企业银行卡对象信息
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class AddCompanyBankCardApplyDto extends BaseDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private Long userNo;

    /**
     * 账户类型 1-对公，2-对私
     */
    @NotNull(message = "账户类型不能为空")
    private int accType;

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

    /**
     * 开户行名称
     */
    @NotNull(message = "开户行不能为空")
    private String bankCode;

    /**
     * 开户行支行名称
     */
    @NotNull(message = "支行不能为空")
    private String bankBranchName;

}
