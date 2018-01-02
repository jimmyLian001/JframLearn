package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.AuthBankDo;
import com.baofu.international.global.account.core.dal.model.AuthOrgDo;
import com.baofu.international.global.account.core.dal.model.AuthPersonDo;

/**
 * 个人认证操作接口
 * <p>
 * 1、新增个人认证记录
 * 2、新增银行卡认证记录
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface AuthPersonManager {

    /**
     * 新增个人认证记录
     *
     * @param authPersonDo 个人信息
     */
    void addAuthPerson(AuthPersonDo authPersonDo);

    /**
     * 新增银行卡认证记录
     *
     * @param authBankDo 银行卡信息
     */
    void addAuthBank(AuthBankDo authBankDo);

    /**
     * 新增企业实名认证记录
     *
     * @param authOrgDo 企业信息
     */
    void addAuthOrg(AuthOrgDo authOrgDo);
}
