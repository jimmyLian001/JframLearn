package com.baofu.international.global.account.core.facade.user;

import com.baofu.international.global.account.core.facade.model.res.UserApplyAccQueryRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserApplyAccQueryReqDto;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;

/**
 * 用户账户申请开通查询相关操作
 * <p>
 * 1、查詢用戶申請開通賬戶信息
 * 2、下载用戶申請開通賬戶信息
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/17
 */
public interface UserApplyAccQueryFacade {

    /**
     * 查詢用戶申請開通賬戶信息
     *
     * @param userApplyAccQueryReqDto 查詢條件
     * @param traceLogId              日志ID
     * @return 返回結果
     */
    Result<PageRespDTO<UserApplyAccQueryRespDto>> queryUserApplyAcc(UserApplyAccQueryReqDto userApplyAccQueryReqDto,
                                                                    String traceLogId);


    /**
     * 异步下载用户申请开通账户信息申请接口
     *
     * @param userApplyAccQueryReqDto 用户申请信息
     * @param traceLogId              日志ID
     * @return 结果
     */
    Result<Long> downloadUserDataApply(UserApplyAccQueryReqDto userApplyAccQueryReqDto, String traceLogId);


    /**
     * @param recordId   申请记录编号ID
     * @param traceLogId 日志ID
     * @return DFS id
     */
    Result<Long> queryDfsIdByRecordId(Long recordId, String traceLogId);

}
