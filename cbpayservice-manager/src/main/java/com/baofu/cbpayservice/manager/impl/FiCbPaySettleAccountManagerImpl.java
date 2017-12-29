package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleAccountMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleAccountDo;
import com.baofu.cbpayservice.manager.FiCbPaySettleAccountManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结汇账户管理操作服务
 * <p>
 * User: lian zd Date:2017/8/2 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class FiCbPaySettleAccountManagerImpl implements FiCbPaySettleAccountManager {

    /**
     * 跨境结汇订单信息Manager
     */
    @Autowired
    private FiCbPaySettleAccountMapper fiCbPaySettleAccountMapper;

    @Override
    public void addSettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo) {
        fiCbPaySettleAccountMapper.insert(fiCbPaySettleAccountDo);
    }

    @Override
    public void modifySettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo) {
        fiCbPaySettleAccountMapper.updateByRecordId(fiCbPaySettleAccountDo);
    }

    @Override
    public FiCbPaySettleAccountDo queryRecordIdExist(Long recordId) {
        FiCbPaySettleAccountDo fiCbPaySettleAccountDo = fiCbPaySettleAccountMapper.queryRecordIdExist(recordId);
        return fiCbPaySettleAccountDo;
    }

    /**
     * 查询结汇账户信息
     * @param fiCbPaySettleAccountDo 查询结汇账户参数
     * @return 结果
     */
    @Override
    public List<FiCbPaySettleAccountDo> listSettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo) {
        return fiCbPaySettleAccountMapper.listSettleAccount(fiCbPaySettleAccountDo);
    }


}
