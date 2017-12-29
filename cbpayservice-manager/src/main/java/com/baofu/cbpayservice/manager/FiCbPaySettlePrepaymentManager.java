package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
public interface FiCbPaySettlePrepaymentManager {

    /**
     * 插入数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     */
    void create(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     */
    void modifyByIncomeNo(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     */
    void modifyByApplyId(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 获取结汇垫资信息
     *
     * @param incomeNo 汇入汇款编号
     * @return CbPaySettlePrepaymentDo 垫资信息
     */
    CbPaySettlePrepaymentDo getPrepaymentInfoByIncomeNo(String incomeNo);

    /**
     * 获取结汇垫资信息
     *
     * @param applyId 垫资申请编号
     * @return CbPaySettlePrepaymentDo 垫资信息
     */
    CbPaySettlePrepaymentDo getPrepaymentInfoByApplyId(Long applyId);


}
