package com.baofu.international.global.account.client.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP地址
 * <p>
 * 1.获取IP地址
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/12/4
 */
public class IpUtil {

    private static final String UNKNOWN ="unknown";

    private IpUtil() {
    }

    /**
     * 获取登录用户IP地址
     *
     * @param request 请求对象
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
