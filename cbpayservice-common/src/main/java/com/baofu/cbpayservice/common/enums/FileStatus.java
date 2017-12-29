package com.baofu.cbpayservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态，INIT:初始，TRUE:处理完成，FALSE：处理失败，UPLOAD：已上传,PROCESSING：处理中
 * User: 不良人 Date:2017/02/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum FileStatus {

    /**
     * 服务贸易类型
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
    TRUE("TRUE", "处理完成"),

    /**
     * 审核不通过
     */
    FALSE("FALSE", "审核不通过"),

    /**
     * 审核部分通过
     */
    AML_PART_SUCCESS("AML-PART-SUCCESS", "审核部分通过"),
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
