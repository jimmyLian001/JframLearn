package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 跨境汇款结果查询请求参数
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class RemitOrderApiQueryDto implements Serializable {

    private static final long serialVersionUID = 1999768911203322177L;

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long memberId;

    /**
     * 汇款批次号
     */
    @NotNull(message = "汇款批次号不能为空")
    private String batchNo;
}
