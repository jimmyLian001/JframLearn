package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayVerifyBiz;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.CbPayOrderRiskControlDo;
import com.baofu.cbpayservice.dal.models.CbPayVerifyCountDo;
import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;
import com.baofu.cbpayservice.facade.CbPayOrderRiskControlFacade;
import com.baofu.cbpayservice.facade.models.CbPayOrderRiskControlReqDto;
import com.baofu.cbpayservice.facade.models.FiCbPayVerifyReqDto;
import com.baofu.cbpayservice.manager.CbPayRiskOrderManager;
import com.baofu.cbpayservice.manager.CbPayVerifyCountManger;
import com.baofu.cbpayservice.manager.FiCbPayVerifyManager;
import com.baofu.cbpayservice.service.convert.CbpayRiskOrderServerConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: wdj Date:2017/04/28 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderRiskControlFacadeImpl implements CbPayOrderRiskControlFacade {

    /**
     * 风控订单服务
     */
    @Autowired
    private CbPayRiskOrderManager cbPayRiskOrderManager;

    /**
     * 实名认证统计服务
     */
    @Autowired
    private CbPayVerifyCountManger cbPayVerifyCountManger;

    /**
     * 实名认证服务
     */
    @Autowired
    private FiCbPayVerifyManager fiCbPayVerifyManager;

    /**
     * 请求新颜征信系统进行实名认证
     */
    @Autowired
    private CbPayVerifyBiz cbPayVerifyBiz;

    /**
     * 跨境在征信开的商户号
     */
    @Value("${real_name_member_id}")
    private String memberIdOfAuth;

    /**
     * 跨境在征信开的终端号
     */
    @Value("${real_name_terminal_id}")
    private String terminalId;

    /**
     * @param cbPayOrderRiskControlReqDto 请求对象
     * @param traceLogId                  日志ID
     * @return 返回人工处理结果
     */
    @Override
    public Result<Boolean> cbPayOrderRiskControlManualAudit(CbPayOrderRiskControlReqDto cbPayOrderRiskControlReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 风控订单人工审核请求参数信息：{}", cbPayOrderRiskControlReqDto);
            //校验请求参数是否合法
            ParamValidate.validateParams(cbPayOrderRiskControlReqDto);
            CbPayOrderRiskControlDo cbPayOrderRiskControlDo = CbpayRiskOrderServerConvert.toRiskControlDo(cbPayOrderRiskControlReqDto);
            cbPayRiskOrderManager.modifyRiskControlOrder(cbPayOrderRiskControlDo);
            log.info("call 更新风控订单成功，宝付订单号为：{}", cbPayOrderRiskControlReqDto.getOrderId());
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 风控订单人工审核 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 风控订单人工审核 result:{}", result);
        return result;
    }

    /**
     * 购汇结汇实名认证实现
     *
     * @param fileBatchNo 文件批次号
     * @param orderType   订单类型
     * @param authCount   认证笔数
     * @param memberId    商户号
     * @param memberName  商户名称
     * @return 实名认证结果
     */
    @Override
    public Result<Boolean> cbPayOrderRiskControlCertification(Long fileBatchNo, Long memberId, String memberName,
                                                              Integer authCount, String orderType, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 风控订单实名认证请求参数信息：文件批次号{}   认证数量：{}  订单类型：{}", fileBatchNo,
                    authCount, orderType);
            //校验请求参数是否合法
            if (fileBatchNo == null || fileBatchNo < 0 || authCount == null || authCount <= 0 || orderType == null
                    || memberId == null || StringUtils.isBlank(memberName)) {
                log.error("call 购汇结汇实名认证 cbPayOrderRiskControlCertification 请求参数不合法。");
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "请求参数不合法。");
            }
            //根据文件批次号和商户号随机抽取数据
            List<FiCbPayVerifyDo> verifyList;
            //实名认证结果统计
            Map<String, Integer> authResult;
            //根据订单类型随机抽取订单 0：购汇订单   1：结汇订单
            if (Constants.ZERO.equals(orderType)) {
                verifyList = fiCbPayVerifyManager.queryNeedVerify(fileBatchNo, memberId, authCount);
                authResult = doAuth(verifyList, authCount, memberName, orderType);
            } else if (Constants.ONE.equals(orderType)) {
                //结汇订单
                verifyList = fiCbPayVerifyManager.queryNeedVerifyOfSettle(fileBatchNo, memberId, authCount);
                authResult = doAuth(verifyList, authCount, memberName, orderType);
            } else {
                log.error("call 购汇结汇实名认证 cbPayOrderRiskControlCertification 抽查订单类型有误。");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00144);
            }
            log.info("实名认证：需要认证的数量：{},认证成功的数量：{},认证失败的数量：{},认证不一致的数量：{}", authCount,
                    authResult.get("authSucCount"), authResult.get("authFailCount"), authResult.get("flag"));
            //更新汇总表信息
            updateOrInsterCbPayVerifyCount(fileBatchNo, authCount - authResult.get("flag"), orderType, authResult.get("authFailCount"),
                    authResult.get("authSucCount"), memberId, memberName);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 风控实名认证 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 风控实名认证 result:{}", result);
        return result;
    }

    /**
     * 风控单笔实名认证
     *
     * @param fiCbPayVerifyReqDto 请求实体类
     * @param traceLogId          日志ID
     * @return 处理结果
     */
    @Override
    public Result<Boolean> cbPayOrderRiskSingleAuth(FiCbPayVerifyReqDto fiCbPayVerifyReqDto, String traceLogId) {

        Result<Boolean> result;
        try {
            log.info("风控单笔认证请求参数：{}", fiCbPayVerifyReqDto);
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            //参数校验
            ParamValidate.validateParams(fiCbPayVerifyReqDto);
            CbPayVerifyResBo cbPayVerifyResBo = cbPayVerifyBiz.idCardAuthOfRisk(CbpayRiskOrderServerConvert.toCbPaySingleVerifyResBo(
                    fiCbPayVerifyReqDto, terminalId, Long.parseLong(memberIdOfAuth)));
            FiCbPayVerifyDo fiCbPayVerifyDo = CbpayRiskOrderServerConvert.toFiCbPaySingleVerifyDo(fiCbPayVerifyReqDto, cbPayVerifyResBo,
                    terminalId, Long.parseLong(memberIdOfAuth));
            //更新实名认证表
            updateOrInsterCbPayVerify(fiCbPayVerifyDo);
            //更新汇总表   如果是-1不更新   海关订单也不更新
            if (cbPayVerifyResBo.getCode() >= 0 && !"2".equals(fiCbPayVerifyReqDto.getOrderType())) {
                updateOrInsterSingleCbPayVerifyCount(fiCbPayVerifyReqDto, cbPayVerifyResBo);
            }
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 风控实名认证 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 风控实名认证 result:{}", result);
        return result;
    }

    /**
     * 根据实名认证结果，更新或者插入数据
     *
     * @param fiCbPayVerifyDo 实名认证信息
     */
    private void updateOrInsterCbPayVerify(FiCbPayVerifyDo fiCbPayVerifyDo) {
        log.info("实名认证处理数据结果：{}", fiCbPayVerifyDo);
        int count = fiCbPayVerifyManager.queryVertifyByOrderId(fiCbPayVerifyDo.getOrderId());
        if (count > 0) {
            if (fiCbPayVerifyDo.getCode() >= 0) {
                fiCbPayVerifyManager.updateVertifyByOrderId(fiCbPayVerifyDo);
            }
        } else {
            fiCbPayVerifyManager.insert(fiCbPayVerifyDo);
        }
    }

    /**
     * 更新汇总表  有就更新没有就插入
     *
     * @param fileBatchNo   文件批次号
     * @param authCount     认证数量
     * @param orderType     订单类型
     * @param authFailCount 认证失败数量
     * @param authSucCount  认证成功数量
     * @param memberId      商户ID
     * @param memberName    商户名称
     */
    private void updateOrInsterCbPayVerifyCount(Long fileBatchNo, Integer authCount, String orderType, int authFailCount,
                                                int authSucCount, Long memberId, String memberName) {

        //判断该批次是否抽查过
        CbPayVerifyCountDo cbPayVerifyCountDo = cbPayVerifyCountManger.queryVerifyCounByFileBatchNo(fileBatchNo);

        if (cbPayVerifyCountDo == null) {
            //将汇总信息插入FI_CBPAY_VERIFY_COLLECT表中
            CbPayVerifyCountDo cbPayVerifyCount = CbpayRiskOrderServerConvert.toCbPayVerifyCountDo(fileBatchNo,
                    authCount, orderType, authFailCount, authSucCount, memberId, memberName);
            cbPayVerifyCountManger.addCbPayVerifyCount(cbPayVerifyCount);
        } else {
            cbPayVerifyCountManger.updateVerifyCount(CbpayRiskOrderServerConvert.toChangeNeedData(cbPayVerifyCountDo,
                    authSucCount, authFailCount, authCount, memberName));
        }
    }

    /**
     * 单笔实名认证  数据汇总插入或者更新
     *
     * @param fiCbPayVerifyReqDto 实名认证信息
     * @param cbPayVerifyResBo    认证结果信息
     */
    private void updateOrInsterSingleCbPayVerifyCount(FiCbPayVerifyReqDto fiCbPayVerifyReqDto, CbPayVerifyResBo cbPayVerifyResBo) {
        //根据文件批次号查询，是否抽查过
        CbPayVerifyCountDo cbPayVerifyCountDo = cbPayVerifyCountManger.queryVerifyCounByFileBatchNo(fiCbPayVerifyReqDto.getFileBathNo());
        if (cbPayVerifyCountDo != null) {
            //更新FI_CBPAY_VERIFY_COLLECT表
            cbPayVerifyCountManger.updateVerifyCount(CbpayRiskOrderServerConvert.toChangeDate(cbPayVerifyCountDo, cbPayVerifyResBo.getCode(),
                    fiCbPayVerifyReqDto.getMemberName()));
        } else {
            //将数据插入数据库中
            CbPayVerifyCountDo cbPayVerifyCount = CbpayRiskOrderServerConvert.toCbPayVerifyCountDo(fiCbPayVerifyReqDto.getFileBathNo(),
                    1, fiCbPayVerifyReqDto.getOrderType(), (cbPayVerifyResBo.getCode() < 0 || cbPayVerifyResBo.getCode() == 1) ? 1 : 0,
                    (cbPayVerifyResBo.getCode() < 0 || cbPayVerifyResBo.getCode() == 1) ? 0 : 1,
                    fiCbPayVerifyReqDto.getMemberId(), fiCbPayVerifyReqDto.getMemberName());
            cbPayVerifyCountManger.addCbPayVerifyCount(cbPayVerifyCount);
        }
    }

    /**
     * 实名认证处理方法
     * orderType 0：购汇订单   1：结汇订单
     *
     * @param verifyList 认证数据
     * @param authCount  认证数量
     * @param memberName 商户名
     * @param orderType  订单类型
     * @return Map
     */
    private Map doAuth(List<FiCbPayVerifyDo> verifyList, Integer authCount, String memberName, String orderType) {

        Map<String, Integer> result = new HashMap<>();
        int authFailCount = 0;
        int authSucCount = 0;
        int flag = 0;
        //可认证的订单数量小于需要认证笔数
        if (authCount > verifyList.size()) {
            log.error("call 购汇结汇实名认证 cbPayOrderRiskControlCertification 没有足够的订单需要进行实名认证。");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00143);
        }
        for (FiCbPayVerifyDo fiCbPayVerifyDo : verifyList) {
            CbPayVerifyResBo cbPayVerifyResBo = cbPayVerifyBiz.idCardAuthOfRisk(CbpayRiskOrderServerConvert.toCbPayVerifyResBo(
                    fiCbPayVerifyDo, terminalId, Long.parseLong(memberIdOfAuth)));
            CbpayRiskOrderServerConvert.toFiCbPayVerifyDo(fiCbPayVerifyDo, cbPayVerifyResBo, memberName,
                    terminalId, Long.parseLong(memberIdOfAuth), orderType);
            //统计失败笔数  购付汇
            if (cbPayVerifyResBo.getCode() == 1) {
                //认证不一致
                log.info("认证不一致的订单信息：{}", fiCbPayVerifyDo);
                ++authFailCount;
            } else if (cbPayVerifyResBo.getCode() < 0) {
                //系统异常
                ++flag;
            } else {
                //认证成功
                ++authSucCount;
            }
            updateOrInsterCbPayVerify(fiCbPayVerifyDo);
        }
        result.put("authFailCount", authFailCount);
        result.put("authSucCount", authSucCount);
        result.put("flag", flag);
        return result;

    }

}
