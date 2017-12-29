package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * redis 服务
 * <p>
 * User: 不良人 Date:2017/5/2 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class RedisBizImpl implements RedisBiz {

    /**
     * redis实现
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 接口重复调用
     * 根据key查询redis中是否存在
     * 不存在则添加一个值和保存时间
     *
     * @param key     redis的key唯一
     * @param timeout redis保存时间
     */
    public String preventRepeat(String key, Long timeout) {
        String baseKey = "CBPAY:PREVENT:REPEAT:";
        key = baseKey + key;
        String value = redisManager.queryObjectByKey(key);

        if (StringUtils.isNotBlank(value)) {
            log.info("call 接口请求参数key存在，key={}", key);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00132);
        }
        redisManager.insertObject(key, key, timeout);
        return key;
    }

    /**
     * 根据key去删除内容
     *
     * @param key redis的key
     */
    public boolean deleteKey(String key) {
        return redisManager.deleteObject(key);
    }

    @Override
    public boolean addKey(String key, String value, Long timeout) {
        return redisManager.insertObject(value, key, timeout);
    }

    @Override
    public String queryByKey(String key) {
        return redisManager.queryObjectByKey(key);
    }

    @Override
    public boolean modifyByKey(String key, String value) {
        return redisManager.modify(value, key);
    }

    /**
     * key锁
     *
     * @param key redis的key唯一
     * @return
     */
    @Override
    public boolean isLock(String key) {
        if (StringUtils.isBlank(key)) {
            return Boolean.FALSE;
        }
        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public boolean lock(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.TRUE;
        }
        Boolean lockFlag = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
        if (!lockFlag) {
            log.info("key：{},锁住失败，请稍后重试", key);
            return Boolean.FALSE;
        }
        log.info("key：{}锁定成功", key);
        return Boolean.TRUE;
    }

    public void unLock(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        redisManager.deleteObject(key);
    }
}
