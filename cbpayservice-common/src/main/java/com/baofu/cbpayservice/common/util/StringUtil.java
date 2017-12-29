package com.baofu.cbpayservice.common.util;

import java.math.BigDecimal;

/**
 * 字符串处理工具类
 * <p>
 * 1、字符串左补0
 * </p>
 * User: wanght Date: 2017/03/08 ProjectName: cbpayservice-common Version: 1.0
 */

public class StringUtil {

    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

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
     * Function Name: objToString
     *
     * @param obj
     * @return 返回String数据
     * description: 将Object转成string类型
     * Modification History: add by feng_jiang 2017/07/07
     */
    public static String objToString(Object obj) {
        String str = "";
        if (obj != null) {
            str = obj + "";
        }
        return str;
    }

    /**
     * Function Name: objToBigDecimal
     *
     * @param obj
     * @return 返回BigDecimal数据
     * description: 将Object转成BigDecimal类型
     * Modification History: add by feng_jiang 2017/07/07
     */
    public static BigDecimal objToBigDecimal(Object obj) {
        BigDecimal bigDec = null;
        if (obj != null) {
            bigDec = (BigDecimal) obj;
        }
        return bigDec;
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
     * 小写数字金额转换成大写
     *
     * @param money 金额
     * @return 大小金额
     */
    public static String toCapital(double money) {
        if (money < 0 || money > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(money * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }
}

