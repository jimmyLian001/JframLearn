package com.baofu.cbpayservice.common.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * 邮件工具类
 * <p>
 * </p>
 * User: wanght Date:2017/9/20 ProjectName: cbpayservice Version: 1.0
 */
public final class EmailUtils {

    private EmailUtils() {

    }

    /**
     * 邮件地址转换
     *
     * @param emailAddress 邮件地址
     * @param regex        分隔符
     * @return 邮件地址集合
     */
    public static List<String> emailAddressConvert(String emailAddress, String regex) {
        List<String> emailAddressList = Lists.newArrayList();
        if (StringUtil.isBlank(emailAddress)) {
            return emailAddressList;
        }
        String[] toAddress = emailAddress.split(regex);
        emailAddressList.addAll(Arrays.asList(toAddress));
        return emailAddressList;
    }

}
