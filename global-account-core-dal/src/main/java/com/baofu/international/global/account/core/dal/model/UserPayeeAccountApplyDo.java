package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserPayeeAccountApplyDo extends BaseDo {

    /**
     * 申请编号
     */
    private Long applyId;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 用户号
     */
    private Long userNo;
    /**
     * 申请开通账户号
     */
    private String applyAccNo;
    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 状态：0-待处理，1-处理中，2-成功，3-失败，4-关闭
     */
    private int status;
    /**
     * 店铺编号
     */
    private Long storeNo;

    /**
     * 收款账户内部账户编号
     */
    private Long accountNo;

}