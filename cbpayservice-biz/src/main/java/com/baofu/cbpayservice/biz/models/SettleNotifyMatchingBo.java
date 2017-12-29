package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 结汇匹配成功通知商户信息
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class SettleNotifyMatchingBo extends SettleNotifyBo {

    /**
     * 到账金额(外币)
     */
    private BigDecimal incomeAmt;

    /**
     * 到账币种(外币)
     */
    private String incomeCcy;

    /**
     * 错误文件名称
     */
    private String errorFileName;
}
