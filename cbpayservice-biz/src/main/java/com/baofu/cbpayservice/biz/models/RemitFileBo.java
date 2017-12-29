package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 汇款上传文件参数
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitFileBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 文件系统DFS上传的ID
     */
    private Long dfsFileId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 操作人
     */
    private String createBy;
}
