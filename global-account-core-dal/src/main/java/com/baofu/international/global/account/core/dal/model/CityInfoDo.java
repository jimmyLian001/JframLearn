package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 城市信息
 * <p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class CityInfoDo {
    /**
     * 城市id
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;
}
