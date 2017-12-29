package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.FiCbpayMemberFtpBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.util.other.SecurityUtil;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import com.baofu.cbpayservice.facade.models.FiCbpayMemberFtpDto;

/**
 * 商户ftp信息转换
 * <p>
 * User: 康志光 Date:2017/7/21 ProjectName: cbpayservice Version: 1.0
 */
public class FiCbpayMemberFtpConvert {

    public static FiCbpayMemberFtpBo toFiCbpayMemberFtpBo(FiCbpayMemberFtpDto fiCbpayMemberFtpDto) throws Exception {
        if (fiCbpayMemberFtpDto == null) {
            return null;
        }
        FiCbpayMemberFtpBo fiCbpayMemberFtpBo = new FiCbpayMemberFtpBo();
        fiCbpayMemberFtpBo.setRecordId(fiCbpayMemberFtpDto.getRecordId());
        fiCbpayMemberFtpBo.setMemberId(fiCbpayMemberFtpDto.getMemberId());
        fiCbpayMemberFtpBo.setFtpIp(fiCbpayMemberFtpDto.getFtpIp());
        fiCbpayMemberFtpBo.setFtpPort(fiCbpayMemberFtpDto.getFtpPort());
        fiCbpayMemberFtpBo.setUserName(fiCbpayMemberFtpDto.getUserName());
        fiCbpayMemberFtpBo.setUserPwd(fiCbpayMemberFtpDto.getUserPwd());
        fiCbpayMemberFtpBo.setFtpPath(fiCbpayMemberFtpDto.getFtpPath());
        fiCbpayMemberFtpBo.setFtpMode(fiCbpayMemberFtpDto.getFtpMode());
        return fiCbpayMemberFtpBo;
    }

    public static FiCbpayMemberFtpDo toFiCbpayMemberFtpDo(FiCbpayMemberFtpDto fiCbpayMemberFtpDto) throws Exception {
        if (fiCbpayMemberFtpDto == null) {
            return null;
        }
        FiCbpayMemberFtpDo fiCbpayMemberFtpDo = new FiCbpayMemberFtpDo();
        fiCbpayMemberFtpDo.setRecordId(fiCbpayMemberFtpDto.getRecordId());
        fiCbpayMemberFtpDo.setMemberId(fiCbpayMemberFtpDto.getMemberId());
        fiCbpayMemberFtpDo.setFtpIp(fiCbpayMemberFtpDto.getFtpIp());
        fiCbpayMemberFtpDo.setFtpPort(fiCbpayMemberFtpDto.getFtpPort());
        fiCbpayMemberFtpDo.setUserName(fiCbpayMemberFtpDto.getUserName());
        fiCbpayMemberFtpDo.setUserPwd(SecurityUtil.desEncrypt(fiCbpayMemberFtpDto.getUserPwd(), Constants.DES_PASSWD));
        fiCbpayMemberFtpDo.setFtpPath(fiCbpayMemberFtpDto.getFtpPath());
        fiCbpayMemberFtpDo.setFtpMode(fiCbpayMemberFtpDto.getFtpMode());
        return fiCbpayMemberFtpDo;
    }

    public static FiCbpayMemberFtpDto toFiCbpayMemberFtpDto(FiCbpayMemberFtpDo fiCbpayMemberFtpDo) throws Exception {
        if (fiCbpayMemberFtpDo == null) {
            return null;
        }
        FiCbpayMemberFtpDto fiCbpayMemberFtpDto = new FiCbpayMemberFtpDto();
        fiCbpayMemberFtpDto.setRecordId(fiCbpayMemberFtpDo.getRecordId());
        fiCbpayMemberFtpDto.setMemberId(fiCbpayMemberFtpDo.getMemberId());
        fiCbpayMemberFtpDto.setFtpIp(fiCbpayMemberFtpDo.getFtpIp());
        fiCbpayMemberFtpDto.setFtpPort(fiCbpayMemberFtpDo.getFtpPort());
        fiCbpayMemberFtpDto.setUserName(fiCbpayMemberFtpDo.getUserName());
        fiCbpayMemberFtpDto.setUserPwd(fiCbpayMemberFtpDo.getUserPwd());
        fiCbpayMemberFtpDto.setFtpPath(fiCbpayMemberFtpDo.getFtpPath());
        fiCbpayMemberFtpDto.setFtpMode(fiCbpayMemberFtpDo.getFtpMode());
        return fiCbpayMemberFtpDto;
    }

}
