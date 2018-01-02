package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserOnlnDo extends BaseDo {
    /**
     * 登录账户号
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * sessionid
     */
    private String sesnId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 用户所在服务器标识
     */
    private String loginSrvrSdsc;
}