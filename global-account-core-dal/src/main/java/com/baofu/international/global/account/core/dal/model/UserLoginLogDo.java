package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserLoginLogDo extends BaseDo {
    /**
     * 登录账户号
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 登录账户类型：1-邮箱，2-手机号码
     */
    private int loginType;

    /**
     * 登录账户状态：1-正常，2-注销，3-锁定，4-冻结
     */
    private int loginState;

    /**
     * 登录IP
     */
    private String loginIp;
}