package com.baofu.international.global.account.client.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * base64工具类
 * <p>
 * User: 蒋文哲 Date: 2017/11/5 Version: 1.0
 * </p>
 */
@Slf4j
public final class Base64Util {
    private Base64Util() {

    }

    /**
     * 加密
     *
     * @param bytes byte[]
     * @return byte[]
     */
    public static byte[] encode(byte[] bytes) {
        byte[] encodeBase64 = null;
        try {
            encodeBase64 = Base64.encodeBase64(bytes);
        } catch (Exception e) {
            log.error("base64加密是错", e);
        }
        return encodeBase64;
    }

    /**
     * 通过Base64加密
     *
     * @param src 需要加密的字符串源
     * @return 加密之后的信息
     */
    public static String encode(String src) {

        try {
            return Base64.encodeBase64String(src.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Base64加密异常：", e);
        }
        return "";
    }

    /**
     * 通过Base64解密
     *
     * @param src 需要加密的字符串
     * @return 返回解密之后信息
     */
    public static String decode(String src) {

        return new String(new Base64().decode(src), StandardCharsets.UTF_8);
    }
}
