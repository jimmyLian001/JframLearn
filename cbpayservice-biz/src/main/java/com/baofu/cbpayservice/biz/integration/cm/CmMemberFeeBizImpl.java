package com.baofu.cbpayservice.biz.integration.cm;

import com.baofoo.cache.bill.service.facade.FeeMemberServiceFacade;
import com.baofoo.cache.bill.service.facade.model.MemberFeeReqDTO;
import com.baofoo.cache.bill.service.facade.model.MemberFeeResDTO;
import com.baofoo.cache.bill.service.facade.model.Result;
import com.baofu.cbpayservice.biz.convert.CmParamConvert;
import com.baofu.cbpayservice.biz.models.MemberFeeReqBo;
import com.baofu.cbpayservice.biz.models.MemberFeeResBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.system.commons.exception.BaseException;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 调用用计费服务
 * User: yangjian  Date: 2017-05-24 ProjectName:cbpay-service  Version: 1.0
 */
@Slf4j
@Service
public class CmMemberFeeBizImpl {

    /**
     * 计费服务中心
     */
    @Autowired
    private FeeMemberServiceFacade feeMemberServiceFacade;

    /**
     * 调用计费
     *
     * @param memberFeeReqBo 计费参数
     * @return 返回计费结果
     */
    public MemberFeeResBo findMemberFee(MemberFeeReqBo memberFeeReqBo) {

        try {
            MemberFeeReqDTO memberFeeReqDTO = CmParamConvert.cmRequestConvert(memberFeeReqBo);
            log.info("调用计系统服务,memberFeeReqDTO请求参数为：{}", memberFeeReqDTO);
            Result<MemberFeeResDTO> result = feeMemberServiceFacade.findMemberFee(memberFeeReqDTO);
            log.info("调用计费查询返回参数：{}", result);
            //调用计费系统失败
            if (!result.isSuccess() || result.getResult() == null) {
                log.info("调用计费系统失败：{}", result.getErrorMsg());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0080, result.getErrorMsg());
            }
            MemberFeeResBo memberFeeResBo = CmParamConvert.cmResponseConvert(result.getResult());
            log.info("转换返回参数result:{}", memberFeeResBo);

            return memberFeeResBo;
        } catch (BaseException e) {
            log.error("调用计费系统失败.异常信息：", e);
            throw e;
        } catch (Exception e) {
            log.error("调用计费系统失败.异常信息：", e);
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "请求计费系统异常");
        }
    }
}
