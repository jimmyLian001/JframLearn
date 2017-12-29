package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.member.dal.model.MaMerchantLink;
import com.baofoo.member.service.facade.cbp_facade.QueryMemberEmailFacade;
import com.baofu.cbpayservice.biz.SettleEmailBiz;
import com.baofu.cbpayservice.biz.convert.SettleEmailConvert;
import com.baofu.cbpayservice.biz.integration.ma.MemberEmailQueryBizImpl;
import com.baofu.cbpayservice.biz.models.MemberEmailBo;
import com.baofu.cbpayservice.biz.models.SendEmailServiceBo;
import com.baofu.cbpayservice.common.constants.EmailConstants;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.common.util.EmailUtils;
import com.baofu.cbpayservice.common.util.PdfUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPaySettleManager;
import com.google.common.base.Splitter;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 结汇流程发送邮件通知
 * <p>
 * 1、收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
 * 2、收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
 * 3、人工匹配之后发送邮件给结汇相关人员提示商户可以上传明细和商户
 * 4、结汇完成之后发送结汇成功邮件通知结汇相关人员和商户
 * </p>
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleEmailBizImpl implements SettleEmailBiz {

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
     * 查询商户结汇申请信息
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 查询商户结汇申请信息
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 跨境结汇订单信息Manager
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 收汇通知收件邮箱
     */
    @Value("${mail.smtp.earningsNotifyTO}")
    private String earningsNotifyTO;

    /**
     * 收汇通知抄送邮箱
     */
    @Value("${mail.smtp.earningsNotifyCC}")
    private String earningsNotifyCC;

    /**
     * 汇入汇款申请通知收件邮箱
     */
    @Value("${mail.smtp.applyNotifyTO}")
    private String applyNotifyTO;

    /**
     * 汇入汇款申请通知抄送邮箱
     */
    @Value("${mail.smtp.applyNotifyCC}")
    private String applyNotifyCC;

    /**
     * 结汇成功通知清算收件邮箱
     */
    @Value("${mail.smtp.settleSuccessNotifyTO}")
    private String settleSuccessNotifyTO;

    /**
     * 结汇成功通知清算抄送邮箱
     */
    @Value("${mail.smtp.settleSuccessNotifyCC}")
    private String settleSuccessNotifyCC;

    /**
     * 匹配成功，相关结汇人员发送邮箱
     */
    @Value("${mail.smtp.notifyMemberUploadFileTO}")
    private String notifyMemberUploadFileTO;

    /**
     * 匹配成功，相关结汇人员超送邮箱
     */
    @Value("${mail.smtp.notifyMemberUploadFileCC}")
    private String notifyMemberUploadFileCC;

    /**
     * 宝付结汇凭证模板
     */
    @Value("${settle.voucher.Tmp}")
    private String settleVoucherTmpPath;

    /**
     * 宝付结汇凭证上传路径
     */
    @Value("${settle.voucher.upload}")
    private String settleVoucherUploadPath;

    /**
     * 汇款凭证密送地址
     */
    @Value("${email.address.bcc}")
    private String emailAddressBCC;

    /**
     * 收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
     *
     * @param incomeNo  汇入汇款编号
     * @param incomeAmt 汇入实际收到金额
     * @param incomeCcy 汇入币种
     */
    @Override
    public void exchangeEarningsNotify(String incomeNo, BigDecimal incomeAmt, String incomeCcy) {

        try {
            DecimalFormat df = new DecimalFormat("#,##0.000");

            //邮件内容
            String content = EmailConstants.EXCHANGE_NOTIFY_CONTENT.replace("${incomeNo}", incomeNo)
                    .replace("${amount}", df.format(incomeAmt))
                    .replace("${ccy}", incomeCcy);

            //收件人
            List<String> earningsNotifyTOList = null;
            if (StringUtils.isNotBlank(earningsNotifyTO)) {
                earningsNotifyTOList = Splitter.on("|!").splitToList(earningsNotifyTO);
            }

            //抄送人
            List<String> earningsNotifyCCList = null;
            if (StringUtils.isNotBlank(earningsNotifyCC)) {
                earningsNotifyCCList = Splitter.on("|!").splitToList(earningsNotifyCC);
            }

            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(earningsNotifyTOList);
            sendEmailServiceBo.setMailAddressCC(earningsNotifyCCList);
            sendEmailServiceBo.setSubject(EmailConstants.EXCHANGE_NOTIFY_TITLE);

            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 人工匹配之后发送邮件给结汇相关人员提示商户可以上传明细发送邮件,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error(" 发送邮件失败", e);
        }

    }

    /**
     * 收到商户前台录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
     *
     * @param memberId 商户编号
     * @param TtNo     商户填写TT编号
     * @param orderAmt 商户填写汇款金额
     * @param orderCcy 商户填写汇款币总
     */
    @Override
    public void importApplyNotify(Long memberId, String TtNo, BigDecimal orderAmt, String orderCcy) {

        try {
            DecimalFormat df = new DecimalFormat("#,##0.000");
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(memberId.intValue());
            //邮件内容
            String content = EmailConstants.IMPORT_APPLY_NOTIFY_CONTENT.
                    replace("${memberName}", cacheMemberDto.getName()).
                    replace("${TtNo}", TtNo).
                    replace("${amount}", df.format(orderAmt)).
                    replace("${ccy}", orderCcy);

            //收件人
            List<String> earningsNotifyTOList = null;
            if (StringUtils.isNotBlank(applyNotifyTO)) {
                earningsNotifyTOList = Splitter.on("|!").splitToList(earningsNotifyTO);
            }

            //抄送人
            List<String> earningsNotifyCCList = null;
            if (StringUtils.isNotBlank(applyNotifyCC)) {
                earningsNotifyCCList = Splitter.on("|!").splitToList(earningsNotifyCC);
            }

            //邮件配置信息
            SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
            sendEmailServiceBo.setMailContent(content);
            sendEmailServiceBo.setMailAddressTO(earningsNotifyTOList);
            sendEmailServiceBo.setMailAddressCC(earningsNotifyCCList);
            sendEmailServiceBo.setSubject(EmailConstants.IMPORT_APPLY_NOTIFY_TITLE);

            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
            log.info("call 人工匹配之后发送邮件给结汇相关人员提示商户可以上传明细发送邮件,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);

        } catch (Exception e) {

            log.error("call 发送邮件失败", e);
        }
    }

    /**
     * 结汇完成之后发送结汇成功邮件通知结汇相关人员
     *
     * @param settleOrderId 结汇订单ID
     */
    @Override
    public void settleSuccessNotify(Long settleOrderId) {

        try {
            //查询信息
            FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(settleOrderId);
            FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(settleOrderId);
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPaySettleApplyDo.getMemberId().intValue());

            //生成pdf文件
            String filePath = settleVoucherUploadPath + UUID.randomUUID().toString() + ".pdf";
            Map<String, String> map = SettleEmailConvert.settleSuccessNotifyMap(fiCbPaySettleDo, fiCbPaySettleApplyDo, cacheMemberDto);
            PdfUtils.editPDF(settleVoucherTmpPath, map, filePath);

            //上传文件到dfs
            InsertReqDTO insertReqDTO = SettleEmailConvert.toInsertReqDTO(settleOrderId);
            File file = new File(filePath);
            CommandResDTO commandResDTO = DfsClient.upload(file, insertReqDTO);
            log.info("call  上传结汇凭证文件响应信息:{}", commandResDTO);
            file.delete();

            //结汇完成之后发送结汇成功邮件通知商户
            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(fiCbPaySettleApplyDo.getMemberId());
            List<String> mailBCCAddress = EmailUtils.emailAddressConvert(emailAddressBCC, ",");
            log.info("汇款凭证密送地址：", emailAddressBCC);
            SendEmailServiceBo memberSEBo = SettleEmailConvert.toSettleSuccessMember(memberEmailBo.getBusinessEmail(), mailBCCAddress,
                    commandResDTO.getFileId());
            //发送邮件
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, memberSEBo);
            log.info("call 结汇完成之后发送结汇成功邮件通知结汇相关人员,生产者，队列名{},发送内容:{}",
                    MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, memberSEBo);

        } catch (Exception e) {

            log.error("call 发送邮件失败", e);
        }
    }

}
