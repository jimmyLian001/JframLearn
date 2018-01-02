package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.AccountPhoneNoBiz;
import com.baofu.international.global.account.core.biz.convert.UserTelServiceConvert;
import com.baofu.international.global.account.core.biz.external.SmsSendBizImpl;
import com.baofu.international.global.account.core.biz.models.FixPhoneNoApplyBo;
import com.baofu.international.global.account.core.biz.models.FixTelInfoQueryBo;
import com.baofu.international.global.account.core.biz.models.FixTelMessageCodeApplyBo;
import com.baofu.international.global.account.core.biz.models.UserRegisterTelInfoBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.RegisterManager;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import com.baofu.international.global.account.core.manager.UserTelInfoManager;
import com.baofu.international.global.account.core.manager.impl.RedisManagerImpl;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户注册手机号维护业务处理接口实现
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
@Slf4j
@Service
public class AccountPhoneNoBizImpl implements AccountPhoneNoBiz {

    /**
     * 用户注册手机号数据库操作接口
     */
    @Autowired
    private UserTelInfoManager userTelInfoManager;

    /**
     * 短信服务
     */
    @Autowired
    SmsSendBizImpl smsSendBiz;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    /**
     * 提供用户查询服
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 注册Manager
     */
    @Autowired
    private RegisterManager registerManager;

    /**
     * 用户申请修改绑定手机号业务逻辑
     *
     * @param fixPhoneNoApplyBo 注册手机号修改用户申请信息
     * @return
     */
    @Override
    public void reviseTel(FixPhoneNoApplyBo fixPhoneNoApplyBo) {

        UserLoginInfoDo userLoginInfo = userTelInfoManager.queryUserInfoByUserNo(fixPhoneNoApplyBo.getUserNo(),
                fixPhoneNoApplyBo.getLoginType(), fixPhoneNoApplyBo.getLoginNo());
        if (userLoginInfo == null) {
            log.info("未查询到用户注册信息");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190204);
        }
        Long userNo = fixPhoneNoApplyBo.getUserNo();
        UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(userNo);
//        根据loginNo判断登录账户是否存在
        int count = registerManager.selectUseLoginInfoNum(fixPhoneNoApplyBo.getAfterFixPhoneNumber());
        if (count > 0) {
            log.info("新手机号已经被注册");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290017);
        }

        UserLoginInfoDo userLoginInfoDo = UserTelServiceConvert.toUserLoginInfoDo(fixPhoneNoApplyBo);
        if (userLoginInfo.getUserType() == 2) {
            UserOrgDo userOrgDo = new UserOrgDo();
            userOrgDo.setPhoneNumber(fixPhoneNoApplyBo.getAfterFixPhoneNumber());
            userOrgDo.setUserInfoNo(userInfoDo.getUserInfoNo());
            userOrgDo.setUpdateBy(fixPhoneNoApplyBo.getLoginNo());
            userTelInfoManager.updateOrgTelByUserNo(userOrgDo);
        }
        if (userLoginInfo.getUserType() == 1) {
            UserPersonalDo userPersonalDo = new UserPersonalDo();
            userPersonalDo.setUserInfoNo(userInfoDo.getUserInfoNo());
            userPersonalDo.setPhoneNumber(fixPhoneNoApplyBo.getAfterFixPhoneNumber());
            userPersonalDo.setUpdateBy(fixPhoneNoApplyBo.getLoginNo());
            userTelInfoManager.updatePersonalTelByUserNo(userPersonalDo);
        }
        //验证成功，更新数据
        if (userLoginInfo.getUserType() == NumberDict.ONE) {
            userTelInfoManager.updateUserLoginInfo(userLoginInfoDo);
        }
        if (userLoginInfo.getUserType() == NumberDict.TWO) {
            int oldCount = registerManager.selectUseLoginInfoNum(fixPhoneNoApplyBo.getCurrentPhoneNumber());
            if (oldCount > 0) {
                userTelInfoManager.updateUserLoginInfo(userLoginInfoDo);
            } else {
                userTelInfoManager.insertLoginInfo(UserTelServiceConvert.toTUserLoginInfoDo(userLoginInfo, fixPhoneNoApplyBo.getAfterFixPhoneNumber()));
            }
        }
    }

    /**
     * 用户注册个人信息查询
     *
     * @param fixTelInfoQueryBo 用户号
     * @return UserRegisterTelInfoVo
     */
    @Override
    public UserRegisterTelInfoBo getUserRegisterTelInfo(FixTelInfoQueryBo fixTelInfoQueryBo) {
        UserSecrueqaInfoDo userSecrueqaInfoDo = userTelInfoManager.queryByUserNo(fixTelInfoQueryBo.getUserNo());
        if (userSecrueqaInfoDo == null) {
            log.info("未查询到用户密保问题");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190203);
        }
        UserLoginInfoDo userLoginInfoDo = userTelInfoManager.queryUserInfoByUserNo(fixTelInfoQueryBo.getUserNo());
        if (userLoginInfoDo == null) {
            log.info("未查询到用户注册手机信息");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190204);
        }
        return UserTelServiceConvert.toUserRegisterTelInfoVo(userSecrueqaInfoDo, userLoginInfoDo);
    }

    /**
     * 短信验证码发送业务
     *
     * @param fixTelMessageCodeApplyBo 发送信息
     */
    @Override
    public void sendMessageCode(FixTelMessageCodeApplyBo fixTelMessageCodeApplyBo) {
        redisManager.insertObject(fixTelMessageCodeApplyBo.getMessageCode(), fixTelMessageCodeApplyBo.getServiceType() +
                fixTelMessageCodeApplyBo.getCurrentPhoneNumber(), NumberDict.TEN_MINUTES);
        String content = fixTelMessageCodeApplyBo.getContent();
        smsSendBiz.sendSms(Lists.newArrayList(fixTelMessageCodeApplyBo.getCurrentPhoneNumber()), content);
    }
}
