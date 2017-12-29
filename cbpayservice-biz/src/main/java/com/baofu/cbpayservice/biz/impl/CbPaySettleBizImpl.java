package com.baofu.cbpayservice.biz.impl;

import com.alibaba.fastjson.JSON;
import com.baofoo.cache.service.facade.model.CacheCBMemberBankDto;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.cbcgw.facade.api.gw.CgwCbPayReqFacade;
import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.request.*;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRelieveResultDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.provision.vo.RSIS150;
import com.baofu.accountcenter.service.facade.dto.req.RechargeReqDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.convert.*;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.integration.ma.MemberEmailQueryBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleApplyMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPaySettleOrderMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayOrderLogisticsMapper;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.JsonUtil;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 结汇操作业务逻辑实现接口实现
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleBizImpl implements CbPaySettleBiz {

    /**
     * 跨境结汇订单信息Manager
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 文件批次操作服务
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    /**
     * 结算订单信息服务
     */
    @Autowired
    private FiCbPaySettleOrderMapper fiCbPaySettleOrderMapper;

    /**
     * 跨境订单商品信息服务
     */
    @Autowired
    private OrderItemManager orderItemManager;

    /**
     * 商户汇入申请信息服务
     */
    @Autowired
    private FiCbPaySettleApplyMapper fiCbPaySettleApplyMapper;

    /**
     * 文件校验服务
     */
    @Autowired
    private SettleFileCheckBiz settleFileCheckBiz;

    /**
     * 缓存信息Manager
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;

    /**
     * 商户币种账户服务
     */
    @Autowired
    private FiCbPaySettleManager fiCbPaySettleManager;

    /**
     * 渠道服务
     */
    @Autowired
    private CgwCbPayReqFacade cgwCbPayReqFacade;

    /**
     * 发送邮件用户
     */
    @Value("${mail.smtp.sendUserName}")
    private String sendUserName;

    /**
     * 浮动汇率biz接口
     */
    @Autowired
    private CbPayMemberRateBiz cbPayMemberRateBiz;

    /**
     * 发送邮件用户密码
     */
    @Value("${mail.smtp.sendUserPassword}")
    private String sendUserPassword;

    /**
     * 发送邮件用户
     */
    @Value("${mail.smtp.emailHost}")
    private String emailHost;

    /**
     * 收件人
     */
    @Value("${mail.smtp.mailAddressTO}")
    private String mailAddressTO;

    /**
     * 抄送人
     */
    @Value("${mail.smtp.mailAddressCC}")
    private String mailAddressCC;

    /**
     * 结汇明细下载文件
     */
    @Value("${settleOrder.down.filePath}")
    private String settleDownLoadPath;

    /**
     * 清算服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 清算服务
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 跨境订单运单服务
     */
    @Autowired
    private FiCbpayOrderLogisticsMapper fiCbpayOrderLogisticsMapper;

    /**
     * 结汇发送邮件通知
     */
    @Autowired
    private SettleEmailBiz settleEmailBiz;

    /**
     * 终端服务
     */
    @Autowired
    private CbPaySettleAmlBiz cbPaySettleAmlBiz;

    /**
     * redis 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 结汇垫资服务
     */
    @Autowired
    private CbPaySettlePrepaymentBiz cbPaySettlePrepaymentBiz;

    /**
     * 结汇垫资数据服务
     */
    @Autowired
    private FiCbPaySettlePrepaymentManager fiCbPaySettlePrepaymentManager;


    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 上传 FTP 服务
     */
    @Autowired
    private SettleNotifyMemberBiz settleNotifyMemberBiz;

    /**
     * 汇入申请相关操作Manager服务
     */
    @Autowired
    private SettleApplyManager settleApplyManager;

    /**
     * redis缓存服务
     */
    @Autowired
    private RedisBiz redisBiz;

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
     * 发送邮件服务
     */
    @Autowired
    private EmailSendServiceImpl emailSendServiceImpl;

    /**
     * 商户汇入相关查询服务
     */
    @Autowired
    private SettleQueryBiz settleQueryBiz;

    /**
     * 商户邮箱查询服务
     */
    @Autowired
    private MemberEmailQueryBizImpl memberEmailQueryBiz;

    /**
     * 收到银行外汇汇款到账通知处理
     * <p>
     * 1、判断参数合法性
     * 2、商户外币汇入编号 + 渠道编号 确定是否存在
     * 3、创建结汇订单
     * 4、发送邮件
     *
     * @param sMTAListenerBo 商户汇款到宝付备付金账户通知对象
     */
    @Override
    public void settleMoneyToAccount(SettleMoneyToAccountListenerBo sMTAListenerBo) {

        String errorMsg = ParamValidate.validateParams(sMTAListenerBo, Constants.SPLIT_MARK);
        if (StringUtils.isNotBlank(errorMsg)) {
            log.error("银行外汇汇款到账通知参数不正确,错误内容:{}", errorMsg);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0003);
        }
        //查询结汇通知信息
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByInNoAndChannelId(sMTAListenerBo.getChannelId(),
                sMTAListenerBo.getIncomeNo());

        if (fiCbPaySettleDo != null) {
            log.error("结汇订单号:{}，渠道号:{},商户外币汇入编号:{},存在。", fiCbPaySettleDo.getOrderId(),
                    fiCbPaySettleDo.getChannelId(), fiCbPaySettleDo.getIncomeNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00104);
        }

        cbPaySettleManager.addSettle(CbPaySettleConvert.toFiCbPaySettleDo(sMTAListenerBo, orderIdManager.orderIdCreate()));

        //收到银行发送到账通知后发送邮件给清算人员和结汇相关人员提示可以进行人工匹配
        settleEmailBiz.exchangeEarningsNotify(sMTAListenerBo.getIncomeNo(), sMTAListenerBo.getIncomeAmt(),
                sMTAListenerBo.getIncomeCcy());
    }

    /**
     * 查询商户收汇申请信息
     *
     * @param settleFileUploadReqBo 查询申请信息条件
     * @return 返回收汇商户申请信息
     */
    private FiCbPaySettleApplyDo querySettleApply(SettleFileUploadReqBo settleFileUploadReqBo) {
        //获取汇入汇款申请
        FiCbPaySettleApplyDo applyDo;
        if (StringUtils.isNotBlank(settleFileUploadReqBo.getIncomeNo())) {
            applyDo = fiCbPaySettleApplyMapper.queryByIncomeNo(settleFileUploadReqBo.getMemberId(), settleFileUploadReqBo.getIncomeNo());
        } else if (settleFileUploadReqBo.getSettleOrderId() != null) {
            applyDo = fiCbPaySettleApplyMapper.queryBySettleId(settleFileUploadReqBo.getSettleOrderId());
        } else {
            log.error("查询商户申请收汇信息异常，查询条件为空，请求参数信息为：{}", settleFileUploadReqBo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00160);
        }
        //判断汇入申请是否存在
        if (applyDo == null) {
            log.error("查询收汇申请信息异常，查询条件信息：{}", settleFileUploadReqBo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        return applyDo;
    }

    /**
     * 查询银行收汇信息
     *
     * @param orderId 银行收汇记录编号
     * @return 返回收汇信息
     */
    private FiCbPaySettleDo querySettle(Long orderId) {
        FiCbPaySettleDo fiCbPaySettle = cbPaySettleManager.queryByOrderId(orderId);
        if (fiCbPaySettle == null) {
            log.error("查询银行收汇通知信息为空，匹配订单编号：{}", orderId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        }
        if (fiCbPaySettle.getFileStatus() == SettleFileStatusEnum.UPLOAD_SUCCESS.getCode()
                || fiCbPaySettle.getFileStatus() == SettleFileStatusEnum.UPLOAD_PROCESS.getCode()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00162);
        }
        return fiCbPaySettle;
    }


    /**
     * 结汇上传文件服务
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadReqBo 请求参数
     * @return 批次ID
     */
    @Override
    public Long settleFileUpload(SettleFileUploadReqBo settleFileUploadReqBo) {

        //查询商户收汇申请信息
        FiCbPaySettleApplyDo applyDo = querySettleApply(settleFileUploadReqBo);
        //查询银行收汇信息并判断状态信息
        FiCbPaySettleDo fiCbPaySettle = querySettle(applyDo.getMatchingOrderId());
        Long fileBatchNo = orderIdManager.orderIdCreate();
        settleFileUploadReqBo.setFileBatchNo(fileBatchNo);
        settleFileUploadReqBo.setSettleOrderId(fiCbPaySettle.getOrderId());
        settleFileUploadReqBo.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        FiCbPayFileUploadDo fileUploadDo = CbPaySettleConvert.toFiCbpayFileUploadDo(settleFileUploadReqBo, fileBatchNo);
        fileUploadDo.setBatchNo(applyDo.getIncomeNo());
        fiCbpayFileUploadMapper.insert(fileUploadDo);

        //文件状态为处理中
        FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
        fiCbPaySettleDo.setOrderId(fiCbPaySettle.getOrderId());
        fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.UPLOAD_PROCESS.getCode());
        cbPaySettleManager.modify(fiCbPaySettleDo);

        //发送Mq消息
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SETTLE_FILE_PROCESS_QUEUE_NAME, settleFileUploadReqBo);
        log.info("call excel代理跨境结算异步处理数据，生产者，消息队列:{},内容为:{}",
                MqSendQueueNameEnum.CBPAY_SETTLE_FILE_PROCESS_QUEUE_NAME, settleFileUploadReqBo);

        return fileBatchNo;
    }

    /**
     * 结汇上传文件处理服务
     *
     * @param settleFileUploadReqBo 请求参数
     */
    @Override
    public void settleFileProcess(SettleFileUploadReqBo settleFileUploadReqBo) throws Exception {

        long startTime = System.currentTimeMillis();
        log.info("call 结汇上传文件处理服务参数:{} ", settleFileUploadReqBo);
        //获取excel文件流
        CommandResDTO resDTO = CbPaySettleConvert.getCommandResDTO(settleFileUploadReqBo);
        List<Object[]> list = CbPaySettleConvert.getContext(resDTO, settleDownLoadPath + File.separator
                + settleFileUploadReqBo.getFileBatchNo() + "_" + resDTO.getFileName());

        //更新总条数和订单类型
        int recordCount = 0;
        if (list.size() > Constants.SETTLE_NO_CONTENT_LINE) {
            recordCount = list.size() - Constants.SETTLE_NO_CONTENT_LINE;
        }
        //文件内容校验
        SettleOrderListBo settleOrderListBo = settleFileCheckBiz.baseCheck(list, settleFileUploadReqBo, false);
        log.info("总金额:{}", settleOrderListBo.getTotalAmount());

        //更新文件信息
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
        fiCbpayFileUploadDo.setRecordCount(recordCount);
        fiCbpayFileUploadDo.setTotalAmount(settleOrderListBo.getTotalAmount());
        fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbpayFileUploadDo);
        log.info("call 结汇上传文件处理服务 校验和解析处理时间:{}", System.currentTimeMillis() - startTime);

        StringBuilder errorBuffer = settleOrderListBo.getErrorMsg();

        //结汇
        if (errorBuffer.length() == 0) {
            //更新收汇信息表  根据 orderId更新memberId  到账通知表
            FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
            fiCbPaySettleDo.setMemberId(settleFileUploadReqBo.getMemberId());
            fiCbPaySettleDo.setOrderId(settleFileUploadReqBo.getSettleOrderId());
            log.info("call 匹配成功,数据落地后 更新收汇通知表参数：{} ", fiCbPaySettleDo);
            cbPaySettleManager.modify(fiCbPaySettleDo);
            //跟新申请表  MatchingOrderId  根据申请ID
            FiCbPaySettleApplyDo fiCbPaySettleApplyDo = new FiCbPaySettleApplyDo();
            fiCbPaySettleApplyDo.setOrderId(settleFileUploadReqBo.getApplyId());
            fiCbPaySettleApplyDo.setMatchingOrderId(settleFileUploadReqBo.getSettleOrderId());
            log.info("call 匹配成功,数据落地后 更新结汇申请表参数：{} ", fiCbPaySettleApplyDo);
            fiCbPaySettleApplyMapper.updateByKeySelective(fiCbPaySettleApplyDo);

            ParamValidate.checkUpdate(fiCbPaySettleApplyMapper.updateByKeyMatchingOrderId(
                    settleFileUploadReqBo.getApplyId(), MatchingStatusEnum.YES_MATCH.getCode()), "汇入申请单状态不正确");

            //发起自动结汇垫资
            cbPaySettlePrepaymentBiz.autoSettlePrepay(settleFileUploadReqBo.getMemberId(), settleFileUploadReqBo.getIncomeNo());

            //通知商户匹配状态
            sendSettleNotify(settleFileUploadReqBo.getMemberId(), settleFileUploadReqBo.getIncomeNo(),
                    settleFileUploadReqBo.getSettleOrderId(), SearchTypeEnum.MATCH_RESULT.getCode());

            //校验成功写数据
            batchCreateSettleOrder(settleOrderListBo, settleFileUploadReqBo);
            //判断是否解付
            solutionPay(settleFileUploadReqBo);
        } else {
            //通知商户匹配状态
            sendSettleNotify(settleFileUploadReqBo.getMemberId(), settleFileUploadReqBo.getIncomeNo(),
                    null, SearchTypeEnum.MATCH_RESULT.getCode());

            log.info("call 结汇上传文件处理服务 校验错误信息：{}", errorBuffer.toString());
            dealError(settleFileUploadReqBo.getFileBatchNo(), errorBuffer.toString(), UploadFileStatus.ERROR.getCode()
                    , settleOrderListBo.getSuccessCount(), settleOrderListBo.getErrorCount());
            FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
            fiCbPaySettleDo.setOrderId(settleFileUploadReqBo.getSettleOrderId());
            fiCbPaySettleDo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
            fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.UPLOAD_DATA_ERROR.getCode());
            cbPaySettleManager.modify(fiCbPaySettleDo);
        }

        //如果复审通过并且有错误的情况
        if (MatchingStatusEnum.YES_MATCH.getCode() == settleFileUploadReqBo.getFlag() && errorBuffer.length() > 0) {
            // 根据ID更结汇匹配状态
            if (settleFileUploadReqBo.getApplyId() != null) {
                log.info("call 结汇订单重复上传同步更新结汇匹配状态。");
                ParamValidate.checkUpdate(fiCbPaySettleApplyMapper.updateByKeyMatchingOrderId(
                        settleFileUploadReqBo.getApplyId(), 13), "汇入申请单状态不正确");
            }
            log.info("人工匹配匹配成功后 校验数据有误发送邮件给商户开始···");
            //发送邮件通知给商户
            String content = Constants.SETTLE_ERROR_CONTENT_MERCHANT.replace("${time}",
                    DateUtil.format(new Date(), Constants.DATE_FORMAT_FULL_TWO)).
                    replace("${incomeNo}", settleFileUploadReqBo.getIncomeNo() == null ? "" :
                            settleFileUploadReqBo.getIncomeNo());
            String subject = "跨境收款—结汇明细有误—宝付网络科技（上海）有限公司";
            MemberEmailBo memberEmailBo = memberEmailQueryBiz.findBusinessEmail(settleFileUploadReqBo.getMemberId());
            List<String> emailToList = Lists.newArrayList(memberEmailBo.getBusinessEmail());
            log.info("发送给商户的邮件：商户结汇文件订单校验不通过，收件人：{}发送邮件内容：{}", emailToList, content);
            emailSendServiceImpl.sendEmailHtml(content, emailToList, null, null,
                    null, subject, null, null, null,
                    "宝付网络科技（上海）有限公司");
            //发送邮件给清算
            String contentCM = Constants.SETTLE_ERROR_CONTENT_CM.replace("${time}",
                    DateUtil.format(new Date(), Constants.DATE_FORMAT_FULL_TWO)).
                    replace("${incomeNo}", settleFileUploadReqBo.getIncomeNo() == null ? "" :
                            settleFileUploadReqBo.getIncomeNo()).replace("${memberName}",
                    settleFileUploadReqBo.getMemberName());
            String subject1 = "跨境收款--交易明细上传--宝付网络科技（上海）有限公司";
            List<String> mailAddressTO = Splitter.on(",").trimResults().splitToList(settleFileErrorEmailTo);
            List<String> mailAddressCC = Splitter.on(",").trimResults().splitToList(settleFileErrorEmailCc);
            log.info("发送给清算的邮件：商户结汇文件订单校验不通过，收件人：{}抄送人：{}发送邮件内容：{}", mailAddressTO,
                    mailAddressCC, contentCM);
            emailSendServiceImpl.sendEmailHtml(contentCM, mailAddressTO, mailAddressCC, null, null, subject1,
                    null, null, null, "宝付网络科技（上海）有限公司");
            log.info("人工匹配匹配成功后 校验数据有误发送邮件给商户结束···");
        }


        log.info("call 结汇上传文件处理服务 总处理时间：{}", System.currentTimeMillis() - startTime);
    }

    /**
     * 结汇明细校验工具上传文件处理服务
     *
     * @param settleFileUploadReqBo 请求参数
     */
    @Override
    public Long settleFileVerifyProcess(SettleFileUploadReqBo settleFileUploadReqBo) {

        long startTime = System.currentTimeMillis();
        log.info("call 明细校验工具结汇上传文件处理服务参数:{} ", settleFileUploadReqBo);
        StringBuffer errorBuffer = new StringBuffer();
        Long errorFileId;
        try {
            //获取excel文件流
            List<Object[]> list = CbPaySettleConvert.getContextVerify(settleFileUploadReqBo.getDfsFileId());
            //文件内容校验
            SettleOrderListBo settleOrderListBo = settleFileCheckBiz.baseCheck(list, settleFileUploadReqBo, true);
            log.info("总金额:{}", settleOrderListBo.getTotalAmount());
            log.info("call 明细校验工具结汇上传文件处理服务 校验和解析处理时间:{}", System.currentTimeMillis() - startTime);
            errorBuffer.append(settleOrderListBo.getErrorMsg());
        } catch (Exception e) {
            errorBuffer.append("结汇明细文件不存在");
            log.error("文件校验异常", e);
        } finally {
            if (errorBuffer.length() == 0) {
                //校验成功
                errorFileId = 1L;
            } else {
                log.info("call 明细校验工具结汇上传文件处理服务 校验错误信息：{}", errorBuffer.toString());
                errorFileId = dealErrorTestAndVerify(errorBuffer.toString());
            }
            if (settleFileUploadReqBo.getFileBatchNo() != null) {
                redisManager.modify(errorFileId, settleFileUploadReqBo.getFileBatchNo().toString());
            }
        }
        log.info("call 明细校验工具结汇上传文件处理服务 总处理时间：{}", System.currentTimeMillis() - startTime);
        return errorFileId;
    }

    /**
     * 解付判断
     *
     * @param settleFileUploadReqBo 结汇文件上传参数
     */
    private void solutionPay(SettleFileUploadReqBo settleFileUploadReqBo) {

        //不需要解付的发送邮件
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(settleFileUploadReqBo.getSettleOrderId());
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(settleFileUploadReqBo.getSettleOrderId());
        if (InComeStatusEnum.COMPELTED_INCOME.getCode() == fiCbPaySettleDo.getIsIncome()) {
            sendEmailToCm(fiCbPaySettleDo.getIncomeNo(), fiCbPaySettleApplyDo.getIncomeAccount(), fiCbPaySettleDo.getIncomeCcy(),
                    fiCbPaySettleDo.getIncomeAmt());
        } else if (InComeStatusEnum.WAIT_INCOME.getCode() == fiCbPaySettleDo.getIsIncome()) {
            //发送mq解付
            SolutionPayApplyMqBo solutionPayApplyMqBo = new SolutionPayApplyMqBo();
            solutionPayApplyMqBo.setMemberId(settleFileUploadReqBo.getMemberId());
            solutionPayApplyMqBo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
            solutionPayApplyMqBo.setSettleOrderId(settleFileUploadReqBo.getSettleOrderId());
            log.info("call 网关发送MQ进行解付动作，生产者，队列名:{},内容:{}", MqSendQueueNameEnum.CBPAY_SOLUTION_PAY_APPLE_QUEUE_NAME,
                    JsonUtil.toJSONString(solutionPayApplyMqBo));
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SOLUTION_PAY_APPLE_QUEUE_NAME, JsonUtil.toJSONString(solutionPayApplyMqBo));
        }
    }

    /**
     * 文件内容校验通过批量创建结汇明细订单
     *
     * @param settleOrderListBo     excel文件内容List
     * @param settleFileUploadReqBo 请求参数
     */
    private void batchCreateSettleOrder(SettleOrderListBo settleOrderListBo, SettleFileUploadReqBo settleFileUploadReqBo) {

        Long startTime = System.currentTimeMillis();
        //excel文件内容总条数
        List<CbPaySettleOrderValidateBo> cbPaySettleOrderValidateBoList = settleOrderListBo.getCbPaySettleOrderValidateBos();
        int totalCount = cbPaySettleOrderValidateBoList.size();

        //结汇订单集合
        List<FiCbPaySettleOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //结汇订单商品信息集合
        List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList = Lists.newArrayList();
        //跨境订单运单信息集合
        List<FiCbpayOrderLogisticsDo> fiCbpayOrderLogisticsDoList = Lists.newArrayList();

        //组装数据
        for (int i = 0; i < cbPaySettleOrderValidateBoList.size(); i++) {
            Long orderId = orderIdManager.orderIdCreate();
            CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo = cbPaySettleOrderValidateBoList.get(i);
            FiCbPaySettleOrderDo fiCbPaySettleOrderDo = CbPaySettleConvert.toFiCbPaySettleOrderDo(cbPaySettleOrderValidateBo,
                    settleFileUploadReqBo, orderId);
            List<FiCbPayOrderItemDo> cbPayOrderItemDoList = CbPaySettleConvert.paramConvert(cbPaySettleOrderValidateBo, orderId, settleOrderListBo.getVersion());
            //跨境订单运单信息
            FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = CbPaySettleConvert.toFiCbpayOrderLogisticsDo(cbPaySettleOrderValidateBo, orderId);

            fiCbPayOrderDoList.add(fiCbPaySettleOrderDo);
            fiCbPayOrderItemDoList.addAll(cbPayOrderItemDoList);
            if (fiCbpayOrderLogisticsDo != null) {
                fiCbpayOrderLogisticsDoList.add(fiCbpayOrderLogisticsDo);
            }

            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == cbPaySettleOrderValidateBoList.size() - 1) {
                fiCbPaySettleOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
            }

            //商品信息每1000条添加到一个List中
            if (fiCbPayOrderItemDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || cbPayOrderItemDoList.size() > Constants.EXCEL_MAX_COUNT
                    || i == cbPaySettleOrderValidateBoList.size() - 1) {
                orderItemManager.addBatchOrderItem(fiCbPayOrderItemDoList);
                fiCbPayOrderItemDoList = Lists.newArrayList();
            }

            //订单信息运单信息每1000条添加到一个List中
            if (fiCbpayOrderLogisticsDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == cbPaySettleOrderValidateBoList.size() - 1) {
                if (!fiCbpayOrderLogisticsDoList.isEmpty()) {
                    fiCbpayOrderLogisticsMapper.batchInsert(fiCbpayOrderLogisticsDoList);
                    fiCbpayOrderLogisticsDoList = Lists.newArrayList();
                }
            }

            //释放内存
            if (i == cbPaySettleOrderValidateBoList.size() - 1) {
                cbPaySettleOrderValidateBoList = Lists.newArrayList();
            }
        }

        //更新文件批次状态为成功
        FiCbPayFileUploadDo fiCbpayFileUploadDo = CbPaySettleConvert.toFiCbPayFileUploadDo(settleFileUploadReqBo.getFileBatchNo(), totalCount);
        fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbpayFileUploadDo);
        if (NumberConstants.TWELVE != settleFileUploadReqBo.getFlag()) {
            FiCbPaySettleDo fiCbPaySettleDo = new FiCbPaySettleDo();
            fiCbPaySettleDo.setOrderId(settleFileUploadReqBo.getSettleOrderId());
            fiCbPaySettleDo.setFileStatus(SettleFileStatusEnum.UPLOAD_SUCCESS.getCode());
            fiCbPaySettleDo.setFileBatchNo(settleFileUploadReqBo.getFileBatchNo());
            cbPaySettleManager.modify(fiCbPaySettleDo);
        }

        log.info("跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
    }

    /**
     * 外汇汇入申请(客户在商户前台发起)
     * <p>
     * 1、判断订单是否存在
     * 2、判断和汇入通知信息是否匹配
     *
     * @param settleIncomeApplyBo 外汇汇入申请参数
     * @return 申请单号
     */
    @Override
    @Transactional
    public Long settleIncomeApply(SettleIncomeApplyBo settleIncomeApplyBo) {

        //保存外汇汇入申请订单信息
        Long orderId = orderIdManager.orderIdCreate();
        FiCbPaySettleApplyDo settleApplyDo = CbPaySettleConvert.toFiCbPaySettleApplyDo(settleIncomeApplyBo, orderId);
        fiCbPaySettleApplyMapper.insert(settleApplyDo);

        //结汇订单明细文件批次入库
        FiCbPayFileUploadDo fileUploadDo = CbPaySettleConvert.paramConvert(settleIncomeApplyBo, orderId);
        fileUploadDo.setBatchNo(String.valueOf(orderId));
        fiCbpayFileUploadMapper.insert(fileUploadDo);

        return orderId;
    }

    /**
     * 外汇汇入申请审核
     *
     * @param receiveAuditBo 审核对象
     */
    @Override
    @Transactional
    public void receiveAudit(ReceiveAuditBo receiveAuditBo) {

        //校验订单是否存在，订单状态是否合法
        FiCbPaySettleDo settleQueryDo = receiveAuditOrderCheck(receiveAuditBo);

        //更新清算审核状态
        cbPaySettleManager.modify(CbPaySettleConvert.receiveAuditParamConvert(receiveAuditBo));

        //复审通过需往外币账户中充值，并自动发送结汇申请，通过使用反向判断结束其他状态的标识
        if (ReceiverAuditCmStatusEnum.SECOND_CHECK.getCode() != receiveAuditBo.getCmAuditStatus()) {
            return;
        }

        //如果是人民币时，不需要往外币账户中充值,此处判断是否为外币才充值
        if (!settleQueryDo.getIncomeCcy().equals(CcyEnum.CNY.getKey())) {
            //调用清算进行充值
            RechargeReqDto rechargeReqDto = CbPaySettleConvert.toWithdrawReqDto(settleQueryDo);
            cbPayCmBiz.foreignCurrencyRecharge(rechargeReqDto);
        }

        //复审通过自动发起结汇
        sendSettleConfirmMq(settleQueryDo.getOrderId());
    }

    /**
     * 收汇清算审核参数信息校验
     *
     * @param receiveAuditBo 请求业务参数信息
     * @return 返回校验成功之后的收汇信息
     */
    private FiCbPaySettleDo receiveAuditOrderCheck(ReceiveAuditBo receiveAuditBo) {

        //校验订单是否存在，订单状态是否合法
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(receiveAuditBo.getOrderId());
        if (fiCbPaySettleDo == null) {
            log.info("银行通知信息不存在，汇入汇款编号:{}", receiveAuditBo.getOrderId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00109);
        }
        //解付状态判断
        if (InComeStatusEnum.COMPELTED_INCOME.getCode() != fiCbPaySettleDo.getIncomeStatus()) {
            log.warn("收汇到账订单:{},解付状态为：{},清算审核异常", fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getIncomeStatus());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00201);
        }
        if (ReceiverAuditCmStatusEnum.INIT.getCode() == receiveAuditBo.getCmAuditStatus()) {
            log.warn("收汇到账订单:{},清算审核状态为：{},清算审核异常", fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getCmAuditState());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00201);
        }
        //收汇清算初审状态校验
        if (receiveAuditBo.getCmAuditStatus() == ReceiverAuditCmStatusEnum.FIRST_CHECK.getCode() ||
                receiveAuditBo.getCmAuditStatus() == ReceiverAuditCmStatusEnum.NO_FIRST_CHECK.getCode()) {
            if (ReceiverAuditCmStatusEnum.INIT.getCode() != fiCbPaySettleDo.getCmAuditState()) {
                log.warn("收汇到账订单:{},清算审核状态为：{},清算初审异常", fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getCmAuditState());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00201);
            }
        }
        //收汇清算复审状态校验
        if (receiveAuditBo.getCmAuditStatus() == ReceiverAuditCmStatusEnum.SECOND_CHECK.getCode() ||
                receiveAuditBo.getCmAuditStatus() == ReceiverAuditCmStatusEnum.NO_SECOND_CHECK.getCode()) {
            if (ReceiverAuditCmStatusEnum.FIRST_CHECK.getCode() != fiCbPaySettleDo.getCmAuditState()) {
                log.warn("收汇到账订单:{},清算审核状态为：{},清算复审异常", fiCbPaySettleDo.getOrderId(), fiCbPaySettleDo.getCmAuditState());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00201);
            }
        }
        return fiCbPaySettleDo;
    }

    /**
     * 运营设置匹配信息
     * <p>
     * 1、更新银行通知表中商户编号
     * 2、更新汇入申请表中匹配状态为匹配成功
     *
     * @param settleOperationSetReqBo 运营设置匹配信息
     */
    @Override
    public void operationSet(SettleOperationSetReqBo settleOperationSetReqBo) {

        FiCbPaySettleDo fiCbPaySettleDo = CbPaySettleConvert.toFiCbPaySettleDo(settleOperationSetReqBo);
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = CbPaySettleConvert.toFiCbPaySettleApplyDo(settleOperationSetReqBo);

        //判断用户是否开通外币账户
        FiCbPaySettleDo paySettleDo = cbPaySettleManager.queryByOrderId(settleOperationSetReqBo.getOrderId());
        if (!CcyEnum.CNY.getKey().equals(paySettleDo.getIncomeCcy())) {
            cbPayCmBiz.queryForeignCurrencyAcc(paySettleDo.getIncomeCcy(), settleOperationSetReqBo.getMemberId());
        }

        fiCbPaySettleManager.operationSet(fiCbPaySettleDo, fiCbPaySettleApplyDo);

    }

    /**
     * excel 内容错误处理
     *
     * @param batchNo    文件批次
     * @param error      错误内容
     * @param fileStatus 文件状态
     */
    private void dealError(Long batchNo, String error, String fileStatus, Integer successCount, Integer failCount) {

        InsertReqDTO insertReqDTO = new InsertReqDTO();
        //文件名
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");
        //机构编码
        insertReqDTO.setOrgCode("CBPAY");
        //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        //文件日期
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        insertReqDTO.setRemark("结汇文件校验错误信息");//备注信息
        CommandResDTO commandResDTO = DfsClient.upload(error.getBytes(), insertReqDTO);
        log.info("call 上传错误明细文件响应信息:{}", commandResDTO);

        //更新批次文件错误信息DFSFileId
        FiCbPayFileUploadDo fiCbpayFileUploadDo = new FiCbPayFileUploadDo();
        fiCbpayFileUploadDo.setFileBatchNo(batchNo);
        fiCbpayFileUploadDo.setErrorFileId(commandResDTO.getFileId());
        fiCbpayFileUploadDo.setStatus(fileStatus);
        fiCbpayFileUploadDo.setFailCount(failCount);
        fiCbpayFileUploadDo.setSuccessCount(successCount);
        fiCbpayFileUploadDo.setErrorFileId(commandResDTO.getFileId());
        fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbpayFileUploadDo);
    }

    /**
     * excel 内容错误处理
     * 将错误文件DFSID传回
     *
     * @param error 错误内容
     */
    private Long dealErrorTestAndVerify(String error) {
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        //文件名
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");
        //机构编码
        insertReqDTO.setOrgCode("CBPAY");
        //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        //文件日期
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        insertReqDTO.setRemark("结汇文件校验错误信息");//备注信息
        CommandResDTO commandResDTO = DfsClient.upload(error.getBytes(), insertReqDTO);
        log.info("call 上传错误明细文件响应信息:{}", commandResDTO);
        return commandResDTO.getFileId();
    }

    /**
     * 发送结汇申请确认MQ
     *
     * @param settleOrderId 结汇宝付订单号
     */
    @Override
    public void sendSettleConfirmMq(Long settleOrderId) {

        CbPaySettleConfirmReqBo cbPaySettleConfirmReqBo = new CbPaySettleConfirmReqBo();
        cbPaySettleConfirmReqBo.setOrderId(settleOrderId);
        log.info("MQ 复审通过自动发起结汇MQ，生产者，队列名:{},消息内容:{}", MqSendQueueNameEnum.CBPAY_CROSS_SETTLE_CONFIRM,
                cbPaySettleConfirmReqBo);
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_CROSS_SETTLE_CONFIRM, cbPaySettleConfirmReqBo);
    }

    /**
     * 结汇申请确认
     *
     * @param cbPaySettleReqBo 结汇申请确认对象
     */
    @Override
    public void settleConfirm(CbPaySettleConfirmReqBo cbPaySettleReqBo) {

        log.info("call 结汇申请确认,结汇申请处理开始时间:{}", DateUtil.getCurrent());
        log.info("call 结汇申请确认,参数:{}", cbPaySettleReqBo);
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(cbPaySettleReqBo.getOrderId());
        log.info("call 结汇申请确认,查询结汇订单信息：{}", fiCbPaySettleDo);

        if (fiCbPaySettleDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        } else if (InComeStatusEnum.COMPELTED_INCOME.getCode() != fiCbPaySettleDo.getIncomeStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00116);
        } else if (SettleStatusEnum.WAIT_SETTLEMENT.getCode() != fiCbPaySettleDo.getSettleStatus()
                && SettleStatusEnum.FAIL.getCode() != fiCbPaySettleDo.getSettleStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00117);
        } else if (SettleFileStatusEnum.UPLOAD_SUCCESS.getCode() != fiCbPaySettleDo.getFileStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00137);
        }

        CbPaySettleBo cbPaySettleBo = CbPaySettleBoConvert.convert(fiCbPaySettleDo);

        MqSendQueueNameEnum mqSendQueueNameEnum;

        //修改结汇订单状态
        if (SettleStatusEnum.WAIT_SETTLEMENT.getCode() == fiCbPaySettleDo.getSettleStatus()) {
            mqSendQueueNameEnum = MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_SETTLE;
            modifySettleOrder(CbPaySettleBoConvert.convert(fiCbPaySettleDo.getOrderId(),
                    SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(), null,
                    SettleStatusEnum.WAIT_SETTLEMENT.getCode(), InComeStatusEnum.COMPELTED_INCOME.getCode()));
        } else if (SettleStatusEnum.FAIL.getCode() == fiCbPaySettleDo.getSettleStatus()) {
            mqSendQueueNameEnum = MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_RETRY;
            modifySettleOrder(CbPaySettleBoConvert.convert(fiCbPaySettleDo.getOrderId(),
                    SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(), null,
                    SettleStatusEnum.FAIL.getCode(), InComeStatusEnum.COMPELTED_INCOME.getCode()));
        } else {
            return;
        }

        try {
            //更新商户外币账户到在途账户
            cbPayCmBiz.transferAcc(cbPaySettleBo);

            //上传结汇申请明细文件
            long fileId = uploadSettlementFile(cbPaySettleBo, cbPaySettleReqBo.getDetailOrderIds());

            //组装结汇申请接口参数信息并发送MQ消息
            CgwSumSettleReqDto cgwSumSettleReqDto = DfsParamConvert.convertToCgwSumSettleReq(cbPaySettleBo, fileId);
            mqSendService.sendMessage(mqSendQueueNameEnum, cgwSumSettleReqDto);

        } catch (Exception e) {
            //修改结汇订单状态
            modifySettleOrder(CbPaySettleBoConvert.convert(fiCbPaySettleDo.getOrderId(),
                    SettleStatusEnum.FAIL.getCode(), null,
                    SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(), InComeStatusEnum.COMPELTED_INCOME.getCode()));
            log.error("call  结汇申请确认，结汇申请异常:{}", e);
        }
    }

    /**
     * 结汇申请发往渠道通知处理
     *
     * @param cgwBaserespDto 结汇申请回执对象
     */
    @Override
    public void settleFirstCallback(CgwBaseRespDto cgwBaserespDto) {

        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(Long.parseLong(cgwBaserespDto.getBfBatchId()));
        log.info("call 结汇申请渠道响应（1），查询结汇订单信息：{}", fiCbPaySettleDo);
        if (fiCbPaySettleDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        } else if (InComeStatusEnum.COMPELTED_INCOME.getCode() != fiCbPaySettleDo.getIncomeStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00116);
        } else if (SettleStatusEnum.SETTLEMENT_PROCESSING.getCode() != fiCbPaySettleDo.getSettleStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00119);
        }

        //修改结汇订单状态为结汇完成状态 0-失败  1-成功 -1-异常
        if (cgwBaserespDto.getCode() == 0 || cgwBaserespDto.getCode() == -1) {
            modifySettleOrder(CbPaySettleBoConvert.convert(fiCbPaySettleDo.getOrderId(),
                    SettleStatusEnum.FAIL.getCode(), null, SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(),
                    InComeStatusEnum.COMPELTED_INCOME.getCode()));
            CbPaySettleBo cbPaySettleBo = CbPaySettleBoConvert.convert(fiCbPaySettleDo);
            cbPayCmBiz.transferToAcc(cbPaySettleBo);
            log.info("call 结汇申请渠道响应（1），结汇失败");
        }
        log.info("call 结汇申请渠道响应（1），接收结汇申请发往渠道通知处理完成：{}", DateUtil.getCurrent());

        //通知商户结汇结果通知
        sendSettleNotify(null, null, fiCbPaySettleDo.getOrderId(), SearchTypeEnum.SETTLEMENT_RESULT.getCode());
    }

    /**
     * 结汇申请回执结果处理
     *
     * @param cgwSettleResultRespDto 结汇申请回执对象
     */
    @Override
    public void settleSecondCallback(CgwSettleResultDto cgwSettleResultRespDto) {

        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(Long.parseLong(cgwSettleResultRespDto.getRemSerialNo()));
        log.info("call 结汇申请渠道响应（2），查询结汇订单信息：{}", fiCbPaySettleDo);
        if (fiCbPaySettleDo == null || cgwSettleResultRespDto.getCgwSettleRespDto() == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        } else if (InComeStatusEnum.COMPELTED_INCOME.getCode() != fiCbPaySettleDo.getIncomeStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00116);
        } else if (SettleStatusEnum.SETTLEMENT_PROCESSING.getCode() != fiCbPaySettleDo.getSettleStatus()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00119);
        }

        //结汇反洗钱处理
        if (cgwSettleResultRespDto.getAmlState() != null) {
            cbPaySettleAmlBiz.amlApplySecondNotify(cgwSettleResultRespDto);
        }

        CbPaySettleBo cbPaySettleBo = CbPaySettleBoConvert.convert(fiCbPaySettleDo, cgwSettleResultRespDto.getCgwSettleRespDto());
        //修改结汇订单状态为结汇完成状态 处理状态 3=成功
        if (cgwSettleResultRespDto.getState() != 3) {
            cbPayCmBiz.transferToAcc(cbPaySettleBo);
            log.info("call 结汇申请渠道响应（2），结汇失败，订单编号：{}", cbPaySettleBo.getOrderId());
            return;
        }
        //银行处理状态 2-失败 1-成功
        if (cgwSettleResultRespDto.getSettleStatus() == 2 || cgwSettleResultRespDto.getSettleStatus() == 0) {
            modifySettleOrder(CbPaySettleBoConvert.convert(cbPaySettleBo.getOrderId(), cbPaySettleBo.getSettleRate(),
                    cbPaySettleBo.getSettleCcy(), cbPaySettleBo.getSettleAmt(), SettleStatusEnum.FAIL.getCode(),
                    null, SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(),
                    InComeStatusEnum.COMPELTED_INCOME.getCode()));
            cbPayCmBiz.transferToAcc(cbPaySettleBo);
            log.info("call 结汇申请渠道响应（2），结汇失败，订单编号：{}", cbPaySettleBo.getOrderId());
        } else if (cgwSettleResultRespDto.getSettleStatus() == 1) {

            //判断是否已经结汇垫资
            boolean isSuccess = cbPaySettlePrepaymentBiz.isPrepayment(cbPaySettleBo.getIncomeNo());

            //商户结算汇率
            if (isSuccess) {
                CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = fiCbPaySettlePrepaymentManager.getPrepaymentInfoByIncomeNo(fiCbPaySettleDo.getIncomeNo());
                cbPaySettleBo.setMemberSettleRate(cbPaySettlePrepaymentDo.getPreSettleRate());
            } else {
                cbPaySettleBo.setMemberSettleRate(getMemberSettleRate(cgwSettleResultRespDto.getCgwSettleRespDto().getExchange(),
                        fiCbPaySettleDo.getMemberId(), fiCbPaySettleDo.getIncomeCcy()).setScale(6, BigDecimal.ROUND_HALF_UP));
            }

            //反洗钱部分成功处理
            int statusCode = 0;
            BigDecimal settleAmt = cbPaySettleBo.getSettleAmt();
            FiCbPayFileUploadDo fiCbPayFileUploadDos = fiCbpayFileUploadMapper.queryByBatchId(fiCbPaySettleDo.getFileBatchNo());
            FiCbPaySettleDo settleDo = cbPaySettleManager.queryByOrderId(Long.parseLong(cgwSettleResultRespDto.getRemSerialNo()));
            if (UploadFileStatus.AML_PART_SUCCESS.getCode().equals(fiCbPayFileUploadDos.getStatus())) {
                statusCode = SettleStatusEnum.PART_TRUE.getCode();
                settleAmt = CcyEnum.CNY.getKey().equals(fiCbPaySettleDo.getIncomeCcy()) ? settleDo.getRealIncomeAmt() : settleAmt;
            } else if (UploadFileStatus.AML_SUCCESS.getCode().equals(fiCbPayFileUploadDos.getStatus())) {
                statusCode = SettleStatusEnum.TURE.getCode();
            }


            //商户结汇金额
            if (Currency.CNY.getCode().equals(fiCbPaySettleDo.getIncomeCcy())) {
                cbPaySettleBo.setMemberSettleAmt(settleAmt);
            } else {
                cbPaySettleBo.setMemberSettleAmt(cgwSettleResultRespDto.getCgwSettleRespDto().getSettleMoney().multiply(
                        (cbPaySettleBo.getMemberSettleRate().divide(new BigDecimal("100"))))
                        .setScale(2, BigDecimal.ROUND_DOWN));
            }

            log.info("call 结汇申请回执结果处理 宝付损益 计算  OrderId:{},settleAmt:{},MemberSettleAmt:{}" +
                            "人民币金额CnyMoney:{},结汇金额SettleMoney:{}", cbPaySettleBo.getOrderId(),
                    settleAmt, cbPaySettleBo.getMemberSettleAmt());
            //宝付损益
            cbPaySettleBo.setProfitAndLoss(settleAmt.subtract(cbPaySettleBo.getMemberSettleAmt())
                    .setScale(2, BigDecimal.ROUND_DOWN));

            log.info("call 结汇申请回执结果处理 OrderId:{},商户结算汇率:{},商户结汇金额:{},宝付损益:{}," +
                            "人民币金额CnyMoney:{},结汇金额SettleMoney:{}",
                    cbPaySettleBo.getOrderId(), cbPaySettleBo.getMemberSettleRate(),
                    cbPaySettleBo.getMemberSettleAmt(), cbPaySettleBo.getProfitAndLoss(),
                    cgwSettleResultRespDto.getCgwSettleRespDto().getCnyMoney(),
                    cgwSettleResultRespDto.getCgwSettleRespDto().getSettleMoney());

            modifySettleOrder(CbPaySettleBoConvert.convert(cbPaySettleBo.getOrderId(), cbPaySettleBo.getSettleRate(),
                    cbPaySettleBo.getSettleCcy(), settleAmt, statusCode,
                    null, SettleStatusEnum.SETTLEMENT_PROCESSING.getCode(),
                    InComeStatusEnum.COMPELTED_INCOME.getCode(), cbPaySettleBo.getMemberSettleAmt(),
                    cbPaySettleBo.getMemberSettleRate(), cbPaySettleBo.getProfitAndLoss()));

            //在途到基本，更新商户人民币账户余额
            rechange(cbPaySettleBo);

            //发送结汇通知邮件
            settleEmailBiz.settleSuccessNotify(Long.parseLong(cgwSettleResultRespDto.getRemSerialNo()));

            // 结汇成功，记人民币备付金账户入金，发送通知央行MQ
            RSIS150 rsis150 = ParamConvert.settleNotifyCentralBankConvert(cgwSettleResultRespDto, fiCbPaySettleDo);
            log.info("结汇完成，人民币备付金变动通知央行报文：rsis150:{},成功时间:{}", JSON.toJSONString(rsis150), rsis150.getSuccTime());
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_NOTIFY_CENTRAL_BANK, rsis150);
        }
        log.info("call 结汇申请渠道响应（2），结汇申请处理完成{}", DateUtil.getCurrent());

        //通知商户结汇结果通知
        sendSettleNotify(null, null, fiCbPaySettleDo.getOrderId(), SearchTypeEnum.SETTLEMENT_RESULT.getCode());

    }

    /**
     * 获取商户结汇费率
     *
     * @param settleRate 银行结汇汇率
     * @param memberId   商户号
     * @param ccy        汇入币种
     * @return 返回结果
     */
    private BigDecimal getMemberSettleRate(BigDecimal settleRate, Long memberId, String ccy) {

        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setMemberId(memberId);
        cbPayMemberRateReqBo.setCcy(ccy);
        //业务类型 2:结汇
        cbPayMemberRateReqBo.setBusinessType(2);

        //获取商户浮动汇率bp
        CbPayMemberRateReqBo memberRate = cbPayMemberRateBiz.queryMemberRateOne(cbPayMemberRateReqBo);

        BigDecimal bp = new BigDecimal(0);

        if (memberRate != null && memberRate.getMemberRateBp() != null) {
            bp = new BigDecimal("" + memberRate.getMemberRateBp()).divide(new BigDecimal("10000"));
        }

        BigDecimal memberSettleRate;
        //除日元外，其他币种都要除以100
        if (Constants.JPY_CURRENCY.equals(ccy)) {
            memberSettleRate = settleRate.subtract(bp);
        } else {
            memberSettleRate = settleRate.divide(new BigDecimal("100"), 6, BigDecimal.ROUND_DOWN).subtract(bp).multiply(new BigDecimal(100));
        }

        log.info("call getMemberSettleRate 获取商户结汇费率 商户号:{}，币种:{}，银行汇率settleRate:{}，" +
                        "商户汇率memberSettleRate:{},商户设置bp:{}",
                memberId, ccy, settleRate, memberSettleRate, bp);

        return memberSettleRate;
    }

    /**
     * 上传结汇申请明细文件
     *
     * @param cbPaySettleBo     结汇订单
     * @param exDetailsOrderIds 反洗钱不通过的订单明细
     */
    private long uploadSettlementFile(CbPaySettleBo cbPaySettleBo, List<Long> exDetailsOrderIds) {

        //获取结汇订单明细
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(cbPaySettleBo.getOrderId());
        CacheCBMemberBankDto cacheCBMemberBankDto = cbPayCacheManager.getCBMemberBank(cbPaySettleBo.getMemberId().intValue());
        //查询结汇上传的订单信息
        List<FiCbPaySettleOrderDo> orderList = fiCbPaySettleOrderMapper.selectByExOrderIds(cbPaySettleBo.getFileBatchNo(),
                exDetailsOrderIds, null);
        StringBuilder stringBuffer = new StringBuilder();
        for (FiCbPaySettleOrderDo fiCbPaySettleOrderDo : orderList) {
            List<FiCbPayOrderItemInfoDo> orderItems = orderItemManager.queryItemInfo(fiCbPaySettleOrderDo.getOrderId());
            CgwSettleReqDto cgwSettleReqDto = DfsParamConvert.convertToSettlementReq(cbPaySettleBo, fiCbPaySettleOrderDo,
                    fiCbPaySettleApplyDo, orderItems, cacheCBMemberBankDto);
            //转换成渠道系统需要的txt格式
            stringBuffer.append(ConvertDsfFile.getString(cgwSettleReqDto, ConvertDsfFile.SETTLE_LIST)).append("\r");
        }

        //上传结汇明细信息
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        //文件名
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");
        //机构编码
        insertReqDTO.setOrgCode("CBPAY");
        //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileGroup(FileGroup.TRADE_INFO);
        //文件日期
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        //备注信息
        insertReqDTO.setRemark("跨境结汇订单明细文件信息");
        CommandResDTO commandResDTO = DfsClient.upload(stringBuffer.toString().getBytes(), insertReqDTO);
        log.info("call  结汇申请确认，上传跨境结汇订单明细文件响应信息:{}", commandResDTO);

        return commandResDTO.getFileId();
    }

    /**
     * 更新结汇订单信息
     *
     * @param cbPaySettleBo 结汇订单对象
     */
    private void modifySettleOrder(CbPaySettleBo cbPaySettleBo) {
        try {
            FiCbPaySettleDo fiCbPaySettleMd = CbPaySettleConvert.toFiCbPaySettleDo(cbPaySettleBo);
            log.info("call 更新结汇订单信息参数：{}", cbPaySettleBo);
            cbPaySettleManager.modify(fiCbPaySettleMd);
        } catch (Exception e) {
            log.error("call 更新结汇订单信息失败：{}", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00118);
        }
    }

    /**
     * 提现与基本账户充值
     *
     * @param cbPaySettleBo 报关参数
     */
    private void rechange(CbPaySettleBo cbPaySettleBo) {
        Date applyDate = DateUtil.getCurrentDate();
        BigDecimal fee = null;
        //在途到外汇科目（提现）
        cbPayCmBiz.withdrawDeposit(cbPaySettleBo);

        boolean isSuccess = cbPaySettlePrepaymentBiz.isPrepayment(cbPaySettleBo.getIncomeNo());
        //判已经垫资，给垫资账户加值，否则，商户基本账户充值
        if (isSuccess) {
            //垫资账户加钱
            cbPayCmBiz.prepaymentAccRecharge(cbPaySettleBo);

            //更新结汇垫资状态
            CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = new CbPaySettlePrepaymentDo();
            cbPaySettlePrepaymentDo.setIncomeNo(cbPaySettleBo.getIncomeNo());
            cbPaySettlePrepaymentDo.setPreStatus(2); // 2- 垫资已返还
            fiCbPaySettlePrepaymentManager.modifyByIncomeNo(cbPaySettlePrepaymentDo);
        } else {
            //商户人民币账户充钱
            fee = cbPayCmBiz.rmbAccRecharge(cbPaySettleBo);
        }
        int statusCode = getSettleStatus(cbPaySettleBo.getFileBatchNo());
        modifySettleOrder(CbPaySettleBoConvert.convert(cbPaySettleBo.getOrderId(), applyDate, DateUtil.getCurrentDate(),
                SettleFlagEnum.SETTLE_SUCCESS.getCode(), fee, null, statusCode,
                InComeStatusEnum.COMPELTED_INCOME.getCode()));

        BigDecimal profitLoss = cbPaySettleBo.getProfitAndLoss();
        int dealCode = DealCodeEnums.SETTLE_PROFIT.getCode();
        if (cbPaySettleBo.getProfitAndLoss().compareTo(BigDecimal.ZERO) < 0) {
            dealCode = DealCodeEnums.SETTLE_LOSS.getCode();
            profitLoss = profitLoss.multiply(new BigDecimal("-1"));
        }
        if (CcyEnum.CNY.getKey().equals(cbPaySettleBo.getIncomeCcy()) || BigDecimal.ZERO.compareTo(profitLoss) == 0) {
            log.info("结汇完成币种为人民币或损益为0时不需要损益记账，订单编号：{}，损益金额：{}", cbPaySettleBo.getOrderId(), profitLoss);
            return;
        }
        // 调用清算服务
        ClearRequestBo request = CmParamConvert.cmRequestConvert(dealCode,
                cbPaySettleBo.getChannelId(), String.valueOf(cbPaySettleBo.getMemberId()), "",
                ProFunEnum.PRO_FUN_10180001.getFunctionId(), cbPaySettleBo.getOrderId(), profitLoss,
                BigDecimal.ZERO, BigDecimal.ZERO, 1, ProFunEnum.PRO_FUN_10180001.getProductId());
        request.setMemberId(cbPaySettleBo.getMemberId());
        log.info("结汇完成损益科目记账：请求参数信息：{}", request);
        ClearingResponseBo clearingResponseBo = cmClearBizImpl.acquiringAccount(request);
        log.info("结汇完成损益科目记账结束，返回信息：{}", clearingResponseBo);
    }

    /**
     * 结汇失败时将外币账户余额由外币在途账户转账到外币基本账户
     *
     * @param cbPaySettleBo 结汇相关参数系想你
     */
    private void settleFailTransferAcc(CbPaySettleBo cbPaySettleBo) {

        Date applyDate = DateUtil.getCurrentDate();
        //在途到外汇科目（提现）
        cbPayCmBiz.transferToAcc(cbPaySettleBo);

        modifySettleOrder(CbPaySettleBoConvert.convert(cbPaySettleBo.getOrderId(), applyDate, DateUtil.getCurrentDate(),
                SettleFlagEnum.SETTLE_SUCCESS.getCode(), null, null,
                SettleStatusEnum.FAIL.getCode(), InComeStatusEnum.COMPELTED_INCOME.getCode()));
    }

    /**
     * 手工重新清算
     *
     * @param orderId 宝付结汇订单号
     */
    @Override
    public void manualSettlement(Long orderId) {
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(orderId);
        log.info("call 手工重新清算，查询结汇订单：{}", DateUtil.getCurrent());
        if (fiCbPaySettleDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        } else if (fiCbPaySettleDo.getSettleFlag() == SettleFlagEnum.SETTLE_SUCCESS.getCode()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00121);
        }

        CbPaySettleBo cbPaySettleBo = CbPaySettleBoConvert.convert(fiCbPaySettleDo);
        if (SettleStatusEnum.FAIL.getCode() == cbPaySettleBo.getSettleStatus()
                && SettleFlagEnum.SETTLE_SUCCESS.getCode() != cbPaySettleBo.getSettleFlag()) {
            settleFailTransferAcc(cbPaySettleBo);
        } else if ((SettleStatusEnum.TURE.getCode() == cbPaySettleBo.getSettleStatus()
                || SettleStatusEnum.PART_TRUE.getCode() == cbPaySettleBo.getSettleStatus())
                && SettleFlagEnum.SETTLE_SUCCESS.getCode() != cbPaySettleBo.getSettleFlag()) {
            rechange(cbPaySettleBo);
            settleEmailBiz.settleSuccessNotify(cbPaySettleBo.getOrderId());
        }
        log.info("call 手工重新清算处理完成时间：{}", DateUtil.getCurrent());
    }

    /**
     * 商户汇入申请审核(后台运营人员操作)
     *
     * @param operateConfirmBo 运营设置匹配状态参数
     */
    @Override
    public void operateConfirm(OperateConfirmBo operateConfirmBo) {

        //查询申请信息
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryByApplyNo(
                operateConfirmBo.getApplyNo());
        if (fiCbPaySettleApplyDo == null) {
            log.info("商户申请信息不存在，申请编号：{}", operateConfirmBo.getApplyNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00115);
        }
        FiCbPaySettleApplyDo settleApplyDo = CbPaySettleConvert.toFiCbPaySettleApplyDo(operateConfirmBo);
        if (MatchingStatusEnum.YES_MATCH.getCode() == operateConfirmBo.getMatchingStatus() || MatchingStatusEnum.RE_CHECK_NO_MATCH.getCode() == operateConfirmBo.getMatchingStatus()) {
            FiCbPaySettleDo fiCbPaySettleDo = this.checkConfirmInfo(operateConfirmBo, fiCbPaySettleApplyDo);
            if (MatchingStatusEnum.RE_CHECK_NO_MATCH.getCode() == operateConfirmBo.getMatchingStatus()) {
                //初审
                settleApplyDo.setInwardRemittanceId(operateConfirmBo.getIncomeNo());
            } else if (MatchingStatusEnum.YES_MATCH.getCode() == operateConfirmBo.getMatchingStatus()) {
                //复审  查询文件批次  如果有，就发送MQ解析文件
                FiCbPayFileUploadDo fiCbPayFileUploadDo = proxyCustomsManager.queryByBatchNoByAppNo(String.valueOf(operateConfirmBo.getApplyNo()));
                //  查询银行收汇通知记录
                if (fiCbPayFileUploadDo != null) {
                    // 匹配成功后 判定商户是否锁住
                    boolean lockFlag = redisBiz.isLock(Constants.SETTLE_OPERATION_FLAG.concat(String.valueOf(operateConfirmBo.getMemberId())));
                    if (lockFlag) {
                        log.info("call 商户{}已锁定，请勿频繁操作！", operateConfirmBo.getMemberId());
                        throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0081);
                    }
                    log.info("call 商户{}未锁住", operateConfirmBo.getMemberId());
                    boolean lock = redisBiz.lock(Constants.SETTLE_OPERATION_FLAG.concat(String.valueOf(operateConfirmBo.getMemberId())));
                    if (lock) {
                        log.info("call 匹配成功后锁住商户，发送MQ处理数据");
                        // 发送MQ
                        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SETTLE_FILE_PROCESS_QUEUE_NAME, CbPaySettleConvert.convertSettleApplyData(fiCbPayFileUploadDo,
                                operateConfirmBo, fiCbPaySettleDo.getOrderId()));
                    }
                }

            }
        }
        if (MatchingStatusEnum.YES_MATCH.getCode() != operateConfirmBo.getMatchingStatus()) {
            ParamValidate.checkUpdate(fiCbPaySettleApplyMapper.updateByKeySelective(settleApplyDo), "汇入申请单状态不正确");
        }

        //通知商户匹配状态
        if (MatchingStatusEnum.FAIL.getCode() == operateConfirmBo.getMatchingStatus()) {
            sendSettleNotify(fiCbPaySettleApplyDo.getMemberId(), fiCbPaySettleApplyDo.getIncomeNo(),
                    null, SearchTypeEnum.MATCH_RESULT.getCode());
        }
    }

    /**
     * 功能：校验银行通知和汇入订单信息
     *
     * @param operateConfirmBo     operateConfirmBo
     * @param fiCbPaySettleApplyDo fiCbPaySettleApplyDo
     * @return return
     */
    private FiCbPaySettleDo checkConfirmInfo(OperateConfirmBo operateConfirmBo, FiCbPaySettleApplyDo fiCbPaySettleApplyDo) {
        //查询收汇信息
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByIncomeNo(operateConfirmBo.getIncomeNo());
        if (fiCbPaySettleDo == null) {
            log.info("银行通知信息不存在，汇入汇款编号：{}", operateConfirmBo.getIncomeNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00109);
        }

        FiCbPaySettleApplyDo cbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(fiCbPaySettleDo.getOrderId());
        if (cbPaySettleApplyDo != null) {
            log.info("汇入汇款编号：{}已经匹配", operateConfirmBo.getIncomeNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00151);
        }

        //查询银行汇入通知，对币种校验
        if (!fiCbPaySettleDo.getIncomeCcy().equals(fiCbPaySettleApplyDo.getOrderCcy())) {
            log.error("商户号:{},汇入申请订单号:{},汇入币种不匹配，银行通知币种:{},商户填写币种:{}",
                    fiCbPaySettleApplyDo.getIncomeAccount(), fiCbPaySettleApplyDo.getIncomeNo(),
                    fiCbPaySettleDo.getIncomeCcy(), fiCbPaySettleApplyDo.getOrderCcy());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00108);
        }
        return fiCbPaySettleDo;
    }

    /**
     * 解付申请
     *
     * @param solutionPayApplyMqBo 解付申请mq队列参数 CBPAY_SOLUTION_PAY_CONFIRM_QUEUE_NAME
     */
    @Override
    public void solutionPayApple(SolutionPayApplyMqBo solutionPayApplyMqBo) {

        List<FiCbPaySettleOrderDo> orderList = fiCbPaySettleOrderMapper.selectBySettleInfo(solutionPayApplyMqBo.getFileBatchNo());
        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(solutionPayApplyMqBo.getSettleOrderId());
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(fiCbPaySettleDo.getOrderId());
        CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(solutionPayApplyMqBo.getMemberId().intValue());
        log.info("cacheMemberDto缓存：{}", cacheMemberDto);

        //上传解付文件到dfs获取dfs文件Id
        Long fileId = solutionPayFile(orderList, fiCbPaySettleDo, fiCbPaySettleApplyDo, cacheMemberDto);
        CgwSumRelieveReqDto cgwSumRelieveReqDto = CbPaySettleBoConvert.toCgwSumRelieveReqDto(fileId, fiCbPaySettleDo);
        //付款人名称设置为申请人名称
        cgwSumRelieveReqDto.setPayerName(fiCbPaySettleApplyDo.getRemittanceName() == null ? cacheMemberDto.getName()
                : fiCbPaySettleApplyDo.getRemittanceName());
        //发送mq通知渠道，请求解付
        log.info("call 解付申请，生产者，队列名：{},消息内容：{}", MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_RELIEVE,
                JsonUtil.toJSONString(cgwSumRelieveReqDto));
        mqSendService.sendMessage(MqSendQueueNameEnum.CGW_CROSS_BORDER_SERVICE_RELIEVE, JsonUtil.toJSONString(cgwSumRelieveReqDto));

        //更新收汇通知状态为解付申请中
        FiCbPaySettleDo cbPaySettleDo = new FiCbPaySettleDo();
        cbPaySettleDo.setOrderId(fiCbPaySettleDo.getOrderId());
        cbPaySettleDo.setIncomeStatus(InComeStatusEnum.INCOMING.getCode());
        cbPaySettleManager.modify(cbPaySettleDo);
    }

    /**
     * 解付文件组装
     *
     * @param orderList       订单明细
     * @param fiCbPaySettleDo 收汇信息
     * @param cacheMemberDto  商户缓存
     * @return dfs文件Id
     */
    private Long solutionPayFile(List<FiCbPaySettleOrderDo> orderList, FiCbPaySettleDo fiCbPaySettleDo, FiCbPaySettleApplyDo fiCbPaySettleApplyDo,
                                 CacheMemberDto cacheMemberDto) {

        //组织解付文件
        StringBuffer stringBuffer = new StringBuffer();
        //获取结汇订单明细
        for (FiCbPaySettleOrderDo fiCbPaySettleOrderDo : orderList) {
            CgwRelieveReqDto cgwRelieveReqDto = DfsParamConvert.convertToCgwRelieveReqDto(fiCbPaySettleOrderDo, fiCbPaySettleApplyDo, fiCbPaySettleDo,
                    cacheMemberDto);
            stringBuffer.append(ConvertDsfFile.getString(cgwRelieveReqDto, ConvertDsfFile.RELIEVE_LIST)).append("\r");
        }

        //上传结汇明细信息
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        //文件名
        insertReqDTO.setFileName(DateUtil.getCurrent() + ".txt");
        //机构编码
        insertReqDTO.setOrgCode("CBPAY");
        //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
        insertReqDTO.setFileGroup(FileGroup.TRADE_INFO);
        //文件日期
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        //备注信息
        insertReqDTO.setRemark("结汇解付订单明细文件信息");

        CommandResDTO commandResDTO = DfsClient.upload(stringBuffer.toString().getBytes(), insertReqDTO);
        log.info("call  结汇解付,上传结汇解付订单明细文件响应信息:{}", commandResDTO);

        return commandResDTO.getFileId();
    }

    /**
     * 解付申请,渠道第二次通知
     *
     * @param chRelieveBatchRespDto 银行通知对象
     */
    @Override
    public void releve(CgwRelieveResultDto chRelieveBatchRespDto) {

        log.info("call 解付申请,渠道第二次通知,参数：{}", chRelieveBatchRespDto);

        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByOrderId(Long.parseLong(chRelieveBatchRespDto.getBfBatchId()));
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryBySettleId(Long.parseLong(chRelieveBatchRespDto.getBfBatchId()));

        if (chRelieveBatchRespDto.getCgwRelieveRespDto().getFlag() == 1) {
            log.info("call  收汇订单:{},解付申请,渠道第二次通知,解付成功.", chRelieveBatchRespDto.getBfBatchId());
            fiCbPaySettleDo.setRelieveAt(DateUtil.getCurrentDate());
            fiCbPaySettleDo.setIncomeStatus(InComeStatusEnum.COMPELTED_INCOME.getCode());
            //发送邮件通知清算
            sendEmailToCm(fiCbPaySettleDo.getIncomeNo(), fiCbPaySettleApplyDo.getIncomeAccount(),
                    fiCbPaySettleDo.getIncomeCcy(), fiCbPaySettleDo.getIncomeAmt());
        } else {
            log.info("call  收汇订单:{},解付申请,渠道第二次通知,解付失败原因:{}。", chRelieveBatchRespDto.getBfBatchId(),
                    chRelieveBatchRespDto.getCgwRelieveRespDto().getErrorMsg());
            fiCbPaySettleDo.setIncomeStatus(InComeStatusEnum.FAIL.getCode());
        }

        cbPaySettleManager.modify(fiCbPaySettleDo);
    }

    /**
     * 收汇补录接口
     *
     * @param collectionMakeupBo 收汇补录参数
     */
    @Override
    public void collectionMakeup(CollectionMakeupBo collectionMakeupBo) {

        FiCbPaySettleDo fiCbPaySettleDo = cbPaySettleManager.queryByIncomeNo(collectionMakeupBo.getIncomeNo());
        if (fiCbPaySettleDo != null) {
            log.info("call 渠道补录收汇TT编号:{}存在。", collectionMakeupBo.getIncomeNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00104);
        }

        CgwReceiptRepairReqDto cgwReceiptRepairReqDto = CbPaySettleBoConvert.toCgwReceiptRepairReqDto(collectionMakeupBo);
        log.info("call 渠道补录收汇接口请求参数:{}", cgwReceiptRepairReqDto);
        CgwBaseRespDto cgwBaseRespDto = cgwCbPayReqFacade.receiptRepair(cgwReceiptRepairReqDto);
        log.info("call 渠道补录收汇接口返回参数:{}", cgwBaseRespDto);

        if (cgwBaseRespDto.getCode() != 1) {
            log.info("call 渠道补录收汇接口返回失败，失败原因:{}", cgwBaseRespDto.getRetMsg());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00138, cgwBaseRespDto.getRetMsg());
        }

    }

    /**
     * 发送结汇结果通知
     *
     * @param memberId      商户号
     * @param incomeNo      汇款流水号
     * @param settleOrderId 结汇订单ID
     * @param searchType    查询类型
     */
    @Override
    public void sendSettleNotify(Long memberId, String incomeNo, Long settleOrderId, String searchType) {


        SettleQueryReqParamBo settleQueryReqParamBo = CbPaySettleConvert.toSettleQueryReqParamBo(memberId, incomeNo,
                settleOrderId, searchType);
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SETTLEMENT_ASYNC_NOTIFY_QUEUE_NAME, settleQueryReqParamBo);
    }

    /**
     * 发送邮件
     *
     * @param ttNo          汇入编号
     * @param inComeAccount 入账账户（宝付备付金）
     * @param ccy           汇入币种
     * @param amount        汇入金额
     */
    private void sendEmailToCm(String ttNo, String inComeAccount, String ccy, BigDecimal amount) {

        RechargeReqDto rechargeReqDto = new RechargeReqDto();
        DecimalFormat df = new DecimalFormat("#,###.000");
        String content = Constants.NOTICE_CM_CONTENT.replace("${ttNo}", String.valueOf(ttNo))
                .replace("${inComeAccount}", inComeAccount)
                .replace("${amount}", df.format(amount))
                .replace("${ccy}", ccy);

        List<String> accountList = Splitter.on("|!").splitToList(mailAddressTO);
        List<String> mailAddressCCList = Splitter.on("|!").splitToList(mailAddressCC);
        log.info("call 发送邮件，发送邮件请求参数:{}", rechargeReqDto);

        SendEmailServiceBo sendEmailServiceBo = new SendEmailServiceBo();
        sendEmailServiceBo.setSubject(Constants.NOTICE_CM_TITLE);
        sendEmailServiceBo.setMailContent(content);
        sendEmailServiceBo.setMailAddressTO(accountList);
        sendEmailServiceBo.setMailAddressCC(mailAddressCCList);
        sendEmailServiceBo.setUserName(sendUserName);
        sendEmailServiceBo.setUserPassword(sendUserPassword);
        sendEmailServiceBo.setEmailHosts(emailHost);

        log.info("call 发送邮件，消息队列名称:{},发送内容:{}", MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_SEND_EMAIL_QUEUE_NAME, sendEmailServiceBo);
    }

    /**
     * 获取全部结汇完成和部分结汇完成状态
     *
     * @param fileBatchNo 批次号
     * @return 结汇状态
     */
    private int getSettleStatus(Long fileBatchNo) {

        int statusCode = 0;
        FiCbPayFileUploadDo fiCbPayFileUploadDos = fiCbpayFileUploadMapper.queryByBatchId(fileBatchNo);
        if (UploadFileStatus.AML_PART_SUCCESS.getCode().equals(fiCbPayFileUploadDos.getStatus())) {
            statusCode = SettleStatusEnum.PART_TRUE.getCode();
        } else if (UploadFileStatus.AML_SUCCESS.getCode().equals(fiCbPayFileUploadDos.getStatus())) {
            statusCode = SettleStatusEnum.TURE.getCode();
        }

        return statusCode;
    }

    /**
     * 商户汇入申请基本信息校验
     *
     * @param settleIncomeApplyBo 汇入申请业务逻辑参数信息
     */
    @Override
    public void settleApplyValidate(SettleIncomeApplyBo settleIncomeApplyBo, Boolean isApiReq) {

        //判断订单是否存在
        FiCbPaySettleApplyDo fiCbPaySettleApplyDo;
        if (isApiReq) {
            fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryByParams(settleIncomeApplyBo.getMemberId(),
                    settleIncomeApplyBo.getIncomeNo());
        } else {
            fiCbPaySettleApplyDo = fiCbPaySettleApplyMapper.queryByIncomeNo(settleIncomeApplyBo.getMemberId(),
                    settleIncomeApplyBo.getIncomeNo());
        }
        if (fiCbPaySettleApplyDo != null) {
            log.error("商户号:{},汇入申请订单号:{},匹配状态:{}存在", settleIncomeApplyBo.getMemberId(),
                    settleIncomeApplyBo.getIncomeNo(), fiCbPaySettleApplyDo.getMatchingStatus());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00106);
        }
        //判断用户是否开通外币账户
        if (!CcyEnum.CNY.getKey().equals(settleIncomeApplyBo.getOrderCcy())) {
            cbPayCmBiz.queryForeignCurrencyAcc(settleIncomeApplyBo.getOrderCcy(), settleIncomeApplyBo.getMemberId());
        }
    }

    /**
     * API 外汇汇入申请异步请求
     *
     * @param settleIncomeApplyBo 外汇汇入申请参数
     * @param traceLogId          日志编号，由于使用异步形式需把日志ID传入
     */
    @Async
    @Override
    public void asynchronousRequestAPI(SettleIncomeApplyBo settleIncomeApplyBo, Long applyNo, String traceLogId) {
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("API外汇汇入申请订单信息异步校验，参数信息：{}", settleIncomeApplyBo);
            Long dfsId = settleFileVerifyProcess(CbPaySettleConvert.toSettleFileUploadReqBoTwo(settleIncomeApplyBo, applyNo));

            //如果校验有错误数据，直接异步返回通知商户
            if (dfsId != 1 && dfsId != 0) {
                String errorFileName = "SETTLE_APPLY_ERROR_" + settleIncomeApplyBo.getMemberId() + "_" + applyNo + ".txt";

                FiCbPaySettleApplyDo settleApplyDo = new FiCbPaySettleApplyDo();
                settleApplyDo.setOrderId(applyNo);
                settleApplyDo.setMatchingStatus(MemberSettleStatusEnum.FILE_PROCESS_FAIL.getCode());
                settleApplyDo.setRemarks(MemberSettleStatusEnum.FILE_PROCESS_FAIL.getDesc());
                //更新汇入申请订单状态为失败
                settleApplyManager.modifySettleApply(settleApplyDo);
                //上传商户FTP
                settleNotifyMemberBiz.uploadMemberFtp(settleIncomeApplyBo.getMemberId(), dfsId, errorFileName);
                SettleNotifyApplyBo settleNotifyApplyBo = SettleApplyConvert.paramConvert(settleIncomeApplyBo);
                //查询需要通知商户的信息
                SettleNotifyMemberBo settleNotifyMemberBo = settleQueryBiz.querySettleNotifyByApplyNo(settleIncomeApplyBo.getMemberId(), applyNo);
                settleNotifyMemberBo.setErrorFileName(errorFileName);
                settleNotifyMemberBo.setProcessStatus(MemberSettleStatusEnum.FILE_PROCESS_FAIL.getCode());
                log.error("结汇API 文件处理商户失败，通知商户明文参数信息：{}", settleNotifyMemberBo);
                //通知商户信息
                settleNotifyMemberBiz.httpNotify(settleNotifyMemberBo, settleNotifyApplyBo);
            } else {
                //收到API录入的汇入汇款申请后提示清算人员和结汇相关人员可以进行人工匹配
                settleEmailBiz.importApplyNotify(settleIncomeApplyBo.getMemberId(), settleIncomeApplyBo.getIncomeNo(),
                        settleIncomeApplyBo.getOrderAmt(),
                        settleIncomeApplyBo.getOrderCcy());
            }
        } catch (Exception e) {
            log.error("call 跨境结汇，(客户API发起)外汇汇入申请异常", e);
        } finally {
            redisManager.deleteObject(Constants.CBPAY_CREATE_SUM_FILE_KEY + settleIncomeApplyBo.getIncomeNo());
            log.info("商户{}外汇汇入申请结束，释放锁完成", settleIncomeApplyBo.getIncomeNo());
        }
    }

    /**
     * 运营线下上传结汇申请汇款凭证
     *
     * @param paymentFileUploadBo 汇款凭证信息对象
     */
    @Override
    public void uploadPaymentFile(SettlePaymentFileUploadBo paymentFileUploadBo) {
        FiCbPaySettleApplyDo settleApplyDo = CbPaySettleConvert.toFiCbPaySettleApplyDo(paymentFileUploadBo);
        ParamValidate.checkUpdate(fiCbPaySettleApplyMapper.updateByKeySelective(settleApplyDo), "汇款凭证上传状态不正确");
    }

}
