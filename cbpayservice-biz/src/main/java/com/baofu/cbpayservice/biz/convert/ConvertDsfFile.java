package com.baofu.cbpayservice.biz.convert;

import com.baofoo.dfs.client.util.DateUtil;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ConvertType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 参数转换
 * User: yangjian  Date: 2017-06-16 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
public class ConvertDsfFile {

    /**
     * 结汇参数
     */
    public static final String[] SETTLE_LIST = {"orderId", "channelId", "payerMemberNo", "payerCountry",
            "payerName", "payerAccNo", "payeeType", "payeeMemberNo", "payeeCertType", "payeeCertNo",
            "payeeName", "payeeAccNo", "transCurrency", "transMoney", "transCode", "settlePropertyCode",
            "commodityInfo", "replenishCertNo", "payeeAccNoType", "settleFundType", "transDate", "payerType",
            "flowRemitNumber", "tranType", "record"};
    /**
     * 解付参数
     */
    public static final String[] RELIEVE_LIST = {"bankReturnNo", "memberNo", "payeeCertNo", "payeeAccNo",
            "payeeName", "payerName", "remitCurrency", "remitMoney", "countryCode", "proposer", "proposerTel",
            "orderId", "payerType", "commodityInfo"};
    /**
     * 付汇参数顺序
     */
    private static final String[] REMITTANCE_LIST = {"channelId", "commodityInfo", "costBorne",
            "memberId", "mode", "orderId", "payerAccNo", "payerAddress", "payerBankName",
            "payerName", "recVAccAddress", "recVAccBicCode", "recVAccCountry", "recVAccCurrency",
            "recVAccName", "recVAccNo", "remitAccType", "remitCertNo", "remitCertType", "remitCurrency",
            "remitMemberNo", "childMemberId", "remitMoney", "cnyAmount", "transDate", "payerType", "tranType", "record"};
    /**
     * 日期参数格式
     */
    private static final String PATTERN = "yyyyMMddHHmmss";

    /**
     * 分隔符
     */
    private static final String SPLIT = "|$|";

    /**
     * 日期名
     */
    private static final String TRANSDATE = "transDate";

    /**
     * 对公对私
     */
    private static final String PAYERTYPE = "payerType";
    /**
     * 购汇参数
     */
    //   private static final String[]

    /**
     * 将list对象按照顺序转换成渠道要$|形式的参数
     *
     * @param list 需要转换的参数
     * @param <T>  传进来的类型
     * @param type 匹配类型
     * @return 返回String类型的结果
     */
    public static <T> String paramConvert(List<T> list, ConvertType type) {
        StringBuffer result = new StringBuffer();
        String[] remittanceList;
        if (Constants.REMITTANCE_LIST.equals(type.getCode())) {
            remittanceList = REMITTANCE_LIST;
        } else if (Constants.SETTLE_LIST.equals(type.getCode())) {
            remittanceList = SETTLE_LIST;
        } else {
            remittanceList = RELIEVE_LIST;
        }
        for (T t : list) {
            result.append(getString(t, remittanceList));
            result.append("\r");
        }
        return result.toString();
    }

    /**
     * @param t              参数
     * @param remittanceList 匹配列表
     * @param <T>            传进来的类型
     * @return 返回结果
     */
    public static <T> String getString(T t, String[] remittanceList) {
        StringBuffer result = new StringBuffer();
        Field[] tSupers = t.getClass().getSuperclass().getDeclaredFields();
        Field[] ts = t.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < remittanceList.length; i++) {
                //0和5是BaseDto中的channelId和orderid两个字段
                if (i != 0) {
                    result.append(SPLIT);
                }
                //获取基类的对象和参数
                for (Field cgwRemit : tSupers) {
                    cgwRemit.setAccessible(true);

                    if (cgwRemit.getName().equals(remittanceList[i])) {
                        if (PAYERTYPE.equals(cgwRemit.getName())) {
                            if (cgwRemit.get(t) != null) {
                                result.append(cgwRemit.get(t));
                            } else {
                                //结汇、解付目前写死
                                result.append("D");
                            }
                            continue;
                        }
                        result.append(cgwRemit.get(t));
                    }

                }
                //获取参数
                for (Field cgwRemit : ts) {
                    cgwRemit.setAccessible(true);
                    if (cgwRemit.getName().equals(remittanceList[i]) && cgwRemit.get(t) != null) {
                        //判断属性是否为日交易日期，更改交易日期格式
                        if (TRANSDATE.equals(cgwRemit.getName())) {
                            result.append(DateUtil.format((Date) cgwRemit.get(t), PATTERN));
                            continue;
                        }
                        result.append(cgwRemit.get(t));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("参数转换异常", e);
        }
        return result.toString();
    }
}
