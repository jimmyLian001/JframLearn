package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofu.cbpayservice.biz.CbPayAmlNotifyBiz;
import com.baofu.cbpayservice.biz.CbPayAmlReturnBiz;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人民币订单通知处理
 * <p>
 * 1、接收银行处理成功异步通知
 * </p>
 * User: wanght Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service("cbPayAmlNotifyBizImpl")
public class CbPayAmlNotifyBizImpl implements CbPayAmlNotifyBiz {

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 反洗钱处理
     */
    @Autowired
    private CbPayAmlReturnBiz cbPayAmlReturnBiz;

    /**
     * 接收银行处理成功异步通知
     */
    @Override
    public void amlApplySecondNotify(CgwRemitResultDto cgwRemitBatchResultDto, String updateBy) {

        log.info("call 渠道返回反洗钱结果：{}", cgwRemitBatchResultDto);
        try {
            //反洗钱文件批次集合
            List<FiCbPayFileUploadDo> fileUploadDoList = proxyCustomsManager.queryByBatchNo(cgwRemitBatchResultDto.getRemSerialNo());
            //汇款批次信息
            FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceByBatchNo(cgwRemitBatchResultDto.getRemSerialNo());


            // 判断文件批次是否存在
            log.info("call 查询文件批次信息：{}", fileUploadDoList);
            if (fileUploadDoList == null || fileUploadDoList.size() <= 0) {
                log.error("call 汇款批次号:{}不存在文件批次", fileUploadDoList);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00139);
            }

            // 全部成功
            if (cgwRemitBatchResultDto.getAmlState() == BaseResultEnum.SUCCESS.getCode()) {
                cbPayAmlReturnBiz.amlSuccess(fileUploadDoList, fiCbPayRemittanceDo, updateBy);
            }

            // 部分成功
            if (cgwRemitBatchResultDto.getAmlState() == BaseResultEnum.PORTION_SUCCESS.getCode()) {
                cbPayAmlReturnBiz.portionSuccess(fileUploadDoList, fiCbPayRemittanceDo, cgwRemitBatchResultDto, updateBy);
            }

            // 全部失败
            if (cgwRemitBatchResultDto.getAmlState() == BaseResultEnum.FAIL.getCode()) {
                cbPayAmlReturnBiz.amlFail(fileUploadDoList, fiCbPayRemittanceDo, updateBy);
            }

            // 异常
            if (cgwRemitBatchResultDto.getAmlState() == BaseResultEnum.UNCERTAIN.getCode()) {
                log.error("call 反洗钱审核响应异常：{}", fileUploadDoList);
            }
        } catch (Exception e) {
            log.error("反洗钱第二次异步通知异常", e);
        }
    }
}
