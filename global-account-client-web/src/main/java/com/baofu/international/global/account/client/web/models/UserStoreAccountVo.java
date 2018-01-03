package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 1、用户店铺收款账户信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserStoreAccountVo implements Serializable {

    /**
     * 店铺编号
     */
    private Long storeNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户帐号
     */
    private String userAccNo;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺所在平台
     */
    private String storePlatform;

    /**
     * 店铺链接
     */
    private String storeUrl;

    /**
     * 店铺状态 0-冻结 1-正常 2-失效
     */
    private int storeState;

    /**
     * 银行路由编号，提供给商户使用的路由编号
     */
    private String routingNumber;

    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;

    /**
     * 账户状态：0-冻结，1-正常，2-失效
     */
    private int status;

    /**
     * 渠道编号
     */
    private Long channelId;
}
