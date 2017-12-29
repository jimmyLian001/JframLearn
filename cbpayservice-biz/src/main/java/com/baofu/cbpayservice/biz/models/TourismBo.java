package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 旅游行业
 * <p>
 * User: 不良人 Date:2017/1/11 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class TourismBo {

    /**
     * 旅行社名称
     */
    private String travelAgencyName;

    /**
     * 旅行国家
     */
    private String travelCountry;

    /**
     * 出行日期
     */
    private Date departureDate;

    /**
     * 旅游天数
     */
    private BigDecimal duration;
}
