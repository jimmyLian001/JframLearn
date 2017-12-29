package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件处理结果通知商户
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class SettleNotifyFileProcessBo extends SettleNotifyBo {

    /**
     * 错误文件名称
     */
    private String errorFileName;
}
