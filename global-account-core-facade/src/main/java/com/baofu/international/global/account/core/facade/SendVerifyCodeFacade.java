package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.SendCodeReqDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.system.commons.result.Result;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
public interface SendVerifyCodeFacade {

    /**
     * 发送短信验证码
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    Result<Boolean> sendSms(SendCodeReqDto sendCodeReqDto, String logId);


    /**
     * 发送短信
     *
     * @param telNo 手机号
     * @param logId 日志Id
     * @return 结果集
     */
    Result<Boolean> sendSmsByTel(String telNo, String content, String logId);

    /**
     * 发送邮箱验证码
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    Result<Boolean> sendEmail(SendCodeReqDto sendCodeReqDto, String logId);

    /**
     * 模糊发送(手机或者邮箱)
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return
     */
    Result<Boolean> sendCode(SendCodeReqDto sendCodeReqDto, String logId);

    /**
     * 校验验证码
     *
     * @param key   redisKey
     * @param code  验证码
     * @param logId 日志Id
     * @return 结果集
     */
    Result<Boolean> checkCode(String key, String code, String logId);

    /**
     * 根据登录用户自主选择发送邮箱还是手机信息
     *
     * @param sendCodeReqDto 请求参数
     * @param logId          日志Id
     * @return 结果集
     */
    Result<SendCodeRespDto> autoSendCode(SendCodeReqDto sendCodeReqDto, String logId);
}
