package com.baofu.cbpayservice.common.util;

import com.baofu.cbpayservice.common.constants.NumberConstants;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 计算手续费工具类
 * <p>
 * User: 不良人 Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 **/
@Slf4j
public final class TransferFeeUtil {

    private TransferFeeUtil() {
    }

    /**
     * 计算子账户转账到主账户手续费(x/（1-x）* transferAmt)
     *
     * @param transferAmt 转账金额
     * @param feeRate     费率
     * @return 手续费金额
     */
    public static BigDecimal withdrawCashFee(BigDecimal transferAmt, BigDecimal feeRate) {

        if (transferAmt == null || feeRate == null || BigDecimal.ONE.compareTo(feeRate) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal rate = feeRate.divide(BigDecimal.ONE.subtract(feeRate), NumberConstants.EIGHT, BigDecimal.ROUND_DOWN);
        log.info("call 计算真实费率:{}", rate);

        BigDecimal fee = rate.multiply(transferAmt);
        log.info("call 计算子账户转账到主账户手续费金额:{}", fee);

        return fee.setScale(NumberConstants.TWO, BigDecimal.ROUND_HALF_UP);
    }

}
