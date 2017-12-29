package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class FiCbPayBatchFileUploadDo extends BaseDo {

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
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 反洗钱币种
     */
    private String amlCcy;

    /**
     * 反洗钱计算时汇率
     */
    private BigDecimal amlRate;
}