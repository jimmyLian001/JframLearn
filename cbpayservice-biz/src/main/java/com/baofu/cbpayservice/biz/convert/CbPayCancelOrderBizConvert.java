package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPayCancelOrderBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;

/**
 * 取消订单参数转换
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service   Version: 1.0
 */
public final class CbPayCancelOrderBizConvert {

    private CbPayCancelOrderBizConvert() {

    }

    /**
     * 更新汇款批次的状态
     *
     * @param cbPayCancelOrderBo 汇款批次的请求参数
     * @return 转换结果
     */
    public static FiCbPayFileUploadDo paramConvert(CbPayCancelOrderBo cbPayCancelOrderBo) {

        FiCbPayFileUploadDo fiCbPayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbPayFileUploadDo.setFileBatchNo(cbPayCancelOrderBo.getFileBatchNo());
        fiCbPayFileUploadDo.setStatus(Constants.BATCH_UPLOAD_CANCEL_STATUS);
        fiCbPayFileUploadDo.setUpdateBy(cbPayCancelOrderBo.getUpdateBy());


        return fiCbPayFileUploadDo;
    }

    /**
     * 更新订单状态
     *
     * @param cbPayCancelOrderBo 汇款批次的请求参数
     * @return 转换结果
     */
    public static FiCbPayOrderDo orderParamConvert(CbPayCancelOrderBo cbPayCancelOrderBo) {

        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setBatchFileId(cbPayCancelOrderBo.getFileBatchNo());
        fiCbPayOrderDo.setMemberId(cbPayCancelOrderBo.getMemberId());
        fiCbPayOrderDo.setUpdateBy(cbPayCancelOrderBo.getUpdateBy());

        return fiCbPayOrderDo;
    }

}
