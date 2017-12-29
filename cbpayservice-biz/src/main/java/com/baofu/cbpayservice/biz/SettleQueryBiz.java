package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.SettleNotifyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.biz.models.SettleQueryReqParamBo;

/**
 * <p>
 * 1、根据商户编号、商户请求流水号查询商户申请订单信息
 * 2、根据商户编号、结汇流水号查询商户申请订单信息
 * 3、根据商户编号、汇入申请内部流水号查询商户申请订单信息
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
public interface SettleQueryBiz {


    /**
     * 根据商户编号、商户请求流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param incomeNo 汇入汇款流水号
     * @return 返回商户需要查询的结果
     */
    SettleNotifyMemberBo querySettleNotifyByReqNo(Long memberId, String incomeNo);

    /**
     * 根据商户编号、结汇流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param settleNo 结汇内部系统唯一编号
     * @return 返回商户需要查询的结果
     */
    SettleNotifyMemberBo querySettleNotifyBySettleNo(Long memberId, Long settleNo);

    /**
     * 根据商户编号、汇入申请内部流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param applyNo  申请编号
     * @return 返回商户需要查询的结果
     */
    SettleNotifyMemberBo querySettleNotifyByApplyNo(Long memberId, Long applyNo);

    /**
     * 根据商户查询信息
     * @param settleQueryReqParamBo
     * @return
     */
    SettleNotifyMemberBo querySettleNotify(SettleQueryReqParamBo settleQueryReqParamBo);
}
