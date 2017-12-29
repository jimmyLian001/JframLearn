package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 跨境汇款结果查询请求参数
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitOrderApiQueryBo {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 汇款批次号
     */
    private String batchNo;
}
