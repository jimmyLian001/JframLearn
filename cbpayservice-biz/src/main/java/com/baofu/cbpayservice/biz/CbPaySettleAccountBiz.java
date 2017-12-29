package com.baofu.cbpayservice.biz;


import com.baofu.cbpayservice.biz.models.CbPaySettleAccountBo;


/**
 * 结汇操作结汇账户管理业务逻辑实现接口
 * <p>
 * User: lian zd Date:2017/7/31 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleAccountBiz {

    /**
     * 结汇账户管理新增账户信息
     *
     * @param cbPaySettleAccountBo 商户新增账户信息传入对象
     */
    Long addSettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo);

    /**
     * 结汇账户管理修改账户信息
     *
     * @param cbPaySettleAccountBo 商户修改账户信息传入对象
     * @param recordId             商户修改账户信息标志ID
     */
    void modifySettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo, Long recordId);

    /**
     * 结汇账户管理删除账户信息
     *
     * @param cbPaySettleAccountBo 商户删除账户信息传入对象
     * @param recordId             商户删除账户信息标志ID
     */
    void deleteSettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo, Long recordId);

    /**
     * 根据recordId查询验证信息
     *
     * @param recordId 商户账户信息标志ID
     */
    void checkSettleAccount(Long recordId);
}
