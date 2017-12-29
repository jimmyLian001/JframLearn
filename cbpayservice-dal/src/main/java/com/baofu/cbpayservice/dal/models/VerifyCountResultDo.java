package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 实名认证统计结果
 * <p>
 * Created by 莫小阳 on 2017/8/10.
 */
@Getter
@Setter
@ToString
public class VerifyCountResultDo implements Serializable {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 总笔数
     */
    private Integer totalCount;

    /**
     * 成功笔数
     */
    private Integer suc;

    /**
     * 失败笔数
     */
    private Integer fail;


}
