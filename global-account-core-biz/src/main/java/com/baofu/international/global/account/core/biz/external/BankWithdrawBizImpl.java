package com.baofu.international.global.account.core.biz.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofu.international.global.account.core.biz.convert.UserWithdrawParamConvert;
import com.baofu.international.global.account.core.biz.models.UserWithdrawReqBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.Base64Util;
import com.baofu.international.global.account.core.common.util.HttpUtil;
import com.baofu.international.global.account.core.common.util.RsaCodingUtil;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 结汇完成之后提现至用户银行卡中相关操作
 * <p>
 * 1、提现至用户绑定的银行卡
 * 2、提现查询
 * </p>
 * User: 香克斯  Date: 2017/11/5 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Component
public class BankWithdrawBizImpl {

    /**
     * 代付申请API请求后缀
     */
    private static final String TRANSFER_APPLY_URL = "/baofoo-fopay/pay/BF0040001.do";

    /**
     * 代付申请API查询请求后缀
     */
    private static final String TRANSFER_QUERY_URL = "/baofoo-fopay/pay/BF0040002.do";
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
     * 用户结汇完成之后转账至国内银行卡请求参数信息
     *
     * @param userWithdrawReqBo 转账至国内银行卡请求参数信息
     * @throws Exception  异常信息
     */
    public void userWithdrawApply(UserWithdrawReqBo userWithdrawReqBo) throws Exception {

        Map<String, String> hashMap = UserWithdrawParamConvert.paramConvert(userWithdrawReqBo);
        String data = JsonUtil.toJSONString(UserWithdrawParamConvert.paramConvert(hashMap));
        log.info("代付API请求明文信息：{}", data);
        String securityStr = Base64Util.encode(data);
        //参数使用私钥加密
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(securityStr, configDict.getSettlePrivateKeyPath(),
                configDict.getSettlePrivateKeyPass());
        //转换请求结汇申请api参数报文
        Map<String, String> reqMap = UserWithdrawParamConvert.paramConvert(dataContent,
                configDict.getSettleMemberId(), configDict.getSettleTerminal());
        //请求URL地址
        String reqUrl = configDict.getTransferReqUrl() + TRANSFER_APPLY_URL;
        log.info("请求代付API接口，接口地址：{},参数信息：{}", reqUrl, reqMap);
        //请求结汇申请API接口
        String responseStr = httpUtil.post(reqUrl, reqMap, Charset.defaultCharset());
        log.info("请求代付API返回参数信息：{}", responseStr);
        String respDecrypt = RsaCodingUtil.decryptByPubCerFile(responseStr, configDict.getSettlePublicKeyPath());
        if (StringUtils.isBlank(respDecrypt)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190005, "代付API申请返回密文解密失败");
        }
        respDecrypt = Base64Util.decode(respDecrypt);
        log.info("请求代付API返回参数公钥解密之后信息:{}", respDecrypt);
        this.parseRetJson(respDecrypt);
    }

    /**
     * 解析返回结果
     *
     * @param respDecrypt 代付返回结果
     */
    private void parseRetJson(String respDecrypt) {
        JSONObject jsonObject = JSON.parseObject(respDecrypt);
        jsonObject = JSON.parseObject(jsonObject.getString("trans_content"));
        JSONObject transHead = JSON.parseObject(jsonObject.getString("trans_head"));
        if (!"0000".equals(transHead.getString("return_code"))) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190036, transHead.getString("return_msg"));
        }
    }

    /**
     * 代付API接口查询
     *
     * @param requestNo 请求查询代付的订单编号
     */
    public void userWithdrawQuery(String requestNo) {

        try {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("trans_batchid", "");
            hashMap.put("trans_no", requestNo);
            String data = JsonUtil.toJSONString(UserWithdrawParamConvert.paramConvert(hashMap));
            log.info("代付API查询请求明文信息：{}", data);
            String securityStr = Base64Util.encode(data);
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(securityStr, configDict.getSettlePrivateKeyPath(),
                    configDict.getSettlePrivateKeyPass());
            //转换请求结汇申请api查询参数报文
            Map<String, String> reqMap = UserWithdrawParamConvert.paramConvert(dataContent,
                    configDict.getSettleMemberId(), configDict.getSettleTerminal());
            //请求URL地址
            String reqUrl = configDict.getTransferReqUrl() + TRANSFER_QUERY_URL;
            log.info("请求代付API查询接口，接口地址：{},参数信息：{}", reqUrl, reqMap);
            //请求结汇申请API查询接口
            String responseStr = httpUtil.post(reqUrl, reqMap, Charset.defaultCharset());
            log.info("请求代付API查询返回参数信息：{}", responseStr);
            String respDecrypt = RsaCodingUtil.decryptByPubCerFile(responseStr, configDict.getSettlePublicKeyPath());
            if (StringUtils.isBlank(respDecrypt)) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190005, "代付API查询结果返回密文解密失败");
            }
            respDecrypt = Base64Util.decode(respDecrypt);
            log.info("请求代付API查询返回参数公钥解密之后信息:{}", respDecrypt);

        } catch (Exception e) {
            log.error("代付API查询异常，异常原因：", e);
        }
    }
}
