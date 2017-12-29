package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPaySettlePrepaymentMapper;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;
import com.baofu.cbpayservice.manager.FiCbPaySettlePrepaymentManager;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 结汇垫资服务
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class FiCbPaySettlePrepaymentManagerImpl implements FiCbPaySettlePrepaymentManager {


    @Autowired
    private FiCbPaySettlePrepaymentMapper fiCbPaySettlePrepaymentMapper;

    /**
     * 插入数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    @Override
    public void create(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo) {
        ParamValidate.checkUpdate(fiCbPaySettlePrepaymentMapper.insert(cbPaySettlePrepaymentDo), "创建结汇垫资失败");
    }

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    @Override
    public void modifyByIncomeNo(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo) {

        ParamValidate.checkUpdate(fiCbPaySettlePrepaymentMapper.updateByIncomeNo(cbPaySettlePrepaymentDo), "修改结汇垫资失败");
    }

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    @Override
    public void modifyByApplyId(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo) {
        ParamValidate.checkUpdate(fiCbPaySettlePrepaymentMapper.updateByApplyId(cbPaySettlePrepaymentDo), "修改结汇垫资失败");
    }

    /**
     * 获取结汇垫资信息
     *
     * @param incomeNo 汇入汇款编号
     * @return
     */
    @Override
    public CbPaySettlePrepaymentDo getPrepaymentInfoByIncomeNo(String incomeNo) {
        return fiCbPaySettlePrepaymentMapper.queryByIncomeNo(incomeNo);
    }

    /**
     * 获取结汇垫资信息
     *
     * @param applyId 垫资申请编号
     * @return CbPaySettlePrepaymentDo 垫资信息
     */
    @Override
    public CbPaySettlePrepaymentDo getPrepaymentInfoByApplyId(Long applyId) {
        return fiCbPaySettlePrepaymentMapper.queryByApplyId(applyId);
    }
}
