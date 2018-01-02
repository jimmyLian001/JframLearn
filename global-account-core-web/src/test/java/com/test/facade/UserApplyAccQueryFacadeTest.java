package com.test.facade;

import com.baofu.international.global.account.core.common.constant.RedisDict;
import com.baofu.international.global.account.core.facade.model.user.UserApplyAccQueryReqDto;
import com.baofu.international.global.account.core.facade.user.UserApplyAccQueryFacade;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/18
 */
public class UserApplyAccQueryFacadeTest extends Base {

    @Autowired
    private UserApplyAccQueryFacade userApplyAccQueryFacade;

    @Autowired
    private RedisManager redisManager;

    /**
     * 查詢用戶申請開通賬戶信息测试
     */
    @Test
    public void queryUserApplyAccTest() {
        UserApplyAccQueryReqDto userApplyAccQueryReqDto = new UserApplyAccQueryReqDto();
        userApplyAccQueryFacade.queryUserApplyAcc(userApplyAccQueryReqDto, UUID.randomUUID().toString());
    }

    /**
     * 下载用戶申請開通賬戶信息测试
     */
    @Test
    public void downloadUserApplyAccTest() {
        redisManager.deleteObject(RedisDict.PHOTO_DOWNLOAD_KEY);
        UserApplyAccQueryReqDto userApplyAccQueryReqDto = new UserApplyAccQueryReqDto();
        userApplyAccQueryFacade.queryUserApplyAcc(userApplyAccQueryReqDto, UUID.randomUUID().toString());
    }
}
