package com.baofu.international.global.account.client.web.controller.register;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.RegisterFacade;
import com.baofu.international.global.account.core.facade.model.AgentRegisterReqDto;
import com.baofu.international.global.account.core.facade.model.CreateUserReqDto;
import com.baofu.international.global.account.core.facade.model.RegisterUserReqDto;
import com.baofu.international.global.account.core.facade.model.SysSecrueqaInfoRespDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * description:注册管理
 * <p>
 * 1.跳转个人注册页面
 * 2.个人用户注册-发送短信验证码
 * 3.个人用户注册-校验短信验证码
 * 4.个人用户注册-查询系统安全问题
 * 5.个人用户注册-创建用户
 * 6.跳转企业注册页面
 * 7.企业用户注册-发送短信验证码
 * 8.企业用户注册-校验短信验证码
 * 9.企业用户注册-查询系统安全问题
 * 10.企业用户注册-创建用户
 * </p>
 *
 * @author : liy
 * @version : 1.0.0
 *          date : 2017/11/5
 */
@Slf4j
@Controller
@RequestMapping("/register/")
public class RegisterController {

    /**
     * 注册接口
     */
    @Autowired
    private RegisterFacade registerFacade;


    /**
     * 跳转个人注册页面
     *
     * @param agentNo 代理商户号
     * @param request 请求参数
     * @return 页面
     */
    @RequestMapping(value = "registerPersonalPage.do/{agentNo}", method = RequestMethod.GET)
    public String registerPersonalPage(@PathVariable(value = "agentNo", required = false) Long agentNo, HttpServletRequest request) {
        this.setSession(request, agentNo);
        log.info("跳转个人注册页面");
        return "register/registerPersonal";
    }

