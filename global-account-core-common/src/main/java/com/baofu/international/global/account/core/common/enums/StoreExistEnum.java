package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/19
 */
@Getter
@AllArgsConstructor
public enum StoreExistEnum {


    /**
     * 已有店铺
     */
    YES("Y", "已有店铺"),

    /**
     * 暂无店铺
     */
    NO("N", "暂无店铺"),;

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
