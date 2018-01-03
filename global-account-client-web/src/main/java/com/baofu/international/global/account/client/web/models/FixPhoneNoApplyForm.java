package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户申请修改注册手机号申请对象信息
 * <p/>
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixPhoneNoApplyForm{

    /**
     * 用户名
     */
    private String userNo;

    /**
     * 手机验证码
     */
    private String messageCode;

    /**
     * 问题序号
     */
    private String questionSequence;

    /**
     * 问题编号
     */
    private String questionNo;

    /**
     * 问题答案
     */
    private String answer;

    /**
     * 当前手机号
     */
    private String currentPhoneNumber;

    /**
     * 修改后手机号
     */
    private String afterFixPhoneNumber;
}
