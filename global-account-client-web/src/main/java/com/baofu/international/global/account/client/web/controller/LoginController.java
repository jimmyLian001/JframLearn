package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.*;
import com.baofu.international.global.account.client.common.util.BeanCopyUtils;
import com.baofu.international.global.account.client.service.LoginService;
import com.baofu.international.global.account.client.web.models.BaseResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.IpUtil;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录
 * <p>
 * 1.跳转首页
 * 2.跳转登录页面
 * 3.查询session中登录次数
 * 4.用户登录
 * 5.用户退出
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
@Controller
@RequestMapping("login/")
public class LoginController {

    private static final String FLAG = "flag";

    /**
     * 登录服务
     */
    @Autowired
    private LoginService loginService;

    /**
     * 跳转首页
     *
     * @return 返回页面
     */
    @RequestMapping(value = "toIndexPage.do", method = RequestMethod.GET)
    public String toIndexPage() {
        SessionVo sessionVo = SessionUtil.getSessionVo();
        if (sessionVo != null) {
            log.info("call 用户已经登录，跳转到用户数首页。");
            return "redirect:/account/index.do";
        }
        log.info("call 用户未登录，跳转到登录界面。");
        return "login/login";
    }


    /**
     * 跳转登录页面
     *
     * @return 返回页面
     */
    @RequestMapping(value = "index.do")
    public String loginController() {
        return "login/login";
    }

    /**
     * 查询session中登录次数
     *
     * @param request 请求
     * @return 登录次数
     */
    @ResponseBody
    @RequestMapping(value = "checkFlag.do", method = RequestMethod.POST)
    public Integer checkValidateCode(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(FLAG);
        return object == null ? Integer.valueOf(NumberStrDict.ONE) : (Integer) object;
    }

    /**
     * 用户登录
     *
     * @param loginNo      登录号
     * @param loginPwd     密码
     * @param validateCode 图片验证码
     * @param request      请求信息
     * @return 返回页面
     */
    @ResponseBody
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public BaseResult loginController(@RequestParam(value = RequestDict.LOGIN_NO) String loginNo,
                                      @RequestParam(value = "loginPwd") String loginPwd,
                                      @RequestParam(value = "validateCode", required = false) String validateCode,
                                      HttpServletRequest request) {
        BaseResult baseResult;
        Object object = request.getSession().getAttribute(FLAG);
        Integer flag = object == null ? Integer.valueOf(NumberStrDict.ONE) : (Integer) object;
        try {
            log.info("[用户登录] 登录名:{},验证码:{}", loginNo.replaceAll(CommonDict.WRAP, ""),
                    validateCode.replaceAll(CommonDict.WRAP, ""));
            String sessionCode = (String) request.getSession().getAttribute(CommonDict.SESSION_LOGIN_KEY);
            if (flag > NumberDict.THREE && !validateCode.equalsIgnoreCase(sessionCode)) {
                log.info("[用户登录] 验证码不一致，页面验证码：{},Session验证码：{}", validateCode, sessionCode);
                return BaseResult.setFail("验证码错误");
            }

            UserLoginRespDTO respDTO = loginService.loginService(loginNo, PwdEncryptUtil.encrypt(loginPwd),
                    IpUtil.getIpAddr(request));
            SessionVo sessionVo = BeanCopyUtils.objectConvert(respDTO, SessionVo.class);
            request.getSession().setAttribute(CommonDict.SESSION_KEY, sessionVo);
            //用户类型保存至Session中，session key为userType
            request.getSession().setAttribute(SessionKeyDict.USER_TYPE, sessionVo.getUserType());
            request.getSession().removeAttribute(FLAG);
            baseResult = BaseResult.setSuccessExt(flag);
        } catch (Exception e) {
            log.error("用户登录异常，错误信息：", e);
            flag = flag + NumberDict.ONE;
            request.getSession().setAttribute(FLAG, flag);
            baseResult = BaseResult.setFailExt(ExceptionUtils.getErrorMsg(e), flag);
        }

        log.info("[用户登录] 返回信息:{}", baseResult);
        return baseResult;
    }

    /**
     * 用户退出
     *
     * @param request 请求信息
     * @return 返回页面
     */
    @RequestMapping(value = "loginOut.do", method = RequestMethod.GET)
    public ModelAndView loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute(CommonDict.SESSION_KEY);
        request.getSession().invalidate();
        return new ModelAndView("redirect:index.do");
    }
}
