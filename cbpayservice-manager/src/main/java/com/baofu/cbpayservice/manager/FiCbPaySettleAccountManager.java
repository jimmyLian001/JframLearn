package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleAccountDo;

import java.util.List;

/**
 * 结汇账户管理操作服务
 * <p>
 * User: lian zd Date:2017/8/2 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbPaySettleAccountManager {

    /**
     * 结汇账户管理新增账户信息
     *
     * @param fiCbPaySettleAccountDo 商户新增账户信息传入对象
     */
    void addSettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo);

    /**
     * 结汇账户管理修改账户信息
     *
     * @param fiCbPaySettleAccountDo 商户修改账户信息传入对象
     */
    void modifySettleAccount(FiCbPaySettleAccountDo fiCbPaySettleAccountDo);

    /**
     * 根据recordId查询该数据是否存在
     *
     * @param recordId 商户账户信息标志ID
     * @return FiCbPaySettleAccountDos
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
