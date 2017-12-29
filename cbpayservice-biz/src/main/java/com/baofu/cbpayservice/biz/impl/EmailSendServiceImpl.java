package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.EmailSendService;
import com.baofu.cbpayservice.common.constants.Constants;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件
 * User: wanght Date:2017/03/03 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class EmailSendServiceImpl implements EmailSendService {

    private static final String personalName = "宝付网络科技（上海）有限公司";

    /**
     * 发送邮件用户
     */
    @Value("${mail.smtp.username}")
    private String sendUserName;

    /**
     * 发送邮件用户密码
     */
    @Value("${mail.smtp.password}")
    private String sendUserPassword;

    /**
     * 邮箱Host
     */
    @Value("${mail.smtp.host}")
    private String emailHost;

    /**
     * 邮箱Host
     */
    @Value("${mail.smtp.auth}")
    private String emailAuth;

    /**
     * 添加多个附件
     *
     * @param file      附件路径地址
     * @param multipart 田间附件信息
     */
    private static void addFile(File file, Multipart multipart) throws Exception {
        if (file == null) {
            return;
        }
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(file);
        mimeBodyPart.setDataHandler(new DataHandler(fds));
        mimeBodyPart.setFileName(MimeUtility.encodeText(fds.getName(), Constants.UTF8, "B"));
        multipart.addBodyPart(mimeBodyPart);
    }

    /**
     * 设置抄送多个邮箱地址
     *
     * @param message         发送邮件对象
     * @param mailAddressList 需要抄送的邮箱地址
     * @param type            接收邮件信息人为收件人还是抄送人
     */
    private static void addRecipients(MimeMessage message, List<String> mailAddressList, Message.RecipientType type) throws Exception {
        if (mailAddressList == null || mailAddressList.isEmpty()) {
            return;
        }
        Address[] address = new InternetAddress[mailAddressList.size()];
        for (int i = 0; i < mailAddressList.size(); i++) {
            address[i] = new InternetAddress(mailAddressList.get(i));
        }
        message.setRecipients(type, address);
    }

    /**
     * 发送邮件
     *
     * @param mailContent    邮件内容
     * @param mailAddressTO  收件人地址，可以多个
     * @param mailAddressCC  抄送人地址，可以多个
     * @param mailAddressBCC 密送人地址，可以多个
     * @param file           附件地址，可以是多个
     * @param subject        邮件主题
     * @param userName       邮箱用户名
     * @param userPassword   邮箱密码
     * @param emailHosts     邮箱host
     */
    @Override
    public Boolean sendMsg(String mailContent, List<String> mailAddressTO, List<String> mailAddressCC, List<String> mailAddressBCC,
                           File file, String subject, String userName, String userPassword, String emailHosts) {
        try {

            if (userName != null && userPassword != null && emailHosts != null) {
                this.sendUserName = userName;
                this.sendUserPassword = userPassword;
                this.emailHost = emailHosts;
            }

            if (mailAddressTO == null || mailAddressTO.isEmpty()) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "收件人地址为空");
            }
            Properties props = new Properties();
            props.put("mail.smtp.host", emailHost);
            // 是否身份验证
            props.put("mail.smtp.auth", emailAuth);
            PopupAuthenticator auth = new PopupAuthenticator();
            Session session = Session.getInstance(props, auth);
            // 主要是利于调试，默认为false
            session.setDebug(false);
            // 发件人
            Address addressFrom = new InternetAddress(sendUserName, MimeUtility.encodeWord(personalName));
            Multipart container = new MimeMultipart();
            if (StringUtils.isNotEmpty(mailContent)) {
                MimeBodyPart textBodyPart = new MimeBodyPart();
                // 邮件内容
                textBodyPart.setText(mailContent);
                container.addBodyPart(textBodyPart);
            }
            MimeMessage message = new MimeMessage(session);
            //设置邮件发送时间
            message.setSentDate(new Date());
            // 设置邮件主题的编码格式
            message.setSubject(subject, Constants.UTF8);
            //发件人
            message.setFrom(addressFrom);
            //抄送人
            addRecipients(message, mailAddressTO, Message.RecipientType.TO);
            //抄送人
            addRecipients(message, mailAddressCC, Message.RecipientType.CC);
            //密送人
            addRecipients(message, mailAddressBCC, Message.RecipientType.BCC);
            //添加附件信息，可以多个附件发送
            addFile(file, container);
            // 添加 文本文件和附件
            message.setContent(container);
            message.saveChanges();
            Transport.send(message);

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("发送邮件信息失败：{}", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 发送邮件 HTML
     *
     * @param mailContent    邮件内容
     * @param mailAddressTO  收件人地址，可以多个
     * @param mailAddressCC  抄送人地址，可以多个
     * @param mailAddressBCC 密送人地址，可以多个
     * @param file           附件地址，可以是多个
     * @param subject        邮件主题
     * @param userName       邮箱用户名
     * @param userPassword   邮箱密码
     * @param emailHosts     邮箱host
     * @param personalName   发件人名称
     */
    @Override
    public Boolean sendEmailHtml(String mailContent, List<String> mailAddressTO, List<String> mailAddressCC, List<String> mailAddressBCC,
                                 File file, String subject, String userName, String userPassword, String emailHosts, String personalName) {
        try {

            if (userName != null && userPassword != null && emailHosts != null) {
                this.sendUserName = userName;
                this.sendUserPassword = userPassword;
                this.emailHost = emailHosts;
            }

            if (mailAddressTO == null || mailAddressTO.isEmpty()) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "收件人地址为空");
            }
            Properties props = new Properties();
            props.put("mail.smtp.host", emailHost);
            // 是否身份验证
            props.put("mail.smtp.auth", emailAuth);
            PopupAuthenticator auth = new PopupAuthenticator();
            Session session = Session.getInstance(props, auth);
            // 主要是利于调试，默认为false
            session.setDebug(false);
            // 发件人
            Address addressFrom = new InternetAddress(sendUserName, MimeUtility.encodeWord(personalName));
            Multipart container = new MimeMultipart();
            if (StringUtils.isNotEmpty(mailContent)) {
                MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setContent(mailContent, "text/html; charset=utf-8");
                container.addBodyPart(textBodyPart);
            }
            MimeMessage message = new MimeMessage(session);
            //设置邮件发送时间
            message.setSentDate(new Date());
            // 设置邮件主题的编码格式
            message.setSubject(subject, Constants.UTF8);
            //发件人
            message.setFrom(addressFrom);
            //抄送人
            addRecipients(message, mailAddressTO, Message.RecipientType.TO);
            //抄送人
            addRecipients(message, mailAddressCC, Message.RecipientType.CC);
            //密送人
            addRecipients(message, mailAddressBCC, Message.RecipientType.BCC);
            //添加附件信息，可以多个附件发送
            addFile(file, container);
            // 添加 文本文件和附件
            message.setContent(container);
            message.saveChanges();
            Transport.send(message);

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("发送邮件信息失败：{}", e);
            return Boolean.FALSE;
        }
    }

    private class PopupAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sendUserName, sendUserPassword);
        }
    }

}
