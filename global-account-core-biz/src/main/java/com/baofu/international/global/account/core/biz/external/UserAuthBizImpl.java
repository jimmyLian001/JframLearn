package com.baofu.international.global.account.core.biz.external;

import com.baofu.international.global.account.core.biz.convert.AuthParamConvert;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.common.constant.AuthDict;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.IdCardAuthStateEnum;
import com.baofu.international.global.account.core.common.util.Base64Util;
import com.baofu.international.global.account.core.common.util.HttpUtil;
import com.baofu.international.global.account.core.common.util.RsaCodingUtil;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 用户信息认证服务
 * <p>
 * 1、方法描述
 * </p>
 *
 * @author : 香克斯
 * @version : 1.0
 * @date : 2017/11/4
 */
@Slf4j
@Component
public class UserAuthBizImpl {

    /**
     * 身份证验证请求后缀
     */
    private static final String ID_CARD_AUTH_REQ_URL = "/idcard/v2/auth";
    /**
     * 银行卡验证请求后缀
     */
    private static final String BANK_CARD_AUTH_REQ_URL = "/bankcard/v1/auth";

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
     * 身份证两要素认证，身份证和姓名认证
     *
     * @param personAuthReqBo 两要素认证请求参数信息
     */
    public IdCardAuthStateEnum idCardAuth(PersonAuthReqBo personAuthReqBo) {
        try {
            log.info("身份证两要素认证，身份证和姓名认证，请求参数信息：{}", personAuthReqBo);
            //企业
            if (AuthDict.FLAG_N.equals(configDict.getIdAuthFlag())) {
                log.info("不进行二要素认证！");
                return IdCardAuthStateEnum.find(0);
            }
            //参数信息转换
            Map<String, String> hashMap = AuthParamConvert.idCardAuthParam(personAuthReqBo, configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal());
            String data = JsonUtil.toJSONString(hashMap);
            log.info("身份证两要素认证转换JSON之后参数：{}", data);
            String securityStr = Base64Util.encode(data);
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(securityStr, configDict.getXinYanPrivateKeyPath(),
                    configDict.getXinYanPrivateKeyPass());
            Map<String, String> reqHashMap = AuthParamConvert.idCardAuthReqParam(configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal(), dataContent);
            String url = configDict.getXinYanReqUrl() + ID_CARD_AUTH_REQ_URL;
            log.info("身份证两要素认证请求地址：{},请求参数：{}", url, reqHashMap);
            String responseStr = httpUtil.post(url, reqHashMap, Charset.defaultCharset());
            log.info("身份证两要素认证请求响应信息：{}", responseStr);

            return handleResult(responseStr);
        } catch (Exception e) {
            log.error("身份证两要素认证失败：{}", e);
            return IdCardAuthStateEnum.AUTH_SYS_ERROR;
        }
    }

    /**
     * 银行卡三要素认证
     *
     * @param personAuthReqBo 认证信息
     */

    public IdCardAuthStateEnum bankCardAuth(PersonAuthReqBo personAuthReqBo) {
        try {
            log.info("身份证三要素认证，身份证和姓名认证，请求参数信息：{}", personAuthReqBo);
            //企业
            if (AuthDict.FLAG_N.equals(configDict.getCardAuthFlag())) {
                log.info("不进行三要素认证！");
                return IdCardAuthStateEnum.find(0);
            }
            //参数信息转换
            Map<String, String> hashMap = AuthParamConvert.bankCardAuthParam(personAuthReqBo, configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal());
            String data = JsonUtil.toJSONString(hashMap);
            log.info("身份证三要素认证转换JSON之后参数：{}", data);
            String securityStr = Base64Util.encode(data);
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(securityStr, configDict.getXinYanPrivateKeyPath(),
                    configDict.getXinYanPrivateKeyPass());
            Map<String, String> reqHashMap = AuthParamConvert.idCardAuthReqParam(configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal(), dataContent);
            String url = configDict.getXinYanReqUrl() + BANK_CARD_AUTH_REQ_URL;
            log.info("身份证三要素认证请求地址：{},请求参数：{}", url, reqHashMap);
            String responseStr = httpUtil.post(url, reqHashMap, Charset.defaultCharset());
            log.info("身份证三要素认证请求响应信息：{}", responseStr);

            return handleResult(responseStr);

        } catch (Exception e) {
            log.error("身份证三要素认证失败：{}", e);
            return IdCardAuthStateEnum.AUTH_CHANNEL_ERROR;
        }
    }

    /**
     * 结果处理
     *
     * @param responseStr 响应结果
     * @return 处理结果
     */
    private IdCardAuthStateEnum handleResult(String responseStr) {
        if (StringUtils.isBlank(responseStr)) {
            return IdCardAuthStateEnum.AUTH_CHANNEL_ERROR;
        }
        Map responseMap = JsonUtil.toObject(responseStr, Map.class);
        //请求调用状态，不是认证状态
        if (!(boolean) responseMap.get(AuthDict.SUCCESS)) {
            //如果渠道调用异常，提示商户系统繁忙，请稍后再试。
            log.error("渠道调用异常：{}", responseMap.get(AuthDict.ERROR_MSG));
            return IdCardAuthStateEnum.AUTH_SYS_ERROR;
        }
        Map dataMap = JsonUtil.toObject(String.valueOf(responseMap.get("data")), Map.class);

        return IdCardAuthStateEnum.find(Integer.parseInt(dataMap.get("code") + ""));
    }
}
