package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.SettleQueryBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettleConvert;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.biz.models.SettleQueryReqParamBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.MatchingStatusEnum;
import com.baofu.cbpayservice.common.enums.MemberSettleStatusEnum;
import com.baofu.cbpayservice.common.enums.SettleStatusEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
@Slf4j
@Service
public class SettleQueryBizImpl implements SettleQueryBiz {

    /**
     * 汇入申请Mapper服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper settleApplyMapper;

    /**
     * 银行汇入mapper服务
     */
    @Autowired
    private FiCbPaySettleMapper settleMapper;

    /**
     * 根据商户编号、商户请求流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param incomeNo 汇入汇款流水号
     * @return 返回商户需要查询的结果
     */
    @Override
    public SettleNotifyMemberBo querySettleNotifyByReqNo(Long memberId, String incomeNo) {

        //查询汇入申请信息
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = settleApplyMapper.queryByParams(memberId, incomeNo);
        if (fiCbPaySettleApplyDo == null) {
            log.warn("商户编号、商户流水号查汇入申请为空，参数信息：memberId:{},incomeNo:{}", memberId, incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        //查询结汇订单信息
        FiCbPaySettleDo cbPaySettleDo = null;
        //判断是否申请成功,只有申请成功时才继续往下查询
        if (MatchingStatusEnum.YES_MATCH.getCode() == fiCbPaySettleApplyDo.getMatchingStatus()) {
            cbPaySettleDo = settleMapper.queryByOrderId(fiCbPaySettleApplyDo.getMatchingOrderId());
        }
        //返回商户请求查询结果信息
        SettleNotifyMemberBo settleNotifyMemberBo = CbPaySettleConvert.paramConvert(fiCbPaySettleApplyDo, cbPaySettleDo);
        //设置返回给商户的状态
        settleNotifyMemberBo.setProcessStatus(settleNotifyStatusConvert(fiCbPaySettleApplyDo, cbPaySettleDo));
        if (MatchingStatusEnum.FILE_DETAIL_FAIL.getCode() == fiCbPaySettleApplyDo.getMatchingStatus()) {
            String errorFileName = "SETTLE_APPLY_ERROR_" + fiCbPaySettleApplyDo.getMemberId() + "_" + fiCbPaySettleApplyDo.getOrderId() + ".txt";
            settleNotifyMemberBo.setErrorFileName(errorFileName);
            settleNotifyMemberBo.setProcessStatus(MemberSettleStatusEnum.FILE_PROCESS_FAIL.getCode());
            settleNotifyMemberBo.setRemarks(MemberSettleStatusEnum.FILE_PROCESS_FAIL.getDesc());
        }
        return settleNotifyMemberBo;
    }

    /**
     * 根据商户编号、结汇流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param settleNo 结汇内部系统唯一编号
     * @return 返回商户需要查询的结果
     */
    @Override
    public SettleNotifyMemberBo querySettleNotifyBySettleNo(Long memberId, Long settleNo) {

        //查询汇入申请信息
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = settleApplyMapper.queryBySettleId(settleNo);
        if (fiCbPaySettleApplyDo == null) {
            log.warn("商户编号、结汇流水号查汇入申请为空，参数信息：memberId:{},applyNo:{}", memberId, settleNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        //查询结汇订单信息
        FiCbPaySettleDo cbPaySettleDo = null;
        //判断是否申请成功,只有申请成功时才继续往下查询
        if (MatchingStatusEnum.YES_MATCH.getCode() == fiCbPaySettleApplyDo.getMatchingStatus()) {
            cbPaySettleDo = settleMapper.queryByOrderId(fiCbPaySettleApplyDo.getMatchingOrderId());
        }
        //返回商户请求查询结果信息
        SettleNotifyMemberBo settleNotifyMemberBo = CbPaySettleConvert.paramConvert(fiCbPaySettleApplyDo, cbPaySettleDo);
        //设置返回给商户的状态
        settleNotifyMemberBo.setProcessStatus(settleNotifyStatusConvert(fiCbPaySettleApplyDo, cbPaySettleDo));

        return settleNotifyMemberBo;
    }

    /**
     * 根据商户编号、汇入申请内部流水号查询商户申请订单信息
     *
     * @param memberId 商户编号
     * @param applyNo  申请编号
     * @return 返回商户需要查询的结果
     */
    @Override
    public SettleNotifyMemberBo querySettleNotifyByApplyNo(Long memberId, Long applyNo) {
        //查询汇入申请信息
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = settleApplyMapper.queryByApplyNo(applyNo);
        if (fiCbPaySettleApplyDo == null) {
            log.warn("商户编号、汇入申请内部流水号查汇入申请为空，参数信息：memberId:{},applyNo:{}", memberId, applyNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        //查询结汇订单信息
        FiCbPaySettleDo cbPaySettleDo = null;
        //判断是否申请成功,只有申请成功时才继续往下查询
        if (MatchingStatusEnum.YES_MATCH.getCode() == fiCbPaySettleApplyDo.getMatchingStatus()) {
            cbPaySettleDo = settleMapper.queryByOrderId(fiCbPaySettleApplyDo.getMatchingOrderId());
        }
        //返回商户请求查询结果信息
        SettleNotifyMemberBo settleNotifyMemberBo = CbPaySettleConvert.paramConvert(fiCbPaySettleApplyDo, cbPaySettleDo);
        //设置返回给商户的状态
        settleNotifyMemberBo.setProcessStatus(settleNotifyStatusConvert(fiCbPaySettleApplyDo, cbPaySettleDo));

        return settleNotifyMemberBo;
    }

    /**
     * 根据商户查询信息
     *
     * @param settleQueryReqParamBo
     * @return
     */
    @Override
    public SettleNotifyMemberBo querySettleNotify(SettleQueryReqParamBo settleQueryReqParamBo) {
        //查询汇入申请信息
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = null;
        if (settleQueryReqParamBo.getApplyNo() != null) {
            fiCbPaySettleApplyDo = settleApplyMapper.queryByApplyNo(settleQueryReqParamBo.getApplyNo());
        } else if (settleQueryReqParamBo.getSettleOrderId() != null) {
            fiCbPaySettleApplyDo = settleApplyMapper.queryBySettleId(settleQueryReqParamBo.getSettleOrderId());
        } else if (settleQueryReqParamBo.getIncomeNo() != null) {
            fiCbPaySettleApplyDo = settleApplyMapper.queryByParams(settleQueryReqParamBo.getMemberId(),
                    settleQueryReqParamBo.getIncomeNo());
        }
        if (fiCbPaySettleApplyDo == null) {
            log.warn("查询汇入申请为空，参数信息：settleQueryReqParamBo:{}", settleQueryReqParamBo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        //查询结汇订单信息
        FiCbPaySettleDo cbPaySettleDo = null;
        //判断是否申请成功,只有申请成功时才继续往下查询
        if (MatchingStatusEnum.YES_MATCH.getCode() == fiCbPaySettleApplyDo.getMatchingStatus()) {
            cbPaySettleDo = settleMapper.queryByOrderId(fiCbPaySettleApplyDo.getMatchingOrderId());
        }
        //返回商户请求查询结果信息
        SettleNotifyMemberBo settleNotifyMemberBo = CbPaySettleConvert.paramConvert(fiCbPaySettleApplyDo, cbPaySettleDo);
        //设置返回给商户的状态
        settleNotifyMemberBo.setProcessStatus(settleNotifyStatusConvert(fiCbPaySettleApplyDo, cbPaySettleDo));

        return settleNotifyMemberBo;
    }

    /**
     * 根据商户申请信息和结汇信息转换返回商户对应的状态
     *
     * @param settleApplyDo 商户申请信息
     * @param cbPaySettleDo 结汇订单信息
     * @return 状态
     */
    private int settleNotifyStatusConvert(FiCbPaySettleApplyDo settleApplyDo, FiCbPaySettleDo cbPaySettleDo) {

        //判断是否申请失败
        if (MatchingStatusEnum.FAIL.getCode() == settleApplyDo.getMatchingStatus()) {
            return MemberSettleStatusEnum.APPLY_FAIL.getCode();
        }
        //文件处理失败
        if (MatchingStatusEnum.FILE_DETAIL_FAIL.getCode() == settleApplyDo.getMatchingStatus()) {
            return MemberSettleStatusEnum.FILE_PROCESS_FAIL.getCode();
        }
        //判断是否申请成功
        if (MatchingStatusEnum.YES_MATCH.getCode() != settleApplyDo.getMatchingStatus()) {
            return MemberSettleStatusEnum.APPLY_ING.getCode();
        }
        //结汇失败
        if (SettleStatusEnum.FAIL.getCode() == cbPaySettleDo.getSettleStatus()) {
            return MemberSettleStatusEnum.SETTLE_FAIL.getCode();
        }
        //判断是否结汇成功
        if (SettleStatusEnum.TURE.getCode() == cbPaySettleDo.getSettleStatus() ||
                SettleStatusEnum.PART_TRUE.getCode() == cbPaySettleDo.getSettleStatus()) {
            return MemberSettleStatusEnum.SETTLE_SUCCESS.getCode();
        }
        return MemberSettleStatusEnum.SETTLE_ING.getCode();
    }
}
