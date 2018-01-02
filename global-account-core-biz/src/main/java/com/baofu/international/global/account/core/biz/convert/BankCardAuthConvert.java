package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.dal.model.AuthApplyDo;
import com.baofu.international.global.account.core.dal.model.AuthBankDo;

import java.util.Date;

/**
 * 银行卡认证信息转换
 * <p>
 * 1、新增个人认证记录信息转换
 * 2、新增认证申请记录信息转换
 * </p>
 *
 * @author : lian zd
 * @date : 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class BankCardAuthConvert {

    private BankCardAuthConvert() {

    }

    /**
     * 新增认证申请记录信息转换
     *
     * @param userInfoNo 申请编号
     * @param userNo     用户号
     * @param authStatus 认证状态
     * @param desc       描述
     * @param createBy   创建人
     * @param type       用户类型
     * @param authMethod 认证类型
     * @return 响应信息
     */
    public static AuthApplyDo addAuthApplyConvert(Long userInfoNo, Long userNo, int authStatus, String desc, String createBy, int type, int authMethod) {

        AuthApplyDo tAuthApplyDo = new AuthApplyDo();
        tAuthApplyDo.setAuthApplyNo(userInfoNo);
        tAuthApplyDo.setUserNo(userNo);
        tAuthApplyDo.setUserType(type);
        tAuthApplyDo.setApplyType(1);
        tAuthApplyDo.setAuthMethod(authMethod);
        tAuthApplyDo.setAuthStatus(authStatus);
        tAuthApplyDo.setFailReason(desc);
        tAuthApplyDo.setCreateBy(createBy);
        tAuthApplyDo.setUpdateBy(createBy);
        tAuthApplyDo.setCreateBy("system");
        tAuthApplyDo.setCreateAt(new Date());
        tAuthApplyDo.setUpdateBy("system");
        tAuthApplyDo.setUpdateAt(new Date());
        return tAuthApplyDo;
    }

    /**
     * 新增个人认证记录信息转换
     *
     * @param personAuthReqBo 个人认证信息
     * @return 响应信息
     */
    public static AuthBankDo addAuthBankConvert(PersonAuthReqBo personAuthReqBo, int authStatus, String bankCardType) {

        AuthBankDo authBankDo = new AuthBankDo();
        authBankDo.setAuthApplyNo(personAuthReqBo.getAuthApplyNo());
        authBankDo.setIdNo(personAuthReqBo.getIdCardNo());
        authBankDo.setName(personAuthReqBo.getIdCardName());
        authBankDo.setAuthStatus(authStatus);
        authBankDo.setCardNo(personAuthReqBo.getBankCardNo());
        authBankDo.setAuthReqNo(personAuthReqBo.getAuthReqNo());
        authBankDo.setBankCardType(Integer.parseInt(bankCardType));
        return authBankDo;
    }

}
