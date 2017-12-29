package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayMemberRateDo;

import java.util.List;

public interface FiCbPayMemberRateMapper {

    /**
     * 根据查询数据库的记录
     *
     * @param recordId 记录数
     * @return FiCbPayMemberRateDo
     */
    FiCbPayMemberRateDo selectByRecordId(Long recordId);

    /**
     * 插入数据库记录
     *
     * @param record 记录数
     * @return 执行结果
     */
    int addMemberRateDo(FiCbPayMemberRateDo record);

    /**
     * 根据主键更新数据库记录
     *
     * @param record 记录数
     * @return 执行结果
     */
    int updateByRecordId(FiCbPayMemberRateDo record);

    /**
     * 获取商户浮动汇率bp
     *
     * @param fiCbPayMemberRateDo 搜索参数
     * @return 返回结果
     */
    FiCbPayMemberRateDo selectMemberRateOne(FiCbPayMemberRateDo fiCbPayMemberRateDo);

    /**
     * 根据会员id和币种查询汇率列表
     *
     * @param fiCbPayMemberRateDo 汇率查询参数
     * @return 汇率列表
     */
    List<FiCbPayMemberRateDo> selectMemberRateList(FiCbPayMemberRateDo fiCbPayMemberRateDo);
}