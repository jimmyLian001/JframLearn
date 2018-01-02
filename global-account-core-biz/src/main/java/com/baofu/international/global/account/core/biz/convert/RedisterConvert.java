package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.CreateUserBo;
import com.baofu.international.global.account.core.biz.models.SendCodeReqBo;
import com.baofu.international.global.account.core.biz.models.SysSecrueqaInfoBo;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.dal.model.*;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * description：注册信息转换类
 * <p/>
 *
 * @author : liy on 2017/11/6
 * @version : 1.0.0
 */
public class RedisterConvert {

    private RedisterConvert() {

    }

    /**
     * 系统操作员
     */
    private static final String SYSTEM = "SYSTEM";

    /**
     * 系统安全问题
     *
     * @param listDo 请求参数
     * @return 结果集
     */
    public static List<SysSecrueqaInfoBo> sysSecrueqaInfoBoConvert(List<SysSecrueqaInfoDo> listDo) {

        List<SysSecrueqaInfoBo> listBo = Lists.newArrayList();
        for (SysSecrueqaInfoDo infoDo : listDo) {
            SysSecrueqaInfoBo infoBo = new SysSecrueqaInfoBo();
            infoBo.setQuestionNo(infoDo.getQuestionNo());
            infoBo.setQuestion(infoDo.getQuestion());
            infoBo.setQuestionType(infoDo.getQuestionType());
            listBo.add(infoBo);
        }
        return listBo;
    }

    /**
     * 发送短信验证码转换
     *
     * @param mobilePhone            手机号
     * @param customerServiceHotline 热线
     * @return 结果集
     */
    public static SendCodeReqBo toSendSms(String mobilePhone, String customerServiceHotline) {

        SendCodeReqBo bo = new SendCodeReqBo();
        bo.setParam(mobilePhone);
        bo.setContent(CommonDict.REGISTER_INFO.concat(customerServiceHotline));
        bo.setRedisKey(CommonDict.REGISTER_KEY.concat(mobilePhone));
        bo.setTimeOut(CommonDict.TEN_MINUTES_MS);
        return bo;
    }

    /**
     * 发送邮箱验证码转换
     *
     * @param email                  邮箱
     * @param customerServiceHotline 热线
     * @return 结果集
     */
    public static SendCodeReqBo toSendEmail(String email, String customerServiceHotline) {

        SendCodeReqBo bo = new SendCodeReqBo();
        bo.setParam(email);
        bo.setContent(CommonDict.REGISTER_INFO.concat(customerServiceHotline));
        bo.setRedisKey(CommonDict.REGISTER_KEY.concat(email));
        bo.setTimeOut(CommonDict.TEN_MINUTES_MS);
        bo.setSubject(CommonDict.SEND_REGISTER_EMAIL_SUBJECT);
        return bo;
    }


    /**
     * 新增用户登录信息转换
     *
     * @param bo     用户信息
     * @param userNo 用户号
     * @return 结果集
     */
    public static UserLoginInfoDo toUserLoginInfoDo(CreateUserBo bo, long userNo) {

        UserLoginInfoDo userLoginInfoDo = new UserLoginInfoDo();
        userLoginInfoDo.setLoginNo(bo.getLoginNo());
        userLoginInfoDo.setUserNo(userNo);
        userLoginInfoDo.setLoginType(bo.getLoginType());
        userLoginInfoDo.setUserType(bo.getUserType());
        userLoginInfoDo.setLoginState(LoginStateEnum.NORMAL.getState());
        userLoginInfoDo.setCreateBy(SYSTEM);
        userLoginInfoDo.setUpdateBy(SYSTEM);
        return userLoginInfoDo;
    }


    /**
     * 新增用户基本信息转换
     *
     * @param bo         用户信息
     * @param userNo     用户号
     * @param userInfoNo 申请编号
     * @return 结果集
     */
    public static UserInfoDo toUserInfoDo(CreateUserBo bo, long userNo, long userInfoNo) {

        UserInfoDo userInfoDo = new UserInfoDo();
        userInfoDo.setUserNo(userNo);
        userInfoDo.setUserInfoNo(userInfoNo);
        userInfoDo.setUserType(bo.getUserType());
        userInfoDo.setCreateBy(SYSTEM);
        userInfoDo.setUpdateBy(SYSTEM);
        return userInfoDo;

    }

