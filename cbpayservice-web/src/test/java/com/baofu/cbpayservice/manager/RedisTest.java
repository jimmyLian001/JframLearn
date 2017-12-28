package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/1 ProjectName:cbpay-service  Version: 1.0
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisManager redisManager;

    @Test
    public void redisLockTest() {
        for (int i = 0; i < 10; i++) {
            Boolean boo = redisManager.lockRedis("REDIS_MANAGER_LOCK_KEY", "11", 3 * 60 * 1000);
        }
    }
}
