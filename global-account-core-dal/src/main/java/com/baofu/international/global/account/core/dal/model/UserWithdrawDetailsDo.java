package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户提现明细Do
 *
 * @author dxy  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class UserWithdrawDetailsDo {

    /**
     * 提现申请号
     */
    private String withdrawId;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 商户名称
     */
    private String userName;

    /**
     * 提现币种
     */
    private String withdrawCCY;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 费率
     */
    private BigDecimal feeRate;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 汇率
     */
    private BigDecimal rate;

    /**
     * 结算金额
     */
    private BigDecimal settleAmount;

    /**
     * 提现状态　：0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private String withdrawStatus;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 申请时间
     */
    private Date applyTime;
}
