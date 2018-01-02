package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.SysBankInfoDo;

import java.util.List;

/**
 * 系统银行卡支持接口
 * <p>
 * 1、查询系统支持发卡行
 * </p>
 * User: lian zd  Date: 2017/11/11 ProjectName: globalaccount Version: 1.0
 */
public interface TSysBankInfoManager {

    /**
     * 查询系统支持发卡行
     *
     * @return 系统支持银行卡信息
     */
    List<SysBankInfoDo> querySysBankInfo();

    /**
     * 根据银行编码查询银行信息
     *
     * @param bankCode 银行编码
     * @return 银行信息
     */
    SysBankInfoDo querySysBankInfo(String bankCode);
}
