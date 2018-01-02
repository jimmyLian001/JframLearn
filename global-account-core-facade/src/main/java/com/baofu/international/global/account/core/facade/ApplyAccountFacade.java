package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.ApplyAccountRepDto;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * <p>
 * 申请境外银行卡账号.
 * </p>
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
public interface ApplyAccountFacade {

    /**
     * 根据用户Id查询出当前的用户信息
     *
     * @param userNo     用户编号
     * @param traceLogId 日志ID
     * @param userType   用户类型
     * @return 返回结果
     */
    Result<List<ApplyAccountRepDto>> getApplyAccountData(Long userNo, int userType, String traceLogId);

    /**
     * 用户创建申请
     *
     * @param reqDto     reqDto
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    Result<Boolean> addApplyAccount(ApplyAccountReqDto reqDto, String traceLogId);

    /**
     * 资质审核成功之后开通收款账户
     *
     * @param qualifiedNo 资质编号
     * @param traceLogId  日志ID
     * @return 返回结果
     */
    Result<Boolean> applyAccountAuditor(Long qualifiedNo, String traceLogId);

    /**
     * Skyee账户开通结果导入
     *
     * @param dfsId      文件编号，由业务后台上传至DFS之后产生的编号
     * @param fileName   文件名称
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    Result<Boolean> skyeeAccOpenResult(Long dfsId, String fileName, String traceLogId);


    /**
     * Skyee账户开通开通之后更新成账户开通处理中
     *
     * @param applyNo    申请编号
     * @param status     开通状态 1为申请中，2为失败
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    Result<Boolean> skyeeAccOpenHandling(Long applyNo, int status, String traceLogId);

    /**
     * 查询用户已经申请开通的币种
     *
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    Result<List<String>> queryAccApplyCcy(Long userNo, String traceLogId);
}
