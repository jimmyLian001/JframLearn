package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserSecrueqaInfoBiz;
import com.baofu.international.global.account.core.biz.convert.UserSecrueqaInfoConvert;
import com.baofu.international.global.account.core.biz.models.UserAnswerReqBo;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;
import com.baofu.international.global.account.core.dal.model.UserQuestionDo;
import com.baofu.international.global.account.core.dal.model.UserSecrueqaInfoDo;
import com.baofu.international.global.account.core.manager.UserSecrueqaInfoManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户安全问题BIZ
 * <p>
 * 1,新增安全问题
 * 2,查询用户安全问题
 * 3,修改用户安全问题
 * 4,验证用户安全问题
 * </p>
 *
 * @author : wuyazi
 * @date: 2017/11/04
 * @version: 1.0.0
 */
@Slf4j
@Service
public class UserSecrueqaInfoBizImpl implements UserSecrueqaInfoBiz {

    /**
     * 用户安全问题manager
     */
    @Autowired
    private UserSecrueqaInfoManager userSecrueqaInfoManager;

    /**
     * 查询用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     */
    @Override
    public List<UserAnswerRespBo> findUserAnswer(UserAnswerReqBo userAnswerReqBo) {
        List<UserQuestionDo> questionDos = userSecrueqaInfoManager.queryQuestions(
                UserSecrueqaInfoConvert.convert(userAnswerReqBo));
        return UserSecrueqaInfoConvert.convert(questionDos);
    }

    /**
     * 修改用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     */
    @Override
    public void modifyUserAnswer(UserAnswerReqBo userAnswerReqBo) {
        userSecrueqaInfoManager.update(UserSecrueqaInfoConvert.convert(userAnswerReqBo));
    }

    /**
     * 验证用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     * @return Boolean 验证结果
     */
    @Override
    public Boolean verifyUserAnswer(UserAnswerReqBo userAnswerReqBo) {
        List<UserQuestionDo> questionDos = userSecrueqaInfoManager.queryQuestions(UserSecrueqaInfoConvert.convert(userAnswerReqBo));
        return questionDos != null && !questionDos.isEmpty()
                && questionDos.get(0).getAnswer().equals(userAnswerReqBo.getAnswer());
    }

    /**
     * 新增安全问题
     *
     * @param userAnswerReqBos
     */
    @Override
    public boolean createQuestions(List<UserAnswerReqBo> userAnswerReqBos) {
        List<UserSecrueqaInfoDo> userSecrueqaInfoDos = Lists.newArrayList();
        for (UserAnswerReqBo userAnswerReqBo : userAnswerReqBos) {
            userSecrueqaInfoDos.add(UserSecrueqaInfoConvert.convert(userAnswerReqBo));
        }
        return userSecrueqaInfoManager.createQuestions(userSecrueqaInfoDos);
    }
}