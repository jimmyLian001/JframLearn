package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 省信息
 * <p>
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class ProvinceInfo {
    /**
     * 省id
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String provinceName;
}
