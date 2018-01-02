package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.mapper.BankCardBinInfoMapper;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import com.baofu.international.global.account.core.manager.CardBinManager;
import com.system.commons.exception.BizServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Component
public class CardBinManagerImpl implements CardBinManager {

    /**
     * 卡bin数据库操作mapper
     */
    @Autowired
    private BankCardBinInfoMapper tBankCardBinInfoMapper;

    /**
     * 查询卡bin信息
     *
     * @param cardBin 卡bin
     * @return 卡bin信息
     */
    @Override
    public BankCardBinInfoDo queryCardBin(String cardBin) {
        List<BankCardBinInfoDo> bankCardBinInfoDos = tBankCardBinInfoMapper.queryListByCardBin(cardBin);
        //如果是空，抛出异常
        if (bankCardBinInfoDos == null || CollectionUtils.isEmpty(bankCardBinInfoDos)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190400);
        }
        return bankCardBinInfoDos.get(0);
    }
}
