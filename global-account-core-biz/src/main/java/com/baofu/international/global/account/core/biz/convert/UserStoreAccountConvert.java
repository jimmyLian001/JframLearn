package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.biz.models.UserStoreAccountBo;
import com.baofu.international.global.account.core.dal.model.*;
import com.system.commons.utils.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 用户店铺账户信息转换实体类
 * <p>
 * 1、用户店铺和用户收款账户信息关联
 * 2、参数转换
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStoreAccountConvert {

    /**
     * 用户店铺和用户收款账户信息关联
     *
     * @param userStoreDo        用户店铺信息
     * @param userPayeeAccountDo 用户收款账户信息
     * @param userAccountBalBo   用户收款账户信息
     * @return 用户店铺收款账户信息
     */
    public static UserStoreAccountBo paramConvert(UserStoreDo userStoreDo, UserAccountBalBo userAccountBalBo
            , UserPayeeAccountDo userPayeeAccountDo, UserOrgDo userOrgDo
            , UserPersonalDo userPersonalDo, UserPayeeAccountApplyDo userPayeeAccountApplyDo) {

        UserStoreAccountBo userStoreAccountBo = new UserStoreAccountBo();
        userStoreAccountBo.setStoreNo(userStoreDo.getStoreNo());
        userStoreAccountBo.setUserNo(userStoreDo.getUserNo());
        userStoreAccountBo.setStoreName(userStoreDo.getStoreName());
        userStoreAccountBo.setStorePlatform(userStoreDo.getStorePlatform());
        userStoreAccountBo.setStoreUrl(userStoreDo.getStoreUrl());
        userStoreAccountBo.setStoreExist(userStoreDo.getStoreExist());
        userStoreAccountBo.setAccountApplyStatus(userPayeeAccountApplyDo.getStatus());
        userStoreAccountBo.setQualifiedNo(userPayeeAccountApplyDo.getQualifiedNo());
        if (userPayeeAccountDo != null) {
            userStoreAccountBo.setAccountNo(userPayeeAccountDo.getAccountNo());
            userStoreAccountBo.setRoutingNumber(userPayeeAccountDo.getRoutingNumber());
            userStoreAccountBo.setCcy(userPayeeAccountDo.getCcy());
            userStoreAccountBo.setWalletId(userPayeeAccountDo.getWalletId());
            userStoreAccountBo.setStatus(userPayeeAccountDo.getStatus());
            userStoreAccountBo.setChannelId(userPayeeAccountDo.getChannelId());
            userStoreAccountBo.setBankAccNo(userPayeeAccountDo.getBankAccNo());
            userStoreAccountBo.setUpdateAt(DateUtil.format(userPayeeAccountDo.getUpdateAt(), DateUtil.settlePattern));
            userStoreAccountBo.setQualifiedNo(userPayeeAccountDo.getQualifiedNo());
        }
        if (userAccountBalBo != null) {
            userStoreAccountBo.setAccountBal(userAccountBalBo.getAccountBal());
            userStoreAccountBo.setAvailableAmt(userAccountBalBo.getAvailableAmt());
            userStoreAccountBo.setWithdrawProcessAmt(userAccountBalBo.getWithdrawProcessAmt());
            userStoreAccountBo.setWaitAmt(userAccountBalBo.getWaitAmt());
        }
        if (userOrgDo != null) {
            userStoreAccountBo.setRealnameStatus(userOrgDo.getRealnameStatus());
            userStoreAccountBo.setName(userOrgDo.getName());
            userStoreAccountBo.setRemark(userOrgDo.getRemarks());
            userStoreAccountBo.setUserInfoNo(userOrgDo.getUserInfoNo());
        }
        if (userPersonalDo != null) {
            userStoreAccountBo.setRealnameStatus(userPersonalDo.getRealnameStatus());
            userStoreAccountBo.setName(userPersonalDo.getName());
            userStoreAccountBo.setRemark(userPersonalDo.getRemarks());
            userStoreAccountBo.setUserInfoNo(userPersonalDo.getUserInfoNo());
        }

        return userStoreAccountBo;
    }
}
