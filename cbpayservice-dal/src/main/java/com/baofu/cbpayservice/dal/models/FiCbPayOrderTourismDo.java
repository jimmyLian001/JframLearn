package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境订单旅游信息表
 * User: feng_jiang Date:2017/07/07
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayOrderTourismDo extends BaseDo {
    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 旅行社名称
     */
    private String travelAgencyName;

    /**
     * 旅行国家
     */
    private String travelCountryCode;

    /**
     * 出行日期
     */
    private Date tripDate;

    /**
     * 旅游天数
     */
    private BigDecimal tourDays;

}