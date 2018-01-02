package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * description:创建用户信息 ReqDto
 * <p/>
 * Created by liy on 2017/11/6 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class CreateUserReqDto implements Serializable {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名格式不正确")
    private String loginNo;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String loginPwd;


    /**
     * 安全保护问题1
     */
    @NotBlank(message = "安全保护问题1不能为空")
    private String questionNoOne;

    /**
     * 问题答案1
     */
    @NotBlank(message = "问题答案1不能为空")
    private String answerOne;

    /**
     * 问题编号2
     */
    @NotBlank(message = "安全保护问题2不能为空")
    private String questionNoTwo;

    /**
     * 问题答案2
     */
    @NotBlank(message = "问题答案2不能为空")
    private String answerTwo;

    /**
     * 问题编号3
     */
    @NotBlank(message = "安全保护问题3不能为空")
    private String questionNoThree;

    /**
     * 问题答案3
     */
    @NotBlank(message = "问题答案3不能为空")
    private String answerThree;

}
