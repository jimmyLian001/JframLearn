package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserAccInfoReqDo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserStoreInfoRespDo;

import java.util.List;

/**
 * <p>
 * 1、根据用户号和银行账户号查询收款账户信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public interface UserAccountManager {

    /**
     * 根据用户号和银行账户号查询收款账户信息
     *
     * @param bankAccNo 银行账户
     * @param userNo    用户编号
     * @return 返回收款账户信息
     */
    List<UserPayeeAccountDo> queryPayeeAccount(Long userNo, String bankAccNo);

    /**
     * 根据用户号和银行账户号币种查询收款账户信息
     *
     * @param bankAccNo 银行账户
     * @param userNo    用户编号
     * @return 返回收款账户信息
     * @parm ccy 币种
     */
    List<UserPayeeAccountDo> queryPayeeAccount(Long userNo, String bankAccNo, String ccy);

    /**
     * 根据用户号和币种查询wyreId
     *
     * @param userAccInfoReqDo 币种
     * @return 结果
     */
    List<UserStoreInfoRespDo> queryUserStoreByCcy(UserAccInfoReqDo userAccInfoReqDo);


    /**
     * 用户账户信息查询
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return
     */
    UserPayeeAccountDo queryUserAccount(Long userNo, Long accountNo);
}
