package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 描述：用户提现文件上传参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@Setter
@ToString
public class UserSettleAppReqBo implements Serializable {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 提现汇总批次号
     */
    private Long withdrawBatchId;

}
