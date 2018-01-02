package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.AgentRegisterBo;
import com.baofu.international.global.account.core.biz.models.CreateUserBo;
import com.baofu.international.global.account.core.biz.models.SysSecrueqaInfoBo;

import java.util.List;

/**
 * description:注册 biz
 * <p/>
 * Created by liy on 2017/11/5 0005 ProjectName：account-core
 */
public interface RegisterBiz {

    /**
     * 查询系统安全问题
     *
     * @return 结果集
     */
    List<SysSecrueqaInfoBo> selectSysSecrueqaInfoList();

    /**
     * 个人用户注册-发送短信验证码
     *
     * @param mobilePhone 手机号
     */
    void sendSmsCaptcha(String mobilePhone);

    /**
     * 个人用户注册-校验信验证码
     *
     * @param mobilePhone 手机号
     * @param captcha     验证码
     */
    void checkSmsCaptcha(String mobilePhone, String captcha);

    /**
     * 企业用户注册-发送邮件验证码
     *
     * @param email 邮箱
     */
    void sendEmailCaptcha(String email);

    /**
     * 企业用户注册-校验邮件验证码
     *
     * @param email   邮箱
     * @param captcha 验证码
     */
    void checkEmailCaptcha(String email, String captcha);

    /**
     * 创建用户
     *
     * @param bo 请求参数
     * @return 结果集
     */
    Long createUser(CreateUserBo bo);

    /**
     * 发送信息异常时，删除RedisKey
     *
     * @param key key
     */
    void delRedisKey(String key);

    /**
     * 通知账户系统用户注册有代理商
     *
     * @param registerBo
     */
    void sendMqMessage(AgentRegisterBo registerBo);
}
