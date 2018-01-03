package com.baofu.international.global.account.client.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Session 中key
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionKeyDict {

    /**
     * 重置登录密码Session 用戶登录号
     */
    public static final String RESET_LOGIN_NO_KEY = "RESET_LOGIN_NO_KEY";

    /**
     * 重置登录密码Session 用户登录类型
     */
    public static final String RESET_LOGIN_TYPE_KEY = "RESET_LOGIN_TYPE_KEY";

    /**
     * Session中存放的用户类型key
     */
    public static final String USER_TYPE = "userType";
}
