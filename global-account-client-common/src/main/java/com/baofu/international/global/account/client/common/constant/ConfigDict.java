package com.baofu.international.global.account.client.common.constant;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * gate配置
 * <p>
 *  1.
 * </p>
 *
 * @author wukong
 * @version 1.0.0
 * @date 2017/11/4
 */
@Getter
@ToString
@RefreshScope
@Component
public class ConfigDict {


    /**
     * 宝付开通的统一结汇证书公钥路径，服务器中的路径
     */
    @Value("${settle.public.key.path}")
    private String settlePublicKeyPath;

    /**
     * EXCEL文件模板路径
     */
    @Value("${account.client.excel.temp.path}")
    private String fileTempPath;

    /**
     * 下载文件缓存目录
     */
    @Value("${account.client.excel.cache.path}")
    private String fileCachePath;
}
