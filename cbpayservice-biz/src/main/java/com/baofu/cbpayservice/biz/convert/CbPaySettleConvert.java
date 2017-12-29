package com.baofu.cbpayservice.biz.convert;

import com.baofoo.Response;
import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwReceiptRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwReceiptResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.*;
import com.baofu.cbpayservice.dal.models.*;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 跨境结汇对象转换
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CbPaySettleConvert {

    public static FiCbPaySettleDo toFiCbPaySettleDo(SettleMoneyToAccountListenerBo sMTAListenerBo, Long orderId) {

        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setOrderId(orderId);
        fiCbPaySettleDo.setMemberId(sMTAListenerBo.getMemberId());
        fiCbPaySettleDo.setChannelId(sMTAListenerBo.getChannelId());
        fiCbPaySettleDo.setIncomeNo(sMTAListenerBo.getIncomeNo());
        fiCbPaySettleDo.setIncomeAmt(sMTAListenerBo.getIncomeAmt());
        fiCbPaySettleDo.setIncomeCcy(sMTAListenerBo.getIncomeCcy());
        fiCbPaySettleDo.setIncomeAt(sMTAListenerBo.getIncomeAt());
        fiCbPaySettleDo.setIncomeStatus(sMTAListenerBo.getIncomeStatus());
        fiCbPaySettleDo.setIsIncome(sMTAListenerBo.getIsIncome());
        fiCbPaySettleDo.setSettleStatus(SettleStatusEnum.WAIT_SETTLEMENT.getCode());
        fiCbPaySettleDo.setIncomeAccountNo(sMTAListenerBo.getIncomeAccountNo());
        fiCbPaySettleDo.setIncomeAccountName(sMTAListenerBo.getIncomeAccountName());
        fiCbPaySettleDo.setIncomeAddress(sMTAListenerBo.getIncomeAddress());
        fiCbPaySettleDo.setIncomeFee(sMTAListenerBo.getIncomeFee());
        fiCbPaySettleDo.setBankName(sMTAListenerBo.getBankName());
        fiCbPaySettleDo.setRemarks(sMTAListenerBo.getRemarks());
        fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.WAIT_UPLOAD.getCode());
        fiCbPaySettleDo.setSettleFlag(SettleFlagEnum.WAIT_SETTLEMENT.getCode());
        if (InComeStatusEnum.COMPELTED_INCOME.getCode() == fiCbPaySettleDo.getIsIncome()) {
            fiCbPaySettleDo.setRelieveAt(sMTAListenerBo.getIncomeAt());
        }
        fiCbPaySettleDo.setCmAuditState(ReceiverAuditCmStatusEnum.INIT.getCode());
        return fiCbPaySettleDo;
    }

    /**
     * 文件批次插入转换
     *
     * @param settleFileUploadReqBo 上传文件参数
     * @param fileBatchNo           文件批次号
     * @return 文件批次对象
     */
    public static FiCbPayFileUploadDo toFiCbpayFileUploadDo(SettleFileUploadReqBo settleFileUploadReqBo, Long fileBatchNo) {

        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileName(settleFileUploadReqBo.getFileName());
        fiCbpayFileUploadDo.setDfsFileId(settleFileUploadReqBo.getDfsFileId());
        fiCbpayFileUploadDo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadDo.setStatus(FileStatus.PROCESSING.getCode());
        fiCbpayFileUploadDo.setRecordCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setSuccessCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setTotalAmount(BigDecimal.ZERO);
        fiCbpayFileUploadDo.setFailCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setFileType(UploadFileType.SETTLE_FILE.getCode());
        fiCbpayFileUploadDo.setOrderType(UploadFileOrderType.ERROR_TYPE.getCode());
        fiCbpayFileUploadDo.setMemberId(settleFileUploadReqBo.getMemberId());
        fiCbpayFileUploadDo.setCreateBy(settleFileUploadReqBo.getCreateBy());
        return fiCbpayFileUploadDo;

    }

    /**
     * 根据文件Id返回DFS文件上传响应参数
     *
     * @param settleFileUploadReqBo DFS文件上传响应参数
     * @return
     */
    public static CommandResDTO getCommandResDTO(SettleFileUploadReqBo settleFileUploadReqBo) {

        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(settleFileUploadReqBo.getDfsFileId());
        reqDTO.setOperation(Operation.QUERY);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        return resDTO;
    }

    /**
     * 解析文件
     *
     * @param resDTO        dfs文件参数
     * @param localPathFile 文件本地路径
     * @return 文件内容
     * @throws Exception 异常
     */
    public static List<Object[]> getContext(CommandResDTO resDTO, String localPathFile) throws Exception {
        DfsClient.download(resDTO.getDfsPath(), localPathFile);
        File file = new File(localPathFile);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        String suffix = resDTO.getFileName().substring(resDTO.getFileName().lastIndexOf(".") + 1, resDTO.getFileName().length());
        String charsetName = FileCharsetDetectorUtil.getFileEncoding(file);
        log.info("文件字符集编码：{}", charsetName);

        if ("csv".equalsIgnoreCase(suffix)) {
            return CSVUtils.readCsvFile(new FileInputStream(file), 21, charsetName);
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            return Excelutil.getDataFromExcel(file, 0, 0, 17, resDTO.getFileName());
        } else if ("txt".equals(suffix.toLowerCase())) {
            return Txtutil.getDataFromTxt(file, 18, resDTO.getFileName());
        }
        return Lists.newArrayList();
    }

    /**
     * 根据文件Id返回excel信息
     *
     * @param dfsId dfs文件id
     * @return 文件数据
     * @throws Exception 异常
     */
    public static List<Object[]> getContextVerify(Long dfsId) throws Exception {
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(dfsId);
        reqDTO.setOperation(Operation.QUERY);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        byte[] bytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String suffix = resDTO.getFileName().substring(resDTO.getFileName().lastIndexOf(".") + 1, resDTO.getFileName().length());

        String charsetName = FileCharsetDetectorUtil.getFileEncoding(new ByteArrayInputStream(bytes));
        log.info("文件字符集编码：{}", charsetName);

        if ("csv".equalsIgnoreCase(suffix)) {
            return CSVUtils.readCsvFile(inputStream, NumberConstants.EIGHTEEN, charsetName);
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            return Excelutil.getDataFromExcelVerify(inputStream, 0, 0, 17, resDTO.getFileName());
        } else if ("txt".equals(suffix.toLowerCase())) {
            return Txtutil.getDataFromTxt(inputStream, NumberConstants.EIGHTEEN, resDTO.getFileName());
        }
        return Lists.newArrayList();
    }

    /**
     * 结汇订单信息转换
     *
     * @param cbPaySettleOrderValidateBo 结汇订单校验信息
     * @param settleFileUploadReqBo      请求参数
     * @param orderId                    结汇订单编号
     * @return 结汇订单信息
     */
    public static FiCbPaySettleOrderDo toFiCbPaySettleOrderDo(CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo,
                                                              SettleFileUploadReqBo settleFileUploadReqBo, Long orderId) {

        FiCbPaySettleOrderDo fiCbPaySettleOrderDo = new FiCbPaySettleOrderDo();
        fiCbPaySettleOrderDo.setMemberId(settleFileUploadReqBo.getMemberId());
        fiCbPaySettleOrderDo.setOrderId(orderId);
        fiCbPaySettleOrderDo.setMemberTransId(cbPaySettleOrderValidateBo.getMemberTransId());
        if (Pattern.matches(Constants.YYYY_MM_DD_HH_MM_SS, cbPaySettleOrderValidateBo.getMemberTransDate())) {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), DateUtil.settlePattern));
        } else if (Pattern.matches(Constants.YYYYMMDDHHMMSS, cbPaySettleOrderValidateBo.getMemberTransDate())) {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), DateUtil.fullPattern));
        } else if (Pattern.matches(Constants.YYYY_MM_DD, cbPaySettleOrderValidateBo.getMemberTransDate())) {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), DateUtil.smallDateFormat));
        } else if (Pattern.matches(Constants.YYYY_MM_DD_SLANT, cbPaySettleOrderValidateBo.getMemberTransDate())) {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), DateUtil.smallDatePattern));
        } else if (Pattern.matches(Constants.YYYY_MM_DD_HH_MM_SS_SLANT, cbPaySettleOrderValidateBo.getMemberTransDate())) {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), Constants.timesPattern));
        } else {
            fiCbPaySettleOrderDo.setMemberTransDate(DateUtil.parse(cbPaySettleOrderValidateBo.getMemberTransDate(), DateUtil.datePattern));
        }
        fiCbPaySettleOrderDo.setOrderAmt(new BigDecimal(cbPaySettleOrderValidateBo.getOrderAmt()));
        fiCbPaySettleOrderDo.setOrderCcy(cbPaySettleOrderValidateBo.getOrderCcy());
        fiCbPaySettleOrderDo.setPayeeIdType(Integer.parseInt(cbPaySettleOrderValidateBo.getPayeeIdType()));
        fiCbPaySettleOrderDo.setPayeeIdNo(SecurityUtil.desEncrypt(cbPaySettleOrderValidateBo.getPayeeIdNo().toUpperCase(),
                Constants.CARD_DES_PASSWD));
        fiCbPaySettleOrderDo.setPayeeName(cbPaySettleOrderValidateBo.getPayeeName());
        fiCbPaySettleOrderDo.setPayeeAccNo(SecurityUtil.desEncrypt(cbPaySettleOrderValidateBo.getPayeeAccNo(), Constants.CARD_DES_PASSWD));
        fiCbPaySettleOrderDo.setBatchNo(settleFileUploadReqBo.getFileBatchNo());
        return fiCbPaySettleOrderDo;
    }

    /**
     * 商品信息转换
     *
     * @param cbPaySettleOrderValidateBo 商品名称字符串
     * @param orderId                    订单ID
     * @return
     */
    public static List<FiCbPayOrderItemDo> paramConvert(CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo, Long orderId, String version) {

        String splitSymbol;
        if (Constants.VERSION_1_1.equals(version)) {
            splitSymbol = Constants.SPLIT_DOLLAR_DOLLAR;
        } else {
            splitSymbol = Constants.SPLIT_SYMBOL;
        }

        List<FiCbPayOrderItemDo> cbPayOrderItemDoList = new ArrayList<>();
        String[] nums = cbPaySettleOrderValidateBo.getGoodsNum().split(splitSymbol);
        String[] names = cbPaySettleOrderValidateBo.getGoodsName().replace("\n", "").replace("\r\n", "").split(splitSymbol);
        String[] prices = cbPaySettleOrderValidateBo.getGoodsPrice().split(splitSymbol);
        for (int i = 0; i < names.length; i++) {
            FiCbPayOrderItemDo fiCbPayOrderItemDo = new FiCbPayOrderItemDo();
            fiCbPayOrderItemDo.setOrderId(orderId);
            String amount = nums[i];
            if (amount.contains(".")) {
                amount = amount.substring(0, amount.indexOf("."));
            }
            fiCbPayOrderItemDo.setCommodityAmount(Integer.parseInt(amount));
            fiCbPayOrderItemDo.setCommodityName(com.baofu.cbpayservice.common.util.StringUtils.stringFilter(names[i]));
            fiCbPayOrderItemDo.setCommodityPrice(new BigDecimal(prices[i]));
            cbPayOrderItemDoList.add(fiCbPayOrderItemDo);
        }
        return cbPayOrderItemDoList;
    }

    /**
     * excel 内容转换
     * 0 (A)商户订单号
     * 1 (B)收款人证件类型
     * 2 (C)收款人证件号码
     * 3 (D)收款人名称
     * 4 (E)收款人账号
     * 5 (F)交易时间
     * 6 (G)交易币种
     * 7 (H)交易金额
     * 8 (I)商品名称
     * 9 (J)商品数量
     * 10 (k)商品单价
     * 11 (L)快递公司编码
     * 12 (M)运单号
     * 13 (N)收货人姓名
     * 14 (O)收货人联系方式
     * 15 (P)收货人地址
     * 16 (Q)发货日期
     * 17 (R)预留手机号码
     *
     * @param objects excel内容
     * @return 结汇订单校验对象
     */
    public static CbPaySettleOrderValidateBo toSettleOrderValidateBo(Object[] objects) {

        CbPaySettleOrderValidateBo orderValidateBo = new CbPaySettleOrderValidateBo();
        orderValidateBo.setMemberTransId(objects[0] == null ? "" : String.valueOf(objects[0]));
        orderValidateBo.setPayeeIdType(objects[1] == null ? "" : String.valueOf(objects[1]));
        orderValidateBo.setPayeeIdNo(objects[2] == null ? "" : String.valueOf(objects[2]));
        orderValidateBo.setPayeeName(objects[3] == null ? "" : String.valueOf(objects[3]));
        orderValidateBo.setPayeeAccNo(objects[4] == null ? "" : String.valueOf(objects[4]));
        orderValidateBo.setMemberTransDate(objects[5] == null ? "" : String.valueOf(objects[5]));
        orderValidateBo.setOrderCcy(objects[6] == null ? "" : String.valueOf(objects[6]));
        orderValidateBo.setOrderAmt(objects[7] == null ? "" : String.valueOf(objects[7]));
        orderValidateBo.setGoodsName(objects[8] == null ? "" : String.valueOf(objects[8]));
        orderValidateBo.setGoodsNum(objects[9] == null ? "" : String.valueOf(objects[9]));
        orderValidateBo.setGoodsPrice(objects[10] == null ? "" : String.valueOf(objects[10]));

        OrderLogisticsValidationBo orderLogisticsValidationBo = new OrderLogisticsValidationBo();
        orderLogisticsValidationBo.setLogisticsCompanyNumber(objects[11] == null ? "" : String.valueOf(objects[11]));
        orderLogisticsValidationBo.setLogisticsNumber(objects[12] == null ? "" : String.valueOf(objects[12]));
        orderLogisticsValidationBo.setConsigneeName(objects[13] == null ? "" : String.valueOf(objects[13]));
        orderLogisticsValidationBo.setConsigneeContact(objects[14] == null ? "" : String.valueOf(objects[14]));
        orderLogisticsValidationBo.setConsigneeAddress(objects[15] == null ? "" : String.valueOf(objects[15]));
        orderLogisticsValidationBo.setDeliveryDate(objects[16] == null ? "" : String.valueOf(objects[16]));
        orderValidateBo.setOrderLogisticsValidationBo(orderLogisticsValidationBo);
        return orderValidateBo;
    }

    /**
     * 外汇汇入申请参数转换
     *
     * @param settleIncomeApplyBo 请求参数
     * @return 外汇汇入申请对象
     */
    public static FiCbPaySettleApplyDo toFiCbPaySettleApplyDo(SettleIncomeApplyBo settleIncomeApplyBo, Long orderId) {

        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = new FiCbPaySettleApplyDo();
        fiCbPaySettleApplyDo.setOrderId(orderId);
        fiCbPaySettleApplyDo.setMemberId(settleIncomeApplyBo.getMemberId());
        fiCbPaySettleApplyDo.setTerminalId(settleIncomeApplyBo.getTerminalId());
        fiCbPaySettleApplyDo.setIncomeNo(settleIncomeApplyBo.getIncomeNo());
        fiCbPaySettleApplyDo.setOrderAmt(settleIncomeApplyBo.getOrderAmt());
        fiCbPaySettleApplyDo.setOrderCcy(settleIncomeApplyBo.getOrderCcy());
        fiCbPaySettleApplyDo.setIncomeAccountName(settleIncomeApplyBo.getIncomeAccountName());
        fiCbPaySettleApplyDo.setMatchingStatus(MatchingStatusEnum.NO_MATCH.getCode());
        fiCbPaySettleApplyDo.setIncomeAccount(settleIncomeApplyBo.getIncomeAccount());
        fiCbPaySettleApplyDo.setPaymentFileId(settleIncomeApplyBo.getPaymentFileId());
        fiCbPaySettleApplyDo.setRemittanceAcc(settleIncomeApplyBo.getRemittanceAcc());
        fiCbPaySettleApplyDo.setRemittanceCountry(settleIncomeApplyBo.getRemittanceCountry());
        fiCbPaySettleApplyDo.setRemittanceName(settleIncomeApplyBo.getRemittanceName());
        fiCbPaySettleApplyDo.setIncomeBankName(settleIncomeApplyBo.getIncomeBankName());
        fiCbPaySettleApplyDo.setNotifyUrl(settleIncomeApplyBo.getNotifyUrl());
        return fiCbPaySettleApplyDo;
    }

    /**
     * 运营设置对象转换成银行通知对象
     *
     * @param settleOperationSetReqBo 运营设置对象
     * @return 汇入银行通知对象
     */
    public static FiCbPaySettleDo toFiCbPaySettleDo(SettleOperationSetReqBo settleOperationSetReqBo) {

        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setMemberId(settleOperationSetReqBo.getMemberId());
        fiCbPaySettleDo.setOrderId(settleOperationSetReqBo.getOrderId());
        return fiCbPaySettleDo;
    }

    /**
     * 运营设置对象转换成汇入申请对象
     *
     * @param settleOperationSetReqBo 运营设置对象
     * @return 成汇入申请对象
     */
    public static FiCbPaySettleApplyDo toFiCbPaySettleApplyDo(SettleOperationSetReqBo settleOperationSetReqBo) {

        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = new FiCbPaySettleApplyDo();
        fiCbPaySettleApplyDo.setOrderId(settleOperationSetReqBo.getApplyNo());
        fiCbPaySettleApplyDo.setMatchingStatus(MatchingStatusEnum.YES_MATCH.getCode());
        return fiCbPaySettleApplyDo;
    }

    /**
     * 清算审核通过，对用户的外币账户进行充值
     *
     * @param fiCbPaySettleDo 结汇对象（申请对象）
     * @return 充值对象
     */
    public static RechargeReqDto toWithdrawReqDto(FiCbPaySettleDo fiCbPaySettleDo) {

        RechargeReqDto rechargeReqDto = new RechargeReqDto();
        rechargeReqDto.setOrderId(fiCbPaySettleDo.getOrderId());
        rechargeReqDto.setMemberId(fiCbPaySettleDo.getMemberId());
        rechargeReqDto.setOrderAmt(fiCbPaySettleDo.getIncomeAmt());
        rechargeReqDto.setOrderCcy(fiCbPaySettleDo.getIncomeCcy());
        rechargeReqDto.setOrderSubType(CmOrderSubTypeEnum.ACCOUNT_RECHARGE.getCode());
        rechargeReqDto.setChannelId(fiCbPaySettleDo.getChannelId());

        return rechargeReqDto;
    }

    /**
     * 收汇通知对象转换
     *
     * @param cgwReceiptResultRespDto 渠道通知对象
     */
    public static SettleMoneyToAccountListenerBo toSettleMoneyToAccountListenerBo(CgwReceiptResultDto cgwReceiptResultRespDto) {

        SettleMoneyToAccountListenerBo settleMoneyToAccountListenerBo = new SettleMoneyToAccountListenerBo();
        CgwReceiptRespDto cgwReceiptRespDto = cgwReceiptResultRespDto.getCgwReceiptRespDto();
        if (StringUtils.isNotBlank(cgwReceiptRespDto.getMemberId()) && StringUtils.isNumeric(cgwReceiptRespDto.getMemberId())) {
            settleMoneyToAccountListenerBo.setMemberId(Long.parseLong(cgwReceiptRespDto.getMemberId().trim()));
        } else {
            settleMoneyToAccountListenerBo.setMemberId(Long.parseLong("001"));
        }
        settleMoneyToAccountListenerBo.setChannelId(cgwReceiptRespDto.getChannelId());
        settleMoneyToAccountListenerBo.setIncomeNo(cgwReceiptRespDto.getBankReturnNo());
        settleMoneyToAccountListenerBo.setIncomeAmt(cgwReceiptRespDto.getMoney());
        settleMoneyToAccountListenerBo.setRemarks(cgwReceiptRespDto.getPostscript());
        // 汇款人名称截取前128位
        settleMoneyToAccountListenerBo.setIncomeAccountName(subString(cgwReceiptRespDto.getPayerInfo1(), 128));
        settleMoneyToAccountListenerBo.setIncomeAccountNo(cgwReceiptRespDto.getPayerInfo2());
        if (cgwReceiptRespDto.getReceptionCharge() != null) {
            settleMoneyToAccountListenerBo.setIncomeFee(cgwReceiptRespDto.getReceptionCharge());
        }
        settleMoneyToAccountListenerBo.setIncomeCcy(cgwReceiptRespDto.getCurrency());
        settleMoneyToAccountListenerBo.setIncomeAt(cgwReceiptRespDto.getTransDate());

        if (cgwReceiptRespDto.getRelieveFlag() == 1 || cgwReceiptRespDto.getRelieveFlag() == 2) {
            settleMoneyToAccountListenerBo.setIncomeStatus(cgwReceiptRespDto.getRelieveFlag());
            settleMoneyToAccountListenerBo.setIsIncome(cgwReceiptRespDto.getRelieveFlag());
        } else {
            settleMoneyToAccountListenerBo.setIsIncome(3);
            settleMoneyToAccountListenerBo.setIncomeStatus(6);
        }
        settleMoneyToAccountListenerBo.setBankName(cgwReceiptRespDto.getBankName());
        return settleMoneyToAccountListenerBo;
    }

    /**
     * 转换结汇订单DO
     *
     * @param cbPaySettleBo
     * @return
     */
    public static FiCbPaySettleDo toFiCbPaySettleDo(CbPaySettleBo cbPaySettleBo) {
        if (cbPaySettleBo == null) {
            return null;
        }
        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setMemberId(cbPaySettleBo.getMemberId());
        fiCbPaySettleDo.setChannelId(cbPaySettleBo.getChannelId());
        fiCbPaySettleDo.setOrderId(cbPaySettleBo.getOrderId());
        fiCbPaySettleDo.setIncomeAmt(cbPaySettleBo.getIncomeAmt());
        fiCbPaySettleDo.setIncomeCcy(cbPaySettleBo.getIncomeCcy());
        fiCbPaySettleDo.setSettleRate(cbPaySettleBo.getSettleRate());
        fiCbPaySettleDo.setSettleCcy(cbPaySettleBo.getSettleCcy());
        fiCbPaySettleDo.setSettleAmt(cbPaySettleBo.getSettleAmt());
        fiCbPaySettleDo.setSettleAt(cbPaySettleBo.getSettleAt());
        fiCbPaySettleDo.setSettleCompleteAt(cbPaySettleBo.getSettleCompleteAt());
        fiCbPaySettleDo.setSettleFlag(cbPaySettleBo.getSettleFlag());
        fiCbPaySettleDo.setSettleFee(cbPaySettleBo.getSettleFee());
        fiCbPaySettleDo.setSettleStatus(cbPaySettleBo.getSettleStatus());
        fiCbPaySettleDo.setIncomeStatus(cbPaySettleBo.getIncomeStatus());
        fiCbPaySettleDo.setOldSettleStatus(cbPaySettleBo.getOldSettleStatus());
        fiCbPaySettleDo.setOldIncomeStatus(cbPaySettleBo.getOldIncomeStatus());

        fiCbPaySettleDo.setMemberSettleAmt(cbPaySettleBo.getMemberSettleAmt());
        fiCbPaySettleDo.setMemberSettleRate(cbPaySettleBo.getMemberSettleRate());
        fiCbPaySettleDo.setProfitAndLoss(cbPaySettleBo.getProfitAndLoss());

        return fiCbPaySettleDo;
    }

    /**
     * 文件批次转换
     *
     * @param fileBatchNo 文件批次号
     * @param totalCount  总条数
     * @return 文件批次信息
     */
    public static FiCbPayFileUploadDo toFiCbPayFileUploadDo(Long fileBatchNo, int totalCount) {

        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadDo.setSuccessCount(totalCount);
        fiCbpayFileUploadDo.setStatus(UploadFileStatus.PASS.getCode());
        return fiCbpayFileUploadDo;
    }

    /**
     * 物流信息转换
     *
     * @param cbPaySettleOrderValidateBo excel文件信息
     * @param orderId                    订单号
     * @return 物流信息
     */
    public static FiCbpayOrderLogisticsDo toFiCbpayOrderLogisticsDo(CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo, Long orderId) {

        FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = null;
        OrderLogisticsValidationBo orderLogisticsValidationBo = cbPaySettleOrderValidateBo.getOrderLogisticsValidationBo();
        if (StringUtils.isNotBlank(orderLogisticsValidationBo.getLogisticsNumber())) {
            fiCbpayOrderLogisticsDo = new FiCbpayOrderLogisticsDo();
            fiCbpayOrderLogisticsDo.setLogisticsCompanyNumber(orderLogisticsValidationBo.getLogisticsCompanyNumber());
            fiCbpayOrderLogisticsDo.setOrderId(orderId);
            fiCbpayOrderLogisticsDo.setLogisticsNumber(orderLogisticsValidationBo.getLogisticsNumber());
            fiCbpayOrderLogisticsDo.setConsigneeName(orderLogisticsValidationBo.getConsigneeName());
            fiCbpayOrderLogisticsDo.setConsigneeContact(orderLogisticsValidationBo.getConsigneeContact());
            fiCbpayOrderLogisticsDo.setConsigneeAddress(orderLogisticsValidationBo.getConsigneeAddress());
            if (Pattern.matches(Constants.YYYY_MM_DD, orderLogisticsValidationBo.getDeliveryDate())) {
                fiCbpayOrderLogisticsDo.setDeliveryDate(DateUtil.parse(orderLogisticsValidationBo.getDeliveryDate(), DateUtil.smallDateFormat));
            } else {
                fiCbpayOrderLogisticsDo.setDeliveryDate(DateUtil.parse(orderLogisticsValidationBo.getDeliveryDate(), DateUtil.datePattern));
            }
        }
        return fiCbpayOrderLogisticsDo;
    }

    public static FiCbPaySettleApplyDo toFiCbPaySettleApplyDo(OperateConfirmBo operateConfirmBo) {

        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = new FiCbPaySettleApplyDo();
        fiCbPaySettleApplyDo.setOrderId(operateConfirmBo.getApplyNo());
        fiCbPaySettleApplyDo.setMatchingStatus(operateConfirmBo.getMatchingStatus());
        fiCbPaySettleApplyDo.setBeforeMatchingStatus(operateConfirmBo.getBeforeMatchingStatus());
        fiCbPaySettleApplyDo.setRemarks(operateConfirmBo.getRemarks());
        return fiCbPaySettleApplyDo;
    }

    /**
     * excel 内容转换
     * 0 (A)商户订单号
     * 1 (B)收款人证件类型
     * 2 (C)收款人证件号码
     * 3 (D)收款人名称
     * 4 (E)收款人账号
     * 5 (F)交易时间
     * 6 (G)交易币种
     * 7 (H)交易金额
     * 8 (I)商品名称
     * 9 (J)商品数量
     * 10 (k)商品单价
     * 11 (L)快递公司编码
     * 12 (M)运单号
     * 13 (N)收货人姓名
     * 14 (O)收货人联系方式
     * 15 (P)收货人地址
     * 16 (Q)发货日期
     * 17 (R)预留手机号码
     *
     * @param objects excel内容
     * @return 结汇订单校验对象
     */
    public static CbPaySettleOrderBo toSettleOrderBo(Object[] objects) {

        CbPaySettleOrderBo orderValidateBo = new CbPaySettleOrderBo();
        orderValidateBo.setMemberTransId(objects[0] == null ? "" : String.valueOf(objects[0]));
        orderValidateBo.setPayeeIdType(objects[1] == null ? "" : String.valueOf(objects[1]));
        orderValidateBo.setPayeeIdNo(objects[2] == null ? "" : String.valueOf(objects[2]));
        orderValidateBo.setPayeeName(objects[3] == null ? "" : String.valueOf(objects[3]));
        orderValidateBo.setPayeeAccNo(objects[4] == null ? "" : String.valueOf(objects[4]));
        orderValidateBo.setMemberTransDate(objects[5] == null ? "" : String.valueOf(objects[5]));
        orderValidateBo.setOrderCcy(objects[6] == null ? "" : String.valueOf(objects[6]));
        orderValidateBo.setOrderAmt(objects[7] == null ? "" : String.valueOf(objects[7]));
        orderValidateBo.setGoodsName(objects[8] == null ? "" : String.valueOf(objects[8]));
        orderValidateBo.setGoodsNum(objects[9] == null ? "" : String.valueOf(objects[9]));
        orderValidateBo.setGoodsPrice(objects[10] == null ? "" : String.valueOf(objects[10]));
        orderValidateBo.setLogisticsCompanyNumber(objects[11] == null ? "" : String.valueOf(objects[11]));
        orderValidateBo.setLogisticsNumber(objects[12] == null ? "" : String.valueOf(objects[12]));
        orderValidateBo.setConsigneeName(objects[13] == null ? "" : String.valueOf(objects[13]));
        orderValidateBo.setConsigneeContact(objects[14] == null ? "" : String.valueOf(objects[14]));
        orderValidateBo.setConsigneeAddress(objects[15] == null ? "" : String.valueOf(objects[15]));
        orderValidateBo.setDeliveryDate(objects[16] == null ? "" : String.valueOf(objects[16]));
        return orderValidateBo;
    }

    /**
     * 结汇结果查询参数转换
     *
     * @param memberId   商户号
     * @param incomeNo   汇款流水号
     * @param searchType 查询类型
     * @return SettleQueryReqParamBo
     */
    public static SettleQueryReqParamBo toSettleQueryReqParamBo(Long memberId, String incomeNo, Long settleOrderId, String searchType) {
        SettleQueryReqParamBo settleQueryReqParamBo = new SettleQueryReqParamBo();
        settleQueryReqParamBo.setIncomeNo(incomeNo);
        settleQueryReqParamBo.setMemberId(memberId);
        settleQueryReqParamBo.setSettleOrderId(settleOrderId);
        settleQueryReqParamBo.setSearchType(searchType);
        return settleQueryReqParamBo;
    }

    public static FiCbPayFileUploadDo paramConvert(SettleIncomeApplyBo settleIncomeApplyBo, Long fileBatchNo) {
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileName(settleIncomeApplyBo.getFileName());
        fiCbpayFileUploadDo.setDfsFileId(settleIncomeApplyBo.getSettleDFSId());
        fiCbpayFileUploadDo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadDo.setStatus(FileStatus.PROCESSING.getCode());
        fiCbpayFileUploadDo.setRecordCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setSuccessCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setFailCount(BigDecimal.ZERO.intValue());
        fiCbpayFileUploadDo.setFileType(UploadFileType.SETTLE_FILE.getCode());
        fiCbpayFileUploadDo.setOrderType(UploadFileOrderType.ERROR_TYPE.getCode());
        fiCbpayFileUploadDo.setTotalAmount(BigDecimal.ZERO);
        fiCbpayFileUploadDo.setMemberId(settleIncomeApplyBo.getMemberId());
        fiCbpayFileUploadDo.setCreateBy(settleIncomeApplyBo.getMemberName());
        return fiCbpayFileUploadDo;
    }

    /**
     * 收汇到账通知清算审核时更新参数信息转换
     *
     * @param receiveAuditBo 清算审核业务参数信息
     * @return 清算审核时更新参数信息
     */
    public static FiCbPaySettleDo receiveAuditParamConvert(ReceiveAuditBo receiveAuditBo) {

        FiCbPaySettleDo fiCbPaySettleModifyDo = new FiCbPaySettleDo();
        fiCbPaySettleModifyDo.setOrderId(receiveAuditBo.getOrderId());
        fiCbPaySettleModifyDo.setCmAuditState(receiveAuditBo.getCmAuditStatus());
        //审核不通过时审核状态变为初始
        if (ReceiverAuditCmStatusEnum.NO_FIRST_CHECK.getCode() == receiveAuditBo.getCmAuditStatus()
                || ReceiverAuditCmStatusEnum.NO_SECOND_CHECK.getCode() == receiveAuditBo.getCmAuditStatus()) {
            fiCbPaySettleModifyDo.setCmAuditState(ReceiverAuditCmStatusEnum.INIT.getCode());
        }
        return fiCbPaySettleModifyDo;
    }

    /**
     * 结汇文件上传参数转换
     *
     * @param settleIncomeApplyBo 结汇文件上传参数
     * @return 结汇文件上传参数
     */
    public static SettleFileUploadReqBo toSettleFileUploadReqBoTwo(SettleIncomeApplyBo settleIncomeApplyBo, Long fileBatchNo) {

        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setMemberId(settleIncomeApplyBo.getMemberId());
        settleFileUploadReqBo.setDfsFileId(settleIncomeApplyBo.getSettleDFSId());
        settleFileUploadReqBo.setFileType(UploadFileType.SETTLE_FILE.getCode());
        settleFileUploadReqBo.setFileName(settleIncomeApplyBo.getFileName());
        settleFileUploadReqBo.setMemberName(settleIncomeApplyBo.getMemberName());
        settleFileUploadReqBo.setCreateBy(SystemEnum.SYSTEM.getCode());
        settleFileUploadReqBo.setManTotalMoney(settleIncomeApplyBo.getOrderAmt());
        settleFileUploadReqBo.setManCcy(settleIncomeApplyBo.getOrderCcy());
        settleFileUploadReqBo.setIncomeNo(settleIncomeApplyBo.getIncomeNo());
        settleFileUploadReqBo.setFileBatchNo(fileBatchNo);

        return settleFileUploadReqBo;
    }


    /**
     * 复审成功，发送MQ参数组装    add  by   wdj
     *
     * @param fiCbPayFileUploadDo
     * @param operateConfirmBo
     * @param matchingOrderId
     * @return
     */
    public static SettleFileUploadReqBo convertSettleApplyData(FiCbPayFileUploadDo fiCbPayFileUploadDo,
                                                               OperateConfirmBo operateConfirmBo, Long matchingOrderId) {
        SettleFileUploadReqBo settleFileUploadReqBo = new SettleFileUploadReqBo();
        settleFileUploadReqBo.setCreateBy(fiCbPayFileUploadDo.getCreateBy());
        settleFileUploadReqBo.setDfsFileId(fiCbPayFileUploadDo.getDfsFileId());
        settleFileUploadReqBo.setMemberId(operateConfirmBo.getMemberId());
        settleFileUploadReqBo.setFileType(Constants.ONE);
        settleFileUploadReqBo.setFileName(fiCbPayFileUploadDo.getFileName());
        settleFileUploadReqBo.setFileBatchNo(fiCbPayFileUploadDo.getFileBatchNo());
        settleFileUploadReqBo.setMemberName(fiCbPayFileUploadDo.getCreateBy());
        settleFileUploadReqBo.setSettleOrderId(matchingOrderId);
        settleFileUploadReqBo.setIncomeNo(operateConfirmBo.getIncomeNo());
        settleFileUploadReqBo.setApplyId(operateConfirmBo.getApplyNo());
        settleFileUploadReqBo.setFlag(operateConfirmBo.getMatchingStatus());
        return settleFileUploadReqBo;
    }


    /**
     * 结汇API通知商户以及商户主动查询结果参数信息转换
     *
     * @param settleApplyDo 商户汇入申请信息
     * @param cbPaySettleDo 结汇订单结果信息
     * @return 返回通知商户的最终结果信息
     */
    public static SettleNotifyMemberBo paramConvert(FiCbPaySettleApplyDo settleApplyDo, FiCbPaySettleDo cbPaySettleDo) {

        SettleNotifyMemberBo settleNotifyMemberBo = new SettleNotifyMemberBo();
        settleNotifyMemberBo.setMemberId(settleApplyDo.getMemberId());
        settleNotifyMemberBo.setTerminalId(settleApplyDo.getTerminalId());
        settleNotifyMemberBo.setRemitReqNo(settleApplyDo.getIncomeNo());
        settleNotifyMemberBo.setOrderAmt(settleApplyDo.getOrderAmt());
        settleNotifyMemberBo.setOrderCcy(settleApplyDo.getOrderCcy());
        settleNotifyMemberBo.setRemarks(settleApplyDo.getRemarks());
        //如果申请不成功时，结汇相关信息为空，直接结束
        if (MatchingStatusEnum.YES_MATCH.getCode() != settleApplyDo.getMatchingStatus()) {
            return settleNotifyMemberBo;
        }
        //结汇状态不等于结汇完成时，结汇相关信息为空，直接结束
        if (SettleStatusEnum.TURE.getCode() != cbPaySettleDo.getSettleStatus() &&
                SettleStatusEnum.PART_TRUE.getCode() != cbPaySettleDo.getSettleStatus()) {
            return settleNotifyMemberBo;
        }
        settleNotifyMemberBo.setRemarks(cbPaySettleDo.getRemarks());
        settleNotifyMemberBo.setExchangeRate(cbPaySettleDo.getMemberSettleRate());
        settleNotifyMemberBo.setExchangeAmt(cbPaySettleDo.getMemberSettleAmt());
        settleNotifyMemberBo.setSettleFee(cbPaySettleDo.getSettleFee());
        settleNotifyMemberBo.setSettleAmt(cbPaySettleDo.getMemberSettleAmt().subtract(cbPaySettleDo.getSettleFee()));
        settleNotifyMemberBo.setRealSettleAmt(cbPaySettleDo.getRealIncomeAmt());

        return settleNotifyMemberBo;
    }

    /**
     * 运营线下上传汇款凭证对象转换成汇入申请对象
     *
     * @param paymentFileUploadBo 运营线下上传的汇款凭证对象
     * @return 成汇入申请对象
     */
    public static FiCbPaySettleApplyDo toFiCbPaySettleApplyDo(SettlePaymentFileUploadBo paymentFileUploadBo) {

        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = new FiCbPaySettleApplyDo();
        fiCbPaySettleApplyDo.setOrderId(paymentFileUploadBo.getApplyNo());
        fiCbPaySettleApplyDo.setPaymentFileId(paymentFileUploadBo.getPaymentFileId());
        return fiCbPaySettleApplyDo;
    }

    /**
     * 截取长度
     *
     * @param source 原字符串
     * @param length 长度
     * @return 截取结果
     */
    private static String subString(String source, int length) {
        if (StringUtil.isBlank(source)) {
            return "";
        }
        if (source.length() > length) {
            source = source.substring(0, length);
        }
        return source;
    }

}
