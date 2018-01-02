package com.baofu.international.global.account.core.dal.mapper;


import com.baofu.international.global.account.core.dal.model.UserSettleApplyDo;

/**
 * 描述：用户结汇申请表
 */
public interface UserSettleApplyMapper {

    /**
     * 根据用户申请流水号获取申请记录
     *
     * @param userReqId 用户申请流水号
     * @return
     */
    UserSettleApplyDo selectByReqId(String userReqId);

    /**
     * 保存用户结汇申请记录
     *
     * @param userSettleApplyDo 用户申请信息
     * @return
     */
    int insert(UserSettleApplyDo userSettleApplyDo);

    /**
     * 更新用户结汇申请记录
     *
     * @param userSettleApplyDo 用户申请信息
     * @return
     */
    int updateUserSettleApply(UserSettleApplyDo userSettleApplyDo);
}