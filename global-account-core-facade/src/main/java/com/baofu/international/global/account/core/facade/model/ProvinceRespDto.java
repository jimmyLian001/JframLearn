package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 省信息
 * <p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProvinceRespDto implements Serializable {
    /**
     * 省id
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String provinceName;
}
