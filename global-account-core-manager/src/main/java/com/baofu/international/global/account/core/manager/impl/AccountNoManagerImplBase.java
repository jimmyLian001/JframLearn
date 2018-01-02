package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.manager.BaseIDManager;
import com.baofu.international.global.account.core.manager.enums.IDType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * <p>
 * 1、
 * </p>
 *
 * @author : daoxuan
 * @date : 2017/11/29
 */
@Component
@Slf4j
public class AccountNoManagerImplBase extends BaseIDManager {

    /**
     * 用户编号luckyNO
     */
    private static final String LUCKY_NO = "6";
    /**
     * 用户编号头三位
     */
    private static final String USER_NO_HEADER = "618";

    /**
     * redis最大值，当达到最大值时清空redis key
     */
    private static final int REDIS_MAX_VALUE = 9999999;
    /**
     * redis中生成key
     */
    private static final String REDIS_KEY = "GLOBAL-ACC:ACCOUNT_NO_KEY";

    /**
     * 生成个人用户编号
     *
     * @return 返回用户编号
     */
    public String generateStringAccountNo() {

        return super.generate19By(USER_NO_HEADER, IDType.ACCOUNT, REDIS_KEY, REDIS_MAX_VALUE, LUCKY_NO);

    }

    /**
     * 生成个人用户编号
     *
     * @return 返回用户编号
     */
    public Long generateLongAccountNo() {

        return Long.valueOf(generateStringAccountNo());

    }
}
