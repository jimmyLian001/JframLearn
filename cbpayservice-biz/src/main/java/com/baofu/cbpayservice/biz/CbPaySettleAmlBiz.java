package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/8/10 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleAmlBiz {

    /**
     * 结汇反洗钱处理
     *
     * @param cgwSettleResultRespDto 响应对象
     */
    void amlApplySecondNotify(CgwSettleResultDto cgwSettleResultRespDto);
}
