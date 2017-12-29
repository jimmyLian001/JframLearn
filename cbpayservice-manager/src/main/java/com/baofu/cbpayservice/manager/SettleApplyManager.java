package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;

/**
 * <p>
 * 1、根据申请编号更新汇入申请状态
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
public interface SettleApplyManager {


    /**
     * 根据申请编号更新汇入申请状态
     *
     * @param applyNo 申请编号
     * @param status  需要更新之后的订单状态
     */
    void modifySettleApply(FiCbPaySettleApplyDo fiCbPaySettleApplyDo);


}
