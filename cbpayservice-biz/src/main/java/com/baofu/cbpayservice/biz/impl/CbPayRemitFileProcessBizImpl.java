package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayRemitFileProcessBiz;
import com.baofu.cbpayservice.biz.FileProcessBiz;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.baofu.cbpayservice.manager.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨境汇款文件处理BizImpl
 * <p/>
 * User: lian zd Date:2017/10/26 ProjectName: cbpayservice Version:1.0
 */
@Service
@Slf4j
public class CbPayRemitFileProcessBizImpl implements CbPayRemitFileProcessBiz {

    /**
     * 汇款文件处理服务
     */
    @Autowired
    private FileProcessBiz fileProcessBiz;

    /**
     * 代理上报manager服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 跨境汇款文件校验和保存入库
     * 将文件校验消费方法
     *
     * @param proxyCustomsMqBo 代理跨境结算mq消息内容对象
     */
    @Override
    public void remitFileProcess(ProxyCustomsMqBo proxyCustomsMqBo) {
        try {

            //获取excel文件流
            List<Object[]> list = ProxyCustomConvert.getCommandResDTO(proxyCustomsMqBo);
            fileProcessBiz.process(proxyCustomsMqBo, list);
        } catch (Exception e) {
            try {
                //更新批次文件错误信息DFSFileId
                FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
                fiCbpayFileUploadDo.setFileBatchNo(proxyCustomsMqBo.getFileBatchNo());
                fiCbpayFileUploadDo.setStatus(UploadFileStatus.NO_PASS.getCode());
                proxyCustomsManager.updateFilestatus(fiCbpayFileUploadDo);
                log.error("代理报关汇款文件解析处理失败 :{}", e);
            } catch (Exception exception) {
                log.error("更新文件批次失败:{}", exception);
            }
        } finally {
            redisManager.insertObject(1.00, proxyCustomsMqBo.getFileBatchNo().toString(), Constants.FILE_CHECK_SCHEDULE_TIME_OUT);
        }
    }
}
