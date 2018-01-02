package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.HttpDict;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * http操作工具类
 * <p>
 * 1.
 * </p>
 *
 * @author wukong
 * @version 1.0.0
 * @date 2017/11/4
 */
@Component
public final class HttpUtil {
    /**
     * 请求超时时间设置
     */
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(NumberDict.SIXTY * NumberDict.MILLISECOND)
            .setConnectTimeout(NumberDict.SIXTY * NumberDict.MILLISECOND)
            .setConnectionRequestTimeout(NumberDict.SIXTY * NumberDict.MILLISECOND)
            .build();
    /**
     * http连接池
     */
    @Autowired
    private HttpConnectionManager httpConnectionManager;

    private HttpUtil() {

    }

    /**
     * post请求
     *
     * @param url     url地址
     * @param param   内容
     * @param charset 字符集
     * @return String
     */
    public String post(String url, Map<String, String> param, Charset charset) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        List<NameValuePair> pairList = mapConvertPair(param);
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, charset));
        return getResult(httpPost, charset);
    }

    /**
     * post请求
     *
     * @param url       url地址
     * @param param     请求参数
     * @param headParam 头部参数
     * @param charset   字符集
     * @return String
     */
    public String post(String url, Map<String, String> param, Map<String, String> headParam, Charset charset) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHead(headParam, httpPost);
        List<NameValuePair> pairList = mapConvertPair(param);
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, charset));
        return getResult(httpPost, charset);
    }

    /**
     * post请求
     *
     * @param url       url地址
     * @param content   内容
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String post(String url, String content, Charset charset, Map<String, String> headParam) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHead(headParam, httpPost);
        httpPost.setEntity(new StringEntity(content, charset));
        return getResult(httpPost, charset);
    }

    /**
     * post请求
     *
     * @param url     url地址
     * @param content 内容
     * @param format  格式
     * @param charset 字符集
     * @return String
     */
    public String post(String url, String content, String format, Charset charset) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new StringEntity(content, charset));
        httpPost.setHeader(HttpDict.HEAD_CONTENT, format);
        return getResult(httpPost, charset);
    }

    /**
     * get请求
     *
     * @param url       url地址
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String get(String url, Charset charset, Map<String, String> headParam) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        setHead(headParam, httpGet);
        httpGet.setURI(new URI(url));
        return getResult(httpGet, charset);
    }

    /**
     * get请求
     *
     * @param url       url地址
     * @param param     内容
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String get(String url, Map<String, String> param, Charset charset, Map<String, String> headParam) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        setHead(headParam, httpGet);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpGet.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpGet, charset);
    }

    /**
     * get请求
     *
     * @param url     url地址
     * @param param   内容
     * @param charset 字符集
     * @return String
     */
    public String get(String url, Map<String, String> param, Charset charset) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpGet.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpGet, charset);
    }

    /**
     * delete请求
     *
     * @param url       url地址
     * @param param     内容
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String delete(String url, Map<String, String> param, Charset charset, Map<String, String> headParam) throws URISyntaxException, IOException {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        setHead(headParam, httpDelete);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpDelete.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpDelete, charset);
    }

    /**
     * delete请求
     *
     * @param url     url地址
     * @param param   内容
     * @param charset 字符集
     * @return String
     */
    public String delete(String url, Map<String, String> param, Charset charset) throws URISyntaxException, IOException {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpDelete.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpDelete, charset);
    }

    /**
     * put请求
     *
     * @param url       url地址
     * @param param     内容
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String put(String url, Map<String, String> param, Charset charset, Map<String, String> headParam) throws URISyntaxException, IOException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        setHead(headParam, httpPut);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpPut.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpPut, charset);
    }

    /**
     * put请求
     *
     * @param url     url地址
     * @param param   内容
     * @param charset 字符集
     * @return String
     */
    public String put(String url, Map<String, String> param, Charset charset) throws URISyntaxException, IOException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        List<NameValuePair> pairList = mapConvertPair(param);
        String str = URLEncodedUtils.format(pairList, charset);
        httpPut.setURI(new URI(url.concat("?").concat(str)));
        return getResult(httpPut, charset);
    }

    /**
     * post请求
     *
     * @param url       url地址
     * @param content   内容
     * @param format    格式
     * @param charset   字符集
     * @param headParam 头部参数
     * @return String
     */
    public String put(String url, String content, String format, Charset charset, Map<String, String> headParam) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        setHead(headParam, httpPut);
        httpPut.setEntity(new StringEntity(content, charset));
        httpPut.setHeader(HttpDict.HEAD_CONTENT, format);
        return getResult(httpPut, charset);
    }

    /**
     * post请求
     *
     * @param url     url地址
     * @param content 内容
     * @param format  格式
     * @param charset 字符集
     * @return String
     */
    public String put(String url, String content, String format, Charset charset) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        httpPut.setEntity(new StringEntity(content, charset));
        httpPut.setHeader(HttpDict.HEAD_CONTENT, format);
        return getResult(httpPut, charset);
    }

    /**
     * 设置请求头
     *
     * @param headParam 头部
     * @param request   HttpRequestBase
     */
    private void setHead(Map<String, String> headParam, HttpRequestBase request) {
        for (Map.Entry<String, String> item : headParam.entrySet()) {
            request.addHeader(item.getKey(), headParam.get(item.getValue()));
        }
    }

    /**
     * map转换pair
     *
     * @param param Map<String, String>
     * @return List<NameValuePair>
     */
    private List<NameValuePair> mapConvertPair(Map<String, String> param) {
        List<NameValuePair> pairList = Lists.newArrayList();
        for (Map.Entry<String, String> item : param.entrySet()) {
            pairList.add(new BasicNameValuePair(item.getKey(), item.getValue()));
        }
        return pairList;
    }

    /**
     * 获取响应
     *
     * @param request HttpRequestBase
     * @param charset String
     * @return String
     */
    private String getResult(HttpRequestBase request, Charset charset) throws IOException {
        CloseableHttpClient httpClient = httpConnectionManager.getHttpClient();
        String returnStr;
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            returnStr = EntityUtils.toString(response.getEntity(), charset);
        }
        return returnStr;
    }

}
