package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FiCbPaySettleBankMapper {

    /**
     * 插入数据库记录
     *
     * @param fiCbPaySettleBankDo 币种账户信息
     */
    int insert(FiCbPaySettleBankDo fiCbPaySettleBankDo);

    /**
     * 根据记录编号查询
     *
     * @param recordId 记录编号
     */
    FiCbPaySettleBankDo selectByRecordId(Long recordId);

    /**
     * 根据记录编号修改
     *
     * @param fiCbPaySettleBankDo 币种账户信息
     */
    int updateByRecordId(FiCbPaySettleBankDo fiCbPaySettleBankDo);

    /**
     * 根据商户号和币种,商户备案主体编号查询指定账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @param entityNo  商户备案主体编号
     * @return 商户币种账户信息
     */
    FiCbPaySettleBankDo selectBankAccByEntityNo(@Param("memberId") Long memberId, @Param("settleCcy") String settleCcy, @Param("entityNo") String entityNo);

    /**
     * 根据商户号和币种查询账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @return 商户币种账户信息
     */
    List<FiCbPaySettleBankDo> selectBankAccsByMIdCcy(@Param("memberId") Long memberId, @Param("settleCcy") String settleCcy);

    /**
     * @param memberId  商户号
     * @param bankAccNo 银行账户编号
     * @param remitCcy  币种 币种
     * @return 商户备案主体编号
     */
    String selectMerchantEntityNo(@Param("memberId") Long memberId, @Param("bankAccNo") String bankAccNo, @Param("remitCcy") String remitCcy);

    /**
     * 查询当前币种最大的子账户编号
     *
     * @param memberId  商户编号
     * @param settleCcy 结算币种
     * @return 返回最大的子账户编号
     */
    String selectMemberMaxEntityNo(@Param("memberId") Long memberId, @Param("settleCcy") String settleCcy);
}