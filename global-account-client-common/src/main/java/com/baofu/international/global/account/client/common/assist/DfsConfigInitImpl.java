package com.baofu.international.global.account.client.common.assist;

import com.baofoo.dfs.client.core.DfsConfig;
import com.baofoo.dfs.client.util.FastDFSUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

/**
 * dfs初始化配置
 * <p>
 * 1.
 * </p>
 *
 * @author 欧西涛
 * @version 1.0.0
 * @date 2017/11/4
 */
@Slf4j
@Setter
@Getter
@ToString
@Lazy(false)
public class DfsConfigInitImpl {

    /**
     * 服务连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 30000;
    /**
     * DFS网络超时时间
     */
    private static final int NET_WORK_TIMEOUT = 30000;
    /**
     * DFS 最大连接数
     */
    private static final int MAX_IDLE = 20;
    /**
     * DFS 总连接数
     */
    private static final int MAX_TOTAL = 20;
    /**
     * zookeeper 服务地址
     */
    private String zookeeperAddress;
    /**
     * DFS tracker 地址列表
     */
    private String trackerAdds;
    /**
     * DFS tracker http 端口
     */
    private int trackerHttpPort;
    /**
     * DFS http 服务地址
     */
    private String httpServer;
    /**
     * DFS 密钥
     */
    private String secretKey = "1qazXsw28080";
    /**
     * DFS 最小连接数
     */
    private int minIdle = 1;
    /**
     * DFS 临时文件目录
     */
    private String uploadTempDir;


    /**
     * 初始化dfs配置
     */
    public void init() {
        log.info("cgw-cross-border-boc_dfs初始化...");
        log.info("connectTimeout:{}", CONNECT_TIMEOUT);
        log.info("httpServer:{}", httpServer);
        log.info("maxIdle:{}", MAX_IDLE);
        log.info("minIdle:{}", minIdle);
        log.info("maxTotal:{}", MAX_TOTAL);
        log.info("networkTimeout:{}", NET_WORK_TIMEOUT);
        log.info("secretKey:{}", secretKey);
        log.info("trackerAdds:{}", trackerAdds);
        log.info("uploadTempDir:{}", uploadTempDir);
        log.info("trackerHttpPort:{}", trackerHttpPort);
        log.info("zookeeperAddress:{}", zookeeperAddress);

        DfsConfig.set_connect_timeout(CONNECT_TIMEOUT);
        DfsConfig.set_http_server(httpServer);
        DfsConfig.set_max_idle(MAX_IDLE);
        DfsConfig.set_min_idle(minIdle);
        DfsConfig.set_max_total(MAX_TOTAL);
        DfsConfig.set_network_timeout(NET_WORK_TIMEOUT);
        DfsConfig.set_secret_key(secretKey);
        DfsConfig.set_tracker_adds(trackerAdds);
        DfsConfig.set_upload_temp_dir(uploadTempDir);
        DfsConfig.set_tracker_http_port(trackerHttpPort);
        DfsConfig.set_zookeeper_address(zookeeperAddress);

        FastDFSUtil.init();
        log.info("dfs 系统参数初始化 结束....... ");
    }
}
