package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.UserAnswerReqBo;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;
import com.baofu.international.global.account.core.facade.model.UserAnswerReqDTO;
import com.baofu.international.global.account.core.facade.model.UserAnswerRespDTO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 用户安全问题转换
 * <p>
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public final class UserSecrueqaInfoConvert {

    private UserSecrueqaInfoConvert() {
    }

    public static UserAnswerReqBo convert(UserAnswerReqDTO userAnswerReqDTO) {
        if (userAnswerReqDTO == null) {
            return null;
        }
        UserAnswerReqBo userAnswerReqBo = new UserAnswerReqBo();
        userAnswerReqBo.setLoginNo(userAnswerReqDTO.getLoginNo());
        userAnswerReqBo.setUserNo(userAnswerReqDTO.getUserNo());
        userAnswerReqBo.setQuestionNo(userAnswerReqDTO.getQuestionNo());
        userAnswerReqBo.setAnswer(userAnswerReqDTO.getAnswer());
        userAnswerReqBo.setQuestionSequence(userAnswerReqDTO.getQuestionSequence());
        userAnswerReqBo.setUpdateBy(userAnswerReqDTO.getUpdatedBy());
        return userAnswerReqBo;
    }


    public static List<UserAnswerRespDTO> convert(List<UserAnswerRespBo> answerRespBos) {
        List<UserAnswerRespDTO> answerRespDTOS = Lists.newArrayList();
        if (answerRespBos == null || answerRespBos.isEmpty()) {
            return answerRespDTOS;
        }
        for (UserAnswerRespBo answerRespBo : answerRespBos) {
            UserAnswerRespDTO answerRespDTO = new UserAnswerRespDTO();
            answerRespDTO.setQuestionNo(answerRespBo.getQuestionNo());
            answerRespDTO.setQuestion(answerRespBo.getQuestion());
            answerRespDTO.setUserNo(answerRespBo.getUserNo());
            answerRespDTO.setAnswer(answerRespBo.getAnswer());
            answerRespDTOS.add(answerRespDTO);
        }
        return answerRespDTOS;
    }

    /**
     * 封装用户答案信息
     *
     * @param userAnswerReqDtos
     * @return 用户答案Bo集
     */
    public static List<UserAnswerReqBo> convertToDtos(List<UserAnswerReqDTO> userAnswerReqDtos) {
        if (userAnswerReqDtos == null) {
            return Lists.newArrayList();
        }
        List<UserAnswerReqBo> userAnswerReqBos = Lists.newArrayList();
        for (UserAnswerReqDTO userAnswerReqDto : userAnswerReqDtos) {
            userAnswerReqBos.add(convert(userAnswerReqDto));
        }
        return userAnswerReqBos;
    }

}
