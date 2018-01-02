package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.dal.model.AccQualifiedDo;

/**
 * 企业用户信息转换
 * <p>
 * 1、更新企业用户信息转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class AccQualifiedConvert {

    private AccQualifiedConvert() {

    }

    /**
     * 新增企业认证信息转换
     *
     * @param userInfoNo 资质关系主键
     * @param userNo     用户号
     * @param loginNo    登录名
     * @param recordId   卡表主键
     * @return 转换信息
     */
    public static AccQualifiedDo addAccQualifiedConvert(Long userInfoNo, Long userNo, String loginNo, Long recordId) {
        AccQualifiedDo accQualifiedDo = new AccQualifiedDo();
        accQualifiedDo.setQualifiedNo(recordId);
        accQualifiedDo.setUserInfoNo(userInfoNo);
        accQualifiedDo.setUserNo(userNo);
        accQualifiedDo.setCreateBy(loginNo);
        accQualifiedDo.setUpdateBy(loginNo);

        return accQualifiedDo;
    }
}
