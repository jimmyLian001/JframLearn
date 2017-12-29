package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * mq消息内容对象
 * <p>
 * User: wanght Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPaySumFileMqBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 操作人
     */
    private String createBy;
}
