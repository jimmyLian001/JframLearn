package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.ChannelNotifyApplyStatusBiz;
import com.baofu.international.global.account.core.biz.convert.ApplyAccountBizConvert;
import com.baofu.international.global.account.core.biz.external.EmailSendBizImpl;
import com.baofu.international.global.account.core.biz.external.SmsSendBizImpl;
import com.baofu.international.global.account.core.biz.models.ChannelNotifyApplyAccountBo;
import com.baofu.international.global.account.core.biz.models.EmailReqBo;
import com.baofu.international.global.account.core.common.constant.NotifyTemplateConst;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.AccApplyStatusEnum;
import com.baofu.international.global.account.core.common.enums.CcyEnum;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.ApplyAccountManager;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.baofu.international.global.account.core.manager.UserPersonalManager;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: yangjian  Date: 2017-11-07 ProjectName:  Version: 1.0
 */
@Slf4j
@Component
public class ChannelNotifyApplyStatusBizImpl implements ChannelNotifyApplyStatusBiz {

    /**
     *
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 用户信息Manager
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 个人用户Manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 企业用户
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 短信服务
     */
    @Autowired
    private SmsSendBizImpl smsSendBiz;

    /**
     * 邮件服务
     */
    @Autowired
    private EmailSendBizImpl emailSendBiz;

    /**
     * 接收渠道第一次MQ消息新增记录
     *
     * @param applyBo applyBo
     */
    @Override
    public void addApplyAccountInfo(ChannelNotifyApplyAccountBo applyBo) {

        Long accountNo = null;
        int applyStatus = AccApplyStatusEnum.HANDLING.getCode();
        if (applyBo.getCode() == NumberDict.TWO) {
            log.error("global-biz-渠道第一次mq通知回调失败，失败信息：{}", applyBo.getMessage());
            applyStatus = AccApplyStatusEnum.FAIL.getCode();
        } else {
            accountNo = applyAccountManager.addAccountInfo(applyBo.getApplyNo(), applyBo.getWalletId());
            log.info("添加账户信息成功，账户号：{}", accountNo);
        }
        log.info("global-biz-更新申请信息路由编号，银行卡号");
        UserPayeeAccountApplyDo userPayeeAccountApplyDo = new UserPayeeAccountApplyDo();
        userPayeeAccountApplyDo.setAccountNo(accountNo);
        userPayeeAccountApplyDo.setApplyId(applyBo.getApplyNo());
        userPayeeAccountApplyDo.setStatus(applyStatus);
        applyAccountManager.modifyApplyStatus(userPayeeAccountApplyDo);
        log.info("global-biz-更新店铺的银行卡号和店铺状态:{}(1、正常 2、失效 3、处理中)", applyBo.getSuccFlag());
    }

    /**
     * 接收渠道第二次MQ消息更新账号和路由编号
     *
     * @param applyBo applyBo
     */
    @Override
    public void updateApplyAccountInfo(ChannelNotifyApplyAccountBo applyBo) {

        log.info("global-biz-更新申请信息路由编号，银行卡号");
        UserPayeeAccountApplyDo tUserPayeeAccountDo = new UserPayeeAccountApplyDo();
        tUserPayeeAccountDo.setApplyId(applyBo.getApplyNo());
        //判断是否开户成功
        tUserPayeeAccountDo.setStatus(applyBo.getCode() != NumberDict.ONE ? AccApplyStatusEnum.FAIL.getCode() :
                AccApplyStatusEnum.SUCCESS.getCode());
        applyAccountManager.modifyApplyStatus(tUserPayeeAccountDo);
        log.info("global-biz-更新订单中的申请状态");
        if (applyBo.getCode() != NumberDict.ONE) {
            log.error("global-biz-渠道二次mq通知回调失败，失败信息：{}", applyBo.getMessage());
            return;
        }
        //更新账户表
        UserPayeeAccountApplyDo userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByApplyId(applyBo.getApplyNo());
        if (userPayeeAccountApplyDo == null) {
            log.error("call 当前申请账户信息查询为空");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290013);
        }
        UserPayeeAccountDo userPayeeAccountDo = new UserPayeeAccountDo();
        userPayeeAccountDo.setRoutingNumber(applyBo.getRoutingNumber());
        userPayeeAccountDo.setBankAccNo(applyBo.getBankAccNo());
        userPayeeAccountDo.setAccountNo(userPayeeAccountApplyDo.getAccountNo());
        userPayeeAccountDo.setBankAccName(applyBo.getBankAccName());
        applyAccountManager.modifyAccountInfo(userPayeeAccountDo);
        log.info("global-biz-查询申信息");
        UserPayeeAccountDo accountDo = applyAccountManager.queryAccountInfo(userPayeeAccountApplyDo.getAccountNo());
        log.info("global-biz-新增余额记录");
        if (accountDo == null) {
            log.error("call 当前账户信息查询为空");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290013);
        }
        applyAccountManager.addAccountBal(ApplyAccountBizConvert.paramConvertAccBal(accountDo));

        //开户结果通知用户
        accountResultNotifyUser(accountDo.getUserNo(), accountDo.getCcy());
    }


    /**
     * 开户结果通知用户
     *
     * @param userNo 用户编号
     */
    private void accountResultNotifyUser(Long userNo, String ccy) {
        try {
            log.info("开户结果通知用户，用户号：{},币种：{}", userNo, ccy);
            UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(userNo);
            String mobile;
            String email;
            //根据用户类型判断查询用户信息
            if (UserTypeEnum.PERSONAL.getType() == userInfoDo.getUserType()) {
                UserPersonalDo userPersonalDo = userPersonalManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
                mobile = userPersonalDo.getPhoneNumber();
                email = userPersonalDo.getEmail();
            } else {
                UserOrgDo userOrgDo = userOrgManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
                mobile = userOrgDo.getPhoneNumber();
                email = userOrgDo.getEmail();
            }
            //通知内容
            String content = NotifyTemplateConst.ACCOUNT_OPEN_RESULT.replace("${CCY}", CcyEnum.getCcyCHN(ccy));
            Boolean notifyResult;
            //判断手机是否为空，如果不为空的情况发送短信，如果手机为空发送邮件
            if (StringUtils.isNotBlank(mobile)) {
                notifyResult = smsSendBiz.sendSms(mobile, content);
            } else {
                EmailReqBo emailReqBo = new EmailReqBo();
                emailReqBo.setMailAddressTO(Lists.newArrayList(email));
                emailReqBo.setContent("【宝付支付】" + content);
                emailReqBo.setSubject("收款账户开户结果邮件通知");
                notifyResult = emailSendBiz.emailSend(emailReqBo);
            }
            log.info("开户结果通知用户，通知结果：{}", notifyResult);
        } catch (Exception e) {
            log.error("开户结果通知用户信息异常：", e);
        }
    }
}
