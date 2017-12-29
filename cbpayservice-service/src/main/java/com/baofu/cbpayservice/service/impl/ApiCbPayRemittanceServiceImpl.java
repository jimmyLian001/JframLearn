package com.baofu.cbpayservice.service.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwVerifyMerchantReqDto;
import com.baofu.cbpayservice.biz.CbPayCommonBiz;
import com.baofu.cbpayservice.biz.CbPaySettleBankBiz;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPayRemittanceMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.facade.ApiCbPayRemittanceFacade;
import com.baofu.cbpayservice.facade.models.ApiCbPayRemittanceOrderReqDto;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.baofu.cbpayservice.service.convert.CbPayRemittanceConvert;
import com.baofu.cbpayservice.service.convert.ProxyCustomsConvert;
import com.google.common.base.Splitter;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * </p>
 * @author 莫小阳  Date:2017/09/28 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class ApiCbPayRemittanceServiceImpl implements ApiCbPayRemittanceFacade {


    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 备案查询接口
     */
    @Autowired
    private CgwCbPayReqFacade cgwCbPayReqFacade;

    /**
     * 商户银行结算账户信息
     */
    @Autowired
    private CbPaySettleBankBiz cbPaySettleBankBiz;

    /**
     * 缓存操作
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 代理报关biz层服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 商户请求信息操作服务类
     */
    @Autowired
    private FiCbPayMemberApiRqstManager fiCbpayMemberApiRqstManager;

    /**
     * 批次文件操作类
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    /**
     * 汇款服务类
     */
    @Autowired
    private FiCbPayRemittanceMapper fiCbPayRemittanceMapper;


    /**
     * @param apiCbPayRemittanceOrderReqDto api请求对象
     * @param traceLogId                    日志ID
     * @return 处理结果
     */
    @Override
    public Result<Boolean> apiCreateRemittanceOrder(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto, String traceLogId) {

        Result<Boolean> result;

        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 创建汇款订单请求参数:{}", apiCbPayRemittanceOrderReqDto);
            ParamValidate.validateParams(apiCbPayRemittanceOrderReqDto);

            // 判定商户是否锁住
            Boolean lockFlag = isLock(apiCbPayRemittanceOrderReqDto);
            log.info("call 商户号：{}，是否锁住：{}", apiCbPayRemittanceOrderReqDto.getMemberId(), lockFlag);
            if (lockFlag) {
                log.info("call 商户{}已锁定，请勿频繁操作！", apiCbPayRemittanceOrderReqDto.getMemberId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
            }
            log.info("call 商户{}未锁住。", apiCbPayRemittanceOrderReqDto.getMemberId());

            //校验商户申请订单号是否已经存在
            Integer count = fiCbpayMemberApiRqstManager.countByMemberReqId(apiCbPayRemittanceOrderReqDto.getMemberId(),
                    apiCbPayRemittanceOrderReqDto.getRemitApplyNo());
            if (count != null && count > 0) {
                log.error("call 商户{}申请单号重复，申请单号：{}", apiCbPayRemittanceOrderReqDto.getMemberId(),
                        apiCbPayRemittanceOrderReqDto.getRemitApplyNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00204);
            }

            //判断是否开通产品
            Integer functionId = ProFunEnum.PRO_FUN_10160002.getFunctionId();
            if (CcyEnum.CNY.getKey().equals(apiCbPayRemittanceOrderReqDto.getRemitCcy())) {
                functionId = ProFunEnum.PRO_FUN_10160001.getFunctionId();
            }
            cbPayCacheManager.getProductFunctions(apiCbPayRemittanceOrderReqDto.getMemberId().intValue(),
                    ProFunEnum.PRO_FUN_10160001.getProductId(), functionId);

            //判断商户是否已经备案
            Long channelId = cbPayCommonBiz.queryChannelId(apiCbPayRemittanceOrderReqDto.getMemberId(),
                    apiCbPayRemittanceOrderReqDto.getRemitCcy(), ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            //获取商戶备案主体编号 entityNo
            String entityNo = cbPaySettleBankBiz.queryMerchantEntityNo(apiCbPayRemittanceOrderReqDto.getMemberId(),
                    apiCbPayRemittanceOrderReqDto.getBankAccNo(), apiCbPayRemittanceOrderReqDto.getRemitCcy());

            log.info("call 商户{}备案主体编号为：{}", apiCbPayRemittanceOrderReqDto.getMemberId(), entityNo);

            if (StringUtil.isBlank(entityNo)) {
                log.error("call 商户备案主体编号为空！");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00208);
            }

            CgwVerifyMerchantReqDto cgwVerifyMerchantReqDto = CbPayRemittanceConvert.verifyMerchantParamConvert(
                    apiCbPayRemittanceOrderReqDto, channelId, entityNo);
            Boolean merchantFlag = cgwCbPayReqFacade.verifyMerchant(cgwVerifyMerchantReqDto);
            log.info("call 商户是否已备案：{}", merchantFlag);
            if (!merchantFlag) {
                log.error("call 商户未备案：商户号：{}，子商户号：{}，币种：{}，渠道号：{}",
                        cgwVerifyMerchantReqDto.getMemberId(), cgwVerifyMerchantReqDto.getChildMemberId(),
                        cgwVerifyMerchantReqDto.getCurrency(), cgwVerifyMerchantReqDto.getChannelId());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00184);
            }

            //校验汇款订单的文件批次号是否为空
            List<String> fileBatchNos = Splitter.on(",").trimResults().splitToList(apiCbPayRemittanceOrderReqDto.getFileBatchNo());
            if (CollectionUtils.isEmpty(fileBatchNos)) {
                log.info("call 商户{}请求创建汇款订单的文件批次号为空", apiCbPayRemittanceOrderReqDto.getMemberId());
                return new Result<>(Boolean.FALSE);
            }

            //校验文件批次号是否合法
            int countData = fiCbPayRemittanceMapper.countChannelStatusByBatchNo(fileBatchNos);
            int fileCount = fiCbpayFileUploadMapper.countFileByFileBatchNo(fileBatchNos);
            if (countData > 0 || fileCount > 0) {
                log.error("call 商户文件批次号不合法，文件批次号：{}", fileBatchNos);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00209);
            }

            //判断批次币种是否一致，币种信息校验
            List<String> ccyList = proxyCustomsBiz.queryAmlCcy(CbPayRemittanceConvert.strToLong(fileBatchNos));
            //查询反洗钱币种
            if (!CollectionUtils.isEmpty(ccyList) && ccyList.size() > 1) {
                log.error("call 文件批次包含多种币种：{}，请检查后重试", ccyList);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00141);
            }
            //校验文件币种是否和原始币种一致
            if (!CcyEnum.CNY.getKey().equals(apiCbPayRemittanceOrderReqDto.getOriginalCcy())) {
                log.error("call 文件批次币种：{}和原始币种：{},不一致，请检查后重试", CcyEnum.CNY.getKey(),
                        apiCbPayRemittanceOrderReqDto.getOriginalCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00205);
            }

            //校验原始金额与订单明细汇总金额是否一致
            BigDecimal sumAmt = fiCbpayFileUploadMapper.sumAllOrderAmt(fileBatchNos);
            if (sumAmt.compareTo(apiCbPayRemittanceOrderReqDto.getOriginalAmt()) != 0) {
                log.error("call 文件批次金额：{}和原始金额：{},不相等，请检查后重试", sumAmt, apiCbPayRemittanceOrderReqDto.getOriginalAmt());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00206);
            }

            // 判断账户主体编号是否一致
            for (Long fileBatchId : CbPayRemittanceConvert.strToLong(fileBatchNos)) {
                String entityNoCache = redisManager.queryObjectByKey(Constants.CBPAY_AML_ENTITY_KEY
                        + apiCbPayRemittanceOrderReqDto.getMemberId() + ":"
                        + apiCbPayRemittanceOrderReqDto.getRemitCcy() + ":" + fileBatchId);
                if (!StringUtil.isBlank(entityNoCache) && !entityNo.equals(entityNoCache)) {
                    log.info("call 商户账户主体编号不一致，汇款选择编号：{}，反洗钱选择编号：{}", entityNo, entityNoCache);
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00150);
                }
            }

            //校验账户的地址信息长度
            FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectBankAccByEntityNo(
                    apiCbPayRemittanceOrderReqDto.getMemberId(), apiCbPayRemittanceOrderReqDto.getRemitCcy(), entityNo);
            if (!addressLengthCheck(fiCbPaySettleBankDo.getBankAccName(), fiCbPaySettleBankDo.getPayeeAddress())) {
                log.error("call 账户地址信息总长度超过120字节或分割后的信息长度超过35字节：{}，{}",
                        fiCbPaySettleBankDo.getBankAccName(), fiCbPaySettleBankDo.getPayeeAddress());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00186);
            }

            //将反洗钱文件订单明细存入缓存
            for (Long fileBatchId : CbPayRemittanceConvert.strToLong(fileBatchNos)) {
                redisManager.insertObject(entityNo, Constants.CBPAY_AML_ENTITY_KEY
                        + apiCbPayRemittanceOrderReqDto.getMemberId() + ":" + apiCbPayRemittanceOrderReqDto.getRemitCcy() + ":" + fileBatchId);
            }

            //更新文件状态
            proxyCustomsBiz.batchUpdateFileStatus(ProxyCustomsConvert.batchUpdateStatusConvert(apiCbPayRemittanceOrderReqDto,
                    CbPayRemittanceConvert.strToLong(fileBatchNos), UploadFileStatus.CREATING.getCode()));
            log.info("call 更新文件批次表成功：{},{}", fileBatchNos, UploadFileStatus.CREATING.getCode());

            //将商户API申请记录保存到数据库
            fiCbpayMemberApiRqstManager.addFiCbPayMemberApiRqst(CbPayRemittanceConvert.convertApiReqParam(apiCbPayRemittanceOrderReqDto));

            //发送mq创建汇款订单
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_CREATE_REMITTANCE_ORDER_QUEUE_NAME,
                    CbPayRemittanceConvert.orderParamConvertApi(apiCbPayRemittanceOrderReqDto,
                            CbPayRemittanceConvert.strToLong(fileBatchNos), entityNo));
            result = new Result<>(Boolean.TRUE);

        } catch (Exception e) {
            log.error("call API创建汇款订单 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call API创建汇款订单 RESULT:{}", result);
        return result;
    }

    /**
     * @param apiCbPayRemittanceOrderReqDto 商户请求对象
     * @return 结果
     */
    private Boolean isLock(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto) {
        List<String> fileIdList = Splitter.on(",").trimResults().splitToList(apiCbPayRemittanceOrderReqDto.getFileBatchNo());
        for (String fileId : fileIdList) {
            String value = redisManager.queryObjectByKey(Constants.CBPAY_CREATE_REMITTANCE_KEY
                    + apiCbPayRemittanceOrderReqDto.getMemberId() + ":" + fileId);
            if (FlagEnum.TRUE.getCode().equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 账户地址信息长度校验
     *
     * @param bankAccName  银行开户名
     * @param payeeAddress 收款人地址
     * @return 返回是否正确
     */
    private Boolean addressLengthCheck(String bankAccName, String payeeAddress) {
        String totalAddress = bankAccName + payeeAddress;
        boolean totalAddressFlag = totalAddress.length() > NumberConstants.ONE_HUNDRED * NumberConstants.TWO ? false : true;
        boolean bankAccNameFlag = StringUtils.lengthCheck(bankAccName, "\\s+", NumberConstants.FIVE * NumberConstants.SEVEN);
        boolean payeeAddressFlag = StringUtils.lengthCheck(payeeAddress, "\\s+", NumberConstants.FIVE * NumberConstants.SEVEN);
        return totalAddressFlag && bankAccNameFlag && payeeAddressFlag;
    }

}
