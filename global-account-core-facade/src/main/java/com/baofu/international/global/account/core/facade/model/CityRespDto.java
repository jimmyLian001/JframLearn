package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 城市信息
 * <p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class CityRespDto implements Serializable {
    /**
     * 城市id
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;
}
