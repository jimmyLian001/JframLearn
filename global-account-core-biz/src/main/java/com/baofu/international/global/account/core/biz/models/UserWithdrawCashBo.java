package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 提现参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@Setter
@ToString
public class UserWithdrawCashBo {
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
     * 提现账户(内部使用)
     */
    private Long accountNo;

    /**
     * 提现银行卡持有人
     */

    private String bankCardHolder;

    /**
     * 提现银行卡帐号
     */

    private String bankCardNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 转账手续费
     */
    private BigDecimal withdrawFee;

    /**
     * 提现费率
     */
    private BigDecimal withdrawFeeRate;

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 记录编号
     */
    private Long bankCardRecordNo;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * 店铺编号
     */
    private Long storeNo;

    /**
     * 渠道编号
     */
    private Long channelId;
}
