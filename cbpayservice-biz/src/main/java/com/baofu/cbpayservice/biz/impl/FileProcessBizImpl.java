package com.baofu.cbpayservice.biz.impl;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofu.cbpayservice.biz.CbPayRemittanceFileProcessBiz;
import com.baofu.cbpayservice.biz.FileProcessBiz;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.biz.SettleNotifyMemberBiz;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.ExcelBaseValidationBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.biz.models.RemitFileCheckResultBo;
import com.baofu.cbpayservice.biz.models.RemitFileNotfiyBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.common.enums.UploadFileOrderType;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.mapper.FiCbPayMemberApiRqstMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 航旅文件处理
 * <p>
 * User: 不良人 Date:2017/6/22 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class FileProcessBizImpl implements FileProcessBiz {

    /**
     * 异步通知服务
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 商户请求信息
     */
    @Autowired
    private FiCbPayMemberApiRqstMapper apiRqstMapper;

    /**
     * 汇款文件处理
     */
    @Autowired
    private CbPayRemittanceFileProcessBiz cbPayRemittanceFileProcessBiz;

    /**
     * 代理跨境结算服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 上传错误文件
     */
    @Autowired
    private SettleNotifyMemberBiz settleNotifyMemberBiz;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 汇款文件处理
     *
     * @param proxyCustomsMqBo mq内容
     * @param list             汇款文件内容
     */
    @Override
    @Transactional
    public void process(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {

        long startTime = System.currentTimeMillis();
        log.info("call 代理报关服务biz层入参:{} ", proxyCustomsMqBo);
        redisManager.insertObject(0.00, proxyCustomsMqBo.getFileBatchNo().toString(), Constants.FILE_CHECK_SCHEDULE_TIME_OUT);
        //文件内容基础校验
        RemitFileCheckResultBo remitFileCheckResultBo = baseCheck(proxyCustomsMqBo, list);
        if (remitFileCheckResultBo.getErrorMsg() != null) {
            CommandResDTO commandResDTO = dealError(remitFileCheckResultBo.getErrorMsg());
            FiCbPayFileUploadDo fileUploadDo = updateFileBatch(remitFileCheckResultBo, proxyCustomsMqBo.getFileBatchNo(),
                    commandResDTO);
            //异常通知
            if (proxyCustomsMqBo.getSourceFlag() != null && proxyCustomsMqBo.getSourceFlag() == NumberConstants.TWO) {
                fileUploadDo.setRecordCount(remitFileCheckResultBo.getTotalCount());
                fileUploadDo.setFailCount(remitFileCheckResultBo.getErrorCount());
                fileUploadDo.setSuccessCount(remitFileCheckResultBo.getSuccessCount());
                fileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
                upLoadFileNotfiy(fileUploadDo, commandResDTO, proxyCustomsMqBo);
            }
            return;
        }
        String careerType = remitFileCheckResultBo.getCareerType();
        remitFileCheckResultBo = cbPayRemittanceFileProcessBiz.dataParseCheck(proxyCustomsMqBo, list, careerType);
        log.info("call 文件总金额：{}", remitFileCheckResultBo.getTotalAmount());
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(proxyCustomsMqBo.getFileBatchNo());
        fiCbpayFileUploadDo.setCareerType(careerType);
        fiCbpayFileUploadDo.setRecordCount(list.size() - Constants.NO_CONTENT_LINE);
        fiCbpayFileUploadDo.setOrderType(remitFileCheckResultBo.getOrderType());
        fiCbpayFileUploadDo.setTotalAmount(remitFileCheckResultBo.getTotalAmount());
        proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
        log.info("call 代理报关服务biz层，校验和解析处理时间：{}", System.currentTimeMillis() - startTime);

        remitFileCheckResultBo.setCareerType(careerType);
        //校验成功
        if (remitFileCheckResultBo.getErrorMsg().length() == 0) {
            int retCount = cbPayRemittanceFileProcessBiz.batchInsertData(remitFileCheckResultBo, proxyCustomsMqBo);
            fiCbpayFileUploadDo.setStatus(UploadFileStatus.PASS.getCode());
            fiCbpayFileUploadDo.setSuccessCount(retCount);
            proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
            //成功通知
            if (proxyCustomsMqBo.getSourceFlag() != null && proxyCustomsMqBo.getSourceFlag() == NumberConstants.TWO) {
                fiCbpayFileUploadDo.setFailCount(NumberConstants.ZERO);
                fiCbpayFileUploadDo.setSuccessCount(retCount);
                notfiyMember(fiCbpayFileUploadDo, null, proxyCustomsMqBo);
            }
        } else {
            //校验失败
            log.info("call 代理报关服务biz层 校验错误信息：{}", remitFileCheckResultBo.getErrorMsg() + "");
            CommandResDTO commandResDTO = dealError(remitFileCheckResultBo.getErrorMsg());
            FiCbPayFileUploadDo fileUploadDo = updateFileBatch(remitFileCheckResultBo, proxyCustomsMqBo.getFileBatchNo(),
                    commandResDTO);
            //异常通知
            if (proxyCustomsMqBo.getSourceFlag() != null && proxyCustomsMqBo.getSourceFlag() == NumberConstants.TWO) {
                fileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
                fileUploadDo.setRecordCount(list.size() - Constants.NO_CONTENT_LINE);
                fileUploadDo.setSuccessCount(fileUploadDo.getRecordCount() - fileUploadDo.getFailCount());
                upLoadFileNotfiy(fileUploadDo, commandResDTO, proxyCustomsMqBo);
            }
        }
        log.info("call 代理报关服务biz层 总处理时间：{}", System.currentTimeMillis() - startTime);
    }

    /**
     * excel基本校验
     *
     * @param proxyCustomsMqBo mq内容
     * @param list             文件内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo baseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        StringBuilder errorBuffer = new StringBuilder();
        RemitFileCheckResultBo remitFileCheckResultBo = new RemitFileCheckResultBo();
        if (list == null || list.size() < Constants.NO_CONTENT_LINE) {
            log.info("call excel批次：{},订单内容条数不能为空", proxyCustomsMqBo.getFileBatchNo());
            errorBuffer.append("上传文件订单内容条数不能为空");
            remitFileCheckResultBo.setErrorMsg(errorBuffer);
            remitFileCheckResultBo.setTotalCount(NumberConstants.ZERO);
            remitFileCheckResultBo.setSuccessCount(NumberConstants.ZERO);
            remitFileCheckResultBo.setErrorCount(BigDecimal.ZERO.intValue());
            return remitFileCheckResultBo;
        }

        //第二行数据校验
        ExcelBaseValidationBo excelBaseValidationBo = ProxyCustomConvert.toExcelBaseValidationBo(list.get(2));
        String errorMessage = ParamValidate.validateParams(excelBaseValidationBo, Constants.SPLIT_MARK);
        if (errorMessage.length() > 0) {
            log.info("call excel批次：{},第三行内容不正确{}", proxyCustomsMqBo.getFileBatchNo(), errorMessage);
            errorBuffer.append("第3行:").append(errorMessage);
            remitFileCheckResultBo.setErrorMsg(errorBuffer);
            remitFileCheckResultBo.setSuccessCount(NumberConstants.ZERO);
            remitFileCheckResultBo.setErrorCount(list.size() - Constants.NO_CONTENT_LINE);
            remitFileCheckResultBo.setTotalCount(list.size() - Constants.NO_CONTENT_LINE);
            return remitFileCheckResultBo;
        }
        if (proxyCustomsMqBo.getMemberId() != Long.parseLong(excelBaseValidationBo.getMemberId())) {
            errorMessage = "商户号填写不正确";
            errorBuffer.append("第3行:").append(errorMessage).append("\r\n");
            log.info("call excel批次：{},第三行内容不正确{}", proxyCustomsMqBo.getFileBatchNo(), errorMessage);
            remitFileCheckResultBo.setErrorMsg(errorBuffer);
            remitFileCheckResultBo.setSuccessCount(NumberConstants.ZERO);
            remitFileCheckResultBo.setErrorCount(list.size() - Constants.NO_CONTENT_LINE);
            remitFileCheckResultBo.setTotalCount(list.size() - Constants.NO_CONTENT_LINE);
            return remitFileCheckResultBo;
        }

        //文件总条数限制50万
        remitFileCheckResultBo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        if (list.size() - Constants.NO_CONTENT_LINE > Constants.EXCEL_CONTENT_MAX) {
            log.info("call excel批次：{},订单内容条数不能超过50万", proxyCustomsMqBo.getFileBatchNo());
            errorBuffer.append("上传文件订单内容条数不能超过50万");
            remitFileCheckResultBo.setErrorMsg(errorBuffer);
            remitFileCheckResultBo.setSuccessCount(NumberConstants.ZERO);
            remitFileCheckResultBo.setErrorCount(list.size() - Constants.NO_CONTENT_LINE);
            remitFileCheckResultBo.setTotalCount(list.size() - Constants.NO_CONTENT_LINE);
            return remitFileCheckResultBo;
        }
        remitFileCheckResultBo.setCareerType(excelBaseValidationBo.getCareerType());

        return remitFileCheckResultBo;
    }

    /**
     * 上传错误信息
     *
     * @param errorMsg 错误信息
     */
    private CommandResDTO dealError(StringBuilder errorMsg) {
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");                   //文件名
        insertReqDTO.setOrgCode("CBPAY");                                           //机构编码
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);                               //文件组
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));       //文件日期
        insertReqDTO.setRemark("代理跨境结算服务贸易文件校验错误信息");            //备注信息
        CommandResDTO commandResDTO = DfsClient.upload(errorMsg.toString().getBytes(), insertReqDTO);
        log.info("call 上传错误明细文件响应信息:{}", commandResDTO);
        return commandResDTO;
    }

    /**
     * 更新文件批次
     *
     * @param resultBo      校验结果
     * @param batchNo       文件批次
     * @param commandResDTO dfs文件信息
     */
    private FiCbPayFileUploadDo updateFileBatch(RemitFileCheckResultBo resultBo, Long batchNo,
                                                CommandResDTO commandResDTO) {
        //更新批次文件错误信息DFSFileId
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(batchNo);
        fiCbpayFileUploadDo.setErrorFileId(commandResDTO.getFileId());
        fiCbpayFileUploadDo.setStatus(UploadFileStatus.ERROR.getCode());
        fiCbpayFileUploadDo.setFailCount(resultBo.getErrorCount());
        fiCbpayFileUploadDo.setSuccessCount(resultBo.getSuccessCount());
        proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
        return fiCbpayFileUploadDo;
    }

    /**
     * 错误异步通知
     *
     * @param fileUploadDo 文件批次号
     * @param resDTO       文件dfs信息
     * @param mqBo         mq信息
     */
    private void upLoadFileNotfiy(FiCbPayFileUploadDo fileUploadDo, CommandResDTO resDTO, ProxyCustomsMqBo mqBo) {

        //上传文件
        try {
            String fileName = Constants.REMIT_ERROR_PREFIX + mqBo.getMemberId() + "_" + resDTO.getFileId() + "_"
                    + DateUtil.getCurrent() + ".txt";
            settleNotifyMemberBiz.uploadMemberFtp(mqBo.getMemberId(), resDTO.getFileId(), fileName);
            notfiyMember(fileUploadDo, fileName, mqBo);
        } catch (Exception e) {
            log.error("call 上传文件到商户ftp异常：{}", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00166);
        }
    }

    /**
     * 异步通知
     *
     * @param fileUploadDo 文件批次号
     * @param fileName     文件名称
     * @param mqBo         mq信息
     */
    private void notfiyMember(FiCbPayFileUploadDo fileUploadDo, String fileName, ProxyCustomsMqBo mqBo) {
        FiCbPayMemberApiRqstDo rqstDo = apiRqstMapper.selectByKey(fileUploadDo.getFileBatchNo());
        RemitFileNotfiyBo remitFileNotfiyBo = new RemitFileNotfiyBo();
        remitFileNotfiyBo.setMemberId(String.valueOf(mqBo.getMemberId()));
        remitFileNotfiyBo.setTerminalId(String.valueOf(rqstDo.getTerminalId()));
        remitFileNotfiyBo.setMemberReqId(rqstDo.getMemberReqId());
        remitFileNotfiyBo.setFileBatchNo(fileUploadDo.getFileBatchNo());
        remitFileNotfiyBo.setRecordCount(fileUploadDo.getRecordCount());
        remitFileNotfiyBo.setTotalAmount(fileUploadDo.getTotalAmount());
        remitFileNotfiyBo.setSuccessCount(fileUploadDo.getSuccessCount());
        remitFileNotfiyBo.setFailCount(fileUploadDo.getFailCount());
        remitFileNotfiyBo.setStatus(fileUploadDo.getStatus());
        remitFileNotfiyBo.setErrorFileName(fileName);
        log.info("call 上传文件异步通知参数：{}", remitFileNotfiyBo);
        notifyBiz.notifyMember(String.valueOf(mqBo.getMemberId()), String.valueOf(rqstDo.getTerminalId()),
                String.valueOf(rqstDo.getNotifyUrl()), remitFileNotfiyBo, rqstDo.getMemberReqId(),
                RemitBusinessTypeEnum.REMIT_FILE_UPLOAD.getCode());
    }
}
