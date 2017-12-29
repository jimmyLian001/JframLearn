package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.CbPayVerifyCountDo;
import org.apache.ibatis.annotations.Param;

/**
 * 实名认证
 * <p>
 * Created by 莫小阳 on 2017/5/31.
 */
public interface FiCbPayVerifyCountMapper {

    /**
     * 将实名认证信息插入数据库中
     *
     * @param cbPayVerifyCount 实名认证信息
     * @return 插入结果
     */
    int insert(CbPayVerifyCountDo cbPayVerifyCount);

    /**
     * @param fileBathNo 文件批次号
     * @return 结果
     */
    CbPayVerifyCountDo selectVerifyCounByFileBatchNo(@Param("fileBathNo") Long fileBathNo);

    /**
     * 根据文件批次号更新实名认证信息
     *
     * @param cbPayVerifyCountDo 实名认证信息
     * @return 跟新结果
     */
    int updateVerifyCount(CbPayVerifyCountDo cbPayVerifyCountDo);
}
