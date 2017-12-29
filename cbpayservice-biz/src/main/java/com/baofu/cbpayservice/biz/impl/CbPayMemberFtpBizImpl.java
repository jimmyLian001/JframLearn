package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayMemberFtpBiz;
import com.baofu.cbpayservice.biz.convert.FiCbpayMemberFtpBoConvert;
import com.baofu.cbpayservice.biz.models.FiCbpayMemberFtpBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import com.baofu.cbpayservice.manager.FiCbpayMemberFtpManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/7/24 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayMemberFtpBizImpl implements CbPayMemberFtpBiz {

    /**
     * orderId服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 商户FTP服务
     */
    @Autowired
    private FiCbpayMemberFtpManager fiCbpayMemberFtpManager;

    /**
     * 新增商户FTP
     *
     * @param fiCbpayMemberFtpBo FTP信息
     * @return
     */
    @Override
    public Long create(FiCbpayMemberFtpBo fiCbpayMemberFtpBo) {

        FiCbpayMemberFtpDo fiCbpayMemberFtpDo = fiCbpayMemberFtpManager.searchByMemberId(fiCbpayMemberFtpBo.getMemberId());
        if (fiCbpayMemberFtpDo != null) {
            log.info("商户FTP已经存在");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00165);
        }

        try {
            Long recordId = orderIdManager.orderIdCreate();
            fiCbpayMemberFtpDo = FiCbpayMemberFtpBoConvert.toFiCbpayMemberFtpDo(fiCbpayMemberFtpBo, recordId);
            fiCbpayMemberFtpManager.create(fiCbpayMemberFtpDo);
            log.info("创建FTP信息成功 recordId：{}", recordId);
            return recordId;
        } catch (Exception e) {
            log.error("创建商户FTP失败：{}", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00164);
        }

    }
}
