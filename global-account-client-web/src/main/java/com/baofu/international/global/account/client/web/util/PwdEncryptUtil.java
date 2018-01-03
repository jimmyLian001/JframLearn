package com.baofu.international.global.account.client.web.util;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录密码加密
 * <p>
 * 1.登录密码加密
 * 2.支付密码加密
 * 3.安全问题答案加密
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
public class PwdEncryptUtil {

    private PwdEncryptUtil() {
    }

    /**
     * 登录密码加密
     *
     * @param pwd 登录密码
     * @return 加密字符串
     */
    public static String encrypt(String pwd) {
        String encryptStr = null;
        try {
            if (pwd != null) {
                String desEncrypt = SecurityUtil.desEncrypt(pwd, CommonDict.LOGIN_WORD_KEY);
                encryptStr = SecurityUtil.md5DesEncrypt(desEncrypt);
            }
        } catch (Exception e) {
            log.error("call 登录密码加密异常", e);
        }
        return encryptStr;
    }

    /**
     * 支付密码加密
     *
     * @param pwd 支付密码
     * @return 加密字符串
     */
    public static String paymentEncrypt(String pwd) {
        String encryptStr = null;
        try {
            if (pwd != null) {
                String pwdMD5 = SecurityUtil.md5DesEncrypt(pwd);
                encryptStr = SecurityUtil.desEncrypt(pwdMD5, CommonDict.PAY_WORD_KEY);
            }
        } catch (Exception e) {
            log.error("call 支付密码加密异常", e);
        }
        return encryptStr;
    }

    /**
     * 安全问题答案加密
     *
     * @param answer 安全问题答案
     * @return 加密字符串
     */
    public static String secrueqaEncrypt(String answer) {
        String encryptStr = null;
        try {
            if (answer != null) {
                encryptStr = SecurityUtil.desEncrypt(answer, CommonDict.SECRUEQA_WORD_KEY);
            }
        } catch (Exception e) {
            log.error("call 安全问题答案加密异常", e);
        }
        return encryptStr;
    }
}
