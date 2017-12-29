package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwReceiptRepairReqDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwSumRelieveReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwSettleRespDto;
import com.baofu.cbpayservice.biz.models.CbPaySettleBo;
import com.baofu.cbpayservice.biz.models.CollectionMakeupBo;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 回调信息组装对象
 * <p>
 * </p>
 * User: 康志光 Date: 2017/4/17 ProjectName: cbpay-customs-service Version: 1.0
 */
public class CbPaySettleBoConvert {

    /**
     * 组装结汇订单状态
     *
     * @param orderId
     * @param settleStatus
     * @param incomeStatus
     * @param oldSettleStatus
     * @param oldIncomeStatus
     * @return
     */
    public static CbPaySettleBo convert(Long orderId, Integer settleStatus, Integer incomeStatus,
                                        Integer oldSettleStatus, Integer oldIncomeStatus) {

        CbPaySettleBo cbPaySettleCallbackReqBo = new CbPaySettleBo();
        cbPaySettleCallbackReqBo.setOrderId(orderId);
        cbPaySettleCallbackReqBo.setSettleStatus(settleStatus);
        cbPaySettleCallbackReqBo.setIncomeStatus(incomeStatus);
        cbPaySettleCallbackReqBo.setOldSettleStatus(oldSettleStatus);
        cbPaySettleCallbackReqBo.setOldIncomeStatus(oldIncomeStatus);

        return cbPaySettleCallbackReqBo;
    }

    /**
     * 组装结汇订单结汇人民币信息
     *
     * @param orderId
     * @param settleRate
     * @param settleCcy
     * @param settleAmt
     * @param settleStatus
     * @param incomeStatus
     * @param oldSettleStatus
     * @param oldIncomeStatus
     * @return
     */
    public static CbPaySettleBo convert(Long orderId, BigDecimal settleRate, String settleCcy, BigDecimal settleAmt,
                                        Integer settleStatus, Integer incomeStatus,
                                        Integer oldSettleStatus, Integer oldIncomeStatus) {

        CbPaySettleBo cbPaySettleCallbackReqBo = new CbPaySettleBo();
        cbPaySettleCallbackReqBo.setOrderId(orderId);
        cbPaySettleCallbackReqBo.setSettleRate(settleRate);
        cbPaySettleCallbackReqBo.setSettleAmt(settleAmt);
        cbPaySettleCallbackReqBo.setSettleCcy(settleCcy);
        cbPaySettleCallbackReqBo.setSettleStatus(settleStatus);
        cbPaySettleCallbackReqBo.setIncomeStatus(incomeStatus);
        cbPaySettleCallbackReqBo.setOldSettleStatus(oldSettleStatus);
        cbPaySettleCallbackReqBo.setOldIncomeStatus(oldIncomeStatus);

        return cbPaySettleCallbackReqBo;
    }

    /**
     * 组装结汇订单结汇人民币信息
     *
     * @param orderId          宝付id
     * @param settleRate       银行结汇汇率
     * @param settleCcy        结汇币种
     * @param settleAmt        银行结汇金额
     * @param settleStatus     结算状态
     * @param incomeStatus     解付状态
     * @param oldSettleStatus  前结算状态
     * @param oldIncomeStatus  前解付状态
     * @param memberSettleAmt  商户结汇金额
     * @param memberSettleRate 商户结算汇率
     * @param profitAndLoss    宝付损益
     * @return
     */
    public static CbPaySettleBo convert(Long orderId, BigDecimal settleRate, String settleCcy, BigDecimal settleAmt,
                                        Integer settleStatus, Integer incomeStatus,
                                        Integer oldSettleStatus, Integer oldIncomeStatus, BigDecimal memberSettleAmt,
                                        BigDecimal memberSettleRate, BigDecimal profitAndLoss) {

        CbPaySettleBo cbPaySettleCallbackReqBo = new CbPaySettleBo();
        cbPaySettleCallbackReqBo.setOrderId(orderId);
        cbPaySettleCallbackReqBo.setSettleRate(settleRate);
        cbPaySettleCallbackReqBo.setSettleAmt(settleAmt);
        cbPaySettleCallbackReqBo.setSettleCcy(settleCcy);
        cbPaySettleCallbackReqBo.setSettleStatus(settleStatus);
        cbPaySettleCallbackReqBo.setIncomeStatus(incomeStatus);
        cbPaySettleCallbackReqBo.setOldSettleStatus(oldSettleStatus);
        cbPaySettleCallbackReqBo.setOldIncomeStatus(oldIncomeStatus);
        cbPaySettleCallbackReqBo.setMemberSettleAmt(memberSettleAmt);
        cbPaySettleCallbackReqBo.setMemberSettleRate(memberSettleRate);
        cbPaySettleCallbackReqBo.setProfitAndLoss(profitAndLoss);

        return cbPaySettleCallbackReqBo;
    }

