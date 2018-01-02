package com.test.web;

import com.baofu.international.global.account.core.facade.TaccountPhoneNoFacade;
import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.manager.impl.RedisManagerImpl;
import com.system.commons.result.Result;
import com.test.frame.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * 用户手机号绑定维护接口测试
 * <p/>
 * User: lian zd Date:2017/11/5 ProjectName: account-core Version:1.0
 */
@Slf4j
public class TaccountPhoneNoFacadeTest extends Base {

    /**
     * 用户手机号绑定维护接口
     */
    @Autowired
    private TaccountPhoneNoFacade taccountPhoneNoFacade;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    @Autowired
    private UserBankCardFacade userBankCardFacade;

    /**
     * 用户绑定手机号修改接口测试
     */
    @Test
    public void userTelFixTest() {
        FixPhoneNoApplyDto dto = new FixPhoneNoApplyDto();
        dto.setUserNo("100018529");
        dto.setCurrentPhoneNumber("13127933306");
        dto.setAfterFixPhoneNumber("15537057066");
//        dto.setQuestionNo("10001");
//        dto.setQuestionSequence("2");
//        dto.setAnswer("答案4");
        Result<Boolean> result = taccountPhoneNoFacade.fixRegisterPhoneNo(dto, "199005295716");
        log.info("修改结果：{}", result);
    }

    /**
     * 用户密保问题查询
     */
    @Test
    public void userAnswerTest() {
        FixTelInfoQueryDto dto = new FixTelInfoQueryDto();
        dto.setUserNo("100018529");
        Result<UserRegisterTelInfoDto> result = taccountPhoneNoFacade.getFixRegisterTelInfo(dto, "199005295716");
        log.info("查询结果：{}", result);
    }

    /**
     * 验证码发送接口测试
     */
    @Test
    public void senMessageCodeTest() {
        FixTelMessageCodeApplyDto dto = new FixTelMessageCodeApplyDto();
        dto.setUserNo("100018529");
        dto.setMessageCode("324342");
        dto.setCurrentPhoneNumber("18217788441");
        dto.setContent("你好，测试验证码 by Jimmy");
        Result<Boolean> result = taccountPhoneNoFacade.sendTelMessageCode(dto, "199005295716");
//        redisManager.insertObject("telFix" + "13127933306","你好");
        Object ff = redisManager.queryObjectByKey("telFix" + "13127933306");
        log.info("查询结果：{},验证码：{}", "result", ff);
    }


    @Test
    public void delBankCardTest() {

        Result<Boolean> result1 = userBankCardFacade.deleteBankCard(100018529L, 213621736127361L,
                UUID.randomUUID().toString());
        log.info("用户申请删除银行卡结果：{}", result1);
    }

    /**
     * 身份证三要素认证测试
     */
    @Test
    public void bankCardAuthTest() {
        //431128199209297612
        AddPersonalBankCardApplyDto dto = new AddPersonalBankCardApplyDto();
        dto.setCardNo("6225768773966708");
        dto.setCardHolder("欧西涛");
        dto.setUserNo("1711121509000417975");
        dto.setAccType("1");
        Result<Boolean> result1 = userBankCardFacade.addPersonBankCard(dto, "132421492859235");
    }

}
