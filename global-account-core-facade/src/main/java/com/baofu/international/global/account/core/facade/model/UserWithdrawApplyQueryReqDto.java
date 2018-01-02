package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description:用户提现申请查询 ReqDTO
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
@Getter
@Setter
@ToString
public class UserWithdrawApplyQueryReqDto implements Serializable {

    private static final long serialVersionUID = 8872242972320074665L;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 提现批次号
     */
    @NotNull(message = "提现批次号不能为空")
    private Long withdrawBatchId;

    /**
     * 当前页
     */
    @NotNull(message = "当前页不能为空")
    private Integer pageNo;

    /**
     * 每页记录数
     */
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize;
}
