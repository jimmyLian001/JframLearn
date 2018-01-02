package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 地区信息
 * <p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class AreaInfoDo {
    /**
     * 地区id
     */
    private String areaId;

    /**
     * 地区名称
     */
    private String areaName;
}
