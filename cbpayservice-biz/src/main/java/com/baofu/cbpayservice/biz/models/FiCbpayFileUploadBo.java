package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 代理报关上传文件类型
 * <p>
 * User: 不良人 Date:2017/1/18 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class FiCbpayFileUploadBo {

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
     * 操作人
     */
    private String createBy;

    /**
     * 订单类型:0:服务贸易类型、1：货物贸易类型
     */
    private String orderType;

    /**
     * 审核状态: 0:初始、1：审核通过 2：审核不通过
     */
    private String auditStatus;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 结算总金额
     */
    private BigDecimal totalAmout;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 行业类型
     */
    private String careerType;

}
