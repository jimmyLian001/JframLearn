package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 跨境人民币汇款状态更新请求参数信息
 * User: wanght Date:2016/11/11 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemtStatusChangeReqDto implements Serializable {

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
     * 更新人员
     */
    @NotBlank(message = "更新人员不能为空")
    private String updateBy;

    /**
     * 审核状态：
     * INIT:初始，
     * SUCESS：银行处理成功 、
     * SUCESS_FIRST_TRUE:成功：清算初审通过 、
     * SUCESS_SECOND_TRUE:成功：清算复审通过（汇款成功） 、
     * FAIL：银行处理失败 、
     * FAIL_FIRST_TRUE 失败：清算初审通过
     * FAIL_SECOND_TRUE:失败：清算复审通过 （汇款失败）
     */
    @NotBlank(message = "审核状态类不能为空")
    private String auditStatus;

    /**
     * 审核动作的之前状态
     */
    @NotBlank(message = "审核动作的之前状态不能为空")
    private String beforeAuditStatus;

    /**
     * 渠道状态：INIT:初始，TRUE:返回成功，PROCESSING：银行处理中 FALSE：返回失败
     */
    private String channelStatus;

    /**
     * 备注
     */
    private String remarks;
}
