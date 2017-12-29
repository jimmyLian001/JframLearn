package com.baofu.cbpayservice.common.constants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zhangw on 2016/4/25.
 */
public class Constants {

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
     * des加解密密钥
     */
    public static final String DES_PASSWD = "encrypt.baofoo.com";

    /**
     * 金额正则表达式
     */
    public static final String AMT_REG = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$";

    /**
     * 金额正则表达式
     */
    public static final String SETTLE_AMT_REG = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,3})?))$";

    /**
     * 纯数字正则表达式
     */
    public static final String NUMBER_REG = "[0-9]+";
    /**
     * 商品数量正则表达式
     */
    public static final String AMOUNT_REG = "^[1-9][0-9]*";

    /**
     * 世界主要交易币种
     */
    public static final String CURRENCY = "CNY|USD|JPY|EUR|GBP|DEM|CHF|FRF|CAD|AUD|HKD|ATS|FIM|BEF|IEP|ITL|LUF|NLG|" +
            "PTE|ESP|IDR|MYR|NZD|PHP|SUR|SGD|KRW|THB";

    /**
     * 人民币简称
     */
    public static final String CNY_CURRENCY = "CNY";

    /**
     * 日元简称
     */
    public static final String JPY_CURRENCY = "JPY";

    /**
     * 日元简称
     */
    public static final String KRW_CURRENCY = "KRW";

    /**
     * 分割符
     */
    public static final String SPLIT_MARK = "|";

    /**
     * 最小金额
     */
    public static final String MONEY_MIN = "0.001";

    /**
     * 字符串0
     */
    public static final String ZERO = "0";

    /**
     * 通知商户返回结果
     */
    public static final String NOTIFY_RESULT = "OK";

    /**
     * des加解密密钥
     */
    public static final String CARD_DES_PASSWD = "card.encrypt.baofoo.com";

    /**
     * b2c-网银支付
     */
    public static final String B2C_PAY_TYPE = "B2C";

    /**
     * quick-快捷支付
     */
    public static final String QUICK_PAY_TYPE = "QUICK";
    /**
     * auth-认证支付
     */
    public static final String AUTH_PAY_TYPE = "AUTH";
    /**
     * wxs-微信扫码
     */
    public static final String WXS_PAY_TYPE = "WEIXIN";

    /**
     * alis-支付宝扫码
     */
    public static final String ALIS_PAY_TYPE = "ALIPAY";

    /**
     * excel商品名称、价格、数量分隔符
     */
    public static final String SPLIT_SYMBOL = ";";

    /**
     * excel商品名称、价格、数量分隔符
     */
    public static final String SPLIT_DOLLAR_DOLLAR = "\\$\\|\\$";

    /**
     * 版本号
     */
    public static final String VERSION_1_1 = "1.1";

    /**
     * 批量插入最大条数
     */
    public static final int INSERT_MAX = 200;

    /**
     * 是否报关或是否收费是否实名
     */
    public static final String PUSH_FLAG = "Y";

    /**
     * PC:proxy_customs 代理报关
     */
    public static final String PROXY_GOODS_TRADE = "PC";

    /**
     * pcb：proxy_cross_border 代理跨境
     */
    public static final String PROXY_SERVICE_TRADE = "PCB";

    /**
     * PC:proxy_customs api代理报关
     */
    public static final String API_PROXY_CUSTOMS = "APIPC";

    /**
     * 跨境结算产品
     */
    public static final List<Integer> CBPAY_PRODUCT_LIST = Lists.newArrayList(1016);

    /**
     * 跨境人民币结算产品功能(宝付订单)
     */
    public static final Integer CB_PAY_ORDER = 10160001;

    /**
     * 创建汇款订单时锁住商户的key
     */
    public static final String CBPAY_CREATE_REMITTANCE_KEY = "CBPAY:CREATE:REMITTANCE:";

    /**
     * 创建币种结算账户时锁住商户的key
     */
    public static final String CBPAY_CREATE_SETTLE_BANK_ACC = "CBPAY:CREATE:SETTLE_BANK_ACC:";

    /**
     * 汇款订单后台审核时锁住商户的key
     */
    public static final String CBPAY_AUDIT_REMITTANCE_KEY = "CBPAY:AUDIT:REMITTANCE:";

    /**
     * 风控批次结束标志key   购付汇
     */
    public static final String CBPAYORDER_RISKCONTROL_END_FLAG = "CBPAYORDER:RISKCONTROL:END:FLAG";


    /**
     * 风控批次结束标志  结汇
     */
    public static final String CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE = "CBPAYORDER:RISKCONTROL:END:FLAG:SETTLE";


    /**
     * 已经风控的批次集合  购付汇
     */
    public static final String CBPAYORDER_RISKCONTROL_ALREADYLIST = "CBPAYORDER:RISKCONTROL:ALREADYLIST";

    /**
     * 已经风控的批次集合  结汇
     */
    public static final String CBPAYORDER_RISKCONTROL_ALREADYLIST_SETTLE = "CBPAYORDER:RISKCONTROL:ALREADYLIST:SETTLE";

    /**
     * 超时时间
     */
    public static final Long TIME_OUT = 31536000L;

    /**
     * 文件校验处理进度超时时间
     */
    public static final Long FILE_CHECK_SCHEDULE_TIME_OUT = 259200000L;

    /**
     * DFS文件超时时间
     */
    public static final Long DFS_TIME_OUT = 1209600000L;

    /**
     * 批量处理大小
     */
    public static final Integer BATCH_DEAL_NUM = 500;

    /**
     * 限额
     */
    public static final String MAX_QUATO = "99999999999999999.99";

    /**
     * 匹配非空格字符
     */
    public static final String NO_SPACE = "^[\\S]*$";

    /**
     * YYYY-MM-DD
     */
    public static final String YYYY_MM_DD = "^((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)"
            + "\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]"
            + "|0[48]))|(2000))-0?2-29))$";

    /**
     * YYYY-MM-DD HH:MM:SS
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]"
            + "|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]"
            + "|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    /**
     * YYYYMMDD
     */
    public static final String YYYYMMDD = "^(([0-9]{4}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))" +
            "|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]" +
            "|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";

    /**
     * YYYYMMDDHHMMSS
     */
    public static final String YYYYMMDDHHMMSS = "^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])"
            + "|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]"
            + "|[2468][048]|[13579][26])00)0229)([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$";

    /**
     * 时间格式：YYYY/MM/DD
     */
    public static final String YYYY_MM_DD_SLANT = "^(([0-9]{4}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))" +
            "|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]" +
            "|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29)";

    /**
     * YYYY/MM/DD HH:MM:SS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SLANT = "^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]"
            + "|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]"
            + "|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)\\s([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    /**
     * 身份证正则
     */
    public static final String ID_REGX = "(\\d{17}[0-9|X|x])";

    /**
     * 组织机构代码正则：不大于9位的字母数字组合，允许倒数第二位为连接符，现允许18位
     */
    public static final String OC_REGX = "([0-9a-zA-Z]{1,18})|([0-9a-zA-Z]{1,8}-[0-9a-zA-Z])";

    /**
     * excel 总条数分割单位值
     */
    public static final Integer EXCEL_MAX_COUNT = 1000;

    /**
     * excel非内容行
     */
    public static final Integer NO_CONTENT_LINE = 4;

    /**
     * 结汇excel非内容行
     */
    public static final Integer SETTLE_NO_CONTENT_LINE = 4;

    /**
     * Excel支持上传最大条数
     */
    public static final Integer EXCEL_CONTENT_MAX = 500000;

    /**
     * EMAIL 主题
     */
    public static final String EMAIL_SUBJECT = "宝付订单资料${date}汇款订单明细，批次号：${batchNo}";

    /**
     * EMAIL 内容
     */
    public static final String EMAIL_CONTENT = "吴老师好，\n         ${date}，汇款批次号：${batchNo}的订单明细见附件，请查收。\n";

    /**
     * 匹配完成发送邮件给清算人员的标题
     */
    public static final String NOTICE_CM_TITLE = "宝付网络科技（上海）有限公司-外汇汇入资金确认";

    /**
     * 匹配完成发送邮件给清算人员的内容
     */
    public static final String NOTICE_CM_CONTENT = "Dear 清算工作人员：\n" +
            "   您好，商户提交的外汇汇入申请订单和银行汇款通知订单已匹配成功，订单信息如下：TT编码：${ttNo}；"
            + "入账账户：${inComeAccount}；到账金额为 ${amount} ${ccy}；请及时登录网银查看实际到账情况。\n";


    /**
     * 结汇邮件上传有误，发送明细给商户
     */

    public static final String SETTLE_ERROR_CONTENT_MERCHANT = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "</head>\n" +
            "<body>\n" +
            "尊敬的宝付商户：<br><br>\n" +
            "\n" +
            "&nbsp; &nbsp; &nbsp; &nbsp; 您好，您于${time}在宝付商户前台填写的汇款流水号为${incomeNo}的外汇汇入申请有误。请及时<br>\n"
            + "登录商户前台<a href=\"https://bm.baofoo.com/\">https://bm.baofoo.com/</a>查看并重新填写外汇汇入申请。<br><br>\n" +
            "\n" +
            "&nbsp; &nbsp; &nbsp; &nbsp;如有任何问题，请您致电宝付统一客服电话021-68811008<br><br>\n" +
            "\n" +
            "&nbsp; &nbsp; &nbsp; &nbsp;由此给您带来的不便，敬请谅解，感谢您对我司工作的理解和支持！<br>\n"
            + "</body>\n" +
            "</html>";


    /**
     * 结汇邮件上传有误，发送明细给清算
     */
    public static final String SETTLE_ERROR_CONTENT_CM = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "</head>\n" +
            "<body>\n" +
            "Dear all，<br><br>\n" +
            "\n" +
            "&nbsp;&nbsp;&nbsp;&nbsp;您好，商户${memberName}于${time}在宝付商户前台填写的汇款流水号为${incomeNo}的外汇汇入申请有误。<br><br>\n"
            + "请及时提醒商户登录商户前台<a href=\"https://bm.baofoo.com/\">https://bm.baofoo.com/</a>查看并重新填写外汇汇入申请。\n"
            + "</body>\n" +
            "</html>";


    /**
     * 时分秒   add by wdj
     */
    public static final String TIME = " 00:00:00";

    /**
     * 时分秒   add by wdj
     */
    public static final String TIMEEND = " 23:59:59";

    /**
     * 风控审核拒绝标识  add by wdj
     */
    public static final String REFUSE = "R";

    /**
     * 渠道分组redis key前缀
     */
    public static final String CHANNEL_GROUP_REDIS_KEY = "CBPAY:CHANNEL:GROUP:";

    /**
     * 邮件发送备案明细信息
     */
    public static final String[] EMAIL_ROW_HEAD = {"商户流水号", "订单币种", "订单金额", "交易时间", "支付人姓名",
            "支付人身份证号码", "账户", "预留手机号码", "商品名称", "商品数量", "单价"};

    /**
     * 创建文件汇总时锁住商户的key
     */
    public static final String CBPAY_CREATE_SUM_FILE_KEY = "CBPAY:CREATE:SUM:FILE:";

    /**
     * 明细文件条数
     */
    public static final Integer CBPAY_EMAIL_DETAIL_COUNT = 45000;

    /**
     * EMAIL 内容
     */
    public static final String NOTIFY_SETTLE_EMAIL_CONTENT = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "</head>\n"
            + "<body>\n"
            + "<p>Dear All：</p>\n"
            + "<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${memberName}于${date}发起了一笔跨境汇款；</p>"
            + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>汇款批次号</b>：${batchNo}；<b>人民币金额</b>：${transMoney}；"
            + "<b>目标币种</b>：${settlementCcy}；</p>\n"
            + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>汇款渠道</b>：${channelName}；"
            + "清算的同事请核查宝付跨境人民币备付金账户资金充足，谢谢！</p>"
            + "</body>\n"
            + "</html>";


    /**
     * EMAIL 内容   付汇凭证邮件内容
     */
    public static final String REMIT_DOC_EMAIL_CONTENT = "尊敬的${memberName}商户：\n"
            + "        您于${date}完成付汇交易，批次号：${batchNo}，付汇金额（${settlementCcy}）：${transMoney}。交易凭证请查收附件。\n";

    /**
     * EMAIL 内容
     */
    public static final String NOTIFY_SETTLE_EMAIL_SUBJECT = "跨境汇款申请通知";

    /**
     * 时间格式化
     */
    public static final String DATE_FORMAT_FULL = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 时间格式化
     */
    public static final String DATE_FORMAT_FULL_TWO = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 时间格式化
     */
    public static final String DATE_FORMAT_SIMPLE = "yyyy年MM月dd日";

    /**
     * 反洗钱申请锁住商户的key
     */
    public static final String CBPAY_AML_ENTITY_KEY = "CBPAY:AML:ENTITY:";

    /**
     * 字符1  add by wdj
     */
    public static final String ONE = "1";

    /**
     * 文件批次号
     */
    public static final String CBPAY_ORDER_BATCH_NO = "CBPAY:ORDER:BATCHNO";

    /**
     * 文件批次待审核状态
     */
    public static final String BATCH_UPLOAD_TRUE_STATUS = "TRUE";

    /**
     * 文件批次取消中状态
     */
    public static final String BATCH_UPLOAD_CANCEL_STATUS = "CANCEL";

    /**
     * 付汇参数列表
     */
    public static final String REMITTANCE_LIST = "REMITTANCE_LIST";

    /**
     * 结汇参数列表
     */
    public static final String SETTLE_LIST = "SETTLE_LIST";

    /**
     * 宝付默认商品
     */
    public static final String BAOFOO_COMMODITY_NAME = "宝付商品";

    /**
     * 中行通道ID
     */
    public static final String CHINA_BANK_AISLE_ID = "12009";

    /**
     * 平安通道ID
     */
    public static final String PINGAN_BANK_AISLE_ID = "12018";

    /**
     * 时分秒   add by wdj
     */
    public static final String TIME_CUSTOMS = " 17:00:00";

    /**
     * 结汇垫资KEY锁标识
     */
    public static final String PREPAYMENT_PROCESS_FLAG = "PREPAYMENT:PROCESS:FLAG:";


    /**
     * 结汇匹配成功处理文件标识
     */
    public static final String SETTLE_OPERATION_FLAG = "SETTLE:OPERATION:FLAG:";


    /**
     * 海关实名认证结果统计模板
     */
    public static final String CUSTOM_VERIFY_EMAIL_TEMPLATE = "<tr>\n"
            + "\t<td bgcolor=\"#fffafa\">${memberId}/${memberName}</td>\n"
            + "\t<td bgcolor=\"#fffafa\">${totalCount}</td>\n"
            + "\t<td bgcolor=\"#fffafa\">${suc}</td>\n"
            + "\t<td bgcolor=\"#fffafa\">${fail}</td>\n"
            + "\t<td bgcolor=\"#fffafa\">${sucRate}%</td>\n"
            + "</tr>";

    /**
     * 无数据模板
     */
    public static final String CUSTOM_VERIFY_EMAIL_TEMPLATE_WITHOUT_DATA = "<td colspan=\"5\" >\n"
            + "\t<center>暂无数据！</center>\n"
            + "</td>";

    /**
     * 实名认证汇总邮件
     */
    public static final String CONTENT = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>实名认证汇总邮件</title>\n"
            + "</head>\n"
            + "<style>\n"
            + "\n"
            + "    table {\n"
            + "        border-collapse: collapse;\n"
            + "        border-spacing: 0;\n"
            + "        border-left: 1px solid #888;\n"
            + "        border-top: 1px solid #888;\n"
            + "        background: #efefef;\n"
            + "    }\n"
            + "\n"
            + "    th, td {\n"
            + "        border-right: 1px solid #888;\n"
            + "        border-bottom: 1px solid #888;\n"
            + "        padding: 5px 15px;\n"
            + "    }\n"
            + "\n"
            + "    th {\n"
            + "        font-weight: bold;\n"
            + "        background: #ccc;\n"
            + "    }\n"
            + "\n"
            + "</style>\n"
            + "<body>\n"
            + "Dear All，<br>\n"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${beginTime}到${endTime}，海关支付单推送实名认证汇总结果如下，请查收。<br>\n"
            + "<br>\n"
            + "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\n"
            + "    <tr>\n"
            + "        <td colspan=\"5\" bgcolor=\"#00bfff\">\n"
            + "            <center>海关支付单推送实名认证汇总结果</center>\n"
            + "        </td>\n"
            + "    </tr>\n"
            + "    <tr>\n"
            + "        <td>商户号/商户名</td>\n"
            + "        <td>抽查总笔数</td>\n"
            + "        <td>成功笔数</td>\n"
            + "        <td>失败笔数</td>\n"
            + "        <td>成功比例</td>\n"
            + "    </tr>\n"
            + "        ${data}\n"
            + "</table>\n"
            + "<br>\n"
            + "\n"
            + "详情地址：<a href=\"https://ad.baofoo.com/\">https://ad.baofoo.com/</a><br>\n"
            + "菜单位置：跨境支付-》风控管理-》支付单推送实名认证<br>\n"
            + "</body>\n"
            + "</html>";

    /**
     * 转账汇总统计redis key
     */
    public static final String TRANSFER_SUM_REDIS_KEY = "CBPAY:TRASFER:SUM:REDIS_KEY";

    /**
     * wyre商户提现key
     */
    public static final String CBPAY_WYPE_WITHDRAW_CASH = "CBPAY:WYPE:WITHDRAW:CASH:";

    /**
     * wyre商户开户key
     */
    public static final String CBPAY_WYPE_OPEN_ACCOUNT = "CBPAY:WYPE:OPEN:ACCOUNT:";

    /**
     * 最小金额
     */
    public static final String MIN_AMT = "0.01";

    /**
     * redis 连接符
     */
    public static final String REDIS_SYMBOL = ":";

    /**
     * 提现邮件内容
     */
    public static final String WYRE_WITHDRAW_CASH_EMAIL = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "</head>\n"
            + "<body>\n"
            + "尊敬的${memberName}商户,<br><br>\n"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的宝付美国收款账户发起了一笔提现，提现明细如下。<br><br>\n"
            + "<table cellspacing=\"0\" border=\"1\" style=\"border-collapse:collapse;text-align: center;margin-left:40px;\" width=\"500\" height=\"30\">\n"
            + "    <tr>\n"
            + "        <td>提现时间</td>\n"
            + "        <td>提现金额</td>\n"
            + "        <td>提现手续费</td>\n"
            + "        <td>币种</td>\n"
            + "    </tr>\n"
            + "    <tr>\n"
            + "        <td>${date}</td>\n"
            + "        <td>${transferAmt}</td>\n"
            + "        <td>${transferFee}</td>\n"
            + "        <td>${ccy}</td>\n"
            + "    </tr>\n"
            + "</table>\n"
            + "<br>\n"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请登录宝付商户控台<a href=\"https://bm.baofoo.com\">bm.baofoo.com</a>关注提现动态。\n"
            + "<br><br>\n"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如对交易有任何疑问，请致电7×24客服热线021-68811008。\n"
            + "</body>\n"
            + "</html>";

    /**
     * 收款账户收支明细标题
     */
    public static final String NOTIFY_PAYEE_PAYMENT_EMAIL_SUBJECT = "收款账户入账通知";

    /**
     * 收款账户收支明细Email内容
     */
    public static final String NOTIFY_PAYEE_PAYMENT_EMAIL_CONTENT = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>收款账户入账通知</title>\n"
            + "</head>\n"
            + "<style>\n"
            + "\n"
            + "    table {\n"
            + "        border-collapse: collapse;\n"
            + "        border-color:#000;\n"
            + "        margin-left: 33px;\n"
            + "    }\n"
            + "\n"
            + "    th, td {\n"
            + "        border-right: 1px solid #888;\n"
            + "        border-bottom: 1px solid #888;\n"
            + "        border-color:#000;\n"
            + "        padding: 5px 15px;\n"
            + "    }\n"
            + "\n"
            + "    p{\n"
            + "		   text-indent:2em;\n"
            + "		}\n"
            + "</style>\n"
            + "<body>\n"
            + "    尊敬的${memberName}商户，<br>\n"
            + "<p>您的宝付美国收款账户收到一笔入账，入账明细如下。</p>\n"
            + "\n"
            + "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\n"
            + "    <tr>\n"
            + "    <td>入账时间</td>\n"
            + "    <td>收款金额</td>\n"
            + "    <td>收款手续费</td>\n"
            + "    <td>币种</td>\n"
            + "  </tr>\n"
            + "  <tr>\n"
            + "    <td>${date}</td>\n"
            + "    <td>${orderAmt}</td>\n"
            + "    <td>2.50</td>\n"
            + "    <td>${orderCcy}</td>\n"
            + "  </tr>\n"
            + "</table>\n"
            + "<br>\n"
            + "<p>请登录宝付商户控台 <a href=\"https://bm.baofoo.com\"> bm.baofoo.com</a> 完成提现。</p>\n"
            + "<p>如对交易有任何疑问，请致电7×24客服热线021-68811008。</p>\n"
            + "</body>\n"
            + "</html>\n";

    /**
     * 购付汇API上传订单明细错误文件名称前缀
     */
    public static final String REMIT_ERROR_PREFIX = "REMIT_ERROR_";
    /**
     * 对外提供redis服务key
     */
    public static final String CBPAY_OUTER_REDIS_LOCK = "CBPAY:OUTER:REDIS:LOCK:";

    /**
     * wyre订单金额差异订单收款人信息
     */
    public static final String CBPAY_WYRE_PAYEE_USER = "CBPAY_WYRE_PAYEE_USER";

    /**
     * 连接符
     */
    public static final String UNDERLINE = "_";

    /**
     * 商品名称
     */
    public static final String PRODUCT_NAME = "手续费返点";

    /**
     * 银行卡号
     */
    public static final String BANK_CARD_NO = "17316325190";

    /**
     * 持卡人姓名
     */
    public static final String ID_HOLDER = "杨翠";

    /**
     * 身份证
     */
    public static final String ID_CARD_NO = "75ca29f17a1c54cade25f826e2d6907bb62b6334d568defd";

    /**
     * 批量汇款锁key
     */
    public static final String BATCH_REMITTANCE_KEY = "CBPAY:BATCH:REMITTANCE:";

    /**
     * 商户跨境汇款文件上传校验解析功能锁定标识
     */
    public static final String REMITTANCE_FILE_PROCESSING_FLAG = "REMITTANCE:FILE:PROCESSING:FLAG:";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 数字汉字和字母正则
     */
    public static final String STRING_REG = "[^0-9a-zA-Z\\u4e00-\\u9fa5]";

    /**
     * 时间格式：yyyy/MM/dd HH:mm:ss
     */
    public static final String timesPattern = "yyyy/MM/dd HH:mm:ss";

    /**
     * 自动结汇账户余额不足邮件内容
     */
    public static final String autoRemitEmail = "尊敬的宝付商户： <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "您好，由于您的账户余额不足，导致当前汇款申请失败，可以通过线下打款或者充值产品进行账户余额添加，当余额充足后，我司将在下一个处理时间点再次发起汇款申请。<br><br>" +
            " 由此给您带来的不变，敬请谅解。<br>" +
            " 感谢您对我司工作的理解和支持！<br>";
}
