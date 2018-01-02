package com.baofu.international.global.account.core.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwTransferRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwTransferResultDto;

/**
 * 功能：用户提现处理渠道通知服务
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public interface UserWithdrawCgwDealBiz {

    /**
     * 功能：用户转账渠道通知处理
     *
     * @param transferRespDto 渠道通知对象
     */
    void userWithdrawOneProcess(CgwTransferRespDto transferRespDto);

    /**
     * 功能：用户转账渠道第二次异步通知处理
     *
     * @param cgwTransferResultDto 渠道通知对象
     */
    void userWithdrawTwoProcess(CgwTransferResultDto cgwTransferResultDto);

    /**
     * 功能：资金归集第一次异步通知
     *
     * @param transferRespDto 参数
     */
    void userWithdrawMergeOneProcess(CgwTransferRespDto transferRespDto);

    /**
     * 功能：资金归集第二次异步通知
     *
     * @param cgwTransferResultDto 参数
     */
    void userWithdrawMergeTwoProcess(CgwTransferResultDto cgwTransferResultDto);

    /**
     * 功能：定时获取中行汇率
     */
    void loadCgwBocRate();
}
