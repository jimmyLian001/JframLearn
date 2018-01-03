package com.baofu.international.global.account.client.web.controller.personalcenter;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.enums.ResultEnum;
import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.service.SecurityConfigService;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.client.web.models.*;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.client.web.models.FixPhoneNoApplyForm;
import com.baofu.international.global.account.client.web.models.PayPwdReqVo;
import com.baofu.international.global.account.client.web.models.ResultVo;
import com.baofu.international.global.account.client.web.models.SecurityConfigVo;
import com.baofu.international.global.account.client.web.models.SecurityReqVo;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.SysModuleList;
import com.baofu.international.global.account.client.web.models.UserLoginPwdReqVo;
import com.baofu.international.global.account.core.facade.RegisterFacade;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.SysSecrueqaInfoRespDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 安全设置
 * <p>
 * 1，登录密码修改
 * 2，登录密码重置
 * 3，支付密码修改
 * 4，密保问题重置
 * </p>
 *
 * @author : wuyazi
 * @version :1.0.0
 * @date :2017/11/06
 */
@Slf4j
@Controller
@RequestMapping("/person/center/")
public class SecurityConfigController {

    private static final String LOGIN_NO = "loginNo";

    private static final String USER_NO = "userNo";

    private static final String MSG_TYPE = "msgType";

    private static final String USER_INFO = "userInfo";

    private static final String QUESTIONS = "questions";

    private static final String SECURITY_CONFIG_VO = "securityConfigVo";

    private static final String SUCCESS = "success";

    private static final String RESULT_URL = "resultUrl";

    private static final String ERROR_MESSAGE = "errorMessage";


    /**
     * 安全中心服务service
     */
    @Autowired
    private SecurityConfigService securityConfigService;

    /**
     * 注册服务
     */
    @Autowired
    private RegisterFacade registerFacade;


    /**
     * 跳转安全设置首页
     *
     * @return model
     */
    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        SessionVo sessionVo = SessionUtil.getSessionVo();
        //获取当前用户名
        String loginNo = sessionVo.getLoginNo();
        Long userNo = sessionVo.getUserNo();
        Integer userType = sessionVo.getUserType();

        UserInfoBo userInfoBo = securityConfigService.getUserInfo(loginNo);
        String mobileBandStatus = StringUtils.isBlank(userInfoBo.getMobileNo()) ? "0" : "1";
        if (userType == UserTypeEnum.ORG.getType()) {
            model = buildModelView("/personalCenter/index/enterprise-setting");
        }

