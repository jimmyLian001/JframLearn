package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;

/**
 * 购付汇反洗钱处理
 * <p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayAmlNotifyBiz {

    /**
     * 购付汇反洗钱处理
     *
     * @param cgwRemitBatchResultDto 反洗钱结果
     * @param updateBy               更新人
     */
    void amlApplySecondNotify(CgwRemitResultDto cgwRemitBatchResultDto, String updateBy);

}
