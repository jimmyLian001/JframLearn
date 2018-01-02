package com.baofu.international.global.account.core.common.constant;

/**
 * 常量
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
public final class Constants {

    private Constants() {

    }

    /**
     * UTF8编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 版本号 1.0.0
     */
    public static final String VERSION_1_0_0 = "1.0.0";

    /**
     * 请求数据类型:JSON
     */
    public static final String DATA_TYPE_JSON = "JSON";

    /**
     * 超时时间
     */
    public static final Long TIME_OUT = 31536000L;

    /**
     * DFS文件超时时间
     */
    public static final Long DFS_TIME_OUT = 30 * 24 * 60 * 60 * 1000L;

    /**
     * 批量处理大小
     */
    public static final Integer BATCH_DEAL_NUM = 500;

    /**
     * 用户提现key
     */
    public static final String GLOBAL_ACCOUNT_WITHDRAW_CASH = "GLOBAL:ACCOUNT:WITHDRAW:CASH:";

    /**
     * 最小金额
     */
    public static final String MIN_AMT = "0.01";

    /**
     * 限额
     */
    public static final String MAX_QUATO = "99999999999999999.99";

    /**
     * redis 连接符
     */
    public static final String REDIS_SYMBOL = ":";

    /**
     * 字符串0
     */
    public static final String ZERO = "0";

    /**
     * 结汇excel非内容行
     */
    public static final Integer WITHDRAW_NO_CONTENT_LINE = 4;

    /**
     * YYYY-MM-DD HH:MM:SS
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]"
            + "|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]"
            + "|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    /**
     * YYYYMMDDHHMMSS
     */
    public static final String YYYYMMDDHHMMSS = "^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])"
            + "|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]"
            + "|[2468][048]|[13579][26])00)-02-29)([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$";

    /**
     * YYYY-MM-DD
     */
    public static final String YYYY_MM_DD = "^((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)"
            + "\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]"
            + "|0[48]))|(2000))-0?2-29))$";

    /**
     * YYYYMMDD
     */
    public static final String YYYYMMDD = "^((((19|20)\\d{2})(0?(1|[3-9])|1[012])(0?[1-9]|[12]\\d|30))|(((19|20)"
            + "\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]"
            + "|0[48]))|(2000))-0?2-29))$";

    /**
     * 纯数字正则表达式
     */
    public static final String NUMBER_REG = "[0-9]+";

    /**
     * 分割符
     */
    public static final String SPLIT_MARK = "|";

    /**
     * 斜綫
     */
    public static final String SLASH = "/";

    /**
     * 版本号
     */
    public static final String VERSION_1_1 = "1.1";

    /**
     * Excel支持上传最大条数
     */
    public static final Integer EXCEL_CONTENT_MAX = 500000;

    /**
     * 世界主要交易币种
     */
    public static final String CURRENCY = "CNY|USD|JPY|EUR|GBP|DEM|CHF|FRF|CAD|AUD|HKD|ATS|FIM|BEF|IEP|ITL|LUF|NLG|" +
            "PTE|ESP|IDR|MYR|NZD|PHP|SUR|SGD|KRW|THB";

    /**
     * 日元简称
     */
    public static final String JPY_CURRENCY = "JPY";

    /**
     * 身份证正则
     */
    public static final String ID_REGX = "(\\d{17}[0-9|X|x])";

    /**
     * 组织机构代码正则：不大于9位的字母数字组合，允许倒数第二位为连接符，现允许18位
     */
    public static final String OC_REGX = "([0-9a-zA-Z]{1,18})|([0-9a-zA-Z]{1,8}-[0-9a-zA-Z])";

    /**
     * excel商品名称、价格、数量分隔符
     */
    public static final String SPLIT_DOLLAR_DOLLAR = "\\$\\|\\$";

    /**
     * 逗号分隔符
     */
    public static final String SPLIT_SYMBOL = ",";

    /**
     * 商品数量正则表达式
     */
    public static final String AMOUNT_REG = "^[1-9][0-9]*";

    /**
     * 金额正则表达式
     */
    public static final String AMT_REG = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$";

    /**
     * 金额正则表达式
     */
    public static final String SETTLE_AMT_REG = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,3})?))$";

    /**
     * excel 总条数分割单位值
     */
    public static final Integer EXCEL_MAX_COUNT = 1000;

    /**
     * 批量插入最大条数
     */
    public static final int INSERT_MAX = 200;

    /**
     * des加解密密钥
     */
    public static final String CARD_DES_KEY = "global.bank.card.baofoo.com";

    /**
     * 常量字符串
     */
    public static final String GLOBAL = "GLOBAL";

    /**
     * 收款账户银行名称
     */
    public static final String PAYEE_BANK_NAME = "上海平安银行";

    /**
     * 收款账户国家
     */
    public static final String PAYEE_COUNTRY = "USA";

    /**
     * 收款账户渠道
     */
    public static final String PAYEE_CHANNEL = "WYRE";

    /**
     * 收款账户渠道结汇申请文件上传方式
     */
    public static final String SETTLE_APPLY_FILE_TYPE = "FTP";

    /**
     * 用户自己回调分发地址url
     */
    public static final String PAYEE_NOTIFY_URL = "/common/distribute";

    /**
     * ZIP 格式
     */
    public static final String ZIP = ".ZIP";
}