    /**
     * 个人用户注册-发送短信验证码
     *
     * @param loginNo loginNo
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxSendSmsCaptcha.do", method = RequestMethod.POST)
    public AjaxResult ajaxSendSmsCaptcha(@RequestParam(RequestDict.LOGIN_NO) String loginNo) {

        try {
            log.info("个人用户注册,发送短信验证码,手机号:{}", loginNo.replaceAll("[\r\n]", ""));
            Result<Boolean> result = registerFacade.sendSmsCaptcha(loginNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("个人用户注册,发送短信验证码,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult<>(NumberDict.ZERO, result.getErrorMsg());
            }
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("个人用户注册,发送短信验证码,异常", e);
            return new AjaxResult<>(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 个人用户注册-校验短信验证码
     *
     * @param dto 请求参数
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxCheckSmsCaptcha.do", method = RequestMethod.POST)
    public AjaxResult ajaxCheckSmsCaptcha(RegisterUserReqDto dto) {

        try {
            this.getPwd(dto);
            log.info("个人用户注册,校验短信验证码,参数:{}", dto);
            Result<Boolean> result = registerFacade.checkSmsCaptcha(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("个人用户注册,校验短信验证码,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult(NumberDict.ZERO, result.getErrorMsg());
            }
            this.setLoginPwdSession(CommonDict.PRO_LOGIN_PW.concat(dto.getLoginNo()), dto.getLoginPwd());
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("个人用户注册,校验短信验证码,异常", e);
            return new AjaxResult(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 个人用户注册-查询系统安全问题
     *
     * @param loginNo loginNo
     * @param mm      ModelMap
     * @return 结果集
     */
    @RequestMapping(value = "personalQuestionPage.do", method = RequestMethod.POST)
    public String personalQuestionPage(@RequestParam(RequestDict.LOGIN_NO) String loginNo, ModelMap mm) {

        try {
            log.info("个人用户注册,跳转系统安全问题页面...");
            String loginPwd = this.getLoginPwdSession(CommonDict.PRO_LOGIN_PW, loginNo);
            if (StringUtils.isEmpty(loginNo) || StringUtils.isEmpty(loginPwd)) {
                log.info("个人用户注册,跳转系统安全问题页面，数据异常,用户名:{},密码:{}", loginNo, loginPwd);
                return PageUrlDict.ERROR_PAGE;
            }
            log.info("个人用户注册,查询系统安全问题...");
            Result<List<SysSecrueqaInfoRespDto>> result = registerFacade.
                    selectSysSecrueqaInfoList(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("个人用户注册,查询系统安全问题,结果:{}", result);
            if (!result.isSuccess()) {
                return PageUrlDict.ERROR_PAGE;
            }
            mm.addAttribute("resultList", result.getResult());
            mm.addAttribute(RequestDict.LOGIN_NO, loginNo);
            mm.addAttribute("loginPwd", loginPwd);
        } catch (Exception e) {
            log.error("个人用户注册,查询系统安全问题,异常", e);
            return PageUrlDict.ERROR_PAGE;
        }

        return "register/personalQuestion";
    }

    /**
     * 个人用户注册-创建用户
     *
     * @param dto 用户信息
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxCreatePersonalUser.do", method = RequestMethod.POST)
    public AjaxResult ajaxCreatePersonalUser(CreateUserReqDto dto, HttpServletRequest request) {

        try {
            dto.setAnswerOne(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerOne()));
            dto.setAnswerTwo(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerTwo()));
            dto.setAnswerThree(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerThree()));
            log.info("个人用户注册,创建用户,参数:{}", dto);
            Result<Long> result = registerFacade.createPersonalUser(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("个人用户注册,创建用户,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult<>(NumberDict.ZERO, result.getErrorMsg());
            }
            this.setLoginInfo(dto.getLoginNo(), result.getResult(), UserTypeEnum.PERSONAL.getType());
            this.sendMqMessage(request, result.getResult(), NumberDict.ONE);
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("个人用户注册,创建用户,异常", e);
            return new AjaxResult(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 跳转企业注册页面
     *
     * @param agentNo 代理商户号
     * @param request 请求参数
     * @return 页面
     */
    @RequestMapping(value = "registerOrgPage.do/{agentNo}", method = RequestMethod.GET)
    public String registerOrgPage(@PathVariable(value = "agentNo", required = false) Long agentNo, HttpServletRequest request) {
        this.setSession(request, agentNo);
        log.info("跳转企业注册页面");
        return "register/registerOrg";
    }

    /**
     * 企业用户注册-发送邮件验证码
     *
     * @param loginNo loginNo
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxSendEmailCaptcha.do", method = RequestMethod.POST)
    public AjaxResult ajaxSendEmailCaptcha(@RequestParam(RequestDict.LOGIN_NO) String loginNo) {

        try {
            log.info("企业用户注册,发送邮件验证码,邮箱:{}", loginNo.replaceAll("[\r\n]", ""));
            Result<Boolean> result = registerFacade.
                    sendEmailCaptcha(loginNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("企业用户注册,发送邮件验证码,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult<>(NumberDict.ZERO, result.getErrorMsg());
            }
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("企业用户注册,发送邮件验证码,异常", e);
            return new AjaxResult(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 企业用户注册-校验邮件验证码
     *
     * @param dto 请求参数
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxCheckEmailCaptcha.do", method = RequestMethod.POST)
    public AjaxResult ajaxCheckEmailCaptcha(RegisterUserReqDto dto) {

        try {
            this.getPwd(dto);
            log.info("企业用户注册,校验邮件验证码,参数:{}", dto);
            Result<Boolean> result = registerFacade.checkEmailCaptcha(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("企业用户注册,校验邮件验证码,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult<>(NumberDict.ZERO, result.getErrorMsg());
            }
            this.setLoginPwdSession(CommonDict.ORG_LOGIN_PW.concat(dto.getLoginNo()), dto.getLoginPwd());
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("企业用户注册,校验邮件验证码,异常", e);
            return new AjaxResult(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 企业用户注册-查询系统安全问题
     *
     * @param loginNo loginNo
     * @param mm      ModelMap
     * @return 结果集
     */
    @RequestMapping(value = "orgQuestionPage.do", method = RequestMethod.POST)
    public String orgQuestionPage(@RequestParam(RequestDict.LOGIN_NO) String loginNo, ModelMap mm) {

        try {
            log.info("企业用户注册,跳转系统安全问题页面...");
            String loginPwd = this.getLoginPwdSession(CommonDict.ORG_LOGIN_PW, loginNo);
            if (StringUtils.isEmpty(loginNo) || StringUtils.isEmpty(loginPwd)) {
                log.info("企业用户注册,跳转系统安全问题页面,数据异常,用户名:{},密码:{}", loginNo, loginPwd);
                return PageUrlDict.ERROR_PAGE;
            }
            Result<List<SysSecrueqaInfoRespDto>> result = registerFacade.
                    selectSysSecrueqaInfoList(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("企业用户注册,查询系统安全问题,结果:{}", result);
            if (!result.isSuccess()) {
                return PageUrlDict.ERROR_PAGE;
            }
            mm.addAttribute(RequestDict.LOGIN_NO, loginNo);
            mm.addAttribute("loginPwd", loginPwd);
            mm.addAttribute("resultList", result.getResult());
        } catch (Exception e) {
            log.error("企业用户注册,查询系统安全问题,异常", e);
            return PageUrlDict.ERROR_PAGE;
        }
        return "register/orgQuestion";
    }

    /**
     * 企业用户注册-创建用户
     *
     * @param dto 用户信息
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "ajaxCreateOrgUser.do", method = RequestMethod.POST)
    public AjaxResult ajaxCreateOrgUser(CreateUserReqDto dto, HttpServletRequest request) {

        try {
            this.getAnswer(dto);
            log.info("企业用户注册,创建用户,参数:{}", dto);
            Result<Long> result = registerFacade.createOrgUser(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("企业用户注册,创建用户,结果:{}", result);
            if (!result.isSuccess()) {
                return new AjaxResult(NumberDict.ZERO, result.getErrorMsg());
            }
            this.setLoginInfo(dto.getLoginNo(), result.getResult(), UserTypeEnum.ORG.getType());
            this.sendMqMessage(request, result.getResult(), NumberDict.TWO);
            return new AjaxResult(NumberDict.ONE);
        } catch (Exception e) {
            log.error("企业用户注册,创建用户,异常", e);
            return new AjaxResult(NumberDict.ZERO, ExceptionUtils.getErrorMsg(e));
        }
    }

    /**
     * 加密登录密码
     *
     * @param dto 登录密码
     */
    private void getPwd(RegisterUserReqDto dto) {

        dto.setLoginPwd(PwdEncryptUtil.encrypt(dto.getLoginPwd()));
        dto.setLoginPwdAgain(PwdEncryptUtil.encrypt(dto.getLoginPwdAgain()));
    }

    /**
     * 加密安全问题答案
     *
     * @param dto 安全问题答案
     */
    private void getAnswer(CreateUserReqDto dto) {

        dto.setAnswerOne(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerOne()));
        dto.setAnswerTwo(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerTwo()));
        dto.setAnswerThree(PwdEncryptUtil.secrueqaEncrypt(dto.getAnswerThree()));
    }

