package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境订单酒店信息表
 * User: feng_jiang Date:2017/07/07
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayOrderHotelDo extends BaseDo {
    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 酒店所在国家
     */
    private String hotelCountryCode;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 入住日期
     */
    private Date checkInDate;

    /**
     * 间夜数
     */
    private BigDecimal nightCount;

}