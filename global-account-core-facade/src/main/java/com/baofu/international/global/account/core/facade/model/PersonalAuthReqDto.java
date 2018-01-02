package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 个人认证信息
 * <p>
 * </p>
 *
 * @author : hetao  Date: 2017/11/6 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class PersonalAuthReqDto implements Serializable {

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 身份证姓名
     */
    private String idCardName;

    /**
     * 登录名
     */
    private String loginNo;
}
