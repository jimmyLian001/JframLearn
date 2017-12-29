package com.baofu.cbpayservice.biz.impl;

import com.baofoo.Response;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.cbpayservice.biz.AmlEmailBiz;
import com.baofu.cbpayservice.biz.CbPayAmlReturnBiz;
import com.baofu.cbpayservice.biz.convert.DfsParamConvert;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.AmlAuditStatus;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDetailV2Do;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayOrderManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/8/8 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayAmlReturnBizImpl implements CbPayAmlReturnBiz {

    /**
     * 订单操作服务
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 发洗钱流程发送邮件通知
     */
    @Autowired
    private AmlEmailBiz amlEmailBiz;

    /**
     * 反洗钱全部成功处理
     *
     * @param fileUploadDoList    文件批次信息集合
     * @param fiCbPayRemittanceDo 汇款批次信息集合
     * @param updateBy            更新人
     */
    @Override
    public void amlSuccess(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo, String updateBy) {

        log.info("call 反洗钱审核全部成功,文件批次号：{}", fileUploadDoList);
        for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fileUploadDoList) {

            //查询成功和初始的跨境订单
            List<FiCbPayRemittanceDetailV2Do> fiCbPayRemittanceDetailDo = cbPayOrderManager.queryRemittanceDetail(null,
                    fiCbPayFileUploadDo.getFileBatchNo());

            log.info("call 查询结果大小：{}", fiCbPayRemittanceDetailDo.size());
            BigDecimal amlAmount = getAmlCNYAmount(fiCbPayRemittanceDetailDo);

            if (fiCbPayFileUploadDo.getRecordCount() == fiCbPayRemittanceDetailDo.size()) {
                updateOrder(AmlAuditStatus.SUCCESS.getCode(), updateBy, fiCbPayFileUploadDo.getFileBatchNo());
            }
            //更新文件批次信息
            updateUploadFile(fiCbPayFileUploadDo.getFileBatchNo(), null, amlAmount, updateBy);
        }
    }

    /**
     * 反洗钱全部失败处理
     *
     * @param fileUploadDoList    文件批次信息集合
     * @param fiCbPayRemittanceDo 汇款批次信息集合
     * @param updateBy            更新人
     */
    public void amlFail(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo, String updateBy) {
        log.info("反洗钱审核全部失败：{}", fileUploadDoList);
        for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fileUploadDoList) {

            //更新文件批次信息
            FiCbPayFileUploadDo fileUploadDo = new FiCbPayFileUploadDo();
            fileUploadDo.setUpdateBy(updateBy);
            fileUploadDo.setAmlAmount(BigDecimal.ZERO);
            fileUploadDo.setSuccessCount(BigDecimal.ZERO.intValue());
            fileUploadDo.setStatus(UploadFileStatus.AML_FAIL.getCode());
            fileUploadDo.setFailCount(fiCbPayFileUploadDo.getSuccessCount());
            fileUploadDo.setFileBatchNo(fiCbPayFileUploadDo.getFileBatchNo());
            proxyCustomsManager.updateFilestatus(fileUploadDo);

            // 更新订单表 审核不通过
            updateOrder(AmlAuditStatus.FAIL.getCode(), updateBy, fiCbPayFileUploadDo.getFileBatchNo());
        }
        //邮件通知商户
        amlEmailBiz.amlFailToMemberEmail(fiCbPayRemittanceDo);
        //邮件通知业务人员
        amlEmailBiz.amlFailToBusinessEmail(fiCbPayRemittanceDo);
    }

    /**
     * 反洗钱部分成功处理
     *
     * @param fileUploadDoList       文件批次信息集合
     * @param fiCbPayRemittanceDo    汇款批次信息集合
     * @param cgwRemitBatchResultDto 渠道返回信息
     * @param updateBy               更新人
     */
    public void portionSuccess(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo,
                               CgwRemitResultDto cgwRemitBatchResultDto, String updateBy) {
        log.info("call 反洗钱审核部分成功，文件批次号：{}", fileUploadDoList);
        String detail = downloadFailDetail(cgwRemitBatchResultDto.getFileId());
        if (StringUtil.isBlank(detail)) {
            log.error("call 错误明细为空,fileId ：{}", cgwRemitBatchResultDto.getFileId());
            return;
        }
        //更新订单表
        for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fileUploadDoList) {
            //文件批次反洗钱状态为部分和和全部成功无需更新跨境订单状态,订单状态更新为审核通过
            if (!UploadFileStatus.AML_PART_SUCCESS.getCode().equals(fiCbPayFileUploadDo.getStatus())
                    || !UploadFileStatus.AML_SUCCESS.getCode().equals(fiCbPayFileUploadDo.getStatus())) {
                updateOrder(AmlAuditStatus.SUCCESS.getCode(), updateBy, fiCbPayFileUploadDo.getFileBatchNo());
            }
        }
        // 更新订单表,审核不通过
        updateFailOrder(detail, updateBy);
        //反洗钱成功的外币金额
        for (FiCbPayFileUploadDo fiCbPayFileUploadDo : fileUploadDoList) {

            //查询反洗钱成功的跨境订单
            List<FiCbPayRemittanceDetailV2Do> successDetailDoList = cbPayOrderManager.queryAmlDetailByBatchFileNo(
                    fiCbPayFileUploadDo.getFileBatchNo(), AmlAuditStatus.SUCCESS.getCode());
            log.info("call 查询文件批次：{}，反洗钱成功跨境订单大小：{}", fiCbPayFileUploadDo.getFileBatchNo(), successDetailDoList.size());

            //查询反洗钱失败的跨境订单
            List<FiCbPayRemittanceDetailV2Do> failDetailDoList = cbPayOrderManager.queryAmlDetailByBatchFileNo(
                    fiCbPayFileUploadDo.getFileBatchNo(), AmlAuditStatus.FAIL.getCode());
            log.info("call 查询文件批次：{}，反洗钱失败跨境订单大小：{}", fiCbPayFileUploadDo.getFileBatchNo(), failDetailDoList.size());

            //人民币成功金额
            BigDecimal successAmount = getAmlCNYAmount(successDetailDoList);
            //人民币失败金额处理
            BigDecimal failAmount = getAmlCNYAmount(failDetailDoList);
            log.info("call 查询人民币失败金额：{}", failAmount);

            //判断文件反洗钱状态
            String uploadFileStatus = null;
            if (failDetailDoList.size() > 0 && fiCbPayFileUploadDo.getRecordCount() > failDetailDoList.size()) {
                //部分成功
                uploadFileStatus = UploadFileStatus.AML_PART_SUCCESS.getCode();
            } else if (failDetailDoList.size() > 0 && fiCbPayFileUploadDo.getRecordCount() == failDetailDoList.size()) {
                //全部失败
                uploadFileStatus = UploadFileStatus.AML_FAIL.getCode();
            } else if (failDetailDoList.size() == 0) {
                //全部成功
                uploadFileStatus = UploadFileStatus.AML_SUCCESS.getCode();
            }

            //更新文件批次信息
            FiCbPayFileUploadDo fileUploadDo = new FiCbPayFileUploadDo();
            fileUploadDo.setUpdateBy(updateBy);
            fileUploadDo.setStatus(uploadFileStatus);
            //反洗钱成功金额
            fileUploadDo.setAmlAmount(successAmount);
            //反洗钱失败笔数
            fileUploadDo.setFailCount(failDetailDoList.size());
            //反洗钱成功笔数
            fileUploadDo.setSuccessCount(successDetailDoList.size());
            fileUploadDo.setFileBatchNo(fiCbPayFileUploadDo.getFileBatchNo());
            proxyCustomsManager.updateFilestatus(fileUploadDo);

        }
        //邮件通知商户
        amlEmailBiz.amlPortionSuccessToMemberEmail(fiCbPayRemittanceDo);
        //邮件通知业务人员
        amlEmailBiz.amlPortionSuccessToBusinessEmail(fiCbPayRemittanceDo);
    }

    /**
     * 部分成功，批量更新订单表,审核不通过
     *
     * @param detail   审核不通过信息
     * @param updateBy 更新人
     * @return 反洗钱不通过订单集合
     */
    private List<Long> updateFailOrder(String detail, String updateBy) {
        //反洗钱不通过的跨境订单ID集合
        String[] orderIdList = detail.split("\\|");
        log.info("call 反洗钱不通过的订单大小：{}", orderIdList.length);
        List<Long> fiCbPayOrderDos = new ArrayList<>();
        List<Long> orderIds = new ArrayList<>();
        log.info("call 反洗钱未通过的跨境订单Id:{}", orderIdList);
        for (String orderId : orderIdList) {
            fiCbPayOrderDos.add(Long.parseLong(orderId));
            orderIds.add(Long.parseLong(orderId));
            if (fiCbPayOrderDos.size() == Constants.BATCH_DEAL_NUM) {
                log.info("更新订单：{}", Constants.BATCH_DEAL_NUM);
                cbPayOrderManager.batchModifyCbPayOrder(null, updateBy, fiCbPayOrderDos,
                        null, AmlAuditStatus.FAIL.getCode());
                fiCbPayOrderDos.clear();
            }
        }
        if (fiCbPayOrderDos.size() > 0) {
            log.info("剩余跨境订单更新完成:{}", fiCbPayOrderDos.size());
            cbPayOrderManager.batchModifyCbPayOrder(null, updateBy, fiCbPayOrderDos,
                    null, AmlAuditStatus.FAIL.getCode());
        }
        return orderIds;
    }

    /**
     * 计算反洗钱外币金额
     *
     * @param fiCbPayRemittanceDetailDo 跨境人民币汇款失败明细信息集合
     * @return 反洗钱金额
     */
    private BigDecimal getAmlCNYAmount(List<FiCbPayRemittanceDetailV2Do> fiCbPayRemittanceDetailDo) {

        BigDecimal amlAmount = BigDecimal.ZERO;
        if (fiCbPayRemittanceDetailDo != null && fiCbPayRemittanceDetailDo.size() > 0) {
            for (FiCbPayRemittanceDetailV2Do detailDo : fiCbPayRemittanceDetailDo) {
                amlAmount = amlAmount.add(detailDo.getTransMoney());
            }
        }
        return amlAmount;
    }

    /**
     * 下载反洗钱不通过错误文件
     *
     * @param fileId DFS的文件ID
     * @return 错误信息
     */
    private String downloadFailDetail(Long fileId) {
        QueryReqDTO reqDTO = DfsParamConvert.queryParamConvert(fileId);
        log.info("DFS文件请求参数:{}", reqDTO);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        log.info("DFS文件响应信息:{}", resDTO);
        byte[] detailBytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
        return new String(detailBytes);
    }

    /**
     * 更新跨境订单信息
     *
     * @param amlStatus   反洗钱状态
     * @param updateBy    更新人
     * @param batchFileId 文件批次
     */
    private void updateOrder(Integer amlStatus, String updateBy, Long batchFileId) {
        try {
            cbPayOrderManager.updateCbPayOrderByFileBatchId(amlStatus, updateBy, batchFileId);
        } catch (Exception e) {
            log.error("更新跨境订单状态异常", e);
        }
    }

    /**
     * 更新文件信息
     *
     * @param fileBatchId 文件批次ID
     * @param status      状态
     * @param amlAmount   反洗钱总金额
     * @param updateBy    更新人
     */
    private void updateUploadFile(Long fileBatchId, String status, BigDecimal amlAmount, String updateBy) {
        // 更新订单表
        try {
            FiCbPayFileUploadDo fiCbPayFileUploadDo = ProxyCustomConvert.uploadFileUpdateParamConvert(fileBatchId, status,
                    amlAmount, null, updateBy);
            log.info("更新文件批次信息：{}", fiCbPayFileUploadDo);
            proxyCustomsManager.updateFilestatus(fiCbPayFileUploadDo);
        } catch (Exception e) {
            log.error("更新跨境订单状态异常", e);
        }
    }
}
