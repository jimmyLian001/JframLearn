package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.ConfigDict;
import com.baofu.international.global.account.client.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.client.common.util.Base64Util;
import com.baofu.international.global.account.client.common.util.rsa.RsaCodingUtil;
import com.baofu.international.global.account.client.common.util.rsa.RsaReadUtil;
import com.baofu.international.global.account.client.service.UserWithdrawApiService;
import com.baofu.international.global.account.client.service.models.UserWithdrawDistributeReqBo;
import com.baofu.international.global.account.client.web.models.BaseResult;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;

/**
 * 用户API操作
 * <p>
 * 1、用户API操作
 * </p>
 * User: feng_jiang  Date: 2017/11/6 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("common/")
public class GlobalAccountAPIController {

    /**
     * 用户结汇申请处理服务
     */
    @Autowired
    private UserWithdrawApiService userWithdrawApiService;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 描述：接收用户结汇申请反馈通知
     *
     * @param result     信息串
     * @param terminalId 终端ID
     * @return 返回Json字符串
     */
    @ResponseBody
    @RequestMapping(value = "distribute", produces = "application/json; charset=utf-8")
    public BaseResult distribute(String result, String terminalId) {
        BaseResult baseResult;
        try {
            String msgText = this.decryptByPubCerFile(result, terminalId);
            UserWithdrawDistributeReqBo withdrawDistributeVo = JsonUtil.toObject(msgText, UserWithdrawDistributeReqBo.class);
            userWithdrawApiService.dealUserSettleApply(withdrawDistributeVo);
            baseResult = BaseResult.setSuccess("用户结汇申请处理成功");
        } catch (Exception e) {
            log.error("call 用户结汇申请处理 Exception:{}", e);
            baseResult = BaseResult.setFail(processException(e));
        }
        log.info("call 用户结汇申请处理请求返回JSON信息：{}，", baseResult);
        return baseResult;
    }

    /**
     * 指定Cer公钥路径解密
     *
     * @param src        参数
     * @param terminalId 参数
     * @return decryptByPublicKey
     */
    private String decryptByPubCerFile(String src, String terminalId) {
        PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(configDict.getSettlePublicKeyPath());
        if (publicKey == null) {
            return null;
        }
        String decrypt = RsaCodingUtil.decryptByPublicKey(src, publicKey);
        if (StringUtils.isBlank(decrypt)) {
            log.error("密文解密失败 终端：{} PARAM:{}", terminalId, src);
        }
        return decrypt;
    }

    /**
     * 描述：接收内卡代付反馈通知
     * @param member_id     信息串
     * @param terminal_id     信息串
     * @param data_type     信息串
     * @param data_content     信息串
     * @return 返回xml字符串
     */
    @ResponseBody
    @RequestMapping(value = "withdraw", produces = "text/xml; charset=utf-8")
    public String agentWithdraw(String member_id, String terminal_id, String data_type, String data_content) {
        log.info("内卡返回代付结果数据：member_id" + member_id + ",terminal_id:" + terminal_id + ",data_content:" + data_content);
        try {
            String respDecrypt = RsaCodingUtil.decryptByPubCerFile(data_content, configDict.getSettlePublicKeyPath());
            if (StringUtils.isBlank(respDecrypt)) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800009, "代付API申请返回密文解密失败");
            }
            respDecrypt = Base64Util.decode(respDecrypt);
            log.info("请求代付API返回参数公钥解密之后信息:{}", respDecrypt);
            userWithdrawApiService.dealUserWithdraw(respDecrypt);
        } catch (Exception e) {
            log.error("call 接收内卡代付反馈通知处理异常 {}", e);
        }
        return "OK";
    }

    /**
     * 异常处理
     * Function Name :
     */
    private String processException(Throwable e) {
        String errMsg = "未知异常";
        if (e instanceof BizServiceException) {
            errMsg = ((BizServiceException) e).getExtraMsg();
        } else if (e instanceof Exception) {
            errMsg = e.getMessage();
        }
        return errMsg;
    }
}
