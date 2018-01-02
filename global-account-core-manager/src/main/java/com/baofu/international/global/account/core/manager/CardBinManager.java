package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface CardBinManager {

    /**
     * 查询卡bin信息
     *
     * @param cardBin 卡bin
     * @return 卡bin信息
     */
    BankCardBinInfoDo queryCardBin(String cardBin);
}
