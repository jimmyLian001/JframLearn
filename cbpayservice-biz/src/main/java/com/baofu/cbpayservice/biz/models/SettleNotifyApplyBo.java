package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class SettleNotifyApplyBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 汇款流水号
     */
    private String remitReqNo;

    /**
     * 通知商户http地址
     */
    private String notifyUrl;
}
