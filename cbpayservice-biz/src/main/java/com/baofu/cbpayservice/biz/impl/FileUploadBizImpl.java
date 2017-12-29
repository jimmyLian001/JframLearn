package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.FileUploadBiz;
import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.biz.convert.FileUploadBizConvert;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.biz.models.RemitFileUploadBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayMemberApiRqstMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 上传文件服务
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class FileUploadBizImpl implements FileUploadBiz {

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 生成订单
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 商户接口请求
     */
    @Autowired
    private FiCbPayMemberApiRqstMapper rqstMapper;

    /**
     * 汇款上传文件
     *
     * @param uploadBo 上传文件信息
     * @return 文件批次号
     */
    @Override
    public Long remitFileUpload(RemitFileUploadBo uploadBo) {

        FiCbPayMemberApiRqstDo rqstDo = rqstMapper.queryByMIdReqId(uploadBo.getMemberId(), uploadBo.getMemberReqId(),
                RemitBusinessTypeEnum.REMIT_FILE_UPLOAD.getCode());
        if (rqstDo != null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00204);
        }

        //创建文件批次
        Long fileBatchNo = orderIdManager.orderIdCreate();
        proxyCustomsBiz.insertFileUpload(FileUploadBizConvert.toFiCbPayFileUploadBo(uploadBo, fileBatchNo));
        rqstMapper.insert(FileUploadBizConvert.toFiCbPayMemberApiRqstDo(uploadBo, fileBatchNo));

        //发送文件处理MQ
        ProxyCustomsMqBo proxyCustomsMqBo = FileUploadBizConvert.toProxyCustomsMqBo(fileBatchNo, uploadBo);
        log.info("call 汇款API上传文件MQ，生产者，消息队列名称：{},内容为：{}",
                MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);
        return fileBatchNo;
    }
}
