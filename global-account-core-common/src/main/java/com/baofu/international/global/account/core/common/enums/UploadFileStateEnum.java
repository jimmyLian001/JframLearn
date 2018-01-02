package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：excel文件状态 ：INIT:初始，TRUE:处理完成，FALSE：处理失败，UPLOAD：已上传,PROCESSING：处理中
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@AllArgsConstructor
public enum UploadFileStateEnum {

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
     * 文件数据有误
     */
    ERROR("ERROR", "文件数据有误"),

    /**
     * 取消
     */
    CANCEL("CANCEL", "取消"),;

    /**
     * 值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;
}
