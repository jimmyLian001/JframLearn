package com.baofu.cbpayservice.common.util.rsa;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * URLDecoder 解密工具类封装
 * User: 香克斯 Date:2016/9/19 ProjectName: asias-parent Version: 1.0
 */
public class URLDecoderUtils {

    /**
     * 解密
     *
     * @param str 原始参数
     * @param enc 编码
     * @return 返回结果
     * @throws UnsupportedEncodingException 抛出异常
     */
    public static String decode(String str, String enc) throws UnsupportedEncodingException {

        if (StringUtils.isBlank(str)) {
            return "";
        }
        return URLDecoder.decode(str, enc);
    }


    /**
     * 解密
     *
     * @param str 原始参数
     * @return 返回结果
     * @throws UnsupportedEncodingException 抛出异常
     */
    public static String decode(String str) throws UnsupportedEncodingException {

        return decode(str, "UTF-8");
    }
}
