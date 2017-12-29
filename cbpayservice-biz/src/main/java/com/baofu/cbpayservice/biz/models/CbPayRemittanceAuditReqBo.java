package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 跨境人民币汇款审核信息请求参数
 * User: wanght Date:2016/10/25 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceAuditReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 审核人员
     */
    private String auditBy;

    /**
     * 审核状态：INIT:初始，TRUE:审核通过，FALSE:审核失败
     */
    private String auditStatus;

    /**
     * 购汇币种（需要购买的币种）
     */
    private String targetCcy;

    /**
     * 购汇方式
     */
    private Integer exchangeType;
}
