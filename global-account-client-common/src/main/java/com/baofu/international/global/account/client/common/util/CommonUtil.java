package com.baofu.international.global.account.client.common.util;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;


/**
 * 公共工具类
 * <p>
 * 1.
 * </p>
 *
 * @author wukong
 * @version 1.0.0
 * @date 2017/11/4
 */
@Slf4j
public final class CommonUtil {
    private CommonUtil() {

    }

    /**
     * web项目初始化配置
     */
    public static void webInitProperties() {
        Properties pps = new Properties();
        try {
            URL resource = CommonUtil.class.getClassLoader().getResource("/");
            String concat = resource.getPath().concat("META-INF/config.properties");
            File file = new File(concat);
            try (InputStream in = Files.newInputStream(Paths.get(file.toURI()))) {
                pps.load(in);
                for (Map.Entry<Object, Object> item : pps.entrySet()) {
                    if (!"${idc}".equals(String.valueOf(item.getValue()))) {
                        System.setProperty(String.valueOf(item.getKey()), String.valueOf(item.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("client-启动-加载配置异常:", e);
        }
    }

    /**
     * jar项目初始化配置
     */
    public static void initProperties() {
        Properties pps = new Properties();
        InputStream in = null;
        try {
            in = CommonUtil.class.getResourceAsStream("/META-INF/config.properties");
            pps.load(in);
            for (Map.Entry<Object, Object> item : pps.entrySet()) {
                if (!"${idc}".equals(String.valueOf(item.getValue()))) {
                    System.setProperty(String.valueOf(item.getKey()), String.valueOf(item.getValue()));
                }
            }
        } catch (Exception e) {
            log.error("core-启动-加载配置异常:", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("core-启动-关闭io流异常:", e);
                }
            }
        }
    }
}
