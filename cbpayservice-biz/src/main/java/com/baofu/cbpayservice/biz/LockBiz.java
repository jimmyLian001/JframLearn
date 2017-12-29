package com.baofu.cbpayservice.biz;

/**
 * 请求锁服务
 * <p>
 * User: 不良人 Date:2017/5/26 ProjectName: feature_test Version: 1.0
 */
public interface LockBiz {

    /**
     * 接口锁
     * 1、判断是否锁住
     * 2、未锁住就加锁
     * 3、已锁住就抛出异常提示不能重复调用
     *
     * @param key 唯一标识
     * @return 返回锁的key
     */
    String lock(String key, Long timeOut);

    /**
     * 锁释放
     *
     * @param key 锁标识
     */
    void unLock(String key);

    /**
     * 接口锁
     * 1、判断是否锁住
     * 2、已锁住就抛出异常提示不能重复调用
     *
     * @param key 唯一标识
     * @return true:锁住|false:未锁住
     */
    Boolean isLock(String key);
}
