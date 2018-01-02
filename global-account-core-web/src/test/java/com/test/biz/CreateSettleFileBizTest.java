package com.test.biz;

import com.baofu.international.global.account.core.biz.SettleFileProcessBiz;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
public class CreateSettleFileBizTest extends Base {

    /**
     *
     */
    @Autowired
    private SettleFileProcessBiz createSettleFileBiz;

    @Test
    public void createFile() {
        String fileName = createSettleFileBiz.createSettleFile(1711141520000001203L);
        Boolean boo = createSettleFileBiz.uploadFtp(fileName);
    }
}
