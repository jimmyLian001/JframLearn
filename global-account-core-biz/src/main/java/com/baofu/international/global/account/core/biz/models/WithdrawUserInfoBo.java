package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 提现用户信息（个人机构公用）
 * <p>
 * User: feng_jiang Date:2017/11/21 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class WithdrawUserInfoBo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 收款人证件类型：1-身份证
     */
    private int payeeIdType;

    /**
     * 收款人证件号码
     */
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    private String payeeName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户姓名
     */
    private String name;

}
