package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p/>
 * <p>
 * 1、
 * </p>
 * User: 白玉京 Date:2017/8/8 0008 ProjectName: cbpay-service
 */
@Getter
@Setter
@ToString
public class CbpayGoodsInfoItemDto implements Serializable {

    /**
     * 商品信息
     */
    private List<OrderItemBo> orderItemList;

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
    private String deliveryDate;
}
