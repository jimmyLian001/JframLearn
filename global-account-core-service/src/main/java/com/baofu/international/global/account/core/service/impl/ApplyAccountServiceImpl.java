package com.baofu.international.global.account.core.service.impl;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwLookUserRespDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.ApplyAccountBiz;
import com.baofu.international.global.account.core.biz.ChannelNotifyApplyStatusBiz;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.models.ApplyAccountBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountInfoBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountReqBo;
import com.baofu.international.global.account.core.biz.models.ChannelNotifyApplyAccountBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.AccApplyStatusEnum;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.baofu.international.global.account.core.common.util.FileCharsetDetectorUtil;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountApplyDo;
import com.baofu.international.global.account.core.facade.ApplyAccountFacade;
import com.baofu.international.global.account.core.facade.model.ApplyAccountRepDto;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.baofu.international.global.account.core.manager.ApplyAccountManager;
import com.baofu.international.global.account.core.service.convert.ApplyAccountServiceConvert;
import com.google.common.base.Splitter;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Slf4j
@Service
public class ApplyAccountServiceImpl implements ApplyAccountFacade {

    /**
     * 申请收款账户业务逻辑
     */
    @Autowired
    private ApplyAccountBiz applyAccountBiz;

    /**
     * 用户申请开通收款账户信息相关Manager服务
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 发送MQ服務
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 渠道通知开户结果处理
     */
    @Autowired
    private ChannelNotifyApplyStatusBiz channelNotifyApplyStatusBiz;

