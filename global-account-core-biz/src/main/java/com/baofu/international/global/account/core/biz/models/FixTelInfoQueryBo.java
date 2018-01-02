package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户密保问题业务对象
 * <p/>
 * User: lian zd Date:2017/11/5 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixTelInfoQueryBo {

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 当前手机号
     */
    private String currentPhoneNumber;
}
