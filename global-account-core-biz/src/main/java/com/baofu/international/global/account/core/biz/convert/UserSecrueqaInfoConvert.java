package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserAnswerReqBo;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;
import com.baofu.international.global.account.core.dal.model.UserQuestionDo;
import com.baofu.international.global.account.core.dal.model.UserSecrueqaInfoDo;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * 用户安全答案信息转换
 * <p>
 * 1,用户安全答案信息转换
 * <p>
 *
 * @author : wuyazi
 * @date: 2017/11/04
 * @version: 1.0.0
 * </p>
 */
public final class UserSecrueqaInfoConvert {

    private UserSecrueqaInfoConvert() {
    }

    public static List<UserAnswerRespBo> convert(List<UserQuestionDo> questionDos) {
        if (questionDos == null || questionDos.isEmpty()) {
            return Lists.newArrayList();
        }
        List<UserAnswerRespBo> answerRespBos = Lists.newArrayList();
        for (UserQuestionDo userQuestionDo : questionDos) {
            UserAnswerRespBo answerRespBo = new UserAnswerRespBo();
            answerRespBo.setUserNo(userQuestionDo.getUserNo());
            answerRespBo.setQuestionNo(userQuestionDo.getQuestionNo());
            answerRespBo.setQuestion(userQuestionDo.getQuestion());
            answerRespBo.setAnswer(userQuestionDo.getAnswer());
            answerRespBos.add(answerRespBo);
        }
        return answerRespBos;
    }

    public static UserSecrueqaInfoDo convert(UserAnswerReqBo userAnswerReqBo) {
        if (userAnswerReqBo == null) {
            return null;
        }
        UserSecrueqaInfoDo userSecrueqaInfoDo = new UserSecrueqaInfoDo();
        userSecrueqaInfoDo.setQuestionSequence(userAnswerReqBo.getQuestionSequence());
        userSecrueqaInfoDo.setUserNo(userAnswerReqBo.getUserNo());
        userSecrueqaInfoDo.setQuestionNo(userAnswerReqBo.getQuestionNo());
        userSecrueqaInfoDo.setAnswer(userAnswerReqBo.getAnswer());
        userSecrueqaInfoDo.setUpdateBy(userAnswerReqBo.getUpdateBy());
        return userSecrueqaInfoDo;
    }


}