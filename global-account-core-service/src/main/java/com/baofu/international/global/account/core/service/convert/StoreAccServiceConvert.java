package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.biz.models.UserStoreAccountBo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserStoreDo;
import com.baofu.international.global.account.core.facade.model.StoreAccountReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 用户店铺账户服务参数信息转换
 * <p>
 * 1、根据用户编号 查询店铺收款账户信息
 * 2、根据用户编号币种查询店铺收款账户信息
 * 3、查询用户店铺账户信息分页
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StoreAccServiceConvert {

    /**
     * @param userStoreAccountBo userStoreAccountBo
     * @param storeAccountReqDto storeAccountReqDto
     * @return 返回结果
     */
    public static UserStoreAccountsDto convert(UserStoreAccountBo userStoreAccountBo, StoreAccountReqDto storeAccountReqDto) {

        UserStoreAccountsDto userStoreAccountsDto = new UserStoreAccountsDto();
        userStoreAccountsDto.setStoreNo(userStoreAccountBo.getStoreNo().toString());
        userStoreAccountsDto.setAccountNo(userStoreAccountBo.getAccountNo());
        userStoreAccountsDto.setStoreName(userStoreAccountBo.getStoreName());
        userStoreAccountsDto.setAccountApplyStatus(userStoreAccountBo.getAccountApplyStatus());
        userStoreAccountsDto.setCcy(userStoreAccountBo.getCcy());
        userStoreAccountsDto.setStatus(userStoreAccountBo.getStatus());
        userStoreAccountsDto.setBankAccNo(userStoreAccountBo.getBankAccNo());
        userStoreAccountsDto.setUpdateAt(userStoreAccountBo.getUpdateAt());
        userStoreAccountsDto.setAvailableAmt(userStoreAccountBo.getAvailableAmt());
        userStoreAccountsDto.setRemark(userStoreAccountBo.getRemark());
        userStoreAccountsDto.setWithdrawProcessAmt(userStoreAccountBo.getWithdrawProcessAmt());
        userStoreAccountsDto.setName(userStoreAccountBo.getName());
        userStoreAccountsDto.setStoreExist(userStoreAccountBo.getStoreExist());
        userStoreAccountsDto.setUserType(storeAccountReqDto.getUserType());
        userStoreAccountsDto.setQualifiedNo(userStoreAccountBo.getQualifiedNo());
        userStoreAccountsDto.setUserInfoNo(String.valueOf(userStoreAccountBo.getUserInfoNo()));
        userStoreAccountsDto.setRealnameStatus(userStoreAccountBo.getRealnameStatus());

        return userStoreAccountsDto;
    }


    /**
     * 参数转换
     *
     * @param userPayeeAccountDo routingNo
     * @param storeDo            storeDo
     * @param userAccountBalBo   balDo
     * @return 返回结果
     */
    public static StoreAccountInfoRepDto paramConvert(UserPayeeAccountDo userPayeeAccountDo, UserStoreDo storeDo,
                                                      UserAccountBalBo userAccountBalBo) {

        StoreAccountInfoRepDto storeAccountInfoRepDto = new StoreAccountInfoRepDto();
        storeAccountInfoRepDto.setStoreName(storeDo.getStoreName());
        storeAccountInfoRepDto.setStoreNo(storeDo.getStoreNo());
        storeAccountInfoRepDto.setAccountBal(userAccountBalBo.getAccountBal());
        storeAccountInfoRepDto.setAvaliableAmt(userAccountBalBo.getAvailableAmt());
        storeAccountInfoRepDto.setWithdrawProcessAmt(userAccountBalBo.getWithdrawProcessAmt());
        storeAccountInfoRepDto.setRountingNo(userPayeeAccountDo.getRoutingNumber());
        storeAccountInfoRepDto.setManagementCategory(storeDo.getManagementCategory());
        storeAccountInfoRepDto.setAwsAccessKey(storeDo.getAwsAccessKey());
        storeAccountInfoRepDto.setSecretKey(storeDo.getSecretKey());
        storeAccountInfoRepDto.setSellerId(storeDo.getSellerId());
        storeAccountInfoRepDto.setBankAccName(userPayeeAccountDo.getBankAccName());
        storeAccountInfoRepDto.setQualifiedNo(userPayeeAccountDo.getQualifiedNo());
        storeAccountInfoRepDto.setBankAccNo(userPayeeAccountDo.getBankAccNo());
        storeAccountInfoRepDto.setCcy(userPayeeAccountDo.getCcy());
        storeAccountInfoRepDto.setStoreExist(storeDo.getStoreExist());

        return storeAccountInfoRepDto;
    }
}

