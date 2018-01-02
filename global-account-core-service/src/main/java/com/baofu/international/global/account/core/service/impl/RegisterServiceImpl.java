package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.RegisterBiz;
import com.baofu.international.global.account.core.biz.models.CreateUserBo;
import com.baofu.international.global.account.core.biz.models.SysSecrueqaInfoBo;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.facade.RegisterFacade;
import com.baofu.international.global.account.core.facade.model.AgentRegisterReqDto;
import com.baofu.international.global.account.core.facade.model.CreateUserReqDto;
import com.baofu.international.global.account.core.facade.model.RegisterUserReqDto;
import com.baofu.international.global.account.core.facade.model.SysSecrueqaInfoRespDto;
import com.baofu.international.global.account.core.service.convert.RegisterConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:注册
 * <p/>
 *
 * @author : liy on 2017/11/5
 * @version : 1.0.0
 */
@Slf4j
@Service
public class RegisterServiceImpl implements RegisterFacade {

    /**
     * 注册Biz
     */
    @Autowired
    private RegisterBiz registerBiz;

    /**
     * 查询系统安全问题
     *
     * @param logId 日志ID
     * @return 结果集
     */
    @Override
    public Result<List<SysSecrueqaInfoRespDto>> selectSysSecrueqaInfoList(String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<List<SysSecrueqaInfoRespDto>> result = new Result<>();
        try {
            List<SysSecrueqaInfoBo> resultList = registerBiz.selectSysSecrueqaInfoList();
            result.setResult(RegisterConvert.sysSecrueqaInfoBoConvert(resultList));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("查询系统安全问题,异常", e);
        }
        log.info("查询系统安全问题,结果:{},{}条", result.isSuccess(), result.getResult().size());
        return result;
    }

    /**
     * 个人用户注册-发送短信验证码
     *
     * @param mobilePhone 手机号
     * @param logId       日志ID
     * @return 结果集
     */
    @Override
    public Result<Boolean> sendSmsCaptcha(String mobilePhone, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("个人用户注册,发送短信验证码,手机号:{}", mobilePhone);
        Result<Boolean> result;
        try {
            registerBiz.sendSmsCaptcha(mobilePhone);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            registerBiz.delRedisKey(CommonDict.CHECK_REGISTER_KEY.concat(mobilePhone));
            result = ExceptionUtils.getResponse(e);
            log.error("个人用户注册,发送短信验证码,异常", e);
        }
        log.info("个人用户注册,发送短信验证码,结果:{}", result);
        return result;
    }

    /**
     * 个人用户注册-校验信验证码
     *
     * @param dto   请求参数
     * @param logId 日志ID
     * @return 结果集
     */
    @Override
    public Result<Boolean> checkSmsCaptcha(RegisterUserReqDto dto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("个人用户注册,校验短信验证码,参数:{}", dto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(dto);
            RegisterConvert.toCheckPwd(dto);
            registerBiz.checkSmsCaptcha(dto.getLoginNo(), dto.getCaptcha());
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("个人用户注册,校验短信验证码,异常", e);
        }
        log.info("个人用户注册,校验短信验证码,结果:{}", result);
        return result;
    }

    /**
     * 个人用户注册-创建用户
     *
     * @param dto   用户信息
     * @param logId 日志ID
     * @return
     */
    @Override
    public Result<Long> createPersonalUser(CreateUserReqDto dto, String logId) {


        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("个人用户注册,创建用户,参数:{}", dto);
        Result<Long> result = new Result<>();
        try {
            ParamValidate.validateParams(dto);
            CreateUserBo bo = RegisterConvert.toPersonal(dto);
            result.setResult(registerBiz.createUser(bo));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("个人用户注册,创建用户,异常", e);
        }
        log.info("个人用户注册,创建用户,结果:{}", result);
        return result;
    }

    /**
     * 企业用户注册-发送邮件验证码
     *
     * @param email 邮箱
     * @param logId 日志ID
     * @return 结果集
     */
    @Override
    public Result<Boolean> sendEmailCaptcha(String email, String logId) {


        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("企业用户注册,发送邮件验证码,邮箱:{}", email);
        Result<Boolean> result;
        try {
            registerBiz.sendEmailCaptcha(email);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            registerBiz.delRedisKey(CommonDict.CHECK_REGISTER_KEY.concat(email));
            result = ExceptionUtils.getResponse(e);
            log.error("企业用户注册,发送邮件验证码,异常", e);
        }
        log.info("企业用户注册,发送邮件验证码,结果:{}", result);
        return result;
    }

    /**
     * 企业用户注册-校验邮件验证码
     *
     * @param dto   请求参数
     * @param logId 日志ID
     * @return
     */
    @Override
    public Result<Boolean> checkEmailCaptcha(RegisterUserReqDto dto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("企业用户注册,校验邮件验证码,参数:{}", dto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(dto);
            RegisterConvert.toCheckPwd(dto);
            registerBiz.checkEmailCaptcha(dto.getLoginNo(), dto.getCaptcha());
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("企业用户注册,校验邮件验证码,异常", e);
        }
        log.info("企业用户注册,校验邮件验证码,结果:{}", result);
        return result;
    }

    /**
     * 企业用户注册-创建用户
     *
     * @param dto   用户信息
     * @param logId 日志ID
     * @return
     */
    @Override
    public Result<Long> createOrgUser(CreateUserReqDto dto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("企业用户注册,创建用户,参数:{}", dto);
        Result<Long> result = new Result<>();
        try {
            ParamValidate.validateParams(dto);
            CreateUserBo bo = RegisterConvert.toOrg(dto);
            result.setResult(registerBiz.createUser(bo));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("企业用户注册,创建用户,异常", e);
        }
        log.info("企业用户注册,创建用户,结果:{}", result);
        return result;
    }

    /**
     * 请求发送Mq通知账户系统开户有代理商户
     *
     * @param reqDto 请求参数
     * @param logId  日志Id
     */
    @Override
    public void sendMqMessageToAgent(AgentRegisterReqDto reqDto, String logId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            registerBiz.sendMqMessage(RegisterConvert.paramConvert(reqDto));
        } catch (Exception e) {
            log.error("发送消息通知账户系统代理商户号异常：", e);
        }
    }
}
