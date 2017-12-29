package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 代理报关参数对象 (QueueName:CBPAY_PROXY_CUSTOMS_QUEUE_NAME)
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsDto implements Serializable {

    private static final long serialVersionUID = -1731085519107780226L;
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
     * 文件类型:0-代理结算汇款文件 、 1-结汇文件、2-API订单汇款文件、3-批量汇款文件
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
