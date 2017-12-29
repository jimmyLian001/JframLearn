package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.AccountBalanceRespBo;
import com.baofu.cbpayservice.facade.models.res.AccountBalanceRespDto;

/**
 * @author 莫小阳  on 2017/10/20.
 */
public final class BalanceParamConvert {

    private BalanceParamConvert() {
    }

    /**
     * 账户信息转换
     *
     * @param accountBalanceBo 账户信息
     * @return 结果
     */
    public static AccountBalanceRespDto convertBalanceBoToDto(AccountBalanceRespBo accountBalanceBo) {
        if (accountBalanceBo == null) {
            return null;
        }
        AccountBalanceRespDto accountBalanceRespDto = new AccountBalanceRespDto();
        accountBalanceRespDto.setOrderAmount(accountBalanceBo.getOrderAmount());
        accountBalanceRespDto.setAccountAmount(accountBalanceBo.getAccountAmount());
        return accountBalanceRespDto;
    }
}