        if (userType == UserTypeEnum.PERSONAL.getType()) {
            model = buildModelView("/personalCenter/index/personal-setting");
            mobileBandStatus = "1";
        }
        //获取用户信息
        //查询用户客户信息
        SecurityConfigVo securityConfigVo = new SecurityConfigVo();
        securityConfigVo.setLoginNo(loginNo);
        securityConfigVo.setUserNo(userNo);
        securityConfigVo.setAuthStatus(String.valueOf(userInfoBo.getRealnameStatus() == null ? 0 : userInfoBo.getRealnameStatus()));
        securityConfigVo.setLoginPassStatus("1");
        securityConfigVo.setMobileBandStatus(mobileBandStatus);
        securityConfigVo.setQuestionsStatus("1");
        securityConfigVo.setPayPassStatus(securityConfigService.payPwdExist(userNo) ? "1" : "0");
        model.addObject(SECURITY_CONFIG_VO, securityConfigVo);
        model.addObject(USER_INFO, userInfoBo);
        return model;
    }

    /**
     * 登入修改密码（第一步）
     *
     * @return model
     */
    @RequestMapping(value = "/modifyPassword/first.do", method = RequestMethod.GET)
    public ModelAndView firstStep() {
        return buildModelView("/personalCenter/forgotpwd/modify-login-pwd");
    }

    /**
     * 修改登录密码（第二步）
     *
     * @param loginPwdReqVo 登录密码修改VO
     * @return model
     */
    @RequestMapping(value = "/modifyPassword/verifyPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean verifyPwd(UserLoginPwdReqVo loginPwdReqVo) {
        log.info("修改登录密码（第二步）参数:{}", loginPwdReqVo);
        //检验旧密码是否正确
        Boolean result = securityConfigService.verifyLoginPwd(SessionUtil.getSessionVo().getUserNo()
                , PwdEncryptUtil.encrypt(loginPwdReqVo.getOldPassword()));
        log.info("修改登录密码（第二步）:验证旧密码结果：{}", result);
        return result;
    }

    /**
     * 修改登录密码（第二步）
     *
     * @param loginPwdReqVo 登录密码修改VO
     * @return model
     */
    @RequestMapping(value = "/modifyPassword/second.do", method = RequestMethod.POST)
    public ModelAndView secondStep(UserLoginPwdReqVo loginPwdReqVo) {
        log.info("修改登录密码（第二步）参数:{}", loginPwdReqVo);
        ModelAndView model = buildModelView(null);
        //获取当前用户名并校验账户是否被锁定
        SessionVo sessionVo = SessionUtil.getSessionVo();
        Long userNo = sessionVo.getUserNo();

        //检验新密码和二次密码是否一致
        if (StringUtils.isBlank(loginPwdReqVo.getOldPassword())
                || StringUtils.isBlank(loginPwdReqVo.getFirstPwd())
                || StringUtils.isBlank(loginPwdReqVo.getSecondPwd())
                || !loginPwdReqVo.getFirstPwd().equals(loginPwdReqVo.getSecondPwd())) {
            log.info("修改登录密码（第二步）: 参数校验失败，参数填写不正确");
            return addResultParams(model, ResultEnum.MODIFY_LOGIN_PWD_SECOND_0);
        }

        //检验旧密码是否正确
        Boolean result = securityConfigService.verifyLoginPwd(userNo, PwdEncryptUtil.encrypt(loginPwdReqVo.getOldPassword()));
        log.info("修改登录密码（第二步）:验证旧密码结果：{}", result);
        if (!result) {
            return addResultParams(model, ResultEnum.MODIFY_LOGIN_PWD_SECOND_3);
        }

        //基本校验通过后，发起更新密码操作
        result = securityConfigService.modifyLoginPwd(userNo, PwdEncryptUtil.encrypt(loginPwdReqVo.getOldPassword())
                , PwdEncryptUtil.encrypt(loginPwdReqVo.getFirstPwd()), PwdEncryptUtil.encrypt(loginPwdReqVo.getSecondPwd()), sessionVo.getLoginNo());
        log.info("修改登录密码（第二步）:修改密码结果：{}", result);
        if (!result) {
            return addResultParams(model, ResultEnum.MODIFY_LOGIN_PWD_SECOND_1);
        }

        return addResultParams(model, ResultEnum.MODIFY_LOGIN_PWD_SECOND_2);
    }


    /**
     * 修改密保问题（第一步）
     *
     * @return model
     */
    @RequestMapping(value = "/modifyQuestion/index.do", method = RequestMethod.GET)
    public ModelAndView indexForModifyQuestion() {
        ModelAndView model = buildModelView("/personalCenter/forgotpwd/reset-question-index");
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(SessionUtil.getSessionVo().getLoginNo());
        return model.addObject(USER_INFO, userInfoBo);
    }

    /**
     * 修改密保答案跳转
     *
     * @param url 跳转地址
     * @return model
     */
    @RequestMapping(value = "/modifyQuestion/{url}.do", method = RequestMethod.GET)
    public ModelAndView directForModifyQuestion(@PathVariable("url") String url) {
        ModelAndView model = buildModelView("/personalCenter/forgotpwd/" + url);
        //获取用戶安全問題
        List<UserAnswerRespDTO> questions = securityConfigService.getUserAnswer(SessionUtil.getSessionVo().getUserNo());
        model.addObject(QUESTIONS, questions);

        //获取系统密保问题集
        Result<List<SysSecrueqaInfoRespDto>> result = registerFacade.
                selectSysSecrueqaInfoList(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        model.addObject("sysQuestionList", result.getResult());

        return model;
    }


    /**
     * 修改密保问题（第二步短信验证）
     *
     * @param securityReqVo 参数
     * @return model
     */
    @RequestMapping(value = "/modifyQuestion/validteCode.do", method = RequestMethod.POST)
    public ModelAndView validateCodeForModifyQuestion(SecurityReqVo securityReqVo) {
        log.info("修改密保问题（第二步）: 参数:{}", securityReqVo);
        ModelAndView model = buildModelView("redirect:/person/center/modifyQuestion/reset-question.do");
        //获取当前用户名
        SessionVo sessionVo = SessionUtil.getSessionVo();
        String loginNo = sessionVo.getLoginNo();
        //验证短信验证码
        boolean result = securityConfigService.verifyCode(loginNo, SendCodeContentEnum.UPDATE_QUESTION_MSG, securityReqVo.getVerifyCode());
        log.info("修改密保问题（第二步）: 验证短信验证码结果:{}", result);
        if (!result) {
            return addResultParams(model, ResultEnum.MODIFY_QUESTION_SECOND_0);
        }
        return model;
    }

    /**
     * 修改密保问题（第二步密保问题验证）
     *
     * @return model
     */
    @RequestMapping(value = "/modifyQuestion/validateAnswer.do", method = RequestMethod.POST)
    public ModelAndView validateAnswerForModifyQuestion(SecurityReqVo securityReqVo) {
        log.info("修改密保问题（第二步）: 参数:{}", securityReqVo);
        ModelAndView model = buildModelView("redirect:/person/center/modifyQuestion/reset-question.do");
        //获取当前用户名
        SessionVo sessionVo = SessionUtil.getSessionVo();
        Long userNo = sessionVo.getUserNo();

        for (String questionNo : securityReqVo.getParams().keySet()) {
            //验证用戶安全問題
            boolean result2 = securityConfigService.verifyUserAnswer(userNo, Long.valueOf(questionNo),
                    PwdEncryptUtil.secrueqaEncrypt(securityReqVo.getParams().get(questionNo)));
            log.info("修改密保问题（第二步）:密保编号为{}的验证结果:{}", questionNo, result2);
            if (!result2) {
                return addResultParams(model, ResultEnum.MODIFY_QUESTION_THREE_3);
            }
        }
        //获取用戶安全問題
        List<UserAnswerRespDTO> answers = securityConfigService.getUserAnswer(userNo);
        return model.addObject("answerDto", answers != null && !answers.isEmpty() ? answers.get(0) : null);
    }

    /**
     * 修改密保问题（第三步）
     *
     * @param securityReqVo 参数
     * @return model
     */
    @RequestMapping(value = "/modifyQuestion/modify.do", method = RequestMethod.POST)
    public ModelAndView secondStepForModifyQuestion(SecurityReqVo securityReqVo) {
        ModelAndView model = buildModelView(null);
        SessionVo sessionVo = SessionUtil.getSessionVo();

        log.info("修改密保问题（第三步）: 参数:{}", securityReqVo);
        if (securityReqVo.getParams() == null) {
            log.info("修改密保问题（第三步）: 密保问题答案不能为空");
            return model;
        }

        //修改密保答案
        int sequence = 1;
        List<UserAnswerReqDTO> userAnswerReqDtos = Lists.newArrayList();
        for (String questionNo : securityReqVo.getParams().keySet()) {
            UserAnswerReqDTO userAnswerReqDTO = new UserAnswerReqDTO();
            userAnswerReqDTO.setQuestionSequence(sequence++);
            userAnswerReqDTO.setUserNo(sessionVo.getUserNo());
            userAnswerReqDTO.setQuestionNo(Long.valueOf(questionNo));
            userAnswerReqDTO.setAnswer(PwdEncryptUtil.secrueqaEncrypt(securityReqVo.getParams().get(questionNo)));
            userAnswerReqDTO.setUpdatedBy(sessionVo.getLoginNo());
            userAnswerReqDtos.add(userAnswerReqDTO);
        }
        boolean result = securityConfigService.modifyUserAnswer(userAnswerReqDtos);
        log.info("修改密保问题（第三步）: 修改密保问题答案结果：{}", result);
        return addResultParams(model, ResultEnum.MODIFY_QUESTION_THREE_2);
    }

    /**
     * 发送验证码
     *
     * @param loginNo 登录号
     * @param msgType 类型
     * @return boolean 发送成功
     */
    @ResponseBody
    @RequestMapping(value = "/sendCode.do", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String sendCode(String loginNo, String msgType) {
        //获取当前用户名
        SessionVo sessionVo = SessionUtil.getSessionVo();
        loginNo = loginNo == null ? sessionVo.getLoginNo() : loginNo;
        log.info("发送验证码: 参数:{}", loginNo);
        SendCodeRespDto result = securityConfigService.autoSendCode(loginNo, msgType);
        log.info("发送验证码结果:{}", result);
        return JsonUtil.toJSONString(result);
    }

    /**
     * 生成页面modelAndView
     *
     * @param url 发文地址地址
     * @return ModelAndView 页面model
     */
    private ModelAndView buildModelView(String url) {
        ModelAndView model = new ModelAndView(StringUtils.isNotBlank(url) ? url : "");
        SessionVo sessionVo = SessionUtil.getSessionVo();
        if (sessionVo == null) {
            model.setViewName("redirect:../login/index.do");
            return model;
        }
        model.addObject(USER_NO, sessionVo.getUserNo());
        model.addObject(LOGIN_NO, sessionVo.getLoginNo());
        return model;
    }

    /**
     * 设置结果页面和参数
     *
     * @param model      页面model
     * @param resultEnum 结果枚举
     */
    private ModelAndView addResultParams(ModelAndView model, ResultEnum resultEnum) {
        ResultVo resultVo = new ResultVo(resultEnum.getSuccessFlag(), resultEnum.getLoginOutFlag(), resultEnum.getTitle()
                , resultEnum.getContent(), resultEnum.getReturnUrl());
        log.info("操作完成，结果响应参数:{}", resultVo);
        model.addObject("result", resultVo);
        model.setViewName(resultEnum.getResultUrl());
        return model;
    }

    /**
     * 进入设置支付密码页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toPaymentPwd.do", method = RequestMethod.GET)
    public ModelAndView toCreatePayPwd(HttpServletRequest request) {
        ModelAndView view = buildModelView("/personalCenter/forgotpwd/payPwd/payment-pwd");
        view.addObject(MSG_TYPE, "1");
        return view;
    }

    /**
     * 设置支付密码
     *
     * @param payPwdReqVo 支付密码请求
     * @return 设置结果
     */
    @ResponseBody
    @RequestMapping(value = "createPayPwd.do", method = RequestMethod.POST)
    public Map<String, String> createPayPwd(PayPwdReqVo payPwdReqVo) {
        Map<String, String> map = Maps.newHashMap();
        String resultUrl = "personalCenter/forgotpwd/payPwd/pwd-success";
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
//            验证短信验证码
            boolean result = securityConfigService.verifyCode(sessionVo.getLoginNo(),
                    SendCodeContentEnum.SET_PAY_PWD_MSG, payPwdReqVo.getMessageCode());
            log.info("设置支付密码: 验证短信验证码结果:{}", result);
            if (!result) {
                map.put(ERROR_MESSAGE, "用户验证码已过期,请重新获取验证码!");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "用户验证码已过期,请获取验证!");
            }
            String firstPayPwd = PwdEncryptUtil.paymentEncrypt(payPwdReqVo.getPayPwd());
            String secondPayPwd = PwdEncryptUtil.paymentEncrypt(payPwdReqVo.getPayPwdAgain());
            if (!firstPayPwd.equals(secondPayPwd)) {
                log.info("设置支付密码: 两次密码不一致:{}，", payPwdReqVo);
                map.put(ERROR_MESSAGE, "两次支付密码不一致，请重新输入");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "设置支付密码: 两次密码不一致");
            }
            Result<Boolean> result2 = securityConfigService.createPayPwd(sessionVo.getUserNo(), firstPayPwd, secondPayPwd, sessionVo.getLoginNo());
            log.info("设置支付密码返回结果：{}", result2);
            if (!result2.isSuccess() || !result2.getResult()) {
                log.info("设置支付密码失败！");
                map.put(ERROR_MESSAGE, "设置支付密码失败，请联系管理员");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "设置支付密码失败，请与管理员联系！");
            }
            map.put(RESULT_URL, resultUrl);
            map.put("msg", SUCCESS);
        } catch (Exception e) {
            log.info("设置支付密码 异常:{}", e);
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        return map;
    }

    /**
     * 重置支付密码（第一步）
     *
     * @return model
     */
    @RequestMapping(value = "resetPaymentPwd/first.do", method = RequestMethod.GET)
    public ModelAndView resetPayPwdFirstStep() {
        ModelAndView model = buildModelView("/personalCenter/forgotpwd/payPwd/reset-pwd");
        SessionVo sessionVo = SessionUtil.getSessionVo();
        //获取用户信息
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(sessionVo.getLoginNo());
        model.addObject(USER_INFO, userInfoBo);
        return model;
    }


    @RequestMapping(value = "resetPaymentPwd/second.do", method = RequestMethod.GET)
    public ModelAndView resetPayPwdTwo(String rechargeType) {
        ModelAndView view = buildModelView("redirect:../login/index.do");
        SessionVo sessionVo = SessionUtil.getSessionVo();
        //验证方式
        switch (rechargeType) {
            case "phone":
                //通过手机号验证
                view.setViewName("personalCenter/forgotpwd/payPwd/reset-pwd-tel");
                break;
            case "question":
                view.setViewName("personalCenter/forgotpwd/payPwd/reset-pwd-question");
                List<UserAnswerRespDTO> result2 = securityConfigService.getUserAnswer(sessionVo.getUserNo());
                view.addObject(QUESTIONS, result2 != null && !result2.isEmpty() ? result2 : null);
                break;
            default:
                break;
        }
        view.addObject(MSG_TYPE, "2");
        return view;
    }


    /**
     * 重置支付密码（第二步）
     *
     * @param messageCode 短信验证
     * @return model
     */
    @ResponseBody
    @RequestMapping(value = "resetPaymentPwd/phone.do", method = RequestMethod.POST)
    public Map<String, String> resetPayPwdSecondStepPhone(String messageCode) {
        Map<String, String> map = Maps.newHashMap();
        log.info("重置支付密码 （短信验证码） : 参数:{}", messageCode);
        String url = "/personalCenter/forgotpwd/payPwd/reset-pwd1";
        //验证短信验证码
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            boolean result = securityConfigService.verifyCode(sessionVo.getLoginNo(), SendCodeContentEnum.UPDATE_PAY_PWD_MSG, messageCode);
            log.info("重置支付密码（短信验证码）:验证短信验证码结果:{}", result);
            if (!result) {
                map.put(ERROR_MESSAGE, "验证码校验失败，请重新获取输入");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "验证码验证失败，请重新获取输入!");
            }
            map.put("msg", SUCCESS);
            map.put(RESULT_URL, url);
        } catch (Exception e) {
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("重置支付密码（手机验证）异常：", e);
        }
        log.info("重置支付密码（手机验证）结果：{}", map);
        return map;
    }

    /**
     * 密保问题验证 重置支付密码
     *
     * @param moduleList
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "resetPaymentPwd/checkQuestion.do", method = RequestMethod.POST)
    public Map<String, String> checkQuestionToresetPwd(SysModuleList moduleList) {

        Map<String, String> map = Maps.newHashMap();
        String resultUrl = "personalCenter/forgotpwd/payPwd/reset-pwd1";
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            for (FixPhoneNoApplyForm form : moduleList.getModules()) {
                //验证用戶安全問題
                boolean result2 = securityConfigService.verifyUserAnswer(sessionVo.getUserNo(), Long.valueOf(form.getQuestionNo()),
                        PwdEncryptUtil.secrueqaEncrypt(form.getAnswer()));
                log.info("修改密保问题（第二步）:密保编号为{}的验证结果:{}", form.getQuestionNo(), result2);
                if (!result2) {
                    map.put(ERROR_MESSAGE, "安全保护问题答案不正确，请重新输入");
                    throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "安全保护问题答案不正确，请重新输入!");
                }
            }
            map.put(RESULT_URL, resultUrl);
            map.put("msg", SUCCESS);
        } catch (Exception e) {
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("通过密保问题修改支付密码异常：{}", e);
        }
        log.info("修改支付密码密保问题验证结果：{}", map);
        return map;
    }

    /**
     * 重置支付密码（第三步）
     *
     * @param payPwd      第一次密码
     * @param payPwdAgain 第二次密码
     * @return model
     */
    @ResponseBody
    @RequestMapping(value = "resetPaymentPwd/three.do", method = RequestMethod.POST)
    public Map<String, String> resetPayPwdThreeStep(String payPwd, String payPwdAgain) {
        log.info("重置支付密码（第三步）: 参数:{}，{}", payPwd, payPwdAgain);
        Map<String, String> map = Maps.newHashMap();
        String url = "/personalCenter/forgotpwd/payPwd/reset_pwd-success";
        try {
            //基本校验通过后，发起更新密码操作
            String firstPayPwd = PwdEncryptUtil.paymentEncrypt(payPwd);
            String secondPayPwd = PwdEncryptUtil.paymentEncrypt(payPwdAgain);
            if (!firstPayPwd.equals(secondPayPwd)) {
                log.info("重置支付密码（第三步）: 两次密码不一致:{}，{}", payPwd, payPwdAgain);
                map.put(ERROR_MESSAGE, "两次支付密码不一致，请重新输入");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "两次支付密码不一致");
            }
            //支付密码更新
            boolean result = securityConfigService.resetPaymentPwd(SessionUtil.getSessionVo().getUserNo(), secondPayPwd, "system");
            log.info("重置支付密码（第三步）: 重置支付密码结果:{}", result);
            if (!result) {
                map.put(ERROR_MESSAGE, "两次支付密码不一致");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "重置支付密码失败，请联系管理员");
            }
            map.put(RESULT_URL, url);
            map.put("msg", SUCCESS);
        } catch (Exception e) {
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("重置支付密码（第三步）异常：{}", e);
        }
        return map;
    }

}
