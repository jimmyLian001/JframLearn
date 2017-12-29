package com.baofu.cbpayservice.biz.dispatch;

import com.baofu.cbpayservice.biz.handler.CmdHandler;
import com.baofu.cbpayservice.dal.models.BizCmdDo;
import com.baofu.cbpayservice.manager.CmdManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.dao.CannotAcquireLockException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务分发器线程
 * <p>
 * 1、线程任务执行方法
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
@Getter
@Setter
public class Dispatcher implements Runnable {

    /**
     * 业务类型名称
     */
    private String name;

    /**
     * 初始线程处理数
     */
    private int coreSize = 2;

    /**
     * 最大线程数
     */
    private int maxSize = 20;

    /**
     * 最大队列数
     */
    private int queueSize = 2000;

    /**
     * 空闲线程最大闲置时间
     */
    private long keepAliveTime = 24 * 60 * 60;

    /**
     * 无命令处理时休息时常(秒)
     */
    private long noTaskSleepSeconds = 60;

    /**
     * 线程池接收新任务阀值
     */
    private int hungrySize = (int) (queueSize * 0.8);

    /**
     * 线程池
     */
    private WorkerPool pool;

    /**
     * 任务处理列
     */
    private BlockingQueue<Runnable> queue;

    /**
     * 命令管理器，查询待处理命令
     */
    private CmdManager cmdManager;

    /**
     * 命令处理Handler
     */
    private CmdHandler cmdHandler;

    /**
     * 1、线程任务执行方法
     */
    @Override
    public void run() {

        queue = new ArrayBlockingQueue<Runnable>(queueSize);
        pool = new WorkerPool(coreSize, maxSize, keepAliveTime, TimeUnit.SECONDS, queue);

        while (true) {
            try {
                MDC.put(SystemMarker.TRACE_LOG_ID, UUID.randomUUID().toString());
                if (queue.size() >= hungrySize) {
                    sleep(10L);
                    continue;
                }
                List<BizCmdDo> commands = cmdManager.lockAndListCommands(name, queueSize - queue.size());

                // 队列没有任务则进行休眠
                if (null == commands || commands.isEmpty()) {
                    log.info("队列名称:{},查询任务为空", name);
                    sleep(noTaskSleepSeconds);
                    continue;
                }

                for (BizCmdDo command : commands) {
                    pool.execute(new HandlerWrapper(command, cmdHandler));
                }
                //休眠
                sleep(5L);
            } catch (CannotAcquireLockException e) {
                log.warn("数据库抢锁失败：{}", name);
            } catch (Exception t) {
                log.error("分发器[{}]执行失败：{}", name, t);
            } finally {
                cmdManager.releaseLock(name);
            }
        }
    }

    /**
     * 休眠
     *
     * @param timeOut 休眠时间
     */
    private void sleep(Long timeOut) {
        try {
            TimeUnit.SECONDS.sleep(timeOut);
        } catch (Exception e) {
            log.error("程序执行休眠失败：{}", e);
        }
    }

    /**
     * 中断线程
     */
    public void destroy() {
        log.warn("收到分发器[{}]停止通知!!", name);
        pool.shutdown();
        Thread.interrupted();
    }

    /**
     * 任务处理线程池
     */
    private class WorkerPool extends ThreadPoolExecutor {
        public WorkerPool(int coreSize, int maxSize, long keepAlive,
                          TimeUnit timeUnit, BlockingQueue<Runnable> queue) {
            super(coreSize, maxSize, keepAlive, timeUnit, queue);
        }

        @Override
        protected void afterExecute(Runnable runnable,
                                    Throwable throwable) {
            super.afterExecute(runnable, throwable);
        }
    }

}
