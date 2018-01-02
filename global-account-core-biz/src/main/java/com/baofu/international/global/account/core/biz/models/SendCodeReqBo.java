package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class SendCodeReqBo {

    private Long userNo;

    /**
     * 手机号或邮箱
     */
    private String param;

    /**
     * 内容(验证码用#code#替换)
     */
    private String content;

    /**
     * rediskey
     */
    private String redisKey;

    /**
     * redis超时时间(默认 120s)
     */
    private Long timeOut;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 发送时间
     */
    private Date sendTime;
}
