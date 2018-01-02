package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.UserInfoReqDTO;

/**
 * 用户添加API服务
 * <p>
 * 1、添加用户信息
 * </p>
 * User: 欧西涛  Date: 2015/10/11 ProjectName: globalaccount Version: 1.0
 */
public interface UserInfoService {

    /**
     * 添加用户信息
     *
     * @param userInfoReqDTO 用户信息
     * @param traceLogId     日志ID
     */
    void addUserInfo(UserInfoReqDTO userInfoReqDTO, String traceLogId);
}
