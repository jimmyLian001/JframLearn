package com.baofu.international.global.account.core.web.config;

import com.baofu.international.global.account.core.common.util.PropertyInitConfig;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 * 1、项目配置初始监听
 * </p>
 * ProjectName:global-account-core-parent
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/9
 */
@Slf4j
public class PropertyInitListener implements ServletContextListener {

    /**
     * 初始化apollo 配置
     *
     * @param servletContextEvent servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("系统配置加载开始");
        PropertyInitConfig.webInitProperties();
        log.info("系统配置加载成功");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        throw new UnsupportedOperationException();
    }
}
