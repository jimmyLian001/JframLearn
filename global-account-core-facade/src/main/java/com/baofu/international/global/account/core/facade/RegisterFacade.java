package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.AgentRegisterReqDto;
import com.baofu.international.global.account.core.facade.model.CreateUserReqDto;
import com.baofu.international.global.account.core.facade.model.RegisterUserReqDto;
import com.baofu.international.global.account.core.facade.model.SysSecrueqaInfoRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * description:注册接口
 * <p/>
 * Created by liy on 2017/11/5 ProjectName：account-core
 */
public interface RegisterFacade {

    /**
     * 查询系统安全问题
     *
     * @param logId 日志ID
     * @return 结果集
     */
    Result<List<SysSecrueqaInfoRespDto>> selectSysSecrueqaInfoList(String logId);

    /**
     * 个人用户注册-发送短信验证码
     *
     * @param mobilePhone 手机号
     * @param logId       日志ID
     * @return 结果集
     */
    Result<Boolean> sendSmsCaptcha(String mobilePhone, String logId);

    /**
     * 个人用户注册-校验短信验证码
     *
     * @param dto   请求参数
     * @param logId 日志ID
     * @return 结果集
     */
    Result<Boolean> checkSmsCaptcha(RegisterUserReqDto dto, String logId);

    /**
     * 个人用户注册-创建用户
     *
     * @param dto   用户信息
     * @param logId 日志ID
     * @return 结果集
     */
    Result<Long> createPersonalUser(CreateUserReqDto dto, String logId);

    /**
     * 企业用户注册-发送邮件验证码
     *
     * @param email 邮箱
     * @param logId 日志ID
     * @return 结果集
     */
    Result<Boolean> sendEmailCaptcha(String email, String logId);

    /**
     * 企业用户注册-校验邮件验证码
     *
     * @param dto   请求参数
     * @param logId 日志ID
     * @return 结果集
     */
    Result<Boolean> checkEmailCaptcha(RegisterUserReqDto dto, String logId);

    /**
     * 企业用户注册-创建用户
     *
     * @param dto   用户信息
     * @param logId 日志ID
     * @return 结果集
     */
    Result<Long> createOrgUser(CreateUserReqDto dto, String logId);

    /**
     * 请求发送Mq通知账户系统开户有代理商户
     *
     * @param reqDto 请求参数
     * @param logId  日志Id
     */
    void sendMqMessageToAgent(AgentRegisterReqDto reqDto, String logId);
}
