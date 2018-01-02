package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户登录接口请求参数
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-core Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserLoginReqDTO implements Serializable {

    private static final long serialVersionUID = 1385633668829087402L;
    /**
     * 用户名（登录号）
     */
    @NotBlank(message = "用户名不能为空")
    private String loginNo;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String loginPwd;

    /**
     * 登录IP
     */
    private String loginIp;

}