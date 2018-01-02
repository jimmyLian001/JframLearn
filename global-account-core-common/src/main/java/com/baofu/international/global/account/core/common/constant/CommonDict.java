package com.baofu.international.global.account.core.common.constant;

/**
 * 公共常量
 * <p>
 * User: by 蒋文哲 Date: 2017/3/30 Version: 1.0
 * </p>
 *
 * @author : 蒋文哲
 */
public final class CommonDict {

    /**
     * 分隔符|
     */
    public static final String SEPARATOR = "\\|";

    /**
     * 换行符
     */
    public static final char NEW_LINE = '\n';
    /**
     * 分隔符
     */
    public static final String SPLIT_FLAG = "$|$";

    /**
     * 字符集 UTF-8
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * 字符集 GBK
     */
    public static final String GBK = "GBK";
    /**
     * 日期根式 yyyyMMddHHmmss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyyMMddHHmmss";
    /**
     * 日期根式 yyyyMMddHHmmssSS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SS = "yyyyMMddHHmmssSS";
    /**
     * 日期根式 yyMMddHHmmssSS
     */
    public static final String YY_MM_DD_HH_MM_SS_SS = "yyMMddHHmmssSS";
    /**
     * 日期根式 yyMMddHHmmssSS
     */
    public static final String YY_MM_DD_HH_MM_SS = "yyMMddHHmmss";
    /**
     * 日期根式 yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_BLANK_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期根式 yyyyMMdd
     */
    public static final String YYYY_MM_DD = "yyyyMMdd";
    /**
     * 日期根式 HHmmss
     */
    public static final String HH_MM_SS = "HHmmss";
    /**
     * 日期根式 ssSS
     */
    public static final String SS_SS = "ssSS ";
    /**
     * 日期根式 MMDDHHMM
     */
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    /**
     * 缓存文件前缀
     */
    public static final String LOCAL_CACHE_FILE = "TMP_";
    /**
     * txt文件后缀
     */
    public static final String LOCAL_FILE_SUFFIX = ".txt";

    /**
     * CSV文件后缀
     */
    public static final String LOCAL_FILE_SUFFIX_CSV = ".CSV";
    /**
     * 渠道前缀 平安
     */
    public static final int PAB_CHANNEL_ID = 12018;
    /**
     * 渠道前缀 中行
     */
    public static final int BOC_CHANNEL_ID = 12009;
    /**
     * 冒号分隔符
     */
    public static final String SPLIT_FLAG_COLON = ":";
    /**
     * 逗号分隔符
     */
    public static final String SPLIT_FLAG_COMMA = ",";
    /**
     * 开始小时
     */
    public static final String START_HOUR_MINUTE_SECONDS = " 00:00:00";
    /**
     * 截止时间
     */
    public static final String END_HOUR_MINUTE_SECONDS = " 14:00:00";
    /**
     * 0.5小时
     */
    public static final Long TEN_MINUTES = 600L;
    /**
     * 1小时时间毫秒
     */
    public static final Long ONE_HOUR = 3600000L;
    /**
     * 2小时时间毫秒
     */
    public static final Long TWO_HOUR = 7200000L;

    /**
     * 空字符串
     */
    public static final String EMPTY_STR = "";
    /**
     * 空字符串
     */
    public static final String UNDERLINE_STR = "_";

    /**
     * 2分钟
     */
    public static final Long TWO_MINUTES = 120000L;

    /**
     * 10分钟(毫秒)
     */
    public static final Long TEN_MINUTES_MS = 600000L;

    /**
     * 替换验证码标识
     */
    public static final String SMS_CODE = "#code#";

    /**
     * 注册账户发送信息
     */
    public static final String REGISTER_INFO = "验证码: #code#，您正在注册宝付国际跨境收款用户，请勿将验证码告诉他人！客服热线：";

    /**
     * 正则表达式邮箱
     */
    public static final String REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    /**
     * 正则表达式手机号
     */
    public static final String REGEX_PHONE = "^0?(13|14|15|17|18)[0-9]{9}$";

    /**
     * 1分钟
     */
    public static final long ONE_MINUTES = 60000L;

    /**
     * 注册发送验证码标识
     */
    public static final String REGISTER_KEY = "RE";

    /**
     * 注册已发送验证码标识
     */
    public static final String CHECK_REGISTER_KEY = "CRE";

    /**
     * 注册邮件主题
     */
    public static final String SEND_REGISTER_EMAIL_SUBJECT = "宝付国际跨境收款-注册用户";


    private CommonDict() {

    }

}
