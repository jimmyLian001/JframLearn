package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserInfoDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private Integer userType;

}