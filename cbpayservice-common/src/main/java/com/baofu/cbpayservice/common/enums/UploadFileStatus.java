package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * excel文件状态 ：INIT:初始，TRUE:处理完成，FALSE：处理失败，UPLOAD：已上传,PROCESSING：处理中
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UploadFileStatus {

    /**
     * 状态，INIT:初始，TRUE:处理完成，FALSE：处理失败，UPLOAD：已上传,PROCESSING：处理中
     */

    INIT("INIT", "初始"),

    /**
     * 已上传
     */
    UPLOAD("UPLOAD", "已上传"),

    /**
     * 处理中
     */
    PROCESSING("PROCESSING", "处理中"),

    /**
     * 处理完成
     */
    PASS("TRUE", "处理完成"),

    /**
     * 处理失败
     */
    NO_PASS("FALSE", "处理失败"),

    /**
     * 反洗钱审核中
     */
    AML_AUDITING("AML-AUDITING", "反洗钱审核中"),

    /**
     * 反洗钱审核成功
     */
    AML_SUCCESS("AML-SUCCESS", "反洗钱审核成功"),

    /**
     * 反洗钱审核部分成功
     */
    AML_PART_SUCCESS("AML-PART-SUCCESS", "反洗钱审核部分成功"),

    /**
     * 反洗钱审核失败
     */
    AML_FAIL("AML-FAIL", "反洗钱审核失败"),

    /**
     * 反洗钱审核异常
     */
    AML_EXCEPTION("AML-EXCEPTION", "反洗钱审核异常"),

    /**
     * 创建汇款文件中
     */
    CREATING("CREATING", "创建汇款文件中"),

    /**
     * 创建汇款订单完成
     */
    COMPLETE("COMPLETE", "创建汇款订单完成"),

    /**
     * 文件数据有误
     */
    ERROR("ERROR", "文件数据有误"),

    /**
     * 取消
     */
    CANCEL("CANCEL", "取消"),
    ;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
