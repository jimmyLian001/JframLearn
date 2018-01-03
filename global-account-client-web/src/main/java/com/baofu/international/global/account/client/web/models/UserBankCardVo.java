package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、用户银行卡返回页面前端信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserBankCardVo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 账户类型 1-对公，2-对私
     */
    private Integer accType;

    /**
     * 银行卡持有人
     */
    private String cardHolder;

    /**
     * 银行账号
     */
    private String cardNo;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 银行卡类型
     */
    private Integer cardType;

    /**
     * 状态 -1-冻结 0-待激活 1-已激活 2-失效
     */
    private Integer state;

    /**
     * 记录编号
     */
    private Long recordNo;
    /**
     * 记录编号
     */
    private String bankCode;
}
