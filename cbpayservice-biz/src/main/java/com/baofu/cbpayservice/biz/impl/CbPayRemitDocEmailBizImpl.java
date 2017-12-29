package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.dfs.client.util.DateUtil;
import com.baofu.cbpayservice.biz.CbPayRemitDocEmailBiz;
import com.baofu.cbpayservice.biz.EmailSendService;
import com.baofu.cbpayservice.biz.integration.ma.MemberEmailQueryBizImpl;
import com.baofu.cbpayservice.biz.models.MemberEmailBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.CcyEnum;
import com.baofu.cbpayservice.common.enums.CountryEnum;
import com.baofu.cbpayservice.common.enums.Currency;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.EmailUtils;
import com.baofu.cbpayservice.common.util.NumberToCN;
import com.baofu.cbpayservice.common.util.PdfUtils;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送汇款凭证实现类
 * <p>
 * Created by 莫小阳 on 2017/7/17.
 */
@Slf4j
@Service
public class CbPayRemitDocEmailBizImpl implements CbPayRemitDocEmailBiz {

    /**
     * 邮件服务
     */
    @Autowired
    private EmailSendService emailSendService;

    /**
     * 查询商户业务邮箱邮箱
     */
    @Autowired
    private MemberEmailQueryBizImpl memberEmailQueryBiz;

    /**
     * 商户缓存信息
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 跨境人民币汇款订单信息Manager
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 多币种账户信息数据服务Manager
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 凭证路径
     */
    @Value(("${remit_doc_path}"))
    private String remitDocPath;

    /**
     * 凭证密送人
     */
    @Value("${email.address.bcc}")
    private String emailAddressBCC;

    /**
     * 发送汇款凭证
     *
     * @param batchNo 汇款批次号
     */
    @Override
    public void sendRemitDocEmail(String batchNo) {

        log.info("call 发送汇款凭证邮件开始，汇款批次号：{}···", batchNo);
        try {
            //查询汇款信息
            FiCbPayRemittanceDo remitOrder = cbPayRemittanceManager.queryRemittanceOrder(batchNo);
            if (remitOrder == null) {
                log.info("batchNo:{},没有该笔汇款订单", batchNo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
            }
            log.info("汇款订单信息：{}", remitOrder);

            //附加信息
            FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(batchNo,
                    remitOrder.getMemberNo());

            if (fiCbPayRemittanceAdditionDo == null) {
                log.info("batchNo:{},没有该笔汇款订单附加信息", batchNo);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
            }
            log.info("汇款订单附加信息：{}", fiCbPayRemittanceAdditionDo);

            //查询币种账户信息
            FiCbPaySettleBankDo settleBank = cbPaySettleBankManager.selectBankAccByEntityNo(remitOrder.getMemberNo(), remitOrder.getSystemCcy(),
                    fiCbPayRemittanceAdditionDo.getEntityNo());
            if (settleBank == null) {
                log.info("商户号：{} ,没有查询到结算账户信息", remitOrder.getMemberNo());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
            }
            log.info("call  币种账户信息：{}", settleBank);

            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(remitOrder.getMemberNo());
            List<String> sendEmailTo = Lists.newArrayList(memberEmailBo.getBusinessEmail());
            //财务联系人
            if (StringUtils.isNotBlank(memberEmailBo.getFinanceEmail())) {
                sendEmailTo.add(memberEmailBo.getFinanceEmail());
            }
            List<String> emailBCCList = EmailUtils.emailAddressConvert(emailAddressBCC, ",");

            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(remitOrder.getMemberNo().intValue());
            if (cacheMemberDto == null) {
                log.error("call 查询商户缓存为 null");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00159);
            }

            if (cacheMemberDto.getName() == null || remitOrder.getRemitSuccDate() == null || remitOrder.getRemitMoney() == null
                    || remitOrder.getBatchNo() == null || remitOrder.getSystemCcy() == null) {
                log.error("memberName={}", cacheMemberDto.getName());
                log.error("date={}", DateUtil.format(remitOrder.getRemitSuccDate(), Constants.DATE_FORMAT_FULL));
                log.error("settlementCcy={}", remitOrder.getRemitMoney());
                log.error("batchNo={}", remitOrder.getBatchNo());
                log.error("settlementCcy={}", remitOrder.getSystemCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00160);
            }

            String content = Constants.REMIT_DOC_EMAIL_CONTENT.replace("${memberName}", cacheMemberDto.getName()).replace("${date}",
                    DateUtil.format(remitOrder.getRemitSuccDate(), Constants.DATE_FORMAT_FULL)).replace("${batchNo}",
                    remitOrder.getBatchNo()).replace("${transMoney}", remitOrder.getRemitMoney().toString()).replace("${settlementCcy}",
                    dealCcy(remitOrder.getSystemCcy()));
            log.info("call 发送付汇凭证邮件内容：{}", content);
            // 模板路径
            String templatePath = remitDocPath + "template.pdf";
            // 生成的新文件路径
            String newPDFPath = remitDocPath + remitOrder.getMemberNo() + "—宝付汇款汇出凭证" + ".pdf";
            Map<String, String> result = setData(remitOrder, settleBank);
            //生成PDF文件
            PdfUtils.editPDF(templatePath, result, newPDFPath);
            File file = new File(newPDFPath);
            emailSendService.sendMsg(content, sendEmailTo,
                    null, emailBCCList, file, "宝付付汇业务汇款凭证", null,
                    null, null);
            //发送完邮件将汇款凭证删除
            if (file.exists()) {
                file.delete();
            }
            log.info("发送汇款凭证邮件结束···");
        } catch (Exception e) {
            log.error(" 发送汇款凭证邮件失败", e);
        }
    }

