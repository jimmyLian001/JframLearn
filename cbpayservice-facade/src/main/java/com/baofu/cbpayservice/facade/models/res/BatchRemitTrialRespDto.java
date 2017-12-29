package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 试算返回参数
 * <p>
 * User: 不良人 Date:2017/10/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class BatchRemitTrialRespDto implements Serializable {

    private static final long serialVersionUID = 8731443805952816185L;

    /**
     * 汇款金额
     */
    private BigDecimal remitAmt;

    /**
     * 目标币种
     */
    private String targetCcy;

    /**
     * 目标金额
     */
    private BigDecimal targetAmt;

    /**
     * 账户信息
     */
    private String accountInfo;

    /**
     * 手续费金额
     */
    private BigDecimal transferFee;

    /**
     * 现汇卖出价（购汇汇率）
     */
    private BigDecimal sellRateOfCcy;


}
