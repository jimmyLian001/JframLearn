package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class FiCbPayFileUploadDo extends BaseDo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 记录数
     */
    private Integer recordCount;

    /**
     * 成功数量
     */
    private Integer successCount;

    /**
     * 失败数量
     */
    private Integer failCount;

    /**
     * 状态，INIT:初始，TRUE:处理完成，FALSE：处理失败
     */
    private String status;

    /**
     * 前置状态
     */
    private String oldStatus;

    /**
     * DFS文件服务器ID编号
     */
    private Long dfsFileId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 订单类型:0:服务贸易类型、1：货物贸易类型
     */
    private String orderType;

    /**
     * 0:创建、1：审核通过 2：审核不通过
     */
    private String auditStatus;

    /**
     * 错误明细文件DFS编号
     */
    private Long errorFileId;

    /**
     * 结算总金额
     */
    private BigDecimal totalAmount;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 行业类型
     */
    private String careerType;

    /**
     * 反洗钱成功金额
     */
    private BigDecimal amlAmount;

    /**
     * 反洗钱币种
     */
    private String amlCcy;

    /**
     * 反洗钱计算时汇率
     */
    private BigDecimal amlRate;
}