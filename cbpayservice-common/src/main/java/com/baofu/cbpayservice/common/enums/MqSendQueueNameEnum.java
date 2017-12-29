package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MQ 消息队列名称
 * User: wanght Date:2016/11/03 ProjectName: asias-icpservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum MqSendQueueNameEnum {

    /**
     * 跨境人民币通知商户消息队列
     */
    CBPAY_NOTIFY_MEMBER_QUEUE_NAME("CBPAY_NOTIFY_MEMBER_QUEUE_NAME", "跨境人民币通知商户消息队列"),

    /**
     * 跨境人民币通知清算消息队列
     */
    CBPAY_NOTIFY_SETTLE_QUEUE_NAME("CBPAY_NOTIFY_SETTLE_QUEUE_NAME", "跨境人民币通知清算消息队列"),

    /**
     * 跨境人民币异步创建汇款订单消息队列
     */
    CBPAY_CREATE_REMITTANCE_ORDER_QUEUE_NAME("CBPAY_CREATE_REMITTANCE_ORDER_QUEUE_NAME", "跨境人民币异步创建汇款订单消息队列"),

    /**
     * 跨境人民币异步审核汇款订单消息队列
     */
    CBPAY_AUDIT_REMITTANCE_QUEUE_NAME("CBPAY_AUDIT_REMITTANCE_QUEUE_NAME", "跨境人民币异步审核汇款订单消息队列"),

    /**
     * 跨境人民币收到汇款通知消息队列
     */
    CBPAY_NOTIFY_REMITTANCE_QUEUE_NAME("CBPAY_NOTIFY_REMITTANCE_QUEUE_NAME", "跨境人民币收到汇款通知消息队列"),

    /**
     * 文件审核后发出消息队列
     */
    CBPAY_PROXY_CUSTOMS_QUEUE_NAME("CBPAY_PROXY_CUSTOMS_QUEUE_NAME", "文件审核后发出消息队列"),

    /**
     * 结汇文件上传后处理队列
     */
    CBPAY_SETTLE_FILE_PROCESS_QUEUE_NAME("CBPAY_SETTLE_FILE_PROCESS_QUEUE_NAME", "结汇文件上传后处理队列"),

    /**
     * 结汇文件(用于明细校验)上传后处理队列
     */
    CBPAY_SETTLE_FILE_TEST_AND_VERIFY_PROCESS_QUEUE_NAME("CBPAY_SETTLE_FILE_TEST_AND_VERIFY_PROCESS_QUEUE_NAME", "结汇文件(用于明细校验)上传后处理队列"),
    /**
     * 代报关服务处理队列
     */
    CBPAY_PROXY_CUSTOMS_PROCESS_QUEUE_NAME("CBPAY_PROXY_CUSTOMS_PROCESS_QUEUE_NAME", "代报关服务处理队列"),

    /**
     * 对代理订单批量进行分发队列
     */
    CBPAY_PROXY_CUSTOMS_DISTRIBUTE_QUEUE_NAME("CBPAY_PROXY_CUSTOMS_DISTRIBUTE_QUEUE_NAME", "对代理订单批量进行分发队列"),

    /**
     * 跨境人民币付汇消息列表
     */
    CGW_CROSS_BORDER_SERVICE_REMIT("CGW_CROSS_BORDER_SERVICE_REMIT", "跨境人民币付汇消息列表"),

    /**
     * 跨境人民币购汇消息列表
     */
    CGW_CROSS_BORDER_SERVICE_EXCHANGE("CGW_CROSS_BORDER_SERVICE_EXCHANGE", "跨境人民币购汇消息列表"),

    /**
     * 跨境人民币邮件服务消息队列
     */
    CBPAY_REMITTANCE_EMAIL_QUEUE_NAME("CBPAY_REMITTANCE_EMAIL_QUEUE_NAME", "跨境人民币邮件服务消息队列"),

    /**
     * 跨境人民币邮件通知清算消息队列
     */
    CBPAY_EMAIL_NOTIFY_SETTLE_QUEUE_NAME("CBPAY_EMAIL_NOTIFY_SETTLE_QUEUE_NAME", "跨境人民币邮件通知清算消息队列"),

    /**
     * 商户发起跨境结汇申请消息队列
     */
    CBPAY_CROSS_SETTLE_CONFIRM("CBPAY_CROSS_SETTLE_CONFIRM", "商户发起跨境结汇申请消息队列"),

    /**
     * 跨境结汇申请消息队列
     */
    CGW_CROSS_BORDER_SERVICE_SETTLE("CGW_CROSS_BORDER_SERVICE_SETTLE", " 跨境结汇申请消息队列"),

    /**
     * 结汇申请发往银行通知队列
     */
    CBPAY_SETTLE_APPLY_QUEUE_NAME("CBPAY_SETTLE_APPLY_QUEUE_NAME", "结汇申请发往银行通知队列"),

    /**
     * 结汇申请银行渠道响应队列
     */
    CBPAY_SETTLE_CALL_QUEUE_NAME("CBPAY_SETTLE_CALL_QUEUE_NAME", "结汇申请银行渠道响应队列"),

    /**
     * 发送邮件mq队列
     */
    CBPAY_SEND_EMAIL_QUEUE_NAME("CBPAY_SEND_EMAIL_QUEUE_NAME", "发送邮件mq队列"),

    /**
     * 发送风控跨境订单队列  wdj
     */
    CBPAY_ORDER_RISKCONTROL_QUEUE_NAME("CBPAY_ORDER_RISKCONTROL_QUEUE_NAME", "发送风控跨境订单队列"),

    /**
     * 提现文件上传队列
     */
    CBPAY_WITHDRAW_FILE_UPLOAD_QUEUE_NAME("CBPAY_WITHDRAW_FILE_UPLOAD_QUEUE_NAME", "提现文件上传队列"),

    /**
     * 根据时间提现文件上传队列
     */
    CBPAY_WITHTIME_FILE_UPLOAD_QUEUE_NAME("CBPAY_WITHTIME_FILE_UPLOAD_QUEUE_NAME", "根据时间提现文件上传队列"),

    /**
     * 解付申请服务队列，网关自己消费
     */
    CBPAY_SOLUTION_PAY_APPLE_QUEUE_NAME("CBPAY_SOLUTION_PAY_APPLE_QUEUE_NAME", "解付申请服务队列"),

    /**
     * 解付申请，发送渠道请求解付
     */
    CGW_CROSS_BORDER_SERVICE_RELIEVE("CGW_CROSS_BORDER_SERVICE_RELIEVE", " 解付申请，发送渠道请求解付"),

    /**
     * 跨境人民币反洗钱消息队列
     */
    CBPAY_AML_APPLY_QUEUE_NAME("CBPAY_AML_APPLY_QUEUE_NAME", "跨境人民币反洗钱消息队列"),

    /**
     * 跨境人民币反洗钱请求明细消息队列
     */
    CGW_CROSS_BORDER_SERVICE_DETAIL_RECORD("CGW_CROSS_BORDER_SERVICE_DETAIL_RECORD", "跨境人民币反洗钱请求明细消息队列"),

    /**
     * 跨境人民币反洗钱响应消息队列
     */
    CBPAY_DETAIL_RECORD_QUEUE_NAME("CBPAY_DETAIL_RECORD_QUEUE_NAME", "跨境人民币反洗钱响应消息队列"),
    /**
     * 跨境人民币汇款重发请求消息队列
     */
    CGW_CROSS_BORDER_SERVICE_RETRY("CGW_CROSS_BORDER_SERVICE_RETRY", "跨境人民币汇款重发请求消息队列"),
    /**
     * 发送风控跨境订单队列  wdj   结汇
     */
    CBPAY_ORDER_RISKCONTROL_QUEUE_NAME_SETTLE("CBPAY_ORDER_RISKCONTROL_QUEUE_NAME_SETTLE", "结汇发送风控跨境订单队列"),

    /**
     * 发送汇款凭证邮件队列  wdj
     */
    CBPAY_REMIT_EMAIL_QUEUE_NAME("CBPAY_REMIT_EMAIL_QUEUE_NAME", "发送汇款凭证邮件队列"),

    /**
     * 结汇结果异步通知商户
     */
    CBPAY_SETTLEMENT_ASYNC_NOTIFY_QUEUE_NAME("CBPAY_SETTLEMENT_ASYNC_NOTIFY_QUEUE_NAME", "结汇结果异步通知商户"),

    /**
     * 人民币备付金变动通知央行
     */
    CBPAY_NOTIFY_CENTRAL_BANK("RSIS150", "人民币备付金变动通知央行"),

    /**
     * wpre转账通知渠道MQ
     */
    CGW_CROSS_BORDER_SERVICE_TRANSFER("CGW_CROSS_BORDER_SERVICE_TRANSFER", "wpre转账通知渠道MQ"),

    /**
     * 解付申请(收款账户)服务队列，网关自己消费
     */
    CBPAY_ACCOUNT_SOLUTION_PAY_APP("CBPAY_ACCOUNT_SOLUTION_PAY_APP", "解付申请(收款账户)服务队列"),

    /**
     * 结汇成功(收款账户)转账服务队列，网关自己消费
     */
    CBPAY_ACCOUNT_SETTLE_TRANSFER("CBPAY_ACCOUNT_SETTLE_TRANSFER", "结汇成功(收款账户)转账服务队列"),

    /**
     * 商户发起跨境结汇申请(收款账户)消息队列
     */
    CBPAY_ACCOUNT_SETTLE_CONFIRM("CBPAY_ACCOUNT_SETTLE_CONFIRM", "商户发起跨境结汇申请(收款账户)消息队列"),

    /**
     * 批量汇款-创建单个文件消息队列
     */
    CREATE_BATCH_REMIT_DETAIL_QUEUE_NAME("CREATE_BATCH_REMIT_DETAIL_QUEUE_NAME", "批量汇款-创建单个文件消息队列"),

    /**
     * 批量文件审核校验发出消息队列
     */
    CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME("CBPAY_PROXY_CUSTOMS_SERIES_QUEUE_NAME", "批量文件审核校验发出消息队列"),
    ;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
