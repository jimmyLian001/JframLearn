package com.baofu.cbpayservice.biz.validation;

import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.RegularExpressionConstants;
import com.baofu.cbpayservice.common.enums.UploadFileOrderType;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * User: 康志光 Date: 2017/3/15 ProjectName: cbpay-customs-service Version: 1.0
 */
@Slf4j
public class ValidationUtil {

    /**
     * @param orderType      订单类型
     * @param index          行数
     * @param proxyCustomsBo 返回对象
     * @returnint
     */
    public static StringBuilder valid(String orderType, int index, ProxyCustomsValidateBo proxyCustomsBo) {

        //校验文件基础信息
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(proxyCustomsBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(proxyCustomsBo.getPayeeIdType(), proxyCustomsBo.getIdCardNo(), validateStr);

        //******************* 运单信息校验    ***********************//
        if (StringUtils.isNotBlank(proxyCustomsBo.getOrderLogisticsValidationBo().getLogisticsNumber())) {
            validateStr.append(ParamValidate.validateParams(proxyCustomsBo.getOrderLogisticsValidationBo(), Constants.SPLIT_MARK));
        }

        //******************* 商品信息校验    ***********************//
        String[] spNameStr = proxyCustomsBo.getGoodsName().split(Constants.SPLIT_SYMBOL);
        String[] spNumberStr = proxyCustomsBo.getGoodsNum().split(Constants.SPLIT_SYMBOL);
        String[] spMoneyStr = proxyCustomsBo.getGoodsPrice().split(Constants.SPLIT_SYMBOL);
        validateStr = productCheck(spNameStr, spNumberStr, spMoneyStr, validateStr, "0");

        //******************* 服务贸易与货物贸易校验    ***********************//
        if (UploadFileOrderType.GOODS_TRADE.getCode().equals(orderType)) {

            if (org.apache.commons.lang.StringUtils.isBlank(proxyCustomsBo.getCompanyOrderNo())) {
                validateStr.append("|电商平台订单号不能为空");
            }

            //支付货款
            if (proxyCustomsBo.getPayGoodsAmount() == null) {
                validateStr.append("|支付货款不能为空");
            }

            //支付税款
            if (proxyCustomsBo.getPayTaxAmount() == null) {
                validateStr.append("|支付税款不能为空");
            }
            //支付运费
            if (proxyCustomsBo.getPayFeeAmount() == null) {
                validateStr.append("|支付运费不能为空");
            }

            //支付保费
            if (proxyCustomsBo.getPayPreAmount() == null) {
                validateStr.append("|支付保费不能为空");
            }

            //支付金额校验
            BigDecimal remainVal = new BigDecimal(proxyCustomsBo.getOrderMoney()).subtract(new BigDecimal(proxyCustomsBo.getPayFeeAmount()))
                    .subtract(new BigDecimal(proxyCustomsBo.getPayGoodsAmount())).subtract(new BigDecimal(proxyCustomsBo.getPayPreAmount()))
                    .subtract(new BigDecimal(proxyCustomsBo.getPayTaxAmount()));
            if (remainVal.compareTo(new BigDecimal(0)) != 0
                    && remainVal.compareTo(new BigDecimal(proxyCustomsBo.getOrderMoney())) != 0) {
                validateStr.append("|订单金额不等于支付货款+支付税款+支付运费+支付保费");
            }
        }

        if (validateStr.length() > 0) {
            validateStr.insert(0, "第" + (index + 1) + "行:");
            log.warn("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, proxyCustomsBo);
            validateStr.append("\r\n");
        }

        return validateStr;
    }


    /**
     * 结汇上传文件明细校验
     *
     * @param index                      行数
     * @param cbPaySettleOrderValidateBo 文件行数内容
     * @return 校验内容
     */
    public static StringBuilder settleFileValid(String version, int index, CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo) {

        //校验文件基础信息
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(cbPaySettleOrderValidateBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(cbPaySettleOrderValidateBo.getPayeeIdType(), cbPaySettleOrderValidateBo.getPayeeIdNo(), validateStr);

        //******************* 运单信息校验    ***********************//
        if (StringUtils.isNotBlank(cbPaySettleOrderValidateBo.getOrderLogisticsValidationBo().getLogisticsNumber())) {
            String err = ParamValidate.validateParams(cbPaySettleOrderValidateBo.getOrderLogisticsValidationBo(), Constants.SPLIT_MARK);
            if (err.length() > 0) {
                validateStr.append(Constants.SPLIT_MARK).append(err);
            }
        }
        //******************* 商品信息校验    ***********************//
        String splitSymbol = null;
        if (Constants.VERSION_1_1.equals(version)) {
            splitSymbol = Constants.SPLIT_DOLLAR_DOLLAR;
        } else {
            splitSymbol = Constants.SPLIT_SYMBOL;
        }

        String[] spNameStr = cbPaySettleOrderValidateBo.getGoodsName().split(splitSymbol);
        String[] spNumberStr = cbPaySettleOrderValidateBo.getGoodsNum().split(splitSymbol);
        String[] spMoneyStr = cbPaySettleOrderValidateBo.getGoodsPrice().split(splitSymbol);
        validateStr = productCheck(spNameStr, spNumberStr, spMoneyStr, validateStr, "1");

        if (validateStr.length() > 0) {
            validateStr.insert(0, String.format("第%s行:", index + 1));
            validateStr.append("\r\n");
            log.info("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, cbPaySettleOrderValidateBo);
        }

        return validateStr;
    }

    /**
     * 功能：商品基本信息校验
     *
     * @param spNameStr   商品名称集合
     * @param spNumberStr 商品数量集合
     * @param spMoneyStr  商品单价集合
     * @param validateStr 错误信息
     * @return 错误信息
     */
    private static StringBuilder productCheck(String[] spNameStr, String[] spNumberStr, String[] spMoneyStr,
                                              StringBuilder validateStr, String flag) {
        if (spNameStr.length != spNumberStr.length || spNumberStr.length != spMoneyStr.length) {
            validateStr.append("|商品名称，商品数量，商品金额填写不一致");
        } else {
            for (int i = 0; i < spNameStr.length; i++) {
                if ("".equals(spNameStr[i]) || spNameStr[i].length() > 256) {
                    validateStr.append("|商品名称格式异常");
                }
                try {
                    if (!spNumberStr[i].matches(Constants.AMOUNT_REG)) {
                        if (Integer.parseInt(spNumberStr[i].substring(0, spNumberStr[i].indexOf("."))) < 0) {
                            validateStr.append("|商品数量不能为负");
                        }
                    }
                } catch (Exception e) {
                    validateStr.append("|商品数量异常");
                }

                //0：表示购汇商品金额校验，1：表示结汇商品金额校验
                if ("0".equals(flag)) {
                    if (!spMoneyStr[i].matches(Constants.AMT_REG)) {
                        validateStr.append("|商品金额异常");
                    }
                } else if ("1".equals(flag)) {
                    if (!spMoneyStr[i].matches(Constants.SETTLE_AMT_REG)) {
                        validateStr.append("|商品金额异常");
                    }
                }
            }
        }

        return validateStr;
    }

    /**
     * 功能：航旅文件校验
     *
     * @param index              行数
     * @param airRemitValidateBo 文件一行内容
     * @return
     */
    public static StringBuilder airRemitValid(int index, AirRemitValidateBo airRemitValidateBo) {

        //校验文件基础信息
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(airRemitValidateBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(airRemitValidateBo.getPayeeIdType(), airRemitValidateBo.getIdCardNo(), validateStr);

        //******************* 航班信息校验    ***********************//
        String[] destinationAddresses = airRemitValidateBo.getDestinationAddress().split(Constants.SPLIT_SYMBOL);
        String[] flightNumbers = airRemitValidateBo.getFlightNumber().split(Constants.SPLIT_SYMBOL);
        String[] takeoffTimes = airRemitValidateBo.getTakeoffTime().split(Constants.SPLIT_SYMBOL);
        validateStr = airInfoCheck(destinationAddresses, flightNumbers, takeoffTimes, validateStr, "0");

        if (validateStr.length() > 0) {
            validateStr.insert(0, String.format("第%s行:", index + 1));
            validateStr.append("\r\n");
            log.info("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, airRemitValidateBo);
        }
        return validateStr;
    }

    /**
     * 功能：留学校验文件基础信息
     *
     * @param index   excel 行索引
     * @param proxyBo
     * @return
     */
    public static StringBuilder studyAbroadRemitValid(int index, CbPayOrderStudyAbroadValidateBo proxyBo) {
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(proxyBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(proxyBo.getPayeeIdType(), proxyBo.getIdCardNo(), validateStr);

        validateStr.append(studyAbroadInfoCheck(proxyBo));
        if (validateStr.length() > 0) {
            validateStr.insert(0, String.format("第%s行:", index + 1));
            validateStr.append("\r\n");
            log.info("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, proxyBo);
        }
        return validateStr;
    }

    private static StringBuilder studyAbroadInfoCheck(CbPayOrderStudyAbroadValidateBo proxyBo) {
        StringBuilder validateStr = new StringBuilder();
        String[] schoolCountryCodeArray = proxyBo.getSchoolCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] schoolNameArray = proxyBo.getSchoolName().split(Constants.SPLIT_SYMBOL);
        String[] studentNameArray = proxyBo.getStudentName().split(Constants.SPLIT_SYMBOL);
        String[] studentIdArray = proxyBo.getStudentId().split(Constants.SPLIT_SYMBOL);
        String[] admissionNoticeIdArray = proxyBo.getAdmissionNoticeId().split(Constants.SPLIT_SYMBOL);
        if (schoolCountryCodeArray.length != schoolNameArray.length || schoolNameArray.length != studentNameArray.length
                || studentNameArray.length != studentIdArray.length || studentIdArray.length != admissionNoticeIdArray.length) {
            validateStr.append("|学校所在国家，学校名称，学生姓名，学生学号，入学通知书编号填写不一致");
        } else {
            for (int i = 0; i < schoolCountryCodeArray.length; i++) {
                if ("".equals(schoolCountryCodeArray[i]) || schoolCountryCodeArray[i].length() > 8) {
                    validateStr.append("|学校所在国家填写异常");
                }
            }
            for (int i = 0; i < schoolNameArray.length; i++) {
                if ("".equals(schoolNameArray[i]) || schoolNameArray[i].length() > 128) {
                    validateStr.append("|学校名称填写异常");
                }
            }
            for (int i = 0; i < studentNameArray.length; i++) {
                if ("".equals(studentNameArray[i]) || studentNameArray[i].length() > 64) {
                    validateStr.append("|学生姓名填写异常");
                }
            }
            for (int i = 0; i < studentIdArray.length; i++) {
                if ("".equals(studentIdArray[i]) || studentIdArray[i].length() > 64) {
                    validateStr.append("|学生学号填写异常");
                }
            }
            for (int i = 0; i < admissionNoticeIdArray.length; i++) {
                if ("".equals(admissionNoticeIdArray[i]) || admissionNoticeIdArray[i].length() > 64) {
                    validateStr.append("|入学通知书编号填写异常");
                }
            }
        }
        return validateStr;
    }

    /**
     * 功能：酒店校验文件基础信息
     *
     * @param index                     excel 行索引
     * @param cbPayOrderHotelValidateBo
     * @return
     */
    public static StringBuilder hotelRemitValid(int index, CbPayOrderHotelValidateBo cbPayOrderHotelValidateBo) {
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(cbPayOrderHotelValidateBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(cbPayOrderHotelValidateBo.getPayeeIdType(), cbPayOrderHotelValidateBo.getIdCardNo(), validateStr);

        validateStr.append(hotelInfoCheck(cbPayOrderHotelValidateBo));
        if (validateStr.length() > 0) {
            validateStr.insert(0, String.format("第%s行:", index + 1));
            validateStr.append("\r\n");
            log.info("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, cbPayOrderHotelValidateBo);
        }
        return validateStr;
    }

    private static StringBuilder hotelInfoCheck(CbPayOrderHotelValidateBo cbPayOrderHotelValidateBo) {
        StringBuilder retStr = new StringBuilder();
        String[] hotelCountryCodeArray = cbPayOrderHotelValidateBo.getHotelCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] hotelNameArray = cbPayOrderHotelValidateBo.getHotelName().split(Constants.SPLIT_SYMBOL);
        String[] checkInDateArray = cbPayOrderHotelValidateBo.getCheckInDate().split(Constants.SPLIT_SYMBOL);
        String[] nightCountArray = cbPayOrderHotelValidateBo.getNightCount().split(Constants.SPLIT_SYMBOL);
        if (hotelCountryCodeArray.length != hotelNameArray.length || hotelNameArray.length != checkInDateArray.length || checkInDateArray.length != nightCountArray.length) {
            retStr.append("|酒店所在国家，酒店名称，入住日期，间夜数填写不一致");
        } else {
            for (int i = 0; i < hotelCountryCodeArray.length; i++) {
                if ("".equals(hotelCountryCodeArray[i]) || hotelCountryCodeArray[i].length() > 8) {
                    retStr.append("|酒店所在国家填写异常");
                }
            }
            for (int i = 0; i < hotelNameArray.length; i++) {
                if ("".equals(hotelNameArray[i]) || hotelNameArray[i].length() > 128) {
                    retStr.append("|酒店名称填写异常");
                }
            }
            for (int i = 0; i < checkInDateArray.length; i++) {
                if ("".equals(checkInDateArray[i]) || !checkInDateArray[i].matches(RegularExpressionConstants.YYYY_MM_DD + "|" + RegularExpressionConstants.YYYYMMDD)) {
                    retStr.append("|入住日期格式填写错误");
                }
            }
            for (int i = 0; i < nightCountArray.length; i++) {
                try {
                    String nightCount = nightCountArray[i].trim();
                    if (!nightCount.matches(Constants.AMT_REG)) {
                        if (Integer.parseInt(nightCount.substring(0, nightCount.indexOf("."))) < 0) {
                            retStr.append("|间夜数不能小于0");
                        } else {
                            new BigDecimal(nightCount);
                        }
                    } else {
                        if (new BigDecimal(nightCount).compareTo(new BigDecimal(0)) <= 0 || new BigDecimal(nightCount).compareTo(new BigDecimal(1000000)) > 0) {
                            retStr.append("|间夜数不合法");
                        } else if (nightCount.indexOf('.') != -1 && nightCount.substring(nightCount.indexOf(".") + 1, nightCount.length()).length() > 1) {
                            retStr.append("|间夜数不合法");
                        }
                    }
                } catch (Exception e) {
                    retStr.append("|间夜数填写异常");
                }
            }
        }
        return retStr;
    }

    /**
     * 功能：旅游校验文件基础信息
     *
     * @param index   excel 行索引
     * @param proxyBo
     * @return
     */
    public static StringBuilder tourismRemitValid(int index, CbPayOrderTourismValidateBo proxyBo) {
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(proxyBo, Constants.SPLIT_MARK));

        //证件号码校验
        idNumberCheck(proxyBo.getPayeeIdType(), proxyBo.getIdCardNo(), validateStr);

        validateStr.append(tourismInfoCheck(proxyBo));
        if (validateStr.length() > 0) {
            validateStr.insert(0, String.format("第%s行:", index + 1));
            validateStr.append("\r\n");
            log.info("call 校验文件错误信息：{}，请求校验参数信息：{}", validateStr, proxyBo);
        }
        return validateStr;
    }

    private static StringBuilder tourismInfoCheck(CbPayOrderTourismValidateBo proxyBo) {
        StringBuilder retStr = new StringBuilder();
        String[] travelAgencyNameArray = proxyBo.getTravelAgencyName().split(Constants.SPLIT_SYMBOL);
        String[] travelCountryCodeArray = proxyBo.getTravelCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] tripDateArray = proxyBo.getTripDate().split(Constants.SPLIT_SYMBOL);
        String[] tourDaysArray = proxyBo.getTourDays().split(Constants.SPLIT_SYMBOL);
        if (travelAgencyNameArray.length != travelCountryCodeArray.length || travelCountryCodeArray.length != tripDateArray.length || tripDateArray.length != tourDaysArray.length) {
            retStr.append("|旅行社名称，旅行国家，出行日期，旅游天数填写不一致");
        } else {
            for (int i = 0; i < travelAgencyNameArray.length; i++) {
                if ("".equals(travelAgencyNameArray[i]) || travelAgencyNameArray[i].length() > 128) {
                    retStr.append("|旅行社名称填写异常");
                }
            }
            for (int i = 0; i < travelCountryCodeArray.length; i++) {
                if ("".equals(travelCountryCodeArray[i]) || travelCountryCodeArray[i].length() > 8) {
                    retStr.append("|旅行国家填写异常");
                }
            }
            for (int i = 0; i < tripDateArray.length; i++) {
                if ("".equals(tripDateArray[i]) || !tripDateArray[i].matches(RegularExpressionConstants.YYYY_MM_DD + "|" + RegularExpressionConstants.YYYYMMDD)) {
                    retStr.append("|出行日期格式填写错误");
                }
            }
            for (int i = 0; i < tourDaysArray.length; i++) {
                try {
                    String tourDays = tourDaysArray[i].trim();
                    if (!tourDays.matches(Constants.AMT_REG)) {
                        if (Integer.parseInt(tourDays.substring(0, tourDays.indexOf("."))) < 0) {
                            retStr.append("|旅游天数不能小于0");
                        } else {
                            new BigDecimal(tourDays);
                        }
                    } else {
                        if (new BigDecimal(tourDays).compareTo(new BigDecimal(0)) <= 0
                                || new BigDecimal(tourDays).compareTo(new BigDecimal(1000000)) > 0) {
                            retStr.append("|旅游天数不合法");
                        } else if (tourDays.indexOf(".") != -1 && tourDays.substring(tourDays.indexOf(".") + 1, tourDays.length()).length() > 1) {
                            retStr.append("|旅游天数不合法");
                        }
                    }
                } catch (Exception e) {
                    retStr.append("|旅游天数填写异常");
                }
            }
        }
        return retStr;
    }

    /**
     * 航旅信息校验
     *
     * @param destinationAddresses 起飞地目的地
     * @param flightNumbers        航班号
     * @param takeoffTimes         起飞时间
     * @param validateStr          错误信息
     * @return 错误信息
     */
    private static StringBuilder airInfoCheck(String[] destinationAddresses, String[] flightNumbers, String[] takeoffTimes,
                                              StringBuilder validateStr, String flag) {
        if (destinationAddresses.length != flightNumbers.length || flightNumbers.length != takeoffTimes.length) {
            validateStr.append("|起飞地目的地，航班号，起飞时间填写不一致");
        } else {
            for (int i = 0; i < destinationAddresses.length; i++) {
                if ("".equals(destinationAddresses[i]) || destinationAddresses[i].length() > 256) {
                    validateStr.append("|起飞地目的地填写异常");
                }
            }

            for (int i = 0; i < takeoffTimes.length; i++) {
                if ("".equals(takeoffTimes[i]) || !takeoffTimes[i].matches(RegularExpressionConstants.YYYYY_MM_DD_HH_MM + "|" + RegularExpressionConstants.YYYYYMMDDHHMM)) {
                    validateStr.append("|起飞时间格式填写错误");
                }

            }

            for (int i = 0; i < flightNumbers.length; i++) {
                if ("".equals(flightNumbers[i]) || flightNumbers[i].length() > 256) {
                    validateStr.append("|航班号填写异常");
                }
            }

        }

        return validateStr;
    }

    /**
     * 证件号码校验
     *
     * @param idType      证件类型
     * @param idCardNo    证件号码
     * @param validateStr 错误信息
     */
    public static void idNumberCheck(String idType, String idCardNo, StringBuilder validateStr) {
        //证件类型不正确时，只校验证件号码是否为空
        if (idType == null || !Pattern.matches("(01)|(1)|(02)|(2)", idType)) {
            if (StringUtils.isBlank(idCardNo)) {
                validateStr.append("|证件号码不能为空");
            }
            return;
        }

        //证件类型为身份证
        if (Integer.valueOf(idType) == 1) {
            if (idCardNo == null || !Pattern.matches(Constants.ID_REGX, idCardNo)) {
                validateStr.append("|身份证号码不正确");
            }
        }
        //证件类型为组织机构代码证
        if (Integer.valueOf(idType) == 2) {
            if (idCardNo == null || !Pattern.matches(Constants.OC_REGX, idCardNo)) {
                validateStr.append("|组织机构代码不正确");
            }
        }
    }

}
