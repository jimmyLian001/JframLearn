package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.ApplyAccountBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountInfoBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountReqBo;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
public interface ApplyAccountBiz {
    /**
     * 查询出当前用户的银行卡信息
     *
     * @param userNo   userNo
     * @param userType userType
     * @return result
     */
    List<ApplyAccountInfoBo> getApplyAccountPageInfo(Long userNo, Integer userType);

    /**
     * 申请跨境账号
     *
     * @param applyAccountBo applyAccountBo
     */
    void applyAccount(ApplyAccountBo applyAccountBo);

    /**
     * 根据id查询用户的相关信息
     *
     * @param applyAccountReqBo applyAccountReqBo
     * @return result
     */
    ApplyAccountBo getApplyInfoByUserNo(ApplyAccountReqBo applyAccountReqBo);

    /**
     * 根据id查询用户的相关信息
     *
     * @param qualifiedNo 资质编号
     * @return result
     */
    List<ApplyAccountBo> getApplyInfo(Long qualifiedNo);

    /**
     * 发送渠道开户
     *
     * @param applyAccountBo 请求参数信息
     */
    void getCreateUserResult(ApplyAccountBo applyAccountBo);
}
