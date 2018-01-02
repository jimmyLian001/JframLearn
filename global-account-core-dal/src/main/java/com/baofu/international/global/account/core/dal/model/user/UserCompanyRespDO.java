package com.baofu.international.global.account.core.dal.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 企业用户响应信息
 * <p>
 * 1、企业用户响应DO
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
@Getter
@Setter
@ToString
public class UserCompanyRespDO {

    /**
     * 会员号
     */
    private String userNo;
    /**
     * 会员姓名
     */
    private String name;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 实名状态（0-未认证；1-认证中 2-已认证 默认为未认证）
     */
    private String realNameStatus;
    /**
     * 账户是否开通（0-未开通；1-开通）
     */
    private String openAccStatus;
    /**
     * 创建时间
     */
    private Date createAt;

}
