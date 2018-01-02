package com.baofu.international.global.account.core.biz.convert;

import com.baofoo.cbcgw.facade.dict.WyreTransferTypeEnum;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwTransferReqDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import org.slf4j.MDC;

/**
 * 描述：提现biz层对象转换
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
public final class UserWithdrawBizConvert {
    private UserWithdrawBizConvert() {
    }

    /**
     * 商户提现申请渠道参数转换
     *
     * @param userWithdrawApplyDo 申请参数
     * @param userPayeeAccountDo  账户信息
     * @param channelId           渠道ID
     * @return 渠道请求参数
     */
    public static CgwTransferReqDto toCgwTransferReqDto(UserWithdrawApplyDo userWithdrawApplyDo,
                                                        UserPayeeAccountDo userPayeeAccountDo, Long channelId) {
        CgwTransferReqDto cgwTransferReqDto = new CgwTransferReqDto();
        cgwTransferReqDto.setBfBatchId(String.valueOf(userWithdrawApplyDo.getWithdrawId()));
        cgwTransferReqDto.setSource(userPayeeAccountDo.getWalletId());
        cgwTransferReqDto.setSourceCurrency(userPayeeAccountDo.getCcy());
        cgwTransferReqDto.setSourceAmount(userWithdrawApplyDo.getDeductAmt());
        cgwTransferReqDto.setBusinessType(WyreTransferTypeEnum.BUSINESS_CREATE_TRANSFER_FIRST.getCode());
        cgwTransferReqDto.setTraceLogId(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        cgwTransferReqDto.setChannelId(channelId.intValue());
        return cgwTransferReqDto;
    }
}
