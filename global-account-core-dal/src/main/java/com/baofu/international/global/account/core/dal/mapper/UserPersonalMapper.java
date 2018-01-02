package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPersonalMapper {

    /**
     * 根据userNo查询
     *
     * @param userInfoNo 申请编号
     * @return 用户信息
     */
    UserPersonalDo selectByUserInfoNo(Long userInfoNo);

    /**
     * 根据用户选择的信息筛选个人用户信息记录数
     *
     * @param userPersonalDo 个人用户对象
     * @return 返回笔数
     */
    int countAllByCondition(UserPersonalDo userPersonalDo);

    /**
     * 根据用户选择的信息筛选个人用户信息
     *
     * @param userPersonalDo 个人用户对象
     * @return 用户信息
     */
    List<UserPersonalDo> selectAllByCondition(UserPersonalDo userPersonalDo);

    /**
     * 插入用户信息
     *
     * @param record 用户信息
     * @return 影响行数
     */
    int insert(UserPersonalDo record);

    /**
     * 修改认证状态
     *
     * @param editUserReqDo 更新请求对象
     * @return >0 更新成功
     */
    int updateAuthStatus(EditUserReqDo editUserReqDo);

    /**
     * 更新个人用户信息表 手机号
     *
     * @param userPersonalDo userPersonalDo
     * @return
     */
    int updateTelByUserNo(UserPersonalDo userPersonalDo);

    /**
     * 更新个人用户信息
     *
     * @param userPersonalDo 个人用户信息
     */
    void updateUserPersonal(UserPersonalDo userPersonalDo);

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    UserPersonalDo selectInfoByUserNo(Long userNo);

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 资质编号
     * @return 用户信息
     */
    UserPersonalDo selectInfoByQualifiedNo(Long qualifiedNo);

    /**
     * 根据email查询
     *
     * @param email 邮箱
     * @return 用户信息
     */
    UserPersonalDo queryByEmail(@Param("email") String email);
}