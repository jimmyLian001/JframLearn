package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 运营设置商户编号
 * <p>
 * User: 不良人 Date:2017/4/16 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleOperationSetReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 申请编号
     */
    private Long applyNo;

    /**
     * 银行到账通知入库单号（宝付生成的OrderId）
     */
    private Long orderId;
}
