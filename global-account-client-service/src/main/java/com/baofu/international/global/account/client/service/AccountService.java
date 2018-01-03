package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.core.facade.model.AccBalanceDto;
import com.baofu.international.global.account.core.facade.model.PageDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;

import java.util.List;

/**
 * 账户中心service
 *
 * @author: 康志光 Date:2017/11/ ProjectName: account-client Version: 1.0
 */
public interface AccountService {

    /**
     * 获取账户余额
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return AccBalanceDto 账户余额
     */
    AccBalanceDto getCcyBalance(Long userNo, String ccy);

    /**
     * 获取店铺账户分页信息
     *
     * @param userNo    用户号
     * @param storeName 店铺名称
     * @param ccy       币种
     * @param pageNum   页码
     * @param pageSize  条数
     * @return PageDto 店铺账户数据
     */
    PageDto<UserStoreAccountsDto> queryStoreAccountForPage(Long userNo, Integer userType, String storeName, String ccy, int pageNum, int pageSize);

    /**
     * 查询账户详情
     *
     * @param userNo   用户号
     * @param userType 用户类型
     * @param storeNo  店铺号
     */
    StoreAccountInfoRepDto queryStoreAccountInfo(Long userNo, Integer userType, Long storeNo);

    /**
     * 更新店铺信息
     */
    void modifyUserStore(StoreInfoModifyReqDto storeInfoModifyReqDto);

    /**
     * 查询用户已经申请开通的币种
     *
     * @param userNo 用户号
     * @return 返回结果
     */
    List<String> queryAccApplyCcy(Long userNo);
}
