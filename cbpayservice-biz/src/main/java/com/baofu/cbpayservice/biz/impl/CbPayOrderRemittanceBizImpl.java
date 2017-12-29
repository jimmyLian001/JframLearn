package com.baofu.cbpayservice.biz.impl;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofu.cbpayservice.biz.CbPayOrderRemitCheckBiz;
import com.baofu.cbpayservice.biz.CbPayOrderRemittanceBiz;
import com.baofu.cbpayservice.biz.convert.CbPayOrderRemitBizConvert;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.CbPaySumFileMqBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.CareerTypeEnum;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderSumDo;
import com.baofu.cbpayservice.manager.CbPayOrderRemittanceManage;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单汇款(非文件上传订单)
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderRemittanceBizImpl implements CbPayOrderRemittanceBiz {

    /**
     * 提现订单操作服务
     */
    @Autowired
    private CbPayOrderRemittanceManage cbPayOrderRemittanceManage;

    /**
     * 代理报关manager层服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 提现文件校验服务
     */
    @Autowired
    private CbPayOrderRemitCheckBiz cbPayOrderRemitCheckBiz;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 提现订单文件处理
     * 1、先校验文件内容
     * 2、更新订单确认状态为已确认
     * 3、汇总订单信息，更新文件批次表
     *
     * @param proxyCustomsMqBo mq文件处理对象
     */
    @Override
    @Transactional
    public void fileProcess(ProxyCustomsMqBo proxyCustomsMqBo) {

        Boolean lockFlag = Boolean.FALSE;
        log.info("call 提现订单文件处理入参 ——> {} ", proxyCustomsMqBo);

        try {
            // 判定商户是否锁住
            lockFlag = isLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + proxyCustomsMqBo.getMemberId());
            log.info("商户号：{}，是否锁住：{}", proxyCustomsMqBo.getMemberId(), lockFlag);
            if (lockFlag) {
                log.info("商户{}已锁住，暂不支持创建汇款订单！", proxyCustomsMqBo.getMemberId());
                return;
            }
            lockFlag = lock(Constants.CBPAY_CREATE_SUM_FILE_KEY + proxyCustomsMqBo.getMemberId());
            if (!lockFlag) {
                log.info("商户{}锁住失败，暂不支持创建汇款订单！", proxyCustomsMqBo.getMemberId());
                return;
            }
            log.info("商户号：{}，锁定成功！", proxyCustomsMqBo.getMemberId());

            //获取excel文件流
            List<Object[]> list = CbPayOrderRemitBizConvert.getCommandResDTO(proxyCustomsMqBo);

            //文件校验
            if (list == null || list.size() < 2) {
                dealError(proxyCustomsMqBo.getFileBatchNo(), "文件内容不能为空", UploadFileStatus.ERROR.getCode()
                        , BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue());
                return;
            }

            if (list.size() > Constants.EXCEL_CONTENT_MAX + 1) {
                dealError(proxyCustomsMqBo.getFileBatchNo(), "文件内容不能超过50万条", UploadFileStatus.ERROR.getCode()
                        , BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue());
                return;
            }

            StringBuffer errorBuffer = new StringBuffer();
            Map<Integer, StringBuffer> errorMap = cbPayOrderRemitCheckBiz.check(list, proxyCustomsMqBo);
            if (errorMap != null && errorMap.size() > 0) {
                for (Integer i : errorMap.keySet()) {
                    errorBuffer.append(errorMap.get(i));
                }
                dealError(proxyCustomsMqBo.getFileBatchNo(), errorBuffer.toString(), UploadFileStatus.ERROR.getCode()
                        , list.size() - errorMap.size() - 1, errorMap.size(), list.size() - 1);
                return;
            }

            //更新订单状态为确认
            List<Long> orderIdList = Lists.newArrayList();
            for (int i = 1; i < list.size(); i++) {
                orderIdList.add(Long.parseLong(String.valueOf(list.get(i)[0])));
                if (orderIdList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == list.size() - 1) {
                    cbPayOrderRemittanceManage.batchModifyCbPayOrder(proxyCustomsMqBo.getMemberId(), orderIdList,
                            proxyCustomsMqBo.getFileBatchNo());
                    orderIdList = Lists.newArrayList();
                }
            }

            //统计提现订单总金额
            BigDecimal totalAmount = cbPayOrderRemittanceManage.sumOrderTotalAmt(proxyCustomsMqBo.getFileBatchNo());
            //更新文件批次状态为成功
            FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
            fiCbpayFileUploadDo.setRecordCount(list.size() - 1);
            fiCbpayFileUploadDo.setFileBatchNo(proxyCustomsMqBo.getFileBatchNo());
            fiCbpayFileUploadDo.setStatus(UploadFileStatus.PASS.getCode());
            fiCbpayFileUploadDo.setSuccessCount(list.size() - 1);
            fiCbpayFileUploadDo.setFileBatchNo(proxyCustomsMqBo.getFileBatchNo());
            fiCbpayFileUploadDo.setOrderType("0");
            fiCbpayFileUploadDo.setTotalAmount(totalAmount);
            fiCbpayFileUploadDo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
            proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
        } catch (Exception e) {
            log.error("call 提现订单文件处理异常", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00133);
        } finally {
            if (lockFlag) {
                unLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + proxyCustomsMqBo.getMemberId());
                log.info("商户{}创建汇款订单结束，释放锁完成", proxyCustomsMqBo.getMemberId());
            }
        }
    }

    /**
     * 根据时间创建汇款订单
     *
     * @param cbPaySumFileMqBo 请求参数
     */
    @Override
    public void fileProcessByTime(CbPaySumFileMqBo cbPaySumFileMqBo) {
        Boolean lockFlag = Boolean.FALSE;
        try {
            log.info("根据时间更新订单状态：{}", cbPaySumFileMqBo);
            // 判定商户是否锁住
            lockFlag = isLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + cbPaySumFileMqBo.getMemberId());
            log.info("商户号：{}，是否锁住：{}", cbPaySumFileMqBo.getMemberId(), lockFlag);
            if (lockFlag) {
                log.info("商户{}已锁住，暂不支持创建汇款订单！", cbPaySumFileMqBo.getMemberId());
                return;
            }
            lockFlag = lock(Constants.CBPAY_CREATE_SUM_FILE_KEY + cbPaySumFileMqBo.getMemberId());
            if (!lockFlag) {
                log.info("商户{}锁住失败，暂不支持创建汇款订单！", cbPaySumFileMqBo.getMemberId());
                return;
            }
            log.info("商户号：{}，锁定成功！", cbPaySumFileMqBo.getMemberId());

            FiCbPayOrderSumDo fiCbPayOrderSumDo = cbPayOrderRemittanceManage.queryOrderByTime(
                    cbPaySumFileMqBo.getBeginTime(), cbPaySumFileMqBo.getEndTime(), cbPaySumFileMqBo.getMemberId());
            log.info("查询结果：{}", fiCbPayOrderSumDo);

            if (fiCbPayOrderSumDo == null || fiCbPayOrderSumDo.getCount() == 0) {
                log.error("查询失败：{}", fiCbPayOrderSumDo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00134);
            }

            cbPayOrderRemittanceManage.updateCbPayOrderByTime(cbPaySumFileMqBo.getBeginTime(), cbPaySumFileMqBo.getEndTime(),
                    cbPaySumFileMqBo.getMemberId(), cbPaySumFileMqBo.getFileBatchNo(), cbPaySumFileMqBo.getCreateBy());
            log.info("更新跨境订单成功!");

            FiCbPayFileUploadDo fiCbpayFileUploadDo = ProxyCustomConvert.uploadParamConvert(cbPaySumFileMqBo.getFileBatchNo(),
                    UploadFileStatus.PASS.getCode(), fiCbPayOrderSumDo.getCount(), "0", fiCbPayOrderSumDo.getTransMoney());
            log.info("更新文件上传参数：{}", fiCbpayFileUploadDo);
            proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
            log.info("更新文件上传状态成功!");
        } catch (Exception e) {
            log.error("根据时间更新数据失败", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00135);
        } finally {
            if (lockFlag) {
                unLock(Constants.CBPAY_CREATE_SUM_FILE_KEY + cbPaySumFileMqBo.getMemberId());
                log.info("商户{}创建汇款订单结束，释放锁完成", cbPaySumFileMqBo.getMemberId());
            }
        }
    }

    /**
     * excel 内容错误处理
     *
     * @param batchNo    文件批次
     * @param error      错误内容
     * @param fileStatus 文件状态
     */
    private void dealError(Long batchNo, String error, String fileStatus, Integer successCount,
                           Integer failCount, Integer recordCount) {
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");//文件名
        insertReqDTO.setOrgCode("CBPAY");//机构编码
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);//文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));//文件日期
        insertReqDTO.setRemark("代理跨境结算服务贸易文件校验错误信息");//备注信息
        CommandResDTO commandResDTO = DfsClient.upload(error.getBytes(), insertReqDTO);
        log.info("call 上传错误明细文件响应信息:{}", commandResDTO);

        //更新批次文件错误信息DFSFileId
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(batchNo);
        fiCbpayFileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
        fiCbpayFileUploadDo.setErrorFileId(commandResDTO.getFileId());
        fiCbpayFileUploadDo.setStatus(fileStatus);
        fiCbpayFileUploadDo.setFailCount(failCount);
        fiCbpayFileUploadDo.setSuccessCount(successCount);
        fiCbpayFileUploadDo.setRecordCount(recordCount);
        proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
    }

    private boolean isLock(String key) {
        String value = redisManager.queryObjectByKey(key);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean lock(String key) {
        Boolean lockFlag = redisManager.lockRedis(key, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
        if (!lockFlag) {
            log.info("key：{},锁住失败，无法创建汇款订单，请稍后重试", key);
            return Boolean.FALSE;
        }
        log.info("key：{}锁定成功", key);
        return Boolean.TRUE;
    }

    private void unLock(String key) {
        redisManager.deleteObject(key);
    }
}