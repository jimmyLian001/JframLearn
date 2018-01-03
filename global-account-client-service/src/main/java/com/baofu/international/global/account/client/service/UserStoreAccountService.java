package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountRespDto;
import com.baofu.international.global.obtain.facade.models.UserStoreCheckReqDto;

import java.util.List;

/**
 * 用户店铺收款账户相关服务接口
 * <p>
 * 1、根据用户号查询所有可用的店铺和收款账户信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
public interface UserStoreAccountService {

    /**
     * 根据用户号查询所有可用的店铺和收款账户信息
     *
     * @param userNo 用户编号
     * @param ccy    币种
     * @return 用户店铺信息
     */
    List<UserStoreAccountRespDto> queryUserStoreAccount(Long userNo, String ccy);

    /**
     * 校验用户店铺信息是否正确
     *
     * @param userStoreCheckReqDto 校验参数信息
     */
    void checkUserSellerInfo(UserStoreCheckReqDto userStoreCheckReqDto);
}
