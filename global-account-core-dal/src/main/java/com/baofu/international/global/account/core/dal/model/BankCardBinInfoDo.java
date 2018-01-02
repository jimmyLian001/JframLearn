package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class BankCardBinInfoDo extends BaseDo {
    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 发卡行代码
     */
    private String bankCode;

    /**
     * 卡种名称
     */
    private String cardName;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 卡号长度
     */
    private Integer cardLength;

    /**
     * BIN号
     */
    private String cardBin;
}