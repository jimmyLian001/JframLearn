package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofu.cbpayservice.biz.CbPayCmBiz;
import com.baofu.cbpayservice.biz.CbPaySettlePrepaymentBiz;
import com.baofu.cbpayservice.biz.ExchangeRateQueryBiz;
import com.baofu.cbpayservice.biz.RedisBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettlePrepaymentConvert;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.CbPaySettlePrepaymentBo;
import com.baofu.cbpayservice.biz.models.ExchangeRateQueryBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleApplyDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPaySettleManager;
import com.baofu.cbpayservice.manager.FiCbPaySettlePrepaymentManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 结汇垫资接口
 * <p>
 * 1,结汇垫资申请
 * 2，结汇垫资审核
 * 3，是否已垫资
 * 4，结汇垫资返还状态修改
 * </p>
 * User: 康志光 Date: 2017/8/17 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettlePrepaymentBizImpl implements CbPaySettlePrepaymentBiz {

    /**
     * 垫资商户号
     */
    @Value("${prepayment_member_id}")
    private String PREPAYMENT_MEMBER_ID;

    /**
     * 垫资信息服务
     */
    @Autowired
    private FiCbPaySettlePrepaymentManager fiCbPaySettlePrepaymentManager;

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 计费服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 结汇信息服务
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 结汇申请服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 订单号生成服务
     */
    @Autowired
    private OrderIdManager orderCreateManager;

    /**
     * 汇率服务
     */
    @Autowired
    private ExchangeRateQueryBiz exchangeRateQueryBiz;

    /**
     * redis服务
     */
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 结汇垫资申请
     *
     * @param incomeNo 汇入汇款编号
     */
    @Override
    public Long prepaymentApply(String incomeNo, Integer autoFlag) {
        log.info("创建结汇垫资信息参数:{}", incomeNo);
        //校验结汇垫资申请是否存在
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = fiCbPaySettlePrepaymentManager.getPrepaymentInfoByIncomeNo(incomeNo);
        if (cbPaySettlePrepaymentDo != null) {
            log.info("结汇垫资申请已存在：{}", incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00179);
        }

        //获取结汇订单
        FiCbPaySettleDo fiCbPaySettleDo = getSettleOrder(incomeNo);
        //判断商户是否开通自动结汇产品
        verifyMemberProd(fiCbPaySettleDo.getMemberId(), autoFlag);
        //生成结汇垫资applyId
        Long applyId = orderCreateManager.orderIdCreate();
        //根据商户浮动汇率和到账金额计算垫资金额
        ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryRateByMemberIdAndCcy(fiCbPaySettleDo.getMemberId(),
                fiCbPaySettleDo.getChannelId(), fiCbPaySettleDo.getIncomeCcy());
        BigDecimal settleAmt = fiCbPaySettleDo.getIncomeAmt().multiply(exchangeRateQueryBo.getBuyAdvanceRateOfCcy().
                divide(new BigDecimal("100"))).setScale(2, BigDecimal.ROUND_DOWN);
        //组装垫资申请信息
        cbPaySettlePrepaymentDo = CbPaySettlePrepaymentConvert.convertTo(fiCbPaySettleDo, applyId, settleAmt,
                exchangeRateQueryBo.getBuyAdvanceRateOfCcy(), PREPAYMENT_MEMBER_ID, autoFlag);
        log.info("结汇垫资申请信息：{}", cbPaySettlePrepaymentDo);
        fiCbPaySettlePrepaymentManager.create(cbPaySettlePrepaymentDo);
        return applyId;
    }

    /**
     * 结汇垫资审核
     *
     * @param applyId 垫资申请编号
     * @param status  垫资状态
     * @param remarks 备注
     * @return
     */
    @Override
    public void prepaymentVerify(Long applyId, Integer status, String remarks) {
        log.info("结汇垫资审核参数:applyId {},cmStatus {}", applyId, status);

        try {
            CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = fiCbPaySettlePrepaymentManager.getPrepaymentInfoByApplyId(applyId);
            //获取结汇订单
            FiCbPaySettleDo fiCbPaySettleDo = getSettleOrder(cbPaySettlePrepaymentDo.getIncomeNo());
            //判断商户是否开通自动结汇产品
            verifyMemberProd(fiCbPaySettleDo.getMemberId(), cbPaySettlePrepaymentDo.getAutoFlag());
            //更新垫资结汇订单状态
            CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo1 = CbPaySettlePrepaymentConvert.convertTo(applyId, status, null, remarks, null);
            fiCbPaySettlePrepaymentManager.modifyByApplyId(cbPaySettlePrepaymentDo1);

            //校验垫资申请状态
            if (SettlePreVerifyStatusEnum.SECOND_PASS.getCode() == cbPaySettlePrepaymentDo.getCmStatus()
                    || SettlePreVerifyStatusEnum.SECOND_PASS.getCode() != status) {
                return;
            }

            log.info("垫资复审通过，开始垫资结汇：{}", DateUtil.getCurrent());
            //垫资->商户 账户间转账
            BigDecimal feeAmt = cbPayCmBiz.memberAccTransfer(cbPaySettlePrepaymentDo, fiCbPaySettleDo.getChannelId());
            //变更垫资状态
            CbPaySettlePrepaymentDo prepaymentDo = CbPaySettlePrepaymentConvert.convertTo(applyId, null, SettlePreStatusEnum.PRE_PAYED.getCode(), null, feeAmt);
            fiCbPaySettlePrepaymentManager.modifyByApplyId(prepaymentDo);
            //变更结汇订单结汇手续费
            FiCbPaySettleDo fiCbPaySettleDo1 = new FiCbPaySettleDo();
            fiCbPaySettleDo1.setOrderId(fiCbPaySettleDo.getOrderId());
            fiCbPaySettleDo1.setMemberSettleAmt(cbPaySettlePrepaymentDo.getPreSettleAmt());
            fiCbPaySettleDo1.setMemberSettleRate(cbPaySettlePrepaymentDo.getPreSettleRate());
            fiCbPaySettleDo1.setSettleFee(feeAmt);
            cbPaySettleManager.modify(fiCbPaySettleDo1);
            log.info("垫资复审通过，垫资结汇结束：{}", DateUtil.getCurrent());
        } catch (Exception e) {
            log.error("垫资审核失败：{}", e);
            CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = CbPaySettlePrepaymentConvert
                    .convertTo(applyId, SettlePreVerifyStatusEnum.SECOND_VERIFY.getCode(), null, remarks, null);
            fiCbPaySettlePrepaymentManager.modifyByApplyId(cbPaySettlePrepaymentDo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00185);
        }

    }

    /**
     * 自动结汇垫资
     *
     * @param memberId 商户号
     * @param incomeNo 汇入汇款编号
     */
    @Override
    public void autoSettlePrepay(Long memberId, String incomeNo) {
        try {
            log.info("自动垫资结汇参数：memberId {}, incomeNo {}", memberId, incomeNo);
            this.prepaymentApply(incomeNo, 0);
            log.info("自动垫资结汇结束：{}", DateUtil.getCurrent());
        } catch (Exception e) {
            log.error("自动垫资结汇失败");
        }
    }

    /**
     * 是否已垫资
     *
     * @param incomeNo 银行汇入汇款编号
     * @return boolean 是否已垫资
     */
    @Override
    public Boolean isPrepayment(String incomeNo) {
        log.info("判断是否已垫资参数:incomeNo {}", incomeNo);
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = fiCbPaySettlePrepaymentManager.getPrepaymentInfoByIncomeNo(incomeNo);
        log.info("查询垫资信息:{}", cbPaySettlePrepaymentDo);
        if (cbPaySettlePrepaymentDo != null && SettlePreStatusEnum.PRE_PAYED.getCode() == cbPaySettlePrepaymentDo.getPreStatus()) {
            return true;
        }
        return false;
    }

    /**
     * 获取结汇订单
     *
     * @param incomeNo 汇入汇款编号
     * @return FiCbPaySettleDo 结汇订单信息
     */
    private FiCbPaySettleDo getSettleOrder(String incomeNo) {

        //校验结汇申请订单和结汇订单
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByIncomeNo(incomeNo);
        if (fiCbPaySettleDo == null) {
            log.info("结汇订单{}不存在", incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        } else if (fiCbPaySettleDo.getSettleStatus() == SettleStatusEnum.PART_TRUE.getCode()
                || fiCbPaySettleDo.getSettleStatus() == SettleStatusEnum.TURE.getCode()) {
            log.info("结汇订单{}已经完成结汇", incomeNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00180);
        }
        //判断这笔订单是否处于结汇处理中
        if (redisBiz.isLock(Constants.PREPAYMENT_PROCESS_FLAG + fiCbPaySettleDo.getOrderId().toString())) {
            log.info("该笔结汇订单{}正在结汇中", fiCbPaySettleDo.getOrderId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00183);
        }

        log.info("结汇订单信息：{}", fiCbPaySettleDo);
        log.info("结汇订单ID：{}", fiCbPaySettleDo.getOrderId());
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(fiCbPaySettleDo.getOrderId());
        log.info("结汇申请订单信息：{}", fiCbPaySettleApplyDo);
        if (fiCbPaySettleApplyDo == null) {
            log.info("结汇申请订单不存在");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        } else if (MatchingStatusEnum.YES_MATCH.getCode() != fiCbPaySettleApplyDo.getMatchingStatus()) {
            log.info("结汇订单还未人工匹配");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00178);
        }
        return fiCbPaySettleDo;
    }


    /**
     * 计算垫资结汇金额
     *
     * @param incomeNo 汇入汇款编号
     * @return CbPaySettlePrepaymentBo 垫资信息
     */
    @Override
    public CbPaySettlePrepaymentBo calculateSettleAmt(String incomeNo) {

        //获取结汇订单
        FiCbPaySettleDo fiCbPaySettleDo = getSettleOrder(incomeNo);
        //获取垫资信息
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = fiCbPaySettlePrepaymentManager.getPrepaymentInfoByIncomeNo(incomeNo);
        BigDecimal settleAmt, settleRate;
        if (cbPaySettlePrepaymentDo == null) {
            //根据商户浮动汇率和到账金额计算垫资金额
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryRateByMemberIdAndCcy(fiCbPaySettleDo.getMemberId(),
                    fiCbPaySettleDo.getChannelId(), fiCbPaySettleDo.getIncomeCcy());
            settleAmt = fiCbPaySettleDo.getIncomeAmt().multiply(exchangeRateQueryBo.getBuyAdvanceRateOfCcy().
                    divide(new BigDecimal("100"))).setScale(2, BigDecimal.ROUND_DOWN);
            settleRate = exchangeRateQueryBo.getBuyAdvanceRateOfCcy();
        } else {
            settleAmt = cbPaySettlePrepaymentDo.getPreSettleAmt();
            settleRate = cbPaySettlePrepaymentDo.getPreSettleRate();
        }
        //获取商户信息
        CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPaySettleDo.getMemberId().intValue());

        //组装垫资申请信息
        CbPaySettlePrepaymentBo cbPaySettlePrepaymentBo = CbPaySettlePrepaymentConvert.convertToPrepaymentBo(cacheMemberDto,fiCbPaySettleDo, PREPAYMENT_MEMBER_ID,
                settleAmt, settleRate,cbPaySettlePrepaymentDo != null ? cbPaySettlePrepaymentDo.getRemarks() : null);
        return cbPaySettlePrepaymentBo;
    }

    /**
     * 验证商户是否开通自动结汇垫资产品
     *
     * @param memberId 商户ID
     * @return boolean
     */
    private void verifyMemberProd(Long memberId, Integer autoFlag) {
        Boolean isOpen = Boolean.FALSE;
        try {
            cbPayCacheManager.getProductFunctions(memberId.intValue(),
                    ProFunEnum.PRO_FUN_10180003.getProductId(), ProFunEnum.PRO_FUN_10180003.getFunctionId());
            isOpen = Boolean.TRUE;
        } catch (Exception e) {
        }
        if (autoFlag == 0 && !isOpen) {
            log.info("商户未开通自动结汇垫资产品，不能自动垫资");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0016);
        } else if (autoFlag == 0 && isOpen) {
            log.info("商户已开通自动结汇垫资产品，允许自动垫资");
        } else if (isOpen) {
            log.info("商户已开通自动结汇垫资产品，不能申请临时垫资");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00182);
        } else {
            log.info("商户未开通自动结汇垫资产品，允许申请临时垫资");
        }
    }

}
