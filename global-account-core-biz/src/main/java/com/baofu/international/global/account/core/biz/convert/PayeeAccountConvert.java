package com.baofu.international.global.account.core.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwLookUserRespDto;
import com.baofu.international.global.account.core.biz.models.ChannelNotifyApplyAccountBo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * 渠道开户返回结果对象转换
 * <p>
 * 1、渠道开户返回结果对象转换
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayeeAccountConvert {


    /**
     * 渠道返回参数转换
     *
     * @param cgwLookUserResp cgwLookUserResp
     * @return 返回渠道通知业务对象
     */
    public static ChannelNotifyApplyAccountBo paramConvert(CgwLookUserRespDto cgwLookUserResp) {

        ChannelNotifyApplyAccountBo channelNotifyApplyAccountBo = new ChannelNotifyApplyAccountBo();
        channelNotifyApplyAccountBo.setBankAccNo(cgwLookUserResp.getBankAccountNumber());
        channelNotifyApplyAccountBo.setRoutingNumber(cgwLookUserResp.getRoutingNumber());
        channelNotifyApplyAccountBo.setSuccFlag(cgwLookUserResp.getSuccFlag());
        channelNotifyApplyAccountBo.setApplyNo(Long.valueOf(cgwLookUserResp.getBfBatchId()));
        channelNotifyApplyAccountBo.setCode(cgwLookUserResp.getCode());
        channelNotifyApplyAccountBo.setMessage(cgwLookUserResp.getMessage());
        channelNotifyApplyAccountBo.setWalletId(cgwLookUserResp.getUserId());
        channelNotifyApplyAccountBo.setBankAccName(cgwLookUserResp.getBankAccName());
        return channelNotifyApplyAccountBo;
    }
}
