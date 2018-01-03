package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 1、用户提现确认请求参数信息
 * </p>
 * User: 香克斯  Date: 2017/11/8 ProjectName:account-client  Version: 1.0
 */
@Getter
@Setter
@ToString
public class WithdrawConfirmVo {

    /**
     * 提现订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 提现订单币种
     */
    private String ccy;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 银行卡记录编号
     */
    private Long bankCardRecordNo;

    /**
     * 收款账户编号
     */
    private String payeeAccNo;
}
