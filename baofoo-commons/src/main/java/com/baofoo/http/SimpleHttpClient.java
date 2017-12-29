package com.baofoo.http;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

/**
 * @author yuqih
 */
public class SimpleHttpClient {

    /**
     * 默认请求超时（单位：毫秒）
     */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    /**
     * 默认读取超时（单位：毫秒）
     */
    private static final int DEFAULT_SO_TIMEOUT = 60000;
    private HttpClient httpclient;
    /**
     * 请求超时
     */
    private int connectionTimeout;
    /**
     * 读取超时
     */
    private int soTimeout;

    /**
     * 按默认请求超时，读取超时设置
     */
    public SimpleHttpClient() {
        this.connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        this.soTimeout = DEFAULT_SO_TIMEOUT;

        this.httpclient = new DefaultHttpClient();

        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.SO_TIMEOUT, soTimeout);
    }

    /**
     * @param connectionTimeout 请求超时
     * @param soTimeout         读取超时
     */
    public SimpleHttpClient(int connectionTimeout, int soTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;

        this.httpclient = new DefaultHttpClient();

        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.SO_TIMEOUT, soTimeout);
    }

    /**
     * @return the httpclient
     */
    public HttpClient getHttpclient() {
        return httpclient;
    }

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
    }

    /**
     * @return the soTimeout
     */
    public int getSoTimeout() {
        return soTimeout;
    }

    /**
     * @param soTimeout the soTimeout to set
     */
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        this.httpclient.getParams().setParameter(
                CoreConnectionPNames.SO_TIMEOUT, soTimeout);
    }

}
