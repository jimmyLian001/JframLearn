package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwExChangeRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwRemitRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwSettleRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;
import com.baofoo.fi.entity.merchant.B2cBankOrder;
import com.baofoo.fi.entity.merchant.B2cBankOrderAddition;
import com.baofoo.provision.vo.RSIS150;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.dal.models.*;
import com.system.commons.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 参数对象转换
 * <p>
 * 1、业务参数转换成跨境订单创建参数
 * 2、业务参数转换成B2c订单创建参数
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
public class ParamConvert {

    /**
     * 业务参数转换成跨境订单创建参数
     *
     * @param cbPayOrderReqBO 业务参数
     * @return 跨境订单创建参数
     */
    public static FiCbPayOrderDo convert(CbPayOrderReqBo cbPayOrderReqBO) {

        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(cbPayOrderReqBO.getOrderId());
        fiCbPayOrderDo.setOrderCcy(cbPayOrderReqBO.getOrderCcy());
        fiCbPayOrderDo.setTransMoney(cbPayOrderReqBO.getTransMoney());
        fiCbPayOrderDo.setTransCcy(cbPayOrderReqBO.getTransCcy());
        fiCbPayOrderDo.setPayId(cbPayOrderReqBO.getPayId());
        fiCbPayOrderDo.setChannelId(cbPayOrderReqBO.getChannelId());
        fiCbPayOrderDo.setOrderCompleteTime(cbPayOrderReqBO.getOrderCompleteTime());
        fiCbPayOrderDo.setTransRate(cbPayOrderReqBO.getTransRate());
        fiCbPayOrderDo.setTransFee(BigDecimal.ZERO);
        fiCbPayOrderDo.setMemberId(cbPayOrderReqBO.getMemberId());
        fiCbPayOrderDo.setMemberTransId(cbPayOrderReqBO.getMemberTransId());
        fiCbPayOrderDo.setTerminalId(cbPayOrderReqBO.getTerminalId());
        fiCbPayOrderDo.setFunctionId(cbPayOrderReqBO.getFunctionId());
        fiCbPayOrderDo.setProductId(cbPayOrderReqBO.getProductId());
        fiCbPayOrderDo.setOrderMoney(cbPayOrderReqBO.getOrderMoney());
        fiCbPayOrderDo.setTransTime(cbPayOrderReqBO.getTransTime());
        fiCbPayOrderDo.setOrderType(cbPayOrderReqBO.getOrderType());
        fiCbPayOrderDo.setTransRate(cbPayOrderReqBO.getTransRate());
        fiCbPayOrderDo.setNotifyStatus(FlagEnum.TRUE.getCode());
        fiCbPayOrderDo.setTransFee(cbPayOrderReqBO.getTransFee());
        fiCbPayOrderDo.setPayStatus(StringUtils.isBlank(cbPayOrderReqBO.getPayStatus()) ?
                FlagEnum.INIT.getCode() : cbPayOrderReqBO.getPayStatus());
        fiCbPayOrderDo.setBatchFileId(cbPayOrderReqBO.getBatchNo() == null ? 0 : cbPayOrderReqBO.getBatchNo());
        String businessNo = StringUtils.leftPad(String.valueOf(cbPayOrderReqBO.getOrderId()), 32, "0");
        fiCbPayOrderDo.setBusinessNo(businessNo);
        fiCbPayOrderDo.setAckStatus(OrederAckStatusEnum.NO.getCode());
        fiCbPayOrderDo.setCareerType(cbPayOrderReqBO.getCareerType());
        return fiCbPayOrderDo;
    }

