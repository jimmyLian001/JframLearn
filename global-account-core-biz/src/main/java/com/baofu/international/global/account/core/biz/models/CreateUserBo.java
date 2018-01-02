package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * description:创建用户信息 Bo
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class CreateUserBo {

    /**
     * 登录账户
     */
    private String loginNo;

    /**
     * 用户类型  1-个人 2-企业
     */
    private Integer userType;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 安全保护问题1
     */
    private String questionNoOne;

    /**
     * 问题答案1
     */
    private String answerOne;

    /**
     * 问题编号2
     */
    private String questionNoTwo;

    /**
     * 问题答案2
     */
    private String answerTwo;

    /**
     * 问题编号3
     */
    private String questionNoThree;

    /**
     * 问题答案3
     */
    private String answerThree;
}
