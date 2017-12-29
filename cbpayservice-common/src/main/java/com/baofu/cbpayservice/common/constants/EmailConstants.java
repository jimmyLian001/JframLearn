package com.baofu.cbpayservice.common.constants;

/**
 * 邮件标题和内容常量
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
public class EmailConstants {

    /**
     * 收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
     */
    //邮件标题
    public static final String EXCHANGE_NOTIFY_TITLE = "跨境收款--人工匹配--系统到账通知--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String EXCHANGE_NOTIFY_CONTENT = "Dear 清算工作人员：\n" +
            "    您好，系统已经接收到银行的汇款通知，订单信息如下：汇入汇款编码：${incomeNo}；到账金额为${amount} 币种：${ccy}；请进行人工匹配";


    /**
     * 收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
     */
    //邮件标题
    public static final String IMPORT_APPLY_NOTIFY_TITLE = "跨境收款--人工匹配----商户汇入申请--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String IMPORT_APPLY_NOTIFY_CONTENT = "Dear 清算工作人员：\n" +
            "   您好，商户 ${memberName} 已经提交外汇汇入申请，订单信息如下：汇款流水号：${TtNo}；金额为${amount} 币种：${ccy}；请进行人工匹配。";


    /**
     * 人工匹配之后发送邮件给商户提示商户可以上传明细
     */
    //邮件标题
    public static final String NOTIFY_MEMBER_UPLOAD_FILE_TITLE = "跨境收款--交易明细上传--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String NOTIFY_MEMBER_UPLOAD_FILE_CONTENT = "尊敬的宝付商户：\n" +
            "   您好，宝付${bankName}银行外汇备付金账户${account}收到您的外汇汇款，到账金额为${amount},币种${ccy}；" +
            "请及时登录商户前台 https://bm.baofoo.com/ 上传交易明细。\n" +
            "   如有任何问题，请您致电宝付统一客服电话021-68811008\n" +
            "   由此给您带来的不便，敬请谅解，感谢您对我司工作的理解和支持！";


    /**
     * 人工匹配之后发送邮件给结汇相关人员提示商户可以上传明细
     */
    //邮件标题
    public static final String NOTIFY_CM_UPLOAD_FILE_TITLE = "跨境收款--交易明细上传--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String NOTIFY_CM_UPLOAD_FILE_CONTENT = "Dear all,\n" +
            "   您好，宝付${bankName}银行外汇备付金账户${account}收到商户 ${memberName} 线下汇款。到账金额为${amount}," +
            "币种：${ccy}的汇款；" +
            "请通知商户 ${memberName} 登录前台 https://bm.baofoo.com/ 上传交易明细。";


    /**
     * 收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
     */
    //邮件标题
    public static final String SETTLE_SUCCESS_NOTIFY_TITLE = "跨境收款--结汇成功---宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String SETTLE_SUCCESS_NOTIFY_CONTENT = "Dear all：\n" +
            "   商户 ${memberName} 提交的汇款流水号为${TtNo}的结汇申请订单已经结汇成功,详情请参见如下结汇凭证。";


    /**
     * 收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
     */
    //邮件标题
    public static final String SETTLE_SUCCESS_NOTIFY_MEMBER_TITLE = "跨境收款--结汇成功--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String SETTLE_SUCCESS_NOTIFY_MEMBER_CONTENT = "尊敬的宝付商户：\n" +
            "   您好，您提交的订单已经结汇成功，详情请参见如下结汇凭证。\n" +
            "   如有任何问题，请您致电宝付统一客服电话021-68811008";

    /**
     * 反洗钱失败通知商户:AML_FAIL_NOTIFY_MEMBER
     */
    //邮件标题
    public static final String AML_FAIL_NOTIFY_MEMBER_TITLE = "汇款明细审核失败---宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String AML_FAIL_NOTIFY_MEMBER_CONTENT = "尊敬的宝付商户：\n" +
            "您好，您提交的批次号为：${batchNo}的汇款订单明细审核全部失败，导致汇款失败。\n" +
            "由此给您带来的不变，敬请谅解。\n" +
            "感谢您对我司工作的理解和支持！\n";

    /**
     * 反洗钱失败通知业务人员：AML_FAIL_NOTIFY_BUSINESS
     */
    //邮件标题
    public static final String AML_FAIL_NOTIFY_BUSINESS_TITLE = "汇款明细审核失败---宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String AML_FAIL_NOTIFY_BUSINESS_CONTENT = "Dear All：\n" +
            "${memberName}商户于${date}发起了一笔跨境汇款，汇款批次号为：${batchNo}\n" +
            "汇款金额（元）：${transMoney}；结算币种为：${systemCcy}；汇款渠道：${channelName}；由于明细审核全部失败，导致汇款失败，请及时通知商户，谢谢！";

    /**
     * 反洗钱部分成功，通知商户：AML_PORTION_SUCCESS_NOTIFY_MEMBER
     */
    //邮件标题
    public static final String AML_PORTION_SUCCESS_NOTIFY_MEMBER_TITLE = "汇款明细审核部分失败---宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String AML_PORTION_SUCCESS_NOTIFY_MEMBER_CONTENT = "尊敬的宝付商户：\n" +
            "您好，您提交的批次号为：${batchNo}批次号的汇款订单明细审核部分失败，导致汇款失\n" +
            "败，请登录商户前台 https://bm.baofoo.com/，及时查询失败原因，若需要继续将成\n" +
            "功部分的继续汇款，可再次点击生成汇款订单。\n" +
            "由此给您带来的不变，敬请谅解。\n" +
            "感谢您对我司工作的理解和支持！";

    /**
     * 反洗钱部分成功,通知业务人员：AML_FAIL_NOTIFY_BUSINESS
     */
    //邮件标题
    public static final String AML_PORTION_SUCCESS_NOTIFY_BUSINESS_TITLE = "汇款明细审核部分失败---宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String AML_PORTION_SUCCESS_NOTIFY_BUSINESS_CONTENT = "Dear All：\n" +
            "${memberName}商户于${date}发起了一笔跨境汇款，汇款批次号为：${batchNo}\n" +
            "汇款金额（元）：${transMoney}；结算币种为：${systemCcy}；汇款渠道：${channelName}；由于明细审核部分失败，导致汇款失败，请及时通知商户，谢谢！";

    /**
     * 收款账户开户成功通知商户
     */
    //邮件标题
    public static final String ACCOUNT_NOTIFY_TITLE = "收款账户开户成功--宝付网络科技（上海）有限公司";
    //邮件内容
    public static final String ACCOUNT_NOTIFY_CONTENT = "尊敬的${memberName}商户：\n"
            + "      您已开通宝付美国收款账户，您的收款账户信息如下：\n"
            + "\n\n"
            + "                   银行账号：${bankAccNo}\n"
            + "                   路由（ABA）：${routingNumber}\n"
            + "                   银行名称：${bankAccName}\n"
            + "                   银行地址：${bankAccAddress}\n"
            + "      请妥善保管上述信息。\n"
            + "      您现在可以开始宝付美国收款之旅了！";
}
