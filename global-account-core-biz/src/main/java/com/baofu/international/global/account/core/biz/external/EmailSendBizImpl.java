package com.baofu.international.global.account.core.biz.external;

import com.baofu.international.global.account.core.biz.models.EmailReqBo;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件统一服务
 * <p>
 * 1、系统发送邮件
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Component
public class EmailSendBizImpl {


    private static final String PERSONAL_NAME = "宝付网络科技（上海）有限公司";

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 系统发送邮件
     *
     * @param emailReqBo 发送邮件参数信息
     * @return 返回是否发送成功
     */
    public Boolean emailSend(EmailReqBo emailReqBo) {
        try {
            log.info("系統发送邮件请求参数信息：{}", emailReqBo);
            Properties props = new Properties();
            props.put("mail.smtp.host", configDict.getEmailHost());
            // 是否身份验证
            props.put("mail.smtp.auth", configDict.getEmailAuth());
            PopupAuthenticator auth = new PopupAuthenticator();
            Session session = Session.getInstance(props, auth);
            // 主要是利于调试，默认为false
            session.setDebug(false);
            // 发件人
            Address addressFrom = new InternetAddress(configDict.getEmailUserName(), MimeUtility.encodeWord(PERSONAL_NAME));
            Multipart container = new MimeMultipart();
            if (StringUtils.isNotEmpty(emailReqBo.getContent())) {
                MimeBodyPart textBodyPart = new MimeBodyPart();
                // 邮件内容
                textBodyPart.setText(emailReqBo.getContent());
                container.addBodyPart(textBodyPart);
            }
            MimeMessage message = new MimeMessage(session);
            //设置邮件发送时间
            message.setSentDate(new Date());
            // 设置邮件主题的编码格式
            message.setSubject(emailReqBo.getSubject(), CommonDict.UTF_8);
            //发件人
            message.setFrom(addressFrom);
            //抄送人
            addRecipients(message, emailReqBo.getMailAddressTO(), Message.RecipientType.TO);
            //抄送人
            addRecipients(message, emailReqBo.getMailAddressCC(), Message.RecipientType.CC);
            //密送人
            addRecipients(message, emailReqBo.getMailAddressBCC(), Message.RecipientType.BCC);
            //添加附件信息，可以多个附件发送
            addFile(emailReqBo.getFiles(), container);
            // 添加 文本文件和附件
            message.setContent(container);
            message.saveChanges();
            Transport.send(message);

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("发送邮件异常：", e);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190004);
        }
    }

    /**
     * 添加多个附件
     *
     * @param files     附件集合
     * @param multipart 田间附件信息
     * @throws Exception 抛出异常
     */
    private void addFile(List<File> files, Multipart multipart) throws Exception {

        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        //附件添加至邮件中
        for (File file : files) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(file);
            mimeBodyPart.setDataHandler(new DataHandler(fds));
            mimeBodyPart.setFileName(MimeUtility.encodeText(fds.getName(), CommonDict.UTF_8, "B"));
            multipart.addBodyPart(mimeBodyPart);
        }
    }


    /**
     * 设置抄送多个邮箱地址
     *
     * @param message         发送邮件对象
     * @param mailAddressList 需要抄送的邮箱地址
     * @param type            接收邮件信息人为收件人还是抄送人
     * @throws Exception 抛出异常
     */
    private void addRecipients(MimeMessage message, List<String> mailAddressList, Message.RecipientType type) throws Exception {
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
     * 设置邮箱用户名和密码
     */
    private class PopupAuthenticator extends Authenticator {

        /**
         * 邮箱认证
         *
         * @return 返回认证结果
         */
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(configDict.getEmailUserName(), configDict.getEmailUserPass());
        }
    }
}
