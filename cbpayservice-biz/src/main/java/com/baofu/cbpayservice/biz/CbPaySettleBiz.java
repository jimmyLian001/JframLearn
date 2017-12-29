package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRelieveResultDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwSettleResultDto;
import com.baofu.cbpayservice.biz.models.*;

/**
 * 结汇操作业务逻辑实现接口
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPaySettleBiz {

    /**
     * 收到银行外汇汇款到账通知处理
     * 1、商户外币汇入编号 + 渠道编号 确定是否存在
     * 2、判断参数合法性
     * 3、创建结汇订单
     *
     * @param sMTAListenerBo 商户汇款到宝付备付金账户通知对象
     */
    void settleMoneyToAccount(SettleMoneyToAccountListenerBo sMTAListenerBo);

    /**
     * 发送结汇申请确认MQ
     *
     * @param orderId 宝付订单号
     */
    void sendSettleConfirmMq(Long orderId);

    /**
     * 结汇申请确认
     *
     * @param cbPaySettleConfirmReqBo 结汇申请确认对象
     */
    void settleConfirm(CbPaySettleConfirmReqBo cbPaySettleConfirmReqBo);

    /**
     * 结汇上传文件服务
     * 由商户前台导入excel发起
     *
     * @param settleFileUploadReqBo 请求参数
     * @return 批次ID
     */
    Long settleFileUpload(SettleFileUploadReqBo settleFileUploadReqBo);

    /**
     * 结汇上传文件处理服务
     *
     * @param settleFileUploadReqBo 请求参数
     * @throws Exception 异常
     */
    void settleFileProcess(SettleFileUploadReqBo settleFileUploadReqBo) throws Exception;

    /**
     * 结汇上传文件处理服务
     * 用于明细校验，数据不保存到数据库
     *
     * @param settleFileUploadReqBo 请求参数
     */
    Long settleFileVerifyProcess(SettleFileUploadReqBo settleFileUploadReqBo);

    /**
     * 外汇汇入申请(客户在商户前台发起)
     *
     * @param settleIncomeApplyBo 外汇汇入申请参数
     * @return 申请单号
     */
    Long settleIncomeApply(SettleIncomeApplyBo settleIncomeApplyBo);

    /**
     * 外汇汇入申请审核
     *
     * @param receiveAuditBo 审核对象
     */
    void receiveAudit(ReceiveAuditBo receiveAuditBo);

    /**
     * 运营设置匹配信息
     *
     * @param settleOperationSetReqBo 运营设置匹配信息
     */
    void operationSet(SettleOperationSetReqBo settleOperationSetReqBo);

    /**
     * 结汇申请发往渠道通知处理
     *
     * @param cgwBaserespDto 结汇申请回执对象
     */
    void settleFirstCallback(CgwBaseRespDto cgwBaserespDto);

    /**
     * 结汇申请回执结果处理
     *
     * @param cgwSettleResultRespDto 结汇申请回执对象
     */
    void settleSecondCallback(CgwSettleResultDto cgwSettleResultRespDto);

    /**
     * 手工重新清算
     *
     * @param orderId 宝付结汇订单号
     */
    void manualSettlement(Long orderId);

    /**
     * 商户汇入申请审核(后台运营人员操作)
     *
     * @param operateConfirmBo 运营设置匹配状态参数
     */
    void operateConfirm(OperateConfirmBo operateConfirmBo);

    /**
     * 解付申请
     *
     * @param solutionPayApplyMqBo 解付申请mq队列参数
     */
    void solutionPayApple(SolutionPayApplyMqBo solutionPayApplyMqBo);

    /**
     * 解付申请,渠道第二次通知
     *
     * @param chRelieveBatchRespDto 银行通知对象
     */
    void releve(CgwRelieveResultDto chRelieveBatchRespDto);

    /**
     * 收汇补录接口
     *
     * @param collectionMakeupBo 收汇补录参数
     */
    void collectionMakeup(CollectionMakeupBo collectionMakeupBo);

    /**
     * 发送结汇结果通知
     *
     * @param memberId      商户号
     * @param incomeNo      汇款流水号
     * @param settleOrderId 结汇订单ID
     * @param searchType    查询类型
     */
    void sendSettleNotify(Long memberId, String incomeNo, Long settleOrderId, String searchType);

    /**
     * 商户汇入申请基本信息校验
     *
     * @param settleIncomeApplyBo 汇入申请业务逻辑参数信息
     */
    void settleApplyValidate(SettleIncomeApplyBo settleIncomeApplyBo, Boolean isApiReq);

    /**
     * API 外汇汇入申请异步请求
     *
     * @param settleIncomeApplyBo 外汇汇入申请参数
     * @param applyNo             汇入申请内部唯一单号
     */
    void asynchronousRequestAPI(SettleIncomeApplyBo settleIncomeApplyBo, Long applyNo ,String traceLogId);

    /**
     * 运营线下上传结汇申请汇款凭证
     * @param settlePaymentFileUploadBo 汇款凭证信息对象
     */
    void uploadPaymentFile(SettlePaymentFileUploadBo settlePaymentFileUploadBo);
}
