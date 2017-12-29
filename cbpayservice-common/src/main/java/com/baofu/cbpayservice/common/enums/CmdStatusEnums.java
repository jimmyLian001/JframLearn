package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * dispatch业务命令状态
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum CmdStatusEnums {

    /**
     * 处理成功
     */
    Success("S", "成功"),

    /**
     * 处理失败
     */
    Failure("F", "失败"),

    /**
     * 初始化
     */
    Initial("I", "初始化"),

    /**
     * 待处理
     */
    Wait("W", "待处理"),

    /**
     * 处理中
     */
    Processing("P", "处理中");

    /**
     * 业务状态枚举Map
     */
    public static Map<String, CmdStatusEnums> codeMap = new HashMap<String, CmdStatusEnums>();

    static {
        for (CmdStatusEnums enums : values()) {
            codeMap.put(enums.getCode(), enums);
        }
    }

    /**
     * 编码
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    public static CmdStatusEnums getEnumsByCode(String code) {
        return codeMap.get(code);
    }

    public static Map<String, String> getEnumsMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (CmdStatusEnums enums : values()) {
            map.put(enums.getCode(), enums.getDesc());
        }
        return map;
    }
}
