package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.commons.utils.JsonUtil;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.RegisterBiz;
import com.baofu.international.global.account.core.biz.SendVerifyCodeBiz;
import com.baofu.international.global.account.core.biz.UserInfoCheckBiz;
import com.baofu.international.global.account.core.biz.convert.RedisterConvert;
import com.baofu.international.global.account.core.biz.models.AgentRegisterBo;
import com.baofu.international.global.account.core.biz.models.CreateUserBo;
import com.baofu.international.global.account.core.biz.models.SysSecrueqaInfoBo;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.baofu.international.global.account.core.manager.impl.UserNoManagerImplBase;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:注册 bizImpl
 * <p/>
 *
 * @author : liy on 2017/11/5
 * @version : 1.0.0
 */
@Slf4j
@Component
public class RegisterBizImpl implements RegisterBiz {

    /**
     * 注册 Manager
     */
    @Autowired
    private RegisterManager registerManager;

    /**
     * 发送验证码Biz
     */
    @Autowired
    private SendVerifyCodeBiz sendVerifyCodeBiz;

    /**
     * redis
     */
    @Autowired
    private RedisManager redisManager;
    /**
     * 用户号管理 by daoxuan
     */
    @Autowired
    private UserNoManagerImplBase userNoManagerImplBase;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 个人用户信息
     */
    @Autowired
    private UserPersonalManager personalManager;

    /**
     * 企业用户信息
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 资质校验
     */
    @Autowired
    private UserInfoCheckBiz userInfoCheckBiz;

    /**
     * 客服热线
     */
    @Value("${customer.service.hotline}")
    private String customerServiceHotline;

    /**
     * 消息队列
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 查询系统安全问题
     *
     * @return 结果集
     */
    @Override
    public List<SysSecrueqaInfoBo> selectSysSecrueqaInfoList() {

        return RedisterConvert.sysSecrueqaInfoBoConvert(registerManager.selectSysSecrueqaInfoList());
    }

    /**
     * 个人用户注册-发送短信验证码
     *
     * @param mobilePhone 手机号
     */
    @Override
    public void sendSmsCaptcha(String mobilePhone) {

        String key = CommonDict.CHECK_REGISTER_KEY.concat(mobilePhone);
        this.loginExist(mobilePhone);
        this.getRedis(mobilePhone, key);
        sendVerifyCodeBiz.sendSms(RedisterConvert.toSendSms(mobilePhone, customerServiceHotline));
    }

