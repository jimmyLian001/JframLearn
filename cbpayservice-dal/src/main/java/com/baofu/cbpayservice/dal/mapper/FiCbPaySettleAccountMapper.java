package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleAccountDo;

import java.util.List;

/**
 * 结汇操作结汇账户管理Mapper操作
 * <p>
 * User: lian zd Date:2017/7/28 ProjectName: cbpayservice Version: 1.0
 */

public interface FiCbPaySettleAccountMapper {

    /**
     * 插入数据库记录
     *
     * @param fiCbPaySettleAccountDo
     */
    int insert(FiCbPaySettleAccountDo fiCbPaySettleAccountDo);


    /**
     * 根据OrderId更新数据
     *
     * @param fiCbPaySettleAccountDo
     */
    int updateByRecordId(FiCbPaySettleAccountDo fiCbPaySettleAccountDo);


    /**
     * 根据recordId查询账户信息是否存在
     *
     * @param recordId
     */
    FiCbPaySettleAccountDo queryRecordIdExist(Long recordId);

    /**
     * 查询结汇账户信息
     *
     * @param fiCbPaySettleAccountDo 查询结汇账户参数
     * @return 结果
     */
    List<FiCbPaySettleAccountDo> listSettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo);
}