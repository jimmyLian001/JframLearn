package com.baofu.cbpayservice.common.enums;


/**
 * <p>
 * 接受清算返回结果枚举类
 * </p>
 * User: yangjian  Date: 2017-05-25 ProjectName:  Version: 1.0
 */
public enum ClearAccResultEnum {

    /**
     * 成功
     */
    CODE_SUCCESS("1", "处理成功"),

    /**
     * 失败
     */
    CODE_FAIL("0", "处理失败"),

    /**
     * 异常
     */
    CODE_EXCEPTION("-1", "处理异常"),

    /**
     * 处理中
     */
    CODE_DEALING("2", "处理中"),

    /**
     * 中间状态
     */
    CODE_MID("3", "中间状态");

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常描述
     */
    private String desc;

    private ClearAccResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ClearAccResultEnum explain(String code) {
        ClearAccResultEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            ClearAccResultEnum clearAccResultEnum = arr$[i$];
            if (clearAccResultEnum.code.equals(code)) {
                return clearAccResultEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
