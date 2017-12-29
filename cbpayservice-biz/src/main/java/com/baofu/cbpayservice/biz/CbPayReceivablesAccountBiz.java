package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayAddReceivableAccountBo;
import com.baofu.cbpayservice.biz.models.PayeeAccountInfoBo;

/**
 * 收款账户业务逻辑实现接口
 * <p>
 * User: lian zd Date:2017/7/31 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayReceivablesAccountBiz {

    /**
     * 收款账户开户业务处理
     * @param cbPayAddReceivableAccountBo 商户新增开户信息传入对象
     */
    void addReceivablesAccount(CbPayAddReceivableAccountBo cbPayAddReceivableAccountBo);

    /**
     * 查询收款账户信息
     *
     * @param memberId 商户号
     * @return 商户信息
     */
    PayeeAccountInfoBo queryPayeeAccountInfo(Long memberId);
}
