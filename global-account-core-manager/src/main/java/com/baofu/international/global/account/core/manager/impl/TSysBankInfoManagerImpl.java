package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.SysBankInfoMapper;
import com.baofu.international.global.account.core.dal.model.SysBankInfoDo;
import com.baofu.international.global.account.core.manager.TSysBankInfoManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询系统支持发卡行
 * <p>
 * User: lian zd Date:2017/11/11 ProjectName: account-core Version:1.0
 */
@Service
public class TSysBankInfoManagerImpl implements TSysBankInfoManager {

    /**
     * 系统银行卡
     */
    @Autowired
    private SysBankInfoMapper sysBankInfoMapper;

    /**
     * 查询系统支持发卡行
     *
     * @return 系统支持银行卡信息
     */
    @Override
    public List<SysBankInfoDo> querySysBankInfo() {
        return sysBankInfoMapper.querySysBankInfo();
    }

    /**
     * 根据银行编码查询银行信息
     *
     * @param bankCode 银行编码
     * @return 银行信息
     */
    @Override
    public SysBankInfoDo querySysBankInfo(String bankCode) {

        SysBankInfoDo sysBankInfoDo = sysBankInfoMapper.selectSysBankInfo(bankCode);
        if (sysBankInfoDo == null) {
            throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "银行信息为空");
        }
        return sysBankInfoDo;
    }
}
