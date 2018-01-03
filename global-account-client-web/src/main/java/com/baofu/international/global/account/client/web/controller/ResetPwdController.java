package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.constant.SessionKeyDict;
import com.baofu.international.global.account.client.common.enums.ResultEnum;
import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.common.enums.SystemEnums;
import com.baofu.international.global.account.client.common.util.DataDesensUtils;
import com.baofu.international.global.account.client.service.LoginService;
import com.baofu.international.global.account.client.service.SecurityConfigService;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.ResultVo;
import com.baofu.international.global.account.client.web.models.SecurityReqVo;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 1、重置登录密码（第一步）
 * 2、
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@Slf4j
@Controller
@RequestMapping("resetPwd")
public class ResetPwdController {

    /**
     * 密保问题标志
     */
    private static final String QUESTION_FLG = "questionFlg";

    /**
     * 短信验证码标志
     */
    private static final String VERIFY_FLAG = "verifyFlag";

    /**
     * 安全中心服务service
     */
    @Autowired
    private SecurityConfigService securityConfigService;

    /**
     * 登录信息相关服务
     */
    @Autowired
    private LoginService loginService;

    /**
     * 重置登录密码（第一步）
     *
     * @return model
     */
    @RequestMapping(value = "/resetPassword/first", method = RequestMethod.GET)
    public String resetFirstStep() {

        return PageUrlDict.RESET_LOGIN_KEY_PAGE;
    }

