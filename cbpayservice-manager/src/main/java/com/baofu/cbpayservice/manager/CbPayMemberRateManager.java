package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayMemberRateDo;

import java.util.List;

/**
 * <p>
 * l、新增浮动汇率
 * 2、修改相应会员的汇率或状态
 * 3、查询浮动汇率
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
public interface CbPayMemberRateManager {

    /**
     * 新增浮动汇率
     *
     * @param fiCbPayMemberRateDo 插入数据对象
     */
    void addMemberRate(FiCbPayMemberRateDo fiCbPayMemberRateDo);

    /**
     * 根据record_id去更新会员浮动汇率的状态和汇率值
     *
     * @param fiCbPayMemberRateDo 查询汇率信息参数
     */
    void modifyMemberRate(FiCbPayMemberRateDo fiCbPayMemberRateDo);

    /**
     * 根据记录编号查询会员的浮动汇率
     *
     * @param recordId 记录编号
     * @return FiCbPayMemberRateDo
     */
    FiCbPayMemberRateDo queryMemberRate(Long recordId);

    /**
     * 获取商户浮动汇率bp
     *
     * @param fiCbPayMemberRateDo 搜索参数
     * @return 返回结果
     */
    FiCbPayMemberRateDo queryMemberRateOne(FiCbPayMemberRateDo fiCbPayMemberRateDo);

    /**
     * 查询会员相应的币种浮动汇率列表
     *
     * @param fiCbPayMemberRateDo 查询汇率信息参数
     * @return 汇率列表
     */
    List<FiCbPayMemberRateDo> queryMemberRateList(FiCbPayMemberRateDo fiCbPayMemberRateDo);
}
