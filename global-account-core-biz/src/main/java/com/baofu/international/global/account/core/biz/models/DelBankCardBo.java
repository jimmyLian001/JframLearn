package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户申请添加个人银行卡对象信息
 * <p/>
 * User: lian zd Date:2017/11/6 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class DelBankCardBo {

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 银行卡持有人
     */
    private String cardHolder;

    /**
     * 银行账号
     */
    private String cardNo;

    /**
     * 所属银行
     */
    private String bankName;

    /**
     * 记录编号
     */
    private Long recordNo;
}
