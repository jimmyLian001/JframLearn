package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.FiCbpayMemberFtpBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.util.other.SecurityUtil;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/7/24 ProjectName: cbpay-customs-service Version: 1.0
 */
public final class FiCbpayMemberFtpBoConvert {

    private FiCbpayMemberFtpBoConvert() {

    }

    /**
     * @param fiCbpayMemberFtpBo 商户ftp信息
     * @param recordId           记录编号
     * @return FiCbpayMemberFtpDo
     * @throws Exception
     */
    public static FiCbpayMemberFtpDo toFiCbpayMemberFtpDo(FiCbpayMemberFtpBo fiCbpayMemberFtpBo, Long recordId) throws Exception {
        if (fiCbpayMemberFtpBo == null) {
            return null;
        }
        FiCbpayMemberFtpDo fiCbpayMemberFtpDo = new FiCbpayMemberFtpDo();
        fiCbpayMemberFtpDo.setRecordId(recordId);
        fiCbpayMemberFtpDo.setMemberId(fiCbpayMemberFtpBo.getMemberId());
        fiCbpayMemberFtpDo.setFtpIp(fiCbpayMemberFtpBo.getFtpIp());
        fiCbpayMemberFtpDo.setFtpPort(fiCbpayMemberFtpBo.getFtpPort());
        fiCbpayMemberFtpDo.setUserName(fiCbpayMemberFtpBo.getUserName());
        fiCbpayMemberFtpDo.setUserPwd(SecurityUtil.desEncrypt(fiCbpayMemberFtpBo.getUserPwd(), Constants.DES_PASSWD));
        fiCbpayMemberFtpDo.setFtpPath(fiCbpayMemberFtpBo.getFtpPath());
        fiCbpayMemberFtpDo.setFtpMode(fiCbpayMemberFtpBo.getFtpMode());
        return fiCbpayMemberFtpDo;
    }

}
