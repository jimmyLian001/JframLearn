package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPaySettleBankBo;
import com.baofu.cbpayservice.biz.models.ModifySettleBankBo;
import com.baofu.cbpayservice.facade.models.CbPaySettleBankDto;
import com.baofu.cbpayservice.facade.models.ModifySettleBankDto;

/**
 * 多币种对象转换
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
public class CbPaySettleBankConvert {

    /**
     * 添加 多币种参数转换biz层参数
     *
     * @param cbPaySettleBankDto 请求参数
     * @return 添加多币种biz参数
     */
    public static CbPaySettleBankBo toCbPaySettleBankBo(CbPaySettleBankDto cbPaySettleBankDto) {

        CbPaySettleBankBo cbPaySettleBankBo = new CbPaySettleBankBo();
        cbPaySettleBankBo.setMemberId(cbPaySettleBankDto.getMemberId());
        cbPaySettleBankBo.setSettleCcy(cbPaySettleBankDto.getSettleCcy());
        cbPaySettleBankBo.setBankAccNo(cbPaySettleBankDto.getBankAccNo());
        cbPaySettleBankBo.setBankName(cbPaySettleBankDto.getBankName());
        cbPaySettleBankBo.setBankAccName(cbPaySettleBankDto.getBankAccName());
        cbPaySettleBankBo.setPayeeAddress(cbPaySettleBankDto.getPayeeAddress());
        cbPaySettleBankBo.setCostBorne(cbPaySettleBankDto.getCostBorne());
        cbPaySettleBankBo.setBankSwiftCode(cbPaySettleBankDto.getBankSwiftCode());
        cbPaySettleBankBo.setBankLargeCode(cbPaySettleBankDto.getBankLargeCode());
        cbPaySettleBankBo.setSettleSystem(cbPaySettleBankDto.getSettleSystem());
        cbPaySettleBankBo.setMiddleSwiftCode(cbPaySettleBankDto.getMiddleSwiftCode());
        cbPaySettleBankBo.setMiddleBankName(cbPaySettleBankDto.getMiddleBankName());
        cbPaySettleBankBo.setMiddleBankCountry(cbPaySettleBankDto.getMiddleBankCountry());
        cbPaySettleBankBo.setMiddleBankCode(cbPaySettleBankDto.getMiddleBankCode());
        cbPaySettleBankBo.setOceanicFlag(cbPaySettleBankDto.getOceanicFlag());
        cbPaySettleBankBo.setRemarks(cbPaySettleBankDto.getRemarks());
        cbPaySettleBankBo.setCreateBy(cbPaySettleBankDto.getCreateBy());
        cbPaySettleBankBo.setExchangeType(Integer.valueOf(cbPaySettleBankDto.getExchangeType()));
        cbPaySettleBankBo.setBankAddress(cbPaySettleBankDto.getBankAddress());
        cbPaySettleBankBo.setBsbNumber(cbPaySettleBankDto.getBsbNumber());
        cbPaySettleBankBo.setBankAccType(cbPaySettleBankDto.getBankAccType());
        if (cbPaySettleBankDto.getSettleStatus() != null) {
            cbPaySettleBankBo.setSettleStatus(Integer.parseInt(cbPaySettleBankDto.getSettleStatus()));
        }
        cbPaySettleBankBo.setCountryCode(cbPaySettleBankDto.getCountryCode());
        return cbPaySettleBankBo;
    }

    /**
     * 商户外币账户信息修改对象转换
     *
     * @param modifySettleBankDto 商户外币账户信息修改请求参数
     * @return 商户外币账户信息修改信息
     */
    public static ModifySettleBankBo toModifySettleBankBo(ModifySettleBankDto modifySettleBankDto) {

        ModifySettleBankBo modifySettleBankBo = new ModifySettleBankBo();
        modifySettleBankBo.setRecordId(modifySettleBankDto.getRecordId());
        modifySettleBankBo.setMemberId(modifySettleBankDto.getMemberId());
        modifySettleBankBo.setSettleCcy(modifySettleBankDto.getSettleCcy());
        modifySettleBankBo.setBankAccNo(modifySettleBankDto.getBankAccNo());
        modifySettleBankBo.setBankName(modifySettleBankDto.getBankName());
        modifySettleBankBo.setBankAccName(modifySettleBankDto.getBankAccName());
        modifySettleBankBo.setPayeeAddress(modifySettleBankDto.getPayeeAddress());
        modifySettleBankBo.setCostBorne(modifySettleBankDto.getCostBorne());
        modifySettleBankBo.setBankSwiftCode(modifySettleBankDto.getBankSwiftCode());
        modifySettleBankBo.setBankLargeCode(modifySettleBankDto.getBankLargeCode());
        modifySettleBankBo.setSettleSystem(modifySettleBankDto.getSettleSystem());
        modifySettleBankBo.setMiddleSwiftCode(modifySettleBankDto.getMiddleSwiftCode());
        modifySettleBankBo.setMiddleBankName(modifySettleBankDto.getMiddleBankName());
        modifySettleBankBo.setMiddleBankCountry(modifySettleBankDto.getMiddleBankCountry());
        modifySettleBankBo.setMiddleBankCode(modifySettleBankDto.getMiddleBankCode());
        modifySettleBankBo.setOceanicFlag(modifySettleBankDto.getOceanicFlag());
        modifySettleBankBo.setRemarks(modifySettleBankDto.getRemarks());
        modifySettleBankBo.setUpdateBy(modifySettleBankDto.getUpdateBy());
        modifySettleBankBo.setBankAddress(modifySettleBankDto.getBankAddress());
        modifySettleBankBo.setExchangeType(Integer.valueOf(modifySettleBankDto.getExchangeType()));
        modifySettleBankBo.setBsbNumber(modifySettleBankDto.getBsbNumber());
        modifySettleBankBo.setBankAccType(modifySettleBankDto.getBankAccType());
        modifySettleBankBo.setCountryCode(modifySettleBankDto.getCountryCode());
        if (modifySettleBankDto.getSettleStatus() != null) {
            modifySettleBankBo.setSettleStatus(Integer.parseInt(modifySettleBankDto.getSettleStatus()));
        }
        return modifySettleBankBo;
    }
}
