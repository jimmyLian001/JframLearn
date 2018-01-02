package com.baofu.international.global.account.core.common.constant;

/**
 * 缓存字典
 * <p>
 * User: 蒋文哲 Date: 2017/7/12 Version: 1.0
 * </p>
 */
public final class RedisDict {
    /**
     * 缓存键值
     */
    public static final String KEY = "account_core:";

    /**
     * 提供对外照片图片key
     */
    public static final String GLOBAL_ACCOUNT_IMAGE = "GLOBAL:ACCOUNT:IMAGE:";

    /**
     * 文件下载key
     */
    public static final String PHOTO_DOWNLOAD_KEY = "GLOBAL:ACCOUNT:CORE:PHOTO-DOWNLOAD";

    /**
     * 微信报警
     */
    public static final String BAIDU_TOKEN = KEY.concat("baiDu:token");

    private RedisDict() {

    }
}
