package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by 莫小阳 on 2017/9/28.
 */
@Setter
@Getter
@ToString
public class FiCbPayMemberApiRqstBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户终端号
     */
    private Long terminalId;

    /**
     * 商户申请流水号
     */
    private String memberReqId;

    /**
     * 业务类型: 11-购付汇订单明细上传，12-购付汇跨境汇款申请
     */
    private String businessType;

    /**
     * 请求对应业务编号
     */
    private Long businessNo;

    /**
     * 请求对应通知地址
     */
    private String notifyUrl;

    /**
     * 请求处理状态:0-待回执、1-已回执、2-已通知
     */
    private Byte processStatus;

    /**
     * 请求处理时间
     */
    private Date processAt;

}
