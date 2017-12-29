package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.RemittanceApiQueryBiz;
import com.baofu.cbpayservice.biz.SettleNotifyMemberBiz;
import com.baofu.cbpayservice.biz.convert.RemittanceApiQueryBizConvert;
import com.baofu.cbpayservice.biz.models.RemitDetailsQueryResultBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryBo;
import com.baofu.cbpayservice.biz.models.RemitOrderApiQueryResultBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.common.util.other.FormatUtil;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.baofu.cbpayservice.common.constants.NumberConstants.ZERO;

/**
 * API汇款信息查询服务
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class RemittanceApiQueryBizImpl implements RemittanceApiQueryBiz {

    /**
     * 跨境人民币汇款订单信息Manager
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 跨境人民币汇款订单信息Manager
     */
    @Autowired
    private FiCbPayMemberApiRqstManager apiRqstManager;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager settleBankManager;

    /**
     * 文件上传至商户FTP服务器
     */
    @Autowired
    private SettleNotifyMemberBiz settleNotifyMemberBiz;


    /**
     * 跨境汇款结果查询
     *
     * @param queryBo 请求参数
     * @return 跨境汇款结果查询返回参数
     */
    @Override
    public RemitOrderApiQueryResultBo remittanceOrderQuery(RemitOrderApiQueryBo queryBo) {

        //汇款关系表查询
        FiCbPayMemberApiRqstDo fiCbpayMemberApiRqstDo = apiRqstManager.queryFiCbPayMemberApiRqst(
                queryBo.getMemberId(), queryBo.getBatchNo(), RemitBusinessTypeEnum.REMIT_APPLY.getCode());

        //查询汇款订单
        FiCbPayRemittanceDo remittanceDo = cbPayRemittanceManager.queryRemitByBatchNoMemberId(queryBo.getMemberId(),
                FormatUtil.toString(fiCbpayMemberApiRqstDo.getBusinessNo()));
        //汇款订单附加信息
        FiCbPayRemittanceAdditionDo additionDo = cbPayRemittanceManager.queryRemittanceAddition(String.valueOf(
                fiCbpayMemberApiRqstDo.getBusinessNo()), queryBo.getMemberId());
        //银行账户信息
        FiCbPaySettleBankDo settleBankDo = settleBankManager.selectBankAccByEntityNo(queryBo.getMemberId(),
                remittanceDo.getSystemCcy(), additionDo.getEntityNo());
        if (settleBankDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00129);
        }
        return RemittanceApiQueryBizConvert.toRemitOrderApiQueryResultBo(remittanceDo, settleBankDo,
                fiCbpayMemberApiRqstDo);
    }

    /**
     * API订单明细上传查询
     *
     * @param queryBo 请求参数
     * @return API订单明细上传查询 返回参数
     */
    @Override
    public RemitDetailsQueryResultBo orderDetailsUploadQuery(RemitOrderApiQueryBo queryBo) throws Exception {

        //汇款关系表查询
        FiCbPayMemberApiRqstDo fiCbpayMemberApiRqstDo = apiRqstManager.queryFiCbPayMemberApiRqst(
                queryBo.getMemberId(), queryBo.getBatchNo(), RemitBusinessTypeEnum.REMIT_FILE_UPLOAD.getCode());

        //查询文件上传信息
        FiCbPayFileUploadDo fiCbPayFileUploadDo = apiRqstManager.queryByApplyId(fiCbpayMemberApiRqstDo.getBusinessNo());

        //购汇失败,生成失败文件名并 把文件上传至商户FTP服务器中
        String errorFileName = null;
        if (null != fiCbPayFileUploadDo.getErrorFileId()
                && fiCbPayFileUploadDo.getErrorFileId() > ZERO) {
            errorFileName = "REMIT_ERROR_" + fiCbpayMemberApiRqstDo.getMemberId() + "_"
                    + fiCbPayFileUploadDo.getErrorFileId() + "_" + System.currentTimeMillis() + ".txt";
            settleNotifyMemberBiz.uploadMemberFtp(fiCbpayMemberApiRqstDo.getMemberId(),
                    fiCbPayFileUploadDo.getErrorFileId(), errorFileName);

        }

        return RemittanceApiQueryBizConvert.toRemitDetailsQueryResultBo(fiCbPayFileUploadDo,
                FormatUtil.toString(fiCbpayMemberApiRqstDo.getMemberReqId()), errorFileName,
                fiCbpayMemberApiRqstDo.getTerminalId());
    }
}
