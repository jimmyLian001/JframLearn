package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.web.models.ValidateCodeInfo;
import com.baofu.international.global.account.client.web.util.ValidateCode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description:验证码
 * <p/>
 *
 * @author : liy on 2017/11/10
 * @version : 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("common/")
public class ValidateCodeController {

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param key      key
     */
    @RequestMapping(value = "getVerify.do", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key) {
        try {
            if (StringUtils.isBlank(key)) {
                key = CommonDict.SESSION_VALIDATE_KEY;
            }
            ValidateCodeInfo codeInfo = new ValidateCodeInfo();
            codeInfo.setSession(key);
            codeInfo.setImgHeight(NumberDict.THIRTY_SIX);
            codeInfo.setImgWidth(NumberDict.ONE_HUNDRED + NumberDict.TEN);
            codeInfo.setLineNum(NumberDict.ZERO);
            codeInfo.setRandStr(CommonDict.VERIFICATION_CODE_KEY);
            codeInfo.setRandNum(NumberDict.FOUR);
            codeInfo.setFontSize(NumberDict.TWENTY_FIVE);
            log.info("生成页面验证码,{}", codeInfo);
            ValidateCode.getVerify(request, response, codeInfo);
        } catch (Exception e) {
            log.error("生成页面验证码异常", e);
        }
    }

    /**
     * 验证码异常
     *
     * @param request      请求
     * @param validateCode 验证码
     * @return 结果对象
     */
    @ResponseBody
    @RequestMapping(value = "checkValidateCode.do", method = RequestMethod.POST)
    public Boolean checkValidateCode(HttpServletRequest request, @RequestParam("validateCode") String validateCode,
                                     @RequestParam("key") String key) {

        log.info("[验证码验证] 页面验证码：{}", validateCode.replaceAll("[\r\n]", ""));
        try {
            String sessionCode = (String) request.getSession().getAttribute(key);
            if (!validateCode.equalsIgnoreCase(sessionCode)) {
                log.info("[验证码验证] 验证码不一致，页面验证码：{},Session验证码：{}", validateCode, sessionCode);
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("验证码验证异常", e);
            return Boolean.FALSE;
        }
    }
}
