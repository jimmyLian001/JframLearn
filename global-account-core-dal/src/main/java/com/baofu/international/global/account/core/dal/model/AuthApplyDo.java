package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class AuthApplyDo extends BaseDo {
    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private int userType;

    /**
     * 申请时间
     */
    private Date applyAt;

    /**
     * 申请类型 1-正常申请
     */
    private int applyType;

    /**
     * 认证类型 1-银行卡认证 2-企业信息认证 3-身份证信息认证
     */
    private int authMethod;

    /**
     * 状态 1-待认证 2-认证中 3-认证失败 4-认证成功 5-失效
     */
    private int authStatus;

    /**
     * 认证失败原因
     */
    private String failReason;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;
}