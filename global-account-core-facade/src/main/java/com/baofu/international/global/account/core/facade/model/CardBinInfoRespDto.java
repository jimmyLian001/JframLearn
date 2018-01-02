package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 卡bin信息
 * <p>
 *
 * @author : hetao Date: 2017/11/06 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class CardBinInfoRespDto implements Serializable {

    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 发卡行代码
     */
    private String bankCode;

    /**
     * 卡种名称
     */
    private String cardName;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 卡号长度
     */
    private int cardLength;

    /**
     * BIN号
     */
    private String cardBin;
}
