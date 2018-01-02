package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.manager.BaseIDManager;
import com.baofu.international.global.account.core.manager.enums.IDType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 生成用户号
 *
 * @author : daoxuan
 * @date : 2017/11/29
 */
@Component
@Slf4j
public class UserNoManagerImplBase extends BaseIDManager {

    /**
     * 用户编号luckyNO
     */
    private static final String LUCKY_NO = "8";
    /**
     * 用户编号头三位
     */
    private static final String USER_NO_HEADER = "518";

    /**
     * redis最大值，当达到最大值时清空redis key
     */
    private static final int REDIS_MAX_VALUE = 9999999;
    /**
     * redis中生成key
     */
    private static final String REDIS_KEY = "GLOBAL-ACC:USER_NO_KEY";

    /**
     * 生成long型用户编号
     *
     * @param userType 用户类型
     * @return 返回用户编号
     */
    public Long generateLongUserNoBy(int userType) {

        return Long.valueOf(generateStringUserNoBy(userType));

    }

    /**
     * 生成String型用户编号
     *
     * @param userType 用户类型
     * @return 返回用户编号
     */
    public String generateStringUserNoBy(int userType) {
        if (IDType.PERSONAL_USER.getCode() == userType) {
            return generateStringPersonalUserNo();
        }
        if (IDType.ORG_USER.getCode() == userType) {
            return generateStringOrgUserNo();
        }
        return null;
    }

    /**
     * 生成个人用户编号
     *
     * @return 返回用户编号
     */
    private String generateStringPersonalUserNo() {

        return super.generate19By(USER_NO_HEADER, IDType.PERSONAL_USER, REDIS_KEY, REDIS_MAX_VALUE, LUCKY_NO);

    }

    /**
     * 生成企业用户编号
     *
     * @return 返回用户编号
     */
    private String generateStringOrgUserNo() {

        return super.generate19By(USER_NO_HEADER, IDType.ORG_USER, REDIS_KEY, REDIS_MAX_VALUE, LUCKY_NO);

    }

}
