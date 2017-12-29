package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.RemitDetailsQueryResultBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryResultBo;

/**
 * API汇款信息查询服务
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public interface RemittanceApiQueryBiz {

    /**
     * 跨境汇款结果查询
     *
     * @param remitOrderApiQueryBo 请求参数
     * @return 跨境汇款结果查询返回参数
     */
    RemitOrderApiQueryResultBo remittanceOrderQuery(RemitOrderApiQueryBo remitOrderApiQueryBo);

    /**
     * API订单明细上传查询
     *
     * @param remitOrderApiQueryBo 请求参数
     * @return API订单明细上传查询 返回参数
     * @throws Exception 异常
     */
    RemitDetailsQueryResultBo orderDetailsUploadQuery(RemitOrderApiQueryBo remitOrderApiQueryBo) throws Exception;
}
