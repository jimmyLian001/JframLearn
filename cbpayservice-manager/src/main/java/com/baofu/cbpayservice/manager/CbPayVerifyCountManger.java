package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.CbPayVerifyCountDo;

/**
 * 跨境订单实名认证Manager
 * <p>
 * User: 莫小阳 Date:2017/05/31 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayVerifyCountManger {

    /**
     * 实名认证添加汇总信息
     *
     * @param cbPayVerifyCount 汇总信息
     */
    void addCbPayVerifyCount(CbPayVerifyCountDo cbPayVerifyCount);

    /**
     * 根据文件批次号查询是否抽查过
     *
     * @param fileBathNo 文件批次号
     * @return 结果
     */
    CbPayVerifyCountDo queryVerifyCounByFileBatchNo(Long fileBathNo);

    /**
     * 更新实名认证汇总信息
     *
     * @param cbPayVerifyCountDo 汇总信息
     */
    void updateVerifyCount(CbPayVerifyCountDo cbPayVerifyCountDo);
}
