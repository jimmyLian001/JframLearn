package com.baofu.cbpayservice.manager;

/**
 * 基本命令锁管理服务
 * <p>
 * 1、获取业务锁，等待n秒
 * 2、获取业务锁
 * 3、手动释放业务锁
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
public interface BizLockManager {

    /**
     * 1、获取业务锁，等待n秒
     *
     * @param lockName 锁名称
     * @param seconds  @param seconds  锁住时间，默认为3分钟
     * @return T/F
     */
    boolean getLockWaiting(String lockName, Long seconds);

    /**
     * 3、手动释放业务锁
     *
     * @param lockName 锁名
     */
    void releaseLock(String lockName);

}
