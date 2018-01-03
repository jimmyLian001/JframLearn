package com.baofu.international.global.account.client.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏处理
 * <p>
 * 1、手机号脱敏：前三位，后四位，其他隐藏<例子：138******1234>
 * 2、[姓名脱敏]隐藏姓<例子：*彦祖>
 * 3、[身份证号脱敏]  前六位，后四位（最后一位也隐藏），其他隐藏。共计18位或者15位。<例子：330211*******576>
 * 4、 [银行卡号脱敏（14位及以上）] 前六位，后四位，其他用星号隐藏<例子：6222600**********1234>
 * 5、字符串脱敏处理替换为*号
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataDesensUtils {

    /**
     * 手机号脱敏：前三位，后四位，其他隐藏<例子：138******1234>
     *
     * @param str 脱敏前字符串信息
     * @return 返回脱敏之后信息
     */
    public static String dealSensMobile(String str) {

        return dealSensitiveData(3, 4, str);
    }

    /**
     * [姓名脱敏]隐藏姓<例子：*彦祖>
     *
     * @param str 脱敏前字符串信息
     * @return 返回脱敏之后信息
     */
    public static String dealSensAccName(String str) {
        if (StringUtils.isNotEmpty(str)) {
            if (str.length() <= 3) {
                return "*" + str.substring(1, str.length());
            } else if (str.length() >= 4 && str.length() <= 5) {
                return "**" + str.substring(2, str.length());
            } else {
                return dealSensitiveData(0, 4, str);
            }
        } else {
            return "";
        }
    }


    /**
     * [身份证号脱敏]  前六位，后四位（最后一位也隐藏），其他隐藏。共计18位或者15位。<例子：330211*******576>
     *
     * @param str 脱敏前字符串信息
     * @return 返回脱敏之后信息
     */
    public static String dealSensIdCardNo(String str) {

        str = dealSensitiveData(6, 4, str);
        if (str.length() < 1) {
            return "";
        }
        return str.substring(0, str.length() - 1) + "*";
    }

    /**
     * [银行卡号脱敏（14位及以上）] 前六位，后四位，其他用星号隐藏<例子：6222600**********1234>
     * [银行卡号脱敏（14位以下）] 显示后4位，隐藏后5~8位<9位卡号例子：6****1234><11位卡号例子：666****1234>
     *
     * @param str 脱敏前字符串信息
     * @return 返回脱敏之后信息
     */
    public static String dealSensBankCardNo(String str) {
        if (StringUtils.isNotEmpty(str)) {
            if (str.length() >= 14) {
                return dealSensitiveData(6, 4, str);
            } else if (str.length() >= 9) {
                return str.substring(0, str.length() - 8) + "****" + str.substring(str.length() - 4, str.length());
            } else if (str.length() >= 5) {
                return dealSensitiveData(0, 4, str);
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    /**
     * 字符串脱敏处理替换为*号
     *
     * @param preNum 前面保留几位不脱敏		>=0
     * @param sufNum 后面保留几位不脱敏		>=0
     * @param str    要脱敏的字符串
     * @return 返回脱敏之后信息
     */
    public static String dealSensitiveData(int preNum, int sufNum, String str) {

        if (StringUtils.isBlank(str)) {
            return "";
        }
        int len = str.length();
        int replaceNum = len - preNum - sufNum;
        if (replaceNum <= 0) {
            return str;
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < replaceNum; i++) {
            s.append("*");
        }

        return str.substring(0, preNum) + s.toString() + str.substring(len - sufNum, len);
    }
}