    /**
     * 组装结汇订单清算信息
     *
     * @param orderId          宝付结汇订单ID
     * @param settleAt         清算申请时间
     * @param settleCompleteAt 清算完成时间
     * @param settleFlag       清算标识
     * @param settleFee        清算手续费，指订单的人民币手续费
     * @param settleStatus     结汇状态
     * @param oldSettleStatus  前结汇状态
     * @param oldIncomeStatus  前解付状态
     * @return CbPaySettleBo
     */
    public static CbPaySettleBo convert(Long orderId, Date settleAt, Date settleCompleteAt, int settleFlag, BigDecimal settleFee,
                                        Integer settleStatus, Integer oldSettleStatus, Integer oldIncomeStatus) {

        CbPaySettleBo cbPaySettleCallbackReqBo = new CbPaySettleBo();
        cbPaySettleCallbackReqBo.setOrderId(orderId);
        cbPaySettleCallbackReqBo.setSettleAt(settleAt);
        cbPaySettleCallbackReqBo.setSettleCompleteAt(settleCompleteAt);
        cbPaySettleCallbackReqBo.setSettleFlag(settleFlag);
        cbPaySettleCallbackReqBo.setSettleFee(settleFee);
        cbPaySettleCallbackReqBo.setSettleStatus(settleStatus);
        cbPaySettleCallbackReqBo.setOldSettleStatus(oldSettleStatus);
        cbPaySettleCallbackReqBo.setOldIncomeStatus(oldIncomeStatus);

        return cbPaySettleCallbackReqBo;
    }

    /**
     * BO转DO
     *
     * @param fiCbPaySettleDo 结汇DO
     * @return CbPaySettleBo
     */
    public static CbPaySettleBo convert(FiCbPaySettleDo fiCbPaySettleDo) {

        CbPaySettleBo cbPaySettleBo = new CbPaySettleBo();
        cbPaySettleBo.setMemberId(fiCbPaySettleDo.getMemberId());
        cbPaySettleBo.setOrderId(fiCbPaySettleDo.getOrderId());
        cbPaySettleBo.setChannelId(fiCbPaySettleDo.getChannelId());
        cbPaySettleBo.setIncomeNo(fiCbPaySettleDo.getIncomeNo());
        cbPaySettleBo.setIncomeAmt(fiCbPaySettleDo.getIncomeAmt());
        cbPaySettleBo.setRealIncomeAmt(fiCbPaySettleDo.getRealIncomeAmt());
        cbPaySettleBo.setIncomeCcy(fiCbPaySettleDo.getIncomeCcy());
        cbPaySettleBo.setIncomeStatus(fiCbPaySettleDo.getIncomeStatus());
        cbPaySettleBo.setOldIncomeStatus(fiCbPaySettleDo.getOldIncomeStatus());
        cbPaySettleBo.setSettleAmt(fiCbPaySettleDo.getSettleAmt());
        cbPaySettleBo.setSettleCcy(fiCbPaySettleDo.getSettleCcy());
        cbPaySettleBo.setSettleRate(fiCbPaySettleDo.getSettleRate());
        cbPaySettleBo.setSettleStatus(fiCbPaySettleDo.getSettleStatus());
        cbPaySettleBo.setOldSettleStatus(fiCbPaySettleDo.getOldSettleStatus());
        cbPaySettleBo.setIncomeFee(fiCbPaySettleDo.getIncomeFee());
        cbPaySettleBo.setSettleAt(fiCbPaySettleDo.getSettleAt());
        cbPaySettleBo.setSettleCompleteAt(fiCbPaySettleDo.getSettleCompleteAt());
        cbPaySettleBo.setSettleFlag(fiCbPaySettleDo.getSettleFlag());
        cbPaySettleBo.setSettleFee(fiCbPaySettleDo.getSettleFee());
        cbPaySettleBo.setFileBatchNo(fiCbPaySettleDo.getFileBatchNo());
        return cbPaySettleBo;
    }

