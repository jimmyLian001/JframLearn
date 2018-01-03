package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;

import java.util.List;

/**
 * 用户银行卡相关管理
 * <p>
 * 1、根据用户编号查询用户绑定银行卡信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
public interface UserBankCardService {

    /**
     * 根据用户编号查询用户绑定银行卡信息
     *
     * @param userNo 用户编号
     * @return 返回银行卡集合信息
     */
    List<UserBankCardInfoDto> queryUserBankCard(Long userNo);

    /**
     * 根据用户编号查询用户绑定银行卡信息
     *
     * @param userNo   用户号
     * @param recordId 卡信息表主键
     * @return 返回银行卡信息
     */
    UserBankCardInfoDto queryUserDefaultBankCard(Long userNo, Long recordId);
}
