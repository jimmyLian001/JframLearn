package com.baofu.international.global.account.client.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestDict {

    /**
     * 用戶提现返回记录信息
     */
    public static final String USER_WITHDRAW_STORE_LIST = "userWithdrawStoreList";

    /**
     * 用户店铺收款账户信息
     */
    public static final String USER_STORE_ACCOUNT = "userStoreAccount";

    /**
     * 用户币种
     */
    public static final String USER_CCY = "ccy";

    /**
     * 登陆账户号
     */
    public static final String LOGIN_NO = "loginNo";

    /**
     * 页面信息
     */
    public static final String PAGE_INFO = "pageInfo";

    /**
     * 用户类型
     */
    public static final String USER_TYPE = "userType";

    /**
     * 请求类型
     */
    public static final String REQUEST_TYPE = "requestType";
}
