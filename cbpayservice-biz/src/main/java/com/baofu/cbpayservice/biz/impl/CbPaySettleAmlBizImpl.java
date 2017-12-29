package com.baofu.cbpayservice.biz.impl;

import com.baofoo.Response;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.cbpayservice.biz.CbPaySettleAmlBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettleConvert;
import com.baofu.cbpayservice.biz.freemarker.FreemarkTemplate;
import com.baofu.cbpayservice.biz.models.CbPaySettleOrderBo;
import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.SettleAmlStatus;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.common.util.JxlsUtils;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleOrderMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleOrderDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPaySettleManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 收结汇反洗钱处理Biz层相关操作
 * <p>
 * User: wanght Date:2017/05/22 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleAmlBizImpl implements CbPaySettleAmlBiz {

    /**
     * 结汇订单明细服务
     */
    @Autowired
    private FiCbPaySettleOrderMapper fiCbPaySettleOrderMapper;

    /**
     * 文件批次服务
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    /**
     * 结汇信息服务
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 获取商户信息
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 结汇明细下载文件
     */
    @Value("${settleOrder.down.filePath}")
    private String settleDownLoadPath;

    /**
     * 反洗钱部分未通过结汇明细上传文件
     */
    @Value("${settleAmlOrder.upload.filePath}")
    private String settleAmlUploadPath;

    /**
     * 反洗钱未通过结汇明细上传文件
     */
    @Value("${settleAmlOrder.upload.templatePath}")
    private String settleAmlTemplatePath;

    /**
     * 反洗钱处理
     */
    public void amlApplySecondNotify(CgwSettleResultDto cgwSettleResultRespDto) {

        log.error("call 结汇反洗钱开始处理参数：{}", cgwSettleResultRespDto);
        Long fileBatchNo = null;
        try {
            FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(Long.parseLong(cgwSettleResultRespDto.getRemSerialNo()));
            fileBatchNo = fiCbPaySettleDo.getFileBatchNo();
            FiCbPayFileUploadDo fiCbPayFileUploadDo = fiCbpayFileUploadMapper.queryByBatchId(fileBatchNo);
            if (fiCbPayFileUploadDo == null) {
                log.error("call 结汇文件批次号：{}该文件批次不存在", fileBatchNo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00139);
            }
            //响应处理
            doSettleResult(cgwSettleResultRespDto, fiCbPaySettleDo);

        } catch (Exception e) {
            log.error("call 处理{}结汇反洗钱审核响应异常：{}", fileBatchNo, e);
        }
    }

    /**
     * 反洗钱处理
     *
     * @param cgwSettleResultRespDto 渠道返回信息
     * @param fiCbPaySettleDo        收汇信息
     */
    private void doSettleResult(CgwSettleResultDto cgwSettleResultRespDto, FiCbPaySettleDo fiCbPaySettleDo) {

        Long fileBatchNo = fiCbPaySettleDo.getFileBatchNo();

        // 全部成功
        if (cgwSettleResultRespDto.getAmlState() == BaseResultEnum.SUCCESS.getCode()) {
            log.info("反洗钱审核全部成功：{}", fileBatchNo);

            //更新文件批次状态
            updateUploadFile(fileBatchNo, null, UploadFileStatus.AML_SUCCESS.getCode(), null);
            //更新实际结汇金额
            batchUpdateSettle(fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getIncomeAmt());
            batchUpdateSettleOrder(fileBatchNo, null, Boolean.TRUE);
        }

        // 部分成功
        if (cgwSettleResultRespDto.getAmlState() == BaseResultEnum.PORTION_SUCCESS.getCode()) {
            log.info("call 结汇反洗钱审核部分成功：{}", fileBatchNo);

            //全部更新成功
            batchUpdateSettleOrder(fileBatchNo, null, Boolean.TRUE);

            //处理失败部分的订单
            Long fileId = cgwSettleResultRespDto.getFileId();
            String detail = downloadFailDetail(fileId);
            if (StringUtil.isBlank(detail)) {
                log.error("call 结汇反洗钱错误明细为空,fileId ：{}", fileId);
                return;
            }
            // 明细
            String[] orderIdList = detail.split("\\|");
            log.info("call 结汇反洗钱错误订单大小：{}", orderIdList.length);
            //批量更新订单表,审核不通过
            List<Long> orderIds = new ArrayList<>();
            for (String orderId : orderIdList) {
                log.info("call 结汇反稀钱不通过的orderId:{}", orderId);
                orderIds.add(Long.parseLong(orderId));
            }
            batchUpdateSettleOrder(fileBatchNo, orderIds, Boolean.FALSE);

            //上传反洗钱未通过的明细
            Long errorFileId = uploadNoPassDataOfAml(fiCbPaySettleDo.getMemberId(), fileBatchNo, orderIds);
            //更新结汇文件批次为部分成功状态
            updateUploadFile(fileBatchNo, errorFileId, UploadFileStatus.AML_PART_SUCCESS.getCode(), null);
            //查询反洗钱不通过的订单
            List<FiCbPaySettleOrderDo> fiCbPaySettleOrderDos = fiCbPaySettleOrderMapper.selectByBatchNo(fileBatchNo, orderIds, SettleAmlStatus.NO_PASS.getCode());
            //反洗钱失败金额
            BigDecimal totalAmt = BigDecimal.ZERO;
            for (FiCbPaySettleOrderDo fiCbPaySettleOrderDo : fiCbPaySettleOrderDos) {
                totalAmt = totalAmt.add(fiCbPaySettleOrderDo.getOrderAmt());
            }
            batchUpdateSettle(fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getIncomeAmt().subtract(totalAmt));
        }

        // 全部失败0-全部失败 -1 - 异常
        if (cgwSettleResultRespDto.getAmlState() == BaseResultEnum.FAIL.getCode() ||
                cgwSettleResultRespDto.getAmlState() == BaseResultEnum.UNCERTAIN.getCode()) {
            log.info("call 结汇反洗钱审核全部失败：{}", fileBatchNo);
            updateUploadFile(fileBatchNo, null, UploadFileStatus.AML_FAIL.getCode(), null);
            batchUpdateSettleOrder(fileBatchNo, null, Boolean.FALSE);
        }

    }

    /**
     * 更新结汇信息真实汇入金额
     *
     * @param settleOrderId 结汇orderId
     * @param realIncomeAmt 真实汇入金额
     */
    private void batchUpdateSettle(Long settleOrderId, BigDecimal realIncomeAmt) {
        // 更新订单表
        try {
            //更新文件信息
            FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
            fiCbPaySettleDo.setOrderId(settleOrderId);
            fiCbPaySettleDo.setRealIncomeAmt(realIncomeAmt);
            cbPaySettleManager.modify(fiCbPaySettleDo);
        } catch (Exception e) {
            log.error("更新跨境结汇订单状态异常", e);
        }
    }

    /**
     * 更新文件批次反洗钱状态
     *
     * @param fileBatchId      文件批次
     * @param exSettleOrderIds 反洗钱状态
     */
    private void batchUpdateSettleOrder(Long fileBatchId, List<Long> exSettleOrderIds, Boolean isAllSuccess) {
        // 更新订单表
        try {

            if (!isAllSuccess && exSettleOrderIds != null && !exSettleOrderIds.isEmpty()) {
                fiCbPaySettleOrderMapper.batchUpdateByOrderId(fileBatchId, exSettleOrderIds, SettleAmlStatus.NO_PASS.getCode());
                fiCbPaySettleOrderMapper.batchUpdateByExOrderIds(fileBatchId, exSettleOrderIds, SettleAmlStatus.PASS.getCode());
            } else if (isAllSuccess) {
                fiCbPaySettleOrderMapper.batchUpdateByOrderId(fileBatchId, null, SettleAmlStatus.PASS.getCode());
            } else {
                fiCbPaySettleOrderMapper.batchUpdateByOrderId(fileBatchId, null, SettleAmlStatus.NO_PASS.getCode());
            }


        } catch (Exception e) {
            log.error("更新跨境结汇订单状态异常", e);
        }
    }

    /**
     * 下载反洗钱部分成功文件
     *
     * @param fileId 文件ID
     * @return 文件内容
     */
    private String downloadFailDetail(Long fileId) {
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(fileId);
        reqDTO.setOperation(Operation.QUERY);
        log.info("DFS文件请求参数:{}", reqDTO);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        log.info("DFS文件响应信息:{}", resDTO);
        byte[] detailBytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
        return new String(detailBytes);
    }


    /**
     * 更新文件批次表状态
     *
     * @param fileBatchId
     * @param status
     */
    private void updateUploadFile(Long fileBatchId, Long erroFileId, String status, String oldStatus) {
        // 更新订单表
        try {
            //更新文件信息
            FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
            fiCbpayFileUploadDo.setFileBatchNo(fileBatchId);
            fiCbpayFileUploadDo.setErrorFileId(erroFileId);
            fiCbpayFileUploadDo.setStatus(status);
            fiCbpayFileUploadDo.setOldStatus(oldStatus);
            fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbpayFileUploadDo);
        } catch (Exception e) {
            log.error("更新跨境结汇订单状态异常", e);
        }
    }

    /**
     * 上传反洗钱部分审核通过的数据
     *
     * @param memberId         商户号
     * @param fileBatchNo      文件批次号
     * @param exSettleOrderIds 排除的结汇订单号
     * @return
     */
    private Long uploadNoPassDataOfAml(Long memberId, Long fileBatchNo, List<Long> exSettleOrderIds) {

        FiCbPayFileUploadDo fiCbPayFileUploadDo = fiCbpayFileUploadMapper.queryByBatchId(fileBatchNo);
        CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(memberId.intValue());

        try {

            //生成未通过反洗钱明细
            List<CbPaySettleOrderBo> cbPaySettleOrderBos = productAmlDatas(fileBatchNo, fiCbPayFileUploadDo.getDfsFileId(), memberId, exSettleOrderIds);
            String fileName = fiCbPayFileUploadDo.getFileName().toLowerCase();
            String version = cbPaySettleOrderBos.get(0).getVersion() == null ? "" : cbPaySettleOrderBos.get(0).getVersion().trim();

            //生成反洗钱未通过明细信息
            File fileOut = productAmlExcel(fileBatchNo, version, fileName, cacheMemberDto, cbPaySettleOrderBos);

            InsertReqDTO insertReqDTO = new InsertReqDTO();
            insertReqDTO.setFileName(fileName);//文件名
            insertReqDTO.setOrgCode("CBPAY");//机构编码
            insertReqDTO.setFileGroup(FileGroup.PRODUCT);//文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
            insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));//文件日期
            insertReqDTO.setRemark("反洗钱结汇未审核通过订单明细文件信息");//备注信息
            log.info("DFS请求报文：{}", insertReqDTO);
            CommandResDTO resDTO = DfsClient.upload(fileOut, insertReqDTO);
            log.info("DFS请求响应：{}", resDTO);
            return resDTO.getFileId();
        } catch (Exception e) {
            log.error("上传反洗钱不通过明细异常:{}", e);
            return null;
        }
    }

    /**
     * 生成未通过反洗钱明细
     *
     * @param fileBatchNo
     * @param dfsFileId
     * @param memberId
     * @param exSettleOrderIds
     * @return
     */
    private List<CbPaySettleOrderBo> productAmlDatas(Long fileBatchNo, Long dfsFileId, Long memberId, List<Long> exSettleOrderIds) throws Exception {

        log.info("生成反洗钱明细参数:{}，{},{},{}", fileBatchNo, memberId, dfsFileId, exSettleOrderIds);

        //获取结汇明细订单
        List<FiCbPaySettleOrderDo> fiCbPaySettleOrderDos = fiCbPaySettleOrderMapper.selectByExOrderIds(fileBatchNo, null, SettleAmlStatus.PASS.getCode());
        List<CbPaySettleOrderBo> cbPaySettleOrderBos = Lists.newArrayList();
        for (FiCbPaySettleOrderDo fiCbPaySettleOrderDo : fiCbPaySettleOrderDos) {
            CbPaySettleOrderBo cbPaySettleOrderBo = new CbPaySettleOrderBo();
            cbPaySettleOrderBo.setMemberId(fiCbPaySettleOrderDo.getMemberId().toString());
            cbPaySettleOrderBo.setMemberTransId(fiCbPaySettleOrderDo.getMemberTransId());
            cbPaySettleOrderBos.add(cbPaySettleOrderBo);
        }

        log.info("反洗钱通过明细数目:{}", cbPaySettleOrderBos.size());

        //获取excel文件流
        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setDfsFileId(dfsFileId);
        CommandResDTO resDTO = CbPaySettleConvert.getCommandResDTO(settleFileUploadReqBo);

        List<Object[]> list = CbPaySettleConvert.getContext(resDTO, settleDownLoadPath + File.separator
                + fileBatchNo + "_" + resDTO.getFileName());
        List<CbPaySettleOrderBo> cbPaySettleOrderBos2 = Lists.newArrayList();
        for (int i = 4; i < list.size(); i++) {
            CbPaySettleOrderBo cbPaySettleOrderBo = CbPaySettleConvert.toSettleOrderBo(list.get(i));
            cbPaySettleOrderBo.setMemberId(memberId.toString());
            cbPaySettleOrderBo.setVersion(list.get(2)[2] == null ? "" : String.valueOf(list.get(2)[2]).trim());
            cbPaySettleOrderBos2.add(cbPaySettleOrderBo);
        }

        log.info("商户上传反洗钱明细数目:{}", cbPaySettleOrderBos2.size());

        //移除
        HashSet datas = Sets.newHashSet(cbPaySettleOrderBos2);
        datas.removeAll(cbPaySettleOrderBos);
        cbPaySettleOrderBos2.clear();
        cbPaySettleOrderBos2.addAll(datas);

        return cbPaySettleOrderBos2;
    }


    /**
     * 生成反洗钱未通过明细文件
     *
     * @param fileBatchNo         文件批次
     * @param version             版本
     * @param fileName            文件名称
     * @param cacheMemberDto      商户信息
     * @param cbPaySettleOrderBos 未通过反洗钱明细信息
     * @return 文件
     * @throws Exception
     */
    private File productAmlExcel(Long fileBatchNo, String version, String fileName, CacheMemberDto cacheMemberDto,
                                 List<CbPaySettleOrderBo> cbPaySettleOrderBos) throws Exception {

        Map<String, Object> params = Maps.newHashMap();
        params.put("settleOrders", cbPaySettleOrderBos);
        params.put("memberId", cacheMemberDto.getMemberId());
        params.put("memberName", cacheMemberDto.getName());
        params.put("version", version);

        //生成excel
        File fileOut;
        File template = null;
        String uploadPath = settleAmlUploadPath;
        String templatePath = settleAmlTemplatePath;
        if (fileName.endsWith("txt")) {
            template = new File(templatePath + "结汇明细交易模板.txt");
            uploadPath = uploadPath + fileBatchNo + "_结汇明细交易模板.txt";
            fileOut = new File(uploadPath);
            FreemarkTemplate.getInstance().exportToFile(params, templatePath, template.getName(), uploadPath);
        } else {
            if (fileName.endsWith("xls") && StringUtils.isBlank(version)) {
                template = new File(templatePath + "结汇明细交易模板.xls");
            } else if (fileName.endsWith("xlsx") && StringUtils.isBlank(version)) {
                template = new File(templatePath + "结汇明细交易模板.xlsx");
            } else if (fileName.endsWith("xls") && "1.1".equals(version)) {
                template = new File(templatePath + "结汇明细交易模板v1.1.xls");
            } else if (fileName.endsWith("xlsx") && "1.1".equals(version)) {
                template = new File(templatePath + "结汇明细交易模板v1.1.xlsx");
            }

            fileOut = new File(uploadPath + fileBatchNo + "_" + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
        }
        return fileOut;
    }
}
