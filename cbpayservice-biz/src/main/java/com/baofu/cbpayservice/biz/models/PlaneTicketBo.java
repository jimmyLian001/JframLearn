package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 机票参数
 * <p>
 * User: 不良人 Date:2016/12/14 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class PlaneTicketBo {

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 起飞时间
     */
    private String departureTime;

    /**
     * 起飞地目的地
     */
    private String departureLocationDestination;
}
