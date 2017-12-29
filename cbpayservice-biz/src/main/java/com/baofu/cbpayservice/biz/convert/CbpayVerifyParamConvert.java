package com.baofu.cbpayservice.biz.convert;

import com.alibaba.fastjson.JSON;
import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.cbpayservice.biz.models.CbPayVerifyReqBo;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数对象转换
 * <p>
 * 1、身份认证参数
 * </p>
 * User: wanght Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
public final class CbpayVerifyParamConvert {

    private CbpayVerifyParamConvert() {

    }

    /**
     * 身份认证参数
     *
     * @param cbPayVerifyReqBo 参数
     * @return 返回字符串
     */
    public static String paramConvert(CbPayVerifyReqBo cbPayVerifyReqBo) {
        Map<String, String> map = new HashMap<>();
        map.put("member_id", String.valueOf(cbPayVerifyReqBo.getMemberId()));
        map.put("terminal_id", cbPayVerifyReqBo.getTerminalId());
        map.put("trans_id", cbPayVerifyReqBo.getTransId());
        map.put("trade_date", cbPayVerifyReqBo.getTransDate());
        map.put("id_card", cbPayVerifyReqBo.getIdCard());
        map.put("id_holder", cbPayVerifyReqBo.getIdName());

        return JSON.toJSONString(map);
    }

    /**
     * 身份认证参数
     *
     * @param cbPayVerifyReqBo 参数
     * @param dataContent     加密字符串
     * @return 返回字符串
     */
    public static String paramConvert(CbPayVerifyReqBo cbPayVerifyReqBo, String dataContent) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("member_id=").append(cbPayVerifyReqBo.getMemberId());
        stringBuilder.append("&terminal_id=").append(cbPayVerifyReqBo.getTerminalId());
        stringBuilder.append("&data_type=").append("json");
        stringBuilder.append("&data_content=").append(dataContent);
        return stringBuilder.toString();
    }

    /**
     * 身份认证数据库参数
     *
     * @param cbPayVerifyReqBo 参数
     * @param cbPayVerifyResBo 加密字符串
     * @return 返回字符串
     */
    public static FiCbPayVerifyDo paramConvert(CbPayVerifyReqBo cbPayVerifyReqBo, CbPayVerifyResBo cbPayVerifyResBo) {

        FiCbPayVerifyDo fiCbPayVerifyDo = new FiCbPayVerifyDo();
        fiCbPayVerifyDo.setOrderId(cbPayVerifyResBo.getOrderId());
        fiCbPayVerifyDo.setTerminalId(cbPayVerifyReqBo.getTerminalId());
        fiCbPayVerifyDo.setMemberId(cbPayVerifyReqBo.getMemberId());
        fiCbPayVerifyDo.setTransDate(cbPayVerifyReqBo.getTransDate());
        fiCbPayVerifyDo.setIdName(cbPayVerifyReqBo.getIdName());
        fiCbPayVerifyDo.setIdCard(SecurityUtil.desEncrypt(cbPayVerifyReqBo.getIdCard(),
                Constants.CARD_DES_PASSWD));
        fiCbPayVerifyDo.setFeeFlag(cbPayVerifyResBo.getFeeFlag());
        fiCbPayVerifyDo.setTransNo(cbPayVerifyResBo.getTransNo());
        fiCbPayVerifyDo.setCode(cbPayVerifyResBo.getCode());
        fiCbPayVerifyDo.setDesc(cbPayVerifyResBo.getDesc());
        fiCbPayVerifyDo.setTransId(cbPayVerifyResBo.getTransId());
        return fiCbPayVerifyDo;
    }

}
