package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.AuthBankMapper;
import com.baofu.international.global.account.core.dal.mapper.AuthOrgMapper;
import com.baofu.international.global.account.core.dal.mapper.AuthPersonMapper;
import com.baofu.international.global.account.core.dal.model.AuthBankDo;
import com.baofu.international.global.account.core.dal.model.AuthOrgDo;
import com.baofu.international.global.account.core.dal.model.AuthPersonDo;
import com.baofu.international.global.account.core.manager.AuthPersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 个人认证操作接口
 * <p>
 * 1,新增个人认证记录
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Component
public class AuthPersonManagerImpl implements AuthPersonManager {

    /**
     * 个人认证操作mapper
     */
    @Autowired
    private AuthPersonMapper tAuthPersonMapper;

    /**
     * 银行卡认证操作mapper
     */
    @Autowired
    private AuthBankMapper tAuthBankMapper;

    /**
     * 企业认证操作mapper
     */
    @Autowired
    private AuthOrgMapper tAuthOrgMapper;

    /**
     * 新增个人认证记录
     *
     * @param authPersonDo 个人认证信息
     */
    @Override
    public void addAuthPerson(AuthPersonDo authPersonDo) {
        tAuthPersonMapper.insert(authPersonDo);
    }

    /**
     * 新增银行卡认证记录
     *
     * @param authBankDo 银行卡信息
     */
    @Override
    public void addAuthBank(AuthBankDo authBankDo) {
        tAuthBankMapper.insert(authBankDo);
    }

    /**
     * 新增企业实名认证记录
     *
     * @param authOrgDo 企业信息
     */
    @Override
    public void addAuthOrg(AuthOrgDo authOrgDo) {
        tAuthOrgMapper.insert(authOrgDo);
    }
}
