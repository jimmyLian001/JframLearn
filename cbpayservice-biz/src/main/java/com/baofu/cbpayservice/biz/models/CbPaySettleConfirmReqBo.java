package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 结汇确认请求对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/4/14 ProjectName: cbpay-customs-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySettleConfirmReqBo {

    /**
     * 宝付内部订单号，数据生成时产生
     */
    private Long orderId;

    /**
     * 反洗钱部分失败结汇明细ID
     */
    private List<Long> detailOrderIds;

}