    /**
     * 根据用户Id查询出当前的用户信息
     *
     * @param userNo     用户编号
     * @param traceLogId 日志ID
     * @param userType   用户类型
     * @return 返回结果
     */
    @Override
    public Result<List<ApplyAccountRepDto>> getApplyAccountData(Long userNo, int userType, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        Result<List<ApplyAccountRepDto>> result;
        try {
            log.info("查询用户资质信息，用户号:{},用户类型：{}", userNo, userType);
            List<ApplyAccountInfoBo> applyAccountInfoBo = applyAccountBiz.getApplyAccountPageInfo(userNo, userType);

            result = new Result<>(ApplyAccountServiceConvert.paramConvert(applyAccountInfoBo));
        } catch (Exception e) {
            log.error("查询用户资质信息异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询用户资质信息返回结果：{}", result);
        return result;
    }

    /**
     * 用户创建申请
     * 转换请求参数
     * 根据请求参数查询出客户的相关信息进行开户操作
     *
     * @param applyAccountReqDto reqDto
     * @return 返回结果
     */
    @Override
    public Result<Boolean> addApplyAccount(ApplyAccountReqDto applyAccountReqDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("开通收款账户请求参数信息:{}", applyAccountReqDto);
        try {
            ApplyAccountReqBo applyAccountReqBo = ApplyAccountServiceConvert.paramConvert(applyAccountReqDto);
            ApplyAccountBo applyAccountBo = applyAccountBiz.getApplyInfoByUserNo(applyAccountReqBo);
            log.info("开户请求参数：{}", applyAccountBo);
            applyAccountBiz.applyAccount(applyAccountBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("开通收款账户异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("开通收款账户返回结果：{}", result);
        return result;
    }

    /**
     * 资质审核成功之后开通收款账户
     *
     * @param qualifiedNo 资质编号
     * @param traceLogId  日志ID
     * @return 返回结果
     */
    @Override
    public Result<Boolean> applyAccountAuditor(Long qualifiedNo, String traceLogId) {
        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("资质审核成功之后开通收款账户:{}", qualifiedNo);
        try {

            List<ApplyAccountBo> applyAccountBoList = applyAccountBiz.getApplyInfo(qualifiedNo);
            log.info("控台审核成功-开户请求参数：{}", applyAccountBoList);
            if (!CollectionUtils.isEmpty(applyAccountBoList)) {
                for (ApplyAccountBo applyAccountBo : applyAccountBoList) {
                    applyAccountBiz.getCreateUserResult(applyAccountBo);
                }
                log.info("资质审核成功之后开通收款账户成功");
            }
            result = new Result<>(Boolean.TRUE);

        } catch (Exception e) {
            log.error("资质审核成功之后开通收款账户异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("资质审核成功之后开通收款账户返回结果：{}", result);
        return result;
    }

    /**
     * Skyee账户开通结果导入
     *
     * @param dfsId      文件编号，由业务后台上传至DFS之后产生的编号
     * @param fileName   文件名称
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    @Override
    public Result<Boolean> skyeeAccOpenResult(Long dfsId, String fileName, String traceLogId) {

        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Skyee账户开通结果导入,请求参数DFS编号：{}", dfsId);
            QueryReqDTO queryReqDTO = new QueryReqDTO();
            queryReqDTO.setFileId(dfsId);
            String filePath = configDict.getSystemTempPath() + File.separator + fileName;
            DfsClient.download(queryReqDTO, configDict.getSystemTempPath());
            String fileEncoding = FileCharsetDetectorUtil.getFileEncoding(new File(filePath));
            log.info("call 当前上传的文件编码格式为：{}", fileEncoding);
            List<String> linesList = IOUtils.readLines(new FileInputStream(new File(filePath)), fileEncoding);
            if (linesList.isEmpty() || linesList.size() < NumberDict.TWO) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290024);
            }
            linesList.remove(linesList.get(0));
            for (String str : linesList) {
                List<String> splitStr = Splitter.on(Constants.SPLIT_SYMBOL).splitToList(str);
                String applyNo = splitStr.get(NumberDict.ZERO).trim();
                UserPayeeAccountApplyDo userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByApplyId(Long.valueOf(applyNo));
                if (userPayeeAccountApplyDo != null && userPayeeAccountApplyDo.getStatus() == AccApplyStatusEnum.SUCCESS.getCode()) {
                    log.warn("用户：{},申请编号：{}已经开户成功，请勿重复操作");
                    continue;
                }
                String bankAccNo = splitStr.get(NumberDict.TWELVE).trim();
                String bicCode = splitStr.get(NumberDict.THIRTEEN).trim();
                if (StringUtils.isBlank(bankAccNo) || StringUtils.isBlank(bicCode)) {
                    log.warn("银行账户或BicCode为空，参数信息：{}", str);
                    continue;
                }
                CgwLookUserRespDto cgwLookUserRespDto = new CgwLookUserRespDto();
                cgwLookUserRespDto.setBfBatchId(applyNo);
                cgwLookUserRespDto.setBankAccountNumber(bankAccNo);
                cgwLookUserRespDto.setRoutingNumber(bicCode);
                cgwLookUserRespDto.setSuccFlag(NumberDict.ZERO);
                cgwLookUserRespDto.setCode(NumberDict.ONE);
                cgwLookUserRespDto.setMessage("开户成功");
                //账户名称
                cgwLookUserRespDto.setBankAccName(splitStr.get(NumberDict.ELEVEN).trim());
                log.info("call 账户名称：{}", splitStr.get(NumberDict.ELEVEN).trim());
                mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_CREATE_USER_QUEUE_NAME_TWO, cgwLookUserRespDto);

            }
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Skyee账户开通结果导入受理异常：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Skyee账户开通结果导入受理结果：{}", result);
        return result;
    }

    /**
     * Skyee账户开通开通之后更新成账户开通处理中
     *
     * @param applyNo    申请编号
     * @param status     开通状态 1为申请中，2为失败
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    @Override
    public Result<Boolean> skyeeAccOpenHandling(Long applyNo, int status, String traceLogId) {
        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Skyee账户开通开通之后更新成账户开通处理中,请求参数申请编号：{},处理状态：{}", applyNo, status);
            ChannelNotifyApplyAccountBo channelNotifyApplyAccountBo = new ChannelNotifyApplyAccountBo();
            channelNotifyApplyAccountBo.setApplyNo(applyNo);
            channelNotifyApplyAccountBo.setCode(status);
            channelNotifyApplyStatusBiz.addApplyAccountInfo(channelNotifyApplyAccountBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Skyee账户开通开通之后更新成账户开通处理中：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Skyee账户开通开通之后更新成账户开通受理结果：{}", result);
        return result;
    }

    /**
     * 查询用户已经申请开通的币种
     *
     * @param userNo     用户号
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    @Override
    public Result<List<String>> queryAccApplyCcy(Long userNo, String traceLogId) {
        Result<List<String>> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("查询用户已经申请开通的币种，用户号：{}", userNo);
            result = new Result<>(applyAccountManager.queryAccApplyCcyByUserNo(userNo));
        } catch (Exception e) {
            log.error("查询用户已经申请开通的币种异常：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询用户已经申请开通的币种返回结果：{}", result);
        return result;
    }
}
