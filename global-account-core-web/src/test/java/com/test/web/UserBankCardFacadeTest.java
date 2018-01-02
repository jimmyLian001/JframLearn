package com.test.web;

import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.model.AddCompanyBankCardApplyDto;
import com.system.commons.result.Result;
import com.test.frame.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * 银行卡管理服务接口测试
 * <p/>
 * User: lian zd Date:2017/11/24 ProjectName: account-core Version:1.0
 */
@Slf4j
public class UserBankCardFacadeTest extends Base {

    /**
     * 银行卡管理服务
     */
    @Autowired
    private UserBankCardFacade userBankCardFacade;

    @Test
    public void addCompanyToPublicBankCard() {
        AddCompanyBankCardApplyDto dto = new AddCompanyBankCardApplyDto();
        dto.setUserNo(1711091341000416446L);
        dto.setCardNo("622848008935745346");
        dto.setCardHolder("李四");
        dto.setAccType(2);
        dto.setBankCode("ICBC");
        dto.setBankBranchName("宝付支行");
        Result<Boolean> result1 = userBankCardFacade.addCompanyPublicBankCard(dto, UUID.randomUUID().toString());
    }

    @Test
    public void delBankCardTest() {

        Result<Boolean> result1 = userBankCardFacade.deleteBankCard(100018529L, 213621736127361L,
                UUID.randomUUID().toString());
        log.info("用户申请删除银行卡结果：{}", result1);
    }

}
