package com.baofu.international.global.account.core.biz.external.baidu;

import com.baofu.international.global.account.core.common.constant.HttpDict;
import com.baofu.international.global.account.core.common.enums.BaiDuEnum;
import com.baofu.international.global.account.core.common.util.Base64Util;
import com.baofu.international.global.account.core.common.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 身份证图像验证处理类
 * <p>
 * 1.
 * </p>
 *
 * @author wukong
 * @version 1.0.0
 * @date 2017/11/4
 */
@Slf4j
@Component
public class IdCardBizImpl {

    /**
     *
     */
    @Autowired
    private TokenBizImpl tokenBiz;
    /**
     * http工具类
     */
    @Autowired
    private HttpUtil httpUtil;

    /**
     * 发送消息
     *
     * @param bytes 字节流
     * @return String
     */
    public String idCard(byte[] bytes) throws IOException {
        String url = String.format(BaiDuEnum.ID_CARD.getVal(), tokenBiz.getToken());
        Charset charset = Charset.defaultCharset();
        Map<String, String> param = Maps.newHashMap();
        param.put("detect_direction", "false");
        param.put("id_card_side", "front");
        param.put("image", new String(Base64Util.encode(bytes), charset));
        param.put("detect_risk", "false");
        Map<String, String> headParam = Maps.newHashMap();
        headParam.put("Content-Type", HttpDict.FORMAT_FORM_URLENCODED);
        return httpUtil.post(url, param, headParam, charset);
    }


}
