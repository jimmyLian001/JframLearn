package com.baofoo.http.util;

import com.baofoo.exception.ServiceException;
import com.baofoo.http.HttpSendModel;
import com.baofoo.http.SimpleHttpClient;
import com.baofoo.http.SimpleHttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class HttpsUtil {

    /**
     * @param keyStore    java密钥
     * @param password    密钥对应密码
     * @param trustStore  受信任的java密钥
     * @param uri         请求uri
     * @param sendCharSet 发送请求编码
     * @param getCharSet  接收请求编码
     * @param params      参数
     * @return
     * @throws Exception
     */

    @SuppressWarnings("deprecation")
    public static SimpleHttpResponse doRequest(KeyStore keyStore,
                                               String password, KeyStore trustStore, HttpSendModel httpSendModel,
                                               String getCharSet) {
        SimpleHttpClient simpleHttpclient = new SimpleHttpClient();
        HttpClient httpclient = simpleHttpclient.getHttpclient();

        SSLSocketFactory socketFactory;
        try {
            socketFactory = new SSLSocketFactory(keyStore, password, trustStore);
        } catch (Exception e) {
            throw new ServiceException("https请求注册密钥失败", e);
        }
        // 不校验域名
        socketFactory
                .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        int port;
        try {
            port = new URI(httpSendModel.getUrl()).getPort();
            if (port == -1) {
                port = 443;
            }
        } catch (URISyntaxException ue) {
            throw new ServiceException("https请求转化Uri并获取短裤失败", ue);
        }

        Scheme sch = new Scheme("https", port, socketFactory);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);

        try {
            return HttpUtil.doRequest(simpleHttpclient, httpSendModel,
                    getCharSet);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * 通过密钥文件路径及密码取得对应的KeyStore
     *
     * @param filePath
     * @param password
     * @return
     */
    public static KeyStore getKeyStore(String filePath, String password) {
        return getKeyStore(filePath, password, KeyStore.getDefaultType());
    }

    /**
     * 通过密钥文件路径及密码取得对应的KeyStore
     *
     * @param filePath
     * @param password
     * @param type     密钥类型
     * @return
     */
    public static KeyStore getKeyStore(String filePath, String password,
                                       String type) {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(type);
        } catch (KeyStoreException ke) {
            throw new ServiceException("读取Key失败", ke);
        }

        FileInputStream instream = null;
        try {
            instream = new FileInputStream(new File(filePath));
            keyStore.load(instream, password.toCharArray());
        } catch (Exception e) {
            throw new ServiceException("读取Key失败", e);
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception e) {
                }
            }
        }

        return keyStore;
    }

}
