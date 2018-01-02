package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.SysBankInfoDo;

import java.util.List;

public interface SysBankInfoMapper {
    /**
     * 查询系统支持银行卡
     */
    List<SysBankInfoDo> querySysBankInfo();

    /**
     * 根据银行编码查询银行信息
     *
     * @param bankCode 银行编码
     * @return 银行信息
     */
    SysBankInfoDo selectSysBankInfo(String bankCode);
}