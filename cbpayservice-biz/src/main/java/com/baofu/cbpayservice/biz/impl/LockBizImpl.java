package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.LockBiz;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接口防重复调用处理方法
 * <p>
 * User: 不良人 Date:2017/5/26 ProjectName: feature_test Version: 1.0
 */
@Slf4j
@Service
public class LockBizImpl implements LockBiz {

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 接口锁
     * 1、判断是否锁住
     * 2、已锁住就抛出异常提示不能重复调用
     * 3、未锁住就加锁
     *
     * @param key 唯一标识
     * @return 返回锁的key
     */
    @Override
    public String lock(String key, Long timeOut) {

        // 判定商户是否锁住
        Boolean lockFlag = Boolean.FALSE;
        //查询锁
        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            lockFlag = Boolean.TRUE;
        }
        //已锁住就抛出异常提示不能重复调用
        log.info("call 锁标识：{},是否锁住：{}", key, lockFlag);
        if (lockFlag) {
            log.info("call key：{},锁住失败，请稍后重试", key);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }
        //未锁住就加锁，加锁失败抛出异常
        lockFlag = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), timeOut);
        if (!lockFlag) {
            log.info("call key：{},锁住失败，请稍后重试", key);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
        }
        log.info("call key：{}锁定成功", key);

        return key;
    }

    /**
     * 锁释放
     *
     * @param key 锁标识
     */
    @Override
    public void unLock(String key) {
        redisManager.deleteObject(key);
    }

    /**
     * 接口锁
     * 1、判断是否锁住
     * 2、已锁住就抛出异常提示不能重复调用
     *
     * @param key 唯一标识
     * @return true:锁住|false:未锁住
     */
    @Override
    public Boolean isLock(String key) {

        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
