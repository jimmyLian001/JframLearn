package com.baofu.cbpayservice.biz.integration.ma;

import com.baofoo.member.dal.model.MaMerchantLink;
import com.baofoo.member.service.facade.cbp_facade.QueryMemberEmailFacade;
import com.baofu.cbpayservice.biz.convert.MemberEmailBizConvert;
import com.baofu.cbpayservice.biz.models.MemberEmailBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商户邮箱相关查询服务
 * <p>
 * 1、查询商户业务邮箱
 * </p>
 * User: 白玉京 Date:2017/9/9 0009 ProjectName: cbpay-service
 */
@Slf4j
@Component
public class MemberEmailQueryBizImpl {

    /**
     * 商户邮箱查询服务，dubbo接口
     */
    @Autowired
    private QueryMemberEmailFacade queryMemberEmailFacade;

    /**
     * 查询商户业务邮箱
     *
     * @param memberNo 商户号
     * @return 结果
     */
    public MemberEmailBo findBusinessEmail(Long memberNo) {
        log.info("call 查询商户号:{},业务邮箱查询。", memberNo);
        try {
            Result<MaMerchantLink> maResult = queryMemberEmailFacade.queryMaMerchantLinkByMemberId(memberNo);
            log.info("call 查询商户号:{}业务邮箱查询,返回参数:{}。", memberNo, maResult);
            if (!maResult.isSuccess() || StringUtil.isBlank(maResult.getResult().getBusinessEmail())) {
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00158);
            }
            return MemberEmailBizConvert.paramConvert(maResult.getResult());
        } catch (BizServiceException e) {
            log.error("call 查询商户业务邮箱发生异常：{}", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00158);
        }
    }
}
