package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息Bo
 * <p>
 * Created by kangzhiguang on 2017/11/6 0005.
 */
@Setter
@Getter
@ToString
public class UserInfoBo {

    /**
     * 登陆号
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 薪资编号
     */
    private Long userInfoNo;

    /**
     * 企业名称
     */
    private String orgName;

    /**
     * 个人或者法人姓名
     */
    private String name;

    /**
     * 个人或者法人证件号
     */
    private String idNo;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 邮件
     */
    private String email;

    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 默认为未认证
     */
    private Integer realnameStatus;


    /**
     * 登录类型 1-邮箱，2-手机号码
     */
    private Integer loginType;

}
