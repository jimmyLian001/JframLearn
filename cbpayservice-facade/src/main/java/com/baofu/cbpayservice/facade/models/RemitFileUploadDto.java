package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 汇款上传文件参数
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitFileUploadDto implements Serializable {

    private static final long serialVersionUID = 8515363156894570710L;
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Pattern(regexp = Constants.NUMBER_REG, message = "商户编号填写异常")
    private String memberId;

    /**
     * 终端编号
     */
    @NotBlank(message = "终端编号不能为空")
    @Pattern(regexp = Constants.NUMBER_REG, message = "终端编号填写异常")
    private String terminalId;

    /**
     * 请求流水号
     */
    @NotBlank(message = "请求流水号不能为空")
    @Length(max = 64, message = "请求流水号不能超过64位")
    private String memberReqId;

    /**
     * 订单明细文件名称
     */
    @NotBlank(message = "请求流水号不能为空")
    private String orderFileName;

    /**
     * 结果通知地址
     */
    @NotBlank(message = "结果通知地址不能为空")
    @Length(max = 256, message = "商户通知接口地址不能超过256位")
    private String notifyUrl;

    /**
     * 文件系统DFS上传的ID
     */
    @NotNull(message = "文件DFS_FILE_ID不能为空")
    private Long dfsFileId;
}
