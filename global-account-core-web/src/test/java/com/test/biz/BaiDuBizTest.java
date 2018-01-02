package com.test.biz;

import com.baofu.international.global.account.core.biz.external.baidu.IdCardBizImpl;
import com.baofu.international.global.account.core.biz.external.baidu.TokenBizImpl;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * 百度接口测试
 * <p>
 * User: 蒋文哲 Date: 2017/11/4 Version: 1.0
 * </p>
 */
public class BaiDuBizTest extends Base {
    /**
     *
     */
    @Autowired
    private TokenBizImpl tokenBizImpl;
    /**
     * 身份证图像识别
     */
    @Autowired
    private IdCardBizImpl idCardBiz;

    /**
     * 短信测试发送
     */
    @Test
    public void getToken() {
        System.out.println(tokenBizImpl.getToken());
    }

    /**
     * 短信测试发送
     */
    @Test
    public void idCard() throws Exception {
        while (true) {
            Thread.sleep(1);
        }
        //   System.out.println(idCardBiz.idCard(null));
    }
}
