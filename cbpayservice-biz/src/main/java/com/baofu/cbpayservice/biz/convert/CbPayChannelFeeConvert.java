package com.baofu.cbpayservice.biz.convert;

import com.baofu.cbpayservice.biz.models.CbPayChannelFeeBo;
import com.baofu.cbpayservice.biz.models.CbPaySelectChannelFeePageBo;
import com.baofu.cbpayservice.dal.models.CbPaySelectChannelFeePageDo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;

/**
 * Service层参数转换
 * <p>
 * 1、渠道成本操作接口请求参数转换成Biz层请求参数信息
 * </p>
 * User: wanght Date:2017/10/24 ProjectName: cbpays-ervice Version: 1.0
 */
public final class CbPayChannelFeeConvert {

    private CbPayChannelFeeConvert() {

    }

    /**
     * 渠道成本分页查询接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPaySelectChannelFeePageBo 分页查询参数
     * @return Biz层请求参数信息
     */
    public static CbPaySelectChannelFeePageDo selectPageListParamConvert(CbPaySelectChannelFeePageBo cbPaySelectChannelFeePageBo) {
        CbPaySelectChannelFeePageDo cbPaySelectChannelFeePageDo = new CbPaySelectChannelFeePageDo();
        cbPaySelectChannelFeePageDo.setBeginTime(cbPaySelectChannelFeePageBo.getBeginTime());
        cbPaySelectChannelFeePageDo.setEndTime(cbPaySelectChannelFeePageBo.getEndTime());
        cbPaySelectChannelFeePageDo.setChannelId(cbPaySelectChannelFeePageBo.getChannelId());
        cbPaySelectChannelFeePageDo.setPageNo(cbPaySelectChannelFeePageBo.getPageNo());
        cbPaySelectChannelFeePageDo.setPageSize(cbPaySelectChannelFeePageBo.getPageSize());
        return cbPaySelectChannelFeePageDo;
    }

    /**
     * 新增渠道成本配置接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayChannelFeeBo 新增参数
     * @return Biz层请求参数信息
     */
    public static FiCbPayChannelFeeDo addParamConvert(CbPayChannelFeeBo cbPayChannelFeeBo) {
        FiCbPayChannelFeeDo fiCbPayChannelFeeDo = new FiCbPayChannelFeeDo();
        fiCbPayChannelFeeDo.setRecordId(cbPayChannelFeeBo.getRecordId());
        fiCbPayChannelFeeDo.setStatus(cbPayChannelFeeBo.getStatus());
        fiCbPayChannelFeeDo.setRemarks(cbPayChannelFeeBo.getRemarks());
        fiCbPayChannelFeeDo.setUpdateBy(cbPayChannelFeeBo.getUpdateBy());
        fiCbPayChannelFeeDo.setMaxMoney(cbPayChannelFeeBo.getMaxMoney());
        fiCbPayChannelFeeDo.setAbroadFixedMoney(cbPayChannelFeeBo.getAbroadFixedMoney());
        fiCbPayChannelFeeDo.setChannelId(cbPayChannelFeeBo.getChannelId());
        fiCbPayChannelFeeDo.setChargeType(cbPayChannelFeeBo.getChargeType());
        fiCbPayChannelFeeDo.setChargeValue(cbPayChannelFeeBo.getChargeValue());
        fiCbPayChannelFeeDo.setCreateBy(cbPayChannelFeeBo.getCreateBy());
        fiCbPayChannelFeeDo.setFixedMoney(cbPayChannelFeeBo.getFixedMoney());
        fiCbPayChannelFeeDo.setMinMoney(cbPayChannelFeeBo.getMinMoney());

        return fiCbPayChannelFeeDo;
    }

    /**
     * 修改渠道成本配置接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayChannelFeeBo 修改参数
     * @return Biz层请求参数信息
     */
    public static FiCbPayChannelFeeDo editParamConvert(CbPayChannelFeeBo cbPayChannelFeeBo) {
        FiCbPayChannelFeeDo fiCbPayChannelFeeDo = new FiCbPayChannelFeeDo();
        fiCbPayChannelFeeDo.setRecordId(cbPayChannelFeeBo.getRecordId());
        fiCbPayChannelFeeDo.setStatus(cbPayChannelFeeBo.getStatus());
        fiCbPayChannelFeeDo.setRemarks(cbPayChannelFeeBo.getRemarks());
        fiCbPayChannelFeeDo.setUpdateBy(cbPayChannelFeeBo.getUpdateBy());
        fiCbPayChannelFeeDo.setMaxMoney(cbPayChannelFeeBo.getMaxMoney());
        fiCbPayChannelFeeDo.setAbroadFixedMoney(cbPayChannelFeeBo.getAbroadFixedMoney());
        fiCbPayChannelFeeDo.setChannelId(cbPayChannelFeeBo.getChannelId());
        fiCbPayChannelFeeDo.setChargeType(cbPayChannelFeeBo.getChargeType());
        fiCbPayChannelFeeDo.setChargeValue(cbPayChannelFeeBo.getChargeValue());
        fiCbPayChannelFeeDo.setFixedMoney(cbPayChannelFeeBo.getFixedMoney());
        fiCbPayChannelFeeDo.setMinMoney(cbPayChannelFeeBo.getMinMoney());

        return fiCbPayChannelFeeDo;
    }
}