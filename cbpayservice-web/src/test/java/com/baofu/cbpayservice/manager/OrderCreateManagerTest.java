package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.BaseTest;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2017/3/23 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class OrderCreateManagerTest extends BaseTest {

    private static final String REDIS_KEY = "ORDER_ID_CREATE_KEY";
    @Autowired
    private OrderIdManager orderCreateManager;

    @Test
    public void orderCreateTest() throws Exception {

        Set<Long> longSet = Sets.newHashSet();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000000; i++) {
                    long systemTime = System.currentTimeMillis();
                    Long orderId = orderCreateManager.orderIdCreate();
                    log.info("宝付订单号：{},创建耗时：{}", orderId, (systemTime - System.currentTimeMillis()));
                    if (longSet.contains(orderId)) {
                        throw new RuntimeException("宝付订单号:" + orderId + ",重复了");
                    }
                    longSet.add(orderId);
                }
            }
        });
        executor.execute(thread);

        while (true) {
            Thread.sleep(1);
        }
    }
}
