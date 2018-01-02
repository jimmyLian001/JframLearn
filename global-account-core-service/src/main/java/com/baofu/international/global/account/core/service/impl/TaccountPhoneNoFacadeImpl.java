package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.AccountPhoneNoBiz;
import com.baofu.international.global.account.core.biz.models.FixTelInfoQueryBo;
import com.baofu.international.global.account.core.biz.models.UserRegisterTelInfoBo;
import com.baofu.international.global.account.core.facade.TaccountPhoneNoFacade;
import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;
import com.baofu.international.global.account.core.facade.model.FixTelInfoQueryDto;
import com.baofu.international.global.account.core.facade.model.FixTelMessageCodeApplyDto;
import com.baofu.international.global.account.core.facade.model.UserRegisterTelInfoDto;
import com.baofu.international.global.account.core.service.convert.UserRegisterTelInfoConvert;
import com.baofu.international.global.account.core.service.convert.UserTelConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收款账户用户手机号绑定维护接口实现
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
@Slf4j
@Service
public class TaccountPhoneNoFacadeImpl implements TaccountPhoneNoFacade {

    /**
     * 用户注册手机号维护业务处理
     */
    @Autowired
    private AccountPhoneNoBiz accountPhoneNoBiz;

    /**
     * 用户申请修改注册手机号码受理接口
     *
     * @param fixPhoneNoApplyDto 注册手机号修改用户申请信息
     * @param traceLogId         日志信息
     * @return 受理结果
     */
    @Override
    public Result<Boolean> fixRegisterPhoneNo(FixPhoneNoApplyDto fixPhoneNoApplyDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户申请修改注册手机号修改受理开始：{}", fixPhoneNoApplyDto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(fixPhoneNoApplyDto);
            accountPhoneNoBiz.reviseTel(UserTelConvert.toFixPhoneNoApplyBo(fixPhoneNoApplyDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户申请修改注册手机号修改受理异常，异常信息：{}", e);
        }
        log.info("用户申请修改注册手机号修改处理结果：{}", result);
        return result;
    }

    /**
     * 用户个人注册信息查询
     *
     * @param fixTelInfoQueryDto 个人账户信息
     * @param traceLogId         日志id
     * @return UserRegisterTelInfoDto
     */
    @Override
    public Result<UserRegisterTelInfoDto> getFixRegisterTelInfo(FixTelInfoQueryDto fixTelInfoQueryDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户注册信息查询请求参数：{}", fixTelInfoQueryDto);
        Result<UserRegisterTelInfoDto> result;
        try {
            ParamValidate.validateParams(fixTelInfoQueryDto);
            FixTelInfoQueryBo fixTelInfoQueryBo = UserTelConvert.toFixTelInfoQueryBo(fixTelInfoQueryDto);
            UserRegisterTelInfoBo userRegisterTelInfoBo = accountPhoneNoBiz.getUserRegisterTelInfo(fixTelInfoQueryBo);
            UserRegisterTelInfoDto userRegisterTelInfoDto = UserRegisterTelInfoConvert.convert(userRegisterTelInfoBo);
            result = new Result<>(userRegisterTelInfoDto);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户申请修改注册手机号修改受理异常，异常信息：{}", e);
        }
        log.info("用户注册信息查询返回结果：{}", result);
        return result;
    }

    /**
     * 获取短信验证码
     *
     * @param fixTelMessageCodeApplyDto 验证码信息
     * @param traceLogId                日志信息
     * @return 受理结果
     */
    @Override
    public Result<Boolean> sendTelMessageCode(FixTelMessageCodeApplyDto fixTelMessageCodeApplyDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户验证码发送请求信息：{}", fixTelMessageCodeApplyDto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(fixTelMessageCodeApplyDto);
            accountPhoneNoBiz.sendMessageCode(UserTelConvert.toFixTelMessageCodeApplyBo(fixTelMessageCodeApplyDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户验证码发送请求受理异常，异常信息：{}", e);
        }
        log.info("用户验证码发送请求处理结果：{}", result);
        return result;
    }

}
