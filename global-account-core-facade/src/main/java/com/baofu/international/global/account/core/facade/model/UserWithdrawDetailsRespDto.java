package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户提现明细管理后台查询，响应Dto
 *
 * @author dxy  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class UserWithdrawDetailsRespDto implements Serializable {

    private static final long serialVersionUID = 9080294733361535484L;

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
     * 提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 提现币种
     */
    private String withdrawCCY;

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
     * 申请时间
     */
    private Date applyTime;

    /**
     * 完成时间
     */
    private Date finishTime;

}
