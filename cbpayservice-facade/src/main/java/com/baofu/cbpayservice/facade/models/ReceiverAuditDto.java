package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 外汇汇入申请审核
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class ReceiverAuditDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 收汇单号
     */
    @NotNull(message = "收汇单号不能为空")
    private Long orderId;

    /**
     * 审核状态:20-初始状态，21-清算初审通过、22-清算复审通过（入账成功） 、23-清算初审不通过 、 24-清算复审不通过
     */
    @NotNull(message = "审核状态不能为空")
    private Integer cmAuditState;
}
