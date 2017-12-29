package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description:宝付转账备付金信息
 * <p/>
 * Created by liy on 2017/9/10 0010 ProjectName：cbp
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum PayeeTransferInfoEnum {

    /**
     * 商户编号
     */
    MEMBER_ID("payee.member.id", "商户编号"),

    /**
     * 转出账户号
     */
    SOURCE_ACC_NO("payee.source.acc.no", "转出账户号"),

    /**
     * 目标账户号
     */
    DEST_ACC_NO("payee.dest.acc.no", "目标账户号"),

    /**
     * 手续费百分比
     */
    fee_rate("payee.fee.rate", "手续费百分比"),;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
