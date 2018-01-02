package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.SettleFileProcessBiz;
import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.util.FtpUtil;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserWithdrawFileUploadDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawOrderQueryDo;
import com.baofu.international.global.account.core.manager.UserWithdrawCashManager;
import com.baofu.international.global.account.core.manager.UserWithdrawOrderManager;
import com.baofu.international.global.account.core.manager.UserWithdrawRelationManager;
import com.google.common.io.Files;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 1、生成结汇申请文件
 * </p>
 * User: 香克斯  Date: 2017/11/13 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Component
public class SettleFileProcessBizImpl implements SettleFileProcessBiz {

    /**
     * 转账关系管理
     */
    @Autowired
    private UserWithdrawRelationManager userWithdrawRelationManager;

    /**
     * 提现订单明细Manager服务
     */
    @Autowired
    private UserWithdrawOrderManager userWithdrawOrderManager;

    /**
     *
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 文件名称前缀
     */
    private static final String FILE_NAME = "DETAIL_";

    /**
     * 生成结汇申请文件
     *
     * @param sumBatchNo 汇总批次号
     * @return 返回生成的文件名称
     */
    @Override
    public String createSettleFile(Long sumBatchNo) {

        log.info("生成结汇申请文件，批次号：{}", sumBatchNo);
        try {
            //查询转账管理
            List<Long> longList = userWithdrawRelationManager.queryWithdrawIdByBatchNo(sumBatchNo);
            //创建文件头部信息
            StringBuilder totalStr = createFileHead();
            for (Long withdrawId : longList) {
                //查询提现文件信息，根据总的批次号查询总的
                List<UserWithdrawFileUploadDo> withdrawFileUploadDoList = userWithdrawCashManager.selectByBatch(String.valueOf(withdrawId));
                UserWithdrawFileUploadDo userWithdrawFileUploadDo = withdrawFileUploadDoList.get(0);
                //查询订单明细信息
                totalStr.append(createFileContext(userWithdrawFileUploadDo.getUserNo(), userWithdrawFileUploadDo.getFileBatchNo()));
            }
            String fileName = FILE_NAME + configDict.getSettleMemberId() + sumBatchNo + CommonDict.LOCAL_FILE_SUFFIX;
            String filePath = configDict.getWithdrawDownLoadPath() + Constants.SLASH;
            log.info("生成结汇申请文件,批次号：{},文件目录：{},文件名：{}", sumBatchNo, filePath, fileName);
            File file = new File(filePath + fileName);
            Files.write(totalStr.toString().getBytes(Constants.UTF8), file);
            return fileName;
        } catch (Exception e) {
            log.error("生成结汇申请文件异常：", e);
            throw new BizServiceException(CommonErrorCode.SYSTEM_INNER_ERROR, "生成结汇申请文件异常");
        }
    }

    /**
     * 结汇申请文件上传至FTP服务器中
     *
     * @param fileName 本地文件名称
     * @return 返回是否上传成功
     */
    @Override
    public Boolean uploadFtp(String fileName) {
        try {
            FtpUtil ftpUtil = FtpUtil.getInstant(configDict.getMemberFtpIp(), configDict.getMemberFtpPort(), 2,
                    configDict.getMemberFtpUserName(), configDict.getMemberFtpUserPass());
            String filePathName = configDict.getWithdrawDownLoadPath() + Constants.SLASH + fileName;
            log.info("结汇申请文件上传至FTP服务器：{}，FTP用戶：{},FTP路徑：{}", filePathName, configDict.getMemberFtpUserName(),
                    configDict.getMemberFtpPath());
            byte[] bytes = Files.toByteArray(new File(filePathName));

            return ftpUtil.uploadFile(configDict.getMemberFtpPath(), fileName, bytes, Constants.UTF8);
        } catch (Exception e) {
            log.error("结汇申请文件上传FTP异常:", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 查询提现订单明细并组装文件
     *
     * @param userNo      用户编号
     * @param fileBatchNO 文件编号
     * @return 返回拼接的字符串信息
     */
    private StringBuilder createFileContext(Long userNo, Long fileBatchNO) {
        //查询订单明细信息
        List<UserWithdrawOrderQueryDo> userWithdrawOrderQueryDoList = userWithdrawOrderManager.queryOrderByFileBatchNo(
                userNo, fileBatchNO);
        StringBuilder stringBuilder = new StringBuilder();
        for (UserWithdrawOrderQueryDo userWithdrawOrderQueryDo : userWithdrawOrderQueryDoList) {
            stringBuilder.append(userWithdrawOrderQueryDo.getOrderId()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getPayeeIdType()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(SecurityUtil.desDecrypt(userWithdrawOrderQueryDo.getPayeeIdNo(), Constants.CARD_DES_KEY)).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getPayeeName()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(SecurityUtil.desDecrypt(userWithdrawOrderQueryDo.getPayeeAccNo(), Constants.CARD_DES_KEY)).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(DateUtil.format(userWithdrawOrderQueryDo.getTradeAt(), DateUtil.settlePattern)).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getOrderCcy()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append((userWithdrawOrderQueryDo.getOrderAmt().setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN))).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getCommodityName()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getCommodityPrice()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(userWithdrawOrderQueryDo.getCommodityAmount()).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(CommonDict.SPLIT_FLAG).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(CommonDict.SPLIT_FLAG).append(CommonDict.SPLIT_FLAG);
            stringBuilder.append(CommonDict.SPLIT_FLAG).append(CommonDict.NEW_LINE);
        }
        return stringBuilder;
    }

    /**
     * 创建文件头部信息
     *
     * @return 返回文件头字符串
     */
    private StringBuilder createFileHead() {

        StringBuilder fileHeadStr = new StringBuilder();
        fileHeadStr.append("\t\t\t\t\t\t").append("宝付结汇交易订单明细表").append(CommonDict.NEW_LINE);
        fileHeadStr.append("商户号*（*为必填项）$|$商户名称*$|$版本").append(CommonDict.NEW_LINE);
        fileHeadStr.append(configDict.getSettleMemberId()).append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append(configDict.getSettleMemberName()).append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append(CommonDict.SPLIT_FLAG).append(CommonDict.NEW_LINE);
        fileHeadStr.append("商户订单号*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收款人证件类型*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收款人证件号码*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收款人名称*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收款人账号*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("交易时间*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("交易币种*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("交易金额*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("商品名称*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("商品数量*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("商品单价*").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("快递公司编码").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("运单号").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收货人姓名").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收货人联系方式").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("收货人地址").append(CommonDict.SPLIT_FLAG);
        fileHeadStr.append("发货日期").append(CommonDict.SPLIT_FLAG).append(CommonDict.NEW_LINE);
        return fileHeadStr;
    }
}
