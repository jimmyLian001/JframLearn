package com.test.dal;

import com.baofu.international.global.account.core.dal.mapper.UserStoreMapper;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * 用户登陆信息manager测试类
 * <p>
 * User:yangjian Date: 2017/11/8 Version: 1.0
 * </p>
 */
public class UseStoreDalTest extends Base {

    /**
     * 用户登陆信息manager类
     */
    @Autowired
    private UserStoreMapper tUserStoreMapper;

    /**
     * 查询Test
     */
    @Test
    public void findTest() {
        System.out.println("-------------" + tUserStoreMapper.selectUserStore(1711082157000415667L, 00415667));
    }
}
