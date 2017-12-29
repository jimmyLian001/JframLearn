package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.manager.FiCbPaySettleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 银行到账通知信息操作服务
 * <p>
 * User: 不良人 Date:2017/4/19 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class FiCbPaySettleManagerImpl implements FiCbPaySettleManager {

    /**
     * 商户汇入申请信息服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 银行汇入通知信息服务
     */
    @Autowired
    private FiCbPaySettleMapper fiCbPaySettleMapper;

    @Override
    @Transactional
    public void operationSet(FiCbPaySettleDo fiCbPaySettleDo, FiCbPaySettleApplyDo fiCbPaySettleApplyDo) {
        fiCbPaySettleMapper.updateByKeySelective(fiCbPaySettleDo);
        fiCbPaySettleApplyMapper.updateByKeySelective(fiCbPaySettleApplyDo);
    }


}
