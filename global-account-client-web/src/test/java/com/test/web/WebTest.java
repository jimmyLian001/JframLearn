package com.test.web;

import com.baofu.international.global.account.client.common.constant.ConfigDict;
import com.test.frame.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * User: by 蒋文哲 Date: 2017/3/17 Version: 1.0
 */
@Slf4j
public class WebTest extends Base {

    @Autowired
    private ConfigDict configDict;

    @Test
    public void testBatchRemit() throws InterruptedException {
        System.out.println(configDict.getSettlePublicKeyPath());
    }
}
