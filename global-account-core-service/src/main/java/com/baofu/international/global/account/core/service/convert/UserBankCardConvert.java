package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.AddCompanyBankCardBo;
import com.baofu.international.global.account.core.biz.models.AddPersonalBankCardBo;
import com.baofu.international.global.account.core.facade.model.AddCompanyBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.AddPersonalBankCardApplyDto;

/**
 * 用户绑定银行卡信息转换
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public class UserBankCardConvert {
    private UserBankCardConvert() {

    }

    /**
     * 用户申请添加个人银行卡信息转换
     *
     * @param personalBankCardDto 用户申请添加个人银行卡信息
     * @return fixPhoneNoApplyBo
     */
    public static AddPersonalBankCardBo toAddPersonalBankCardBo(AddPersonalBankCardApplyDto personalBankCardDto) {

        AddPersonalBankCardBo addPersonalBankCardBo = new AddPersonalBankCardBo();
        addPersonalBankCardBo.setAccType(Integer.parseInt(personalBankCardDto.getAccType()));
        addPersonalBankCardBo.setCardHolder(personalBankCardDto.getCardHolder());
        addPersonalBankCardBo.setCardNo(personalBankCardDto.getCardNo());
        addPersonalBankCardBo.setUserNo(Long.valueOf(personalBankCardDto.getUserNo()));
        return addPersonalBankCardBo;
    }

    /**
     * 用户申请添加个人银行卡信息转换
     *
     * @param companyBankCardApplyDto 用户申请添加企业银行卡信息
     * @return fixPhoneNoApplyBo
     */
    public static AddCompanyBankCardBo toAddCompanyBankCardBo(AddCompanyBankCardApplyDto companyBankCardApplyDto) {

        AddCompanyBankCardBo addCompanyBankCardBo = new AddCompanyBankCardBo();
        addCompanyBankCardBo.setAccType(companyBankCardApplyDto.getAccType());
        addCompanyBankCardBo.setCardHolder(companyBankCardApplyDto.getCardHolder());
        addCompanyBankCardBo.setCardNo(companyBankCardApplyDto.getCardNo());
        addCompanyBankCardBo.setUserNo(companyBankCardApplyDto.getUserNo());
        addCompanyBankCardBo.setBankCode(companyBankCardApplyDto.getBankCode());
        addCompanyBankCardBo.setBankBranchName(companyBankCardApplyDto.getBankBranchName());
        return addCompanyBankCardBo;
    }

}
