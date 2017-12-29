package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayMemberRateReqBo;

import java.util.List;

/**
 * <p>
 * 1、新增浮动汇率
 * 2、修改浮动汇率
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
public interface CbPayMemberRateBiz {

    /**
     * 新增浮动汇率
     *
     * @param cbPayMemberRateReqBo 汇率请求参数
     */
    void addMemberRate(CbPayMemberRateReqBo cbPayMemberRateReqBo);

    /**
     * 更新浮动汇率
     *
     * @param cbPayMemberRateReqBo 汇率请求参数
     */
    void modifyMemberRate(CbPayMemberRateReqBo cbPayMemberRateReqBo);

    /**
     * 获取商户浮动汇率bp
     *
     * @param cbPayMemberRateReqBo 搜索参数
     * @return 返回结果
     */
    CbPayMemberRateReqBo queryMemberRateOne(CbPayMemberRateReqBo cbPayMemberRateReqBo);

    /**
     * 根据会员ID和币种查询汇率列表(在有效时间范围之内)
     *
     * @param cbPayMemberRateReqBo 汇率请求参数
     * @return 汇率集合
     */
    List<CbPayMemberRateReqBo> queryMemberRateList(CbPayMemberRateReqBo cbPayMemberRateReqBo);
}
