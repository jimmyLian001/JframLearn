package com.baofu.cbpayservice.common.enums;

import com.system.commons.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 错误码枚举信息
 * User: 香客斯 Date: 2016/09/19 ProductName: asias-parent Version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeEnum implements ErrorCode {

    RESULT_SUCCESS("AS_100000", "校验处理通过"),
    RESULT_ERROR_BF0001("AS_100001", "系统繁忙，请稍后再试"),
    RESULT_ERROR_BF0002("AS_100002", "系统异常，请联系宝付"),
    RESULT_ERROR_BF0003("AS_100003", "参数错误:"),
    RESULT_ERROR_BF0004("AS_100004", "您的身份信息不正确"),
    RESULT_ERROR_BF0005("AS_100005", "您输入的银行卡信息有误，请重新输入"),
    RESULT_ERROR_BF0006("AS_100006", "您输入的信息有误，请重新输入"),
    RESULT_ERROR_BF0011("AS_100011", "该终端不存在"),
    RESULT_ERROR_BF0012("AS_100012", "该商户不存在"),
    RESULT_ERROR_BF0013("AS_100013", "该商户未开通"),
    RESULT_ERROR_BF0014("AS_100014", "该终端未开通"),
    RESULT_ERROR_BF0015("AS_100015", "错误的商户号，终端号"),
    RESULT_ERROR_BF0016("AS_100016", "功能未开通"),
    RESULT_ERROR_BF0017("AS_100017", "用户id或商户号不能为空"),
    RESULT_ERROR_BF0018("AS_100018", "商户请求地址异常"),
    RESULT_ERROR_BF0019("AS_100019", "商户限额"),
    RESULT_ERROR_BF0020("AS_100020", "创建宝付订单号异常"),
    RESULT_ERROR_BF0021("AS_100021", "错误的交易密文"),
    RESULT_ERROR_BF0022("AS_100022", "报文格式错误"),
    RESULT_ERROR_BF0023("AS_100023", "检测卡号是否进入黑名单,获取卡号黑名单数据失败!"),
    RESULT_ERROR_BF0024("AS_100024", "无法获取来源网址数据库表的校验数据"),
    RESULT_ERROR_BF0025("AS_100025", "单笔限额校验,获取单笔限额表数据失败!"),
    RESULT_ERROR_BF0026("AS_100026", "获取兑换汇率失败"),
    RESULT_ERROR_BF0028("AS_100028", "解析商品信息失败"),
    RESULT_ERROR_BF0029("AS_100029", "商户提交商品信息为空"),
    RESULT_ERROR_BF0030("AS_100030", "商户未开通此产品"),
    RESULT_ERROR_BF0031("AS_100031", "暂不支持该功能"),
    RESULT_ERROR_BF0032("AS_100032", "产品校验失败或该终端未设置产品"),
    RESULT_ERROR_BF0033("AS_100033", "您的姓名为空，请输入"),
    RESULT_ERROR_BF0034("AS_100034", "您的手机号码不正确，请重新输入"),
    RESULT_ERROR_BF0035("AS_100035", "报文交易要素缺失，请返回商户重新操作"),
    RESULT_ERROR_BF0036("AS_100036", "支付方式(pay_id)不能为空"),
    RESULT_ERROR_BF0037("AS_100037", "用户尚未认证,请先认证"),
    RESULT_ERROR_BF0038("AS_100038", "订单明细参数基本校验失败"),
    RESULT_ERROR_BF0039("AS_100039", "产品校验失败"),
    RESULT_ERROR_BF0040("AS_100040", "产品号获取异常"),
    RESULT_ERROR_BF0041("AS_100041", "终端产品功能校验失败"),
    RESULT_ERROR_BF0042("AS_100042", "订单不允许重复提交"),
    RESULT_ERROR_BF0043("AS_100043", "风控调用失败"),
    RESULT_ERROR_BF0044("AS_100044", "卡四要素校验失败"),
    RESULT_ERROR_BF0045("AS_100045", "请输入商户号"),
    RESULT_ERROR_BF0046("AS_100046", "请输入商户订单号"),
    RESULT_ERROR_BF0047("AS_100047", "请输入商户用户唯一标识"),
    RESULT_ERROR_BF0048("AS_100048", "用户已存在认证关系"),
    RESULT_ERROR_BF0050("AS_100050", "请求IP未绑定"),
    RESULT_ERROR_BF0051("AS_100051", "宝付订单号不能为空"),
    RESULT_ERROR_BF0052("AS_100052", "加密参数信息为空"),
    RESULT_ERROR_BF0053("AS_100053", "订单号信息集合为空"),
    RESULT_ERROR_BF0054("AS_100054", "汇款订单状态不正确"),
    RESULT_ERROR_BF0055("AS_100055", "订单不存在"),
    RESULT_ERROR_BF0056("AS_100056", "创建汇款订单文件异常"),
    RESULT_ERROR_BF0057("AS_100057", "汇款订单明细不存在"),
    RESULT_ERROR_BF0058("AS_100058", "汇款订单已审核"),
    RESULT_ERROR_BF0059("AS_100059", "调用计费系统失败"),
    RESULT_ERROR_BF0060("AS_100060", "创建汇款订单异常"),
    RESULT_ERROR_BF0061("AS_100061", "聚合支付订单创建失败"),
    RESULT_ERROR_BF0062("AS_100062", "商户渠道成本信息未配置"),
    RESULT_ERROR_BF0063("AS_100063", "渠道成本信息未配置"),
    RESULT_ERROR_BF0064("AS_100064", "未查询到对应的渠道信息"),
    RESULT_ERROR_BF0065("AS_100065", "商户账户余额不足"),
    RESULT_ERROR_BF0066("AS_100066", "未查询到该终端缓存信息"),
    RESULT_ERROR_BF0067("AS_100067", "定时任务配置信息为空"),
    RESULT_ERROR_BF0068("AS_100068", "文件格式错误或者不存在"),
    RESULT_ERROR_BF0069("AS_100069", "文件数据为空"),
    RESULT_ERROR_BF0070("AS_100070", "批量报关失败"),
    RESULT_ERROR_BF0071("AS_100071", "创建跨境订单失败"),
    RESULT_ERROR_BF0072("AS_100072", "订单id获取失败"),
    RESULT_ERROR_BF0073("AS_100073", "上送订单类型异常"),
    RESULT_ERROR_BF0074("AS_100074", "调用清算系统异常"),
    RESULT_ERROR_BF0075("AS_100075", "实名认证异常"),
    RESULT_ERROR_BF0076("AS_100076", "该订单已存在"),
    RESULT_ERROR_BF0077("AS_100077", "订单已审核"),
    RESULT_ERROR_BF0078("AS_100078", "报关失败"),
    RESULT_ERROR_BF0079("AS_100079", "代理报关订单明细不存在"),
    RESULT_ERROR_BF0080("AS_100080", "调用计费失败"),
    RESULT_ERROR_BF0081("AS_100081", "正在处理，请勿频繁操作！"),
    RESULT_ERROR_BF0082("AS_100082", "创建购汇明细文件异常"),
    RESULT_ERROR_BF0083("AS_100083", "商户订单号已创建支付单"),
    RESULT_ERROR_BF0084("AS_100084", "支付单信息不存在"),
    RESULT_ERROR_BF0085("AS_100085", "上传明细文件异常"),
    RESULT_ERROR_BF0086("AS_100086", "汇款附加信息不存在"),
    RESULT_ERROR_BF0089("AS_100089", "交易密文解密失败"),
    RESULT_ERROR_BF0090("AS_100090", "交易密文不能为空"),
    RESULT_ERROR_BF0091("AS_100091", "通知类型异常"),
    RESULT_ERROR_BF0092("AS_100092", "接口版本异常"),
    RESULT_ERROR_BF0093("AS_100093", "功能信息不能为空"),
    RESULT_ERROR_BF0094("AS_100094", "订单信息不存在"),
    RESULT_ERROR_BF0095("AS_100095", "通知商户返回信息不正确"),
    RESULT_ERROR_BF0096("AS_100096", "通知商户异常"),
    RESULT_ERROR_BF0097("AS_100097", "调用缓存服务异常"),
    RESULT_ERROR_BF0098("AS_100098", "支付单附加信息不存在"),
    RESULT_ERROR_BF0099("AS_100099", "支付订单状态不正确"),
    RESULT_ERROR_BF00100("AS_1000100", "入账费用承担方非宝付"),
    RESULT_ERROR_BF00101("AS_1000101", "入账费用待审核或已审核通过"),
    RESULT_ERROR_BF00102("AS_1000102", "入账费用未设置"),
    RESULT_ERROR_BF00103("AS_1000103", "入账费用已审核"),
    RESULT_ERROR_BF00104("AS_1000104", "跨境结汇订单存在"),
    RESULT_ERROR_BF00105("AS_1000105", "跨境结汇订单不是待结汇状态"),
    RESULT_ERROR_BF00106("AS_1000106", "汇入编号已创建申请单"),
    RESULT_ERROR_BF00107("AS_1000107", "填写金额和汇入金额不匹配"),
    RESULT_ERROR_BF00108("AS_1000108", "填写币种和汇入币种不匹配"),
    RESULT_ERROR_BF00109("AS_1000109", "银行通知信息不存在"),
    RESULT_ERROR_BF00110("AS_1000110", "调用清算系统异常"),
    RESULT_ERROR_BF00115("AS_1000115", "跨境结汇订单不存在"),
    RESULT_ERROR_BF00116("AS_1000116", "跨境结汇订单未解付"),
    RESULT_ERROR_BF00117("AS_1000117", "跨境结汇订单未处于待结汇状态"),
    RESULT_ERROR_BF00118("AS_1000118", "跨境结汇订单结汇状态更新异常"),
    RESULT_ERROR_BF00119("AS_1000119", "跨境结汇订单未处于结汇中状态"),
    RESULT_ERROR_BF00120("AS_1000120", "跨境结汇外币账户余额不足"),
    RESULT_ERROR_BF00121("AS_1000121", "跨境结汇订单待清算或清算失败"),
    RESULT_ERROR_BF00122("AS_1000122", "调用渠道系统异常"),
    RESULT_ERROR_BF00123("AS_1000123", "调用渠道查汇失败"),
    RESULT_ERROR_BF00124("AS_1000124", "汇总批次号不能为空"),
    RESULT_ERROR_BF00125("AS_1000125", "渠道分组信息添加失败"),
    RESULT_ERROR_BF00126("AS_1000126", "查询渠道分组信息为空"),
    RESULT_ERROR_BF00127("AS_1000127", "商户分组信息配置为空"),
    RESULT_ERROR_BF00128("AS_1000128", "更新商户分组信息配置失败"),
    RESULT_ERROR_BF00129("AS_1000129", "商户该币种信息已存在"),
    RESULT_ERROR_BF00130("AS_1000130", "商户该币种信息不存在"),
    RESULT_ERROR_BF00131("AS_1000131", "商户该币种不可结算"),
    RESULT_ERROR_BF00132("AS_1000132", "接口不能重复调用"),
    RESULT_ERROR_BF00133("AS_1000133", "提现订单文件处理异常"),
    RESULT_ERROR_BF00134("AS_1000134", "按时间未查询到订单信息"),
    RESULT_ERROR_BF00135("AS_1000135", "按时间查询订单汇总更新异常"),
    RESULT_ERROR_BF00136("AS_1000136", "查询汇率异常"),
    RESULT_ERROR_BF00137("AS_1000137", "结汇支付明细文件状态不正确"),
    RESULT_ERROR_BF00138("AS_1000138", "添加补录失败"),

    RESULT_ERROR_BF00139("AS_1000139", "该文件批次信息不存在"),
    RESULT_ERROR_BF00140("AS_1000140", "该文件批次信息状态不正确"),
    RESULT_ERROR_BF00141("AS_1000141", "该文件批次包含多个币种"),
    RESULT_ERROR_BF00142("AS_1000142", "汇款重发异常"),
    RESULT_ERROR_BF00143("AS_1000143", "没有足够的订单需要进行实名认证"),
    RESULT_ERROR_BF00144("AS_1000144", "抽查订单类型有误~"),
    RESULT_ERROR_BF00145("AS_1000145", "商户分组信息已存在"),
    RESULT_ERROR_BF00146("AS_1000146", "文件批次状态非待审核状态"),
    RESULT_ERROR_BF00147("AS_1000147", "该文件正在取消中请勿重复操作"),
    RESULT_ERROR_BF00148("AS_1000148", "创建币种结算账户异常"),
    RESULT_ERROR_BF00149("AS_1000149", "账户类型为个人时，国别不能为空"),
    RESULT_ERROR_BF00150("AS_1000150", "汇款账户主体编号不一致"),
    RESULT_ERROR_BF00151("AS_1000151", "汇入汇款编号已经匹配"),
    RESULT_ERROR_BF00152("AS_1000152", "多批次汇款币种不一致，请勾选相同币种的批次进行汇款！"),
    RESULT_ERROR_BF00153("AS_1000153", "暂不支持的银行通道"),
    RESULT_ERROR_BF00154("AS_1000154", "汇率信息或币信息异常"),
    RESULT_ERROR_BF00155("AS_1000155", "国别编号不能为空"),
    RESULT_ERROR_BF00156("AS_1000156", "没有查到相关国家信息"),
    RESULT_ERROR_BF00157("AS_1000157", "币种信息不能为空"),
    RESULT_ERROR_BF00158("AS_1000158", "查询商户业务邮箱信息异常"),
    RESULT_ERROR_BF00159("AS_1000159", "查询商户缓存信息异常"),
    RESULT_ERROR_BF00160("AS_1000160", "参数信息异常"),
    RESULT_ERROR_BF00161("AS_1000161", "行业类型不正确"),

    RESULT_ERROR_BF00162("AS_1000162", "该收汇申请的结汇文件已经上传"),
    RESULT_ERROR_BF00163("AS_1000163", "汇入汇款申请不存在"),
    RESULT_ERROR_BF00164("AS_1000164", "创建商户FTP信息失败"),
    RESULT_ERROR_BF00165("AS_1000165", "商户FTP信息已经存在"),
    RESULT_ERROR_BF00166("AS_1000166", "上传商户FTP文件异常"),
    RESULT_ERROR_BF00167("AS_1000167", "查询结汇类型异常"),
    RESULT_ERROR_BF00168("AS_1000168", "查询条件信息异常"),
    RESULT_ERROR_BF00169("AS_1000169", "商户FTP信息未配置"),
    RESULT_ERROR_BF00170("AS_1000170", "无须发送结汇异步通知"),
    RESULT_ERROR_BF00171("AS_1000171", "查询渠道汇率异常"),
    RESULT_ERROR_BF00172("AS_1000172", "此结汇账户信息已被删除，请勿修改"),
    RESULT_ERROR_BF00173("AS_1000173", "此结汇账户信息不存在"),
    RESULT_ERROR_BF00174("AS_1000174", "此结汇账户信息已被删除，请勿重复操作"),
    RESULT_ERROR_BF00175("AS_1000175", "浮动值设置方式为bp，bp值不能为空"),
    RESULT_ERROR_BF00176("AS_1000176", "浮动值设置方式为百分比，百分比值不能为空"),
    RESULT_ERROR_BF00177("AS_1000177", "垫资结汇清算失败"),
    RESULT_ERROR_BF00178("AS_1000178", "未完成人工匹配"),
    RESULT_ERROR_BF00179("AS_1000179", "结汇垫资申请已存在"),
    RESULT_ERROR_BF00180("AS_1000180", "结汇订单已经完成结汇"),
    RESULT_ERROR_BF00181("AS_1000181", "结汇垫资申请不存在"),
    RESULT_ERROR_BF00182("AS_1000182", "商户已开通自动结汇垫资产品，不能申请临时垫资"),
    RESULT_ERROR_BF00183("AS_1000183", "该结汇订单正在结汇处理中"),
    RESULT_ERROR_BF00184("AS_1000184", "商户未备案"),
    RESULT_ERROR_BF00185("AS_1000185", "垫资审核失败"),
    RESULT_ERROR_BF00186("AS_1000186", "账户地址信息长度超过120字节或分割后的信息长度超过35字节"),
    RESULT_ERROR_BF00187("AS_1000187", "收款账户账户名称重复"),

    RESULT_ERROR_BF00188("AS_1000188", "商户收款账户信息不存在"),
    RESULT_ERROR_BF00189("AS_1000189", "计算手续费的订单金额不正确"),
    RESULT_ERROR_BF00190("AS_1000190", "收款账户账户开户渠道处理异常"),
    RESULT_ERROR_BF00191("AS_1000191", "商户账户余额不足，暂时不能提现"),
    RESULT_ERROR_BF00192("AS_1000192", "渠道通知状态不正确"),
    RESULT_ERROR_BF00193("AS_1000193", "查询账户余额异常"),
    RESULT_ERROR_BF00194("AS_1000194", "商户提现申请明细不存在"),
    RESULT_ERROR_BF00195("AS_1000195", "收款账户收支明细已存在"),
    RESULT_ERROR_BF00196("AS_1000196", "商户转账请求汇总批次号不存在"),
    RESULT_ERROR_BF00197("AS_1000197", "商户转账请求汇总批次未匹配"),
    RESULT_ERROR_BF00198("AS_1000198", "商户转账请求汇总批次明细不存在"),
    RESULT_ERROR_BF00199("AS_1000199", "跨境结汇订单未结汇完成"),
    RESULT_ERROR_BF00200("AS_1000200", "商户转账更新异常"),
    RESULT_ERROR_BF00201("AS_1000201", "收汇订单清算审核状态异常，审核失败"),
    RESULT_ERROR_BF00202("AS_1000202", "正在处理中，请勿重复操作"),
    RESULT_ERROR_BF00203("AS_1000203", "请求渠道系统异常"),
    RESULT_ERROR_BF00204("AS_1000204", "商户请求流水号存在"),
    RESULT_ERROR_BF00205("AS_1000205", "原始币种和订单币种不一致"),
    RESULT_ERROR_BF00206("AS_1000206", "文件批次金额和原始金额不相等"),
    RESULT_ERROR_BF00207("AS_1000207", "商户申请单号重复"),
    RESULT_ERROR_BF00208("AS_1000208", "商户备案主体编号为空"),
    RESULT_ERROR_BF00209("AS_1000209", "文件批次号不合法"),
    RESULT_ERROR_BF0210("AS_100210", "FTP服务器中文件不存在"),
    RESULT_ERROR_BF0211("AS_100211", "FTP文件下载失败"),
    RESULT_ERROR_BF00212("AS_1000212", "商户转账请求汇总批次已匹配"),
    RESULT_ERROR_BF00213("AS_1000213", "批量汇款账户余额不足"),
    RESULT_ERROR_BF00214("AS_1000214", "批量汇款订单金额不足"),
    RESULT_ERROR_BF00216("AS_1000216", "待汇款的批次文件集合包含多个行业类型"),
    RESULT_ERROR_BF00217("AS_1000217", "商户申请汇款金额与待汇款的批次文件集合汇款金额不相等"),
    RESULT_ERROR_BF00218("AS_1000218", "汇款明细文件校验结果查询申请参数为空"),
    RESULT_ERROR_BF00219("AS_1000219", "商户跨境汇款明细文件上传功能已锁定，请勿频繁操作！"),
    RESULT_ERROR_BF00220("AS_1000220", "跨境汇款明细文件批量上传解析校验商户号不统一"),
    RESULT_ERROR_BF00221("AS_1000221", "call 代理跨境汇款明细文件批量上传校验解析文件包含重复文件"),
    RESULT_ERROR_BF00222("AS_1000222", "结汇申请汇款凭证已经上传，请勿重复操作"),
    RESULT_ERROR_BF00223("AS_1000223", "商户未配置自动汇款配置信息，请先配置"),
    RESULT_ERROR_BF00224("AS_1000224", "未发现交易订单总金额"),
    RESULT_ERROR_BF00225("AS_1000225", "未达到起始结算金额"),
    RESULT_ERROR_BF00226("AS_1000226", "商户自动汇款已经关闭"),
    ;

    /**
     * 异常码
     */
    private String errorCode;

    /**
     * 异常描述
     */
    private String errorDesc;


    /**
     * 根据错误编码获取描述信息
     *
     * @param errorCode 错误编码
     * @return 错误描述
     */
    public static ErrorCodeEnum queryDesc(String errorCode) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (errorCodeEnum.getErrorCode().equals(errorCode)) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    /**
     * 设置错误描述信息
     *
     * @param errorMsg 错误信息
     */
    public void setErrorDesc(String errorMsg) {
        this.errorDesc = errorMsg;
    }
}
