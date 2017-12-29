package com.baofu.cbpayservice.biz.convert;

import com.baofoo.member.dal.model.MaMerchantLink;
import com.baofu.cbpayservice.biz.models.MemberEmailBo;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
public class MemberEmailBizConvert {

    /**
     * 商户邮箱信息转换
     *
     * @param maMerchantLink 商户业务邮箱接口方参数信息
     * @return 返回对象
     */
    public static MemberEmailBo paramConvert(MaMerchantLink maMerchantLink) {

        MemberEmailBo memberEmailBo = new MemberEmailBo();
        memberEmailBo.setMemberId(maMerchantLink.getMemberId());
        memberEmailBo.setBusinessName(maMerchantLink.getBusinessName());
        memberEmailBo.setBusinessPhone(maMerchantLink.getBusinessPhone());
        memberEmailBo.setBusinessQq(maMerchantLink.getBusinessQq());
        memberEmailBo.setBusinessEmail(maMerchantLink.getBusinessEmail());
        memberEmailBo.setTechName(maMerchantLink.getTechName());
        memberEmailBo.setTechPhone(maMerchantLink.getTechPhone());
        memberEmailBo.setTechQq(maMerchantLink.getTechQq());
        memberEmailBo.setTechEmail(maMerchantLink.getTechEmail());
        memberEmailBo.setFinanceName(maMerchantLink.getFinanceName());
        memberEmailBo.setFinancePhone(maMerchantLink.getFinancePhone());
        memberEmailBo.setFinanceQq(maMerchantLink.getFinanceQq());
        memberEmailBo.setFinanceEmail(maMerchantLink.getFinanceEmail());

        return memberEmailBo;
    }
}
