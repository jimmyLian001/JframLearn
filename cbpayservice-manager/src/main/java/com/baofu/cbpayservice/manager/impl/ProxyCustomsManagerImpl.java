package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayBatchFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 代理报关manager层服务
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Repository
public class ProxyCustomsManagerImpl implements ProxyCustomsManager {

    /**
     * 操作代理上传文件
     */
    @Autowired
    private FiCbpayFileUploadMapper fiCbpayFileUploadMapper;

    /**
     * 插入文件批次
     *
     * @param fiCbPayFileUploadDo 文件批次对象
     * @return 文件批次号
     */
    @Override
    public Long insert(FiCbPayFileUploadDo fiCbPayFileUploadDo) {

        log.info("call 文件批次插入：{}", fiCbPayFileUploadDo);
        ParamValidate.checkUpdate(fiCbpayFileUploadMapper.insert(fiCbPayFileUploadDo));

        return fiCbPayFileUploadDo.getId();
    }

    /**
     * 查询文件批次信息
     *
     * @param batchId 批次号ID
     * @return 文件批次信息
     */
    @Override
    public FiCbPayFileUploadDo queryByBatchId(Long batchId) {

        log.info("call 查询文件批次，参数batchId:{}", batchId);
        FiCbPayFileUploadDo fiCbpayFileUploadDo = fiCbpayFileUploadMapper.queryByBatchId(batchId);

        if (fiCbpayFileUploadDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0094);
        }

        return fiCbpayFileUploadDo;
    }

    /**
     * 更新文件批次状态
     *
     * @param fiCbPayFileUploadDo 更新文件批次对象
     */
    @Override
    public void updateFilestatus(FiCbPayFileUploadDo fiCbPayFileUploadDo) {

        log.info("call 更新文件批次状态:{}", fiCbPayFileUploadDo);
        ParamValidate.checkUpdate(fiCbpayFileUploadMapper.updateByPrimaryKeySelective(fiCbPayFileUploadDo));
    }

    /**
     * 批量更新文件状态
     *
     * @param fiCbPayBatchFileUploadDo 请求参数
     */
    @Override
    public void batchUpdateFileStatus(FiCbPayBatchFileUploadDo fiCbPayBatchFileUploadDo) {
        fiCbpayFileUploadMapper.batchUpdateFileStatus(fiCbPayBatchFileUploadDo);
    }

    /**
     * 查询文件批次信息
     *
     * @param batchNo 汇款批次号
     * @return 文件批次信息
     */
    @Override
    public List<FiCbPayFileUploadDo> queryByBatchNo(String batchNo) {

        List<FiCbPayFileUploadDo> fiCbpayFileUploadDoList = fiCbpayFileUploadMapper.queryByBatchNo(batchNo);
        if (CollectionUtils.isEmpty(fiCbpayFileUploadDoList)) {
            log.info("call 查询文件批次信息为空：{}", batchNo);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0094);
        }

        return fiCbpayFileUploadDoList;
    }

    /**
     * 查询文件批次包含币种数
     *
     * @param batchFileIdList 文件批次id集合
     */
    @Override
    public List<String> queryAmlCcy(List<Long> batchFileIdList) {
        return fiCbpayFileUploadMapper.queryAmlCcy(batchFileIdList);
    }

    /**
     * 根据结汇申请ID查询文件批次
     *
     * @param appNo 宝付内部结汇申请ID
     * @ 结果
     */
    @Override
    public FiCbPayFileUploadDo queryByBatchNoByAppNo(String appNo) {
        return fiCbpayFileUploadMapper.queryByBatchNoByAppNo(appNo);
    }

}