    /**
     * 重置登录密码（第二步）
     *
     * @param loginNo    登录号
     * @param verifyCode 验证码
     * @return model
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword/second", method = RequestMethod.POST)
    public AjaxResult<String> resetSecondStep(HttpServletRequest request, String loginNo, String verifyCode) {

        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            log.info("重置登录密码（第二步）: 参数:{}，{}", loginNo, verifyCode);
            //获取用户信息
            UserLoginRespDTO userLoginRespDTO = loginService.queryLoginInfo(loginNo);
            if (userLoginRespDTO.getUserNo() == null) {
                throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "用户不存在，请确认输入信息是否正确");
            }
            //验证验证码
            String sessionCode = (String) WebUtils.getSessionAttribute(request, CommonDict.RESRT_LOGIN_PASS_SESSION);
            if (!verifyCode.equalsIgnoreCase(sessionCode)) {
                log.info("重置登录密码（第二步）: 验证验证码失败:{}", sessionCode);
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "抱歉，验证码不对！");
            }
            //把用户号放入到Session中
            WebUtils.setSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY, loginNo);
            WebUtils.setSessionAttribute(request, SessionKeyDict.RESET_LOGIN_TYPE_KEY, userLoginRespDTO.getLoginType());
            ajaxResult.setCode(0);
            ajaxResult.setMessage("成功");
        } catch (Exception e) {
            log.error("重置登录密码（第二步）异常：", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("重置登录密码（第二步）返回结果：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 跳转到选择密码页面
     *
     * @return 返回页面和参数信息
     */
    @RequestMapping(value = "/resetPassword/resetSelect", method = RequestMethod.GET)
    public ModelAndView resetSelect(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("personalCenter/forgotpwd/reset-loginPwd-select");
        String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
        //判断在Session是否存在，如果不存在直接跳转到登陆页面
        if (StringUtils.isBlank(loginNo)) {
            log.warn("用户充值密码Session已过期，重新跳转到重置密码初始页");
            modelAndView = new ModelAndView(PageUrlDict.RESET_LOGIN_KEY_PAGE);
        } else {
            Integer loginType = (Integer) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_TYPE_KEY);
            //如果是手机号码脱敏
            if (loginType == 2) {
                loginNo = DataDesensUtils.dealSensMobile(loginNo);
            }
            modelAndView.addObject(RequestDict.LOGIN_NO, loginNo);
            modelAndView.addObject("loginType", loginType);
        }
        return modelAndView;
    }


    /**
     * 重置登录密码（第三步选择短信或安保问题）
     *
     * @param request 登录号
     * @return model
     */
    @RequestMapping("/resetPassword/{editType}")
    public ModelAndView directForReset(@PathVariable("editType") String editType, HttpServletRequest request) {

        String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
        //判断在Session是否存在，如果不存在直接跳转到登陆页面
        if (StringUtils.isBlank(loginNo)) {
            log.warn("用户充值密码Session已过期，重新跳转到重置密码初始页");
            return new ModelAndView(PageUrlDict.RESET_LOGIN_KEY_PAGE);
        }
        log.info("重置登录密码（第三步）: 参数:{}", loginNo);

        //获取用户信息
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(loginNo);
        //默认使用短信或邮箱验证
        ModelAndView model = new ModelAndView("/personalCenter/forgotpwd/reset-loginPwd-sms");
        //使用密保问题验证
        if ("2".equalsIgnoreCase(editType)) {
            model.setViewName("/personalCenter/forgotpwd/reset-loginPwd-answer");
            //获取用戶安全問題
            List<UserAnswerRespDTO> result2 = securityConfigService.getUserAnswer(userInfoBo.getUserNo());
            model.addObject("questions", result2);
        }
        model.addObject(RequestDict.LOGIN_NO, loginNo);
        model.addObject("userInfo", userInfoBo);
        return model;
    }

    /**
     * 重置登录密码（第三步密保问题）
     *
     * @param request       请求信息
     * @param securityReqVo 参数
     * @return model
     */
    @RequestMapping(value = "/resetPassword/validateAnswer.do", method = RequestMethod.POST)
    public ModelAndView resetThreeStep(SecurityReqVo securityReqVo, HttpServletRequest request) {
        log.info("重置登录密码（第三步密保问题）: 参数:{}", securityReqVo);
        ModelAndView model = new ModelAndView("/personalCenter/forgotpwd/reset-loginPwd");

        String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
        //判断在Session是否存在，如果不存在直接跳转到登陆页面
        if (StringUtils.isBlank(loginNo)) {
            log.warn("用户充值密码Session已过期，重新跳转到重置密码初始页");
            return new ModelAndView(PageUrlDict.RESET_LOGIN_KEY_PAGE);
        }

        //获取用户信息
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(loginNo);
        log.info("重置登录密码（第三步密保问题）:获取用户:{}", userInfoBo);
        if (userInfoBo.getUserNo() == null) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_THREE_3);
        }
        //验证用戶安全問題
        for (String questionNo : securityReqVo.getParams().keySet()) {
            boolean result2 = securityConfigService.verifyUserAnswer(userInfoBo.getUserNo(), Long.valueOf(questionNo),
                                                                     PwdEncryptUtil
                                                                         .secrueqaEncrypt(securityReqVo.getParams().get(questionNo)));
            log.info("重置登录密码（第三步密保问题）:验证安全答案结果:{}", result2);
            if (!result2) {
                return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_THREE_2);
            }
        }
        WebUtils.setSessionAttribute(request, QUESTION_FLG, Boolean.TRUE);
        return model;
    }

    /**
     * 重置登录密码（第四步）
     *
     * @param firstPwd  第一次密码
     * @param secondPwd 第二次密码
     * @return model
     */
    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.POST)
    public ModelAndView resetFourStep(String firstPwd, String secondPwd, HttpServletRequest request) {

        String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
        //判断在Session是否存在，如果不存在直接跳转到登陆页面
        if (StringUtils.isBlank(loginNo)) {
            log.warn("用户充值密码Session已过期，重新跳转到重置密码初始页");
            return new ModelAndView(PageUrlDict.RESET_LOGIN_KEY_PAGE);
        }
        log.info("重置登录密码（第四步）: 参数:{}，{},{}", loginNo, firstPwd, secondPwd);
        ModelAndView model = new ModelAndView("/personalCenter/forgotpwd/reset-loginPwd-result");
        Boolean verifyFlg = SessionUtil.getSessionValue(VERIFY_FLAG);
        Boolean questionFlg = SessionUtil.getSessionValue(QUESTION_FLG);

        //校验参数是否为空
        if (StringUtils.isBlank(firstPwd) || StringUtils.isBlank(secondPwd)
                || (!(verifyFlg != null && verifyFlg && questionFlg == null)
                && !(questionFlg != null && verifyFlg == null && questionFlg))) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_PARAMS_VALIDATE);
        }
        //获取用户信息
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(loginNo);
        log.info("重置登录密码（第四步）:获取用户:{}", userInfoBo);
        if (userInfoBo.getUserNo() == null) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_THREE_1);
        }
        //基本校验通过后，发起更新密码操作
        boolean result = securityConfigService.resetLoginPwd(userInfoBo.getUserNo(),
                PwdEncryptUtil.encrypt(firstPwd), PwdEncryptUtil.encrypt(secondPwd), SystemEnums.SYSTEM_NAME.getCode());
        log.info("重置登录密码（第四步）:重置登录密码结果:{}", result);
        if (!result) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_FOUR_0);
        }
        SessionUtil.removeSessionValue(QUESTION_FLG);
        SessionUtil.removeSessionValue(VERIFY_FLAG);
        return model;
    }


    /**
     * 重置登录密码（第三步短信验证）
     *
     * @param request    请求信息
     * @param verifyCode 验证码
     * @return model
     */
    @RequestMapping("/resetPassword/validateCode.do")
    public ModelAndView resetThreeStep(HttpServletRequest request, String verifyCode) {

        String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
        //判断在Session是否存在，如果不存在直接跳转到登陆页面
        if (StringUtils.isBlank(loginNo)) {
            log.warn("用户充值密码Session已过期，重新跳转到重置密码初始页");
            return new ModelAndView(PageUrlDict.RESET_LOGIN_KEY_PAGE);
        }
        log.info("重置登录密码（第三步短信验证）: 参数:{},{}", loginNo, verifyCode);
        ModelAndView model = new ModelAndView("/personalCenter/forgotpwd/reset-loginPwd");
        model.addObject(RequestDict.LOGIN_NO, loginNo);
        //获取用户信息
        UserInfoBo userInfoBo = securityConfigService.getUserInfo(loginNo);
        log.info("重置登录密码（第三步短信验证）:获取用户:{}", userInfoBo);
        if (userInfoBo.getUserNo() == null) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_THREE_1);
        }
        //验证短信验证码
        boolean result = securityConfigService.verifyCode(loginNo, SendCodeContentEnum.UPDATE_LOGIN_PWD_MSG, verifyCode);
        log.info("重置登录密码（第三步短信验证）:验证短信验证码结果:{}", result);
        if (!result) {
            return addResultParams(model, ResultEnum.RESET_LOGIN_PWD_THREE_0);
        }
        SessionUtil.setSessionValue(VERIFY_FLAG, true);
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
     * 发送验证码
     *
     * @param request 登录号
     * @return boolean 是否发送成功
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword/sendCode", method = RequestMethod.POST)
    public AjaxResult<String> sendCode(HttpServletRequest request) {

        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            //获取当前用户名
            String loginNo = (String) WebUtils.getSessionAttribute(request, SessionKeyDict.RESET_LOGIN_NO_KEY);
            log.info("发送验证码: 参数:{}", loginNo);
            SendCodeRespDto sendCodeRespDto = securityConfigService.autoSendCode(loginNo,
                    SendCodeContentEnum.UPDATE_LOGIN_PWD_MSG.getBusiType());
            log.info("发送验证码结果{}", sendCodeRespDto);
            if (sendCodeRespDto != null && sendCodeRespDto.getSendFlag()) {
                ajaxResult.setCode(0);
                ajaxResult.setMessage("成功");
            } else {
                ajaxResult.setCode(1);
                ajaxResult.setMessage(sendCodeRespDto.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("重置密码发送验证码异常：", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("重置密码发送验证码返回结果：{}", ajaxResult);
        return ajaxResult;
    }

}
