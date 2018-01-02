package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawFileUploadDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 文件批次
     */
    private Long fileBatchNo;

    /**
     * 提现批次号
     */
    private String batchNo;

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
     * 状态，INIT:初始，TRUE:处理完成，FALSE：审核不通过，UPLOAD：已上传,PROCESSING：处理中,ERROR:文件数据有误,CANCEL:取消中
     */
    private String status;

    /**
     * DFS文件服务器ID编号
     */
    private Long dfsFileId;

    /**
     * 文件类型:0-用户提现明细文件
     */
    private String fileType;

    /**
     * 0:服务贸易类型、1：货物贸易类型
     */
    private String orderType;

    /**
     * 错误明细文件DFS编号
     */
    private Long errorDfsFileId;

    /**
     * 订单明细总金额
     */
    private BigDecimal totalAmt;
}