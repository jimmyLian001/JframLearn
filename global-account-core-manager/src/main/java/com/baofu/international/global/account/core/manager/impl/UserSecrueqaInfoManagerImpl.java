package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserSecrueqaInfoMapper;
import com.baofu.international.global.account.core.dal.model.UserQuestionDo;
import com.baofu.international.global.account.core.dal.model.UserSecrueqaInfoDo;
import com.baofu.international.global.account.core.manager.UserSecrueqaInfoManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户安全问题manager
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Slf4j
@Service
public class UserSecrueqaInfoManagerImpl implements UserSecrueqaInfoManager {

    /**
     * 用户安全问题mapper
     */
    @Autowired
    private UserSecrueqaInfoMapper userSecrueqaInfoMapper;

    /**
     * 查询批量
     *
     * @param userSecrueqaInfoDo
     * @return List<T>
     */
    @Override
    public List<UserQuestionDo> queryQuestions(UserSecrueqaInfoDo userSecrueqaInfoDo) {
        return userSecrueqaInfoMapper.selectByQuestionNo(userSecrueqaInfoDo);
    }

    /**
     * 更新
     *
     * @param obj T
     * @return int
     */
    @Override
    public int update(UserSecrueqaInfoDo obj) {
        int num = userSecrueqaInfoMapper.updatedByQuestionNo(obj);
        ParamValidate.checkUpdate(num);
        return num;
    }

    /**
     * 新增安全问题
     *
     * @param userSecrueqaInfoDos
     */
    @Override
    public boolean createQuestions(List<UserSecrueqaInfoDo> userSecrueqaInfoDos) {
        if (userSecrueqaInfoDos == null || userSecrueqaInfoDos.isEmpty()) {
            return false;
        }
        //删除用户答案信息
        userSecrueqaInfoMapper.deleteByUserNo(userSecrueqaInfoDos.get(0).getUserNo());
        //新增用户答案信息
        for (UserSecrueqaInfoDo userSecrueqaInfoDo : userSecrueqaInfoDos) {
            userSecrueqaInfoMapper.insertInfo(userSecrueqaInfoDo);
        }
        return true;
    }
}
