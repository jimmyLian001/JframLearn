package com.baofu.cbpayservice.biz.convert;

import com.baofoo.Response;
import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.constants.RegularExpressionConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.common.util.*;
import com.baofu.cbpayservice.dal.models.*;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代理相关数据转换
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class ProxyCustomConvert {

    /**
     * 服务贸易excel数据转换成多代理上报对象
     * <p>
     * 0  (A)商户流水号
     * 1  (B)订单币种
     * 2  (C)订单金额
     * 3  (D)交易时间
     * 4  (E)支付人姓名
     * 5  (F)支付人身份证号码
     * add 是否银行账户
     * 6  (G)账户
     * 7  (H)预留手机号码
     * 8  (I)商品名称
     * 9  (J)商品数量
     * 10 (K)单价
     * 11 (L)快递公司
     * 12 (M)运单号
     * 13 (N)收货人姓名
     * 14 (O)收货人联系方式
     * 15 (P)收货人地址
     * 16 (Q)发货日期
     *
     * @param objects excel 转换成list数据
     * @return 代理上报对象
     * update by feng_jiang 2017/7/7
     */
    public static ProxyCustomsValidateBo excelServiceradeCustomBos(Object[] objects) {
        int i = 0;
        ProxyCustomsValidateBo bo = new ProxyCustomsValidateBo();
        OrderLogisticsValidationBo orderLogisticsValidationBo = new OrderLogisticsValidationBo();
        bo.setMemberTransId(StringUtil.objToString(objects[i++]));                                  //商户订单号
        bo.setOrderCcy(StringUtil.objToString(objects[i++]));                                       //订单币种
        bo.setOrderMoney(StringUtil.objToString(objects[i++]));                                     //订单金额
        bo.setTradeDate(StringUtil.objToString(objects[i++]));                                      //交易时间
        bo.setPayeeIdType(StringUtil.objToString(objects[i++]));                                    //付款人证件类型
        bo.setIdHolder(StringUtil.objToString(objects[i++]));                                       //付款人姓名
        bo.setIdCardNo(StringUtil.objToString(objects[i++]));                                       //付款人证件号码
        bo.setBankCardNo(StringUtil.objToString(objects[i++]));                                     //付款人账户
        String temp = StringUtil.objToString(objects[i++]);
        bo.setMobile("".equals(temp) ? BigDecimal.ZERO.toPlainString() : temp);                          //预留手机号码
        bo.setGoodsName(StringUtil.objToString(objects[i++]));                                      //商品名称
        bo.setGoodsNum(StringUtil.objToString(objects[i++]));                                       //商品数量
        bo.setGoodsPrice(StringUtil.objToString(objects[i++]));                                     //商品单价
        orderLogisticsValidationBo.setLogisticsCompanyNumber(StringUtil.objToString(objects[i++])); //快递公司编码
        orderLogisticsValidationBo.setLogisticsNumber(StringUtil.objToString(objects[i++]));        //运单号
        orderLogisticsValidationBo.setConsigneeName(StringUtil.objToString(objects[i++]));          //收货人姓名
        orderLogisticsValidationBo.setConsigneeContact(StringUtil.objToString(objects[i++]));       //收货人联系方式
        orderLogisticsValidationBo.setConsigneeAddress(StringUtil.objToString(objects[i++]));       //收货人地址
        orderLogisticsValidationBo.setDeliveryDate(StringUtil.objToString(objects[i++]));           //发货日期
        bo.setOrderLogisticsValidationBo(orderLogisticsValidationBo);
        bo.setFunctionId(BigDecimal.ZERO.toPlainString());
        return bo;
    }

    /**
     * 功能：留学excel数据转换成多代理上报对象
     *
     * @param objects 数据集合
     * @return 留学报关对象
     * Created by feng_jiang 2017/7/7
     */
    public static CbPayOrderStudyAbroadValidateBo toCbPayOrderStudyAbroadValidateBo(Object[] objects) {
        int i = 0;
        CbPayOrderStudyAbroadValidateBo bo = new CbPayOrderStudyAbroadValidateBo();
        bo.setMemberTransId(StringUtil.objToString(objects[i++]));                              //商户订单号
        bo.setOrderCcy(StringUtil.objToString(objects[i++]));                                   //订单币种
        bo.setOrderMoney(StringUtil.objToString(objects[i++]));                                 //订单金额
        bo.setTradeDate(StringUtil.objToString(objects[i++]));                                  //交易时间
        bo.setPayeeIdType(StringUtil.objToString(objects[i++]));                                //付款人证件类型
        bo.setIdHolder(StringUtil.objToString(objects[i++]));                                   //付款人姓名
        bo.setIdCardNo(StringUtil.objToString(objects[i++]));                                   //付款人证件号码
        bo.setBankCardNo(StringUtil.objToString(objects[i++]));                                 //付款人账户
        String temp = StringUtil.objToString(objects[i++]);
        bo.setMobile("".equals(temp) ? BigDecimal.ZERO.toPlainString() : temp);                 //预留手机号码
        bo.setSchoolCountryCode(StringUtil.objToString(objects[i++]));                          //学校所在国家
        bo.setSchoolName(StringUtil.objToString(objects[i++]));                                 //学校名称
        bo.setStudentName(StringUtil.objToString(objects[i++]));                                //学生姓名
        bo.setStudentId(StringUtil.objToString(objects[i++]));                                  //学生学号
        bo.setAdmissionNoticeId(StringUtil.objToString(objects[i++]));                          //入学通知书编号*
        return bo;
    }

    /**
     * 功能：酒店excel数据转换成多代理上报对象
     *
     * @param objects 数据集合
     * @return CbPayOrderHotelValidateBo
     * Created by feng_jiang 2017/7/7
     */
    public static CbPayOrderHotelValidateBo toCbPayOrderHotelValidateBo(Object[] objects) {
        int i = 0;
        CbPayOrderHotelValidateBo bo = new CbPayOrderHotelValidateBo();
        bo.setMemberTransId(StringUtil.objToString(objects[i++]));                              //商户订单号
        bo.setOrderCcy(StringUtil.objToString(objects[i++]));                                   //订单币种
        bo.setOrderMoney(StringUtil.objToString(objects[i++]));                                 //订单金额
        bo.setTradeDate(StringUtil.objToString(objects[i++]));                                  //交易时间
        bo.setPayeeIdType(StringUtil.objToString(objects[i++]));                                //付款人证件类型
        bo.setIdHolder(StringUtil.objToString(objects[i++]));                                   //付款人姓名
        bo.setIdCardNo(StringUtil.objToString(objects[i++]));                                   //付款人证件号码
        bo.setBankCardNo(StringUtil.objToString(objects[i++]));                                 //付款人账户
        String temp = StringUtil.objToString(objects[i++]);
        bo.setMobile("".equals(temp) ? BigDecimal.ZERO.toPlainString() : temp);                 //预留手机号码
        bo.setHotelCountryCode(StringUtil.objToString(objects[i++]));                           //酒店所在国家
        bo.setHotelName(StringUtil.objToString(objects[i++]));                                  //酒店名称
        bo.setCheckInDate(StringUtil.objToString(objects[i++]));                                //入住日期
        bo.setNightCount(StringUtil.objToString(objects[i++]));                                 //间夜数
        return bo;
    }

    /**
     * 功能：旅游excel数据转换成多代理上报对象
     *
     * @param objects 数据集合
     * @return 旅游报关对象
     * Created by feng_jiang 2017/7/7
     */
    public static CbPayOrderTourismValidateBo toCbPayOrderTourismValidateBo(Object[] objects) {
        int i = 0;
        CbPayOrderTourismValidateBo bo = new CbPayOrderTourismValidateBo();
        bo.setMemberTransId(StringUtil.objToString(objects[i++]));                              //商户订单号
        bo.setOrderCcy(StringUtil.objToString(objects[i++]));                                   //订单币种
        bo.setOrderMoney(StringUtil.objToString(objects[i++]));                                 //订单金额
        bo.setTradeDate(StringUtil.objToString(objects[i++]));                                  //交易时间
        bo.setPayeeIdType(StringUtil.objToString(objects[i++]));                                //付款人证件类型
        bo.setIdHolder(StringUtil.objToString(objects[i++]));                                   //付款人姓名
        bo.setIdCardNo(StringUtil.objToString(objects[i++]));                                   //付款人证件号码
        bo.setBankCardNo(StringUtil.objToString(objects[i++]));                                 //付款人账户
        String temp = StringUtil.objToString(objects[i++]);
        bo.setMobile("".equals(temp) ? BigDecimal.ZERO.toPlainString() : temp);                 //预留手机号码
        bo.setTravelAgencyName(StringUtil.objToString(objects[i++]));                           //旅行社名称
        bo.setTravelCountryCode(StringUtil.objToString(objects[i++]));                          //旅行国家
        bo.setTripDate(StringUtil.objToString(objects[i++]));                                   //出行日期
        bo.setTourDays(StringUtil.objToString(objects[i++]));                                   //旅游天数
        return bo;
    }

    /**
     * 功能：服务贸易excel数据转换成多代理上报对象
     *
     * @param objects 数据集合
     * @return 航旅对象
     * Created by feng_jiang 2017/7/7
     */
    public static AirRemitValidateBo toAirRemitValidateBo(Object[] objects) {
        int i = 0;
        AirRemitValidateBo bo = new AirRemitValidateBo();
        bo.setMemberTransId(StringUtil.objToString(objects[i++]));                              //商户订单号
        bo.setOrderCcy(StringUtil.objToString(objects[i++]));                                   //订单币种
        bo.setOrderMoney(StringUtil.objToString(objects[i++]));                                 //订单金额
        bo.setTradeDate(StringUtil.objToString(objects[i++]));                                  //交易时间
        bo.setPayeeIdType(StringUtil.objToString(objects[i++]));                                //付款人证件类型
        bo.setIdHolder(StringUtil.objToString(objects[i++]));                                   //付款人姓名
        bo.setIdCardNo(StringUtil.objToString(objects[i++]));                                   //付款人证件号码
        bo.setBankCardNo(StringUtil.objToString(objects[i++]));                                 //付款人账户
        String temp = StringUtil.objToString(objects[i++]);
        bo.setMobile("".equals(temp) ? BigDecimal.ZERO.toPlainString() : temp);                 //预留手机号码
        bo.setFlightNumber(StringUtil.objToString(objects[i++]));                               //航班号
        bo.setDestinationAddress(StringUtil.objToString(objects[i++]));                         //起飞地目的地
        bo.setTakeoffTime(StringUtil.objToString(objects[i++]));                                //起飞时间
        return bo;
    }

    /**
     * 根据文件Id返回excel信息
     *
     * @param proxyCustomsMqBo 代理报关MQ信息
     * @return 文件数据
     */
    public static List<Object[]> getCommandResDTO(ProxyCustomsMqBo proxyCustomsMqBo) throws Exception {
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(proxyCustomsMqBo.getDfsFileId());
        reqDTO.setOperation(Operation.QUERY);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        log.info("返回文件相关信息 ——> {}", resDTO);
        byte[] bytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String suffix = resDTO.getFileName().substring(resDTO.getFileName().lastIndexOf(".") + 1, resDTO.getFileName().length());

        String charsetName = FileCharsetDetectorUtil.getFileEncoding(new ByteArrayInputStream(bytes));
        log.info("文件字符集编码：{}", charsetName);
        if ("csv".equalsIgnoreCase(suffix)) {
            return CSVUtils.readCsvFile(inputStream, 21, charsetName);
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            return Excelutil.getDataFromExcel(inputStream, 0, 0, 21, resDTO.getFileName());
        } else if ("txt".equals(suffix.toLowerCase())) {
            return Txtutil.getDataFromTxt(inputStream, 18, resDTO.getFileName());
        }
        return Lists.newArrayList();
    }

    /**
     * api业务参数转换成跨境订单创建参数
     *
     * @param proxyCustomsBo 业务参数
     * @param orderId        订单编号
     * @return 跨境订单创建参数
     */
    public static FiCbPayOrderDo convert(ProxyCustomsBo proxyCustomsBo, Long orderId) {
        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setOrderCcy(proxyCustomsBo.getOrderCcy());
        fiCbPayOrderDo.setTransMoney(proxyCustomsBo.getPaymentMoney().divide(new BigDecimal(100)));
        fiCbPayOrderDo.setTransCcy(proxyCustomsBo.getOrderCcy());
        fiCbPayOrderDo.setPayId(0);
        fiCbPayOrderDo.setChannelId(0);
        fiCbPayOrderDo.setOrderCompleteTime(new Date());
        fiCbPayOrderDo.setTransRate(BigDecimal.ONE);
        fiCbPayOrderDo.setTransFee(BigDecimal.ZERO);
        fiCbPayOrderDo.setMemberId(proxyCustomsBo.getMemberId());
        fiCbPayOrderDo.setMemberTransId(proxyCustomsBo.getMemberTransId());
        fiCbPayOrderDo.setTerminalId(0);
        fiCbPayOrderDo.setFunctionId(proxyCustomsBo.getFunctionId());
        fiCbPayOrderDo.setProductId(proxyCustomsBo.getProductId());
        fiCbPayOrderDo.setOrderMoney(proxyCustomsBo.getOrderMoney().divide(new BigDecimal(100)));
        fiCbPayOrderDo.setTransTime(new Date());
        fiCbPayOrderDo.setOrderType(Constants.PUSH_FLAG.equals(proxyCustomsBo.getPushFlag().toUpperCase()) ?
                Constants.PROXY_GOODS_TRADE : Constants.PROXY_SERVICE_TRADE);
        fiCbPayOrderDo.setTransRate(BigDecimal.ONE);
        fiCbPayOrderDo.setTransFee(BigDecimal.ZERO);
        fiCbPayOrderDo.setPayStatus(FlagEnum.TRUE.getCode());
        return fiCbPayOrderDo;
    }

    /**
     * api代报入参转换成代理报关对象
     *
     * @param customsBo 代理报关Bo
     * @param orderId   订单号
     * @return ProxyCustomsApiBo
     */
    public static ProxyCustomsApiBo toProxyCustomsMqBo(ProxyCustomsBo customsBo, Long orderId) {

        ProxyCustomsApiBo proxyCustomsApiBo = new ProxyCustomsApiBo();
        proxyCustomsApiBo.setMemberTransId(customsBo.getMemberTransId() == null ? orderId.toString() :
                customsBo.getMemberTransId());
        proxyCustomsApiBo.setOrderCcy(customsBo.getOrderCcy());
        proxyCustomsApiBo.setOrderMoney(customsBo.getOrderMoney());
        proxyCustomsApiBo.setPaymentCcy(customsBo.getPaymentCcy());
        proxyCustomsApiBo.setPaymentMoney(customsBo.getPaymentMoney());
        proxyCustomsApiBo.setIdHolder(customsBo.getIdHolder());
        proxyCustomsApiBo.setIdCardNo(customsBo.getIdCardNo());
        proxyCustomsApiBo.setBankCardNo(customsBo.getBankCardNo());
        proxyCustomsApiBo.setMobile(customsBo.getMobile());
        proxyCustomsApiBo.setGoodsName(customsBo.getGoodsName());
        proxyCustomsApiBo.setGoodsPrice(customsBo.getGoodsPrice());
        proxyCustomsApiBo.setGoodsNum(customsBo.getGoodsNum());
        proxyCustomsApiBo.setProductId(customsBo.getProductId());
        proxyCustomsApiBo.setMemberId(customsBo.getMemberId());
        proxyCustomsApiBo.setTerminalId(customsBo.getTerminalId().intValue());
        proxyCustomsApiBo.setOrderId(orderId);
        proxyCustomsApiBo.setClientIp(customsBo.getClientIp());
        proxyCustomsApiBo.setCbPayOrderItemBos(CbpaySendOrderConvert.paramConvert(customsBo.getCbpayGoodsItemBos()));
        return proxyCustomsApiBo;
    }

    /**
     * 功能：代理报关文件内容转换成跨境订单对象
     *
     * @param obj              excel一行内容转换成
     * @param proxyCustomsMqBo 代理报关MQ信息
     * @param orderId          宝付订单号
     * @param careerType       行业类型
     * @return 跨境订单对象
     * Created by feng_jiang 2017/7/7
     */
    public static FiCbPayOrderDo trans2FiCbPayOrderDo(Object obj, ProxyCustomsMqBo proxyCustomsMqBo, Long orderId, String careerType) {
        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        String[][] fields = {{"memberTransId", ""}, {"orderCcy", ""}, {"orderMoney", ""}, {"tradeDate", ""}};
        ProxyCustomConvert.getValByMethod(obj, fields);
        fiCbPayOrderDo.setMemberTransId(fields[0][1]);                                                              //商户订单号
        fiCbPayOrderDo.setOrderCcy(fields[1][1]);                                                                   //订单币种
        fiCbPayOrderDo.setTransMoney(new BigDecimal(fields[2][1]));                                                 //交易金额
        fiCbPayOrderDo.setTransCcy(fields[1][1]);                                                                   //交易币种
        fiCbPayOrderDo.setOrderMoney(new BigDecimal(fields[2][1]));                                                 //订单金额
        if (Pattern.matches(Constants.YYYY_MM_DD_HH_MM_SS, fields[3][1])) {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], DateUtil.settlePattern));                      //交易时间，由商户传入,判断格式
        } else if (Pattern.matches(Constants.YYYYMMDDHHMMSS, fields[3][1])) {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], DateUtil.fullPattern));
        } else if (Pattern.matches(Constants.YYYY_MM_DD, fields[3][1])) {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], DateUtil.smallDateFormat));
        } else if (Pattern.matches(Constants.YYYY_MM_DD_SLANT, fields[3][1])) {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], DateUtil.smallDatePattern));
        } else if (Pattern.matches(Constants.YYYY_MM_DD_HH_MM_SS_SLANT, fields[3][1])) {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], Constants.timesPattern));
        } else {
            fiCbPayOrderDo.setTransTime(DateUtil.parse(fields[3][1], DateUtil.datePattern));
        }
        fiCbPayOrderDo.setOrderId(orderId);                                                                         //宝付订单号
        fiCbPayOrderDo.setMemberId(proxyCustomsMqBo.getMemberId());                                                 //商户编号
        fiCbPayOrderDo.setBatchFileId(proxyCustomsMqBo.getFileBatchNo());                                           //文件批次号
        fiCbPayOrderDo.setOrderCompleteTime(DateUtil.getCurrentDate());                                             //订单完成时间
        fiCbPayOrderDo.setFunctionId(Constants.CB_PAY_ORDER);                                                       //功能编号
        fiCbPayOrderDo.setProductId(Constants.CBPAY_PRODUCT_LIST.get(0));                                              //产品编号
        if (proxyCustomsMqBo.getSourceFlag() != null && proxyCustomsMqBo.getSourceFlag() == NumberConstants.THREE) {
            fiCbPayOrderDo.setOrderType(OrderType.BATCH_REMIT_ORDER.getCode());
        } else {
            fiCbPayOrderDo.setOrderType(OrderType.PROXY_SERVICE_TRADE.getCode());
        }
        fiCbPayOrderDo.setPayStatus(PayStatus.TRUE.getCode());                                                      //支付状态
        fiCbPayOrderDo.setBusinessNo(StringUtils.leftPad(String.valueOf(orderId), 32, "0"));          //业务流水号
        fiCbPayOrderDo.setTransRate(BigDecimal.ONE);                                                                //交易汇率
        fiCbPayOrderDo.setTransFee(BigDecimal.ZERO);                                                                //交易手续费
        fiCbPayOrderDo.setPayId(BigDecimal.ZERO.intValue());                                                        //支付编号
        fiCbPayOrderDo.setChannelId(BigDecimal.ZERO.intValue());                                                    //渠道编号
        fiCbPayOrderDo.setTerminalId(BigDecimal.ZERO.intValue());                                                   //终端编号
        fiCbPayOrderDo.setNotifyStatus(FlagEnum.TRUE.getCode());                                                    //
        fiCbPayOrderDo.setAckStatus(OrederAckStatusEnum.YES.getCode());                                             //确认状态
        fiCbPayOrderDo.setRemittanceStatus(RemittanceStatus.INIT.getCode());
        fiCbPayOrderDo.setCareerType(careerType);                                                                   //行业类型
        return fiCbPayOrderDo;
    }

    /**
     * 功能：根据方法名获取对应属性值
     *
     * @param obj
     * @param fields
     */
    private static void getValByMethod(Object obj, String[][] fields) {
        PropertyDescriptor pd;
        for (int k = 0; k < fields.length; k++) {
            try {
                pd = new PropertyDescriptor(fields[k][0], obj.getClass());
                Method getMethod = pd.getReadMethod();
                fields[k][1] = getMethod.invoke(obj) + "";
            } catch (Exception e) {
            }
        }
    }

    /**
     * 跨境订单转换附加信息
     *
     * @param obj     请求订单信息
     * @param orderId 宝付订单号
     * @return 网关订单附加信息
     * Created by feng_jiang 2017/7/7
     */
    public static FiCbPayOrderAdditionDo orderAdditionDoConvert(Object obj, Long orderId) {
        FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = new FiCbPayOrderAdditionDo();
        String[][] fields = {{"bankCardNo", ""}, {"idCardNo", ""}, {"idHolder", ""}, {"mobile", ""}, {"payeeIdType", ""}};
        ProxyCustomConvert.getValByMethod(obj, fields);
        fiCbPayOrderAdditionDo.setOrderId(orderId);                                                                         //宝付订单号
        fiCbPayOrderAdditionDo.setBankCardNo(SecurityUtil.desEncrypt(fields[0][1], Constants.CARD_DES_PASSWD));              //银行卡号
        fiCbPayOrderAdditionDo.setIdCardNo(SecurityUtil.desEncrypt(fields[1][1].toUpperCase(), Constants.CARD_DES_PASSWD));  //身份证号
        fiCbPayOrderAdditionDo.setIdHolder(fields[2][1]);                                                                   //持卡人姓名
        fiCbPayOrderAdditionDo.setMobile(fields[3][1]);                                                                     //银行预留手机号
        fiCbPayOrderAdditionDo.setClientIp(BigDecimal.ZERO.toString());                                                     //请求IP地址
        fiCbPayOrderAdditionDo.setPayeeIdType(Integer.parseInt(fields[4][1]));                                              //收款人证件类型：1-身份证
        return fiCbPayOrderAdditionDo;
    }

    /**
     * 商品信息转换
     *
     * @param proxyCustomsBo 商品名称字符串
     * @param orderId        订单ID
     * @return 商品信息集合
     */
    public static List<FiCbPayOrderItemDo> paramConvert(ProxyCustomsValidateBo proxyCustomsBo, Long orderId) {

        List<FiCbPayOrderItemDo> cbPayOrderItemDoList = new ArrayList<>();
        String[] names = proxyCustomsBo.getGoodsName().replace("\n", "").replace("\r\n", "").split(Constants.SPLIT_SYMBOL);
        String[] nums = proxyCustomsBo.getGoodsNum().split(Constants.SPLIT_SYMBOL);
        String[] prices = proxyCustomsBo.getGoodsPrice().split(Constants.SPLIT_SYMBOL);
        for (int i = 0; i < names.length; i++) {
            FiCbPayOrderItemDo fiCbPayOrderItemDo = new FiCbPayOrderItemDo();
            fiCbPayOrderItemDo.setOrderId(orderId);
            String amount = nums[i];
            if (amount.contains(".")) {
                amount = amount.substring(0, amount.indexOf("."));
            }
            fiCbPayOrderItemDo.setCommodityAmount(Integer.parseInt(amount));
            //处理商品名称中有特殊字符的情况
            fiCbPayOrderItemDo.setCommodityName(com.baofu.cbpayservice.common.util.StringUtils.stringFilter(names[i]));
            fiCbPayOrderItemDo.setCommodityPrice(new BigDecimal(prices[i]));
            cbPayOrderItemDoList.add(fiCbPayOrderItemDo);
        }
        return cbPayOrderItemDoList;
    }

    public static List<FiCbPayOrderStudyAbroadDo> paramConvert(CbPayOrderStudyAbroadValidateBo proxyBo, Long orderId) {
        List<FiCbPayOrderStudyAbroadDo> fiCbPayOrderStudyAbroadDoList = new ArrayList<>();
        String[] schoolCountryCodeArray = proxyBo.getSchoolCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] schoolNameArray = proxyBo.getSchoolName().split(Constants.SPLIT_SYMBOL);
        String[] studentNameArray = proxyBo.getStudentName().split(Constants.SPLIT_SYMBOL);
        String[] studentIdArray = proxyBo.getStudentId().split(Constants.SPLIT_SYMBOL);
        String[] admissionNoticeIdArray = proxyBo.getAdmissionNoticeId().split(Constants.SPLIT_SYMBOL);
        FiCbPayOrderStudyAbroadDo fiCbPayOrderStudyAbroadDo;
        for (int i = 0; i < studentIdArray.length; i++) {
            fiCbPayOrderStudyAbroadDo = new FiCbPayOrderStudyAbroadDo();
            fiCbPayOrderStudyAbroadDo.setOrderId(orderId);
            fiCbPayOrderStudyAbroadDo.setSchoolCountryCode(schoolCountryCodeArray[i]);
            fiCbPayOrderStudyAbroadDo.setSchoolName(schoolNameArray[i]);
            fiCbPayOrderStudyAbroadDo.setStudentName(studentNameArray[i]);
            fiCbPayOrderStudyAbroadDo.setStudentId(studentIdArray[i]);
            fiCbPayOrderStudyAbroadDo.setAdmissionNoticeId(admissionNoticeIdArray[i]);
            fiCbPayOrderStudyAbroadDoList.add(fiCbPayOrderStudyAbroadDo);
        }
        return fiCbPayOrderStudyAbroadDoList;
    }

    public static List<FiCbPayOrderHotelDo> paramConvert(CbPayOrderHotelValidateBo proxyBo, Long orderId) {
        List<FiCbPayOrderHotelDo> fiCbPayOrderHotelDoList = new ArrayList<>();
        String[] hotelCountryCodeArray = proxyBo.getHotelCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] hotelNameArray = proxyBo.getHotelName().split(Constants.SPLIT_SYMBOL);
        String[] checkInDateArray = proxyBo.getCheckInDate().split(Constants.SPLIT_SYMBOL);
        String[] nightCountArray = proxyBo.getNightCount().split(Constants.SPLIT_SYMBOL);
        for (int i = 0; i < hotelNameArray.length; i++) {
            FiCbPayOrderHotelDo fiCbPayOrderHotelDo = new FiCbPayOrderHotelDo();
            fiCbPayOrderHotelDo.setOrderId(orderId);
            fiCbPayOrderHotelDo.setHotelCountryCode(hotelCountryCodeArray[i]);
            fiCbPayOrderHotelDo.setHotelName(hotelNameArray[i]);
            if (Pattern.matches(RegularExpressionConstants.YYYY_MM_DD, checkInDateArray[i])) {
                fiCbPayOrderHotelDo.setCheckInDate(DateUtil.parse(checkInDateArray[i], DateUtil.smallDateFormat));
            } else {
                fiCbPayOrderHotelDo.setCheckInDate(DateUtil.parse(checkInDateArray[i], DateUtil.datePattern));
            }
            fiCbPayOrderHotelDo.setNightCount(new BigDecimal(nightCountArray[i]));
            fiCbPayOrderHotelDoList.add(fiCbPayOrderHotelDo);
        }
        return fiCbPayOrderHotelDoList;
    }

    public static List<FiCbPayOrderTourismDo> paramConvert(CbPayOrderTourismValidateBo proxyBo, Long orderId) {
        List<FiCbPayOrderTourismDo> fiCbPayOrderTourismDoList = new ArrayList<>();
        String[] travelAgencyNameArray = proxyBo.getTravelAgencyName().split(Constants.SPLIT_SYMBOL);
        String[] travelCountryCodeArray = proxyBo.getTravelCountryCode().split(Constants.SPLIT_SYMBOL);
        String[] tripDateArray = proxyBo.getTripDate().split(Constants.SPLIT_SYMBOL);
        String[] tourDaysArray = proxyBo.getTourDays().split(Constants.SPLIT_SYMBOL);
        for (int i = 0; i < travelCountryCodeArray.length; i++) {
            FiCbPayOrderTourismDo fiCbPayOrderTourismDo = new FiCbPayOrderTourismDo();
            fiCbPayOrderTourismDo.setOrderId(orderId);
            fiCbPayOrderTourismDo.setTravelAgencyName(travelAgencyNameArray[i]);
            fiCbPayOrderTourismDo.setTravelCountryCode(travelCountryCodeArray[i]);
            if (Pattern.matches(RegularExpressionConstants.YYYY_MM_DD, tripDateArray[i])) {
                fiCbPayOrderTourismDo.setTripDate(DateUtil.parse(tripDateArray[i], DateUtil.smallDateFormat));
            } else {
                fiCbPayOrderTourismDo.setTripDate(DateUtil.parse(tripDateArray[i], DateUtil.datePattern));
            }
            fiCbPayOrderTourismDo.setTourDays(new BigDecimal(tourDaysArray[i]));
            fiCbPayOrderTourismDoList.add(fiCbPayOrderTourismDo);
        }
        return fiCbPayOrderTourismDoList;
    }

    /**
     * 航旅信息转换
     *
     * @param airRemitValidateBo 航旅信息
     * @param orderId            订单ID
     * @return 航旅信息结合
     */
    public static List<FiCbPayOrderTicketsDo> paramConvert(AirRemitValidateBo airRemitValidateBo, Long orderId) {

        List<FiCbPayOrderTicketsDo> fiCbPayOrderTicketsDoList = new ArrayList<>();
        String[] flightNumbers = airRemitValidateBo.getFlightNumber().replace("\n", "").replace("\r\n", "").split(Constants.SPLIT_SYMBOL);
        String[] destinationAddresses = airRemitValidateBo.getDestinationAddress().replace("\n", "").replace("\r\n", "").split(Constants.SPLIT_SYMBOL);
        String[] takeoffTimes = airRemitValidateBo.getTakeoffTime().split(Constants.SPLIT_SYMBOL);
        for (int i = 0; i < destinationAddresses.length; i++) {
            FiCbPayOrderTicketsDo fiCbPayOrderTicketsDo = new FiCbPayOrderTicketsDo();
            fiCbPayOrderTicketsDo.setOrderId(orderId);
            fiCbPayOrderTicketsDo.setFlightNumber(flightNumbers[i]);
            fiCbPayOrderTicketsDo.setAddress(destinationAddresses[i]);
            if (Pattern.matches(RegularExpressionConstants.YYYYY_MM_DD_HH_MM, takeoffTimes[i])) {
                fiCbPayOrderTicketsDo.setTakeoffTime(DateUtil.parse(String.valueOf(takeoffTimes[i]), DateUtil.ymdhm));
            } else {
                fiCbPayOrderTicketsDo.setTakeoffTime(DateUtil.parse(String.valueOf(takeoffTimes[i]), DateUtil.year_of_minute));
            }
            fiCbPayOrderTicketsDoList.add(fiCbPayOrderTicketsDo);
        }
        return fiCbPayOrderTicketsDoList;
    }

    /**
     * 跨境订单创建
     *
     * @param proxyCustomsMqBo mq内容
     * @return 跨境订单
     */
    public static CbPayOrderReqBo cbPayOrderReqBo(ProxyCustomsApiBo proxyCustomsMqBo) {

        CbPayOrderReqBo cbPayOrderReqBo = new CbPayOrderReqBo();
        cbPayOrderReqBo.setOrderCcy(Currency.CNY.getCode());
        cbPayOrderReqBo.setOrderId(proxyCustomsMqBo.getOrderId());
        cbPayOrderReqBo.setPayId(BigDecimal.ZERO.intValue());
        cbPayOrderReqBo.setChannelId(BigDecimal.ZERO.intValue());
        cbPayOrderReqBo.setMemberId(proxyCustomsMqBo.getMemberId());
        cbPayOrderReqBo.setMemberTransId(proxyCustomsMqBo.getMemberTransId());
        cbPayOrderReqBo.setTerminalId(proxyCustomsMqBo.getTerminalId());
        cbPayOrderReqBo.setProductId(proxyCustomsMqBo.getProductId());
        cbPayOrderReqBo.setOrderMoney(proxyCustomsMqBo.getOrderMoney());
        cbPayOrderReqBo.setIdHolder(proxyCustomsMqBo.getIdHolder());
        cbPayOrderReqBo.setMobile(proxyCustomsMqBo.getMobile());
        cbPayOrderReqBo.setFunctionId(Constants.CB_PAY_ORDER);
        cbPayOrderReqBo.setTransMoney(proxyCustomsMqBo.getPaymentMoney());
        cbPayOrderReqBo.setTransTime(DateUtil.getCurrentDate());
        cbPayOrderReqBo.setOrderType(Constants.API_PROXY_CUSTOMS);
        cbPayOrderReqBo.setTransCcy(Currency.CNY.getCode());
        cbPayOrderReqBo.setTransRate(BigDecimal.ONE);
        cbPayOrderReqBo.setCbPayOrderItemBos(proxyCustomsMqBo.getCbPayOrderItemBos());
        cbPayOrderReqBo.setClientIp(proxyCustomsMqBo.getClientIp());
        cbPayOrderReqBo.setPayStatus(FlagEnum.TRUE.getCode());
        cbPayOrderReqBo.setNotifyType(FlagEnum.TRUE.getCode());
        cbPayOrderReqBo.setTransFee(BigDecimal.ZERO);
        cbPayOrderReqBo.setBankCardNo(proxyCustomsMqBo.getBankCardNo());
        cbPayOrderReqBo.setIdCardNo(proxyCustomsMqBo.getIdCardNo().toUpperCase());
        cbPayOrderReqBo.setOrderCompleteTime(DateUtil.getCurrentDate());
        cbPayOrderReqBo.setBatchNo(BigDecimal.ZERO.longValue());
        cbPayOrderReqBo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        cbPayOrderReqBo.setPayeeIdType(BigDecimal.ONE.intValue());
        return cbPayOrderReqBo;
    }

    /**
     * 代理报关批次文件记录
     *
     * @param fiCbpayFileUploadBo 关批次文件信息
     * @return 关批次文件信息
     */
    public static FiCbPayFileUploadDo toFiCbPayFileUploadDo(FiCbpayFileUploadBo fiCbpayFileUploadBo) {

        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileName(fiCbpayFileUploadBo.getFileName());
        fiCbpayFileUploadDo.setRecordCount(fiCbpayFileUploadBo.getRecordCount());
        fiCbpayFileUploadDo.setSuccessCount(fiCbpayFileUploadBo.getSuccessCount());
        fiCbpayFileUploadDo.setFailCount(fiCbpayFileUploadBo.getFailCount());
        fiCbpayFileUploadDo.setStatus(fiCbpayFileUploadBo.getStatus());
        fiCbpayFileUploadDo.setDfsFileId(fiCbpayFileUploadBo.getDfsFileId());
        fiCbpayFileUploadDo.setFileType(fiCbpayFileUploadBo.getFileType());
        fiCbpayFileUploadDo.setMemberId(fiCbpayFileUploadBo.getMemberId());
        fiCbpayFileUploadDo.setCreateBy(fiCbpayFileUploadBo.getCreateBy());
        fiCbpayFileUploadDo.setOrderType(fiCbpayFileUploadBo.getOrderType());
        fiCbpayFileUploadDo.setAuditStatus(fiCbpayFileUploadBo.getAuditStatus());
        fiCbpayFileUploadDo.setFileBatchNo(fiCbpayFileUploadBo.getFileBatchNo());
        fiCbpayFileUploadDo.setCareerType(fiCbpayFileUploadBo.getCareerType());
        fiCbpayFileUploadDo.setTotalAmount(BigDecimal.ZERO);
        return fiCbpayFileUploadDo;
    }

    /**
     * 金额验证
     *
     * @param str 校验参数
     * @return 校验是否成功
     */
    public static boolean isNumber(String str) {
        // 判断小数点后2位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 金额验证
     *
     * @param str 校验参数
     * @return 校验是否成功
     */
    public static boolean isTrreeNumber(String str) {
        // 判断小数点后2位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,3})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 文件批次状态批量更新
     *
     * @param cbPayBatchFileUpLoadBo 文件批次信息
     * @return 文件批次对象
     */
    public static FiCbPayBatchFileUploadDo batchUpdateStatusConvert(CbPayBatchFileUpLoadBo cbPayBatchFileUpLoadBo) {
        FiCbPayBatchFileUploadDo fiCbPayBatchFileUploadDo = new FiCbPayBatchFileUploadDo();
        fiCbPayBatchFileUploadDo.setUpdateBy(cbPayBatchFileUpLoadBo.getUpdateBy());
        fiCbPayBatchFileUploadDo.setStatus(cbPayBatchFileUpLoadBo.getStatus());
        fiCbPayBatchFileUploadDo.setBatchFileIdList(cbPayBatchFileUpLoadBo.getBatchFileIdList());
        fiCbPayBatchFileUploadDo.setMemberId(cbPayBatchFileUpLoadBo.getMemberId());
        fiCbPayBatchFileUploadDo.setAmlCcy(cbPayBatchFileUpLoadBo.getAmlCcy());
        return fiCbPayBatchFileUploadDo;
    }

    /**
     * excel第二行基本内容参数转换
     *
     * @param objects 第二行内容
     * @return 校验参数对象
     */
    public static ExcelBaseValidationBo toExcelBaseValidationBo(Object[] objects) {

        ExcelBaseValidationBo excelBaseValidationBo = new ExcelBaseValidationBo();
        excelBaseValidationBo.setVersion(objects[0] == null ? "" : objects[0].toString());
        excelBaseValidationBo.setCareerType(objects[1] == null ? "" : objects[1].toString());
        excelBaseValidationBo.setMemberId(objects[2] == null ? "" : objects[2].toString());
        return excelBaseValidationBo;
    }

    /**
     * 转换跨境订单运单信息
     *
     * @param proxyCustomsBo excel一行内容转换成
     * @param orderId        跨境订单好
     * @return 跨境订单运单信息
     */
    public static FiCbpayOrderLogisticsDo toFiCbpayOrderLogisticsDo(ProxyCustomsValidateBo proxyCustomsBo, Long orderId) {

        FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = null;
        OrderLogisticsValidationBo orderLogisticsValidationBo = proxyCustomsBo.getOrderLogisticsValidationBo();
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

    public static FiCbPayFileUploadDo uploadParamConvert(Long fileBatchNo, String status, Integer successCount,
                                                         String orderType, BigDecimal totalAmount) {
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadDo.setStatus(status);
        fiCbpayFileUploadDo.setSuccessCount(successCount);
        fiCbpayFileUploadDo.setRecordCount(successCount);
        fiCbpayFileUploadDo.setOrderType(orderType);
        fiCbpayFileUploadDo.setTotalAmount(totalAmount);
        fiCbpayFileUploadDo.setCareerType(CareerTypeEnum.GOODS_TRADE.getCode());
        return fiCbpayFileUploadDo;
    }

    public static FiCbPayFileUploadDo uploadFileUpdateParamConvert(Long fileBatchNo, String status, BigDecimal amlAmount,
                                                                   BigDecimal amlRate, String updateBy) {
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadDo.setStatus(status);
        fiCbpayFileUploadDo.setAmlAmount(amlAmount);
        fiCbpayFileUploadDo.setUpdateBy(updateBy);
        fiCbpayFileUploadDo.setAmlRate(amlRate);
        return fiCbpayFileUploadDo;
    }
}
