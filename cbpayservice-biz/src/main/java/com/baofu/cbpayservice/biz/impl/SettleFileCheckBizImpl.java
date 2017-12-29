package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.CbPayChannelFacadeBiz;
import com.baofu.cbpayservice.biz.SettleFileCheckBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettleConvert;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.CbPaySettleOrderValidateBo;
import com.baofu.cbpayservice.biz.models.SettleBaseValidationBo;
import com.baofu.cbpayservice.biz.models.SettleFileUploadReqBo;
import com.baofu.cbpayservice.biz.models.SettleOrderListBo;
import com.baofu.cbpayservice.biz.validation.ValidationUtil;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.ServiceConfigEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleOrderMapper;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleDo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import com.system.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 描述
 * <p>
 * </p>
 * User: 不良人 Date:2017/4/16 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleFileCheckBizImpl implements SettleFileCheckBiz {

    /**
     * 订单明细信息服务
     */
    @Autowired
    private FiCbPaySettleOrderMapper fiCbPaySettleOrderMapper;

    /**
     * 收汇订单信息服务
     */
    @Autowired
    private FiCbPaySettleMapper fiCbPaySettleMapper;

    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;

    /**
     * 结汇邮件出错主发人
     */
    @Value("${settle_file_error_emailTo}")
    private String settleFileErrorEmailTo;

    /**
     * 结汇邮件出错抄送人
     */
    @Value("${settle_file_error_emailCc}")
    private String settleFileErrorEmailCc;

    /**
     * 文件校验
     *
     * @param list                  文件内容集合
     * @param settleFileUploadReqBo 请求参数
     * @return 校验结果
     */
    @Override
    public SettleOrderListBo baseCheck(List<Object[]> list, SettleFileUploadReqBo settleFileUploadReqBo, Boolean testAndVerifyOnlyFlag) {
        StringBuilder errorBuffer = new StringBuilder();
        SettleOrderListBo settleOrderListBo = new SettleOrderListBo();

        if (list == null || list.size() <= Constants.SETTLE_NO_CONTENT_LINE) {
            errorBuffer.append("excel订单内容条数不能为空");
            settleOrderListBo.setErrorMsg(errorBuffer);
            settleOrderListBo.setErrorCount(BigDecimal.ZERO.intValue());
            log.info("excel批次：{},订单内容条数不能为空", settleFileUploadReqBo.getFileBatchNo());
            return settleOrderListBo;
        }

        //第3行数据校验
        SettleBaseValidationBo settleBaseValidationBo = new SettleBaseValidationBo();
        settleBaseValidationBo.setMemberId(list.get(2)[0] == null ? "" : String.valueOf(list.get(2)[0]).trim());
        settleBaseValidationBo.setMemberName(list.get(2)[1] == null ? "" : String.valueOf(list.get(2)[1]).trim());
        settleBaseValidationBo.setVersion(list.get(2)[2] == null ? "" : String.valueOf(list.get(2)[2]).trim());
        String errorMessage = ParamValidate.validateParams(settleBaseValidationBo, Constants.SPLIT_MARK);
        if (errorMessage.length() > 0) {
            log.info("excel批次：{},第3行内容不正确{}", settleFileUploadReqBo.getFileBatchNo(), errorMessage);
            errorBuffer.append("第3行:").append(errorMessage);
            if (list.get(2)[2] != null && !"".equals(list.get(2)[2]) && (list.get(2)[2] == null || !Constants.VERSION_1_1.equals(list.get(2)[2]))) {
                errorBuffer.append("| 文件版本号不正确");
            }
            settleOrderListBo.setErrorMsg(errorBuffer);
            settleOrderListBo.setErrorCount(list.size() - Constants.SETTLE_NO_CONTENT_LINE);
            return settleOrderListBo;
        }
        if (list.get(2)[2] != null && !"".equals(list.get(2)[2]) && (list.get(2)[2] == null || !Constants.VERSION_1_1.equals(list.get(2)[2]))) {
            log.info("excel批次：{},第3行内容不正确文件版本号不正确,版本号：{}", settleFileUploadReqBo.getFileBatchNo(), list.get(2)[2]);
            errorBuffer.append("第3行:").append("文件版本号不正确");
            settleOrderListBo.setErrorMsg(errorBuffer);
            settleOrderListBo.setErrorCount(list.size() - Constants.SETTLE_NO_CONTENT_LINE);
            return settleOrderListBo;
        }
        if (settleFileUploadReqBo.getMemberId() != Long.parseLong(settleBaseValidationBo.getMemberId())) {
            errorMessage = "商户号填写不正确";
            errorBuffer.append(errorMessage);
        }
        if (!(settleFileUploadReqBo.getMemberName().equals(settleBaseValidationBo.getMemberName()))) {
            errorMessage = "| 商户名称填写不正确";
            errorBuffer.append(errorMessage);
        }
        if (errorBuffer.length() > 0) {
            errorBuffer.insert(0, "第3行:").append("\r\n");
            log.info("excel批次：{},第3行内容不正确{}", settleFileUploadReqBo.getFileBatchNo(), errorMessage);
            settleOrderListBo.setErrorMsg(errorBuffer);
            return settleOrderListBo;
        }

        //文件总条数限制50万
        if (list.size() - Constants.SETTLE_NO_CONTENT_LINE > Constants.EXCEL_CONTENT_MAX) {
            log.info("excel批次：{},l订单内容条数不能超过50万", settleFileUploadReqBo.getFileBatchNo());
            errorBuffer.append("excel订单内容条数不能超过50万");
            settleOrderListBo.setErrorMsg(errorBuffer);
            return settleOrderListBo;
        }

        if (testAndVerifyOnlyFlag) {
            return checkVerify(settleBaseValidationBo.getVersion(), list, settleFileUploadReqBo);
        } else {
            FiCbPaySettleDo fiCbPaySettleDo;
            if (NumberConstants.TWELVE == settleFileUploadReqBo.getFlag()) {
                fiCbPaySettleDo = new FiCbPaySettleDo();
                fiCbPaySettleDo.setIncomeCcy(settleFileUploadReqBo.getManCcy());
                fiCbPaySettleDo.setIncomeAmt(settleFileUploadReqBo.getManTotalMoney());
            } else {
                fiCbPaySettleDo = fiCbPaySettleMapper.queryByOrderId(settleFileUploadReqBo.getSettleOrderId());
                if (fiCbPaySettleDo == null) {
                    log.info("收汇订单单号={}不存在", settleFileUploadReqBo.getSettleOrderId());
                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
                }
            }
            return check(settleBaseValidationBo.getVersion(), list, settleFileUploadReqBo, fiCbPaySettleDo);
        }

    }

    /**
     * excel 数据校验
     *
     * @param settleFileUploadReqBo 代理对象
     * @param list                  excel内容
     * @return 校验结果
     */
    private SettleOrderListBo check(String version, List<Object[]> list, SettleFileUploadReqBo settleFileUploadReqBo, FiCbPaySettleDo fiCbPaySettleDo) {

        long startTime = System.currentTimeMillis();
        BigDecimal totalAmount = BigDecimal.ZERO;
        StringBuilder errorBuffer = new StringBuilder();
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        SettleOrderListBo settleOrderListBo = new SettleOrderListBo();
        //查询渠道汇率
        Long channelId = Long.parseLong(Configuration.getString(ServiceConfigEnum.PING_AN_SETTLE_CHANNEL_ID.getKey()));
        CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, fiCbPaySettleDo.getIncomeCcy());
        //四万五千美元校验，如果是美元时限定大小为49999，非美元是限额为49500
        BigDecimal usdAmt = new BigDecimal(49999).multiply(exchangeRate.getBuyRateOfCcy());
        if (!Currency.USD.getCode().equals(fiCbPaySettleDo.getIncomeCcy())) {
            CgwExchangeRateResultDto usdExchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, Currency.USD.getCode());
            usdAmt = new BigDecimal(49500).multiply(usdExchangeRate.getBuyRateOfCcy());
        }

        // 人民币金额20万
        BigDecimal cnyAmt = new BigDecimal("200000");

        List<CbPaySettleOrderValidateBo> orderValidateBoList = new ArrayList<>();
        for (int i = Constants.SETTLE_NO_CONTENT_LINE; i < list.size(); i++) {
            CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo = CbPaySettleConvert.toSettleOrderValidateBo(list.get(i));
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.settleFileValid(version, i, cbPaySettleOrderValidateBo);

            //统计总金额
            String paymentMoney = cbPaySettleOrderValidateBo.getOrderAmt();
            if (StringUtils.isNotBlank(paymentMoney) && ProxyCustomConvert.isTrreeNumber
                    (paymentMoney) && paymentMoney.length() < 22) {
                if (new BigDecimal(paymentMoney).multiply(exchangeRate.getBuyRateOfCcy()).compareTo(usdAmt) > 0) {
                    if (errorMsg.length() == 0) {
                        errorMsg.append("第").append(i + 1).append("行，交易金额不能超过5W美金或等值外币").append("\r\n");
                    } else {
                        int start = errorMsg.indexOf("\r");
                        errorMsg.insert(start, "|交易金额不能超过5万美金或等值外币|");
                    }
                }
                totalAmount = totalAmount.add(new BigDecimal(paymentMoney));
            }

            //校验交易币种
            if (!fiCbPaySettleDo.getIncomeCcy().equals(cbPaySettleOrderValidateBo.getOrderCcy())) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，交易币种和收汇币种不相同").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|交易币种和收汇币种不相同|");
                }
            }

            // 人民币金额小于20W
            if (Currency.CNY.getCode().equals(cbPaySettleOrderValidateBo.getOrderCcy()) && cnyAmt.compareTo(new BigDecimal(paymentMoney)) < 1) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，人民币金额不能超过20万").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|人民币金额不能超过20万|");
                }
            }

            //判断日元金额整数
            Pattern pattern = Pattern.compile("^[0-9]+([.]{1}[0-9]+){0,1}$");
            if (fiCbPaySettleDo.getIncomeCcy().equals(Constants.JPY_CURRENCY) && pattern.matcher(cbPaySettleOrderValidateBo.getOrderAmt()).matches()) {
                String amtString = cbPaySettleOrderValidateBo.getOrderAmt();
                if (amtString.contains(".")) {
                    String ss = amtString.substring(amtString.indexOf("."), amtString.length());
                    if (ss.equals(".0") || ss.equals(".00") || ss.equals(".000")) {

                    } else {
                        if (errorMsg.length() == 0) {
                            errorMsg.append("第").append(i + 1).append("行，日元金额必须为整数").append("\r\n");
                        } else {
                            int start = errorMsg.indexOf("\r");
                            errorMsg.insert(start, "|日元金额必须为整数|");
                        }
                    }
                }
            }

            if (errorMsg.length() == 0) {
                orderValidateBoList.add(cbPaySettleOrderValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            Integer rowNum = memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId());
            if (rowNum != null && (errorMsgMap.get(i) == null || errorMsgMap.get(i).length() == 0)) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append("第").append(i + 1).append("行：该行和第")
                        .append(memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId()) + 1).append("行商户订单号重复");
                stringBuffer.append("\r\n");
                errorMsgMap.put(i, stringBuffer);
            } else if (rowNum != null) {
                int start = errorMsg.indexOf("\r");
                errorMsgMap.get(i).insert(start, "|该行和第" + (memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId()) + 1)
                        + "行商户订单号重复");
            }

            //校验订单号是否存在
            memberTransMap.put(cbPaySettleOrderValidateBo.getMemberTransId(), i);
            memberTransIds.add(cbPaySettleOrderValidateBo.getMemberTransId());
            if (memberTransIds.size() == 200 || i == list.size() - 1) {
                checkMemberTransId(settleFileUploadReqBo, memberTransIds, errorMsgMap, memberTransMap);
                memberTransIds.clear();
            }
        }

        if (!errorMsgMap.isEmpty()) {
            for (Integer i : errorMsgMap.keySet()) {
                errorBuffer.append(errorMsgMap.get(i));
            }
        }

        //金额校验
        if (fiCbPaySettleDo.getIncomeAmt().compareTo(totalAmount) != 0) {
            log.info("收汇金额={}和文件明细金额={}不相等", fiCbPaySettleDo.getIncomeAmt(), totalAmount);
            errorBuffer.append("收汇金额和文件明细金额不相等");
        }

        settleOrderListBo.setVersion(version);
        settleOrderListBo.setErrorMsg(errorBuffer);
        settleOrderListBo.setTotalAmount(totalAmount);
        settleOrderListBo.setCbPaySettleOrderValidateBos(orderValidateBoList);
        settleOrderListBo.setErrorCount(errorMsgMap.size());
        settleOrderListBo.setSuccessCount(list.size() - errorMsgMap.size() - Constants.SETTLE_NO_CONTENT_LINE);
        log.info("代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return settleOrderListBo;
    }

    /**
     * excel 数据校验
     *
     * @param settleFileUploadReqBo 代理对象
     * @param list                  excel内容
     * @return 校验结果
     */
    private SettleOrderListBo checkVerify(String version, List<Object[]> list, SettleFileUploadReqBo settleFileUploadReqBo) {

        long startTime = System.currentTimeMillis();
        BigDecimal totalAmount = BigDecimal.ZERO;
        StringBuilder errorBuffer = new StringBuilder();
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        SettleOrderListBo settleOrderListBo = new SettleOrderListBo();

        //查询渠道汇率
        CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo1st = CbPaySettleConvert.toSettleOrderValidateBo(list.get(Constants.SETTLE_NO_CONTENT_LINE));
        //判断订单内容首条币种是否合法
        if (!(Constants.CURRENCY).contains(cbPaySettleOrderValidateBo1st.getOrderCcy()) || cbPaySettleOrderValidateBo1st.getOrderCcy().length() != 3 ||
                cbPaySettleOrderValidateBo1st.getOrderCcy().contains("|")) {
            log.info("excel批次，订单内容首条币种：{}", cbPaySettleOrderValidateBo1st.getOrderCcy());
            errorBuffer.append("excel订单内容首条币种不能为空");
            settleOrderListBo.setErrorMsg(errorBuffer);
            return settleOrderListBo;
        }
        //币种信息
        String ccy = cbPaySettleOrderValidateBo1st.getOrderCcy();
        if (StringUtils.isNotBlank(settleFileUploadReqBo.getManCcy())) {
            ccy = settleFileUploadReqBo.getManCcy();
        }

        Long channelId = Long.parseLong(Configuration.getString(ServiceConfigEnum.PING_AN_SETTLE_CHANNEL_ID.getKey()));
        CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, ccy);

        //四万五千美元校验，如果是美元时限定大小为49999，非美元是限额为49500
        BigDecimal usdAmt = new BigDecimal(49999).multiply(exchangeRate.getBuyRateOfCcy());
        if (!Currency.USD.getCode().equals(ccy)) {
            CgwExchangeRateResultDto usdExchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId, Currency.USD.getCode());
            usdAmt = new BigDecimal(49500).multiply(usdExchangeRate.getBuyRateOfCcy());
        }

        BigDecimal cnyAmt = new BigDecimal("200000");
        List<CbPaySettleOrderValidateBo> orderValidateBoList = new ArrayList<>();
        for (int i = Constants.SETTLE_NO_CONTENT_LINE; i < list.size(); i++) {
            CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo = CbPaySettleConvert.toSettleOrderValidateBo(list.get(i));
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.settleFileValid(version, i, cbPaySettleOrderValidateBo);

            //校验每笔订单明细金额不能大于50000美元
            String paymentMoney = cbPaySettleOrderValidateBo.getOrderAmt();
            if (StringUtils.isNotBlank(paymentMoney) && ProxyCustomConvert.isTrreeNumber(paymentMoney) && paymentMoney.length() < 22) {
                if (new BigDecimal(paymentMoney).multiply(exchangeRate.getBuyRateOfCcy()).compareTo(usdAmt) > 0) {
                    if (errorMsg.length() == 0) {
                        errorMsg.append("第").append(i + 1).append("行，交易金额不能超过5W美金或等值外币").append("\r\n");
                    } else {
                        int start = errorMsg.indexOf("\r");
                        errorMsg.insert(start, "|交易金额不能超过5万美金或等值外币|");
                    }
                    log.error("订单号：{},交易金额：{},币种：{}超过等值5W美元", cbPaySettleOrderValidateBo.getMemberTransId(),
                            cbPaySettleOrderValidateBo.getOrderAmt(), cbPaySettleOrderValidateBo.getOrderCcy());
                }
                //统计总金额
                totalAmount = totalAmount.add(new BigDecimal(paymentMoney));
            }

            //校验交易币种,明细校验工具上传文件时调用
            String incomeCcy = cbPaySettleOrderValidateBo.getOrderCcy();
            if (!(Constants.CURRENCY).contains(incomeCcy) || incomeCcy.length() != 3 || incomeCcy.contains("|")) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，交易币种不存在").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|交易币种不存在|");
                }
            }

            // 人民币金额小于20W
            if (Currency.CNY.getCode().equals(incomeCcy) && cnyAmt.compareTo(new BigDecimal(paymentMoney)) < 1) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，人民币金额不能超过20万").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|人民币金额不能超过20万|");
                }
            }

            //校验交易币种是否统一
            if (!cbPaySettleOrderValidateBo1st.getOrderCcy().equals(cbPaySettleOrderValidateBo.getOrderCcy())) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，交易币种和订单行首行币种不相同").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|交易币种和和订单行首行币种不相同|");
                }
            }

            //如果ManCcy不为空，则校验
            if (!StringUtil.isBlank(settleFileUploadReqBo.getManCcy()) && !settleFileUploadReqBo.getManCcy().
                    equals(cbPaySettleOrderValidateBo.getOrderCcy())) {
                if (errorMsg.length() == 0) {
                    errorMsg.append("第").append(i + 1).append("行，交易币种和商户录入币种不相同").append("\r\n");
                } else {
                    int start = errorMsg.indexOf("\r");
                    errorMsg.insert(start, "|交易币种和商户录入币种不相同|");
                }
            }


            //判断日元金额整数
            Pattern pattern = Pattern.compile("^[0-9]+([.]{1}[0-9]+){0,1}$");
            if (cbPaySettleOrderValidateBo.getOrderCcy().equals(Constants.JPY_CURRENCY) && pattern.matcher(cbPaySettleOrderValidateBo.getOrderAmt()).matches()) {
                String amtString = cbPaySettleOrderValidateBo.getOrderAmt();
                if (amtString.contains(".")) {
                    String ss = amtString.substring(amtString.indexOf("."), amtString.length());
                    if (!ss.equals(".0") && !ss.equals(".00") && !ss.equals(".000")) {
                        if (errorMsg.length() == 0) {
                            errorMsg.append("第").append(i + 1).append("行，日元金额必须为整数").append("\r\n");
                        } else {
                            int start = errorMsg.indexOf("\r");
                            errorMsg.insert(start, "|日元金额必须为整数|");
                        }
                    }
                }
            }

            if (errorMsg.length() == 0) {
                orderValidateBoList.add(cbPaySettleOrderValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            Integer rowNum = memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId());
            if (rowNum != null && (errorMsgMap.get(i) == null || errorMsgMap.get(i).length() == 0)) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append("第").append(i + 1).append("行：该行和第")
                        .append(memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId()) + 1).append("行商户订单号重复");
                stringBuffer.append("\r\n");
                errorMsgMap.put(i, stringBuffer);
            } else if (rowNum != null) {
                int start = errorMsg.indexOf("\r");
                errorMsgMap.get(i).insert(start, "|该行和第" + (memberTransMap.get(cbPaySettleOrderValidateBo.getMemberTransId()) + 1)
                        + "行商户订单号重复");
            }

            //校验订单号是否存在
            memberTransMap.put(cbPaySettleOrderValidateBo.getMemberTransId(), i);
            memberTransIds.add(cbPaySettleOrderValidateBo.getMemberTransId());
            if (memberTransIds.size() == 200 || i == list.size() - 1) {
                checkMemberTransId(settleFileUploadReqBo, memberTransIds, errorMsgMap, memberTransMap);
                memberTransIds.clear();
            }
        }
        if (!errorMsgMap.isEmpty()) {
            for (Integer i : errorMsgMap.keySet()) {
                errorBuffer.append(errorMsgMap.get(i));
            }
        }
        //校验商户输入的总金额和文件总金额是否相等
        if (settleFileUploadReqBo.getManTotalMoney() != null &&
                settleFileUploadReqBo.getManTotalMoney().compareTo(totalAmount) != 0) {
            if (StringUtils.isEmpty(errorBuffer.toString())) {
                errorBuffer.append("|");
            }
            log.error("商户录入的金额和文件明细总金额不相等,输入总金额：{},订单明细总金额:{}", totalAmount,
                    settleFileUploadReqBo.getManTotalMoney());
            errorBuffer.append("商户录入的金额和文件明细总金额不相等");
        }
        settleOrderListBo.setVersion(version);
        settleOrderListBo.setErrorMsg(errorBuffer);
        settleOrderListBo.setTotalAmount(totalAmount);
        settleOrderListBo.setCbPaySettleOrderValidateBos(orderValidateBoList);
        settleOrderListBo.setErrorCount(errorMsgMap.size());
        settleOrderListBo.setSuccessCount(list.size() - errorMsgMap.size() - Constants.SETTLE_NO_CONTENT_LINE);
        log.info("代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return settleOrderListBo;
    }

    /**
     * 校验订单号是否在数据库存在
     *
     * @param settleFileUploadReqBo 代理对象
     * @param memberTransIds        需要校验的商户订单号
     * @param errorMsgMap           错误信息集合
     * @param memberTransMap        商户订单集合
     */
    private void checkMemberTransId(SettleFileUploadReqBo settleFileUploadReqBo, List<String> memberTransIds, Map<Integer,
            StringBuilder> errorMsgMap, Map<String, Integer> memberTransMap) {

        List<String> merchantTrans = fiCbPaySettleOrderMapper.selectMerchantTrans(settleFileUploadReqBo.getMemberId(),
                memberTransIds);

        if (merchantTrans != null && !merchantTrans.isEmpty()) {
            for (String memberTransId : merchantTrans) {
                if (errorMsgMap.get(memberTransMap.get(memberTransId)) == null) {
                    StringBuilder errorBuffer = new StringBuilder();
                    errorMsgMap.put(memberTransMap.get(memberTransId), errorBuffer.append("第")
                            .append(memberTransMap.get(memberTransId) + 1).append("行:商户订单号存在").append("\r\n"));
                } else {
                    int i = errorMsgMap.get(memberTransMap.get(memberTransId)).indexOf("\r");
                    errorMsgMap.get(memberTransMap.get(memberTransId)).insert(i, "|商户订单号存在");
                }
            }
            memberTransIds.clear();
        }
    }
}
