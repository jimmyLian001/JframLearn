package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.common.constant.Constants;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * 结汇汇入申请参数转换
 * <p>
 * 1、结汇汇入申请API 请求参数报文头
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SettleApplyParamConvert {

    /**
     * 结汇汇入申请API 请求参数报文头
     *
     * @param dataContent 请求的参数密文信息
     * @return 返回Map参数
     */
    public static Map<String, String> paramConvert(String dataContent, Long memberId, Long terminalId) {

        Map<String, String> hashMap = Maps.newHashMap();
        hashMap.put("version", "1.0.0");
        hashMap.put("dataType", Constants.DATA_TYPE_JSON);
        hashMap.put("memberId", String.valueOf(memberId));
        hashMap.put("terminalId", String.valueOf(terminalId));
        hashMap.put("dataContent", dataContent);

        return hashMap;
    }
}
