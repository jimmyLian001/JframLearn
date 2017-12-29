package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 运营设置商户编号
 * <p>
 * User: 不良人 Date:2017/4/16 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleOperationSetDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 申请编号
     */
    @NotNull(message = "申请编号不能为空")
    private Long applyNo;

    /**
     * 银行到账通知入库单号（宝付生成的OrderId）
     */
    @NotNull(message = "银行到账通知入库单号")
    private Long orderId;
}
