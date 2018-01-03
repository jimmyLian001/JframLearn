package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.common.assist.RedisManagerImpl;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.service.UserTelReviseService;
import com.baofu.international.global.account.client.service.convert.OrgInfoConvert;
import com.baofu.international.global.account.client.service.convert.PersonInfoConvert;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.TaccountPhoneNoFacade;
import com.baofu.international.global.account.core.facade.UserLoginFacade;
import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.UserSecrueqaInfoFacade;
import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;
import com.baofu.international.global.account.core.facade.model.FixTelInfoQueryDto;
import com.baofu.international.global.account.core.facade.model.FixTelMessageCodeApplyDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.baofu.international.global.account.core.facade.model.UserRegisterTelInfoDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户注册手机号维护业务处理
 * <p>
 *
 * @author : lian zd
 * @date :2017/11/16 ProjectName: account-client Version:1.0
 */
@Service
@Slf4j
public class UserTelReviseServiceImpl implements UserTelReviseService {

    /**
     * 收款账户用户手机号绑定维护
     */
    @Autowired
    private TaccountPhoneNoFacade taccountPhoneNoFacade;

    /**
     * 密保问题服务
     */
    @Autowired
    private UserSecrueqaInfoFacade userSecrueqaInfoFacade;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    /**
     * 企业用户信息服务
     */
    @Autowired
    private UserOrgFacade userOrgFacade;

    /**
     * 个人用户信息服务
     */
    @Autowired
    private UserPersonalFacade userPersonalFacade;

    /**
     * 用户登录信息服务
     */
    @Autowired
    private UserLoginFacade userLoginFacade;

    public static final String SMS_CODE = "#code#";

