package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayBatchFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文件批次处理Mapper
 */
public interface FiCbpayFileUploadMapper {

    /**
     * 插入代理报关文件表
     *
     * @param record 文件批次信息
     * @return 执行结果
     */
    int insert(FiCbPayFileUploadDo record);

    /**
     * 更新代理报关文件表
     *
     * @param record 文件批次信息
     * @return 执行结果
     */
    int updateByPrimaryKeySelective(FiCbPayFileUploadDo record);

    /**
     * 根据批次号查询代理报关文件表
     *
     * @param batchId 批次号
     * @return FiCbPayFileUploadDo
     */
    FiCbPayFileUploadDo queryByBatchId(Long batchId);

    /**
     * 根据汇款批次号查询代理报关文件表
     *
     * @param orderId queryByApplyId
     * @return FiCbPayFileUploadDo
     */
    FiCbPayFileUploadDo queryByApplyId(String orderId);

    /**
     * 批量更新文件状态
     *
     * @param fiCbPayBatchFileUploadDo 请求参数
     */
    void batchUpdateFileStatus(FiCbPayBatchFileUploadDo fiCbPayBatchFileUploadDo);

    /**
     * 根据汇款批次号查询代理报关文件表
     *
     * @param batchNo 批次号
     * @return 文件批次集合
     */
    List<FiCbPayFileUploadDo> queryByBatchNo(@Param("batchNo") String batchNo);

    /**
     * 查询文件批次包含币种数
     *
     * @param batchFileIdList 文件批次id集合
     * @return 币种信息
     */
    List<String> queryAmlCcy(@Param("batchFileIdList") List<Long> batchFileIdList);

    /**
     * 根据结汇申请ID查询文件批次
     *
     * @param appNo 结汇申请ID
     * @return 结果
     */
    FiCbPayFileUploadDo queryByBatchNoByAppNo(@Param("appNo") String appNo);

    /**
     * 根据订单批次号更新数据
     *
     * @param fileUploadDo 文件信息
     * @return 影响行数
     */
    int updateFileBybatch(FiCbPayFileUploadDo fileUploadDo);

    /**
     * 根据文件批次号统计总金额
     *
     * @param list 文件批次号
     * @return 结果
     */
    BigDecimal sumAllOrderAmt(@Param("list") List<String> list);

    /**
     * @param list 文件批次号
     * @return 结果
     */
    int countFileByFileBatchNo(@Param("list") List<String> list);
}