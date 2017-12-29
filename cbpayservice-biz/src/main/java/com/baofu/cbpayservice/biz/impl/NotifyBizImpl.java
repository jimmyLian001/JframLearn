package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheTerminalDto;
import com.baofoo.ps.http.HttpFormParameter;
import com.baofoo.ps.http.HttpMethod;
import com.baofoo.ps.http.HttpSendModel;
import com.baofoo.ps.http.SimpleHttpResponse;
import com.baofoo.ps.http.util.HttpUtil;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.util.rsa.RsaCodingUtil;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 异步通知服务
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class NotifyBizImpl implements NotifyBiz {

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cacheManager;

    /**
     * 商户申请信息操作类
     */
    @Autowired
    private FiCbPayMemberApiRqstManager fiCbPayMemberApiRqstManager;

    /**
     * 通知商户
     *
     * @param memberId     商户号
     * @param terminalId   终端号
     * @param notifyUrl    通知地址
     * @param t            通知内容
     * @param memberReqId  商户请求流水号
     * @param businessType 业务类型
     */
    @Override
    public <T> void notifyMember(String memberId, String terminalId, String notifyUrl, T t, String memberReqId,
                                 String businessType) {

        try {
            // 准备通知商户前参数准备
            HttpSendModel httpSendModel = new HttpSendModel(notifyUrl);
            List<HttpFormParameter> params = new ArrayList<>();
            //终端校验
            CacheTerminalDto terminal = cacheManager.getTerminal(Integer.parseInt(terminalId));
            String encrypt = RsaCodingUtil.encryptByPrivateKey(JsonUtil.toJSONString(t), terminal.getPrivateKey());
            params.add(new HttpFormParameter("version", Constants.VERSION_1_0_0));
            params.add(new HttpFormParameter("dataType", Constants.DATA_TYPE_JSON));
            params.add(new HttpFormParameter("dataContent", encrypt));
            params.add(new HttpFormParameter("memberId", memberId));
            params.add(new HttpFormParameter("terminalId", terminalId));
            httpSendModel.setMethod(HttpMethod.POST);
            httpSendModel.setParams(params);
            log.info("call 异步通知商户:{},请求地址：{},参数：{}", memberId, notifyUrl, httpSendModel.toString());
            // 通知商户
            SimpleHttpResponse response = HttpUtil.doRequest(httpSendModel, Constants.UTF8);
            log.info("call 异步通知返回结果信息：statusCode:{},entityString:{},errorMessage:{}", response.getStatusCode(),
                    response.getEntityString(), response.getErrorMessage());
            if (HttpStatus.SC_OK != response.getStatusCode()) {
                log.error("call 异步通知返回失败：{}", response.getErrorMessage());
            }
            // 0-待回执、1-已回执、2-已通知
            fiCbPayMemberApiRqstManager.updateApiRqstInfoStatusByReqNo(memberReqId, NumberConstants.TWO, businessType);
        } catch (Exception e) {
            log.error("call 异步通知异常：{}", e);
        }
    }


    /**
     * 通知商户
     *
     * @param memberId   商户号
     * @param terminalId 终端号
     * @param notifyUrl  通知地址
     * @param t          通知内容
     */
    @Override
    public <T> void notifyMember(String memberId, String terminalId, String notifyUrl, T t) {

        try {
            // 准备通知商户前参数准备
            HttpSendModel httpSendModel = new HttpSendModel(notifyUrl);
            List<HttpFormParameter> params = new ArrayList<>();
            //终端校验
            CacheTerminalDto terminal = cacheManager.getTerminal(Integer.parseInt(terminalId));
            String encrypt = RsaCodingUtil.encryptByPrivateKey(JsonUtil.toJSONString(t), terminal.getPrivateKey());
            params.add(new HttpFormParameter("version", Constants.VERSION_1_0_0));
            params.add(new HttpFormParameter("dataType", Constants.DATA_TYPE_JSON));
            params.add(new HttpFormParameter("dataContent", encrypt));
            params.add(new HttpFormParameter("memberId", memberId));
            params.add(new HttpFormParameter("terminalId", terminalId));
            httpSendModel.setMethod(HttpMethod.POST);
            httpSendModel.setParams(params);
            log.info("call 异步通知商户:{},请求地址：{},参数：{}", memberId, notifyUrl, httpSendModel.toString());
            // 通知商户
            SimpleHttpResponse response = HttpUtil.doRequest(httpSendModel, Constants.UTF8);
            log.info("call 异步通知返回结果信息：statusCode:{},entityString:{},errorMessage:{}", response.getStatusCode(),
                    response.getEntityString(), response.getErrorMessage());
            if (HttpStatus.SC_OK != response.getStatusCode()) {
                log.error("call 异步通知返回失败：{}", response.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("call 异步通知异常：{}", e);
        }
    }
}
