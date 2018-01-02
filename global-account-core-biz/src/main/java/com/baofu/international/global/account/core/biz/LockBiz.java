package com.baofu.international.global.account.core.biz;

/**
 * 描述：锁服务
 * <p>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
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
    String lock(String key);

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
