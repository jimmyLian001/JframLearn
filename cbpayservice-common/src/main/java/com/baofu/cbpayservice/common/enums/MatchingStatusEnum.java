package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 汇入申请和银行汇入通知匹配状态，10-未匹配，11-匹配成功，12-待设置商户编号
 * <p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum MatchingStatusEnum {

    /**
     * 未匹配-->待初审匹配
     */
    NO_MATCH(10, "申请中"),

    /**
     * 未匹配-->待复审匹配
     */
    RE_CHECK_NO_MATCH(14, "未匹配"),

    /**
     * 匹配成功
     */
    YES_MATCH(11, "申请成功"),

    /**
     * 待设置商户编号
     */
    WAIT_SET(12, "待设置商户编号"),

    /**
     * 文件处理失败
     */
    FILE_DETAIL_FAIL(7, "文件处理失败"),

    /**
     * 失效
     */
    FAIL(13, "申请失败");

    /**
     * 产品id
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
