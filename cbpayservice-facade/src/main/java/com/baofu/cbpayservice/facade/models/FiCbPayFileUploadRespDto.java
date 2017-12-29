package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境汇款汇款明细文件支持批量上传结果查询返回对象
 * <p/>
 * User: lian zd Date:2017/10/25 ProjectName: cbpayservice Version:1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayFileUploadRespDto implements Serializable {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件校验处理进度
     */
    private String processingSchedule;

    /**
     * 状态，INIT:初始，TRUE:处理完成，FALSE：处理失败,PROCESSING：处理中,ERROR:文件数据有误
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
     * 订单类型:0:服务贸易类型、1：货物贸易类型
     */
    private String orderType;

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
     * 行业类型
     */
    private String careerType;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy;

}