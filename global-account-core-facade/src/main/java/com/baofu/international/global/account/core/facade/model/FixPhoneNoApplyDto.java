package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户申请修改注册手机号申请对象信息
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixPhoneNoApplyDto extends BaseDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userNo;

    /**
     * 当前手机号
     */
    @Pattern(regexp = "^0?(13|14|15|17|18)[0-9]{9}$", message = "手机号格式不正确")
    private String currentPhoneNumber;

    /**
     * 修改后手机号
     */
    @NotNull(message = "修改后手机号不能为空")
    @Pattern(regexp = "^0?(13|14|15|17|18)[0-9]{9}$", message = "手机号格式不正确")
    private String afterFixPhoneNumber;
    /**
     * 修改后手机号
     */
    @NotNull(message = "用户类型不能为空")
    private Integer loginType;
    /**
     * 修改后手机号
     */
    @NotNull(message = "用户号不能为空")
    private String loginNo;
}
