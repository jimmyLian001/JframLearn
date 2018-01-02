package com.baofu.international.global.account.core.service.impl;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.models.SkyeePaymentDetailBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.baofu.international.global.account.core.common.util.FileCharsetDetectorUtil;
import com.baofu.international.global.account.core.facade.AccountPaymentDetailFacade;
import com.google.common.base.Splitter;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 1、Skyee账户收支明细导入
 * </p>
 * ProjectName : global-account-core-parent
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/26
 */
@Slf4j
@Service
public class AccountPaymentDetailFacadeImpl implements AccountPaymentDetailFacade {

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;
    /**
     * Mq发送服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * Skyee账户收支明细导入
     *
     * @param dfsId      文件编号，由业务后台上传至DFS之后产生的编号
     * @param fileName   文件名称
     * @param traceLogId 日志编号
     * @return 返回受理结果
     */
    @Override
    public Result<Boolean> skyeeAccPaymentDetailImport(Long dfsId, String fileName, String traceLogId) {

        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("Skyee账户收支明细结果导入,请求参数DFS编号：{}", dfsId);
            QueryReqDTO queryReqDTO = new QueryReqDTO();
            queryReqDTO.setFileId(dfsId);
            String filePath = configDict.getSystemTempPath() + File.separator + fileName;
            DfsClient.download(queryReqDTO, configDict.getSystemTempPath());
            String fileEncoding = FileCharsetDetectorUtil.getFileEncoding(new File(filePath));
            log.info("call 当前上传的文件编码格式为：{}", fileEncoding);
            List<String> linesList = IOUtils.readLines(new FileInputStream(new File(filePath)), fileEncoding);
            if (linesList.isEmpty() || linesList.size() < NumberDict.TWO) {
                log.error("call 上传收支明细数据为空");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290024);
            }
            linesList.remove(linesList.get(0));
            for (String str : linesList) {
                List<String> splitStr = Splitter.on(Constants.SPLIT_SYMBOL).splitToList(str);
                SkyeePaymentDetailBo skyeePaymentDetailBo = new SkyeePaymentDetailBo();
                skyeePaymentDetailBo.setBankCardNo(splitStr.get(NumberDict.FIVE).trim());
                skyeePaymentDetailBo.setCcy(splitStr.get(NumberDict.ONE).trim());
                skyeePaymentDetailBo.setOrderAmt(new BigDecimal(splitStr.get(NumberDict.TWO).trim()));
                skyeePaymentDetailBo.setOrderDate(DateUtil.parse(splitStr.get(NumberDict.THREE).trim(), DateUtil.smallDateFormat));
                skyeePaymentDetailBo.setOrderId(splitStr.get(NumberDict.ZERO).trim());
                skyeePaymentDetailBo.setSellerId(splitStr.get(NumberDict.FOUR).trim());
                log.info("Skyee收支明细发送MQ消息，消息内容：{}", skyeePaymentDetailBo);
                mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_SKYEE_ACC_DETAIL_QUEUE_NAME, skyeePaymentDetailBo);
            }
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Skyee账户收支明细结果导入受理异常：{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("Skyee账户收支明细结果导入受理结果：{}", result);
        return result;
    }
}
