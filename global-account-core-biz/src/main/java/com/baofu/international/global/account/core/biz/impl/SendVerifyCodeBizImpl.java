package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.SendVerifyCodeBiz;
import com.baofu.international.global.account.core.biz.UserPwdBiz;
import com.baofu.international.global.account.core.biz.convert.SendCodeConvert;
import com.baofu.international.global.account.core.biz.external.EmailSendBizImpl;
import com.baofu.international.global.account.core.biz.external.SmsSendBizImpl;
import com.baofu.international.global.account.core.biz.models.EmailReqBo;
import com.baofu.international.global.account.core.biz.models.SendCodeReqBo;
import com.baofu.international.global.account.core.biz.models.SendCodeRespBo;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.common.util.CheckUtil;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.dal.model.UserLoginInfoDo;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.manager.*;
import com.baofu.international.global.account.core.manager.impl.RedisManagerImpl;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * description:
 * <p/>
 *
 * @author :liy
 * @version : 1.0.0
 * @date :2017/11/6
 */
@Slf4j
@Component
public class SendVerifyCodeBizImpl implements SendVerifyCodeBiz {


    /**
     * 手机短信服务
     */
    @Autowired
    private SmsSendBizImpl smsSendBiz;

    /**
     * 发送邮件统一服务
     */
    @Autowired
    private EmailSendBizImpl emailSendBiz;

    /**
     * Redis服务
     */
    @Autowired(required = false)
    private RedisManagerImpl redisManager;

    /**
     * 用户客户服务BIZ
     */
    @Autowired
    private UserPwdBiz userPwdBiz;

    /**
     * 企业用户Manager服务
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 个人用户Manager服务
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 用户登录服务
     */
    @Autowired
    private UserLoginManager userLoginManager;
    /**
     * 用戶信息
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 发送短信验证码
     *
     * @param bo 请求参数
     */
    @Override
    public void sendSms(SendCodeReqBo bo) {

        if (!CheckUtil.isPhone(bo.getParam())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290006);
        }
        int code = getValidateCode();
        String content = bo.getContent().replaceFirst(CommonDict.SMS_CODE, String.valueOf(code));
        smsSendBiz.sendSms(bo.getParam(), content);
        redisManager.insertObject(code, bo.getRedisKey(), bo.getTimeOut() == null ? CommonDict.TWO_MINUTES : bo.getTimeOut());

    }

    /**
     * 发送邮箱验证码
     *
     * @param bo 请求参数
     */
    @Override
    public void sendEmail(SendCodeReqBo bo) {

        if (!CheckUtil.isEmail(bo.getParam())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290005);
        }
        int code = getValidateCode();
        String content = bo.getContent().replaceFirst(CommonDict.SMS_CODE, String.valueOf(code));
        EmailReqBo emailReqBo = new EmailReqBo();
        emailReqBo.setMailAddressTO(Lists.newArrayList(bo.getParam()));
        emailReqBo.setSubject(bo.getSubject() == null ? "宝付支付" : bo.getSubject());
        emailReqBo.setContent(content);
        emailSendBiz.emailSend(emailReqBo);
        redisManager.insertObject(code, bo.getRedisKey(), bo.getTimeOut() == null ? CommonDict.TWO_MINUTES : bo.getTimeOut());
    }

    /**
     * 模糊发送(手机或者邮箱)
     *
     * @param bo 请求参数
     */
    @Override
    public void sendCode(SendCodeReqBo bo) {

        if (CheckUtil.isPhone(bo.getParam())) {
            sendSms(bo);
            return;
        }
        if (CheckUtil.isEmail(bo.getParam())) {
            sendEmail(bo);
            return;
        }
        throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290003);
    }

    /**
     * 校验验证码
     *
     * @param key  redisKey
     * @param code 验证码
     */
    @Override
    public void checkCode(String key, String code) {

        if (StringUtils.isBlank(key)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290004);
        }
        if (StringUtils.isBlank(code)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290008);
        }
        String validateCode = redisManager.queryObjectByKey(key);
        if (validateCode == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290001);
        }
        if (!validateCode.equals(code)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290002);
        }
        redisManager.deleteObject(key);
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
     * 自主选择发送(手机或者邮箱)
     *
     * @param bo 请求参数
     * @return 结果集
     */
    @Override
    public SendCodeRespBo autoSend(SendCodeReqBo bo) {
        try {
            //判断是否已经发送短信验证码
            Long remainTime = redisManager.ttl(bo.getRedisKey());
            Long timeOut = bo.getTimeOut() == null ? CommonDict.TWO_MINUTES : bo.getTimeOut();
            if (remainTime > 0 && timeOut / NumberDict.ONE_THOUSAND - remainTime < NumberDict.SIXTY) {
                return SendCodeConvert.convert(null, ErrorCodeEnum.ERROR_CODE_290010.getErrorDesc());
            }

            //获取用户号
            Long userNo = userPwdBiz.find(bo.getParam());
            //获取用户信息
            UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(userNo);
            //根据用户类型获取用户个人信息或企业信息（手机和邮箱）
            String toAddress = null;
            UserLoginInfoDo userLoginInfoDo = userLoginManager.queryLoginInfo(bo.getParam());
            if (userLoginInfoDo.getUserType() == UserTypeEnum.ORG.getType()) {
                UserOrgDo userOrgDo = userOrgManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
                toAddress = StringUtils.isBlank(userOrgDo.getPhoneNumber()) ? userOrgDo.getEmail() : userOrgDo.getPhoneNumber();
            }
            if (userLoginInfoDo.getUserType() == UserTypeEnum.PERSONAL.getType()) {
                UserPersonalDo userPersonalDo = userPersonalManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
                toAddress = userPersonalDo.getPhoneNumber();
            }
            //设置发送地址
            bo.setParam(toAddress);
            //发送短信
            sendCode(bo);
            return SendCodeConvert.convert(toAddress, null);
        } catch (BizServiceException e) {
            log.error("发送验证码失败：{}", e);
            return SendCodeConvert.convert(null, e.getExtraMsg());
        } catch (Exception e) {
            log.error("发送验证码异常：{}", e);
            return SendCodeConvert.convert(null, "发送验证码失败");
        }
    }
}
