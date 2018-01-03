package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 安全设置service
 *
 * @author: 康志光 Date:2017/11/ ProjectName: account-client Version: 1.0
 */
public interface SecurityConfigService {

    /**
     * 修改登录密码
     *
     * @param oldPwd    旧密码
     * @param firstPwd  一次密码
     * @param secondPwd 二次密码
     * @param userNo    用户号
     * @return boolean 处理标识
     */
    boolean modifyLoginPwd(Long userNo, String oldPwd, String firstPwd, String secondPwd, String operator);

    /**
     * 验证密码是否填写正确
     *
     * @param userNo
     * @param pwd
     * @return
     */
    boolean verifyLoginPwd(Long userNo, String pwd);

    /**
     * 重置登录密码
     *
     * @param firstPwd  一次密码
     * @param secondPwd 二次密码
     * @param userNo    用户号
     * @return boolean 处理标识
     */
    public boolean resetLoginPwd(Long userNo, String firstPwd, String secondPwd, String operator);


    /**
     * 验证验证码
     *
     * @param loginNo             用户号
     * @param sendCodeContentEnum 消息类型
     * @param code                验证码
     * @return 处理结果
     */
    boolean verifyCode(String loginNo, SendCodeContentEnum sendCodeContentEnum, String code);


    /**
     * 根据用户号和密保问题号查询密保问题
     *
     * @param userNo 用户号
     * @return List<UserAnswerRespDTO>  密保问题集合
     */
    List<UserAnswerRespDTO> getUserAnswer(Long userNo);

    /**
     * 根据用户号和密保问题号验证密保问题是否正确
     *
     * @param userNo     用户号
     * @param questionNo 密保问题编号
     * @param answer     答案
     * @return boolean 处理标识
     */
    boolean verifyUserAnswer(Long userNo, Long questionNo, String answer);

    /**
     * 修改安全保密问题答案
     *
     * @param userAnswerReqDtos 答案
     * @return 处理标识
     */
    boolean modifyUserAnswer(List<UserAnswerReqDTO> userAnswerReqDtos);

    /**
     * 重置支付密码
     *
     * @param userNo 用户号
     * @param newPwd 密码
     * @return 处理标识
     */
    boolean resetPaymentPwd(Long userNo, String newPwd, String operator);

    /**
     * 判断支付密码是否设置
     *
     * @param userNo 用户号
     * @return boolean 设置结果
     */
    boolean payPwdExist(Long userNo);

    /**
     * 获取用户信息
     *
     * @param loginNo 登录号
     * @return 用户信息
     */
    UserInfoBo getUserInfo(String loginNo);

    /**
     * 发送验证码
     *
     * @param loginNo  登录号
     * @param busiType 短信类型
     * @return 处理标识
     */
    SendCodeRespDto autoSendCode(String loginNo, String busiType);

    /**
     * 设置支付密码
     *
     * @param userNo 登录号
     * @return
     */
    Result<Boolean> createPayPwd(Long userNo, String firstPayPwd, String secondPayPwd, String loginNo);

}
