package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.UserPwdMapper;
import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import com.baofu.international.global.account.core.manager.UserPwdManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户密码manager
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Slf4j
@Service
public class UserPwdManagerImpl implements UserPwdManager {

    /**
     * 用户密码信息mapper
     */
    @Autowired
    private UserPwdMapper userPwdMapper;

    /**
     * 查询单个
     *
     * @param userNo  用户号
     * @param pwdType 密码类型
     * @return UserPwdDo
     */
    @Override
    public UserPwdDo query(Long userNo, Integer pwdType) {
        return userPwdMapper.selectByUserNo(userNo, pwdType);
    }

    /**
     * 更新
     *
     * @param userPwdDo T
     * @return int
     */
    @Override
    public int update(UserPwdDo userPwdDo) {
        int count = userPwdMapper.updatedByUserNo(userPwdDo);
        ParamValidate.checkUpdate(count, "更新用户客户数据失败");
        return count;
    }

    /**
     * 新增支付密码
     *
     * @param userPwdDo 参数
     * @return 更新数量
     */
    @Override
    public int insert(UserPwdDo userPwdDo) {
        int count = userPwdMapper.insert(userPwdDo);
        ParamValidate.checkUpdate(count, "更新用户客户数据失败");
        return count;
    }
}
