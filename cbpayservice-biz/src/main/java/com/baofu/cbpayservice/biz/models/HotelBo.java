package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨境支付订单参数
 * <p>
 * User: 不良人 Date:2017/1/11 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class HotelBo {

    /**
     * 酒店所在国家
     */
    private String liveCountry;

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
    private BigDecimal roomNights;
}
