package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description:用户提现汇总查询 RespDto
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
@Getter
@Setter
@ToString
public class UserWithdrawSumQueryRespDto implements Serializable {

    private static final long serialVersionUID = 2868710487071727780L;

    /**
     * 提现批次号
     */
    private Long withdrawBatchId;

    /**
     * 海外渠道
     */
    private String channelName;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt = BigDecimal.ZERO;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 目标账户号
     */
    private String destAccNo;

    /**
     * 转账状态，0-待转账；1-转账处理中，2-转账成功，3-转账失败
     */
    private Integer transferState;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 境内渠道
     */
    private String domesticChannel;
}