    /**
     * 保存登录密码
     *
     * @param key      key
     * @param loginPwd 登录密码
     */
    private void setLoginPwdSession(String key, String loginPwd) {

        SessionUtil.setSessionValue(key, loginPwd);
    }

    /**
     * 获取保存登录密码
     *
     * @param key     key
     * @param loginNo 登录名
     * @return 登录密码
     */
    private String getLoginPwdSession(String key, String loginNo) {

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(loginNo)) {
            return null;
        }
        return SessionUtil.<String>getSessionValue(key.concat(loginNo));
    }

    /**
     * 登录用户
     *
     * @param loginNo  登录账户号
     * @param userNo   用户号
     * @param userType 用户类型
     */
    private void setLoginInfo(String loginNo, long userNo, int userType) {

        SessionVo sessionVo = new SessionVo();
        sessionVo.setLoginNo(loginNo);
        sessionVo.setUserNo(userNo);
        sessionVo.setUserType(userType);
        SessionUtil.setSessionValue(CommonDict.SESSION_KEY, sessionVo);
    }

    /**
     * 调用Mq发送方法
     *
     * @param request  请求参数
     * @param userNo   用户号
     * @param userType 用户注册类型
     */
    private void sendMqMessage(HttpServletRequest request, Long userNo, Integer userType) {
        Long agentNo = (Long) request.getSession().getAttribute("agentNo");
        if (!ObjectUtils.isEmpty(agentNo)) {
            log.info("代理商户号:{}", agentNo);
            AgentRegisterReqDto registerReqDto = new AgentRegisterReqDto();
            registerReqDto.setAgentNo(agentNo);
            registerReqDto.setUserNo(userNo);
            registerReqDto.setUserType(userType);
            registerFacade.sendMqMessageToAgent(registerReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        }
    }

    /**
     * 将angentNo 插入缓存
     *
     * @param request 请求参数
     * @param agentNo 代理商号
     */
    private void setSession(HttpServletRequest request, Long agentNo) {
        if (!ObjectUtils.isEmpty(agentNo) && agentNo != NumberDict.ONE && agentNo != NumberDict.TWO) {
            log.info("代理商户号：{}", agentNo);
            HttpSession session = request.getSession();
            session.setAttribute("agentNo", agentNo);
        }
    }
}


