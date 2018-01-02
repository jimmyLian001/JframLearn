package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MQ 消息队列名称
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum MqSendQueueNameEnum {
    /**
     * 提现通知渠道MQ
     */
    CGW_CROSS_BORDER_SERVICE_TRANSFER("CGW_CROSS_BORDER_SERVICE_TRANSFER", "提现通知渠道MQ"),

    /**
     * 提现文件(用于明细校验)上传后处理队列
     */
    GLOBAL_WITHDRAW_FILE_VERIFY_QUEUE_NAME("GLOBAL_WITHDRAW_FILE_VERIFY_QUEUE_NAME", "提现文件(用于明细校验)上传后处理队列"),

    /**
     * 用户发起汇入汇款申请处理队列
     */
    GLOBAL_USER_SETTLE_APPLY_QUEUE_NAME("GLOBAL_USER_SETTLE_APPLY_QUEUE_NAME", "用户发起汇入汇款申请处理队列"),

    /**
     * 用户发起提现申请处理队列
     */
    GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME("GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME", "用户发起提现申请处理队列"),

    /**
     * 提现创建订单队列
     */
    GLOBAL_USER_CREATE_ORDER_QUEUE_NAME("GLOBAL_USER_CREATE_ORDER_QUEUE_NAME", "提现创建订单队列"),

    /**
     * 开通收款账户开通结果队列
     */
    GLOBAL_CREATE_USER_QUEUE_NAME_TWO("GLOBAL_CREATE_USER_QUEUE_NAME_TWO", "开通收款账户开通结果队列"),

    /**
     * 用户提现成功代理商分润处理队列
     */
    GLOBAL_AGENT_SHARE_PROFIT_QUEUE_NAME("GLOBAL_AGENT_SHARE_PROFIT_QUEUE_NAME", "用户提现成功代理商分润处理队列"),

    /**
     * 收款账户账户注册成功处理队列
     */
    GLOBAL_ACCOUNT_REGISTER_SUCCESS_QUEUE_NAME("GLOBAL_ACCOUNT_REGISTER_SUCCESS_QUEUE_NAME", "收款账户账户注册成功处理队列"),

    /**
     * 收款账户开通账户成功处理队列
     */
    GLOBAL_ACCOUNT_OPEN_SUCCESS_QUEUE_NAME("GLOBAL_ACCOUNT_OPEN_SUCCESS_QUEUE_NAME", "收款账户开通账户成功处理队列"),

    /**
     * 收款账户Skyee收支明细队列
     */
    GLOBAL_SKYEE_ACC_DETAIL_QUEUE_NAME("GLOBAL_SKYEE_ACC_DETAIL_QUEUE_NAME", "收款账户Skyee收支明细队列"),

    /**
     * Skye下载用户申请开户数据队列
     */
    SKYEE_USER_ACC_APPLY_DATA_ZIP_EXPORT("SKYEE_USER_ACC_APPLY_DATA_ZIP_EXPORT", "Skye下载用户申请开户数据队列"),;
    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
