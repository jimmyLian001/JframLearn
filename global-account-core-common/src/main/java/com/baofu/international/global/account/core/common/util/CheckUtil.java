package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.CommonDict;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description:校验工具类
 * <p/>
 * Created by liy on 2017/11/6 ProjectName：account-core
 */
@Slf4j
public final class CheckUtil {

    private CheckUtil() {
    }

    /**
     * 手机号格式校验
     *
     * @param mobiles 手机号
     * @return 结果集
     */
    public static boolean isPhone(String mobiles) {
        if (StringUtils.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(CommonDict.REGEX_PHONE);
        Matcher m = p.matcher(mobiles);
        log.info("手机号校验：{},{}", mobiles, m.matches());
        return m.matches();
    }

    /**
     * 邮箱格式校验
     *
     * @param email 邮箱
     * @return 结果集
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        Pattern p = Pattern.compile(CommonDict.REGEX_EMAIL);
        Matcher m = p.matcher(email);
        log.info("邮箱校验：{},{}", email, m.matches());
        return m.matches();
    }
}
