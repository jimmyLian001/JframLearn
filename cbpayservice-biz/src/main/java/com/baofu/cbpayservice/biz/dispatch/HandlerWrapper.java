package com.baofu.cbpayservice.biz.dispatch;

import com.baofu.cbpayservice.biz.handler.CmdHandler;
import com.baofu.cbpayservice.dal.models.BizCmdDo;
import com.system.ext.logback.util.TraceLogIdUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;

/**
 * 任务执行线程管理器
 * <p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class HandlerWrapper implements Runnable {

    /**
     * 处理命令对象
     */
    private BizCmdDo command;

    /**
     * 具体命令处理类
     */
    private CmdHandler cmdHandler;

    @Override
    public void run() {
        try {

            //设置日志ID
            MDC.put(SystemMarker.TRACE_LOG_ID, TraceLogIdUtil.createTraceLogId());

            cmdHandler.execute(command);
        } catch (Exception t) {
            log.error("executeCmdTread 异常{},{}", t.getMessage(), t);
            cmdHandler.handlerException(command, t.getMessage());
        }
    }
}
