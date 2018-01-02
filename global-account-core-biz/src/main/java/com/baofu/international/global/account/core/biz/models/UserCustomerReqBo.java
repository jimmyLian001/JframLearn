package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录信息
 * <p>
 * User: 康志光 Date: 2017/11/06 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserCustomerReqBo {

    /**
     * 登录号
     */
    private String loginNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 客户号
     */
    private Long customerNo;

}
