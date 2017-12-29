package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.PayeeAccountInfoBo;
import com.baofu.cbpayservice.facade.models.res.PayeeAccountInfoRespDto;

/**
 * description:收款人账户信息 参数转换 service
 * <p/>
 * Created by liy on 2017/9/11 0011 ProjectName：cbp
 */
public class PayeeAccountInfoConvert {

    protected PayeeAccountInfoConvert() {
    }

    /**
     * 参数转换 Bo --》 RespDto
     *
     * @param payeeAccountInfoBo Bo
     * @return dto
     */
    public static PayeeAccountInfoRespDto toPayeeAccountInfoDto(PayeeAccountInfoBo payeeAccountInfoBo) {

        PayeeAccountInfoRespDto respDto = new PayeeAccountInfoRespDto();
        respDto.setBankAccNo(payeeAccountInfoBo.getBankAccNo());
        respDto.setBankName("Silvergate Bank");
        respDto.setRoutingNumber(payeeAccountInfoBo.getRoutingNumber());
        respDto.setPayeeName(payeeAccountInfoBo.getPayeeName());
        respDto.setAccBalance(payeeAccountInfoBo.getAccBalance());
        respDto.setBankAddress("4275 Executive Square, Suite 800 La Jolla, CA 92037");
        respDto.setMemberAccNo(payeeAccountInfoBo.getMemberAccNo());
        return respDto;
    }
}