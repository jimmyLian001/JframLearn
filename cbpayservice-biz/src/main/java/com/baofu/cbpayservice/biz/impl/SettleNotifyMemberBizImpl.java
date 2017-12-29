package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheTerminalDto;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.ps.http.HttpFormParameter;
import com.baofoo.ps.http.HttpMethod;
import com.baofoo.ps.http.HttpSendModel;
import com.baofoo.ps.http.SimpleHttpResponse;
import com.baofoo.ps.http.util.HttpUtil;
import com.baofu.cbpayservice.biz.SettleNotifyMemberBiz;
import com.baofu.cbpayservice.biz.models.SettleNotifyApplyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyBo;
import com.baofu.cbpayservice.biz.models.SettleNotifyMemberBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.util.FTPUtil;
import com.baofu.cbpayservice.common.util.other.SecurityUtil;
import com.baofu.cbpayservice.common.util.rsa.RsaCodingUtil;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.baofu.cbpayservice.manager.FiCbpayMemberFtpManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2017/7/25 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class SettleNotifyMemberBizImpl implements SettleNotifyMemberBiz {

    /**
     * 商户FTP服务
     */
    @Autowired
    private FiCbpayMemberFtpManager fiCbpayMemberFtpManager;

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cacheManager;

    /**
     * 通知商户
     *
     * @param settleNotifyBo 通知参数信息
     */
    @Override
    public void httpNotify(SettleNotifyMemberBo settleNotifyBo, SettleNotifyApplyBo settleNotifyApplyBo) {

        // 准备通知商户前参数准备
        HttpSendModel httpSendModel = new HttpSendModel(settleNotifyApplyBo.getNotifyUrl());
        List<HttpFormParameter> params = new ArrayList<>();
        log.info("跨境收款通知商户，参数信息：{}", settleNotifyBo);
        //终端校验
        CacheTerminalDto terminal = cacheManager.getTerminal(settleNotifyApplyBo.getTerminalId());
        String encrypt = RsaCodingUtil.encryptByPrivateKey(JsonUtil.toJSONString(settleNotifyBo), terminal.getPrivateKey());
        params.add(new HttpFormParameter("version", "1.0.0"));
        params.add(new HttpFormParameter("dataType", "JSON"));
        params.add(new HttpFormParameter("result", encrypt));
        params.add(new HttpFormParameter("memberId", settleNotifyApplyBo.getMemberId().toString()));
        params.add(new HttpFormParameter("terminalId", settleNotifyApplyBo.getTerminalId().toString()));
        httpSendModel.setMethod(HttpMethod.POST);
        httpSendModel.setParams(params);
        log.info("结汇业务异步通知商户:{},请求地址：{},参数：{}", settleNotifyApplyBo.getMemberId(), settleNotifyApplyBo.getNotifyUrl(),
                httpSendModel.toString());
        // 通知商户
        SimpleHttpResponse response = HttpUtil.doRequest(httpSendModel, Constants.UTF8);
        log.info("结汇异步通知返回结果信息：statusCode:{},entityString:{},errorMessage:{}", response.getStatusCode(),
                response.getEntityString(), response.getErrorMessage());
        String notifyResult = StringUtils.trim(response.getEntityString());
        if (!response.isRequestSuccess() || !"OK".equalsIgnoreCase(notifyResult)) {
            log.error("远程服务调用返回失败 Exception{}:", response.getErrorMessage());
        }
    }


    /**
     * 文件处理失败时失败文件上传至商户FTP服务器中
     *
     * @param memberId 商户编号
     * @param dfsId    DFS编号
     */
    @Override
    public void uploadMemberFtp(Long memberId, Long dfsId, String fileName) throws Exception {

        log.info("上传商户FTP信息,商户编号:{},DFS编号：{},文件名称：{}", memberId, dfsId, fileName);
        if (dfsId == null) {
            return;
        }
        FiCbpayMemberFtpDo memberFtpDo = fiCbpayMemberFtpManager.searchByMemberId(memberId);
        if (memberFtpDo == null) {
            log.error("商户FTP信息为空,未配置，商户编号:{}", memberId);
            return;
        }
        //根据FileId下载文件到本地
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(dfsId);
        byte[] content = DfsClient.downloadByte(reqDTO);
        boolean boo = FTPUtil.getInstant(memberFtpDo.getFtpIp(), memberFtpDo.getFtpPort(), memberFtpDo.getFtpMode(),
                memberFtpDo.getUserName(), SecurityUtil.desDecrypt(memberFtpDo.getUserPwd(), Constants.DES_PASSWD), Boolean.TRUE).
                uploadFile(memberFtpDo.getFtpPath(), fileName, content, Constants.UTF8);
        if (!boo) {
            log.error("上传文件至商户FTP异常，商户号:{}", memberId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00166);
        }
    }
}
