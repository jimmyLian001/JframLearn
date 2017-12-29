package com.baofu.cbpayservice.dal.models;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class FiCbpayRemitConfigDo extends BaseDo {
    /**
     * 记录编号，代表此记录的唯一编号
     */
    private Long recordId;

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 汇款周期：1-日结，2-周结，3-月结，4-一周两结
     */
    private Integer remitCycle;

    /**
     * 汇款最小金额
     */
    private BigDecimal remitMinAmt;

    /**
     * 启用状态：1-启用，2-关闭
     */
    private Integer state;

    /**
     * 汇款账户编号
     */
    private Long remitAccId;

    /**
     * 上一次汇款时间
     */
    private Date lastDate;
}