package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.NumberDict;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 描述：计算手续费工具类（迁移）
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
public final class WithdrawFeeUtil {

    private WithdrawFeeUtil() {
    }

    /**
     * 计算子账户转账到主账户手续费(x/（1-x）* withdrawAmt)  （旧的，待废除0
     *
     * @param withdrawAmt 提现金额
     * @param feeRate     费率
     * @return 手续费金额
     */
    public static BigDecimal withdrawCashFee(BigDecimal withdrawAmt, BigDecimal feeRate) {

        if (withdrawAmt == null || feeRate == null || BigDecimal.ONE.compareTo(feeRate) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal rate = feeRate.divide(BigDecimal.ONE.subtract(feeRate), NumberDict.EIGHT, BigDecimal.ROUND_DOWN);
        log.info("call 计算真实费率:{}", rate);

        BigDecimal fee = rate.multiply(withdrawAmt);
        log.info("call 计算子账户转账到主账户手续费金额:{}", fee);

        return fee.setScale(NumberDict.TWO, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算子账户转账到主账户手续费1%
     *
     * @param withdrawAmt 提现金额
     * @param feeRate     费率
     * @return 手续费金额
     */
    public static BigDecimal calcWithdrawCashFee(BigDecimal withdrawAmt, BigDecimal feeRate) {
        if (withdrawAmt == null || feeRate == null || BigDecimal.ONE.compareTo(feeRate) < 0) {
            return BigDecimal.ZERO;
        }
        log.info("call 计算真实费率:{}", feeRate);
        BigDecimal fee = feeRate.multiply(withdrawAmt);
        log.info("call 计算子账户转账到主账户手续费金额:{}", fee);

        return fee.setScale(NumberDict.TWO, BigDecimal.ROUND_HALF_UP);
    }

}
