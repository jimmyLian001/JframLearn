package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.model.CacheCBMemberBankDto;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.cbcgw.facade.dto.gw.request.*;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofu.cbpayservice.biz.models.CbPaySettleBo;
import com.baofu.cbpayservice.biz.models.CgwRemitReqDetailDto;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.CareerTypeEnum;
import com.baofu.cbpayservice.common.enums.CostBorneEnum;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.dal.models.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 清算参数信息转换
 * <p>
 * 1、清算记账参数信息转换
 * 2、计费参数转换
 * </p>
 * User: wanght Date:2016/11/11 ProjectName: cbpay-service Version: 1.0
 */
public class DfsParamConvert {

    /**
     * 清算记账参数信息转换
     *
     * @param filePath 文件路径
     * @param length   长度
     * @param fileName 文件名称
     * @param memberId 商户id
     * @return 转换信息
     */
    public static InsertReqDTO dfsRequestConvert(String filePath, int length, String fileName, String memberId) {

        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setFilePath(filePath);
        insertReqDTO.setFileSize(length);
        insertReqDTO.setFileName(fileName);
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        insertReqDTO.setRemark("商户汇款订单明细");
        insertReqDTO.setOrgCode(memberId);
        insertReqDTO.setFileDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));

        return insertReqDTO;
    }

    /**
     * 付汇申请参数信息转换
     *
     * @return 转换信息
     */
    public static CgwSumRemitReqDto channelRequestConvert(FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                                          FiCbPaySettleBankDo fiCbPaySettleBankDo,
                                                          FiCbPayRemittanceAdditionDo additionDo) {
        CgwSumRemitReqDto cgwSumRemitReqDO = new CgwSumRemitReqDto();

        if (Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) { //跨境人民币
            cgwSumRemitReqDO.setMode(3);
        } else {
            if (fiCbPaySettleBankDo.getExchangeType() == 1) { //定额外币
                cgwSumRemitReqDO.setMode(1);
            } else {
                cgwSumRemitReqDO.setMode(2);
            }
        }
        cgwSumRemitReqDO.setAmount(fiCbPayRemittanceDo.getSystemMoney());
        cgwSumRemitReqDO.setDebitCurrency(fiCbPayRemittanceDo.getSystemCcy());
        cgwSumRemitReqDO.setPayeeAccNo(fiCbPaySettleBankDo.getBankAccNo());
        cgwSumRemitReqDO.setPayeeAddress(fiCbPaySettleBankDo.getPayeeAddress());
        cgwSumRemitReqDO.setPayeeBicCode(fiCbPayRemittanceDo.getSwiftCode());
        //交易时实时加一个空格，防止拼接过后再分割的单词长度超过35
        cgwSumRemitReqDO.setPayeeName(fiCbPaySettleBankDo.getBankAccName() + " ");
        cgwSumRemitReqDO.setPayeeMemberNo(String.valueOf(additionDo.getMemberNo()));
        cgwSumRemitReqDO.setBfBatchId(fiCbPayRemittanceDo.getBatchNo());
        cgwSumRemitReqDO.setChannelId(fiCbPayRemittanceDo.getChannelId().intValue());
        cgwSumRemitReqDO.setClearBankBic(fiCbPaySettleBankDo.getMiddleBankCode());
        cgwSumRemitReqDO.setFileId(additionDo.getDfsFileId());
        cgwSumRemitReqDO.setCostBearer(CostBorneEnum.getCostBorne(additionDo.getCostBorne()));
        cgwSumRemitReqDO.setCnyAmount(fiCbPayRemittanceDo.getTransMoney());
        cgwSumRemitReqDO.setChildMemberId(additionDo.getEntityNo());

        return cgwSumRemitReqDO;
    }

    /**
     * 付汇申请参数信息转换
     *
     * @return 转换信息
     */
    public static CgwSumRemitReqDto remitRequestConvert(FiCbPayRemittanceDo fiCbPayRemittanceDo,
                                                        FiCbPaySettleBankDo fiCbPaySettleBankDo,
                                                        FiCbPayRemittanceAdditionDo additionDo) {
        CgwSumRemitReqDto cgwSumRemitReqDO = new CgwSumRemitReqDto();
        //跨境人民币
        if (Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) {
            cgwSumRemitReqDO.setMode(3);
        } else {
            cgwSumRemitReqDO.setMode(fiCbPaySettleBankDo.getExchangeType() == 1 ? 1 : 2);
        }
        cgwSumRemitReqDO.setAmount(fiCbPayRemittanceDo.getRemitMoney());
        cgwSumRemitReqDO.setDebitCurrency(fiCbPayRemittanceDo.getSystemCcy());
        cgwSumRemitReqDO.setPayeeAccNo(fiCbPaySettleBankDo.getBankAccNo());
        cgwSumRemitReqDO.setPayeeAddress(fiCbPaySettleBankDo.getPayeeAddress());
        cgwSumRemitReqDO.setPayeeBicCode(fiCbPayRemittanceDo.getSwiftCode());
        cgwSumRemitReqDO.setPayeeName(fiCbPaySettleBankDo.getBankAccName());
        cgwSumRemitReqDO.setPayeeMemberNo(String.valueOf(additionDo.getMemberNo()));
        cgwSumRemitReqDO.setBfBatchId(fiCbPayRemittanceDo.getBatchNo());
        cgwSumRemitReqDO.setChannelId(fiCbPayRemittanceDo.getChannelId().intValue());
        cgwSumRemitReqDO.setClearBankBic(fiCbPaySettleBankDo.getMiddleBankCode());
        cgwSumRemitReqDO.setFileId(additionDo.getDfsFileId());
        cgwSumRemitReqDO.setCostBearer(CostBorneEnum.getCostBorne(additionDo.getCostBorne()));
        cgwSumRemitReqDO.setCnyAmount(fiCbPayRemittanceDo.getTransMoney());
        cgwSumRemitReqDO.setChildMemberId(additionDo.getEntityNo());

        return cgwSumRemitReqDO;
    }

    /**
     * 清算记账参数信息转换
     *
     * @param batchNno                  批次号
     * @param fiCbPayRemittanceDetailDo 汇款批次明细
     * @param fiCbPaySettleBankDo       结算账户
     * @param fiCbPayRemittanceDo       汇款批次
     * @param rate                      汇率
     * @return 转换信息
     */
    public static CgwRemitReqDetailDto channelDetailRequestConvert(String batchNno,
                                                                   List<FiCbPayRemittanceDetailV2Do> fiCbPayRemittanceDetailDo,
                                                                   FiCbPaySettleBankDo fiCbPaySettleBankDo,
                                                                   FiCbPayRemittanceDo fiCbPayRemittanceDo, BigDecimal rate) {
        CgwRemitReqDetailDto cgwRemitReqDetailDto = new CgwRemitReqDetailDto();
        List<CgwRemitReqDto> cgwRemitReqDOList = new ArrayList<>();
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (FiCbPayRemittanceDetailV2Do detailDo : fiCbPayRemittanceDetailDo) {
            CgwRemitReqDto cgwRemitReqDO = new CgwRemitReqDto();
            cgwRemitReqDO.setBfBatchId(batchNno);
            cgwRemitReqDO.setChannelId(fiCbPayRemittanceDo.getChannelId().intValue());
            cgwRemitReqDO.setMemberId(String.valueOf(fiCbPaySettleBankDo.getMemberId()));
            // ExchangeType 0：定额人民币 1：定额外币
            if (Currency.CNY.getCode().equals(fiCbPayRemittanceDo.getSystemCcy())) {
                cgwRemitReqDO.setMode(3);
            } else {
                cgwRemitReqDO.setMode(fiCbPaySettleBankDo.getExchangeType() == 1 ? 1 : 2);
            }

            int scale = 2;
            // 韩元和日元取整
            if (Constants.JPY_CURRENCY.equals(fiCbPayRemittanceDo.getSystemCcy())
                    || Constants.KRW_CURRENCY.equals(fiCbPayRemittanceDo.getSystemCcy())) {
                scale = 0;
            }

            BigDecimal remitMoney = detailDo.getTransMoney().divide(rate, scale, BigDecimal.ROUND_DOWN);
            //判断通过汇率转换之后的外币金额是否等于0，购汇方式是否为订单外币，条件都匹配时此笔订单明细不发送银行
            if (BigDecimal.ZERO.compareTo(remitMoney) == 0 && fiCbPaySettleBankDo.getExchangeType() == 1) {
                continue;
            }

            cgwRemitReqDO.setRemitMoney(remitMoney);
            cgwRemitReqDO.setCnyAmount(detailDo.getTransMoney());

            cgwRemitReqDO.setCommodityInfo(detailDo.getCommodityInfo() == null ?
                    Constants.BAOFOO_COMMODITY_NAME + ",1," + cgwRemitReqDO.getRemitMoney() : detailDo.getCommodityInfo());
            cgwRemitReqDO.setOrderId(detailDo.getOrderId());
            cgwRemitReqDO.setPayerName(detailDo.getIdHolder());
            cgwRemitReqDO.setPayerAccNo(SecurityUtil.desDecrypt(detailDo.getBankCardNo(),
                    Constants.CARD_DES_PASSWD));
            cgwRemitReqDO.setPayerAddress("pudong Shang hai China");
            cgwRemitReqDO.setPayerBankName("");
            cgwRemitReqDO.setRemitMemberNo(String.valueOf(fiCbPaySettleBankDo.getMemberId()));
            cgwRemitReqDO.setRemitAccType("01");
            cgwRemitReqDO.setRemitCertNo(SecurityUtil.desDecrypt(detailDo.getIdCardNo(),
                    Constants.CARD_DES_PASSWD));
            cgwRemitReqDO.setRemitCertType(detailDo.getPayeeIdType() == 1 ? "01" : "02");
            cgwRemitReqDO.setTransDate(detailDo.getTransTime());

            cgwRemitReqDO.setRecVAccAddress(fiCbPaySettleBankDo.getPayeeAddress());
            cgwRemitReqDO.setRecVAccBicCode(fiCbPayRemittanceDo.getSwiftCode());
            //by feng_jiang 2017/06/30
            cgwRemitReqDO.setRecVAccCountry(fiCbPaySettleBankDo.getCountryCode() == null ? "" : fiCbPaySettleBankDo.getCountryCode());
            cgwRemitReqDO.setCountryCode(fiCbPaySettleBankDo.getCountryCode() == null ? "" : fiCbPaySettleBankDo.getCountryCode());
            cgwRemitReqDO.setRecVAccCurrency(fiCbPayRemittanceDo.getSystemCcy());
            //交易时实时加一个空格，防止拼接过后再分割的单词长度超过35
            cgwRemitReqDO.setRecVAccName(fiCbPaySettleBankDo.getBankAccName() + " ");
            cgwRemitReqDO.setRecVAccNo(fiCbPaySettleBankDo.getBankAccNo());
            cgwRemitReqDO.setRemitCurrency(fiCbPayRemittanceDo.getSystemCcy());
            cgwRemitReqDO.setCostBorne(CostBorneEnum.getCostBorne(fiCbPaySettleBankDo.getCostBorne()));
            //D:2  对私，C:1  对公
            cgwRemitReqDO.setPayerType(String.valueOf(detailDo.getPayeeIdType()));
            cgwRemitReqDO.setChildMemberId(fiCbPaySettleBankDo.getEntityNo());
            //根据新字段求和
            sumAmt = sumAmt.add(cgwRemitReqDO.getRemitMoney());

            //行业类型
            if (StringUtils.isBlank(detailDo.getCareerType())) {
                //默认为01
                cgwRemitReqDO.setTranType(CareerTypeEnum.GOODS_TRADE.getCode());

            } else if (CareerTypeEnum.HOTEL.getCode().equals(detailDo.getCareerType())) {
                //酒店和机票购汇类型都填写02
                cgwRemitReqDO.setTranType(CareerTypeEnum.AIR_TICKETS.getCode());

            } else {
                cgwRemitReqDO.setTranType(detailDo.getCareerType());
            }
            //反洗钱状态： 0：初始 1：审核通过
            cgwRemitReqDO.setRecord(detailDo.getAmlStatus());
            cgwRemitReqDOList.add(cgwRemitReqDO);
        }

        cgwRemitReqDetailDto.setCgwRemitReqDtoList(cgwRemitReqDOList);
        cgwRemitReqDetailDto.setRemitSumAmt(sumAmt);
        return cgwRemitReqDetailDto;
    }

    /**
     * 购汇汇总信息转换
     *
     * @param fiCbPayRemittanceDo 请求参数
     * @return 转换参数
     */
    public static CgwSumExchangeReqDto convertToPurchase(FiCbPayRemittanceDo fiCbPayRemittanceDo, Long dfsFileId,
                                                         FiCbPaySettleBankDo fiCbPaySettleBankDo, BigDecimal sumAmt) {

        CgwSumExchangeReqDto cgwSumExchangeReq = new CgwSumExchangeReqDto();
        cgwSumExchangeReq.setChannelId(fiCbPayRemittanceDo.getChannelId().intValue());
        cgwSumExchangeReq.setMemberId(String.valueOf(fiCbPaySettleBankDo.getMemberId()));
        cgwSumExchangeReq.setBfBatchId(fiCbPayRemittanceDo.getBatchNo());
        if (fiCbPaySettleBankDo.getExchangeType() == 1) {
            cgwSumExchangeReq.setExModel("1");
        } else {
            // mode 1：定额外币 2：定额人民币 3：跨境人民币
            cgwSumExchangeReq.setExModel("2");
        }
        cgwSumExchangeReq.setCnyMoney(fiCbPayRemittanceDo.getTransMoney());
        cgwSumExchangeReq.setExCnyMoney(fiCbPayRemittanceDo.getTransMoney());
        cgwSumExchangeReq.setExForeignCurrency(fiCbPayRemittanceDo.getSystemCcy());
        cgwSumExchangeReq.setFileId(dfsFileId);
        cgwSumExchangeReq.setForeignMoney(sumAmt);
        cgwSumExchangeReq.setChildMemberId(fiCbPaySettleBankDo.getEntityNo());

        return cgwSumExchangeReq;
    }

    /**
     * 结汇申请明细转换
     *
     * @param fiCbPaySettleOrderDo
     * @return
     */
    public static CgwSettleReqDto convertToSettlementReq(CbPaySettleBo cbPaySettleBo,
                                                         FiCbPaySettleOrderDo fiCbPaySettleOrderDo,
                                                         FiCbPaySettleApplyDo fiCbPaySettleApplyDo,
                                                         List<FiCbPayOrderItemInfoDo> orderItems,
                                                         CacheCBMemberBankDto CacheCBMemberBankDto) {
        if (fiCbPaySettleOrderDo == null) {
            return null;
        }
        CgwSettleReqDto cgwSettleReqDto = new CgwSettleReqDto();
        cgwSettleReqDto.setTransDate(new Date());

        cgwSettleReqDto.setPayerMemberNo(fiCbPaySettleOrderDo.getMemberId().toString());
        cgwSettleReqDto.setPayerCountry(fiCbPaySettleApplyDo.getRemittanceCountry());
        cgwSettleReqDto.setPayerName(CacheCBMemberBankDto.getAccountName());
        cgwSettleReqDto.setPayerAccNo(CacheCBMemberBankDto.getBankAccount());

        cgwSettleReqDto.setPayeeType("P");
        cgwSettleReqDto.setPayeeCertType(fiCbPaySettleOrderDo.getPayeeIdType().toString());
        cgwSettleReqDto.setPayeeCertNo(SecurityUtil.desDecrypt(fiCbPaySettleOrderDo.getPayeeIdNo(),
                Constants.CARD_DES_PASSWD));
        cgwSettleReqDto.setPayeeName(fiCbPaySettleOrderDo.getPayeeName());
        cgwSettleReqDto.setPayeeAccNo(SecurityUtil.desDecrypt(fiCbPaySettleOrderDo.getPayeeAccNo(), Constants.CARD_DES_PASSWD));

        cgwSettleReqDto.setTransCurrency(fiCbPaySettleOrderDo.getOrderCcy());
        cgwSettleReqDto.setTransMoney(fiCbPaySettleOrderDo.getOrderAmt());
        cgwSettleReqDto.setSettlePropertyCode("110");
        cgwSettleReqDto.setBfBatchId(fiCbPaySettleOrderDo.getOrderId().toString());
        cgwSettleReqDto.setFlowRemitNumber(cbPaySettleBo.getIncomeNo());
        cgwSettleReqDto.setPayeeAccNoType("01");
        cgwSettleReqDto.setSettleFundType("02");

        cgwSettleReqDto.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        cgwSettleReqDto.setChannelId(cbPaySettleBo.getChannelId().intValue());
        cgwSettleReqDto.setMemberId(cbPaySettleBo.getMemberId().toString());
        cgwSettleReqDto.setOrderId(fiCbPaySettleOrderDo.getOrderId());
        cgwSettleReqDto.setRemark(fiCbPaySettleOrderDo.getRemarks());
        cgwSettleReqDto.setTranType(CareerTypeEnum.GOODS_TRADE.getCode());
        //D:2  对私，C:1  对公
        cgwSettleReqDto.setPayerType(String.valueOf(fiCbPaySettleOrderDo.getPayeeIdType()));
        //结汇不存在部分反洗钱成功的文件批次再次进行结汇
        cgwSettleReqDto.setRecord(BigDecimal.ZERO.intValue());
        //组装商品信息
        StringBuffer goodsInfo = new StringBuffer();
        for (FiCbPayOrderItemInfoDo fiCbPayOrderItemInfoDo : orderItems) {
            goodsInfo.append(String.format("%s,%s,%s;", fiCbPayOrderItemInfoDo.getCommodityName(),
                    fiCbPayOrderItemInfoDo.getCommodityAmount(), fiCbPayOrderItemInfoDo.getCommodityPrice()));
        }
        cgwSettleReqDto.setCommodityInfo(goodsInfo.toString());

        return cgwSettleReqDto;
    }

    /**
     * 组装结汇申请信息
     *
     * @param cbPaySettleBo 结算订单
     * @param fileId        DSF文件ID
     * @return CgwSumSettleReqDto 结汇申请信息
     */
    public static CgwSumSettleReqDto convertToCgwSumSettleReq(CbPaySettleBo cbPaySettleBo, Long fileId) {
        CgwSumSettleReqDto cgwSumSettleReqDto = new CgwSumSettleReqDto();
        cgwSumSettleReqDto.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        cgwSumSettleReqDto.setChannelId(cbPaySettleBo.getChannelId().intValue());
        cgwSumSettleReqDto.setFileId(fileId);
        cgwSumSettleReqDto.setBusinessType(1); //1-贸易
        cgwSumSettleReqDto.setBfBatchId(cbPaySettleBo.getOrderId().toString());
        cgwSumSettleReqDto.setSettleCurrency(cbPaySettleBo.getIncomeCcy());
        cgwSumSettleReqDto.setSettleMoney(cbPaySettleBo.getIncomeAmt());
        return cgwSumSettleReqDto;
    }

    /**
     * 组装结汇解付信息
     */
    public static CgwRelieveReqDto convertToCgwRelieveReqDto(FiCbPaySettleOrderDo fiCbPaySettleOrderDo,
                                                             FiCbPaySettleApplyDo fiCbPaySettleApplyDo,
                                                             FiCbPaySettleDo fiCbPaySettleDo, CacheMemberDto cacheMemberDto) {

        CgwRelieveReqDto cgwRelieveReqDto = new CgwRelieveReqDto();
        cgwRelieveReqDto.setBankReturnNo(fiCbPaySettleDo.getIncomeNo());
        cgwRelieveReqDto.setMemberNo(String.valueOf(fiCbPaySettleDo.getMemberId()));
        cgwRelieveReqDto.setPayeeCertNo(SecurityUtil.desDecrypt(fiCbPaySettleOrderDo.getPayeeIdNo(), Constants.CARD_DES_PASSWD));
        cgwRelieveReqDto.setPayeeAccNo(SecurityUtil.desDecrypt(fiCbPaySettleOrderDo.getPayeeAccNo(), Constants.CARD_DES_PASSWD));
        cgwRelieveReqDto.setPayeeName(fiCbPaySettleOrderDo.getPayeeName());
        cgwRelieveReqDto.setPayerName(cacheMemberDto.getName());
        cgwRelieveReqDto.setRemitCurrency(fiCbPaySettleDo.getIncomeCcy());
        cgwRelieveReqDto.setRemitMoney(fiCbPaySettleOrderDo.getOrderAmt());
        cgwRelieveReqDto.setOrderId(fiCbPaySettleOrderDo.getOrderId());
        cgwRelieveReqDto.setCommodityInfo(fiCbPaySettleOrderDo.getCommodityInfo());
        if (cgwRelieveReqDto.getCommodityInfo() == null) {
            cgwRelieveReqDto.setCommodityInfo("宝付商品");
        }
        //付款人常驻国家
        cgwRelieveReqDto.setCountryCode(fiCbPaySettleApplyDo.getRemittanceCountry());
        //申请人
        cgwRelieveReqDto.setProposer("宝付网络科技");
        //申请人电话
        cgwRelieveReqDto.setProposerTel("021-68811008");
        //D:2  对私，C:1  对公
        cgwRelieveReqDto.setPayerType(String.valueOf(fiCbPaySettleOrderDo.getPayeeIdType()));
        return cgwRelieveReqDto;
    }

    public static QueryReqDTO queryParamConvert(Long fileId) {
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(fileId);
        reqDTO.setOperation(Operation.QUERY);
        return reqDTO;
    }
}
