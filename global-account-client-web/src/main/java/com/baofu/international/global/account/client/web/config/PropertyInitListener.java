package com.baofu.international.global.account.client.web.config;


import com.baofu.international.global.account.client.common.util.CommonUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化Apollo启动参数
 * <p>
 * User: 蒋文哲 Date: 2017/10/24 Version: 1.0
 * </p>
 */
public class PropertyInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CommonUtil.webInitProperties();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        throw new UnsupportedOperationException();
    }
}
