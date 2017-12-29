package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayBatchFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;

import java.util.List;

/**
 * 代理报关manager层服务
 * <p>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
public interface ProxyCustomsManager {

    /**
     * 更新文件批次状态
     *
     * @param fiCbpayFileUploadDo 更新文件批次对象
     */
    void updateFilestatus(FiCbPayFileUploadDo fiCbpayFileUploadDo);

    /**
     * 插入文件批次
     *
     * @param fiCbpayFileUploadDo 文件批次对象
     */
    Long insert(FiCbPayFileUploadDo fiCbpayFileUploadDo);

    /**
     * 批次号查询文件批次
     *
     * @param batchId 批次号ID
     */
    FiCbPayFileUploadDo queryByBatchId(Long batchId);

    /**
     * 批量更新文件状态
     *
     * @param fiCbPayBatchFileUploadDo 请求参数
     */
    void batchUpdateFileStatus(FiCbPayBatchFileUploadDo fiCbPayBatchFileUploadDo);

    /**
     * 查询文件批次信息
     *
     * @param batchNo 汇款批次号
     * @return 文件批次信息
     */
    List<FiCbPayFileUploadDo> queryByBatchNo(String batchNo);

    /**
     * 查询文件批次包含币种数
     *
     * @param batchFileIdList 文件批次id集合
     */
    List<String> queryAmlCcy(List<Long> batchFileIdList);

    /**
     *  根据结汇申请ID查询文件批次
     *
     * @param appNo  宝付内部结汇申请ID
     * @ 结果
     */
    FiCbPayFileUploadDo queryByBatchNoByAppNo(String appNo);
}

