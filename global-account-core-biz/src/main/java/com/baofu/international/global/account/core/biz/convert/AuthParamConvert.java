package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.CompanyAuthReqBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.google.common.collect.Maps;
import com.system.commons.utils.DateUtil;

import java.util.Map;

/**
 * <p>
 * 1、方法描述
 * </p>
 * Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 *
 * @author : 香克斯
 */
public final class AuthParamConvert {

    private AuthParamConvert() {

    }

    /**
     * 身份证两要素认证新颜参数转换
     *
     * @param companyAuthReqBo 企业信息
     * @param memberId         在新颜开通的商户号
     * @param terminal         在新颜开通的终端
     * @return 返回map对象
     */
    public static Map<String, String> companyAuthParam(CompanyAuthReqBo companyAuthReqBo, Long memberId, Long terminal) {

        Map<String, String> map = commonParam(memberId, terminal, String.valueOf(companyAuthReqBo.getAuthApplyNo()));
        map.put("mainKey", "entName");
        map.put("entName", companyAuthReqBo.getEntName());
        map.put("frName", companyAuthReqBo.getFrName());
        map.put("frCid", companyAuthReqBo.getFrCid());
        return map;
    }


    /**
     * 身份证三要素认证新颜参数转换
     *
     * @param personAuthReqBo 身份证信息
     * @param memberId        在新颜开通的商户号
     * @param terminal        在新颜开通的终端
     * @return 返回map对象
     */
    public static Map<String, String> bankCardAuthParam(PersonAuthReqBo personAuthReqBo, Long memberId, Long terminal) {

        Map<String, String> map = commonParam(memberId, terminal, String.valueOf(personAuthReqBo.getAuthReqNo()));
        map.put("id_card", personAuthReqBo.getIdCardNo());
        map.put("acc_no", personAuthReqBo.getBankCardNo());
        map.put("id_holder", personAuthReqBo.getIdCardName());
        map.put("is_photo", "noPhoto");
        map.put("verify_element", "123");
        return map;
    }

    /**
     * 身份证两要素认证新颜参数转换
     *
     * @param personAuthReqBo 身份证信息
     * @param memberId        在新颜开通的商户号
     * @param terminal        在新颜开通的终端
     * @return 返回map对象
     */
    public static Map<String, String> idCardAuthParam(PersonAuthReqBo personAuthReqBo, Long memberId, Long terminal) {

        Map<String, String> map = commonParam(memberId, terminal, String.valueOf(personAuthReqBo.getAuthReqNo()));
        map.put("id_card", personAuthReqBo.getIdCardNo());
        map.put("id_holder", personAuthReqBo.getIdCardName());
        map.put("is_photo", "noPhoto");
        return map;
    }

    /**
     * 身份认证参数
     *
     * @param memberId    商户号
     * @param terminal    终端编号
     * @param dataContent 加密字符串
     * @return 返回字符串
     */
    public static Map<String, String> idCardAuthReqParam(Long memberId, Long terminal, String dataContent) {
        Map<String, String> hashMap = baseParam(memberId, terminal);
        hashMap.put("data_type", "json");
        hashMap.put("data_content", dataContent);
        return hashMap;
    }

    private static Map<String, String> commonParam(Long memberId, Long terminal, String transId) {
        Map<String, String> map = baseParam(memberId, terminal);
        map.put("trans_id", transId);
        map.put("trade_date", DateUtil.getCurrent(DateUtil.fullPattern));
        map.put("industry_type", "D13");
        return map;
    }

    private static Map<String, String> baseParam(Long memberId, Long terminal) {
        Map<String, String> hashMap = Maps.newHashMap();
        hashMap.put("member_id", String.valueOf(memberId));
        hashMap.put("terminal_id", String.valueOf(terminal));
        return hashMap;
    }
}
