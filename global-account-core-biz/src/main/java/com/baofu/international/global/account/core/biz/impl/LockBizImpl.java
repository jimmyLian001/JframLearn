package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.LockBiz;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.FlagEnum;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：接口防重复调用处理方法
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
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
    public String lock(String key) {

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
            log.error("call 锁标识：{},是否锁住：{}，请勿频繁操作！", key, lockFlag);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190015);
        }
        //未锁住就加锁，加锁失败抛出异常
        lockFlag = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
        if (!lockFlag) {
            log.info("call key：{},锁住失败，请稍后重试", key);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190014);
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
