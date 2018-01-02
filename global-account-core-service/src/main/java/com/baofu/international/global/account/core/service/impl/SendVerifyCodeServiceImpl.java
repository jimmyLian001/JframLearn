package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.SendVerifyCodeBiz;
import com.baofu.international.global.account.core.biz.external.SmsSendBizImpl;
import com.baofu.international.global.account.core.biz.models.SendCodeRespBo;
import com.baofu.international.global.account.core.facade.SendVerifyCodeFacade;
import com.baofu.international.global.account.core.facade.model.SendCodeReqDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.service.convert.SendCodeConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
@Slf4j
@Service
public class SendVerifyCodeServiceImpl implements SendVerifyCodeFacade {

    /**
     *
     */
    @Autowired
    private SendVerifyCodeBiz sendVerifyCodeBiz;


    /**
     * 短信发送服务
     */
    @Autowired
    private SmsSendBizImpl smsSendBiz;

    /**
     * 发送短信验证码
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    @Override
    public Result<Boolean> sendSms(SendCodeReqDto sendCodeReqDto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<Boolean> result;
        log.info("发送短信验证码:{}", sendCodeReqDto);
        try {
            ParamValidate.validateParams(sendCodeReqDto);
            sendVerifyCodeBiz.sendSms(SendCodeConvert.convert(sendCodeReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("发送短信验证码异常", e);
        }
        return result;
    }

    /**
     * 发送邮箱验证码
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    @Override
    public Result<Boolean> sendEmail(SendCodeReqDto sendCodeReqDto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<Boolean> result;
        log.info("发送邮箱验证码:{}", sendCodeReqDto);
        try {
            ParamValidate.validateParams(sendCodeReqDto);
            sendVerifyCodeBiz.sendEmail(SendCodeConvert.convert(sendCodeReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("发送邮箱验证码异常", e);
        }
        return result;
    }

    /**
     * 模糊发送(手机或者邮箱)
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    @Override
    public Result<Boolean> sendCode(SendCodeReqDto sendCodeReqDto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<Boolean> result;
        log.info("模糊发送(手机或者邮箱)验证码:{}", sendCodeReqDto);
        try {
            ParamValidate.validateParams(sendCodeReqDto);
            sendVerifyCodeBiz.sendCode(SendCodeConvert.convert(sendCodeReqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("模糊发送(手机或者邮箱)证码异常", e);
        }
        return result;
    }

    /**
     * 校验验证码
     *
     * @param key   redisKey
     * @param code  验证码
     * @param logId 日志Id
     * @return
     */
    @Override
    public Result<Boolean> checkCode(String key, String code, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<Boolean> result;
        log.info("校验验证码,redisKey:{},验证码{}", key, code);
        try {
            sendVerifyCodeBiz.checkCode(key, code);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("校验验证码异常", e);
        }
        return result;
    }


    /**
     * 根据登录用户自主选择发送邮箱还是手机信息
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    @Override
    public Result<SendCodeRespDto> autoSendCode(SendCodeReqDto sendCodeReqDto, String logId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<SendCodeRespDto> result;
        log.info("自主选择发送(手机或者邮箱)验证码参数:{}", sendCodeReqDto);
        try {
            ParamValidate.validateParams(sendCodeReqDto);
            SendCodeRespBo sendCodeRespBo = sendVerifyCodeBiz.autoSend(SendCodeConvert.convert(sendCodeReqDto));
            result = new Result<>(SendCodeConvert.convert(sendCodeRespBo));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("自主选择发送(手机或者邮箱)验证码失败", e);
        }
        log.info("自主选择发送(手机或者邮箱)验证码响应:{}", result);
        return result;
    }

    /**
     * 使用手机号发送短信
     *
     * @param telNo 手机号
     * @param logId 日志Id
     * @return true 发送成功 false 发送失败
     */
    @Override
    public Result<Boolean> sendSmsByTel(String telNo, String content, String logId) {
        Result<Boolean> result;
        try {
            smsSendBiz.sendSms(telNo, content);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("校验验证码异常", e);
        }
        return result;
    }
}