    /**
     * 业务参数转换成B2c订单创建参数
     *
     * @param cbPayOrderReqBO 业务参数
     * @return B2c订单创建参数
     */
    public static B2cBankOrder b2cConvert(CbPayOrderReqBo cbPayOrderReqBO) {

        B2cBankOrder order = new B2cBankOrder();
        order.setMemberId(cbPayOrderReqBO.getMemberId().intValue());
        order.setMemberTransId(cbPayOrderReqBO.getMemberTransId());
        order.setFunctionId(cbPayOrderReqBO.getFunctionId());
        order.setProductId(cbPayOrderReqBO.getProductId());
        order.setOrderMoney(cbPayOrderReqBO.getOrderMoney());
        order.setTradeDate(cbPayOrderReqBO.getTransTime());
        //卡类型 101借记卡 102信用卡 0混合
        order.setCardType(0);
        //渠道ID
        order.setChannelId(0);
        B2cBankOrderAddition addition = new B2cBankOrderAddition();
        //提交方式：1-提交产品 2-提交功能
        addition.setRequestType(1);
        //接口版本
        addition.setInterfaceVersion("4.0");
        //请求的支付id(有可能是产品id,也可能是功能id)
        addition.setRequestPayid(cbPayOrderReqBO.getFunctionId() + "");
        //通知方式(1-服务器和页面通知，2-仅服务器通知
        addition.setNoticeType(NotifyTypeEnum.findCode(cbPayOrderReqBO.getNotifyType()));
        addition.setSignature("");
        addition.setAdditionalInfo(cbPayOrderReqBO.getAdditionalInfo());
        addition.setCommodityName("商品名称");
        //商品数量
        addition.setCommodityAmount("1");
        addition.setClientIp(cbPayOrderReqBO.getClientIp());
        addition.setReferrer("请求域名");
        //附加信息
        order.setAddition(addition);
        return order;
    }

    /**
     * 请求订单信息转换成网关订单附加信息
     *
     * @param cbPayOrderReqBO 请求订单信息
     * @return 网关订单附加信息
     */
    public static FiCbPayOrderAdditionDo additionConvert(CbPayOrderReqBo cbPayOrderReqBO) {

        FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = new FiCbPayOrderAdditionDo();
        fiCbPayOrderAdditionDo.setOrderId(cbPayOrderReqBO.getOrderId());
        fiCbPayOrderAdditionDo.setNotifyType(cbPayOrderReqBO.getNotifyType());
        fiCbPayOrderAdditionDo.setServerNotifyUrl(cbPayOrderReqBO.getReturnUrl());
        fiCbPayOrderAdditionDo.setPageNotifyUrl(cbPayOrderReqBO.getPageReturnUrl());
        fiCbPayOrderAdditionDo.setAdditionInfo(cbPayOrderReqBO.getAdditionalInfo());
        fiCbPayOrderAdditionDo.setBankCardNo(SecurityUtil.desEncrypt(cbPayOrderReqBO.getBankCardNo(),
                Constants.CARD_DES_PASSWD));
        fiCbPayOrderAdditionDo.setIdCardNo(SecurityUtil.desEncrypt(cbPayOrderReqBO.getIdCardNo(),
                Constants.CARD_DES_PASSWD));
        fiCbPayOrderAdditionDo.setIdHolder(cbPayOrderReqBO.getIdHolder());
        fiCbPayOrderAdditionDo.setMobile(cbPayOrderReqBO.getMobile());
        fiCbPayOrderAdditionDo.setClientIp(cbPayOrderReqBO.getClientIp());
        fiCbPayOrderAdditionDo.setIsBankAccount("Y");
        fiCbPayOrderAdditionDo.setPayeeIdType(cbPayOrderReqBO.getPayeeIdType());
        return fiCbPayOrderAdditionDo;
    }

    /**
     * 商品信息转换
     *
     * @param cbPayOrderReqBO 订单请求参数信息
     * @return 数据库层次商品信息
     */
    public static List<FiCbPayOrderItemDo> paramConvert(CbPayOrderReqBo cbPayOrderReqBO) {

        List<FiCbPayOrderItemDo> cbPayOrderItemDoList = new ArrayList<>();
        for (CbPayOrderItemBo cbPayOrderItemBo : cbPayOrderReqBO.getCbPayOrderItemBos()) {
            cbPayOrderItemDoList.add(paramConvert(cbPayOrderItemBo, cbPayOrderReqBO.getOrderId()));
        }
        return cbPayOrderItemDoList;
    }

