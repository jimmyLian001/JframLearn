package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.enums.ClearAccResultEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: yangjian  Date: 2017-05-25 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ClearingResponseBo {

    /**
     * 处理返回结果
     */
    private ClearAccResultEnum clearAccResultEnum;

    /**
     * 订单号
     */
    private Long dealId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 通知会员状态
     */
    private boolean notifyMaState;

    /**
     * 付款方费用
     */
    private BigDecimal payerFee;

    /**
     * 收款方费用
     */
    private BigDecimal payeeFee;

    /**
     * 交易时间
     */
    private Date dealDate;
}
