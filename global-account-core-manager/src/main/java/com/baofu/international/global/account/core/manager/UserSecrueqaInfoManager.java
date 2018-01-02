package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserQuestionDo;
import com.baofu.international.global.account.core.dal.model.UserSecrueqaInfoDo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户安全问题manager
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Service
public interface UserSecrueqaInfoManager {

    /**
     * 查询用户安全问题
     *
     * @param userSecrueqaInfoDo 客户号
     * @return List<UserQuestionDo> 安全问题集合
     */
    List<UserQuestionDo> queryQuestions(UserSecrueqaInfoDo userSecrueqaInfoDo);

    /**
     * 更新
     *
     * @param userSecrueqaInfoDo 查询用户安全问题
     * @return int 更新数量
     */
    int update(UserSecrueqaInfoDo userSecrueqaInfoDo);


    /**
     * 新增安全问题
     *
     * @param userSecrueqaInfoDos 答案信息集
     * @return boolean
     */
    boolean createQuestions(List<UserSecrueqaInfoDo> userSecrueqaInfoDos);
}
