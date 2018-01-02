package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户申请修改注册手机号申请对象信息
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class UserRegisterTelInfoBo {

    /**
     * 用户名
     */
    private String userNo;

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
