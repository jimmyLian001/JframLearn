package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户申请修改注册手机号申请业务信息
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixTelMessageCodeApplyBo {

    /**
     * 用户名
     */
    private String userNo;

    /**
     * 当前手机号
     */
    private String currentPhoneNumber;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 验证码
     */
    private String messageCode;

    /**
     * 业务类型
     */
    private String serviceType;
}
