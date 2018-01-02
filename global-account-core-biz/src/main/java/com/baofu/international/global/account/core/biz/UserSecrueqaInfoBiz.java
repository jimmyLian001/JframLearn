package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.UserAnswerReqBo;
import com.baofu.international.global.account.core.biz.models.UserAnswerRespBo;

import java.util.List;


/**
 * 用户安全问题BIZ
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public interface UserSecrueqaInfoBiz {

    /**
     * 查询用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     */
    List<UserAnswerRespBo> findUserAnswer(UserAnswerReqBo userAnswerReqBo);

    /**
     * 修改用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     */
    void modifyUserAnswer(UserAnswerReqBo userAnswerReqBo);

    /**
     * 验证用户安全问题
     *
     * @param userAnswerReqBo 用户安全问题请求参数
     */
    Boolean verifyUserAnswer(UserAnswerReqBo userAnswerReqBo);

    /**
     * 新增安全问题
     *
     * @param userAnswerReqBos 用户答案信息BOS
     */
    boolean createQuestions(List<UserAnswerReqBo> userAnswerReqBos);

}