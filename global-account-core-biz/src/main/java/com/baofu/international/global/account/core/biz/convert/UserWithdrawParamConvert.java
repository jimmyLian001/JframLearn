package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserWithdrawReqBo;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 1、用户提现至银行卡参数信息转换
 * </p>
 * User: 香克斯  Date: 2017/11/5 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserWithdrawParamConvert {
    private UserWithdrawParamConvert() {
    }

    /**
     * 用户提现至银行卡参数信息转换
     *
     * @param userWithdrawReqBo 需要转换的参数信息
     * @return 返回转换之后的Map对象
     */
    public static Map<String, String> paramConvert(UserWithdrawReqBo userWithdrawReqBo) {

        //转换代付json最底层报文
        Map<String, String> hashMap = new HashMap<>();
        //商户订单号
        hashMap.put("trans_no", userWithdrawReqBo.getRequestNo());
        //转账金额
        hashMap.put("trans_money", userWithdrawReqBo.getOrderAmt().toString());
        //收款人姓名
        hashMap.put("to_acc_name", userWithdrawReqBo.getCardHolder());
        //收款人银行帐号
        hashMap.put("to_acc_no", userWithdrawReqBo.getBankCardNo());
        //收款人银行名称
        hashMap.put("to_bank_name", userWithdrawReqBo.getBankName());
        hashMap.put("to_pro_name", "");
        hashMap.put("to_city_name", "");
        hashMap.put("to_acc_dept", "");
        //银行身份证卡号
        hashMap.put("trans_card_id", userWithdrawReqBo.getCardId());
        hashMap.put("trans_mobile", "");
        hashMap.put("trans_summary", userWithdrawReqBo.getRemarks());

        return hashMap;
    }

    /**
     * 结汇汇入申请API 请求参数报文头
     *
     * @param dataContent 请求的参数密文信息
     * @return 返回Map参数
     */
    public static Map<String, String> paramConvert(String dataContent, Long memberId, Long terminalId) {

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("version", "4.0.0");
        hashMap.put("data_type", "json");
        hashMap.put("member_id", String.valueOf(memberId));
        hashMap.put("terminal_id", String.valueOf(terminalId));
        hashMap.put("data_content", dataContent);

        return hashMap;
    }

    /**
     * 请求代付API接口报文头
     *
     * @param hashMap 报文信息
     * @return 返回完整Map报文信息
     */
    public static Map<String, Object> paramConvert(Map<String, String> hashMap) {

        //添加到Map对象中
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("trans_reqData", Lists.newArrayList(hashMap));
        Map<String, Object> reqDatas = new HashMap<>();
        reqDatas.put("trans_reqDatas", Lists.newArrayList(reqData));
        Map<String, Object> transContent = new HashMap<>();
        transContent.put("trans_content", reqDatas);

        return transContent;
    }
}
