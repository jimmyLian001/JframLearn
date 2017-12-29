package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

/**
 * API汇款文件上传异步通知参数
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitFileNotfiyBo {

    /**
     * 商户号
     */
    private String memberId;

    /**
     * 终端号
     */
    private String terminalId;

    /**
     * 请求流水号
     */
    private String memberReqId;

    /**
     * 文件批次编号
     */
    private Long fileBatchNo;

    /**
     * 总笔数
     */
    private Integer recordCount;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 成功笔数
     */
    private Integer successCount;

    /**
     * 失败笔数
     */
    private Integer failCount;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 错误文件名称
     */
    private String errorFileName;

}
