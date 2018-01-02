package com.baofu.international.global.account.core.biz.external;

import com.baofu.international.global.account.core.biz.convert.AuthParamConvert;
import com.baofu.international.global.account.core.biz.models.CompanyAuthReqBo;
import com.baofu.international.global.account.core.common.constant.AuthDict;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.CompanyAuthStateEnum;
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
public class CompanyAuthBizImpl {


    /**
     * 银行卡验证请求后缀
     */
    private static final String COMPANY_AUTH_REQ_URL = "/biz/company/v1/check/legal";

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
     * 银行卡三要素认证
     *
     * @param companyAuthReqBo 认证信息
     */

    public CompanyAuthStateEnum companyAuth(CompanyAuthReqBo companyAuthReqBo) {
        try {
            log.info("企业认证请求参数信息：{}", companyAuthReqBo);
            //企业
            if (AuthDict.FLAG_N.equals(configDict.getCompanyAuthFlag())) {
                log.info("不进行企业实名认证！");
                return CompanyAuthStateEnum.find(0);
            }
            //参数信息转换
            Map<String, String> hashMap = AuthParamConvert.companyAuthParam(companyAuthReqBo, configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal());
            String data = JsonUtil.toJSONString(hashMap);
            log.info("企业认证转换JSON之后参数：{}", data);
            String securityStr = Base64Util.encode(data);
            //参数使用私钥加密
            String dataContent = RsaCodingUtil.encryptByPriPfxFile(securityStr, configDict.getXinYanPrivateKeyPath(),
                    configDict.getXinYanPrivateKeyPass());
            Map<String, String> reqHashMap = AuthParamConvert.idCardAuthReqParam(configDict.getXinYanMemberId(),
                    configDict.getXinYanTerminal(), dataContent);
            String url = configDict.getXinYanReqUrl() + COMPANY_AUTH_REQ_URL;
            log.info("企业认证请求地址：{},请求参数：{}", url, reqHashMap);
            String responseStr = httpUtil.post(url, reqHashMap, Charset.defaultCharset());
            log.info("企业认证请求响应信息：{}", responseStr);
            if (StringUtils.isBlank(responseStr)) {
                return CompanyAuthStateEnum.AUTH_CHANNEL_ERROR;
            }
            Map responseMap = JsonUtil.toObject(responseStr, Map.class);
            //请求调用状态，不是认证状态
            if (!(boolean) responseMap.get(AuthDict.SUCCESS)) {
                //如果渠道调用异常，提示商户系统繁忙，请稍后再试。
                log.error("渠道调用异常：{}", responseMap.get(AuthDict.ERROR_MSG));
                return CompanyAuthStateEnum.AUTH_SYS_ERROR;
            }
            Map dataMap = JsonUtil.toObject(String.valueOf(responseMap.get("data")), Map.class);

            return CompanyAuthStateEnum.find(Integer.parseInt(dataMap.get("code") + ""));
        } catch (Exception e) {
            log.error("企业认证失败：{}", e);
            return CompanyAuthStateEnum.AUTH_SYS_ERROR;
        }
    }

}
