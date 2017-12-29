package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofu.cbpayservice.biz.CustomsSendEmailBiz;
import com.baofu.cbpayservice.biz.EmailSendService;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.DateFormatUtil;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.VerifyCountResultDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.FiCbPayVerifyManager;
import com.google.common.base.Splitter;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 发送海关实名认证统计邮件
 * <p>
 * Created by 莫小阳 on 2017/8/9.
 */
@Slf4j
@Service
public class CustomsSendEmailBizImpl implements CustomsSendEmailBiz {

    /**
     * 邮件服务
     */
    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private FiCbPayVerifyManager fiCbPayVerifyManager;

    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    @Value("${customs.email.receive}")
    private String customsEmailTo;

    @Value("${customs.email.cc}")
    private String customsEmailCc;

    /**
     * 发送海关实名认证统计邮件
     */
    @Override
    public void sendVerifyEmail() {
        log.info("call 开始发送海关实名认证统计邮件···");
        try {
            List<String> mailAddressTO = Splitter.on(",").trimResults().splitToList(customsEmailTo);
            List<String> mailAddressCC = Splitter.on(",").trimResults().splitToList(customsEmailCc);
            //统计海关实名认证   17:00/每天;统计数据时间间隔为D-1日17：00 到D日17：00
            String beginTime = DateFormatUtil.getNextDayWithPattern(new Date(), DateUtil.smallDatePattern).concat(Constants.TIME_CUSTOMS);
            String endTime = new SimpleDateFormat(DateUtil.smallDatePattern).format(new Date()).concat(Constants.TIME_CUSTOMS);
            List<VerifyCountResultDo> result = fiCbPayVerifyManager.statisticVerifyResult(beginTime, endTime);
            String countData = getContent(result);
            String mailContent = Constants.CONTENT.replace("${data}", countData).replace("${beginTime}", beginTime).replace("${endTime}", endTime);
            String subject = "海关支付单推送实名认证汇总结果".concat("（" + DateFormatUtil.getNextDay(new Date()) + "）");
            emailSendService.sendEmailHtml(mailContent, mailAddressTO, mailAddressCC, null, null, subject, null,
                    null, null, "跨境业务邮箱");
            log.info("call 发送海关实名认证统计邮件结束···");
        } catch (Exception e) {
            log.error("call 开始发送海关实名认证统计邮件出现异常：{}", e);
        }
    }

    /**
     * 处理发送文件内容
     *
     * @param result 统计结果
     * @return 邮件内容
     */
    private String getContent(List<VerifyCountResultDo> result) {

        log.info("call 开始组装邮件表格：{}", result);
        StringBuffer sb = new StringBuffer("");
        if (CollectionUtils.isEmpty(result)) {
            log.info("call 没有报关数据！");
            sb.append(Constants.CUSTOM_VERIFY_EMAIL_TEMPLATE_WITHOUT_DATA);
            return sb.toString();
        }
        for (VerifyCountResultDo verifyCountResultDo : result) {
            //计算成功比率
            double v = verifyCountResultDo.getSuc() * 1.00 / verifyCountResultDo.getTotalCount();
            BigDecimal bigDecimal = new BigDecimal(v).setScale(4, BigDecimal.ROUND_HALF_UP).movePointRight(2);
            //获取商户名
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(verifyCountResultDo.getMemberId().intValue());
            if (StringUtil.isBlank(cacheMemberDto.getName())) {
                log.info("call  获取商户名称为空~");
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00159);
            }
            sb = sb.append(Constants.CUSTOM_VERIFY_EMAIL_TEMPLATE.replace("${memberId}",
                    String.valueOf(verifyCountResultDo.getMemberId())).replace("${memberName}",
                    cacheMemberDto.getName()).replace("${totalCount}",
                    String.valueOf(verifyCountResultDo.getTotalCount())).replace("${suc}",
                    String.valueOf(verifyCountResultDo.getSuc())).replace("${fail}",
                    String.valueOf(verifyCountResultDo.getFail())).replace("${sucRate}", String.valueOf(bigDecimal)));
        }
        return sb.toString();
    }
}
