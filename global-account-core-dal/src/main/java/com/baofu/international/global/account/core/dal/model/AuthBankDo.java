package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class AuthBankDo extends BaseDo {
    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 请求渠道流水号
     */
    private Long authReqNo;

    /**
     * 状态 1-申请 2-认证中 3-认证失败 4-认证成功
     */
    private int authStatus;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 用户在银行办理银行卡预留的手机号码
     */
    private String phone;

    /**
     * 银行卡类型 1-借记卡 2-银行账户 3-存折
     */
    private int bankCardType;

    /**
     * 银行卡有效期
     */
    private Date bankCardValiddate;
}