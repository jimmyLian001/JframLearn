package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户申请添加企业银行卡对象信息
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class AddCompanyBankCardBo {

    /**
     * 用户名
     */
    private Long userNo;

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
    private String bankCode;

    /**
     * 开户行支行名称
     */
    private String bankBranchName;

}
