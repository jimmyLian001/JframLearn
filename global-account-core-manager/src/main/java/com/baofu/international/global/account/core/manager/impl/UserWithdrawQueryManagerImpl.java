package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.mapper.*;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.UserWithdrawQueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:用户提现查询 ManagerImpl
 * <p/>
 * Created by liy on 2017/11/28 ProjectName：account
 */
@Repository
public class UserWithdrawQueryManagerImpl implements UserWithdrawQueryManager {

    /**
     * 用户提现汇总
     */
    @Autowired
    private UserWithdrawSumMapper sumMapper;

    /**
     * 提现申请
     */
    @Autowired
    private UserWithdrawApplyMapper applyMapper;

    /**
     * 用户信息
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 个人用户信息
     */
    @Autowired
    private UserPersonalMapper userPersonalMapper;

    /**
     * 企业用户信息
     */
    @Autowired
    private UserOrgMapper userOrgMapper;

    @Override
    public List<UserWithdrawSumDo> selectUserWithdrawSumPageInfo(UserWithdrawSumDo sumDo) {
        return sumMapper.selectUserWithdrawSumPageInfo(sumDo);
    }

    /**
     * 用户提现申请分页查询
     *
     * @param applyDo    请求参数
     * @param withdrawId 提现明细编号
     * @return 结果集
     */
    @Override
    public List<UserWithdrawApplyDo> selectUserWithdrawApplyPageInfo(UserWithdrawApplyDo applyDo,
                                                                     List<Long> withdrawId) {

        List<UserWithdrawApplyDo> result = applyMapper.selectUserWithdrawApplyPageInfo(applyDo, withdrawId);
        for (UserWithdrawApplyDo entity : result) {
            //用修改人存储实名认证的用户名
            entity.setUpdateBy(this.getRealNameByUserNo(entity.getUserNo()));
        }
        return result;
    }

    /**
     * 根据用户号获取实名认证的用户名
     *
     * @param userNo 用户号
     * @return 用户名
     */
    @Override
    public String getRealNameByUserNo(Long userNo) {

        //根据用户号查询用户
        UserInfoDo userInfoDo = userInfoMapper.selectByUserNo(userNo);
        if (userInfoDo == null) {
            return "";
        }
        int userType = userInfoDo.getUserType();
        if (userType == UserTypeEnum.PERSONAL.getType()) {
            UserPersonalDo userPersonalDo = userPersonalMapper.selectByUserInfoNo(userInfoDo.getUserInfoNo());
            if (userPersonalDo == null) {
                return "";
            }
            return userPersonalDo.getName();
        }
        if (userType == UserTypeEnum.ORG.getType()) {
            UserOrgDo userOrgDo = userOrgMapper.selectByUserInfoNo(userInfoDo.getUserInfoNo());
            if (userOrgDo == null) {
                return "";
            }
            return userOrgDo.getName();
        }
        return "";
    }


}
