package com.baofu.international.global.account.core.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwReceiptAndDisbursementRespDto;
import com.baofu.international.global.account.core.biz.models.AccountDetailBo;
import com.baofu.international.global.account.core.biz.models.SkyeePaymentDetailBo;
import com.baofu.international.global.account.core.dal.model.PaymentDetailDo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.system.commons.utils.DateUtil;

import java.math.BigDecimal;


/**
 * 描述：收款账户收支明细对象转换
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public class PaymentDetailBizConvert {
    private PaymentDetailBizConvert() {
    }

    /**
     * respDto 转 Do
     *
     * @param accountDetailBo respDto
     * @return Do
     */
    public static PaymentDetailDo toTPayeePaymentDetailDo(AccountDetailBo accountDetailBo) {

        PaymentDetailDo paymentDetailDo = new PaymentDetailDo();
        paymentDetailDo.setUserNo(accountDetailBo.getUserNo());
        paymentDetailDo.setWalletId(accountDetailBo.getWalletId());
        paymentDetailDo.setBankRespId(accountDetailBo.getBankRespNo());
        paymentDetailDo.setOrderAmt(accountDetailBo.getOrderAmt());
        paymentDetailDo.setOrderCcy(accountDetailBo.getOrderCcy());
        //1-海外收款
        paymentDetailDo.setBusinessType(1);
        //余额方向:1-收入；2-支出
        paymentDetailDo.setBalanceDirection(accountDetailBo.getOrderType());
        paymentDetailDo.setRemarks(accountDetailBo.getRemarks());

        return paymentDetailDo;
    }

    /**
     * 账户收支明细转换成网关内部对象
     *
     * @param channelRespDto 渠道方返回信息
     * @return 收款账户内部对象
     */
    public static AccountDetailBo paramConvert(CgwReceiptAndDisbursementRespDto channelRespDto) {
        AccountDetailBo accountDetailBo = new AccountDetailBo();
        accountDetailBo.setUserNo(Long.parseLong(channelRespDto.getMemberId()));
        accountDetailBo.setOrderCcy(channelRespDto.getCurrency());
        accountDetailBo.setOrderAmt(new BigDecimal(channelRespDto.getAmount()));
        accountDetailBo.setOrderType(channelRespDto.getPaymentType());
        accountDetailBo.setBankRespNo(channelRespDto.getId());
        accountDetailBo.setRemarks(channelRespDto.getMessage());
        accountDetailBo.setWalletId(channelRespDto.getUserId());
        return accountDetailBo;
    }


    /**
     * 账户收支明细转换成网关内部对象
     *
     * @param skyeePaymentDetailBo 渠道方返回信息
     * @return 收款账户内部对象
     */
    public static AccountDetailBo paramConvert(SkyeePaymentDetailBo skyeePaymentDetailBo, UserPayeeAccountDo userPayeeAccountDo) {

        AccountDetailBo accountDetailBo = new AccountDetailBo();
        accountDetailBo.setUserNo(userPayeeAccountDo.getUserNo());
        accountDetailBo.setOrderCcy(skyeePaymentDetailBo.getCcy());
        accountDetailBo.setOrderAmt(skyeePaymentDetailBo.getOrderAmt());
        accountDetailBo.setOrderType(1);
        accountDetailBo.setBankRespNo(skyeePaymentDetailBo.getOrderId() +
                DateUtil.format(skyeePaymentDetailBo.getOrderDate(), DateUtil.datePattern));
        accountDetailBo.setAccountNo(userPayeeAccountDo.getAccountNo());

        return accountDetailBo;
    }

}
