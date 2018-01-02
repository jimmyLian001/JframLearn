package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserWithdrawFileUploadDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWithdrawFileUploadMapper {

    /**
     * 保存上传数据
     *
     * @param record 上传数据
     * @return 上传结果
     */
    int insert(UserWithdrawFileUploadDo record);

    /**
     * 根据订单批次号更新数据
     *
     * @param fileUploadDo 文件信息
     * @return 影响行数
     */
    int updateFileByBatch(UserWithdrawFileUploadDo fileUploadDo);

    /**
     * 更新代理报关文件表
     *
     * @param record 文件批次信息
     * @return 执行结果
     */
    int updateByPrimaryKeySelective(UserWithdrawFileUploadDo record);

    /**
     * 根据汇款批次号查询代理报关文件表
     *
     * @param batchNo 批次号
     * @return 文件批次集合
     */
    List<UserWithdrawFileUploadDo> queryByBatchNo(@Param("batchNo") String batchNo);

}