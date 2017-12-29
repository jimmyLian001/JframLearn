package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class FiCbpayOrderLogisticsDo extends BaseDo {

    /**
     * 跨境订单号
     */
    private Long orderId;

    /**
     * 快递公司编号（物流公司编号）
     */
    private String logisticsCompanyNumber;

    /**
     * 物流单号（运单号）
     */
    private String logisticsNumber;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人联系方式
     */
    private String consigneeContact;

    /**
     * 收货人地址
     */
    private String consigneeAddress;

    /**
     * 发货日期
     */
    private Date deliveryDate;

}