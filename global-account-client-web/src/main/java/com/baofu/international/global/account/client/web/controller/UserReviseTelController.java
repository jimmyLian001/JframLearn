package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.util.CheckUtil;
import com.baofu.international.global.account.client.service.UserTelReviseService;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.client.web.convert.UserReviseTelConvert;
import com.baofu.international.global.account.client.web.models.*;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.FixPhoneNoApplyForm;
import com.baofu.international.global.account.client.web.models.MobileMsgVo;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.SysModuleList;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.google.common.collect.Maps;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户注册手机号维护控制
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-client Version:1.0
 */
@Slf4j
@Controller
@RequestMapping("userTel/")
public class UserReviseTelController {

    /**
     * 用户注册手机号维护业务处理
     */
    @Autowired
    private UserTelReviseService userTelReviseService;
    /**
     * 返回首页
     */
    private static final String REDIRECT_URL = "redirect:../login/index.do";

    private static final String RESULT_URL = "resultUrl";

    private static final String PHONE_NUMBER = "phoneNumber";

    private static final String SERVICE_TYPE = "serviceType";

    private static final String SUCCESS = "success";

    private static final String USER_NO = "userNo";

    /**
     * 用户进入手机号绑定修改申请，跳转去选择方式页面
     *
     * @return
     */
    @RequestMapping(value = "resetPhoneNo/apply.do", method = RequestMethod.GET)
    public ModelAndView resetPhoneNoApply() {
        log.info("进入申请");
        ModelAndView mv = buildModelView("/personalCenter/reviseTel/modify-tel-num");
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            UserInfoBo result = userTelReviseService.getUserInfo(sessionVo.getLoginNo());
            String phoneNumberSec = sessionVo.getLoginNo();
            if (sessionVo.getUserType() == NumberDict.TWO) {
                phoneNumberSec = result.getMobileNo();
            }
            mv.addObject(PHONE_NUMBER, phoneNumberSec);
            mv.addObject(USER_NO, sessionVo.getUserNo());
            mv.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        } catch (Exception e) {
            mv.setViewName(REDIRECT_URL);
            mv.addObject(CommonDict.ERROR_MESSAGE_RETURN, "用户个人信息获取失败");
            mv.addObject("msg", CommonDict.ERROR_FLAG_RETURN);
            log.info("用户注册手机号修改查询用户信息失败:{}", e);
        }
        return mv;
    }

    /**
     * 选择修改绑定手机的方式
     *
     * @param rechargeType
     * @return
     */
    @RequestMapping(value = "resetPhoneNo/second.do", method = RequestMethod.GET)
    public ModelAndView resetPayPwdTwo(String rechargeType) {
        ModelAndView view = buildModelView(REDIRECT_URL);
        SessionVo sessionVo = SessionUtil.getSessionVo();
        if (sessionVo == null) {
            return view;
        }
        UserInfoBo result = userTelReviseService.getUserInfo(sessionVo.getLoginNo());
        String phoneNumberSec = sessionVo.getLoginNo();
        if (sessionVo.getUserType() == NumberDict.TWO) {
            phoneNumberSec = result.getMobileNo();
        }
        //验证方式
        switch (rechargeType) {
            case "phone":
                //通过手机号验证
                view.setViewName("personalCenter/reviseTel/reset-num-tel");
                break;
            case "question":
                view.setViewName("personalCenter/reviseTel/reset-num-question");
                Result<List<UserAnswerRespDTO>> result2 = userTelReviseService.findUserAnswer(sessionVo.getUserNo(), null);
                view.addObject("questions", result2 != null && !result2.getResult().isEmpty() ? result2.getResult() : null);
                break;
            default:
                break;
        }
        view.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        view.addObject("oldMobile", phoneNumberSec);
        view.addObject(SERVICE_TYPE, "updateTel");
        return view;
    }

    /**
     * 获取短信验证码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMessageCode.do", method = RequestMethod.POST)
    public AjaxResult getMessageCode(HttpServletRequest request) {
        log.info("进入验证码获取···");
        AjaxResult ajaxResult = new AjaxResult(NumberDict.ONE);
        try {
            String afterFixPhoneNumber = request.getParameter("afterFixPhoneNumber");
            String serviceType = request.getParameter(SERVICE_TYPE);
            SessionVo sessionVo = SessionUtil.getSessionVo();
            String userNo = sessionVo.getUserNo().toString();
            Result<Boolean> result = userTelReviseService.sendMessageCode(userNo, serviceType, afterFixPhoneNumber);
            if (!result.isSuccess() || !result.getResult()) {
                ajaxResult.setMessage(result.getErrorMsg());
                ajaxResult.setCode(NumberDict.ZERO);
                return ajaxResult;
            }
        } catch (Exception e) {
            log.error("发送验证码异常：{}", e);
            ajaxResult.setMessage(ajaxResult.getMessage() == null ? "发送验证码异常,请联系管理员！" : ajaxResult.getMessage());
            ajaxResult.setCode(NumberDict.ZERO);
        }
        return ajaxResult;
    }

    @ResponseBody
    @RequestMapping(value = "resetPhoneNo/checkTelCode.do", method = RequestMethod.POST)
    public Map<String, String> checkCurrentBindingCode(MobileMsgVo msgVo) {
        log.info("修改手机号验证当前绑定手机申请：{}", msgVo);
        Map<String, String> map = Maps.newHashMap();
        String url = "/personalCenter/reviseTel/reset-num2";
        //验证短信验证码
        String mobileNo = msgVo.getCurrentPhoneNumber();
        String messageCode = msgVo.getMessageCode();
        String serviceType = msgVo.getServiceType();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            UserInfoBo userInfoBo = userTelReviseService.getUserInfo(sessionVo.getLoginNo());
            String phoneNumberSec = userInfoBo.getMobileNo();
            if (!StringUtils.equals(mobileNo, phoneNumberSec)) {
                log.info("手机号输入有误，请输入当前用户绑定的手机号！");
                map.put(CommonDict.ERROR_MESSAGE_RETURN, "手机号输入有误，请输入当前用户绑定的手机号！");
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            String message = serviceType.concat(phoneNumberSec);
            Result<Boolean> result = userTelReviseService.checkMessageCode(message, messageCode);
            log.info("修改绑定手机（短信验证码）:验证短信验证码结果:{}", result);
            if (!result.isSuccess() || !result.getResult()) {
                map.put(CommonDict.ERROR_MESSAGE_RETURN, result.getErrorMsg());
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            map.put(RESULT_URL, url);
            map.put("msg", SUCCESS);
            map.put(SERVICE_TYPE, msgVo.getServiceType());
        } catch (Exception e) {
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("修改绑定手机异常：{}", e);
        }
        log.info("修改绑定手机（手机验证）结果：{}", map);
        return map;
    }


    /**
     * 设置绑定手机页面
     *
     * @return
     */
    @RequestMapping(value = "bindPhoneNoToApply.do", method = RequestMethod.GET)
    public ModelAndView bindPhoneNoToApply() {

        ModelAndView mv = buildModelView("personalCenter/reviseTel/bind-mobile-phone");
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            if (sessionVo == null) {
                mv.setViewName(REDIRECT_URL);
                return mv;
            }
            String userNo = sessionVo.getUserNo().toString();
            mv.addObject(USER_NO, userNo);
            mv.addObject("msg", SUCCESS);
            mv.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            mv.addObject(SERVICE_TYPE, "setTel");
        } catch (Exception e) {
            log.error("用户设置绑定手机页面跳转异常:{}", e);
            mv.addObject("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        return mv;
    }

    /**
     * 绑定手机
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "bindPhoneNoApply.do", method = RequestMethod.POST)
    public Map<String, String> bindPhoneNoApply(MobileMsgVo msgVo) {
        Map<String, String> map = Maps.newHashMap();
        log.info("绑定手机号请求受理:{}", msgVo);
        String resultUrl = "personalCenter/reviseTel/num-success";
        if ("setTel".equals(msgVo.getServiceType())) {
            resultUrl = "personalCenter/reviseTel/reset_num-success";
        }
        SessionVo sessionVo = SessionUtil.getSessionVo();
        String phoneNumber = msgVo.getAfterFixPhoneNumber();
        String messageCode = msgVo.getMessageCode();
        try {
            Result<Boolean> checkResult = userTelReviseService.checkMessageCode(msgVo.getServiceType() + phoneNumber, messageCode);
            //验证码认证
            if (!checkResult.isSuccess() || !checkResult.getResult()) {
                map.put(CommonDict.ERROR_MESSAGE_RETURN, checkResult.getErrorMsg());
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            UserInfoBo userInfoBo = userTelReviseService.getUserInfo(sessionVo.getLoginNo());
            Result<Boolean> result = userTelReviseService.fixRegisterPhoneNo(
                    UserReviseTelConvert.fixPhoneNoApplyDtoConvert(phoneNumber, userInfoBo.getMobileNo(), sessionVo));
            log.info("用户绑定手机返回结果：{}", result);
            if (!result.isSuccess() || !result.getResult()) {
                log.info("用户绑定手机号请求失败，修改失败");
                map.put(CommonDict.ERROR_MESSAGE_RETURN, result.getErrorMsg());
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            map.put(RESULT_URL, resultUrl);
            map.put("msg", SUCCESS);
            map.put(PHONE_NUMBER, phoneNumber);
        } catch (Exception e) {
            map.put(CommonDict.ERROR_MESSAGE_RETURN, "绑定手机号号异常，请联系管理员！");
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("用户绑定手机号失败:{}", e);
        }
        return map;
    }

    /**
     * 修改绑定手机 （密保问题答案)
     *
     * @param moduleList 验证申请
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "resetPhoneNo/question.do", method = RequestMethod.POST)
    public Map<String, String> resetMobilePhoneByquestion(SysModuleList moduleList) {
        SessionVo sessionVo = SessionUtil.getSessionVo();
        Map<String, String> map = Maps.newHashMap();
        String url = "personalCenter/reviseTel/reset-num2";
        log.info("修改绑定手机(密保问题)请求参数：{}", moduleList);
        try {
            UserInfoBo userInfoBo = userTelReviseService.getUserInfo(sessionVo.getLoginNo());
            if (!StringUtils.equals(userInfoBo.getMobileNo(), moduleList.getCurrentPhoneNumber())) {
                map.put(CommonDict.ERROR_MESSAGE_RETURN, "请输入当前用户绑定的手机号");
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            if (moduleList.getModules() == null || moduleList.getModules().size() < 3) {
                map.put(CommonDict.ERROR_MESSAGE_RETURN, "请输入安全保护问题答案");
                map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                return map;
            }
            for (FixPhoneNoApplyForm form : moduleList.getModules()) {
                //验证用戶安全問題
                boolean result2 = userTelReviseService.verifyUserAnswer(sessionVo.getUserNo(), Long.valueOf(form.getQuestionNo()),
                        PwdEncryptUtil.secrueqaEncrypt(form.getAnswer()));
                log.info("修改密保问题（第二步）:密保编号为{}的验证结果:{}", form.getQuestionNo(), result2);
                if (!result2) {
                    map.put(CommonDict.ERROR_MESSAGE_RETURN, "安全保护问题答案不正确，请重新输入");
                    map.put("msg", CommonDict.ERROR_FLAG_RETURN);
                    return map;
                }
            }
            map.put(RESULT_URL, url);
            map.put("msg", SUCCESS);
        } catch (Exception e) {
            map.put(CommonDict.ERROR_MESSAGE_RETURN, "通过密保问题修改绑定手机异常");
            map.put("msg", CommonDict.ERROR_FLAG_RETURN);
            log.error("通过密保问题修改绑定手机异常：{}", e);
        }
        log.info("修改绑定手机（密保问题）验证结果：{}", map);
        return map;
    }

    /**
     * 绑定手机结果
     *
     * @param resultUrl
     * @return
     */
    @RequestMapping(value = "resultPageJumps.do", method = RequestMethod.GET)
    public ModelAndView bindPhoneNoApplyResult(String resultUrl, HttpServletRequest request) {
        log.info("操作结果跳转页面:{}", resultUrl);
        ModelAndView mv = buildModelView(resultUrl);
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            String phoneNumber = "undefined".equals(request.getParameter(PHONE_NUMBER)) ? null : request.getParameter(PHONE_NUMBER);
            String loginNo = sessionVo.getLoginNo();
            if (StringUtils.isNotEmpty(phoneNumber) && !StringUtils.equals(phoneNumber, sessionVo.getLoginNo()) && CheckUtil.isPhone(loginNo)) {
                loginNo = phoneNumber;
            }
            sessionVo.setLoginNo(loginNo);
            request.getSession().setAttribute(CommonDict.SESSION_KEY, sessionVo);
            mv.addObject("msg", SUCCESS);
        } catch (Exception e) {
            log.info("操作结果跳转页面异常:{}", e);
            mv.addObject("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "verifyPhoneNo.do", method = RequestMethod.POST)
    public Boolean verifyPhoneNo(String afterFixPhoneNumber) {
        log.info("修改绑定手机判断校验是否已经绑定：{}", afterFixPhoneNumber);
        return userTelReviseService.getUserLoginInfoByLoginNo(afterFixPhoneNumber);
    }

    /**
     * 生成页面modelAndView
     *
     * @param url 参数
     * @return ModelAndView 页面model
     */
    private ModelAndView buildModelView(String url) {
        ModelAndView model = new ModelAndView(StringUtils.isNotBlank(url) ? url : "");
        SessionVo sessionVo = SessionUtil.getSessionVo();
        if (sessionVo == null) {
            model.setViewName(REDIRECT_URL);
            return model;
        }
        model.addObject(USER_NO, sessionVo.getUserNo());
        model.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        return model;
    }
}
