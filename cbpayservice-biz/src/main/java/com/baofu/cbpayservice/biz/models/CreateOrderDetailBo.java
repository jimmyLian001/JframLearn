package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 批量汇款-异步创建汇款明细文件
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CreateOrderDetailBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 行业类型
     */
    private String careerType;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 日志Id
     */
    private String traceLogId;

}
