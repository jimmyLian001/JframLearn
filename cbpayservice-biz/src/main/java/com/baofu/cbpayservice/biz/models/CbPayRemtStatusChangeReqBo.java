package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 跨境人民币汇款更新请求参数信息
 * User: wanght Date:2016/11/11 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemtStatusChangeReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 更新人员
     */
    private String updateBy;

    /**
     * 审核状态：INIT:初始，AUDITING：审核处理中 ,TRUE:审核通过，FALSE:审核失败
     */
    private String auditStatus;

    /**
     * 渠道状态：INIT:初始，TRUE:返回成功，PROCESSING：银行处理中 FALSE：返回失败
     */
    private String channelStatus;

    /**
     * 结算状态：INIT:未结算，TRUE:已结算，FALSE:结算失败
     */
    private String cmStatus;

    /**
     * 审核动作的之前状态
     */
    private String beforeAuditStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 购汇方式
     */
    private Integer exchangeType;
}
