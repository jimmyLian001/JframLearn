package com.baofu.cbpayservice.biz.impl;

import com.baofoo.Response;
import com.baofoo.cache.service.facade.TerminalQueryFacade;
import com.baofoo.cache.service.facade.model.CacheTerminalDto;
import com.baofoo.http.HttpSendModel;
import com.baofoo.http.SimpleHttpResponse;
import com.baofoo.http.util.HttpUtil;
import com.baofu.cbpayservice.biz.CbPayOrderNotifyBiz;
import com.baofu.cbpayservice.biz.convert.ParamConvert;
import com.baofu.cbpayservice.biz.models.BaseResultBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.util.rsa.RsaCodingUtil;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.OrderAdditionManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 人民币订单通知处理
 * <p>
 * 1、根据宝付订单号通知商户
 * </p>
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderNotifyBizImpl implements CbPayOrderNotifyBiz {

    /**
     * 跨境人民币订单信息Manager
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * Ma终端缓存
     */
    @Autowired
    private TerminalQueryFacade terminalQueryFacade;

    /**
     * 订单附加信息Manager服务
     */
    @Autowired
    private OrderAdditionManager orderAdditionManager;

    /**
     * 根据宝付订单号通知商户
     *
     * @param orderId    宝付订单号
     * @param operatorBy 操作用户
     */
    @Override
    public Boolean notifyMember(Long orderId, String operatorBy) {

        //获取订单信息
        FiCbPayOrderDo fiCbPayOrderDo = cbPayOrderManager.queryOrder(orderId);

        //查询附加信息
        FiCbPayOrderAdditionDo orderAdditionDo = orderAdditionManager.queryOrderAddition(orderId);
        Integer terminalNo = Integer.parseInt(fiCbPayOrderDo.getTerminalId() + "");
        //获取终端信息
        Response<CacheTerminalDto> response = terminalQueryFacade.getTerminal(terminalNo);
        if (!response.isSuccess()) {
            log.error("根据终端编号获取终端缓存信息失败 terminalNo:{},RESULT:{}", terminalNo, response);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097, response.getErrorMsg());
        }
        if (response.getResult() == null) {
            log.error("根据终端编号获取终端缓存信息为空 terminalNo:{}", terminalNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097, "终端缓存信息为空");
        }
        CacheTerminalDto terminal = response.getResult();
        //拼接通知参数对象报文
        Map<String, String> paramMap = ParamConvert.paramConvert(fiCbPayOrderDo, orderAdditionDo);
        log.info("宝付订单号：{},通知商户明文信息:{}", orderId, paramMap);
        String json = RsaCodingUtil.encryptByPrivateKey(JsonUtil.toJSONString(paramMap), terminal.getPrivateKey());
        if (StringUtils.isBlank(json)) {
            log.error("终端：{},参数:{},加密异常", terminalNo, paramMap);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0052);
        }
        //组装通知商户参数信息
        BaseResultBo baseResultBo = BaseResultBo.setSuccessResult(json, fiCbPayOrderDo.getMemberId(),
                fiCbPayOrderDo.getTerminalId());
        String httpParam = ParamConvert.paramConvert(baseResultBo);
        //通知商户
        Boolean notifyFlag = doRequest(orderAdditionDo.getServerNotifyUrl() + "?" + httpParam,
                terminal.isOkNoticeFlag());
        //判断是否成功，成功更新通知商户状态
        if (!notifyFlag) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0096);
        }
        modifyNotifyStatus(orderId, operatorBy);
        return Boolean.TRUE;
    }

    /**
     * 通知商户
     *
     * @param urlAndParam 请求Url和参数信息
     * @return 返回是否通知成功
     */
    private Boolean doRequest(String urlAndParam, Boolean okNoticeFlag) {
        try {
            log.info("通知商户 请求信息：{}", urlAndParam);
            //准备通知商户前参数准备
            HttpSendModel httpSendModel = new HttpSendModel(urlAndParam);
            //通知商户
            SimpleHttpResponse response = HttpUtil.doRequest(httpSendModel, Constants.UTF8);
            log.info("通知商户返回结果信息：statusCode:{},entityString:{},errorMessage:{}", response.getStatusCode(),
                    response.getEntityString(), response.getErrorMessage());
            if (!response.isRequestSuccess()) {
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, response.getErrorMessage());
            }
            String notifyResult = StringUtils.trim(response.getEntityString());
            if (!Constants.NOTIFY_RESULT.equalsIgnoreCase(notifyResult) && okNoticeFlag) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0095);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("call 通知商户 Exception:{}", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 更新通知商户状态
     *
     * @param orderId 宝付订单号
     */
    private void modifyNotifyStatus(Long orderId, String operatorBy) {

        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setNotifyStatus(FlagEnum.TRUE.getCode());
        fiCbPayOrderDo.setOrderCompleteTime(new Date());
        if (StringUtils.isNotBlank(operatorBy)) {
            fiCbPayOrderDo.setUpdateBy(operatorBy);
        }
        cbPayOrderManager.modifyCbPayOrder(fiCbPayOrderDo);
    }

    /**
     * 更新宝付订单报关状态   add by wdj
     *
     * @param orderId    宝付订单号
     * @param adminState 总署报关状态
     */
    @Override
    public void modifyStateByOrderId(Long orderId, String adminState) {
        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setAdminState(adminState);
        fiCbPayOrderDo.setUpdateBy("SYSTEM");
        cbPayOrderManager.modifyCbPayOrder(fiCbPayOrderDo);
    }

}
