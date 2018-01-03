package com.baofu.international.global.account.client.web.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luoping on 2017/11/30 0030.
 */
public final class StringFormatUtil {
    private StringFormatUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 页面信息脱敏
     *
     * @param info
     * @return
     */
    public static String secrueqaEncrypt(String info) {
        String model = "";
        if (!StringUtils.isBlank(info)) {
            String start = info.substring(0, 3);
            String end = info.substring(info.length() - 4, info.length());
            for (int i = 0; i < info.length() - 7; i++) {
                model = model.concat("*");
            }
            return start.concat(model).concat(end);
        }
        return model;
    }
}
