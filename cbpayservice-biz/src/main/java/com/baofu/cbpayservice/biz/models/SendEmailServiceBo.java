package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 发送邮件 监听
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SendEmailServiceBo {

    /**
     * 邮件内容
     */
    @NotBlank(message = "邮件内容不能为空")
    private String mailContent;

    /**
     * 收件人地址
     */
    @NotBlank(message = "收件人地址不能为空")
    private List<String> mailAddressTO;

    /**
     * 抄送人地址
     */
    private List<String> mailAddressCC;

    /**
     * 密送人地址
     */
    private List<String> mailAddressBCC;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 发件人邮箱
     */
    private String userName;

    /**
     * 发件人邮箱密码
     */
    private String userPassword;

    /**
     * 发件人邮箱服务协议地址
     */
    private String emailHosts;

    /**
     * DFS文件ID
     */
    private Long fileId;
}