    /**
     * 修改用户绑定手机请求
     *
     * @param dto 修改用户绑定手机请求参数
     */
    @Override
    public Result<Boolean> fixRegisterPhoneNo(FixPhoneNoApplyDto dto) {
        log.info("修改用户绑定手机请求参数：{}", dto);
        Result<Boolean> result = taccountPhoneNoFacade.fixRegisterPhoneNo(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("修改用户绑定手机返回结果：{}", result);
        return result;
    }

    /**
     * 短信验证码校验
     *
     * @param message 校验内容
     */
    @Override
    public Result<Boolean> checkMessageCode(String message, String messageInput) {
        String messageCode = redisManager.queryObjectByKey(message);
        Result<Boolean> result = new Result<>(Boolean.TRUE);
        if (messageCode == null) {
            log.info("用户短信验证码已过期");
            result = new Result<>(Boolean.FALSE);
            result.setErrorMsg("用户短信验证码已过期,请重新发送");
        }
        if (!messageInput.equals(messageCode)) {
            log.info("验证码不正确，实际验证码：{}，输入验证码：{}", messageCode, messageInput);
            result = new Result<>(Boolean.FALSE);
            result.setErrorMsg("验证码不正确,请重新发送");
        }
        redisManager.deleteObject(message);
        return result;
    }

    /**
     * 发送短信验证码
     *
     * @param userNo      用户号
     * @param serviceType 业务类型
     * @param phoneNo     手机号
     */
    @Override
    public Result<Boolean> sendMessageCode(String userNo, String serviceType, String phoneNo) {
        int messageCode = getValidateCode();
        FixTelMessageCodeApplyDto dto = new FixTelMessageCodeApplyDto();
        String content;
        switch (serviceType) {
            case "updateTel":
                content = SendCodeContentEnum.UPDATE_MOBILE_PHONE_MSG.getMsgContent();
                break;
            case "setTel":
                content = SendCodeContentEnum.BIND_MOBILE_PHONE_MSG.getMsgContent();
                break;
            default:
                content = "你好，您正在使用宝付支付，验证码：#code#,请在十分钟内输入验证码，验证码十分钟内有效！";
                break;
        }
        content = content.replaceFirst(SMS_CODE, String.valueOf(messageCode));
        dto.setContent(content);
        dto.setCurrentPhoneNumber(phoneNo);
        dto.setUserNo(userNo);
        dto.setMessageCode(String.valueOf(messageCode));
        dto.setServiceType(serviceType);
        log.info("发送验证码请求参数：{}", dto);
        return taccountPhoneNoFacade.sendTelMessageCode(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 获取用户绑定手机号个人信息
     *
     * @param userNo 用户号
     * @return UserRegisterTelInfoDto
     */
    @Override
    public Result<UserRegisterTelInfoDto> getUserTelInfo(String userNo) {
        FixTelInfoQueryDto dto = new FixTelInfoQueryDto();
        dto.setUserNo(userNo);
        Result<UserRegisterTelInfoDto> result = taccountPhoneNoFacade.getFixRegisterTelInfo(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("用户个人信息查询结果：{}", result);
        return result;
    }

    /**
     * 获取用戶安全問題
     *
     * @param userNo     用户号
     * @param questionNo 问题序号
     */
    @Override
    public Result<List<UserAnswerRespDTO>> findUserAnswer(Long userNo, Long questionNo) {
        UserAnswerReqDTO userAnswerReqDTO = new UserAnswerReqDTO();
        userAnswerReqDTO.setUserNo(userNo);
        userAnswerReqDTO.setQuestionNo(questionNo);
        Result<List<UserAnswerRespDTO>> result = userSecrueqaInfoFacade.findUserAnswer(userAnswerReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("用户密保问题查询结果：{}", result);
        return result;
    }

    /**
     * 6位随机数
     *
     * @return 结果集
     */
    private int getValidateCode() {

        return (int) ((RandomUtils.nextDouble(0, 1) * NumberDict.NINE + NumberDict.ONE) * NumberDict.ONE_HUNDRED_THOUSAND);
    }


    /**
     * 获取用户信息
     *
     * @param loginNo 用户号
     * @return 用户信息
     */
    @Override
    public UserInfoBo getUserInfo(String loginNo) {
        UserInfoBo userInfoBo = new UserInfoBo();

        try {
            String uuid = MDC.get(MDCPropertyConsts.TRACE_LOG_ID);
            Result<UserLoginRespDTO> result1 = userLoginFacade.findLoginInfo(loginNo, uuid);
            if (!result1.isSuccess() || result1.getResult() == null) {
                return userInfoBo;
            }
            UserLoginRespDTO respDTO = result1.getResult();
            //个人用户
            Long userNo = respDTO.getUserNo();
            Integer userType = respDTO.getUserType();
            userInfoBo.setUserNo(userNo);
            userInfoBo.setLoginNo(loginNo);
            if (1 == userType) {
                Result<UserPersonalDto> result = userPersonalFacade.findByUserNo(userNo, uuid);
                if (result.isSuccess() && result.getResult() != null) {
                    PersonInfoConvert.convertUserInfoBo(userInfoBo, result.getResult());
                }
            }
            //企业用户
            if (2 == userType) {
                Result<OrgInfoRespDto> result = userOrgFacade.findByUserNo(userNo, uuid);
                if (result.isSuccess() && result.getResult() != null) {
                    OrgInfoConvert.convertUserInfoBo(userInfoBo, result.getResult());
                }
            }

            log.info("获取用户信息：{}", userInfoBo);
            return userInfoBo;
        } catch (Exception e) {
            log.error("获取用户信息失败：{}", e);
            return userInfoBo;
        }
    }

    @Override
    public boolean verifyUserAnswer(Long userNo, Long questionNo, String answer) {
        try {
            Result<List<UserAnswerRespDTO>> result = this.findUserAnswer(userNo, questionNo);
            return result.isSuccess() && result.getResult() != null && result.getResult().get(0).getAnswer().equals(answer);
        } catch (Exception e) {
            log.error("验证用户密保答案异常：{}", e);
            return false;
        }
    }

    @Override
    public Boolean getUserLoginInfoByLoginNo(String loginNo) {
        try {
            log.info("查询手机是否已经绑定");
            Result<UserLoginRespDTO> result = userLoginFacade.findLoginInfo(loginNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("查询手机是否已经绑定返回");

            return !result.isSuccess() && result.getResult() == null;
        } catch (Exception e) {
            log.error("查询手机是否已经绑定异常：{}", e);
            return Boolean.FALSE;
        }
    }

}
