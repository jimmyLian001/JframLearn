package com.baofu.cbpayservice.biz.dispatch;

import com.baofu.cbpayservice.manager.CmdManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * dispatch启动入口
 * <p>
 * 1、随容器启动而启动
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
@Service
public class DispatcherStart {

    /**
     * 分发器线程名称
     */
    @Setter
    @Getter
    private String name;

    /**
     * 分发器列表，Key：分发器线程名称前缀；Value:分发器实例
     */
    @Setter
    @Getter
    private Map<String, Dispatcher> dispatcherMap;

    /**
     * dispatch命令管理服务
     */
    @Autowired
    private CmdManager cmdManager;

    /**
     * 随容器启动而启动，创建dispatcher 守护进程
     */
    public void init() {
        log.info("------- 【{}】正在启动...... -------", getName());
        // cmdManager.reactiveCommandsServerIP(IPUtil.getServerIp());
        SimpleAsyncTaskExecutor cmdDispatchExecutor = new SimpleAsyncTaskExecutor();
        cmdDispatchExecutor.setDaemon(true);
        cmdDispatchExecutor.setConcurrencyLimit(dispatcherMap.size());
        for (String dispatcherName : dispatcherMap.keySet()) {
            cmdDispatchExecutor.setThreadNamePrefix(dispatcherName);
            cmdDispatchExecutor.execute(dispatcherMap.get(dispatcherName));
        }
        log.info("------- 【{}】启动完毕! -------", getName());
    }
}
