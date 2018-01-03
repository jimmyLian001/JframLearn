package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 登录信息存放在Session中对象
 *
 * @author: 不良人 Date:2017/11/5 ProjectName: account-client Version: 1.0
 */
@Setter
@Getter
@ToString
public class SessionVo implements Serializable {

    private static final long serialVersionUID = 370954614330939441L;
    /**
     * 登录账户号 手机号或邮箱
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private Integer userType;
}
