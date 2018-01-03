package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 添加银行卡请求参数
 *
 * @author : 不良人 Date:2017/11/21 ProjectName: account-client Version: 1.0
 */
@Setter
@Getter
@ToString
public class BankInfoDto {

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 银行卡号
     */
    @NotNull(message = "银行卡号不能为空")
    private String bankCardNo;

    /**
     * 账户类型 1-私人，2-企业法人，3-对公帐号
     */
    @NotNull(message = "账户类型不能为空")
    private Integer accType;

    /**
     * 开户行
     */
    private String bankCode;

    /**
     * 支行名称
     */
    private String branchBank;
}
