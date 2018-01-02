package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;
import com.baofu.international.global.account.core.facade.model.FixTelInfoQueryDto;
import com.baofu.international.global.account.core.facade.model.FixTelMessageCodeApplyDto;
import com.baofu.international.global.account.core.facade.model.UserRegisterTelInfoDto;
import com.system.commons.result.Result;

/**
 * 收款账户用户手机号绑定维护接口
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
public interface TaccountPhoneNoFacade {

    /**
     * 用户申请修改注册手机号码受理接口
     *
     * @param fixPhoneNoApplyDto 申请修改信息
     * @param traceLogId         日志id
     * @return 受理结果
     */
    Result<Boolean> fixRegisterPhoneNo(FixPhoneNoApplyDto fixPhoneNoApplyDto, String traceLogId);

    /**
     * 用户个人注册信息查询
     *
     * @param fixTelInfoQueryDto 个人账户信息
     * @param traceLogId         日志id
     * @return UserRegisterTelInfoDto
     */
    Result<UserRegisterTelInfoDto> getFixRegisterTelInfo(FixTelInfoQueryDto fixTelInfoQueryDto, String traceLogId);

    /**
     * 获取短信验证码
     *
     * @param fixTelMessageCodeApplyDto 验证码信息
     * @param traceLogId                日志id
     * @return UserRegisterTelInfoVo
     */
    Result<Boolean> sendTelMessageCode(FixTelMessageCodeApplyDto fixTelMessageCodeApplyDto, String traceLogId);

}