    /**
     * 新增用户登录密码信息转换
     *
     * @param bo     用户信息
     * @param userNo 用户号
     * @return 结果集
     */
    public static UserPwdDo toUserPwdDoLogin(CreateUserBo bo, long userNo) {

        UserPwdDo userPwdDo = new UserPwdDo();
        userPwdDo.setUserNo(userNo);
        userPwdDo.setPwd(bo.getLoginPwd());
        userPwdDo.setPwdType(PwdTypeEnum.LOGIN.getType());
        userPwdDo.setState(PwdStateEnum.NORMAL.getState());
        userPwdDo.setErrorNum(0);
        userPwdDo.setVersion(1);
        userPwdDo.setCreateBy(SYSTEM);
        userPwdDo.setUpdateBy(SYSTEM);
        return userPwdDo;
    }

    /**
     * 新增用户安全问题转换
     *
     * @param bo     用户信息
     * @param userNo 用户号
     * @return 结果集
     */
    public static List<UserSecrueqaInfoDo> toUserSecrueqaInfoDoList(CreateUserBo bo, long userNo) {

        List<UserSecrueqaInfoDo> doList = new ArrayList<>();
        doList.add(userSecrueqaInfoDoConvert(userNo, bo.getQuestionNoOne(), bo.getAnswerOne()));
        doList.add(userSecrueqaInfoDoConvert(userNo, bo.getQuestionNoTwo(), bo.getAnswerTwo()));
        doList.add(userSecrueqaInfoDoConvert(userNo, bo.getQuestionNoThree(), bo.getAnswerThree()));
        return doList;
    }


    /**
     * 公共用户安全问题转换
     *
     * @param userNo     用户号
     * @param questionNo 问题编号
     * @param answer     答案
     * @return 结果集
     */
    public static UserSecrueqaInfoDo userSecrueqaInfoDoConvert(long userNo, String questionNo, String answer) {

        UserSecrueqaInfoDo userSecrueqaInfoDo = new UserSecrueqaInfoDo();
        userSecrueqaInfoDo.setUserNo(userNo);
        userSecrueqaInfoDo.setQuestionSequence(1);
        userSecrueqaInfoDo.setQuestionNo(Long.valueOf(questionNo));
        userSecrueqaInfoDo.setAnswer(answer);
        userSecrueqaInfoDo.setCreateBy(SYSTEM);
        userSecrueqaInfoDo.setUpdateBy(SYSTEM);
        userSecrueqaInfoDo.setState(QuestionStateEnum.EFFECTIVE.getState());
        return userSecrueqaInfoDo;
    }

    /**
     * 新增个人用户信息转换
     *
     * @param bo      用户信息
     * @param applyNo 申请编号
     * @return 结果集
     */
    public static UserPersonalDo toUserPersonalDo(CreateUserBo bo, long applyNo) {

        UserPersonalDo userPersonalDo = new UserPersonalDo();
        userPersonalDo.setUserInfoNo(applyNo);
        userPersonalDo.setRealnameStatus(RealNameStatusEnum.NOT.getState());
        userPersonalDo.setPhoneNumber(bo.getLoginNo());

        return userPersonalDo;

    }

    /**
     * 新增企业用户信息转换
     *
     * @param bo         用户信息
     * @param userInfoNo 申请编号
     * @return 结果集
     */
    public static UserOrgDo toUserOrgDo(CreateUserBo bo, long userInfoNo) {

        UserOrgDo userOrgDo = new UserOrgDo();
        userOrgDo.setUserInfoNo(userInfoNo);
        userOrgDo.setRealnameStatus(RealNameStatusEnum.NOT.getState());
        userOrgDo.setEmail(bo.getLoginNo());
        userOrgDo.setCreateBy(SYSTEM);
        userOrgDo.setUpdateBy(SYSTEM);
        return userOrgDo;

    }

}
