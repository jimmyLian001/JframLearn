package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User: wanght Date:2017/3/21 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayBatchFileUpLoadBo {

    /**
     * 文件批次集合
     */
    private List<Long> batchFileIdList;

    /**
     * 状态
     */
    private String status;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 反洗钱币种
     */
    private String amlCcy;
}
