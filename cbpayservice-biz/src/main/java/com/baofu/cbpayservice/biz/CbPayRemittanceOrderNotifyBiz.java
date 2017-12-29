package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;

/**
 * 跨境人民币异步通知接口
 * <p>
 * 1、接收渠道异步通知
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayRemittanceOrderNotifyBiz {
    /**
     * 接收渠道异步通知
     *
     * @param cgwRemitBatchResultDto 请求参数
     * @param updateBy               更新人
     */
    void receiveNotify(CgwRemitResultDto cgwRemitBatchResultDto, String updateBy);

    /**
     * 接收渠道异步通知
     *
     * @param cgwBaseResultDO 请求参数
     * @param updateBy        更新人
     */
    void receiveOrderNotify(CgwBaseRespDto cgwBaseResultDO, String updateBy);
}
