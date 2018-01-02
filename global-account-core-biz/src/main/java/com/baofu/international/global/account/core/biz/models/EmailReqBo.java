package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString(exclude = {"files"})
public class EmailReqBo {

    /**
     * 收件人邮箱列表
     */
    private List<String> mailAddressTO;

    /**
     * 抄送人邮箱列表
     */
    private List<String> mailAddressCC;

    /**
     * 密送邮箱列表
     */
    private List<String> mailAddressBCC;

    /**
     * 批量发送邮件附件
     */
    private List<File> files;

    /**
     * 发送邮件内容
     */
    private String content;

    /**
     * 邮件主题
     */
    private String subject;
}
