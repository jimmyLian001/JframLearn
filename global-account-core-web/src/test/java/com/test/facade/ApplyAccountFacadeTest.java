package com.test.facade;

import com.baofu.international.global.account.core.facade.ApplyAccountFacade;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * User: yangjian  Date: 2017-11-23 ProjectName:  Version: 1.0
 */
public class ApplyAccountFacadeTest extends Base {

    /**
     *
     */
    @Autowired
    private ApplyAccountFacade applyAccountFacade;

    /**
     * 开户资质审核通过
     */
    @Test
    public void applyAccountByVerifyTest() {
        ApplyAccountReqDto reqDto = new ApplyAccountReqDto();
        reqDto.setUserType(2);
        applyAccountFacade.addApplyAccount(reqDto, UUID.randomUUID().toString());
    }

    /**
     * 新开户
     * <p>
     * 1、开户账号资质待审核
     * 2、开户账户资质已审核
     * </p>
     */
    @Test
    public void applyAccountTest() {

        ApplyAccountReqDto reqDto = new ApplyAccountReqDto();
        reqDto.setCcy("USD");
        reqDto.setUserType(2);
        reqDto.setStoreExist("Y");
        reqDto.setUserNo(1000185292L);
        reqDto.setStoreName("红豆领带");
        reqDto.setSellerId("111223kdk");
        reqDto.setStorePlatform("amazon");
        reqDto.setAwsAccessKey("11231344");
        reqDto.setManagementCategory("服饰");
        reqDto.setSecretKey("adff-03#adfcdw%fa");
        reqDto.setQualifiedNo(123366L);

        applyAccountFacade.addApplyAccount(reqDto, UUID.randomUUID().toString());
    }

    /**
     * 资质信息查询
     */
    @Test
    public void applyInfoTest() {
        System.out.println("开户主体信息：" + applyAccountFacade.getApplyAccountData(5182171127000015438L,
                2, UUID.randomUUID().toString()));

    }
}
