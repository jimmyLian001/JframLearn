package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商户汇入申请运营确认
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class OperateConfirmDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 申请单号
     */
    @NotNull(message = "申请单号不能为空")
    private Long applyNo;

    /**
     * 汇入汇款编号（银行到账通知填写）
     */
//    @NotBlank(message = "汇入汇款编号不能为空")
    private String incomeNo;

    /**
     * 匹配状态:10-未匹配，11-匹配成功，12-待设置商户编号, 13-失效
     */
    @NotNull(message = "匹配状态不能为空")
    private Integer matchingStatus;

    /**
     * 匹配之前的状态
     */
    @NotNull(message = "匹配之前的状态不能为空")
    private Integer beforeMatchingStatus;

    /**
     * 备注
     */
    private String remarks;
}
