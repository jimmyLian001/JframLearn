package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleBankMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 多币种账户信息数据服务接口
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Repository
public class CbPaySettleBankManagerImpl implements CbPaySettleBankManager {

    /**
     * 多币种账户信息数据操作服务
     */
    @Autowired
    private FiCbPaySettleBankMapper fiCbPaySettleBankMapper;

    /**
     * 根据商户号和币种查询账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @param entityNo  备案主体编号
     * @return 账户信息
     */
    @Override
    public FiCbPaySettleBankDo selectBankAccByEntityNo(Long memberId, String settleCcy, String entityNo) {

        log.info("call 查询账户信息 根据商户号:{},币种:{},备案主体编号:{}查询", memberId, settleCcy, entityNo);
        FiCbPaySettleBankDo paySettleBankDo = fiCbPaySettleBankMapper.selectBankAccByEntityNo(memberId,
                settleCcy, entityNo);
        log.info("call 根据商户号和币种查询账户信息 查询返回信息：{}", paySettleBankDo);
        if (paySettleBankDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00173);
        }

        return paySettleBankDo;
    }

    /**
     * 根据商户号和币种查询账户信息
     *
     * @param memberId  商户号
     * @param settleCcy 币种
     * @return List 账户信息
     */
    @Override
    public List<FiCbPaySettleBankDo> selectBankAccsByMIdCcy(Long memberId, String settleCcy) {
        return fiCbPaySettleBankMapper.selectBankAccsByMIdCcy(memberId, settleCcy);
    }

    /**
     * 添加商户账户信息
     *
     * @param cbPaySettleBankDo 商户账户信息
     */
    @Override
    public void addSettleBank(FiCbPaySettleBankDo cbPaySettleBankDo) {
        ParamValidate.checkUpdate(fiCbPaySettleBankMapper.insert(cbPaySettleBankDo), "添加商户币种账户信息错误");
    }

    /**
     * 根据记录编号查询
     *
     * @param recordId 记录编号
     * @return 商户账户信息
     */
    public FiCbPaySettleBankDo selectByRecordId(Long recordId) {
        return fiCbPaySettleBankMapper.selectByRecordId(recordId);
    }

    /**
     * 修改商户币种账户信息
     *
     * @param cbPaySettleBankDo 商户币种账户信息
     */
    @Override
    public void modifySettleBank(FiCbPaySettleBankDo cbPaySettleBankDo) {
        ParamValidate.checkUpdate(fiCbPaySettleBankMapper.updateByRecordId(cbPaySettleBankDo), "修改商户币种账户信息错误");
    }

    /**
     * @param memberId  商户号
     * @param bankAccNo 银行账户编号
     * @param remitCcy  币种
     * @return 商户备案主体编号
     */
    @Override
    public String selectMerchantEntityNo(Long memberId, String bankAccNo, String remitCcy) {
        return fiCbPaySettleBankMapper.selectMerchantEntityNo(memberId, bankAccNo, remitCcy);
    }

    /**
     * 查询当前币种最大的子账户编号
     *
     * @param memberId  商户编号
     * @param settleCcy 结算币种
     * @return 返回最大的子账户编号
     */
    @Override
    public String queryMemberMaxEntityNo(Long memberId, String settleCcy) {

        return fiCbPaySettleBankMapper.selectMemberMaxEntityNo(memberId, settleCcy);
    }
}
