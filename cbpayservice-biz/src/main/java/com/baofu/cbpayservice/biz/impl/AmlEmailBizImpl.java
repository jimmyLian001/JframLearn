package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.cgw.model.ChannelCacheDTO;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofu.cbpayservice.biz.AmlEmailBiz;
import com.baofu.cbpayservice.biz.integration.ma.MemberEmailQueryBizImpl;
import com.baofu.cbpayservice.biz.models.MemberEmailBo;
import com.baofu.cbpayservice.biz.models.SendEmailServiceBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.EmailConstants;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.common.enums.ServiceConfigEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import com.system.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/8/9 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class AmlEmailBizImpl implements AmlEmailBiz {

    /**
     * 查询商户业务邮箱邮箱
     */
    @Autowired
    private MemberEmailQueryBizImpl memberEmailQueryBiz;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 反洗钱失败通知商户
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    @Override
    public void amlFailToMemberEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        try {
            //通知商户邮件
            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(fiCbPayRemittanceDo.getMemberNo());
            //反洗钱失败通知商户邮件
            //收件人
            List<String> amlFailNotifyMemberEmail = Lists.newArrayList();
            amlFailNotifyMemberEmail.add(memberEmailBo.getBusinessEmail());
            //正文内容
            String content = EmailConstants.AML_FAIL_NOTIFY_MEMBER_CONTENT.replace("${batchNo}", fiCbPayRemittanceDo.getBatchNo());
            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(amlFailNotifyMemberEmail);
            sendEmailServiceBo.setSubject(EmailConstants.AML_FAIL_NOTIFY_MEMBER_TITLE);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 反洗钱失败通知商户,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error(" 发送邮件失败", e);
        }
    }

    /**
     * 反洗钱失败通知业务人员
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    public void amlFailToBusinessEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        try {
            DecimalFormat df = new DecimalFormat("#,##0.000");
            ChannelCacheDTO channelCacheDTO = cbPayCacheManager.getChannelCache(fiCbPayRemittanceDo.getChannelId().intValue());
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPayRemittanceDo.getMemberNo().intValue());
            //邮件内容
            String content = EmailConstants.AML_FAIL_NOTIFY_BUSINESS_CONTENT.replace("${memberName}", cacheMemberDto.getName())
                    .replace("${date}", DateUtil.format(fiCbPayRemittanceDo.getCreateAt(), Constants.DATE_FORMAT_FULL_TWO))
                    .replace("${batchNo}", fiCbPayRemittanceDo.getBatchNo())
                    .replace("${transMoney}", df.format(fiCbPayRemittanceDo.getTransMoney()))
                    .replace("${systemCcy}", fiCbPayRemittanceDo.getSystemCcy())
                    .replace("${channelName}", channelCacheDTO.getChannelName());
            //收件人
            List<String> amlFailToBusinessToList = null;
            String amlFailToBusinessToStr = Configuration.getString(ServiceConfigEnum.AML_FAIL_BUSINESS_TO_KEY.getKey());
            if (StringUtils.isNotBlank(amlFailToBusinessToStr)) {
                amlFailToBusinessToList = Splitter.on("|!").splitToList(amlFailToBusinessToStr);
            }

            //抄送人
            List<String> amlFailToBusinessCCList = null;
            String amlFailToBusinessCCStr = Configuration.getString(ServiceConfigEnum.AML_FAIL_BUSINESS_CC_KEY.getKey());
            if (StringUtils.isNotBlank(amlFailToBusinessCCStr)) {
                amlFailToBusinessCCList = Splitter.on("|!").splitToList(amlFailToBusinessCCStr);
            }

            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(amlFailToBusinessToList);
            sendEmailServiceBo.setMailAddressCC(amlFailToBusinessCCList);
            sendEmailServiceBo.setSubject(EmailConstants.AML_FAIL_NOTIFY_BUSINESS_TITLE);

            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 反洗钱失败通知业务人员,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error("call 发送邮件失败", e);
        }
    }

    /**
     * 反洗钱部分成功通知商户
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    @Override
    public void amlPortionSuccessToMemberEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        try {
            //通知商户邮件
            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(fiCbPayRemittanceDo.getMemberNo());
            //反洗钱失败通知商户邮件
            //收件人
            List<String> amlFailNotifyMemberEmail = Lists.newArrayList();
            amlFailNotifyMemberEmail.add(memberEmailBo.getBusinessEmail());
            //正文内容
            String content = EmailConstants.AML_PORTION_SUCCESS_NOTIFY_MEMBER_CONTENT.replace("${batchNo}", fiCbPayRemittanceDo.getBatchNo());
            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(amlFailNotifyMemberEmail);
            sendEmailServiceBo.setSubject(EmailConstants.AML_PORTION_SUCCESS_NOTIFY_MEMBER_TITLE);
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 反洗钱失败通知商户,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error(" 发送邮件失败", e);
        }
    }

    /**
     * 反洗钱部分成功通知业务人员
     *
     * @param fiCbPayRemittanceDo 汇款批次信息
     */
    @Override
    public void amlPortionSuccessToBusinessEmail(FiCbPayRemittanceDo fiCbPayRemittanceDo) {

        try {
            DecimalFormat df = new DecimalFormat("#,##0.000");
            ChannelCacheDTO channelCacheDTO = cbPayCacheManager.getChannelCache(fiCbPayRemittanceDo.getChannelId().intValue());
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPayRemittanceDo.getMemberNo().intValue());
            //邮件内容
            String content = EmailConstants.AML_PORTION_SUCCESS_NOTIFY_BUSINESS_CONTENT.replace("${memberName}", cacheMemberDto.getName())
                    .replace("${date}", DateUtil.format(fiCbPayRemittanceDo.getCreateAt(), Constants.DATE_FORMAT_FULL_TWO))
                    .replace("${batchNo}", fiCbPayRemittanceDo.getBatchNo())
                    .replace("${transMoney}", df.format(fiCbPayRemittanceDo.getTransMoney()))
                    .replace("${systemCcy}", fiCbPayRemittanceDo.getSystemCcy())
                    .replace("${channelName}", channelCacheDTO.getChannelName());
            //收件人
            List<String> amlFailToBusinessToList = null;
            String amlPortionSuccessBusinessTOStr = Configuration.getString(ServiceConfigEnum.AML_PORTION_SUCCESS_BUSINESS_TO_KEY.getKey());
            if (StringUtils.isNotBlank(amlPortionSuccessBusinessTOStr)) {
                amlFailToBusinessToList = Splitter.on("|!").splitToList(amlPortionSuccessBusinessTOStr);
            }

            //抄送人
            List<String> amlFailToBusinessCCList = null;
            String amlPortionSuccessBusinessCCStr = Configuration.getString(ServiceConfigEnum.AML_PORTION_SUCCESS_BUSINESS_CC_KEY.getKey());
            if (StringUtils.isNotBlank(amlPortionSuccessBusinessCCStr)) {
                amlFailToBusinessCCList = Splitter.on("|!").splitToList(amlPortionSuccessBusinessCCStr);
            }

            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(amlFailToBusinessToList);
            sendEmailServiceBo.setMailAddressCC(amlFailToBusinessCCList);
            sendEmailServiceBo.setSubject(EmailConstants.AML_PORTION_SUCCESS_NOTIFY_BUSINESS_TITLE);

            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 反洗钱失败通知业务人员,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error("call 发送邮件失败", e);
        }
    }
}
