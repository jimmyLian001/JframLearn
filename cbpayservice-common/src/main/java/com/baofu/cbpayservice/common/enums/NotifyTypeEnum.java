package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 通知类型
 * User: 香克斯 Date:2016/9/24 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum NotifyTypeEnum {

    PAGE("PAGE", 0, "页面通知"),
    SERVER("SERVER", 2, "服务端通知"),
    PAGE_SERVER("PAGE_SERVER", 1, "页面通知和服务端通知");

    private String name;
    private Integer code;
    private String desc;


    /**
     * 类型转换
     *
     * @param name
     * @return
     */
    public static Integer findCode(final String name) {

        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (NotifyTypeEnum noticeType : NotifyTypeEnum.values()) {
            if (noticeType.name.equals(name)) {
                return noticeType.getCode();
            }
        }
        return null;
    }
}