    /**
     * 个人用户注册-校验信验证码
     *
     * @param mobilePhone 手机号
     * @param captcha     验证码
     */
    @Override
    public void checkSmsCaptcha(String mobilePhone, String captcha) {

        this.loginExist(mobilePhone);
        if (StringUtils.isBlank(captcha)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290008);
        }
        sendVerifyCodeBiz.checkCode(CommonDict.REGISTER_KEY.concat(mobilePhone), captcha);
    }

    /**
     * 企业用户注册-发送邮件验证码
     *
     * @param email 邮箱
     */
    @Override
    public void sendEmailCaptcha(String email) {

        String key = CommonDict.CHECK_REGISTER_KEY.concat(email);
        this.loginExist(email);
        this.addCheckEmail(email);
        this.getRedis(email, key);
        sendVerifyCodeBiz.sendEmail(RedisterConvert.toSendEmail(email, customerServiceHotline));
    }

    /**
     * 企业用户注册-校验邮件验证码
     *
     * @param email   邮箱
     * @param captcha 验证码
     */
    @Override
    public void checkEmailCaptcha(String email, String captcha) {

        this.loginExist(email);
        this.addCheckEmail(email);
        if (StringUtils.isBlank(captcha)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290008);
        }
        sendVerifyCodeBiz.checkCode(CommonDict.REGISTER_KEY.concat(email), captcha);
    }

    /**
     * 创建用户
     *
     * @param bo 请求参数
     * @return 结果集
     */
    @Override
    @Transactional
    public Long createUser(CreateUserBo bo) {

        this.loginExist(bo.getLoginNo());

        //创建用户号
        long userNo = userNoManagerImplBase.generateLongUserNoBy(bo.getUserType());

        //创建用户信息编号
        long userInfoNo = orderIdManager.orderIdCreate();
        log.info("创建用户信息编号为:{}", userInfoNo);

        // 新增用户登录信息
        UserLoginInfoDo userLoginInfoDo = RedisterConvert.toUserLoginInfoDo(bo, userNo);
        log.info("新增用户登录信息:{}", userLoginInfoDo);
        registerManager.insertUserLoginInfo(userLoginInfoDo);

        // 新增用户基本信息
        UserInfoDo userInfoDo = RedisterConvert.toUserInfoDo(bo, userNo, userInfoNo);
        log.info("新增用户基本信息:{}", userInfoDo);
        registerManager.insertUserInfo(userInfoDo);

        //新增个人用户信息
        if (UserTypeEnum.PERSONAL.getType() == bo.getUserType()) {
            UserPersonalDo userPersonalDo = RedisterConvert.toUserPersonalDo(bo, userInfoNo);
            log.info("新增个人用户信息:{}", userInfoDo);
            personalManager.insertUserPersonalInfo(userPersonalDo);
        }

        //新增企业用户信息
        if (UserTypeEnum.ORG.getType() == bo.getUserType()) {
            this.addCheckEmail(bo.getLoginNo());
            UserOrgDo userOrgDo = RedisterConvert.toUserOrgDo(bo, userInfoNo);
            log.info("新增企业用户信息:{}", userInfoDo);
            userOrgManager.insert(userOrgDo);
        }

        //新增用户登录密码
        UserPwdDo userPwdDoLogin = RedisterConvert.toUserPwdDoLogin(bo, userNo);
        log.info("新增用户登录密码:{}", userPwdDoLogin);
        registerManager.insertUserPwdInfo(userPwdDoLogin);

        //新增用户安全问题
        List<UserSecrueqaInfoDo> doList = RedisterConvert.toUserSecrueqaInfoDoList(bo, userNo);
        log.info("新增用户安全问题:{}", doList);
        for (UserSecrueqaInfoDo infoDo : doList) {
            this.checkQuestionNo(infoDo.getQuestionNo());
            registerManager.insertUserSecrueqaInfo(infoDo);
        }
        return userNo;
    }

    /**
     * 发送信息异常时，删除RedisKey
     *
     * @param key key
     */
    @Override
    public void delRedisKey(String key) {

        try {
            if (!redisManager.queryObjectByKey(key).isEmpty()) {
                redisManager.deleteObject(key);
            }
        } catch (Exception e) {
            log.info("删除发送过信息的Key异常", e);
        }
    }

    /**
     * 通知账户系统用户注册有代理商
     *
     * @param registerBo
     */
    @Override
    public void sendMqMessage(AgentRegisterBo registerBo) {
        mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_ACCOUNT_REGISTER_SUCCESS_QUEUE_NAME, JsonUtil.toJSONString(registerBo));
        log.info("通知账户代理商户号，队列名：{},内容：{}", MqSendQueueNameEnum.GLOBAL_USER_WITHDRAW_APPLY_QUEUE_NAME, registerBo);
    }

    /**
     * 根据loginNo判断登录账户是否存在
     *
     * @param loginNo 登录账户
     */
    private void loginExist(String loginNo) {

        if (StringUtils.isEmpty(loginNo)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290003);
        }
        int flag = registerManager.selectUseLoginInfoNum(loginNo);
        if (flag > 0) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190010);
        }
    }

    /**
     * 判断是否发送过信息
     *
     * @param loginNo 登录名
     * @param key     redisKey
     */
    private void getRedis(String loginNo, String key) {

        String value = redisManager.queryObjectByKey(key);
        if (!StringUtils.isBlank(value)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290010);
        }
        redisManager.insertObject(loginNo, key, CommonDict.ONE_MINUTES);
    }

    /**
     * 判断安全问题是否存在
     *
     * @param questionNo 问题编号
     */
    private void checkQuestionNo(long questionNo) {

        SysSecrueqaInfoDo infoDo = registerManager.selectSysSecrueqaInfoByQuestionNo(questionNo);
        if (infoDo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290014);
        }
    }

    /**
     * 新增资质时邮箱校验
     *
     * @param email 邮箱
     */
    private void addCheckEmail(String email) {

        userInfoCheckBiz.addCheckEmail(email);
    }
}
