package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * <p>
 * 1、用户提现请求参数
 * </p>
 * User: feng_jiang  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserWithdrawReqDto implements Serializable {

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 店铺编号
     */
    private Long storeNo;
}
