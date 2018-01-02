package com.baofu.international.global.account.core.biz.external.baidu;

import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.HttpDict;
import com.baofu.international.global.account.core.common.constant.RedisDict;
import com.baofu.international.global.account.core.common.enums.BaiDuEnum;
import com.baofu.international.global.account.core.common.util.HttpUtil;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度token处理类
 * <p>
 * User: 蒋文哲 Date: 2017/11/4 Version: 1.0
 * </p>
 */
@Slf4j
@Component
public class TokenBizImpl {
    /**
     * 缓存实现类
     */
    @Autowired
    private RedisManager redisManager;
    /**
     * http工具类
     */
    @Autowired
    private HttpUtil httpUtil;

    /**
     * 发送消息
     *
     * @return String
     */
    public String getToken() {
        String accessToken = CommonDict.EMPTY_STR;
        final int timeout = 6500;
        try {
            String redisKey = RedisDict.BAIDU_TOKEN;
            accessToken = redisManager.queryObjectByKey(redisKey);
            if (accessToken == null) {
                String req = String.format(BaiDuEnum.GET_TOKEN.getVal(),
                        BaiDuEnum.GRANT_TYPE.getVal(),
                        BaiDuEnum.CLIENT_ID.getVal(),
                        BaiDuEnum.CLIENT_SECRET.getVal());
                Map<String, String> headParam = new HashMap<>();
                headParam.put("Content-Type", HttpDict.FORMAT_HTML);
                String resp = httpUtil.get(req, Charset.defaultCharset(), headParam);
                Map<String, String> map = JsonUtil.toObject(resp, Map.class);
                accessToken = map.get("access_token");
                redisManager.insertObject(accessToken, redisKey, timeout);
            } else {
                accessToken = accessToken.replace("\"", CommonDict.EMPTY_STR);
            }
        } catch (Exception e) {
            log.error("中枢-获取百度Token-异常:{}", e);
        }
        return accessToken;
    }
}
