package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yimo on 2017/11/22.
 */
@Setter
@Getter
@ToString
public class WithdrawRateReqDto implements Serializable {

    private Long recordId;
    /**
     * 用户号
     */
    private String userNo;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 银行账户币种
     */
    private String bankAccCcy;

    /**
     * 当前页
     */
    @NotNull(message = "当前页不能为空")
    private int pageNo;

    /**
     * 每页记录数
     */
    @NotNull(message = "每页记录数不能为空")
    private int pageSize;

    /**
     * 费率设置状态
     */
    private long rateSetState;

    /**
     * 费率
     */
    private String rate;

    /**
     * 日志ID
     */
    private String tranceLogID;

    private String createBy;

    private String updateBy;
}
