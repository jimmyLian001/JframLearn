package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
public interface FiCbPaySettlePrepaymentMapper {

    /**
     * 插入数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    int insert(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    int updateByIncomeNo(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 更新数据库记录
     *
     * @param cbPaySettlePrepaymentDo 垫资对象
     * @return
     */
    int updateByApplyId(CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo);

    /**
     * 查询垫资信息
     *
     * @param incomeNo 汇入汇款编号
     */
    CbPaySettlePrepaymentDo queryByIncomeNo(String incomeNo);

    /**
     * 查询垫资信息
     *
     * @param applyId 汇入汇款编号
     */
    CbPaySettlePrepaymentDo queryByApplyId(Long applyId);

}
