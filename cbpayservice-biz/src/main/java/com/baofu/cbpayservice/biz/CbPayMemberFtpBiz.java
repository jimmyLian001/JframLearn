package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.FiCbpayMemberFtpBo;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/7/24 ProjectName: cbpay-customs-service Version: 1.0
 */
public interface CbPayMemberFtpBiz {

    /**
     * 新增商户FTP
     *
     * @param fiCbpayMemberFtpBo FTP信息
     * @return 记录编号
     */
    Long create(FiCbpayMemberFtpBo fiCbpayMemberFtpBo);

}