    /**
     * BO转DO
     *
     * @param fiCbPaySettleDo       结汇DO
     * @param cgwSettleBatchRespDto 响应信息
     * @return CbPaySettleBo
     */
    public static CbPaySettleBo convert(FiCbPaySettleDo fiCbPaySettleDo, CgwSettleRespDto cgwSettleBatchRespDto) {
        if (fiCbPaySettleDo == null) {
            return null;
        }
        CbPaySettleBo cbPaySettleBo = new CbPaySettleBo();
        cbPaySettleBo.setMemberId(fiCbPaySettleDo.getMemberId());
        cbPaySettleBo.setOrderId(fiCbPaySettleDo.getOrderId());
        cbPaySettleBo.setChannelId(fiCbPaySettleDo.getChannelId());
        cbPaySettleBo.setIncomeNo(fiCbPaySettleDo.getIncomeNo());
        cbPaySettleBo.setIncomeAmt(fiCbPaySettleDo.getIncomeAmt());
        cbPaySettleBo.setRealIncomeAmt(fiCbPaySettleDo.getRealIncomeAmt());
        cbPaySettleBo.setIncomeCcy(fiCbPaySettleDo.getIncomeCcy());
        cbPaySettleBo.setIncomeStatus(fiCbPaySettleDo.getIncomeStatus());
        cbPaySettleBo.setOldIncomeStatus(fiCbPaySettleDo.getOldIncomeStatus());
        cbPaySettleBo.setSettleAmt(cgwSettleBatchRespDto.getCnyMoney());
        cbPaySettleBo.setSettleCcy(Currency.CNY.getCode());
        cbPaySettleBo.setSettleRate(cgwSettleBatchRespDto.getExchange());
        cbPaySettleBo.setSettleStatus(fiCbPaySettleDo.getSettleStatus());
        cbPaySettleBo.setOldSettleStatus(fiCbPaySettleDo.getOldSettleStatus());
        cbPaySettleBo.setIncomeFee(fiCbPaySettleDo.getIncomeFee());
        cbPaySettleBo.setSettleAt(fiCbPaySettleDo.getSettleAt());
        cbPaySettleBo.setSettleCompleteAt(fiCbPaySettleDo.getSettleCompleteAt());
        cbPaySettleBo.setSettleFlag(fiCbPaySettleDo.getSettleFlag());
        cbPaySettleBo.setSettleFee(fiCbPaySettleDo.getSettleFee());
        cbPaySettleBo.setFileBatchNo(fiCbPaySettleDo.getFileBatchNo());
        return cbPaySettleBo;
    }

    /**
     * 解付对象转换
     *
     * @param fileId          dfs文件Id
     * @param fiCbPaySettleDo 收汇对象
     * @return 解付RepDto
     */
    public static CgwSumRelieveReqDto toCgwSumRelieveReqDto(Long fileId, FiCbPaySettleDo fiCbPaySettleDo) {

        CgwSumRelieveReqDto cgwSumRelieveReqDto = new CgwSumRelieveReqDto();
        cgwSumRelieveReqDto.setFileId(fileId);
        cgwSumRelieveReqDto.setBankReturnNo(fiCbPaySettleDo.getIncomeNo());
        cgwSumRelieveReqDto.setRemitMoney(fiCbPaySettleDo.getIncomeAmt());

        cgwSumRelieveReqDto.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        cgwSumRelieveReqDto.setChannelId(fiCbPaySettleDo.getChannelId().intValue());
        cgwSumRelieveReqDto.setBfBatchId(String.valueOf(fiCbPaySettleDo.getOrderId()));

        return cgwSumRelieveReqDto;
    }

    /**
     * 补录对象转换成渠道对象
     *
     * @param collectionMakeupBo 补录对象参数
     * @return 渠道对象
     */
    public static CgwReceiptRepairReqDto toCgwReceiptRepairReqDto(CollectionMakeupBo collectionMakeupBo) {

        CgwReceiptRepairReqDto cgwReceiptRepairReqDto = new CgwReceiptRepairReqDto();

        cgwReceiptRepairReqDto.setMoney(collectionMakeupBo.getIncomeAmt());
        cgwReceiptRepairReqDto.setCurrency(collectionMakeupBo.getIncomeCcy());
        cgwReceiptRepairReqDto.setTransDate(collectionMakeupBo.getIncomeAt());
        cgwReceiptRepairReqDto.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        cgwReceiptRepairReqDto.setBankReturnNo(collectionMakeupBo.getIncomeNo());
        cgwReceiptRepairReqDto.setChannelId(collectionMakeupBo.getChannelId().intValue());
        cgwReceiptRepairReqDto.setPostscript(String.valueOf(collectionMakeupBo.getMemberId()));

        return cgwReceiptRepairReqDto;
    }
}
