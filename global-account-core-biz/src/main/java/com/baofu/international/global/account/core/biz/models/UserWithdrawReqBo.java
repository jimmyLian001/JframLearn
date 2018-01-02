package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 1、用户结汇完成之后转账至国内银行卡请求参数信息
 * </p>
 * User: 香克斯  Date: 2017/11/5 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserWithdrawReqBo {

    /**
     * 请求唯一流水号
     */
    private String requestNo;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 银行卡持有人
     */
    private String cardHolder;

    /**
     * 银行名称，可通过卡bin获取
     */
    private String bankName;

    /**
     * 预留信息
     */
    private String remarks;

    /**
     * 银行身份证卡号
     */
    private String cardId;
}