    /**
     * 给PDF文本域设置值
     *
     * @param remitOrder 汇款订单信息
     * @param settleBank 结算账户信息
     * @return 处理结果
     */
    private Map<String, String> setData(FiCbPayRemittanceDo remitOrder, FiCbPaySettleBankDo settleBank) {
        HashMap<String, String> result = Maps.newHashMap();
        result.put("merchantNo", String.valueOf(remitOrder.getMemberNo()));
        result.put("remitBatchNo", remitOrder.getBatchNo());
        result.put("remitDate", DateUtil.format(remitOrder.getRemitSuccDate(), DateUtil.settlePattern));
        result.put("payeeName", settleBank.getBankAccName());
        result.put("payerName", "宝付网络科技（上海）有限公司");
        result.put("payeeBankName", settleBank.getBankName());
        result.put("payerCountry", "中华人民共和国-CHN");
        result.put("payeeCountry", StringUtil.isBlank(settleBank.getCountryCode()) ? "" : getCountry(settleBank.getCountryCode()));
        result.put("rate", dealRate(remitOrder.getSystemRate(), remitOrder.getSystemCcy()));
        result.put("merchantFee", String.valueOf(remitOrder.getTransFee()));
        result.put("remitAmt", String.valueOf(remitOrder.getRemitMoney()));
        result.put("remitAmtCHN", NumberToCN.number2CNMontrayUnit(remitOrder.getRemitMoney()));
        result.put("createDate", DateUtil.format(new Date(), DateUtil.settlePattern));
        result.put("remitCcy", dealCcy(remitOrder.getSystemCcy()));
        return result;
    }

    /**
     * 将币种转成中文信息
     *
     * @param systemCcy 币种
     * @return 结果
     */
    private String dealCcy(String systemCcy) {

        log.info("call 币种编号：{}", systemCcy);
        if (StringUtil.isBlank(systemCcy)) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00157);
        }
        String ccyCHN = CcyEnum.getCcyCHN(systemCcy);
        if (StringUtil.isBlank(ccyCHN)) {
            log.error("call 币种枚举类中缺少币种为{}的支持！", systemCcy);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00157);
        }
        return ccyCHN;
    }

    /**
     * 汇率处理
     *
     * @param realRate 牌价
     * @param ccy      币种
     * @return 汇率
     */
    private String dealRate(BigDecimal realRate, String ccy) {
        log.info("call 格式化汇率信息   汇率：{}  币种：{}", realRate, ccy);
        //参数校验
        if (realRate == null || StringUtil.isBlank(ccy)) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00154);
        }
        if (Currency.CNY.getCode().equals(ccy)) {
            return "1.000000";
        }
        //除以100，保留6位小数，截断
        BigDecimal bigDecimal1 = realRate.divide(new BigDecimal(100)).setScale(6, BigDecimal.ROUND_DOWN);
        return String.valueOf(bigDecimal1);
    }

    /**
     * 根据国别编号查询国家中文名称
     *
     * @param countryCode 国别编号
     * @return 结果
     */
    private String getCountry(String countryCode) {
        log.info("call 国别编号：{}", countryCode);
        String countryCHN = CountryEnum.getCountryCHN(countryCode);
        if (StringUtil.isBlank(countryCHN)) {
            log.error("call 国家枚举类缺少对国家编号为{}的支持！", countryCode);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00156);
        }
        return countryCHN + "-" + countryCode;
    }
}
