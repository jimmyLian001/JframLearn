package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserPwdDo;

/**
 * 用户密码服务manager
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
public interface UserPwdManager {

    /**
     * 查询单个
     *
     * @param userNo  用户号
     * @param pwdType 密码类型
     * @return UserPwdDo
     */
    UserPwdDo query(Long userNo, Integer pwdType);

    /**
     * 更新用户信息
     *
     * @param userPwdDo 参数
     * @return int 更新数量
     */
    int update(UserPwdDo userPwdDo);

    /**
     * 新增支付密码
     *
     * @param userPwdDo 参数
     * @return 更新数量
     */
    int insert(UserPwdDo userPwdDo);

}
