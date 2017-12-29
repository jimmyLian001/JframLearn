package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/7/17 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ServiceConfigEnum {

    /**
     * 反洗钱失败,通知业务人员,收件人key
     */
    AML_FAIL_BUSINESS_TO_KEY("mail.smtp.amlFailToBusinessEmailTO", "反洗钱失败,通知业务人员,收件人key"),

    /**
     * 反洗钱失败,通知业务人员,抄送人key
     */
    AML_FAIL_BUSINESS_CC_KEY("mail.smtp.amlFailToBusinessEmailCC", "反洗钱失败,通知业务人员,抄送人key"),

    /**
     * 反洗钱部分成功,通知业务人员,收件人key
     */
    AML_PORTION_SUCCESS_BUSINESS_TO_KEY("mail.smtp.amlPortionSuccessBusinessTO", "反洗钱部分成功,通知业务人员,收件人key"),

    /**
     * 反洗钱部分成功,通知业务人员,抄送人key
     */
    AML_PORTION_SUCCESS_BUSINESS_CC_KEY("mail.smtp.amlPortionSuccessBusinessCC", "反洗钱部分成功,通知业务人员,抄送人key"),

    /**
     * 结汇订单明细金额查询汇率:计算订单金额不能超过45000
     */
    PING_AN_SETTLE_CHANNEL_ID("ping_an_settle_channel_id", "结汇订单明细金额查询汇率"),

    /**
     * 宝付wpre主账户
     */
    WYRE_MASTER_ACC_NO("wyre_master_acc_no", "宝付wpre主账户"),

    /**
     * 子账户转账到主账户费率
     */
    WYRE_FEE_RATE("wyre_fee_rate", "子账户转账到主账户费率"),
    ;

    /**
     * 配置中心key
     */
    private String key;

    /**
     * 描述
     */
    private String value;

}
