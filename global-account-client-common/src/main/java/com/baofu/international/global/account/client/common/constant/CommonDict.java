package com.baofu.international.global.account.client.common.constant;

/**
 * 公共常量
 * <p>
 * User: by 蒋文哲 Date: 2017/3/30 Version: 1.0
 * </p>
 */
public final class CommonDict {
    /**
     * 分隔符|
     */
    public static final String SEPARATOR = "\\|";
    /**
     * 分隔符
     */
    public static final String SPLIT_FLAG = "|$|";
    /**
     * 亚马逊
     */
    public static final String AMAZON = "AMAZON";
    /**
     * 字符集 UTF-8
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * 字符集 GBK
     */
    public static final String GBK = "GBK";
    /**
     * 字符集 GBK
     */
    public static final String EXIST = "Y";
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
     * 冒号分隔符
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
     * 登录密码加密秘钥
     */
    public static final String LOGIN_WORD_KEY = "global.baofoo.com";

    /**
     * 支付密码加密秘钥
     */
    public static final String PAY_WORD_KEY = "global.payment.baofoo.com";

    /**
     * 安全问题答案加密秘钥
     */
    public static final String SECRUEQA_WORD_KEY = "global.secrueqa.baofoo.com";

    /**
     * 用户信息Session key
     */
    public static final String SESSION_KEY = "SESSION_KEY";

    /**
     * 注册验证码session标识
     */
    public static final String SESSION_REGISTER_KEY = "REGISTER_SESSION";

    /**
     * 验证码session标识
     */
    public static final String SESSION_VALIDATE_KEY = "VALIDATE_SESSION";

    /**
     * 页面验证码字符串
     */
    public static final String VERIFICATION_CODE_KEY = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 注册验证码session标识
     */
    public static final String SESSION_LOGIN_KEY = "LOGIN_SESSION";

    /**
     * 重置登录密码验证码session标识
     */
    public static final String RESRT_LOGIN_PASS_SESSION = "RESRT_LOGIN_PWD_SESSION";

    /**
     * 银行卡号加密秘钥
     */
    public static final String BANK_CARD_KEY = "global.bank.card.baofoo.com";

    /**
     * 正则表达式邮箱
     */
    public static final String REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    /**
     * 正则表达式手机号
     */
    public static final String REGEX_PHONE = "^0?(13|14|15|17|18)[0-9]{9}$";

    /**
     * 图片文件大小
     */
    public static final Long IMAGE_FILE_SIZE = 2 * 1024 * 1024L;

    /**
     * 保存登录密码session标识-个人
     */
    public static final String PRO_LOGIN_PW = "RERroLoginPwd";

    /**
     * 保存登录密码session标识-企业
     */
    public static final String ORG_LOGIN_PW = "REOrgLoginPwd";

    /**
     * 添加银行卡类型-企业法人和个人
     */
    public static final String BANKCARD_ADD_PERSONAL_BANKCARD = "addPersonBankCard";

    /**
     * 添加银行卡类型-企业对公
     */
    public static final String BANKCARD_ADD_COMPANY_BANKCARD = "addCompanyBankCard";

    /**
     * 银行卡解除绑定
     */
    public static final String BANKCARD_REMOVE_BANKCARD = "deleteBankCard";

    /**
     * 提现银行卡验证码已经发送标识
     */
    public static final String BANKCARD_MESSAGE_CODE_SEND_YES = "bankCardMessageCodeHaveAlreadySend";

    /**
     * 错误信息返回页面
     */
    public static final String ERROR_MESSAGE_RETURN = "errorMessage";

    /**
     * 错误返回结果
     */
    public static final String ERROR_FLAG_RETURN = "error";

    /**
     * 成功返回结果
     */
    public static final String SUCCESS_FLAG_RETURN = "success";

    /**
     * 用户当前登录已经失效
     */
    public static final String MESSAGE_CODE_EXPIRE_OR_UNDEFINE = "尚未获取验证码或验证码已经失效，请点击获取验证码";

    /**
     * 加密des
     */
    public static final String DES = "DES";

    /**
     * 加密MD5
     */
    public static final String MD5 = "MD5";

    /**
     * 换行符
     */
    public static final String WRAP = "[\r\n]";

    /**
     * 银行卡尾号短信和邮件发送替换符
     */
    public static final String BANK_CARD_TAIL_NO_REPLACE = "#bankCardTailNo#";


    private CommonDict() {

    }

}
