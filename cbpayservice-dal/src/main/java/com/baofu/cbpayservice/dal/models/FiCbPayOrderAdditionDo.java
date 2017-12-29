package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单附加信息
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbPayOrderAdditionDo extends BaseDo {

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 通知方式：SERVER-服务端通知/PAGE-页面通知/SERVER_PAGE-服务端和页面通知
     */
    private String notifyType;

    /**
     * 服务端通知地址
     */
    private String serverNotifyUrl;

    /**
     * 页面通知地址
     */
    private String pageNotifyUrl;

    /**
     * 附加信息
     */
    private String additionInfo;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 持卡人姓名
     */
    private String idHolder;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 预留手机号
     */
    private String mobile;

    /**
     * 收款人证件类型：1-身份证
     */
    private Integer payeeIdType;

    /**
     * 是否银行账户
     */
    private String isBankAccount;
}