package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.AddCompanyBankCardBo;
import com.baofu.international.global.account.core.biz.models.AddPersonalBankCardBo;
import com.baofu.international.global.account.core.biz.models.PersonInfoReqBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.AccTypeEnum;
import com.baofu.international.global.account.core.common.enums.CardStateEnum;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;

import java.util.Date;

/**
 * 收款账户用户银行卡维护数据操作对象转换
 * <p>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public final class UserBankCardServiceConvert {

    private UserBankCardServiceConvert() {

    }

    /**
     * 用户申请添加个人银行卡信息转换
     *
     * @param addPersonalBankCardBo 用户申请添加个人银行卡信息
     * @return UserBankCardInfoDo
     */
    public static UserBankCardInfoDo toTUserBankCardInfDo(AddPersonalBankCardBo addPersonalBankCardBo, Long recordId, int state) {
        Date date = new Date();
        UserBankCardInfoDo userBankCardInfoDo = new UserBankCardInfoDo();
        userBankCardInfoDo.setUserNo(addPersonalBankCardBo.getUserNo());
        userBankCardInfoDo.setAccType(addPersonalBankCardBo.getAccType());
        userBankCardInfoDo.setCardHolder(addPersonalBankCardBo.getCardHolder());
        userBankCardInfoDo.setCardNo(addPersonalBankCardBo.getCardNo());
        userBankCardInfoDo.setRecordNo(recordId);
        userBankCardInfoDo.setState(state);
        userBankCardInfoDo.setCreateAt(date);
        userBankCardInfoDo.setUpdateAt(date);
        return userBankCardInfoDo;
    }

    /**
     * 用户申请添加企业对公银行卡信息转换
     *
     * @param addCompanyBankCardBo 用户申请添加企业对公银行卡信息
     * @return UserBankCardInfoDo
     */
    public static UserBankCardInfoDo toTUserBankCardInfDo(AddCompanyBankCardBo addCompanyBankCardBo, Long recordId, int state, UserOrgDo tOrgInfoDo) {
        Date date = new Date();
        UserBankCardInfoDo userBankCardInfoDo = new UserBankCardInfoDo();
        userBankCardInfoDo.setUserNo(addCompanyBankCardBo.getUserNo());
        userBankCardInfoDo.setAccType(addCompanyBankCardBo.getAccType());
        userBankCardInfoDo.setCardHolder(tOrgInfoDo.getName());
        userBankCardInfoDo.setCardNo(addCompanyBankCardBo.getCardNo());
        userBankCardInfoDo.setBankCode(addCompanyBankCardBo.getBankCode());
        userBankCardInfoDo.setCardType(NumberDict.ONE);
        userBankCardInfoDo.setBankBranchName(addCompanyBankCardBo.getBankBranchName());
        userBankCardInfoDo.setState(state);
        userBankCardInfoDo.setRecordNo(recordId);
        userBankCardInfoDo.setCreateAt(date);
        userBankCardInfoDo.setUpdateAt(date);

        return userBankCardInfoDo;
    }

    /**
     * 用户申请添加个人银行卡信息转换
     *
     * @param personInfoReqBo 用户申请添加个人银行卡信息
     * @param recordId        主键
     * @return UserBankCardInfoDo
     */
    public static UserBankCardInfoDo toTUserBankCardInfDo(PersonInfoReqBo personInfoReqBo, Long recordId) {
        UserBankCardInfoDo userBankCardInfoDo = new UserBankCardInfoDo();
        userBankCardInfoDo.setUserNo(personInfoReqBo.getUserNo());
        userBankCardInfoDo.setAccType(AccTypeEnum.ACC_TYPE_2.getCode());
        userBankCardInfoDo.setCardHolder(personInfoReqBo.getIdName());
        userBankCardInfoDo.setCardNo(personInfoReqBo.getBankCardNo());
        userBankCardInfoDo.setRecordNo(recordId);
        userBankCardInfoDo.setState(CardStateEnum.ACTIVATED.getCode());
        userBankCardInfoDo.setCreateAt(new Date());
        userBankCardInfoDo.setUpdateAt(new Date());
        return userBankCardInfoDo;
    }

}
