package com.baofu.international.global.account.client.web.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@Component
public class ApolloRefreshConfig {

    /**
     * 配置刷新
     */
    @Autowired
    private RefreshScope refreshScope;
    /**
     * 配置名称
     */
    private String name = "configDict";

    /**
     * 配置监听
     *
     * @param changeEvent ConfigChangeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshScope.refresh(name);
    }
}
