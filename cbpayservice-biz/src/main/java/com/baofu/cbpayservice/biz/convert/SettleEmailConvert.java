package com.baofu.cbpayservice.biz.convert;

import com.baofoo.Response;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.cbpayservice.biz.models.SendEmailServiceBo;
import com.baofu.cbpayservice.common.constants.EmailConstants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.CcyEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 结汇邮件对象转换
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
public final class SettleEmailConvert {

    private SettleEmailConvert() {

    }

    /**
     * 结汇完成之后发送结汇成功邮件通知结汇相关人员内容转换
     *
     * @param fiCbPaySettleDo      收汇信息信息
     * @param fiCbPaySettleApplyDo 商户汇入申请信息
     * @param cacheMemberDto       商户缓存信息
     * @return 转换集合
     */
    public static Map<String, String> settleSuccessNotifyMap(FiCbPaySettleDo fiCbPaySettleDo,
                                                             FiCbPaySettleApplyDo fiCbPaySettleApplyDo, CacheMemberDto cacheMemberDto) {

        DecimalFormat df = new DecimalFormat("#,##0.000");
        Map<String, String> map = Maps.newHashMap();
        map.put("merchantNo", String.valueOf(cacheMemberDto.getMemberId()));
        map.put("merchantName", cacheMemberDto.getName());
        map.put("payeeBankName", fiCbPaySettleDo.getBankName());
        map.put("payeeBankNo", fiCbPaySettleApplyDo.getIncomeAccount());
        map.put("payerNo", fiCbPaySettleDo.getIncomeNo());
        map.put("incomeAmt", df.format(fiCbPaySettleDo.getIncomeAmt()));
        map.put("incomeRate", df.format(fiCbPaySettleDo.getMemberSettleRate()));
        map.put("incomeCnyAmt", df.format(fiCbPaySettleDo.getMemberSettleAmt()));
        map.put("settleFeeAmt", df.format(fiCbPaySettleDo.getSettleFee()));
        map.put("settleCnyAmt", df.format((fiCbPaySettleDo.getMemberSettleAmt() == null
                ? NumberConstants.FRACTIONAL_AMOUNT : fiCbPaySettleDo.getMemberSettleAmt()).subtract(fiCbPaySettleDo.getSettleFee() == null
                ? NumberConstants.FRACTIONAL_AMOUNT : fiCbPaySettleDo.getSettleFee())));
        map.put("settleCcy", CcyEnum.getCcyCHN(fiCbPaySettleDo.getIncomeCcy()));
        map.put("settleAmt", df.format(fiCbPaySettleDo.getRealIncomeAmt()));
        map.put("settleCnyAmtCHN", StringUtil.toCapital(((fiCbPaySettleDo.getMemberSettleAmt() == null
                ? NumberConstants.FRACTIONAL_AMOUNT : fiCbPaySettleDo.getMemberSettleAmt()).subtract(
                (fiCbPaySettleDo.getSettleFee() == null ? NumberConstants.FRACTIONAL_AMOUNT :
                        fiCbPaySettleDo.getSettleFee()))).doubleValue()));
        map.put("createDate", DateUtil.getCurrent(DateUtil.settlePattern));
        map.put("remarks", "宝付结汇凭证");

        return map;
    }

    /**
     * 根据文件Id返回excel信息
     *
     * @param fileId 文件ID
     * @return 上传文件内容
     */
    public static CommandResDTO getCommandResDTO(Long fileId) {

        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(fileId);
        reqDTO.setOperation(Operation.QUERY);
        Response res = SocketUtil.sendMessage(reqDTO);
        return (CommandResDTO) res.getResult();
    }

    /**
     * 结汇成功通知清算人员
     *
     * @param memberName            商户名
     * @param settleSuccessNotifyTO 发送地址
     * @param settleSuccessNotifyCC 抄送地址
     * @param fileId                结汇凭证dfsId
     * @param incomeNo              汇入汇款编号
     * @return 邮件参数
     */
    public static SendEmailServiceBo toSettleSuccessCm(String memberName, String settleSuccessNotifyTO,
                                                       String settleSuccessNotifyCC,
                                                       Long fileId, String incomeNo) {

        //邮件内容
        String cmContent = EmailConstants.SETTLE_SUCCESS_NOTIFY_CONTENT.replace("${memberName}", memberName)
                .replace("${TtNo}", incomeNo);
        //发件人
        List<String> settleSuccessNotifyTOList = null;
        if (StringUtils.isNotBlank(settleSuccessNotifyTO)) {
            settleSuccessNotifyTOList = Splitter.on("|!").splitToList(settleSuccessNotifyTO);
        }
        //抄送人
        List<String> settleSuccessNotifyCCList = null;
        if (StringUtils.isNotBlank(settleSuccessNotifyCC)) {
            settleSuccessNotifyCCList = Splitter.on("|!").splitToList(settleSuccessNotifyCC);
        }

        SendEmailServiceBo cmSendEmailServiceBo = new SendEmailServiceBo();
        cmSendEmailServiceBo.setFileId(fileId);
        cmSendEmailServiceBo.setMailContent(cmContent);
        cmSendEmailServiceBo.setMailAddressTO(settleSuccessNotifyTOList);
        cmSendEmailServiceBo.setMailAddressCC(settleSuccessNotifyCCList);
        cmSendEmailServiceBo.setSubject(EmailConstants.SETTLE_SUCCESS_NOTIFY_TITLE);

        return cmSendEmailServiceBo;
    }

