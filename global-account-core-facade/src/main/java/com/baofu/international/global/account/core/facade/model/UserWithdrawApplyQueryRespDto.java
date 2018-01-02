package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description:用户提现申请查询 RespDto
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
@Getter
@Setter
@ToString
public class UserWithdrawApplyQueryRespDto implements Serializable {

    private static final long serialVersionUID = 159551082004407341L;

    /**
     * 提现明细编号
     */
    private Long withdrawId;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 提现手续费
     */
    private BigDecimal withdrawFee;

    /**
     * 提现汇率
     */
    private BigDecimal transferRate;

    /**
     * 结算金额(目标金额)
     */
    private BigDecimal destAmt;

    /**
     * 用户结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 到账金额(结算金额-用户结算手续费)
     */
    private BigDecimal arrivalAmt;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 提现状态，0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private Integer withdrawState;

    /**
     * 提现完成时间
     */
    private Date withdrawAt;
}
