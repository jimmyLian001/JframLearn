package com.baofu.international.global.account.core.facade.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 发送验证码请求对象
 * <p/>
 * User: lian zd Date:2017/11/6 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixTelMessageCodeApplyDto extends BaseDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userNo;

    /**
     * 当前手机号
     */
    @NotNull(message = "当前手机号不能为空")
    @Pattern(regexp = "^0?(13|14|15|17|18)[0-9]{9}$", message = "手机号格式不正确")
    private String currentPhoneNumber;

    /**
     * 短信内容
     */
    @NotNull(message = "短信内容不能为空")
    private String content;

    /**
     * 验证码
     */
    @NotNull(message = "短信验证码不能为空")
    private String messageCode;

    /**
     * 业务类型
     */
    @NotNull(message = "用户名不能为空")
    private String serviceType;

}