    /**
     * 结汇成功通知清算人员
     *
     * @param fileId         结汇凭证dfsId
     * @param memberEmail    收件人
     * @param mailBCCAddress 密送人
     * @return 邮件参数
     */
    public static SendEmailServiceBo toSettleSuccessMember(String memberEmail, List<String> mailBCCAddress, Long fileId) {

        //邮件内容
        List<String> memberEmailList = Lists.newArrayList();
        memberEmailList.add(memberEmail);

        SendEmailServiceBo cmSendEmailServiceBo = new SendEmailServiceBo();
        cmSendEmailServiceBo.setFileId(fileId);
        cmSendEmailServiceBo.setMailContent(EmailConstants.SETTLE_SUCCESS_NOTIFY_MEMBER_CONTENT);
        cmSendEmailServiceBo.setMailAddressTO(memberEmailList);
        cmSendEmailServiceBo.setMailAddressBCC(mailBCCAddress);
        cmSendEmailServiceBo.setSubject(EmailConstants.SETTLE_SUCCESS_NOTIFY_MEMBER_TITLE);

        return cmSendEmailServiceBo;
    }

    /**
     * 上传结汇凭证文件参数
     *
     * @return DFS文件上传请求参数model
     */
    public static InsertReqDTO toInsertReqDTO(Long settleOrderId) {

        InsertReqDTO insertReqDTO = new InsertReqDTO();
        //文件名
        insertReqDTO.setFileName("宝付结汇凭证（电子）_" + settleOrderId + ".pdf");
        //机构编码
        insertReqDTO.setOrgCode("CBPAY");
        //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        //文件日期
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        //备注信息
        insertReqDTO.setRemark("宝付结汇凭证（电子）");
        return insertReqDTO;
    }

    /**
     * 匹配完成，通知商户上传交易明细邮件
     *
     * @param bankName      银行名称
     * @param account       宝付备付金账户
     * @param incomeAmt     到账金额
     * @param incomeCcy     到账币种
     * @param businessEmail 商户邮箱
     * @return 商户通知邮箱内容
     */
    public static SendEmailServiceBo toMemberUploadFile(String bankName, String account, BigDecimal incomeAmt,
                                                        String incomeCcy, String businessEmail) {

        DecimalFormat df = new DecimalFormat("#,##0.000");
        String content = EmailConstants.NOTIFY_MEMBER_UPLOAD_FILE_CONTENT.replace("${account}", account)
                .replace("${amount}", df.format(incomeAmt))
                .replace("${bankName}", bankName)
                .replace("${ccy}", incomeCcy);

        List<String> accountList = Lists.newArrayList();
        accountList.add(businessEmail);
        SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
        sendEmailServiceBo.setSubject(EmailConstants.NOTIFY_MEMBER_UPLOAD_FILE_TITLE);
        sendEmailServiceBo.setMailContent(content);
        sendEmailServiceBo.setMailAddressTO(accountList);

        return sendEmailServiceBo;
    }

    /**
     * 匹配完成，通知结汇相关人员提醒商户上传交易明细邮件
     *
     * @param memberName               商户名
     * @param bankName                 银行名称
     * @param account                  宝付备付金账户
     * @param incomeAmt                到账金额
     * @param incomeCcy                到账币种
     * @param notifyMemberUploadFileTO 发送地址
     * @param notifyMemberUploadFileCC 抄送地址
     * @return 邮件服务参数
     */
    public static SendEmailServiceBo toCmUploadFile(String memberName, String bankName, String account, BigDecimal incomeAmt, String incomeCcy,
                                                    String notifyMemberUploadFileTO, String notifyMemberUploadFileCC) {

        //邮件内容
        DecimalFormat df = new DecimalFormat("#,##0.000");
        String content = EmailConstants.NOTIFY_CM_UPLOAD_FILE_CONTENT.replace("${account}", account)
                .replace("${amount}", df.format(incomeAmt))
                .replace("${memberName}", memberName)
                .replace("${bankName}", bankName)
                .replace("${ccy}", incomeCcy);
        //发件人
        List<String> settleSuccessNotifyTOList = null;
        if (StringUtils.isNotBlank(notifyMemberUploadFileTO)) {
            settleSuccessNotifyTOList = Splitter.on("|!").splitToList(notifyMemberUploadFileTO);
        }
        //抄送人
        List<String> settleSuccessNotifyCCList = null;
        if (StringUtils.isNotBlank(notifyMemberUploadFileCC)) {
            settleSuccessNotifyCCList = Splitter.on("|!").splitToList(notifyMemberUploadFileCC);
        }

        SendEmailServiceBo cmSendEmailServiceBo = new SendEmailServiceBo();
        cmSendEmailServiceBo.setMailContent(content);
        cmSendEmailServiceBo.setMailAddressTO(settleSuccessNotifyTOList);
        cmSendEmailServiceBo.setMailAddressCC(settleSuccessNotifyCCList);
        cmSendEmailServiceBo.setSubject(EmailConstants.NOTIFY_CM_UPLOAD_FILE_TITLE);
        return cmSendEmailServiceBo;
    }
}
