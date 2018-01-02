package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：计算用户提现手续费请求参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserWithdrawFeeReqBo implements Serializable {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 提现账户
     */
    private Long accountNo;

    /**
     * 店铺编号
     */
    private String storeNo;
}
