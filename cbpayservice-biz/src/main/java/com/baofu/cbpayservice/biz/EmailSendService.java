package com.baofu.cbpayservice.biz;

import java.io.File;
import java.util.List;

/**
 * 发送邮件
 * User: wanght Date:2017/03/03 ProjectName: cbpay-service Version: 1.0
 */
public interface EmailSendService {

    /**
     * 发送邮件
     *
     * @param mailContent      邮件内容
     * @param mailAddressTO    收件人地址，可以多个
     * @param mailAddressCC    抄送人地址，可以多个
     * @param mailAddressBCC   密送人地址，可以多个
     * @param file             附件地址，可以是多个
     * @param subject          邮件主题
     * @param sendUserName     邮箱用户名
     * @param sendUserPassword 邮箱密码
     * @param emailHost        邮箱host
     */
    Boolean sendMsg(String mailContent, List<String> mailAddressTO, List<String> mailAddressCC, List<String> mailAddressBCC,
                    File file, String subject, String sendUserName, String sendUserPassword, String emailHost);

    /**
     * 发送邮件 HTML
     *
     * @param mailContent      邮件内容
     * @param mailAddressTO    收件人地址，可以多个
     * @param mailAddressCC    抄送人地址，可以多个
     * @param mailAddressBCC   密送人地址，可以多个
     * @param file             附件地址，可以是多个
     * @param subject          邮件主题
     * @param sendUserName     邮箱用户名
     * @param sendUserPassword 邮箱密码
     * @param emailHost        邮箱host
     * @param personalName     发件人名称
     */
    Boolean sendEmailHtml(String mailContent, List<String> mailAddressTO, List<String> mailAddressCC, List<String> mailAddressBCC,
                          File file, String subject, String sendUserName, String sendUserPassword, String emailHost, String personalName);

}
