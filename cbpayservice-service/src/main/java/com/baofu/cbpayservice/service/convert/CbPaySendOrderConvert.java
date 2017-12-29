package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPaySendOrderReqBo;
import com.baofu.cbpayservice.biz.models.CbpayGoodsItemBo;
import com.baofu.cbpayservice.facade.models.CbPaySendOrderReqDto;
import com.baofu.cbpayservice.facade.models.CbpayGoodsItemDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 上送接口参数转换
 * <p>
 * User: 不良人 Date:2016/12/14 ProjectName: cbpayservice Version: 1.0
 */
public class CbPaySendOrderConvert {

    /**
     * 上送接口biz层参数转换
     *
     * @param cbpaySendOrderParam 上送接口参数
     * @return 上送接口biz层接口入参
     */
    public static CbPaySendOrderReqBo paramConvert(CbPaySendOrderReqDto cbpaySendOrderParam) {

        CbPaySendOrderReqBo cbpaySendOrderReqBo = new CbPaySendOrderReqBo();
        cbpaySendOrderReqBo.setMemberId(cbpaySendOrderParam.getMemberId());
        cbpaySendOrderReqBo.setTerminalId(cbpaySendOrderParam.getTerminalId());
        cbpaySendOrderReqBo.setMemberTransId(cbpaySendOrderParam.getMemberTransId());
        cbpaySendOrderReqBo.setBankCardNo(cbpaySendOrderParam.getBankCardNo());
        cbpaySendOrderReqBo.setIdCardNo(cbpaySendOrderParam.getIdCardNo());
        cbpaySendOrderReqBo.setIdHolder(cbpaySendOrderParam.getIdHolder());
        cbpaySendOrderReqBo.setMobile(cbpaySendOrderParam.getMobile());
        cbpaySendOrderReqBo.setOrderPayType(cbpaySendOrderParam.getOrderPayType());
        cbpaySendOrderReqBo.setClientIp(cbpaySendOrderParam.getClientIp());
        List<CbpayGoodsItemDto> cbpayGoodsItemDtos = cbpaySendOrderParam.getCbpayGoodsItemDtos();
        if (cbpayGoodsItemDtos == null || cbpayGoodsItemDtos.size() < 1) {
            return cbpaySendOrderReqBo;
        }
        List<CbpayGoodsItemBo> cbpayGoodsItemBos = new ArrayList<CbpayGoodsItemBo>();
        for (CbpayGoodsItemDto cbpayGoodsItemDto : cbpayGoodsItemDtos) {
            CbpayGoodsItemBo cbpayGoodsItemBo = new CbpayGoodsItemBo();
            cbpayGoodsItemBo.setGoodsPrice(cbpayGoodsItemDto.getGoodsPrice());
            cbpayGoodsItemBo.setGoodsName(cbpayGoodsItemDto.getGoodsName());
            cbpayGoodsItemBo.setGoodsNum(cbpayGoodsItemDto.getGoodsNum());
            cbpayGoodsItemBos.add(cbpayGoodsItemBo);
        }
        cbpaySendOrderReqBo.setCbpayGoodsItemBos(cbpayGoodsItemBos);
        return cbpaySendOrderReqBo;
    }
}
