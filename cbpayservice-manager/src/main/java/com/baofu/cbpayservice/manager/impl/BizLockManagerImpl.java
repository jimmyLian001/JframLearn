package com.baofu.cbpayservice.manager.impl;


import com.baofu.cbpayservice.manager.BizLockManager;
import com.baofu.cbpayservice.manager.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基本命令锁管理服务
 * <p>
 * 1、获取锁等待时间
 * 2、获取业务锁
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
@Component
public class BizLockManagerImpl implements BizLockManager {

    /**
     * dispatch锁中默认值
     */
    private static final String dispatchLock = "DISPATCH_LOCK";
    /**
     * redis 操作
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 1、获取锁等待时间
     *
     * @param lockName 锁名称
     * @param seconds  锁住时间，锁住时间，默认为3分钟
     * @return boolean
     */
    @Override
    public boolean getLockWaiting(final String lockName, final Long seconds) {

        if (StringUtils.isBlank(lockName)) {
            return Boolean.FALSE;
        }

        return redisManager.lockRedis(lockName, dispatchLock, seconds);
    }

    /**
     * 3、手动释放业务锁
     *
     * @param lockName 锁名
     */
    @Override
    public void releaseLock(String lockName) {

        redisManager.deleteObject(lockName);
    }
}
