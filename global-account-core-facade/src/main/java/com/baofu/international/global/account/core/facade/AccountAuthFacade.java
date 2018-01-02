package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.OrgAuthReqDto;
import com.baofu.international.global.account.core.facade.model.PersonalAuthReqDto;
import com.system.commons.result.Result;

/**
 * 认证操作接口
 * <p>
 * 1、身份证二要素认证
 * 2、银行卡三要素认证
 * 3、企业信息实名认证
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface AccountAuthFacade {

    /**
     * 个人信息实名认证
     *
     * @param personAuthReqDto 个人信息
     * @param traceLogId       日志ID
     * @return 申请编号
     */
    Result<Long> personalAuthApply(PersonalAuthReqDto personAuthReqDto, String traceLogId);

    /**
     * 企业信息实名认证
     *
     * @param orgAuthReqDto 企业信息
     * @param traceLogId    日志ID
     * @return 响应码
     */
    Result<Long> orgAuthApply(OrgAuthReqDto orgAuthReqDto, String traceLogId);
}
