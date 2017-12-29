package com.baofu.cbpayservice.common.util;

import com.baofu.cbpayservice.common.constants.Constants;

import java.util.regex.PatternSyntaxException;

/**
 * 字符串处理工具类
 * <p>
 * 1、字符串左补0
 * </p>
 * User: wanght Date: 2017/03/08 ProjectName: cbpayservice-common Version: 1.0
 */

public class StringUtils {

    /**
     * 字符串左补0
     *
     * @param length 总长度
     * @param str    原字符串
     * @return 左补零后结果
     */
    public static String leftAppendZero(int length, String str) {
        if (str == null) {
            str = "";
        }
        int strLen = str.length();
        while (strLen < length) {
            StringBuffer sb = new StringBuffer();
            sb.append("0").append(str);

            str = sb.toString();
            strLen = str.length();
        }

        return str;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 原字符串
     * @return true|false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 小数位数format
     *
     * @param value 原始值
     * @param digit 小数点后的位数
     */
    public static String decimalFormat(String value, int digit) {
        if (value != null && !"".equals(value)) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            if (value.contains(".")) {
                String intStr = value.substring(0, value.indexOf("."));
                if (intStr == null || "".equals(intStr)) {
                    sb.insert(0, "0");
                }
                int length = sb.toString().substring(sb.toString().lastIndexOf(".") + 1).length();
                if (length < digit) {
                    for (int i = 0; i < (digit - length); i++) {
                        sb.append("0");
                    }
                } else {
                    return sb.toString().substring(0, sb.toString().lastIndexOf(".") + digit + 1);
                }
            } else {
                sb.append(".");
                for (int j = 0; j < digit; j++) {
                    sb.append("0");
                }
            }
            return sb.toString();
        } else {
            return "0.00";
        }
    }

    /**
     * 根据分隔符校验字符串长度
     *
     * @param str    原字符串
     * @param strReg 分割符
     * @param length 分割后的每个字符串要求的最大长度
     * @return true|false
     * dxy 2017.08.28
     */
    public static boolean lengthCheck(String str, String strReg, int length) {
        String[] strArray = str.split(strReg);
        for (String s : strArray) {
            if (s.length() > length) {
                return false;
            }
        }
        return true;
    }

    /**
     * 过滤特殊字符串
     *
     * @param str 目标字符串
     * @return 结果
     * @throws PatternSyntaxException 异常
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        if (str == null) {
            return "";
        }
        return str.replaceAll(Constants.STRING_REG, Constants.EMPTY_STRING);
    }


}