    /**
     * 商品信息转换
     *
     * @param cbPayOrderItemBo 订单请求商品信息
     * @param orderId          宝付订单号
     * @return 数据库层次商品信息
     */
    public static FiCbPayOrderItemDo paramConvert(CbPayOrderItemBo cbPayOrderItemBo, Long orderId) {
        FiCbPayOrderItemDo fiCbPayOrderItemDo = new FiCbPayOrderItemDo();
        fiCbPayOrderItemDo.setOrderId(orderId);
        fiCbPayOrderItemDo.setCommodityName(com.baofu.cbpayservice.common.util.StringUtils.stringFilter(cbPayOrderItemBo.getCommodityName()));
        fiCbPayOrderItemDo.setCommodityPrice(cbPayOrderItemBo.getCommodityPrice());
        fiCbPayOrderItemDo.setCommodityAmount(cbPayOrderItemBo.getCommodityAmount());

        return fiCbPayOrderItemDo;
    }

    /**
     * 通知商户请求参数转换并加密
     *
     * @param orderDo         网关交易信息
     * @param orderAdditionDo 网关交易附加信息
     * @return 返回Http通知参数信息
     */
    public static Map<String, String> paramConvert(FiCbPayOrderDo orderDo, FiCbPayOrderAdditionDo orderAdditionDo) {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("memberId", String.valueOf(orderDo.getMemberId()));
        paramMap.put("terminalId", String.valueOf(orderDo.getTerminalId()));
        paramMap.put("memberTransId", orderDo.getMemberTransId());
        paramMap.put("orderId", String.valueOf(orderDo.getOrderId()));
        paramMap.put("orderMoney", String.valueOf(orderDo.getOrderMoney()));
        paramMap.put("orderCcy", orderDo.getOrderCcy());
        paramMap.put("additionalInfo", orderAdditionDo.getAdditionInfo());
        paramMap.put("completeTime", DateUtil.format(orderDo.getOrderCompleteTime()));
        paramMap.put("orderDesc", "订单交易成功");

        return paramMap;
    }

    /**
     * dispatch命令信息初始化
     *
     * @param bizId         业务ID
     * @param bizType       　业务类型
     * @param maxRetryTimes 　最大重试次数
     * @return 返回对象
     */
    public static BizCmdDo paramConvert(String bizId, String bizType, Long maxRetryTimes) {

        BizCmdDo bizCmdDO = new BizCmdDo();
        bizCmdDO.setBizId(bizId);
        bizCmdDO.setBizType(bizType);
        bizCmdDO.setMaxRetryTimes(maxRetryTimes);

        return bizCmdDO;
    }

