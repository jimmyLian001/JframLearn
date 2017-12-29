package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 提现文件上传参数对象
 * <p>
 * User: 不良人 Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class OrderRemittanceDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 文件系统DFS上传的ID
     */
    @NotNull(message = "文件DFS_FILE_ID不能为空")
    private Long dfsFileId;

    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空")
    private String fileType;

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String createBy;
}
