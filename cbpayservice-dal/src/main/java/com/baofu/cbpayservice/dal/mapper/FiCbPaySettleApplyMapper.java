package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import org.apache.ibatis.annotations.Param;

public interface FiCbPaySettleApplyMapper {

    /**
     * 插入数据库记录
     *
     * @param fiCbPaySettleApplyDo 数据对象
     */
    int insert(FiCbPaySettleApplyDo fiCbPaySettleApplyDo);

    /**
     * 根据orderId更新
     *
     * @param fiCbPaySettleApplyDo 数据对象
     */
    int updateByKeySelective(FiCbPaySettleApplyDo fiCbPaySettleApplyDo);

    /**
     * 根据商户编号和TT编号（汇入编号）查询
     *
     * @param memberId 商户编号
     * @param incomeNo TT编号
     */
    FiCbPaySettleApplyDo queryByIncomeNo(@Param("memberId") Long memberId, @Param("incomeNo") String incomeNo);

    /**
     * 根据申请编号查询
     *
     * @param applyNo 申请编号
     */
    FiCbPaySettleApplyDo queryByApplyNo(@Param("orderId") Long applyNo);

    /**
     * 根据匹配id查询（matchingOrderId）
     *
     * @param settleId 收汇Id（匹配ID）
     * @return 申请信息
     */
    FiCbPaySettleApplyDo queryBySettleId(@Param("matchingOrderId") Long settleId);

    /**
     * 根据参数查询
     *
     * @param memberId 商户号
     * @param incomeNo 汇款银行编号
     * @return List<FiCbPaySettleApplyDo> 结汇申请集合
     */
    FiCbPaySettleApplyDo queryByParams(@Param("memberId") Long memberId, @Param("incomeNo") String incomeNo);

    /**
     * 根据匹配ID更新申请状态
     *
     * @param matchingOrderId 匹配ID
     * @return 结果
     */
    int updateByKeyMatchingOrderId(@Param("matchingOrderId") Long matchingOrderId, @Param("status") int status);
}