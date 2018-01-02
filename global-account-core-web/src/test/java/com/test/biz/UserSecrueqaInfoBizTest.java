package com.test.biz;

import com.baofu.international.global.account.core.biz.UserSecrueqaInfoBiz;
import com.baofu.international.global.account.core.biz.models.UserAnswerReqBo;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by kangzhiguang on 2017/11/5 0005.
 */
public class UserSecrueqaInfoBizTest extends Base {

    @Autowired
    private UserSecrueqaInfoBiz userSecrueqaInfoBiz;

    @Test
    public void findUserAnswer() {
        UserAnswerReqBo answerReqBo = new UserAnswerReqBo();
        answerReqBo.setUserNo(100018529l);
        answerReqBo.setQuestionNo(10003l);
        List<UserAnswerRespBo> list = userSecrueqaInfoBiz.findUserAnswer(answerReqBo);
        System.out.println(list);
    }

    @Test
    public void modifyUserAnswer() {
        UserAnswerReqBo answerReqBo = new UserAnswerReqBo();
        answerReqBo.setUserNo(1000018529l);
        answerReqBo.setQuestionNo(10001l);
        answerReqBo.setAnswer("1231231");
        userSecrueqaInfoBiz.modifyUserAnswer(answerReqBo);
    }

    @Test
    public void verifyUserAnswer() {
        UserAnswerReqBo answerReqBo = new UserAnswerReqBo();
        answerReqBo.setUserNo(1000018529l);
        answerReqBo.setQuestionNo(10001l);
        answerReqBo.setAnswer("12312312");
        Boolean isSuccess = userSecrueqaInfoBiz.verifyUserAnswer(answerReqBo);
        System.out.println(isSuccess);
    }

}
