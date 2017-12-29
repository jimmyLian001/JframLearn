package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FiCbPaySettleAccountDo extends BaseDo {

    /**
     * 宝付内部编号
     */
    private Long recordId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款人帐号
     */
    private String incomeAccountNo;

    /**
     * 汇款人名称
     */
    private String incomeAccountName;

    /**
     * 汇款人常驻国家
     */
    private String incomeCountry;

    /**
     * 删除状态，1-未删除，0-删除
     */
    private int deleteFlag;

}