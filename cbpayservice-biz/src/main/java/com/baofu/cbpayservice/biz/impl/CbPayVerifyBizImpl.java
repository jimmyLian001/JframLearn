package com.baofu.cbpayservice.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofoo.fi.util.SecurityUtil;
import com.baofoo.http.HttpSendModel;
import com.baofoo.http.SimpleHttpResponse;
import com.baofoo.http.util.HttpUtil;
import com.baofu.cbpayservice.biz.CbPayVerifyBiz;
import com.baofu.cbpayservice.biz.constant.CbPayConstants;
import com.baofu.cbpayservice.biz.convert.CbpayVerifyParamConvert;
import com.baofu.cbpayservice.biz.models.CbPayVerifyReqBo;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.VerifyCodeEnum;
import com.baofu.cbpayservice.common.util.rsa.RsaCodingUtil;
import com.baofu.cbpayservice.manager.FiCbPayVerifyManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 跨境认证Biz层相关操作
 * <p>
 * 1、身份认证
 * </p>
 * User: wanght Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayVerifyBizImpl implements CbPayVerifyBiz {

    /**
     * 数据库操作mapper
     */
    @Autowired
    private FiCbPayVerifyManager fiCbPayVerifyManager;

    /**
     * 后台通知地址
     */
    @Value("${idCard_auth_url}")
    private String idCardAuthUrl;

    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 身份认证
     *
     * @param cbPayVerifyReqBo 参数
     * @return 认证结果
     */
    @Override
    public CbPayVerifyResBo idCardAuth(CbPayVerifyReqBo cbPayVerifyReqBo) {
        log.info("接收请求参数：{}", cbPayVerifyReqBo);
        CbPayVerifyResBo cbPayVerifyResBo = new CbPayVerifyResBo();
        try {
            cbPayVerifyReqBo.setTransId(DateUtil.getCurrent() + cbPayVerifyReqBo.getTransId());
            String data = CbpayVerifyParamConvert.paramConvert(cbPayVerifyReqBo);
            log.info("请求明文：{}", data);

            String securityStr = SecurityUtil.Base64Encode(data);
            log.info("请求密文：{}", securityStr);

            String dataContent = RsaCodingUtil.encryptByPrivateKey(securityStr, CbPayConstants.privateKey);
            String httpParam = idCardAuthUrl + "?" + CbpayVerifyParamConvert.paramConvert(cbPayVerifyReqBo, dataContent);

            log.info("请求参数：{}", httpParam);
            String result = doRequest(httpParam);
            log.info("响应参数：{}", result);

            if (StringUtils.isBlank(result)) {
                setResult(cbPayVerifyResBo, -1, "渠道响应信息为空");
                log.info("响应报文：{}", cbPayVerifyResBo);
                return cbPayVerifyResBo;
            }
            JSONObject resultObject = JSON.parseObject(result);
            if (!resultObject.getBoolean("success")) {
                setResult(cbPayVerifyResBo, -1, resultObject.getString("errorMsg"));
                log.info("响应报文：{}", cbPayVerifyResBo);
                return cbPayVerifyResBo;
            } else {
                String code = resultObject.getJSONObject("data").getString("code");
                String desc = resultObject.getJSONObject("data").getString("desc");
                setResult(cbPayVerifyResBo, VerifyCodeEnum.query(code), desc);
                cbPayVerifyResBo.setFeeFlag(resultObject.getJSONObject("data").getString("fee"));
                cbPayVerifyResBo.setTransNo(resultObject.getJSONObject("data").getString("trade_no"));
                cbPayVerifyResBo.setTransId(cbPayVerifyReqBo.getTransId());
            }


            Long orderId = orderIdManager.orderIdCreate();
            cbPayVerifyResBo.setOrderId(orderId);
            // 插入数据库
            fiCbPayVerifyManager.insert(CbpayVerifyParamConvert.paramConvert(cbPayVerifyReqBo, cbPayVerifyResBo));

            log.info("响应报文：{}", cbPayVerifyResBo);
            return cbPayVerifyResBo;
        } catch (Exception e) {
            log.error("认证异常:", e);
            setResult(cbPayVerifyResBo, -1, "认证异常");
            log.info("响应报文：{}", cbPayVerifyResBo);
            return cbPayVerifyResBo;
        }

    }

    private String doRequest(String urlAndParam) {
        try {
            //准备通知商户前参数准备
            HttpSendModel httpSendModel = new HttpSendModel(urlAndParam);
            //通知商户
            SimpleHttpResponse response = HttpUtil.doRequest(httpSendModel, Constants.UTF8);
            if (!response.isRequestSuccess()) {
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, response.getErrorMessage());
            }
            return response.getEntityString();
        } catch (Exception e) {
            log.error("call 调用身份认证 Exception:{}", e);
            return null;
        }
    }

    private CbPayVerifyResBo setResult(CbPayVerifyResBo cbPayVerifyResBo, int code, String msg) {
        cbPayVerifyResBo.setCode(code);
        cbPayVerifyResBo.setDesc(msg);
        return cbPayVerifyResBo;
    }

    /**
     * 身份认证
     *
     * @param cbPayVerifyReqBo 参数
     * @return 认证结果
     */
    @Override
    public CbPayVerifyResBo idCardAuthOfRisk(CbPayVerifyReqBo cbPayVerifyReqBo) {
        log.info("接收请求参数：{}", cbPayVerifyReqBo);
        CbPayVerifyResBo cbPayVerifyResBo = new CbPayVerifyResBo();
        try {
            cbPayVerifyReqBo.setTransId(DateUtil.getCurrent() + cbPayVerifyReqBo.getTransId());
            String data = CbpayVerifyParamConvert.paramConvert(cbPayVerifyReqBo);
            log.info("请求明文：{}", data);

            String securityStr = SecurityUtil.Base64Encode(data);
            log.info("请求密文：{}", securityStr);

            String data_content = RsaCodingUtil.encryptByPrivateKey(securityStr, CbPayConstants.privateKey);
            String httpParam = idCardAuthUrl + "?" + CbpayVerifyParamConvert.paramConvert(cbPayVerifyReqBo, data_content);

            log.info("请求参数：{}", httpParam);
            String result = doRequest(httpParam);
            log.info("响应参数：{}", result);

            if (StringUtils.isBlank(result)) {
                setResult(cbPayVerifyResBo, -1, "渠道响应信息为空");
                log.info("响应报文：{}", cbPayVerifyResBo);
                return cbPayVerifyResBo;
            }
            JSONObject resultObject = JSON.parseObject(result);
            if (!resultObject.getBoolean("success")) {
                setResult(cbPayVerifyResBo, -1, resultObject.getString("errorMsg"));
                log.info("响应报文：{}", cbPayVerifyResBo);
                return cbPayVerifyResBo;
            } else {
                String code = resultObject.getJSONObject("data").getString("code");
                String desc = resultObject.getJSONObject("data").getString("desc");
                setResult(cbPayVerifyResBo, VerifyCodeEnum.query(code), desc);
                cbPayVerifyResBo.setFeeFlag(resultObject.getJSONObject("data").getString("fee"));
                cbPayVerifyResBo.setTransNo(resultObject.getJSONObject("data").getString("trade_no"));
                cbPayVerifyResBo.setTransId(cbPayVerifyReqBo.getTransId());
            }
            Long orderId = orderIdManager.orderIdCreate();
            cbPayVerifyResBo.setOrderId(orderId);
            log.info("响应报文：{}", cbPayVerifyResBo);
            return cbPayVerifyResBo;
        } catch (Exception e) {
            log.error("认证异常:", e);
            setResult(cbPayVerifyResBo, -1, "认证异常");
            log.info("响应报文：{}", cbPayVerifyResBo);
            return cbPayVerifyResBo;
        }

    }


}
