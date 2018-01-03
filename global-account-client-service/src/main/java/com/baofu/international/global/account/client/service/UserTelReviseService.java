package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.baofu.international.global.account.core.facade.model.UserRegisterTelInfoDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 用户注册手机号维护业务处理
 * <p>
 * User: lian zd Date:2017/11/16 ProjectName: account-client Version:1.0
 */
public interface UserTelReviseService {
    /**
     * 修改用户绑定手机请求
     *
     * @param dto 修改用户绑定手机请求参数
     */
    Result<Boolean> fixRegisterPhoneNo(FixPhoneNoApplyDto dto);

    /**
     * 短信验证码校验
     *
     * @param message      校验内容
     * @param messageInput 用户输入验证码
     */
    Result<Boolean> checkMessageCode(String message, String messageInput);

    /**
     * 发送短信验证码
     *
     * @param userNo      用户号
     * @param serviceType 业务类型
     * @param phoneNo     手机号
     */
    Result<Boolean> sendMessageCode(String userNo, String serviceType, String phoneNo);

    /**
     * 获取用户绑定手机号个人信息
     *
     * @param userNo 用户号
     * @return UserRegisterTelInfoDto
     */
    Result<UserRegisterTelInfoDto> getUserTelInfo(String userNo);

    /**
     * 获取用戶安全問題
     *
     * @param userNo     用户号
     * @param questionNo 问题序号
     */
    Result<List<UserAnswerRespDTO>> findUserAnswer(Long userNo, Long questionNo);

    /**
     * 获取用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    UserInfoBo getUserInfo(String loginNo);


    /**
     * 根据用户号和密保问题号验证密保问题是否正确
     *
     * @param userNo     用户号
     * @param questionNo 密保问题编号
     * @param answer     答案
     * @return boolean 处理标识
     */
    boolean verifyUserAnswer(Long userNo, Long questionNo, String answer);

    Boolean getUserLoginInfoByLoginNo(String loginInfo);
}

