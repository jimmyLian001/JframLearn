package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPayOrderItemBo;
import com.baofu.cbpayservice.biz.models.CbpayGoodsItemBo;
import com.baofu.cbpayservice.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数转换
 * User: 不良人 Date:2016/12/14 ProjectName: cbpayservice Version: 1.0
 */
public class CbpaySendOrderConvert {


    /**
     * 商品信息转换
     *
     * @param cbpayGoodsItemBos 商品集合
     * @return 商品集合
     */
    public static List<CbPayOrderItemBo> paramConvert(List<CbpayGoodsItemBo> cbpayGoodsItemBos) {
        List<CbPayOrderItemBo> cbPayOrderItemBos = new ArrayList<>();
        for (CbpayGoodsItemBo cbpayGoodsItemBo : cbpayGoodsItemBos) {
            CbPayOrderItemBo cbPayOrderItemBo = new CbPayOrderItemBo();
            cbPayOrderItemBo.setCommodityAmount(cbpayGoodsItemBo.getGoodsNum());
            cbPayOrderItemBo.setCommodityName(StringUtils.stringFilter(cbpayGoodsItemBo.getGoodsName()));
            cbPayOrderItemBo.setCommodityPrice(cbpayGoodsItemBo.getGoodsPrice());
            cbPayOrderItemBos.add(cbPayOrderItemBo);
        }
        return cbPayOrderItemBos;
    }
}
