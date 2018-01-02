package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserPwdDo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserPwdMapper {

    /**
     * 用户密码查询
     *
     * @param userNo  用户号
     * @param pwdType 密码类型
     * @return 用户密码信息
     */
    UserPwdDo selectByUserNo(@Param("userNo") Long userNo, @Param("pwdType") Integer pwdType);

    /**
     * 用户密码插入
     *
     * @param record 用户密码信息
     * @return 影响行数
     */
    int insert(UserPwdDo record);

    /**
     * 更新登录密码错误次数
     *
     * @param userNo           用户号
     * @param loginPwdErrorNum 登录错误次数
     * @param loginPwdLockAt   最后一次错误时间
     * @param userState        用户状态
     */
    void updateByUserNo(@Param("userNo") Long userNo, @Param("loginPwdErrorNum") int loginPwdErrorNum,
                        @Param("loginPwdLockAt") Date loginPwdLockAt, @Param("userState") Integer userState,
                        @Param("pwdType") Integer pwdType);


    /**
     * 更新用户信息
     *
     * @param userPwdDo 用户信息
     * @return int 数量
     */
    int updatedByUserNo(UserPwdDo userPwdDo);
}