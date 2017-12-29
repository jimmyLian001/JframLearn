package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 业务命令信息
 * <p>
 * User: 香克斯  Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class BizCmdDo extends BaseDo {

    private static final long serialVersionUID = -1203843220455615314L;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 执行的server地址
     */
    private String serverIP;

    /**
     * 最后一次执行失败的原因
     */
    private String failReason;

    /**
     * 环境标签
     */
    private String envTag;

    /**
     * 任务状态 db_column: STATUS
     */
    private String status;

    /**
     * 是否正在处理中 db_column: IS_DOING
     */
    private String isDoing;

    /**
     * 重试次数 db_column: RETRY_TIMES
     */
    private Long retryTimes = 0L;

    /**
     * 最大重试次数，负数表示无限次
     */
    private Long maxRetryTimes = -1L;

    /**
     * 下次执行时间 db_column: NEXT_EXE_TIME
     */
    private Date nextExeTime;

    /**
     * 命令执行起始时间 db_column: ENABLE_START_DATE
     */
    private Date enableStartDate;

    /**
     * 命令执行终止终止时间 db_column: ENABLE_END_DATE
     */
    private Date enableEndDate;
}