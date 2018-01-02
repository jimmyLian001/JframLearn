package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.PersonInfoReqBo;

/**
 * 个人用户信息接口
 * <p>
 * 1、更新个人用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface UserPersonalBiz {

    /**
     * 更新个人用户信息
     *
     * @param personInfoReqBo 个人用户息
     */
    void addUserPersonal(PersonInfoReqBo personInfoReqBo);

    /**
     * 更新个人用户信息
     *
     * @param personInfoReqBo 个人用户息
     */
    void updateUserPersonal(PersonInfoReqBo personInfoReqBo);
}
