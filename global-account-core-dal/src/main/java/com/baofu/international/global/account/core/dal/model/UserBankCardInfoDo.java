package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserBankCardInfoDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户号
     */
    private Long recordNo;

    /**
     * 账户类型 1-对公，2-对私
     */
    private int accType;

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
     * 银行卡类型 1借记卡 2货记卡 3预付卡 4准贷记卡
     */
    private int cardType;

    /**
     * 状态 0-冻结 1-待激活 2-已激活 3-失效
     */
    private int state;

    /**
     * 银行编码，主要用户页面显示银行图标使用
     */
    private String bankCode;

    /**
     * 支行名称
     */
    private String bankBranchName;
}