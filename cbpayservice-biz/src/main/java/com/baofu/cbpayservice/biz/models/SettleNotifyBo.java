package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class SettleNotifyBo {

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
     * 备注信息
     */
    private String remarks;
}
