package com.baofu.international.global.account.client.web.util;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.web.models.SessionVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session对象
 *
 * @author: 不良人 Date:2017/11/6 ProjectName: account-client Version: 1.0
 */
public final class SessionUtil {

    private SessionUtil() {
    }

    /**
     * 获取session中值
     *
     * @return session对象
     */
    public static SessionVo getSessionVo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (SessionVo) request.getSession().getAttribute(CommonDict.SESSION_KEY);
    }

    /**
     * session设值
     *
     * @param key key
     * @param obj 数据对象
     * @return session对象
     */
    public static void setSessionValue(String key, Object obj) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(key, obj);
    }

    /**
     * 获取session值
     *
     * @return session对象
     */
    public static <T extends Object> T getSessionValue(String key) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return (T) session.getAttribute(key);
    }

    /**
     * 删除session值
     *
     * @return session对象
     */
    public static void removeSessionValue(String key) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute(key);
    }

}
