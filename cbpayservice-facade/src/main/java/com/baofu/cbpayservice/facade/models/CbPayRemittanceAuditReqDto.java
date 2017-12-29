package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 跨境人民币汇款审核请求参数信息
 * User: wanght Date:2016/11/11 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceAuditReqDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 汇款批次号
     */
    @NotBlank(message = "汇款批次号不能为空")
    private String batchNo;

    /**
     * 审核人员
     */
    @NotBlank(message = "审核人员不能为空")
    private String auditBy;

    /**
     * 审核状态：INIT:初始，TRUE:审核通过，FALSE:审核失败
     */
    @Pattern(regexp = "(INIT)|(AUDITING)|(TRUE)|(FALSE)", message = "审核状态类型异常")
    @NotBlank(message = "审核状态不能为空")
    private String auditStatus;
}
