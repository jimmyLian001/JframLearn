package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.cgw.model.ChannelCacheDTO;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.cbpayservice.biz.CbPayEmailBiz;
import com.baofu.cbpayservice.biz.EmailSendService;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.EmailUtils;
import com.baofu.cbpayservice.dal.models.FiCbPayEmailDetailDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.ConfigManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 跨境人民币邮件处理Biz层相关操作
 * <p>
 * 1、付汇成功发送明细邮件
 * </p>
 * User: wanght Date:2017/04/06 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayEmailBizImpl implements CbPayEmailBiz {

    /**
     * 跨境人民币订单信息Manager
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 邮件服务
     */
    @Autowired
    private EmailSendService emailSendService;

    /**
     * cbpay订单操作服务
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 渠道服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 配置中心統一管理
     */
    @Autowired
    private ConfigManager configManager;

    /**
     * 付汇成功发送明细邮件
     *
     * @param batchNo         批次号
     * @param downloadDetail  下载地址
     * @param emailToReceiver 收件人
     * @param emailCcReceiver 抄送人
     * @return 发送结果
     */
    @Override
    public Boolean sendUploadFile(String batchNo, String downloadDetail, String emailToReceiver, String emailCcReceiver) {
        Long startTime = System.currentTimeMillis();
        // 判定汇款订单是否存在
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceByBatchNo(batchNo);
        if (fiCbPayRemittanceDo == null) {
            log.info("发送明细邮件时未查询汇款订单：{}", batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        //判断是否是中行
        String channelIdStr = fiCbPayRemittanceDo.getChannelId().toString();
        if (channelIdStr.length() < 5 || !Constants.CHINA_BANK_AISLE_ID.equals(channelIdStr.substring(0, 5))) {
            log.warn("发送明细邮件时渠道编号：{}，不是中行渠道，中行通道ID:{}", channelIdStr, Constants.CHINA_BANK_AISLE_ID);
            return false;
        }

        String bankBatchNo = fiCbPayRemittanceDo.getBankBatchNo();

        // 查询交易明细
        List<FiCbPayEmailDetailDo> emailDetailList = cbPayOrderManager.queryEmailDetail(batchNo);
        log.info("发送明细邮件大小：{},batch:{}", emailDetailList.size(), batchNo);

        String filePath = downloadDetail + fiCbPayRemittanceDo.getMemberNo() + File.separator +
                DateUtil.getCurrentStr() + File.separator;

        try {
            int fileCount = 1;

            List<FiCbPayEmailDetailDo> list = new ArrayList<>();
            for (FiCbPayEmailDetailDo emailDetailDo : emailDetailList) {
                // 卡号、身份证解密
                emailDetailDo.setBankCardNo(SecurityUtil.desDecrypt(emailDetailDo.getBankCardNo(), Constants.CARD_DES_PASSWD));
                emailDetailDo.setIdCardNo(SecurityUtil.desDecrypt(emailDetailDo.getIdCardNo(), Constants.CARD_DES_PASSWD));

                list.add(emailDetailDo);
                if (list.size() == Constants.CBPAY_EMAIL_DETAIL_COUNT) {
                    writeExcel(filePath, batchNo, bankBatchNo, fileCount, list);
                    list.clear();
                    fileCount++;
                }
            }

            // 最后一个文件
            if (list.size() > 0) {
                writeExcel(filePath, batchNo, bankBatchNo, fileCount, list);
            }

            // 发送邮件
            log.info("收件人地址：{}", emailToReceiver);
            List<String> emailToAddressList = EmailUtils.emailAddressConvert(emailToReceiver, ",");

            log.info("抄送人地址：{}", emailCcReceiver);
            List<String> emailCcAddressList = EmailUtils.emailAddressConvert(emailCcReceiver, ",");

            sendEmail(filePath + batchNo + File.separator, emailToAddressList, emailCcAddressList, batchNo, bankBatchNo);
            log.info("批次：{}，发送邮件总时间：{}", batchNo, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("发送邮件异常", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 邮件通知清算
     *
     * @param batchNo          批次号
     * @param emailToReceiver  收件人
     * @param emailCcReceiver  抄送人
     * @param emailBCCReceiver 密送人
     * @return 通知结果
     */
    @Override
    public Boolean sendEmailNotifySettle(String batchNo, String emailToReceiver, String emailCcReceiver, String emailBCCReceiver) {
        Long startTime = System.currentTimeMillis();
        // 判定汇款订单是否存在
        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceByBatchNo(batchNo);
        if (fiCbPayRemittanceDo == null) {
            log.info("付汇申请发送邮件通知清算：{}", batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }

        //判断当前渠道是否需要发送通知邮件给清算人员
        if (configManager.getNotNotifyChannel().contains(fiCbPayRemittanceDo.getChannelId() + Constants.SPLIT_SYMBOL)) {
            log.warn("当前付汇渠道无需发送邮件通知清算系统，渠道号：{}", fiCbPayRemittanceDo.getChannelId());
            return Boolean.FALSE;
        }

        // 发送邮件
        log.info("收件人地址：{}", emailToReceiver);
        List<String> emailToAddressList = EmailUtils.emailAddressConvert(emailToReceiver, ",");

        log.info("抄送人地址：{}", emailCcReceiver);
        List<String> emailCcAddressList = EmailUtils.emailAddressConvert(emailCcReceiver, ",");

        List<String> emailBCCAddressList = EmailUtils.emailAddressConvert(emailBCCReceiver, ",");

        ChannelCacheDTO channelCacheDTO = cbPayCacheManager.getChannelCache(fiCbPayRemittanceDo.getChannelId().intValue());
        CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPayRemittanceDo.getMemberNo().intValue());

        String content = Constants.NOTIFY_SETTLE_EMAIL_CONTENT.replace("${memberName}", cacheMemberDto.getName()).replace("${date}",
                DateUtil.getCurrent(Constants.DATE_FORMAT_FULL)).replace("${batchNo}", batchNo).replace("${transMoney}",
                fiCbPayRemittanceDo.getTransMoney().toString()).replace("${channelName}",
                channelCacheDTO.getChannelName()).replace("${settlementCcy}", fiCbPayRemittanceDo.getSystemCcy());
        emailSendService.sendEmailHtml(content, emailToAddressList, emailCcAddressList, emailBCCAddressList, null, Constants.NOTIFY_SETTLE_EMAIL_SUBJECT,
                null, null, null, "宝付网络科技（上海）有限公司");

        log.info("批次：{}，发送邮件总时间：{}", batchNo, System.currentTimeMillis() - startTime);
        return Boolean.TRUE;
    }

    private void writeExcel(String filePath, String batchNo, String bankBatchNo, int fileCount, List<FiCbPayEmailDetailDo> emailDetailDo) throws Exception {

        File file = new File(filePath + batchNo + File.separator + bankBatchNo + "_" + fileCount + ".xlsx");
        log.info("创建明细文件：{},批次号：{}", file.getName(), batchNo);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        Workbook wb = null;
        OutputStream stream = null;
        try {
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("sheet1");
            Row row = sheet.createRow(0);
            setHeadRowValue(row, Constants.EMAIL_ROW_HEAD);

            //创建文件流
            stream = new FileOutputStream(file);

            for (int i = 0; i < emailDetailDo.size(); i++) {
                row = sheet.createRow(i + 1);
                // 设置行信息
                setCellValue(row, emailDetailDo.get(i));
                //写入数据
            }
            wb.write(stream);
        } finally {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(stream);
        }

    }

    private void sendEmail(String filePath, List<String> emailToAddress, List<String> emailCcAddress, String batchNo, String bankBatchNo) {
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        if (fileList == null || fileList.length == 0) {
            log.info("汇款批次号:{},待发送附件信息为空！", batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0002);
        }

        for (File subFile : fileList) {
            String content = Constants.EMAIL_CONTENT.replace("${date}", DateUtil.getCurrent(Constants.DATE_FORMAT_SIMPLE)).replace("${batchNo}", bankBatchNo);
            String subject = Constants.EMAIL_SUBJECT.replace("${date}", DateUtil.getCurrent(Constants.DATE_FORMAT_SIMPLE)).replace("${batchNo}", bankBatchNo);
            emailSendService.sendMsg(content, emailToAddress, emailCcAddress, null, subFile, subject, null, null, null);
        }
    }

    private void setCellValue(Row row, FiCbPayEmailDetailDo emailDetailDo) {
        row.createCell(0).setCellValue(emailDetailDo.getMemberTransId());
        row.createCell(1).setCellValue(emailDetailDo.getTransCcy());
        row.createCell(2).setCellValue(String.valueOf(emailDetailDo.getTransMoney()));
        row.createCell(3).setCellValue(DateUtil.format(emailDetailDo.getTransTime(), DateUtil.settlePattern));
        row.createCell(4).setCellValue(emailDetailDo.getIdHolder());
        row.createCell(5).setCellValue(emailDetailDo.getIdCardNo());
        row.createCell(6).setCellValue(emailDetailDo.getBankCardNo());
        row.createCell(7).setCellValue(emailDetailDo.getMobile());
        row.createCell(8).setCellValue(emailDetailDo.getCommodityName());
        row.createCell(9).setCellValue(emailDetailDo.getCommodityAmount());
        row.createCell(10).setCellValue(emailDetailDo.getCommodityPrice());
    }

    private void setHeadRowValue(Row row, String[] values) {
        row.createCell(0).setCellValue(values[0]);
        row.createCell(1).setCellValue(values[1]);
        row.createCell(2).setCellValue(values[2]);
        row.createCell(3).setCellValue(values[3]);
        row.createCell(4).setCellValue(values[4]);
        row.createCell(5).setCellValue(values[5]);
        row.createCell(6).setCellValue(values[6]);
        row.createCell(7).setCellValue(values[7]);
        row.createCell(8).setCellValue(values[8]);
        row.createCell(9).setCellValue(values[9]);
        row.createCell(10).setCellValue(values[10]);
    }

}
