package com.baofu.cbpayservice.biz;

/**
 * redis 服务
 * <p>
 * User: 不良人 Date:2017/5/2 ProjectName: feature-BFO-793-170426-zwb Version: 1.0
 */
public interface RedisBiz {

    /**
     * 根据key查询redis中是否存在
     * 不存在则添加一个值和保存时间
     *
     * @param key     redis的key唯一
     * @param timeout redis保存时间
     */
    String preventRepeat(String key, Long timeout);

    /**
     * 根据key去删除内容
     *
     * @param key redis的key
     */
    boolean deleteKey(String key);

    /**
     * 功能：根据key添加一个值和保存时间
     *
     * @param key     redis的key唯一
     * @param value   redis的值
     * @param timeout redis保存时间
     */
    boolean addKey(String key, String value, Long timeout);

    /**
     * 功能：根据key查询redis中的值
     *
     * @param key redis的key唯一
     */
    String queryByKey(String key);

    /**
     * 功能：根据key修改redis中的值
     *
     * @param key   redis的key唯一
     * @param value redis的值
     */
    boolean modifyByKey(String key, String value);

    /**
     * key锁
     *
     * @param key redis的key唯一
     * @return
     */
    boolean isLock(String key);

    /**
     * key加锁
     *
     * @param key redis的key唯一
     * @return
     */
    boolean lock(String key);

    /**
     * 解锁
     *
     * @param key redis的key唯一
     */
    void unLock(String key);
}
