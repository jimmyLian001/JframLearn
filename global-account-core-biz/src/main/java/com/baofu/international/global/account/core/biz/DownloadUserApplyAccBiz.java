package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.UserApplyAccQueryReqBo;

/**
 * 下载Skyee开户数据处理
 * <p>
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/26 0026
 */
public interface DownloadUserApplyAccBiz {

    /**
     * 异步下载Skyee开户信息数据
     *
     * @param userApplyAccQueryReqBo 下载数据请求参数
     */
    void downloadUserApplyAcc(UserApplyAccQueryReqBo userApplyAccQueryReqBo);

}