    /**
     * 通知商户信息
     *
     * @param baseResult 通知参数
     * @return 返回字符串
     */
    public static String paramConvert(BaseResultBo baseResult) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("success=").append(baseResult.getSuccess());
        stringBuilder.append("&errorMsg=").append(baseResult.getErrorMsg());
        stringBuilder.append("&result=").append(baseResult.getResult());
        stringBuilder.append("&memberId=").append(baseResult.getMemberId());
        stringBuilder.append("&terminalId=").append(baseResult.getTerminalId());
        return stringBuilder.toString();

    }

    /**
     * 跨境订单接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayRemittanceReqBo 跨境汇款订单请求参数
     * @param batchNo              批次号
     * @param tradeAmt             汇款总金额
     * @param fiCbPaySettleBankDo  商户币总账户信息
     * @return Biz层请求参数信息
     */
    public static FiCbPayRemittanceDo remittanceParamConvert(CbPayRemittanceReqBo cbPayRemittanceReqBo, Long channelId,
                                                             Long batchNo, BigDecimal tradeAmt,
                                                             FiCbPaySettleBankDo fiCbPaySettleBankDo, BigDecimal fee) {
        FiCbPayRemittanceDo fiCbPayRemittanceDo = new FiCbPayRemittanceDo();
        fiCbPayRemittanceDo.setBatchNo(String.valueOf(batchNo));
        fiCbPayRemittanceDo.setMemberNo(cbPayRemittanceReqBo.getMemberId());
        fiCbPayRemittanceDo.setTransMoney(tradeAmt);
        fiCbPayRemittanceDo.setTransCcy(Currency.CNY.getCode());
        fiCbPayRemittanceDo.setSwiftCode(fiCbPaySettleBankDo.getBankSwiftCode());
        fiCbPayRemittanceDo.setTransType(TransType.CASH.getCode());
        fiCbPayRemittanceDo.setChannelId(channelId);
        fiCbPayRemittanceDo.setAuditStatus(AuditStatus.INIT.getCode());
        fiCbPayRemittanceDo.setChannelStatus(ChannelStatus.INIT.getCode());
        fiCbPayRemittanceDo.setCreateBy(cbPayRemittanceReqBo.getCreateBy());
        fiCbPayRemittanceDo.setCreateAt(new Date());
        fiCbPayRemittanceDo.setUpdateAt(new Date());
        fiCbPayRemittanceDo.setUpdateBy(cbPayRemittanceReqBo.getCreateBy());
        fiCbPayRemittanceDo.setBankCardNo(fiCbPaySettleBankDo.getBankAccNo());
        fiCbPayRemittanceDo.setSystemRate(Currency.CNY.getCode().equals(fiCbPaySettleBankDo.getSettleCcy()) ? BigDecimal.ONE : BigDecimal.ZERO);
        fiCbPayRemittanceDo.setRealRate(Currency.CNY.getCode().equals(fiCbPaySettleBankDo.getSettleCcy()) ? BigDecimal.ONE : BigDecimal.ZERO);
        fiCbPayRemittanceDo.setSystemMoney(Currency.CNY.getCode().equals(fiCbPaySettleBankDo.getSettleCcy()) ? tradeAmt : BigDecimal.ZERO);
        fiCbPayRemittanceDo.setSystemCcy(fiCbPaySettleBankDo.getSettleCcy());
        fiCbPayRemittanceDo.setApplyStatus(ApplyStatus.INIT.getCode());
        fiCbPayRemittanceDo.setCmStatus(CMStatus.INIT.getCode());
        fiCbPayRemittanceDo.setRemarks(cbPayRemittanceReqBo.getRemark());
        fiCbPayRemittanceDo.setTransFee(fee);
        fiCbPayRemittanceDo.setOrderType(RemittanceOrderType.REMITTANCE_ORDER.getCode());
        fiCbPayRemittanceDo.setBusinessNo(StringUtils.leftPad(String.valueOf(batchNo), 32, "0"));
        fiCbPayRemittanceDo.setExchangeType(fiCbPaySettleBankDo.getExchangeType());
        // 购汇状态 1：已购汇 0：未购汇
        fiCbPayRemittanceDo.setPurchaseStatus(Currency.CNY.getCode().equals(fiCbPaySettleBankDo.getSettleCcy()) ? 1 : 0);
        fiCbPayRemittanceDo.setRemitMoney(Currency.CNY.getCode().equals(fiCbPaySettleBankDo.getSettleCcy()) ? tradeAmt : BigDecimal.ZERO);
        return fiCbPayRemittanceDo;
    }

    /**
     * 跨境订单接口请求参数转换成Biz层请求参数信息
     *
     * @param memberId            商户号
     * @param batchNo             批次号
     * @param fiCbPaySettleBankDo 商户币总账户信息
     * @return Biz层请求参数信息
     */
    public static FiCbPayRemittanceAdditionDo remittanceAdditionParamConvert(Long memberId, String createBy, String updateBy, String batchNo,
                                                                             FiCbPaySettleBankDo fiCbPaySettleBankDo, Long dfsFileId) {
        FiCbPayRemittanceAdditionDo additionDo = new FiCbPayRemittanceAdditionDo();
        additionDo.setBatchNo(batchNo);
        additionDo.setMemberNo(memberId);
        additionDo.setOceanic(fiCbPaySettleBankDo.getOceanicFlag());
        additionDo.setDfsFileId(dfsFileId);
        additionDo.setCostBorne(fiCbPaySettleBankDo.getCostBorne());
        additionDo.setCreateBy(createBy);
        additionDo.setUpdateBy(updateBy);
        additionDo.setBankName(fiCbPaySettleBankDo.getBankName());
        additionDo.setBankAccName(fiCbPaySettleBankDo.getBankAccName());
        additionDo.setBankAccNo(fiCbPaySettleBankDo.getBankAccNo());
        additionDo.setEntityNo(fiCbPaySettleBankDo.getEntityNo());
        return additionDo;
    }

    /**
     * 构建DFS信息
     *
     * @param batchNo              批次号
     * @param cbPayRemittanceReqBo 跨境人民币汇款信息请求参数
     * @param auditStatus          审核状态
     * @param auditBy              审核人
     * @return 返回初始化之后结果信息
     */
    public static CbPayRemittanceAuditReqBo auditParamConvert(String batchNo, CbPayRemittanceReqBo cbPayRemittanceReqBo,
                                                              String auditStatus, String auditBy) {
        CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo = new CbPayRemittanceAuditReqBo();
        cbPayRemittanceAuditReqBo.setBatchNo(batchNo);
        cbPayRemittanceAuditReqBo.setAuditBy(auditBy);
        cbPayRemittanceAuditReqBo.setAuditStatus(auditStatus);
        cbPayRemittanceAuditReqBo.setMemberId(cbPayRemittanceReqBo.getMemberId());
        cbPayRemittanceAuditReqBo.setTargetCcy(cbPayRemittanceReqBo.getTargetCcy());

        return cbPayRemittanceAuditReqBo;
    }

    /**
     * 购汇人民币备付金变动上报央行
     *
     * @param cgwRemitResultDto   响应结果
     * @param fiCbPayRemittanceDo 汇款信息
     * @param fiCbPaySettleBankDo 账户信息
     * @return 返回通知央行的MQ对象
     */
    public static RSIS150 exchangeNotifyCentralBankConvert(CgwRemitResultDto cgwRemitResultDto,
                                                           FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                                           FiCbPaySettleBankDo fiCbPaySettleBankDo) {

        RSIS150 rsis150 = new RSIS150();
        CgwExChangeRespDto cgwExChangeRespDto = cgwRemitResultDto.getCgwExChangeRespDto();
        //宝付订单号
        rsis150.setOrderId(fiCbPayRemittanceDo.getBatchNo());
        if (fiCbPayRemittanceDo.getBusinessNo() != null) {
            //业务流水号
            rsis150.setBusinessNo(fiCbPayRemittanceDo.getBusinessNo());
        } else {
            //没有的话填宝付订单号
            rsis150.setBusinessNo(fiCbPayRemittanceDo.getBatchNo());
        }
        //出金
        rsis150.setDepositOrWithdraw("01");
        //柜台号，取渠道ID的前7位
        rsis150.setCounterId(fiCbPayRemittanceDo.getChannelId().toString().substring(0, 7));
        //渠道号
        rsis150.setChannelId(fiCbPayRemittanceDo.getChannelId().toString());
        //成功时间
        rsis150.setSuccTime(cgwExChangeRespDto.getExchDate());
        //银行交易流水号
        rsis150.setBankSeqNO(cgwExChangeRespDto.getExchNumber());
        //出金的时候填商户号，入金的时候填银行流水号
        rsis150.setPayerInfo(fiCbPayRemittanceDo.getMemberNo().toString());
        //出金的时候填收款人帐号，入金的时候填商户号
        rsis150.setPayeeInfo(fiCbPaySettleBankDo.getBankAccNo());
        //交易金额
        rsis150.setAmount(cgwExChangeRespDto.getExcHCnyAmt());
        //只有付汇的时候才有渠道成本
        rsis150.setBankFee(BigDecimal.ZERO);
        return rsis150;
    }

    /**
     * 付汇人民币备付金变动上报央行
     *
     * @param cgwRemitResultDto   响应结果
     * @param fiCbPayRemittanceDo 汇款信息
     * @param fiCbPaySettleBankDo 账户信息
     * @param channelFee          渠道成本
     * @return 返回通知央行的MQ对象
     */
    public static RSIS150 remitNotifyCentralBankConvert(CgwRemitResultDto cgwRemitResultDto,
                                                        FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                                        FiCbPaySettleBankDo fiCbPaySettleBankDo, BigDecimal channelFee) {

        RSIS150 rsis150 = new RSIS150();
        CgwRemitRespDto cgwRemitRespDto = cgwRemitResultDto.getCgwRemitRespDto();
        //宝付订单号
        rsis150.setOrderId(fiCbPayRemittanceDo.getBatchNo());
        if (fiCbPayRemittanceDo.getBusinessNo() != null) {
            //业务流水号
            rsis150.setBusinessNo(fiCbPayRemittanceDo.getBusinessNo());
        } else {
            //没有的话填宝付订单号
            rsis150.setBusinessNo(fiCbPayRemittanceDo.getBatchNo());
        }
        //出金
        rsis150.setDepositOrWithdraw("01");
        //柜台号，取渠道ID的前7位
        rsis150.setCounterId(fiCbPayRemittanceDo.getChannelId().toString().substring(0, 7));
        //渠道号
        rsis150.setChannelId(fiCbPayRemittanceDo.getChannelId().toString());
        //成功时间
        rsis150.setSuccTime(cgwRemitRespDto.getProcessTime());
        //银行交易流水号
        rsis150.setBankSeqNO(cgwRemitRespDto.getRemitNo());
        //出金的时候填商户号，入金的时候填银行流水号
        rsis150.setPayerInfo(fiCbPayRemittanceDo.getMemberNo().toString());
        //出金的时候填收款人帐号，入金的时候填商户号
        rsis150.setPayeeInfo(fiCbPaySettleBankDo.getBankAccNo());
        //交易金额
        rsis150.setAmount(cgwRemitRespDto.getRemitMoney());
        //只有付汇的时候才有渠道成本
        rsis150.setBankFee(channelFee);
        return rsis150;
    }

    /**
     * 结汇人民币备付金变动上报央行
     *
     * @param cgwSettleResultDto 结汇结果信息
     * @param fiCbPaySettleDo    结汇Do信息
     * @return 返回通知央行的MQ对象
     */
    public static RSIS150 settleNotifyCentralBankConvert(CgwSettleResultDto cgwSettleResultDto, FiCbPaySettleDo fiCbPaySettleDo) {

        RSIS150 rsis150 = new RSIS150();
        CgwSettleRespDto cgwSettleRespDto = cgwSettleResultDto.getCgwSettleRespDto();
        //宝付订单号
        rsis150.setOrderId(cgwSettleResultDto.getRemSerialNo());
        //业务流水号
        rsis150.setBusinessNo(cgwSettleResultDto.getRemSerialNo());
        //入金
        rsis150.setDepositOrWithdraw("02");
        //柜台号，取渠道ID的前7位
        rsis150.setCounterId(fiCbPaySettleDo.getChannelId().toString().substring(0, 7));
        //渠道号
        rsis150.setChannelId(fiCbPaySettleDo.getChannelId().toString());
        //成功时间
        rsis150.setSuccTime(cgwSettleRespDto.getSuccessDate());
        //银行交易流水号
        rsis150.setBankSeqNO(cgwSettleRespDto.getSettleNo());
        //出金的时候填商户号，入金的时候填银行流水号
        rsis150.setPayerInfo(cgwSettleRespDto.getSettleNo());
        //出金的时候填收款人帐号，入金的时候填商户号
        rsis150.setPayeeInfo(fiCbPaySettleDo.getMemberId().toString());
        //交易金额
        rsis150.setAmount(cgwSettleRespDto.getCnyMoney());
        //只有付汇的时候才有渠道成本
        rsis150.setBankFee(BigDecimal.ZERO);
        return rsis150;
    }
}
