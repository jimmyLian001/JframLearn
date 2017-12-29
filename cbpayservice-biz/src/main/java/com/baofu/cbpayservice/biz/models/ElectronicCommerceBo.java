package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建电商文件查询结果
 *
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class ElectronicCommerceBo {

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 交易时间
     */
    private String transTime;

    /**
     * 收款人证件类型：1-身份证 2-组织机构代码
     */
    private String payeeIdType;

    /**
     * 持卡人姓名
     */
    private String idHolder;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 预留手机号
     */
    private String mobile;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品数量
     */
    private String productNum;

    /**
     * 商品价格
     */
    private String productPrice;

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
