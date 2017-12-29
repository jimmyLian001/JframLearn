package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.SettleNotifyApplyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.biz.models.SettleQueryReqParamBo;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
public interface SettleNotifyMemberBiz {

    /**
     * 通知商户
     *
     * @param settleNotifyBo 通知参数信息
     */
    void httpNotify(SettleNotifyMemberBo settleNotifyBo, SettleNotifyApplyBo settleNotifyApplyBo);

    /**
     * 文件处理失败时失败文件上传至商户FTP服务器中
     *
     * @param memberId 商户编号
     * @param dfsId    DFS编号
     */
    void uploadMemberFtp(Long memberId, Long dfsId, String fileName) throws Exception;
}
