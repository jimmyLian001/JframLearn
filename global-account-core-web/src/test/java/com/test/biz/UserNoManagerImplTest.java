package com.test.biz;

import com.baofu.international.global.account.core.manager.impl.UserNoManagerImplBase;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 描述： <p> 1、 </p> User: daoxuan Date: 2017/11/13
 */
public class UserNoManagerImplTest extends Base {

    @Autowired
    private UserNoManagerImplBase UserNoManagerImpl;

    @Test
    public void test() {

        Assert.assertEquals(19, UserNoManagerImpl.generateStringUserNoBy(1).length());
        Assert.assertEquals(19, UserNoManagerImpl.generateStringUserNoBy(2).length());

    }
}
