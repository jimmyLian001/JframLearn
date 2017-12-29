package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;

import java.util.List;

/**
 * 多币种账户信息数据服务接口
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleBankManager {

    /**
     * 根据商户号和币种查询账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @param entityNo  备案主体编号
     * @return 账户信息
     */
    FiCbPaySettleBankDo selectBankAccByEntityNo(Long memberId, String settleCcy, String entityNo);

    /**
     * 根据商户号和币种查询账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @return List 账户信息
     */
    List<FiCbPaySettleBankDo> selectBankAccsByMIdCcy(Long memberId, String settleCcy);

    /**
     * 添加商户账户信息
     *
     * @param cbPaySettleBankDo 商户账户信息
     */
    void addSettleBank(FiCbPaySettleBankDo cbPaySettleBankDo);

    /**
     * 根据记录编号查询
     *
     * @param recordId 记录编号
     * @return 商户账户信息
     */
    FiCbPaySettleBankDo selectByRecordId(Long recordId);

    /**
     * 修改商户币种账户信息
     *
     * @param cbPaySettleBankDo 商户币种账户信息
     */
    void modifySettleBank(FiCbPaySettleBankDo cbPaySettleBankDo);

    /**
     * @param memberId  商户号
     * @param bankAccNo 银行账户编号
     * @param remitCcy  币种
     * @return 商户备案主体编号
     */
    String selectMerchantEntityNo(Long memberId, String bankAccNo, String remitCcy);

    /**
     * 查询当前币种最大的子账户编号
     *
     * @param memberId  商户编号
     * @param settleCcy 结算币种
     * @return 返回最大的子账户编号
     */
    String queryMemberMaxEntityNo(Long memberId, String settleCcy);
}
