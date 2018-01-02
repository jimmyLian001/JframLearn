package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态，INIT:初始，TRUE:处理完成，FALSE：处理失败，UPLOAD：已上传,PROCESSING：处理中
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum FileStateEnum {

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
    FALSE("FALSE", "审核不通过"),;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
