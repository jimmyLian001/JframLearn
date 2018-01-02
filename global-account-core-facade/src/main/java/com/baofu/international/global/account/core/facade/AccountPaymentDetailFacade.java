package com.baofu.international.global.account.core.facade;

import com.system.commons.result.Result;

/**
 * <p>
 * 1、Skyee账户收支明细导入
 * </p>
 * ProjectName : global-account-core-parent
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/26
 */
public interface AccountPaymentDetailFacade {

    /**
     * Skyee账户收支明细导入
     *
     * @param dfsId      文件编号，由业务后台上传至DFS之后产生的编号
     * @param fileName   文件名称
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    Result<Boolean> skyeeAccPaymentDetailImport(Long dfsId, String fileName, String traceLogId);
}
