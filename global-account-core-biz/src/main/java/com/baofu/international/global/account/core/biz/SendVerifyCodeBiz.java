package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.SendCodeReqBo;
import com.baofu.international.global.account.core.biz.models.SendCodeRespBo;

/**
 * description:发送验证码Biz
 * <p/>
 * Created by liy on 2017/11/6  ProjectName：account-core
 */
public interface SendVerifyCodeBiz {

    /**
     * 发送短信验证码
     *
     * @param bo 请求参数
     * @return 结果集
     */
    void sendSms(SendCodeReqBo bo);

    /**
     * 发送邮箱验证码
     *
     * @param bo 请求参数
     * @return 结果集
     */
    void sendEmail(SendCodeReqBo bo);

    /**
     * 模糊发送(手机或者邮箱)
     *
     * @param bo 请求参数
     * @return 结果集
     */
    void sendCode(SendCodeReqBo bo);

    /**
     * 校验验证码
     *
     * @param key  redisKey
     * @param code 验证码
     * @return 结果集
     */
    void checkCode(String key, String code);

    /**
     * 自主选择发送(手机或者邮箱)
     *
     * @param bo 请求参数
     * @return 结果集
     */
    SendCodeRespBo autoSend(SendCodeReqBo bo);
}
