package com.test.facade;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.facade.RegisterFacade;
import com.baofu.international.global.account.core.facade.model.CreateUserReqDto;
import com.baofu.international.global.account.core.facade.model.RegisterUserReqDto;
import com.test.frame.Base;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * description:注册接口测试
 * <p/>
 * Created by liy on 2017/11/21 ProjectName：account
 */
@Slf4j
public class RegisterFacadeTest extends Base {

    /**
     * 注册接口
     */
    @Autowired
    private RegisterFacade registerFacade;

    /**
     * 查询系统安全问题
     */
    @Test
    public void selectSysSecrueqaInfoListTest() {

        registerFacade.selectSysSecrueqaInfoList(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 个人用户注册-发送短信验证码
     */
    @Test
    public void sendSmsCaptchaTest() {

        String mobilePhone = "18721818845";
        registerFacade.sendSmsCaptcha(mobilePhone, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 个人用户注册-校验短信验证码
     */
    @Test
    public void checkSmsCaptchaTest() {
        RegisterUserReqDto dto = new RegisterUserReqDto();
        dto.setLoginNo("18721818845");
        dto.setCaptcha("971346");
        dto.setLoginPwd("123");
        dto.setLoginPwdAgain("123");
        registerFacade.checkSmsCaptcha(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 个人用户注册-创建用户
     */
    @Test
    public void createPersonalUserTest() {

        CreateUserReqDto reqDto = new CreateUserReqDto();
        reqDto.setLoginNo("18721818845");
        reqDto.setLoginPwd("123456");
        reqDto.setQuestionNoOne("1");
        reqDto.setAnswerOne("答案1");
        reqDto.setQuestionNoTwo("2");
        reqDto.setAnswerTwo("答案2");
        reqDto.setQuestionNoThree("3");
        reqDto.setAnswerThree("答案3");
        registerFacade.createPersonalUser(reqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 企业用户注册-发送邮件验证码
     */
    @Test
    public void sendEmailCaptchaTest() {

        String email = "fengsuixing@baofoo.com";
        registerFacade.sendEmailCaptcha(email, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 企业用户注册-校验邮件验证码
     */
    @Test
    public void checkEmailCaptchaTest() {

        RegisterUserReqDto dto = new RegisterUserReqDto();
        dto.setLoginNo("fengsuixing@baofoo.com");
        dto.setCaptcha("971346");
        dto.setLoginPwd("123");
        dto.setLoginPwdAgain("123");
        registerFacade.checkEmailCaptcha(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 企业用户注册-校验邮件验证码
     */
    @Test
    public void createOrgUserTest() {

        CreateUserReqDto reqDto = new CreateUserReqDto();
        reqDto.setLoginNo("fengsuixing@baofoo.com");
        reqDto.setLoginPwd("123456");
        reqDto.setQuestionNoOne("1");
        reqDto.setAnswerOne("答案1");
        reqDto.setQuestionNoTwo("2");
        reqDto.setAnswerTwo("答案2");
        reqDto.setQuestionNoThree("3");
        reqDto.setAnswerThree("答案3");
        registerFacade.createOrgUser(reqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

}
