package com.baofoo.http.util;

import com.baofoo.exception.ServiceException;
import com.baofoo.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * http请求相关工具类
 *
 * @author yuqih
 */
public class HttpUtil {

    /**
     * @param httpSendModel
     * @param getCharSet
     * @return
     */
    public static SimpleHttpResponse doRequest(HttpSendModel httpSendModel,
                                               String getCharSet) {

        // 创建默认的httpClient客户端端
        SimpleHttpClient simpleHttpclient = new SimpleHttpClient();

        try {
            return doRequest(simpleHttpclient, httpSendModel, getCharSet);
        } finally {
            simpleHttpclient.getHttpclient().getConnectionManager().shutdown();
        }

    }

    /**
     * @param httpclient
     * @param httpSendModel
     * @param getCharSet
     * @return
     */
    public static SimpleHttpResponse doRequest(
            SimpleHttpClient simpleHttpclient, HttpSendModel httpSendModel,
            String getCharSet) {

        HttpRequestBase httpRequest = buildHttpRequest(httpSendModel);

        try {
            HttpResponse response = simpleHttpclient.getHttpclient().execute(
                    httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            if (isRequestSuccess(statusCode)) {
                return new SimpleHttpResponse(statusCode, EntityUtils.toString(
                        response.getEntity(), getCharSet), null);
            } else {
                return new SimpleHttpResponse(statusCode, null, response
                        .getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            throw new ServiceException("http请求异常", e);
        }

    }

    /**
     * @param httpSendModel
     * @return
     */
    protected static HttpRequestBase buildHttpRequest(
            HttpSendModel httpSendModel) {
        HttpRequestBase httpRequest;
        if (httpSendModel.getMethod() == null) {
            throw new ServiceException("请求方式未设定");
        } else if (httpSendModel.getMethod() == HttpMethod.POST) {

            String url = httpSendModel.getUrl();
            String sendCharSet = httpSendModel.getCharSet();
            List<HttpFormParameter> params = httpSendModel.getParams();

            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            if (params != null && params.size() != 0) {

                for (HttpFormParameter param : params) {
                    qparams.add(new BasicNameValuePair(param.getName(), param
                            .getValue()));
                }

            }

            HttpPost httppost = new HttpPost(url);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(qparams,
                        sendCharSet));
            } catch (UnsupportedEncodingException e) {
                throw new ServiceException("构建post请求参数失败", e);
            }

            httpRequest = httppost;
        } else if (httpSendModel.getMethod() == HttpMethod.GET) {
            HttpGet httpget = new HttpGet(httpSendModel.buildGetRequestUrl());

            httpRequest = httpget;
        } else {
            throw new ServiceException("请求方式不支持：" + httpSendModel.getMethod());
        }

        return httpRequest;
    }

    /**
     * 请求是否成功
     *
     * @param statusCode
     * @return
     */
    public static boolean isRequestSuccess(int statusCode) {
        return statusCode == 200;
    }

}
