package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description:用户提现汇总查询 ReqDTO
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
@Getter
@Setter
@ToString
public class UserWithdrawSumQueryReqDto implements Serializable {

    private static final long serialVersionUID = -2971163714760568488L;

    /**
     * 汇款流水号
     */
    private Long remittanceSerialNo;

    /**
     * 海外渠道
     */
    private String overseasChannel;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 境内渠道
     */
    private String domesticChannel;

    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空")
    private String beginTime;

    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    /**
     * 转账状态
     */
    private Integer transferState;

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
