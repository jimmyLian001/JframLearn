package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.service.SecurityConfigService;
import com.baofu.international.global.account.client.service.convert.OrgInfoConvert;
import com.baofu.international.global.account.client.service.convert.PersonInfoConvert;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.*;
import com.baofu.international.global.account.core.facade.SendVerifyCodeFacade;
import com.baofu.international.global.account.core.facade.UserLoginFacade;
import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.UserPwdFacade;
import com.baofu.international.global.account.core.facade.UserSecrueqaInfoFacade;
import com.baofu.international.global.account.core.facade.model.SendCodeReqDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.baofu.international.global.account.core.facade.model.UserPwdReqDto;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户安全中心设置service
 *
 * @author: 康志光
 * @date:2017/11/08
 * @version: 1.0.0
 */
@Slf4j
@Service
public class SecurityConfigServiceImpl implements SecurityConfigService {

    /**
     * 用户客户信息服务facade
     */
    @Autowired
    private UserPwdFacade userPwdFacade;

    /**
     * 用户安全问题服务facade
     */
    @Autowired
    private UserSecrueqaInfoFacade userSecrueqaInfoFacade;

    /**
     * 验证码服务facade
     */
    @Autowired
    private SendVerifyCodeFacade sendVerifyCodeFacade;

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

    /**
     * 修改登录密码
     *
     * @param oldPwd    旧密码
     * @param firstPwd  一次密码
     * @param secondPwd 二次密码
     * @param userNo    用户号
     * @return boolean 处理标识
     */
    @Override
    public boolean modifyLoginPwd(Long userNo, String oldPwd, String firstPwd, String secondPwd, String operator) {
        try {
            //基本校验通过后，发起更新密码操作
            UserPwdReqDto loginPwdReqDTO = new UserPwdReqDto();
            loginPwdReqDTO.setUserNo(userNo);
            loginPwdReqDTO.setFirstPwd(firstPwd);
            loginPwdReqDTO.setSecondPwd(secondPwd);
            loginPwdReqDTO.setOldPwd(oldPwd);
            loginPwdReqDTO.setPwdType(1);
            loginPwdReqDTO.setOperator(operator);
            Result<Boolean> result = userPwdFacade.modifyUserPwd(loginPwdReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("修改登录密码异常：{}", e);
            return false;
        }
    }

    /**
     * 验证密码是否填写正确
     *
     * @param userNo
     * @param pwd
     * @return
     */
    @Override
    public boolean verifyLoginPwd(Long userNo, String pwd) {
        try {

            Result<UserPwdRespDto> result = userPwdFacade.findPwdInfo(userNo, 1, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            if (!result.isSuccess() || result.getResult() == null) {
                return false;
            }
            return result.getResult().getPwd().equals(pwd);
        } catch (Exception e) {
            log.error("验证登录密码异常：{}", e);
            return false;
        }
    }

    /**
     * 重置登录密码
     *
     * @param firstPwd  一次密码
     * @param secondPwd 二次密码
     * @param userNo    用户号
     * @return boolean 处理标识
     */
    @Override
    public boolean resetLoginPwd(Long userNo, String firstPwd, String secondPwd, String operator) {
        try {
            //基本校验通过后，发起更新密码操作
            UserPwdReqDto loginPwdReqDTO = new UserPwdReqDto();
            loginPwdReqDTO.setUserNo(userNo);
            loginPwdReqDTO.setFirstPwd(firstPwd);
            loginPwdReqDTO.setSecondPwd(secondPwd);
            loginPwdReqDTO.setPwdType(1);
            loginPwdReqDTO.setOperator(operator);
            Result<Boolean> result = userPwdFacade.resetUserPwd(loginPwdReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("重置登录密码异常：{}", e);
            return false;
        }
    }

    /**
     * 验证验证码
     *
     * @param loginNo 用户号
     * @param code    验证码
     * @return 处理结果
     */
    @Override
    public boolean verifyCode(String loginNo, SendCodeContentEnum sendCodeContentEnum, String code) {
        try {
            Result<Boolean> result = sendVerifyCodeFacade.checkCode(sendCodeContentEnum.getRedisKey().concat(loginNo)
                    , code, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("验证验证码异常：{}", e);
            return false;
        }
    }

    /**
     * 根据用户号和密保问题号查询密保问题
     *
     * @param userNo 用户号
     * @return List<UserAnswerRespDTO>  密保问题集合
     */
    @Override
    public List<UserAnswerRespDTO> getUserAnswer(Long userNo) {
        try {
            UserAnswerReqDTO userAnswerReqDTO = new UserAnswerReqDTO();
            userAnswerReqDTO.setUserNo(userNo);
            Result<List<UserAnswerRespDTO>> result = userSecrueqaInfoFacade.findUserAnswer(userAnswerReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.getResult();
        } catch (Exception e) {
            log.error("获取用户密保答案异常：{}", e);
            return Lists.newArrayList();
        }
    }

    /**
     * 根据用户号和密保问题号验证密保问题是否正确
     *
     * @param userNo     用户号
     * @param questionNo 密保问题编号
     * @param answer     答案
     * @return boolean 处理标识
     */
    @Override
    public boolean verifyUserAnswer(Long userNo, Long questionNo, String answer) {
        try {
            UserAnswerReqDTO userAnswerReqDTO = new UserAnswerReqDTO();
            userAnswerReqDTO.setUserNo(userNo);
            userAnswerReqDTO.setQuestionNo(questionNo);
            Result<List<UserAnswerRespDTO>> result = userSecrueqaInfoFacade.findUserAnswer(userAnswerReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult() != null && result.getResult().get(0).getAnswer().equals(answer);
        } catch (Exception e) {
            log.error("验证用户密保答案异常：{}", e);
            return false;
        }
    }

    /**
     * 修改安全保密问题答案
     *
     * @param userAnswerReqDtos 答案
     * @return 处理标识
     */
    @Override
    public boolean modifyUserAnswer(List<UserAnswerReqDTO> userAnswerReqDtos) {
        try {
            Result<Boolean> result = userSecrueqaInfoFacade.createUserAnswer(userAnswerReqDtos, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("修改安全保密问题答案异常：{}", e);
            return false;
        }
    }

    /**
     * 重置支付密码
     *
     * @param userNo 用户号
     * @param newPwd 密码
     * @return 处理标识
     */
    @Override
    public boolean resetPaymentPwd(Long userNo, String newPwd, String operator) {
        try {
            //基本校验通过后，发起更新密码操作
            UserPwdReqDto loginPwdReqDTO = new UserPwdReqDto();
            loginPwdReqDTO.setUserNo(userNo);
            loginPwdReqDTO.setFirstPwd(newPwd);
            loginPwdReqDTO.setSecondPwd(newPwd);
            loginPwdReqDTO.setPwdType(NumberDict.TWO);
            loginPwdReqDTO.setOperator(operator);
            Result<Boolean> result = userPwdFacade.resetUserPwd(loginPwdReqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("重置支付密码异常：{}", e);
            return false;
        }
    }

    /**
     * 判断支付密码是否设置
     *
     * @param userNo 用户号
     * @return boolean 设置结果
     */
    @Override
    public boolean payPwdExist(Long userNo) {
        try {
            Result<UserPwdRespDto> result = userPwdFacade.findPwdInfo(userNo, 2, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.isSuccess() && result.getResult() != null;
        } catch (Exception e) {
            log.error("获取支付密码异常：{}", e);
            return false;
        }
    }

    @Override
    public SendCodeRespDto autoSendCode(String loginNo, String busiType) {
        try {
            SendCodeContentEnum sendCodeContentEnum = SendCodeContentEnum.getContentMsg(busiType);
            //发送验证码
            SendCodeReqDto sendCodeReqDto = new SendCodeReqDto();
            sendCodeReqDto.setEmailTitle(sendCodeContentEnum.getTitle());
            sendCodeReqDto.setParam(loginNo);
            sendCodeReqDto.setContent(sendCodeContentEnum.getMsgContent());
            sendCodeReqDto.setRedisKey(sendCodeContentEnum.getRedisKey().concat(loginNo));
            sendCodeReqDto.setTimeOut(sendCodeContentEnum.getTimeout());
            Result<SendCodeRespDto> result = sendVerifyCodeFacade.autoSendCode(sendCodeReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            return result.getResult();
        } catch (Exception e) {
            log.error("发送短信验证码异常：{}", e);
            return null;
        }
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

            //个人用户
            UserLoginRespDTO userLoginRespDTO = result1.getResult();
            Long userNo = userLoginRespDTO.getUserNo();
            Integer userType = userLoginRespDTO.getUserType();
            if (UserTypeEnum.PERSONAL.getType() == userType) {
                Result<UserPersonalDto> result = userPersonalFacade.findByUserNo(userNo, uuid);
                if (result.isSuccess() && result.getResult().getUserInfoNo() != null) {
                    userInfoBo = PersonInfoConvert.convertUserInfoBo(userLoginRespDTO, result.getResult());
                }
            }

            //企业用户
            if (UserTypeEnum.ORG.getType() == userType) {
                Result<OrgInfoRespDto> result = userOrgFacade.findByUserNo(userNo, uuid);
                if (result.isSuccess() && result.getResult().getUserInfoNo() != null) {
                    userInfoBo = OrgInfoConvert.convertUserInfoBo(userLoginRespDTO, result.getResult());
                }
            }

            log.info("获取用户信息：{}", userInfoBo);
            return userInfoBo;
        } catch (Exception e) {
            log.error("获取用户信息失败：{}", e);
            return userInfoBo;
        }
    }


    /**
     * 设置支付密码
     *
     * @param userNo      登录号
     * @param firstPayPwd
     * @param firstPayPwd
     * @return
     */
    @Override
    public Result<Boolean> createPayPwd(Long userNo, String firstPayPwd, String secondPwd, String loginNo) {
        Result<Boolean> result;
        try {
            UserPwdReqDto userPwdReqDto = new UserPwdReqDto();
            userPwdReqDto.setUserNo(userNo);
            userPwdReqDto.setFirstPwd(firstPayPwd);
            userPwdReqDto.setSecondPwd(secondPwd);
            userPwdReqDto.setPwdType(NumberDict.TWO);
            userPwdReqDto.setOperator(loginNo);
            log.info("设置支付密码参数：{}", userPwdReqDto);
            result = userPwdFacade.setPayPwd(userPwdReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        } catch (Exception e) {
            log.error("设置支付密码异常：{}", e);
            result = new Result<>(Boolean.FALSE);
        }
        log.info("设置支付密码返回结果：{}", result);
        return result;
    }


}
