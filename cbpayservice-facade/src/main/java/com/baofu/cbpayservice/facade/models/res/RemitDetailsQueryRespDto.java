package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p/>
 * <p>
 * 1、订单明细上传查询返回参数
 * </p>
 * User: 白玉京 Date:2017/10/1 ProjectName:cbpayservice
 */

@Getter
@Setter
@ToString
public class RemitDetailsQueryRespDto implements Serializable {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 终端号
     */
    private Long terminalId;

    /**
     * 请求流水号
     */
    private String memberReqId;

    /**
     * 文件批次编号
     */
    private String fileBatchNo;

    /**
     * 总笔数
     */
    private Integer recordCount;

    /**
     * 总金额
     */
    private String totalAmount;

    /**
     * 成功笔数
     */
    private Integer successCount;

    /**
     * 失败笔数
     */
    private Integer failCount;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 错误文件名称
     */
    private String errorFileName;
}
