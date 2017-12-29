package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayMemberApiRqstMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 购付汇API商户请求Manager服务实现
 * <p>
 * 1、查询渠道成本配置信息
 * </p>
 * User: 莫小阳 Date:2017/09/28 ProjectName: asias-icpservice Version: 1.0
 */
@Slf4j
@Repository
public class FiCbPayMemberApiRqstManagerImpl implements FiCbPayMemberApiRqstManager {

    /**
     * 数据操作类
     */
    @Autowired
    private FiCbPayMemberApiRqstMapper fiCbPayMemberApiRqstMapper;

    /**
     * 文件批次操作服务
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    /**
     * 保存商户请求信息
     *
     * @param fiCbPayMemberApiRqstDo 商户请求参数
     */
    @Override
    public void addFiCbPayMemberApiRqst(FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo) {
        ParamValidate.checkUpdate(fiCbPayMemberApiRqstMapper.insert(fiCbPayMemberApiRqstDo),
                "购付汇API商户请求信息新增异常");
    }

    /**
     * 根据申请单号查询申请记录数
     *
     * @param remitApplyNo 商户申请单号  商户保证唯一
     * @param memberId     商户号
     * @return 结果
     */
    @Override
    public Integer countByMemberReqId(Long memberId, String remitApplyNo) {
        return fiCbPayMemberApiRqstMapper.countByMemberReqId(memberId, remitApplyNo);
    }

    /**
     * @param remitApplyNo 商户汇款申请编号
     * @param proxyOrderId 汇款批次号
     */
    @Override
    public void updateApiRqstInfoByReqNo(String remitApplyNo, Long proxyOrderId) {
        ParamValidate.checkUpdate(fiCbPayMemberApiRqstMapper.updateApiRqstInfoByReqNo(remitApplyNo, proxyOrderId),
                "更新API商户请求关系信息表异常");
    }

    /**
     * 根据业务编号查询商户请求信息
     *
     * @param bfBatchId 请求对应业务编号
     * @return
     */
    @Override
    public FiCbPayMemberApiRqstDo queryReqInfoByBussNo(String bfBatchId) {
        return fiCbPayMemberApiRqstMapper.selectByKey(Long.parseLong(bfBatchId));
    }

    /**
     * @param memberReqId  商户请求Id
     * @param two          0-待回执、1-已回执、2-已通知
     * @param businessType 业务类型
     */
    @Override
    public void updateApiRqstInfoStatusByReqNo(String memberReqId, int two, String businessType) {
        ParamValidate.checkUpdate(fiCbPayMemberApiRqstMapper.updateApiRqstInfoStatusByReqNo(memberReqId, two, businessType),
                "更新API商户请求通知状态异常");
    }

    /**
     * 根据商户号和商户申请流水号查询
     *
     * @param memberId     商户号
     * @param memberReqId  商户申请流水号
     * @param businessType 业务类型
     * @return 汇款关系
     */
    @Override
    public FiCbPayMemberApiRqstDo queryFiCbPayMemberApiRqst(Long memberId, String memberReqId, String businessType) {

        log.info("call 根据商户号:{},商户申请流水号:{}查询", memberId, memberReqId);
        FiCbPayMemberApiRqstDo apiRqstDo = fiCbPayMemberApiRqstMapper.queryByMIdReqId(memberId, memberReqId, businessType);
        log.info("call 根据商户号、商户申请流水号查询返回信息：{}", apiRqstDo);
        if (apiRqstDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00163);
        }
        return apiRqstDo;
    }

    /**
     * 根据批次号查询代理报关文件表
     *
     * @param batchNo 批次号
     * @return j结果
     */
    @Override
    public FiCbPayFileUploadDo queryByApplyId(Long batchNo) {

        log.info("call 批次号:{}查询", batchNo);
        FiCbPayFileUploadDo fiCbPayFileUploadDo = fiCbpayFileUploadMapper.queryByBatchId(batchNo);

        log.info("call 批次号查询返回信息：{}", fiCbPayFileUploadDo);

        if (fiCbPayFileUploadDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00139);
        }

        return fiCbPayFileUploadDo;
    }
}
