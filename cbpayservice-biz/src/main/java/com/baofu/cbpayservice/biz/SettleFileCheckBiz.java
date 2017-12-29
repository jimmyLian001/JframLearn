package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
import com.baofu.cbpayservice.biz.models.SettleOrderListBo;

import java.util.List;

/**
 * 结汇上传文件校验类
 * <p>
 * User: 不良人 Date:2017/4/16 ProjectName: cbpayservice Version: 1.0
 */
public interface SettleFileCheckBiz {

    /**
     * 结汇上传文件校验
     *
     * @param list                  文件内容
     * @param settleFileUploadReqBo 请求参数
     * @param testAndVerifyOnlyFlag 是否为非入库校验明细 true为是
     * @return
     */
    SettleOrderListBo baseCheck(List<Object[]> list, SettleFileUploadReqBo settleFileUploadReqBo, Boolean testAndVerifyOnlyFlag);
}
