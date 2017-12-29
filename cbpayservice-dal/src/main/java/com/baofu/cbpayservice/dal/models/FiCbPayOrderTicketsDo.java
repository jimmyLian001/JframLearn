package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class FiCbPayOrderTicketsDo extends BaseDo {

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 起飞地目的地
     */
    private String address;

    /**
     * 起飞时间
     */
    private Date takeoffTime;

}