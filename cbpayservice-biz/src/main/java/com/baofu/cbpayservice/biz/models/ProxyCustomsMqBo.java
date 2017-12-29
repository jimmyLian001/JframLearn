package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 代理跨境结算mq消息内容对象
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsMqBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件系统DFS上传的ID
     */
    private Long dfsFileId;

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 来源：1-单个汇款功能、2-API接口、3-批量汇款功能
     */
    private Integer sourceFlag;

}
