package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 莫小阳  on 2017/11/11.
 */
@Getter
@Setter
@ToString
public class UserAccInfoReqDto implements Serializable {
    private static final long serialVersionUID = -3234493307063112059L;

    /**
     * 用户号
     */
    @NotNull(message = "用户号不允许为空")
    private Long userNo;

    /**
     * 币种
     */
    private String ccy;

}
