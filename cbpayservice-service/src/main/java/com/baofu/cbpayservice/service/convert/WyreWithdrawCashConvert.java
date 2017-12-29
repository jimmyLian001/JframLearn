package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.MemberWithdrawCashBo;
import com.baofu.cbpayservice.facade.models.MemberWithdrawCashDto;

/**
 * Wyre商户前台提现对象转换
 * <p>
 * User: 不良人 Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 **/
public final class WyreWithdrawCashConvert {

    private WyreWithdrawCashConvert() {
    }

    /**
     * Wyre商户前台提现参数转换
     *
     * @param memberWithdrawCashDto 提现请求参数
     * @return 提现参数
     */
    public static MemberWithdrawCashBo toMemberWithdrawCashBo(MemberWithdrawCashDto memberWithdrawCashDto) {

        MemberWithdrawCashBo memberWithdrawCashBo = new MemberWithdrawCashBo();
        memberWithdrawCashBo.setMemberId(memberWithdrawCashDto.getMemberId());
        memberWithdrawCashBo.setTransferAmt(memberWithdrawCashDto.getTransferAmt());
        memberWithdrawCashBo.setOrderDetailFileId(memberWithdrawCashDto.getOrderDetailFileId());
        memberWithdrawCashBo.setFileName(memberWithdrawCashDto.getFileName());
        memberWithdrawCashBo.setCreateBy(memberWithdrawCashDto.getCreateBy());
        return memberWithdrawCashBo;
    }
}
