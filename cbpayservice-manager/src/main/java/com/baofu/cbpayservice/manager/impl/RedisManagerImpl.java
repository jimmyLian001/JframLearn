package com.baofu.cbpayservice.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.baofu.cbpayservice.manager.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis实现
 * <p>
 * 1、查询redis 数据库
 * 2、插入redis 数据库
 * 3、插入redis 数据库,设置有效期
 * 4、删除redis 保存对象
 * 5、更新 redis
 * 6、查询redis 数据库
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class RedisManagerImpl implements RedisManager {

    /**
     * redis 操作
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询redis 数据库
     *
     * @param keyEnum 查询关键字
     * @param clazz   指定返回List内存放的对象类型
     * @param <T>     返回对象类型,集合泛型
     * @return List<T>      返回对象集合
     */
    public <T> List<T> queryListByKey(final String keyEnum, final Class<T> clazz) {
        log.debug("queryListByKey request：{}", keyEnum);

        String resultStr = queryObjectByKey(keyEnum);
        if (StringUtils.isBlank(resultStr)) {
            return null;
        }

        List<T> value = JSONObject.parseArray(resultStr, clazz);

        log.debug("queryListByKey response：{}", value.toString());
        return value;
    }

    /**
     * 插入redis 数据库
     *
     * @param obj     保存对象
     * @param keyEnum 关键字
     * @return 对象类型, 泛型
     */
    public boolean insertObject(final Object obj, final String keyEnum) {
        return insertObject(obj, keyEnum, 0L);
    }

    /**
     * 插入redis 数据库,设置有效期
     *
     * @param obj     保存对象
     * @param keyEnum 关键字
     * @param timeout 有效期（毫秒）
     * @return 对象类型, 泛型
     */
    public boolean insertObject(final Object obj, final String keyEnum, final long timeout) {

        if (obj == null) {
            return false;
        }

        log.debug("insertObject request：key={}，obj={}", keyEnum, obj.toString());

        final String value = (obj instanceof String) ? obj.toString() : JSONObject.toJSONString(obj);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(keyEnum);
                byte[] redisValue = redisTemplate.getStringSerializer().serialize(value);
                connection.set(redisKey, redisValue);
                if (timeout > 0) {
                    redisTemplate.expire(keyEnum, timeout, TimeUnit.MILLISECONDS);
                }
                return true;
            }
        });

        log.debug("insertObject response：{}", result);
        return result;
    }

    /**
     * 更新 redis
     *
     * @param obj      操作对象
     * @param keyEnums keys数组
     * @return boolean      更新状态
     */
    public boolean modify(final Object obj, final String... keyEnums) {
        for (String key : keyEnums) {
            deleteObject(key);
            insertObject(obj, key);
        }
        return true;
    }

    /**
     * 查询redis 数据库
     *
     * @param keyEnum 查询关键字
     * @return String
     */
    public String queryObjectByKey(final String keyEnum) {
        log.debug("queryObjectByKey request：{}", keyEnum);

        String resultStr = (String) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(keyEnum);
                if (connection.exists(redisKey)) {
                    byte[] value = connection.get(redisKey);
                    return redisTemplate.getStringSerializer().deserialize(value);
                }
                return null;
            }
        });

        log.debug("queryObjectByKey response：{}", resultStr);
        return resultStr;
    }

    /**
     * 查询redis 数据库
     *
     * @param keyEnum 查询关键字
     * @return <T> T
     */
    public <T> T queryObjectByKey(final String keyEnum, final Class<T> clazz) {
        log.debug("queryObjectByKey request：{}", keyEnum);

        String resultStr = queryObjectByKey(keyEnum);
        if (StringUtils.isBlank(resultStr)) {
            return null;
        }

        T value = JSONObject.parseObject(resultStr, clazz);

        log.debug("queryObjectByKey response：{}", value.toString());
        return value;
    }

    /**
     * 删除redis 保存对象
     *
     * @param keyEnum 查询关键字
     * @return 删除结果
     */
    public boolean deleteObject(final String keyEnum) {
        log.debug("deleteObject request:key={}", keyEnum);

        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(keyEnum);
                return connection.del(redisKey);
            }
        });

        log.debug("deleteObject response：{}", result);
        return result > 0;
    }


    /**
     * Redis锁
     *
     * @param key   锁的Key
     * @param value 锁的Value
     * @return 返回是否成功
     */
    public boolean lockRedis(final String key, final String value, final long timeout) {

        log.debug("lockRedis request:key={},value={}", key, value);
        if (timeout < 0) {
            log.warn("lockRedis Fail lock Time gt 0");
            return false;
        }
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(key);
                byte[] redisValue = redisTemplate.getStringSerializer().serialize(value);
                Boolean lock = false;
                try {
                    lock = connection.setNX(redisKey, redisValue);
                    log.debug("lockRedis Lock Result:{}", lock);
                    Boolean setTimeOutResult = redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
                    log.debug("lockRedis setTimeOutResult TimeOut:{} Result:{}", timeout, setTimeOutResult);
                    if (lock && setTimeOutResult) {
                        return true;
                    }
                    if (!setTimeOutResult) {
                        redisTemplate.delete(key);
                        return false;
                    }
                } catch (Exception e) {
                    if (lock) {
                        redisTemplate.delete(key);
                    }
                    log.warn("lockRedis Fail Exception:{}", e);
                }
                return false;
            }
        });
        log.debug("lockRedis request Result:{}", result);
        return result;
    }


    /**
     * 设置rediskey的超时时间
     *
     * @param key     key
     * @param timeOut 超时时间，不能为0，单位：毫秒
     */
    @Override
    public boolean modifyTimeOut(final String key, Long timeOut) {

        if (timeOut > 0) {
            return redisTemplate.expire(key, timeOut, TimeUnit.MILLISECONDS);
        }
        return Boolean.FALSE;
    }

    /**
     * 统计次数
     *
     * @param keyEnum 查询关键字
     */
    @Override
    public Long incr(final String keyEnum) {
        log.debug("deleteObject request:key={}", keyEnum);

        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(keyEnum);
                return connection.incr(redisKey);
            }
        });
        log.debug("deleteObject response：{}", result);
        return result;
    }
}
