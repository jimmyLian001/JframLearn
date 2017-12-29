package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 解付申请Mq参数对象
 * <p>
 * User: 不良人 Date:2017/5/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SolutionPayApplyMqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 收汇订单Id
     */
    private Long settleOrderId;
}
