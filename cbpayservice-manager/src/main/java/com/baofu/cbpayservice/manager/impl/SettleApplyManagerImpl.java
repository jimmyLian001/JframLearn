package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.manager.SettleApplyManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
@Repository
public class SettleApplyManagerImpl implements SettleApplyManager {

    /**
     * 汇入申请相关操作服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 根据申请编号更新汇入申请状态
     *
     * @param applyNo 申请编号
     * @param status  需要更新之后的订单状态
     */
    @Override
    public void modifySettleApply(FiCbPaySettleApplyDo fiCbPaySettleApplyDo) {

        ParamValidate.validateParams(fiCbPaySettleApplyMapper.updateByKeySelective(fiCbPaySettleApplyDo),
                "更新汇入申请订单状态异常");
    }
}
