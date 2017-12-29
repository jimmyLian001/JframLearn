package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 结汇文件上传参数(API)
 * <p>
 * User: 康志光 Date:2017/7/17 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleFileUploadApiDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 商户名称
     */
    @NotNull(message = "商户名称不能为空")
    private String memberName;


    /**
     * 文件系统DFS上传的ID
     */
    @NotNull(message = "文件DFS_FILE_ID不能为空")
    private Long dfsFileId;

    /**
     * 文件类型 0-汇款文件 1-结汇文件
     */
    @NotBlank(message = "文件类型不能为空")
    private String fileType;

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    /**
     * 汇款流水号
     */
    @NotNull(message = "汇款流水号不能为空")
    private String incomeNo;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String createBy;
}
