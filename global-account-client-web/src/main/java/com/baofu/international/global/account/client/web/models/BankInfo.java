package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 银行信息
 * <p>
 * @author : hetao Date:2017/11/04 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class BankInfo {
    /**
     * 银行id
     */
    private String bankId;

    /**
     * 银行名称
     */
    private String bankName;
}
