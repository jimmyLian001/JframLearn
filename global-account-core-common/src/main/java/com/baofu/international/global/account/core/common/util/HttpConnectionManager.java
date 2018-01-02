package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.NumberDict;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * http连接池
 * <p>
 * User: 蒋文哲 Date: 2017/8/16 Version: 1.0
 * </p>
 *
 * @author : 蒋文哲
 */
@Slf4j
@Component
public class HttpConnectionManager {
    /**
     * 连接池
     */
    private PoolingHttpClientConnectionManager cm = null;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            log.error("中枢-工具-http连接池初始-异常：", e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(NumberDict.ONE_HUNDRED);
        cm.setDefaultMaxPerRoute(NumberDict.TWENTY);
    }

    /**
     * @return CloseableHttpClient
     */
    public CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }
}
