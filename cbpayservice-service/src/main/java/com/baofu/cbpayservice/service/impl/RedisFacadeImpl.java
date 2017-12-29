package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.LockBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.facade.RedisFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * RedisFacade对外facade实现
 * User:feng_jiang Date:2017/7/3 ProjectName: cbpayservice
 */
@Slf4j
@Service
public class RedisFacadeImpl implements RedisFacade {

    /**
     * Redis服务
     */
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 请求锁服务
     */
    @Autowired
    private LockBiz lockBiz;

    @Override
    public Result<Boolean> addKey(String key, String value, Long timeout, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            result = new Result<>(redisBiz.addKey(key, value, timeout));
        } catch (Exception e) {
            log.error("新增redis发生异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Redis接口新增Redis结束 result:{}", result);
        return result;
    }

    @Override
    public Result<String> queryByKey(String key, String traceLogId) {
        Result<String> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            result = new Result<>(redisBiz.queryByKey(key));
        } catch (Exception e) {
            log.error("查询Redis发生异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Redis接口查询Redis结束 result:{}", result);
        return result;
    }

    @Override
    public Result<Boolean> modifyByKey(String key, String value, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            result = new Result<>(redisBiz.modifyByKey(key, value));
        } catch (Exception e) {
            log.error("更新redis发生异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Redis接口更新Redis结束 result:{}", result);
        return result;
    }

    @Override
    public Result<Boolean> deleteKey(String key, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            result = new Result<>(redisBiz.deleteKey(key));
        } catch (Exception e) {
            log.error("删除redis发生异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Redis接口删除Redis结束 result:{}", result);
        return result;
    }

    /**
     * redis服务查询锁住状态
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    @Override
    public Result<Boolean> queryRedisLockState(String key, String traceLogId) {
        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Redis锁住状态查询key:{}", key);
            key = Constants.CBPAY_OUTER_REDIS_LOCK + key;
            boolean redisLockState = lockBiz.isLock(key);
            result = new Result<>(redisLockState);
        } catch (Exception e) {
            log.error("查询redis锁住状态请求异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询redis锁住状态成功 result:{}", result);
        return result;
    }

    /**
     * 锁住redis
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    @Override
    @Deprecated
    public Result<Boolean> redisLock(String key, String traceLogId) {

        return redisLock(key, Constants.TIME_OUT, traceLogId);
    }

    /**
     * 锁住redis
     *
     * @param key        key
     * @param timeOut    超时时间
     * @param traceLogId 日志id
     * @return 锁住是否成功
     */
    @Override
    public Result<Boolean> redisLock(String key, Long timeOut, String traceLogId) {
        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Redis 锁住key:{}，超时时间：{}", key, timeOut);
            key = Constants.CBPAY_OUTER_REDIS_LOCK + key;
            lockBiz.lock(key, timeOut);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("redis尝试锁住请求异常，Exception：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("redis锁住请求成功 result:{}", result);
        return result;
    }

    /**
     * 解锁redis
     *
     * @param key        key
     * @param traceLogId 日志id
     * @return 解锁是否成功
     */
    @Override
    public Result<Boolean> redisUnlock(String key, String traceLogId) {
        Result<Boolean> result;
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Redis解锁key:{}", key);
            key = Constants.CBPAY_OUTER_REDIS_LOCK + key;
            lockBiz.unLock(key);
            result = new Result<>(true);
        } catch (Exception e) {
            log.error("redis尝试解锁请求异常，Exception：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("redis解锁成功 result:{}", result);
        return result;
    }
}
