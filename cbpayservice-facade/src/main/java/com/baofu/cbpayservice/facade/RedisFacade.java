package com.baofu.cbpayservice.facade;

import com.system.commons.result.Result;

/**
 * 功能：RedisFacade对外接口服务
 * User: feng_jiang Date:2017/7/3 ProjectName: cbpayservice
 */
public interface RedisFacade {

    /**
     * 功能：根据key添加一个值和保存时间
     *
     * @param key        redis的key唯一
     * @param value      redis的值
     * @param timeout    redis保存时间
     * @param traceLogId 日志ID
     */
    Result<Boolean> addKey(String key, String value, Long timeout, String traceLogId);

    /**
     * 功能：根据key查询redis中的值
     *
     * @param key        redis的key唯一
     * @param traceLogId 日志ID
     * @return 查询结果
     */
    Result<String> queryByKey(String key, String traceLogId);

    /**
     * 功能：根据key修改redis中的值
     *
     * @param key        redis的key唯一
     * @param value      redis的值
     * @param traceLogId 日志ID
     * @return 更新结果
     */
    Result<Boolean> modifyByKey(String key, String value, String traceLogId);

    /**
     * 功能：根据key删除redis中的值
     *
     * @param key        redis的key唯一
     * @param traceLogId 日志ID
     * @return 删除结果
     */
    Result<Boolean> deleteKey(String key, String traceLogId);

    /**
     * redis服务查询锁住状态
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    Result<Boolean> queryRedisLockState(String key, String traceLogId);

    /**
     * 锁住redis
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    @Deprecated
    Result<Boolean> redisLock(String key, String traceLogId);

    /**
     * 锁住redis
     *
     * @param key        key
     * @param timeOut    超时时间
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    Result<Boolean> redisLock(String key, Long timeOut, String traceLogId);

    /**
     * 解锁redis
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 解锁是否成功
     */
    Result<Boolean> redisUnlock(String key, String traceLogId);
}
