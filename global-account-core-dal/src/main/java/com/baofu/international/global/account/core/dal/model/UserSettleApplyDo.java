package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserSettleApplyDo extends BaseDo {
    /**
     * 用户号
     */
    private Long memberId;

    /**
     * 用户申请流水号
     */
    private String memberReqId;

    /**
     * 请求对应业务编号
     */
    private Long businessNo;

    /**
     * 业务类型: 11-用户提现
     */
    private String businessType;

    /**
     * 文件上传类型: FTP, HTTP
     */
    private String fileType;

    /**
     * 汇入汇款申请文件名
     */
    private String settleName;

    /**
     * 请求回调通知地址
     */
    private String notifyUrl;

    /**
     * 请求处理状态: 0-待回执、1-已回执
     */
    private Integer applyStatus;

    /**
     * 结汇状态：5-结汇失败 6-结汇成
     */
    private Integer settleStatus;
}