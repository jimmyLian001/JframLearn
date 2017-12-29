package com.baofu.cbpayservice.service.convert;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.dfs.client.util.DateUtil;
import com.baofu.cbpayservice.biz.models.CbPayAddReceivableAccountBo;
import com.baofu.cbpayservice.facade.models.CbPayEstablishAccountDto;

/**
 * 收款账户管理开户参数转换
 * <p>
 * User: lian zd Date:2017/9/7  ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayReceivableAccountConvert {

    private CbPayReceivableAccountConvert() {

    }

    /**
     * @param cbPayEstablishAccountDto 收款账户开户信息对象
     * @param applyId                  申请id
     * @param memberDto                商户缓存信息
     * @return 转换结果
     */
    public static CbPayAddReceivableAccountBo toCbPayAddReceivableAccountBo(CbPayEstablishAccountDto cbPayEstablishAccountDto, Long applyId,
                                                                            CacheMemberDto memberDto) {

        CbPayAddReceivableAccountBo cbPayAddReceivableAccountBo = new CbPayAddReceivableAccountBo();
        cbPayAddReceivableAccountBo.setApplyId(applyId);
        cbPayAddReceivableAccountBo.setMemberId(cbPayEstablishAccountDto.getMemberId());
        cbPayAddReceivableAccountBo.setCountry("CN");
        cbPayAddReceivableAccountBo.setCcy("USD");
        cbPayAddReceivableAccountBo.setPostalCode(cbPayEstablishAccountDto.getPostalCode());
        cbPayAddReceivableAccountBo.setCity(cbPayEstablishAccountDto.getCity());
        cbPayAddReceivableAccountBo.setAddress(cbPayEstablishAccountDto.getAddress());
        cbPayAddReceivableAccountBo.setBirthDay(DateUtil.parse(cbPayEstablishAccountDto.getBirthDay(), DateUtil.yyMMdd));
        cbPayAddReceivableAccountBo.setFirstName(cbPayEstablishAccountDto.getFirstName());
        cbPayAddReceivableAccountBo.setLastName(cbPayEstablishAccountDto.getLastName());
        cbPayAddReceivableAccountBo.setMemberAccNo(memberDto.getAccountName());
        cbPayAddReceivableAccountBo.setPhoneNumber(cbPayEstablishAccountDto.getPhoneNumber());
        cbPayAddReceivableAccountBo.setMemberType(cbPayEstablishAccountDto.getMemberType());
        cbPayAddReceivableAccountBo.setPassportDfsId(cbPayEstablishAccountDto.getPassportDfsId());
        cbPayAddReceivableAccountBo.setLicenseDfsId(cbPayEstablishAccountDto.getLicenseDfsId());
        cbPayAddReceivableAccountBo.setIdNo(cbPayEstablishAccountDto.getIdNo());
        cbPayAddReceivableAccountBo.setProvince(cbPayEstablishAccountDto.getProvince());
        cbPayAddReceivableAccountBo.setIdType(cbPayEstablishAccountDto.getIdType());
        cbPayAddReceivableAccountBo.setCompanyName(cbPayEstablishAccountDto.getCompanyName());
        cbPayAddReceivableAccountBo.setAccountHolderWebsite(cbPayEstablishAccountDto.getAccountHolderWebsite());
        return cbPayAddReceivableAccountBo;
    }
}
