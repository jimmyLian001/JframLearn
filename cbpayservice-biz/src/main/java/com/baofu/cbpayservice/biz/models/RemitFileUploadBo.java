package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 汇款上传文件参数
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class RemitFileUploadBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Long terminalId;

    /**
     * 请求流水号
     */
    private String memberReqId;

    /**
     * 订单明细文件名称
     */
    private String orderFileName;

    /**
     * 结果通知地址
     */
    private String notifyUrl;

    /**
     * 汇款文件DFSID
     */
    private Long dfsFileId;

}
