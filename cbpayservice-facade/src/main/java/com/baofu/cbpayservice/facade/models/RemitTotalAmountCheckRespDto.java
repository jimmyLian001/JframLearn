package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境汇款汇款明细文件汇款金额校验返回对象
 * <p/>
 * User: lian zd Date:2017/10/30 ProjectName: cbpayservice Version:1.0
 */
@Getter
@Setter
@ToString
public class RemitTotalAmountCheckRespDto implements Serializable {

    /**
     * 结算总金额
     */
    private BigDecimal totalAmount;

    /**
     * 批次文件总金额
     */
    private BigDecimal fileTotalAmount;

    /**
     * 文件批次号
     */
    private List<Long> fileBatchNoList;

    /**
     * 文件总金额与汇款金额是否一致，true:一致，false:不一致
     */
    private boolean checkResult;
}
