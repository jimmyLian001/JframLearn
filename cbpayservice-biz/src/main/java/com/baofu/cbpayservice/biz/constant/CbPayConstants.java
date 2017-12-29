package com.baofu.cbpayservice.biz.constant;

import com.baofu.cbpayservice.common.util.rsa.RsaCodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;

@Component
@Slf4j
public class CbPayConstants {

    /**
     * 私钥
     */
    public static PrivateKey privateKey;

    /**
     * 私钥路径
     */
    @Value(("${key_file_path}"))
    private String filePath;

    /**
     * 私钥密码
     */
    @Value(("${store_password}"))
    private String password;

    /**
     * 初始化私钥
     */
    @PostConstruct
    public void init() {
        log.info("加载秘钥start.........");
        try {
            privateKey = RsaCodingUtil.readPrivateKey(filePath, password);
            log.info("PrivateKey:{}", privateKey.getAlgorithm());
            log.info("加载秘钥end.........");
        } catch (Exception e) {
            log.error("加载秘钥出错", e);
        }
    }
}
