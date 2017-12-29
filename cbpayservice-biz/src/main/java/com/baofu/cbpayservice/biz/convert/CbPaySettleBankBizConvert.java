package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPaySettleBankBo;
import com.baofu.cbpayservice.biz.models.ModifySettleBankBo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import org.apache.commons.lang.StringUtils;

/**
 * 多币种账户信息biz层参数转换
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: feature-BFO-793-170426-zwb Version: 1.0
 */
public final class CbPaySettleBankBizConvert {

    private CbPaySettleBankBizConvert() {

    }

    /**
     * 多币种账户信息biz层参数转换成Do层参数
     *
     * @param cbPaySettleBankBo 多币种账户信息biz层参数
     * @param bankAccSeqNo      子账户序列号
     * @param recordId          记录编号
     * @return Do层参数
     */
    public static FiCbPaySettleBankDo toFiCbPaySettleBankDo(CbPaySettleBankBo cbPaySettleBankBo, String bankAccSeqNo, Long recordId) {

        FiCbPaySettleBankDo fiCbPaySettleBankDo = new FiCbPaySettleBankDo();
        fiCbPaySettleBankDo.setRecordId(recordId);
        fiCbPaySettleBankDo.setMemberId(cbPaySettleBankBo.getMemberId());
        fiCbPaySettleBankDo.setSettleCcy(cbPaySettleBankBo.getSettleCcy());
        fiCbPaySettleBankDo.setBankAccNo(cbPaySettleBankBo.getBankAccNo());
        fiCbPaySettleBankDo.setBankName(cbPaySettleBankBo.getBankName());
        fiCbPaySettleBankDo.setBankAccName(cbPaySettleBankBo.getBankAccName());
        fiCbPaySettleBankDo.setPayeeAddress(cbPaySettleBankBo.getPayeeAddress());
        fiCbPaySettleBankDo.setCostBorne(cbPaySettleBankBo.getCostBorne());
        fiCbPaySettleBankDo.setBankSwiftCode(cbPaySettleBankBo.getBankSwiftCode());
        fiCbPaySettleBankDo.setBankLargeCode(cbPaySettleBankBo.getBankLargeCode());
        fiCbPaySettleBankDo.setSettleSystem(cbPaySettleBankBo.getSettleSystem());
        fiCbPaySettleBankDo.setMiddleSwiftCode(cbPaySettleBankBo.getMiddleSwiftCode());
        fiCbPaySettleBankDo.setMiddleBankName(cbPaySettleBankBo.getMiddleBankName());
        fiCbPaySettleBankDo.setMiddleBankCountry(cbPaySettleBankBo.getMiddleBankCountry());
        fiCbPaySettleBankDo.setMiddleBankCode(cbPaySettleBankBo.getMiddleBankCode());
        fiCbPaySettleBankDo.setOceanicFlag(cbPaySettleBankBo.getOceanicFlag());
        fiCbPaySettleBankDo.setRemarks(cbPaySettleBankBo.getRemarks());
        fiCbPaySettleBankDo.setSettleStatus(cbPaySettleBankBo.getSettleStatus());
        fiCbPaySettleBankDo.setCreateBy(cbPaySettleBankBo.getCreateBy());
        fiCbPaySettleBankDo.setExchangeType(cbPaySettleBankBo.getExchangeType());
        fiCbPaySettleBankDo.setBankAddress(cbPaySettleBankBo.getBankAddress());
        fiCbPaySettleBankDo.setEntityNo(bankAccSeqNo);
        fiCbPaySettleBankDo.setBankAccType(Integer.parseInt(cbPaySettleBankBo.getBankAccType()));
        fiCbPaySettleBankDo.setBsbNumber(cbPaySettleBankBo.getBsbNumber());
        fiCbPaySettleBankDo.setCountryCode(cbPaySettleBankBo.getCountryCode());
        return fiCbPaySettleBankDo;
    }

    /**
     * 商户币种账户信息修改对象转换
     *
     * @param modifySettleBankBo 商户币种账户信息修改对象
     * @return 要修改信息
     */
    public static FiCbPaySettleBankDo toFiCbPaySettleBankDo(ModifySettleBankBo modifySettleBankBo) {

        FiCbPaySettleBankDo fiCbPaySettleBankDo = new FiCbPaySettleBankDo();
        fiCbPaySettleBankDo.setRecordId(modifySettleBankBo.getRecordId());
        fiCbPaySettleBankDo.setMemberId(modifySettleBankBo.getMemberId());
        fiCbPaySettleBankDo.setSettleCcy(modifySettleBankBo.getSettleCcy());
        fiCbPaySettleBankDo.setBankAccNo(modifySettleBankBo.getBankAccNo());
        fiCbPaySettleBankDo.setBankName(modifySettleBankBo.getBankName());
        fiCbPaySettleBankDo.setBankAccName(modifySettleBankBo.getBankAccName());
        fiCbPaySettleBankDo.setPayeeAddress(modifySettleBankBo.getPayeeAddress());
        fiCbPaySettleBankDo.setCostBorne(modifySettleBankBo.getCostBorne());
        fiCbPaySettleBankDo.setBankSwiftCode(modifySettleBankBo.getBankSwiftCode());
        fiCbPaySettleBankDo.setBankLargeCode(modifySettleBankBo.getBankLargeCode());
        fiCbPaySettleBankDo.setSettleSystem(modifySettleBankBo.getSettleSystem());
        fiCbPaySettleBankDo.setMiddleSwiftCode(modifySettleBankBo.getMiddleSwiftCode());
        fiCbPaySettleBankDo.setMiddleBankName(modifySettleBankBo.getMiddleBankName());
        fiCbPaySettleBankDo.setMiddleBankCountry(modifySettleBankBo.getMiddleBankCountry());
        fiCbPaySettleBankDo.setMiddleBankCode(modifySettleBankBo.getMiddleBankCode());
        fiCbPaySettleBankDo.setOceanicFlag(modifySettleBankBo.getOceanicFlag());
        fiCbPaySettleBankDo.setRemarks(modifySettleBankBo.getRemarks());
        fiCbPaySettleBankDo.setSettleStatus(modifySettleBankBo.getSettleStatus());
        fiCbPaySettleBankDo.setUpdateBy(modifySettleBankBo.getUpdateBy());
        fiCbPaySettleBankDo.setExchangeType(modifySettleBankBo.getExchangeType());
        fiCbPaySettleBankDo.setBankAddress(modifySettleBankBo.getBankAddress());
        fiCbPaySettleBankDo.setBankAccType(Integer.parseInt(modifySettleBankBo.getBankAccType()));
        fiCbPaySettleBankDo.setBsbNumber(modifySettleBankBo.getBsbNumber());
        fiCbPaySettleBankDo.setCountryCode(modifySettleBankBo.getCountryCode());
        return fiCbPaySettleBankDo;
    }
}
