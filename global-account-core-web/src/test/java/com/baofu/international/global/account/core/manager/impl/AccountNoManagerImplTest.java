package com.baofu.international.global.account.core.manager.impl;

import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 描述：
 * <p>
 * 1、
 * </p>
 *
 * @author : daoxuan
 * @date : 2017/11/29
 */
public class AccountNoManagerImplTest extends Base {

    @Autowired
    private AccountNoManagerImplBase accountNoManager;

    @Test
    public void test() {

        Assert.assertEquals(19, accountNoManager.generateStringAccountNo().length());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme