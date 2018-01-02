package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.*;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
public interface ApplyAccountManager {

    /**
     * 新增申请记录
     *
     * @param userPayeeAccountApplyDo userPayeeAccountApplyDo
     */
    void addApplyRecourd(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 新增开户首次返回账号信息
     *
     * @param applyNo  申请编号
     * @param walletId walletId
     * @return 返回结果
     */
    Long addAccountInfo(Long applyNo, String walletId);

    /**
     * 根据walletId 更新当前数据信息
     *
     * @param userPayeeAccountDo userPayeeAccountDo
     */
    void modifyAccountInfo(UserPayeeAccountDo userPayeeAccountDo);

    /**
     * 根据walletId查询当前账户信息
     *
     * @param accountNo 账户号
     * @return 返回结果
     */
    UserPayeeAccountDo queryAccountInfo(Long accountNo);

    /**
     * 根据申请的acc_no更新申请状态
     *
     * @param userPayeeAccountApplyDo userPayeeAccountApplyDo
     */
    void modifyApplyStatus(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 新增账户余额表
     *
     * @param userAccountBalDo userAccountBalDo
     */
    void addAccountBal(UserAccountBalDo userAccountBalDo);

    /**
     * 根据申请号查询
     *
     * @param userPayeeAccountApplyDo userPayeeAccountApplyDo
     */
    List<UserPayeeAccountApplyDo> getApplyAccountByApplyNo(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 根据订单号去查询
     *
     * @param applyId userInfoNo
     * @return 返回结果
     */
    UserPayeeAccountApplyDo queryApplyAccountByApplyId(Long applyId);

    /**
     * 店铺编号
     *
     * @param storeNo 店铺编号
     * @return 返回结果
     */
    UserPayeeAccountApplyDo queryApplyAccountByStoreNo(Long storeNo);

    /**
     * 根据订单号去查询
     *
     * @param qualifiedNo qualifiedNo
     * @return 返回结果
     */
    List<UserPayeeAccountApplyDo> queryApplyAccountByQualifiedNo(Long qualifiedNo);

    /**
     * 查询用户申请币种信息
     *
     * @param userNo 用户号
     * @return 币种集合
     */
    List<String> queryAccApplyCcyByUserNo(Long userNo);
}
