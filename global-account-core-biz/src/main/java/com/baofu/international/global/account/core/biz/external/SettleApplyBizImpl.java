package com.baofu.international.global.account.core.biz.external;

import com.baofu.international.global.account.core.biz.convert.SettleApplyParamConvert;
import com.baofu.international.global.account.core.biz.models.SettleApplyBo;
import com.baofu.international.global.account.core.biz.models.SettleQueryRespBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.HttpUtil;
import com.baofu.international.global.account.core.common.util.RsaCodingUtil;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 结汇汇入申请
 * <p>
 * 1、提现至国内备付金成功之后，需在宝付汇入申请中申请，以待做匹配操作
 * 2、查询汇入申请结果
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Component
public class SettleApplyBizImpl {

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * http
     */
    @Autowired
    private HttpUtil httpUtil;

    /**
     * 汇入申请请求URL后缀
     */
    private static final String SETTLE_APPLY_REQ_URL = "/settle/apply";

    /**
     * 汇入申请查询请求URL后缀
     */
    private static final String SETTLE_QUERY_REQ_URL = "/settle/search";

    /**
     * 提现至国内备付金成功之后，需在宝付汇入申请中申请，以待做匹配操作
     *
     * @param settleApplyBo 汇入申请操作信息
     * @return 返回是否申请成功标识
     */
    public Boolean settleApply(SettleApplyBo settleApplyBo) {

        try {
            String dataJson = JsonUtil.toJSONString(settleApplyBo);
            //请求URL
            String reqUrl = configDict.getSettleReqUrl() + SETTLE_APPLY_REQ_URL;
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(dataJson, configDict.getSettlePrivateKeyPath(),
                    configDict.getSettlePrivateKeyPass());
            //转换请求结汇申请api参数报文
            Map<String, String> reqMap = SettleApplyParamConvert.paramConvert(dataContent, configDict.getSettleMemberId(),
                    configDict.getSettleTerminal());
            log.info("请求汇入API接口，接口地址：{},参数信息：{}", reqUrl, reqMap);
            //请求结汇申请API接口
            String responseStr = httpUtil.post(reqUrl, reqMap, Charset.defaultCharset());
            log.info("请求汇入API返回参数信息：{}", responseStr);
            //转换成map
            Map<String, String> respHashMap = JsonUtil.toObject(responseStr, Map.class);
            if (Boolean.FALSE.toString().equals(respHashMap.get("success"))) {
                log.error("汇入申请API返回失败，失败原因：{}", respHashMap.get("errorMsg"));
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190007, respHashMap.get("errorMsg"));
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("宝付内卡结汇API申请失败：", e);
        }
        return Boolean.FALSE;
    }

    /**
     * 查询汇入申请结果
     *
     * @param remitReqNo 申请时的唯一流水号
     * @return 返回查询结果
     */
    public SettleQueryRespBo settleQuery(String remitReqNo) {

        try {
            Map<String, String> reqParam = Maps.newHashMap();
            reqParam.put("remitReqNo", remitReqNo);
            //请求URL
            String reqUrl = configDict.getSettleReqUrl() + SETTLE_QUERY_REQ_URL;
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(JsonUtil.toJSONString(reqParam),
                    configDict.getSettlePrivateKeyPath(), configDict.getSettlePrivateKeyPass());
            //转换请求结汇申请api参数报文
            Map<String, String> reqMap = SettleApplyParamConvert.paramConvert(dataContent, configDict.getSettleMemberId(),
                    configDict.getSettleTerminal());
            log.info("请求汇入查询接口，接口地址：{},参数信息：{}", reqUrl, reqMap);
            //请求结汇申请API接口
            String responseStr = httpUtil.post(reqUrl, reqMap, Charset.defaultCharset());
            log.info("请求汇入查询返回参数信息：{}", responseStr);
            //转换成map
            Map<String, String> respHashMap = JsonUtil.toObject(responseStr, Map.class);
            if (Boolean.FALSE.toString().equals(respHashMap.get("success"))) {
                log.error("汇入申请查询返回失败，失败原因：{}", respHashMap.get("errorMsg"));
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190008, respHashMap.get("errorMsg"));
            }
            String respData = RsaCodingUtil.decryptByPubCerFile(respHashMap.get("result"), configDict.getSettlePublicKeyPath());
            log.info("汇入申请查询密文解密后参数信息:{}", respData);

            return JsonUtil.toObject(respData, SettleQueryRespBo.class);
        } catch (Exception e) {
            log.error("汇入申请查询返回异常：", e);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190008, ExceptionUtils.getErrorMsg(e));
        }
    }
}
