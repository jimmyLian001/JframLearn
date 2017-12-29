package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建机票文件查询结果
 *
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class AirTicketsBo {

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
    private String orderMoney;

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
    private String takeoffTime;


}
