package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;

/**
 * 银行到账通知信息操作服务
 * <p>
 * User: 不良人 Date:2017/4/19 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbPaySettleManager {

    /**
     * 运营设置匹配信息
     *
     * @param fiCbPaySettleDo      结汇订单信息
     * @param fiCbPaySettleApplyDo 结汇申请信息
     */
    void operationSet(FiCbPaySettleDo fiCbPaySettleDo, FiCbPaySettleApplyDo fiCbPaySettleApplyDo);
}
