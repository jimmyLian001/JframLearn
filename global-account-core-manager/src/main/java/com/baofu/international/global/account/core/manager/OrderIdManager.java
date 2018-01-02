package com.baofu.international.global.account.core.manager;

/**
 * 宝付订单号创建
 * <p>
 * </p>
 * User: 香克斯 Date:2017/3/23 ProjectName: cbpayservice Version: 1.0
 */
public interface OrderIdManager {

    /**
     * 创建宝付订单号
     *
     * @return 返回宝付订单号
     */
    Long orderIdCreate();
}
