package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPaySettleBankBo;
import com.baofu.cbpayservice.biz.models.ModifySettleBankBo;

/**
 * 多币种账户信息biz接口
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleBankBiz {

    /**
     * 添加多币种账户信息
     *
     * @param cbPaySettleBankBo 多币种账户biz参数
     * @return 记录编号
     */
    Long addSettleBank(CbPaySettleBankBo cbPaySettleBankBo);

    /**
     * 修改商户币种账户信息
     *
     * @param modifySettleBankBo 修改商户币种账户信息
     */
    void modifySettleBank(ModifySettleBankBo modifySettleBankBo);

    /**
     * 判断用户是否开通该币种
     *
     * @param memberId       商户号
     * @param targetCurrency 币种
     */
    void checkMemberCcy(Long memberId, String targetCurrency);

    /**
     * @param memberId  商户号
     * @param bankAccNo 银行账户编号
     * @param remitCcy  币种
     * @return 商户备案主体编号
     */
    String queryMerchantEntityNo(Long memberId, String bankAccNo, String remitCcy);
}
