package com.baofu.international.global.account.core.biz.external;

import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.manager.UserWithdrawCashManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Component
public class UserAccountBizImpl {

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户前台提现服务
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 描述：查询用户对应有效账户信息
     *
     * @param userNo    用户号
     * @param accountNo 提现账号
     * @return 用户账户信息
     */
    public UserPayeeAccountDo queryUserAccountInfo(Long userNo, Long accountNo) {
        UserPayeeAccountDo queryAccountDo = new UserPayeeAccountDo();
        queryAccountDo.setUserNo(userNo);
        queryAccountDo.setAccountNo(accountNo);
        queryAccountDo.setStatus(NumberDict.ONE);
         UserPayeeAccountDo tPayeeAccountDo = userWithdrawCashManager.selectUserAccountInfo(queryAccountDo);
        if (tPayeeAccountDo == null) {
            log.info("call 用户号:{},账户号：{},用户账户信息不存在", userNo, accountNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190023);
        }
        return tPayeeAccountDo;
    }

    /**
     * 描述：根据用户号&钱包ID查询用户账户信息
     *
     * @param walletId  钱包账号
     * @param channelId 渠道编号
     * @return 用户账户信息
     */
    public UserPayeeAccountDo queryUserAccountByWalletId(String walletId, Long channelId) {
        UserPayeeAccountDo queryAccountDo = new UserPayeeAccountDo();
        queryAccountDo.setWalletId(walletId);
        queryAccountDo.setStatus(NumberDict.ONE);
        queryAccountDo.setChannelId(channelId);
        return userWithdrawCashManager.selectUserAccountInfo(queryAccountDo);
    }

    /**
     * 描述：查询用户对应有效账户信息
     *
     * @param userNo 用户号
     * @param ccy    提现币种
     * @return 用户账户信息
     */
    public List<UserPayeeAccountDo> selectPayeeAccountByCcy(Long userNo, String ccy) {
        return userWithdrawCashManager.selectPayeeAccountByCcy(userNo, null, ccy);
    }

}
