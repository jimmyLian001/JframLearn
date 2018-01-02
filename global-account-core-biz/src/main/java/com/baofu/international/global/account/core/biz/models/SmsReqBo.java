package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/3 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class SmsReqBo {

    /**
     * 发送短信接口集合
     */
    private List<String> mobiles;

    /**
     * 发送短信内容
     */
    private String content;
}
