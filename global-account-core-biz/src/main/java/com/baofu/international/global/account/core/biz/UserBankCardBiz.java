package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.AddCompanyBankCardBo;
import com.baofu.international.global.account.core.biz.models.AddPersonalBankCardBo;

/**
 * 用户银行卡维护业务处理接口
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public interface UserBankCardBiz {

    /**
     * 用户申请添加个人银行卡
     *
     * @param addPersonalBankCardBo 个人银行卡添加信息
     */
    void addPersonalBank(AddPersonalBankCardBo addPersonalBankCardBo);

    /**
     * 用户申请添加企业对公银行卡
     *
     * @param addCompanyBankCardBo 企业法人银行卡添加信息
     */
    void addCompanyLegalPBC(AddCompanyBankCardBo addCompanyBankCardBo);

    /**
     * 删除银行卡
     *
     * @param userNo   用户编号
     * @param recordNo 记录编号
     */
    void delBankCard(Long userNo, Long recordNo);
}
