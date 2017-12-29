package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPayChannelFeeBo;
import com.baofu.cbpayservice.biz.models.CbPaySelectChannelFeePageBo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import com.baofu.cbpayservice.facade.models.CbPayChannelFeeDto;
import com.baofu.cbpayservice.facade.models.CbPaySelectChannelFeePageDto;
import com.baofu.cbpayservice.facade.models.res.CbPayChannelFeeRespDto;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
     * @param cbPaySelectChannelFeePageDto 分页查询参数
     * @return Biz层请求参数信息
     */
    public static CbPaySelectChannelFeePageBo selectPageListParamConvert(CbPaySelectChannelFeePageDto cbPaySelectChannelFeePageDto) {
        CbPaySelectChannelFeePageBo cbPaySelectChannelFeePageBo = new CbPaySelectChannelFeePageBo();
        cbPaySelectChannelFeePageBo.setBeginTime(cbPaySelectChannelFeePageDto.getBeginTime());
        cbPaySelectChannelFeePageBo.setEndTime(cbPaySelectChannelFeePageDto.getEndTime());
        cbPaySelectChannelFeePageBo.setChannelId(cbPaySelectChannelFeePageDto.getChannelId());
        cbPaySelectChannelFeePageBo.setPageNo(cbPaySelectChannelFeePageDto.getPageNo());
        cbPaySelectChannelFeePageBo.setPageSize(cbPaySelectChannelFeePageDto.getPageSize());
        return cbPaySelectChannelFeePageBo;
    }

    /**
     * 渠道成本分页查询结果转换成响应信息
     *
     * @param pageList 分页查询结果
     * @return Biz层请求参数信息
     */
    public static List<CbPayChannelFeeRespDto> pageListResultConvert(List<FiCbPayChannelFeeDo> pageList) {
        if (CollectionUtils.isEmpty(pageList)) {
            return null;
        }
        List<CbPayChannelFeeRespDto> cbPayChannelFeeRespDtoList = new ArrayList<>();
        for (FiCbPayChannelFeeDo fiCbPayChannelFeeDo : pageList) {
            CbPayChannelFeeRespDto cbPayChannelFeeRespDto = new CbPayChannelFeeRespDto();
            cbPayChannelFeeRespDto.setRecordId(fiCbPayChannelFeeDo.getRecordId());
            cbPayChannelFeeRespDto.setAbroadFixedMoney(fiCbPayChannelFeeDo.getAbroadFixedMoney());
            cbPayChannelFeeRespDto.setChannelId(fiCbPayChannelFeeDo.getChannelId());
            cbPayChannelFeeRespDto.setChargeType(fiCbPayChannelFeeDo.getChargeType());
            cbPayChannelFeeRespDto.setChargeValue(fiCbPayChannelFeeDo.getChargeValue());
            cbPayChannelFeeRespDto.setCreateBy(fiCbPayChannelFeeDo.getCreateBy());
            cbPayChannelFeeRespDto.setCreateAt(fiCbPayChannelFeeDo.getCreateAt());
            cbPayChannelFeeRespDto.setFixedMoney(fiCbPayChannelFeeDo.getFixedMoney());
            cbPayChannelFeeRespDto.setMaxMoney(fiCbPayChannelFeeDo.getMaxMoney());
            cbPayChannelFeeRespDto.setMinMoney(fiCbPayChannelFeeDo.getMinMoney());
            cbPayChannelFeeRespDto.setUpdateBy(fiCbPayChannelFeeDo.getUpdateBy());
            cbPayChannelFeeRespDto.setUpdateAt(fiCbPayChannelFeeDo.getUpdateAt());
            cbPayChannelFeeRespDto.setRemarks(fiCbPayChannelFeeDo.getRemarks());
            cbPayChannelFeeRespDto.setStatus(fiCbPayChannelFeeDo.getStatus());

            cbPayChannelFeeRespDtoList.add(cbPayChannelFeeRespDto);
        }
        return cbPayChannelFeeRespDtoList;
    }

    /**
     * 新增渠道成本配置接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayChannelFeeDto 新增参数
     * @return Biz层请求参数信息
     */
    public static CbPayChannelFeeBo addParamConvert(CbPayChannelFeeDto cbPayChannelFeeDto) {
        CbPayChannelFeeBo cbPayChannelFeeBo = new CbPayChannelFeeBo();
        cbPayChannelFeeBo.setStatus(cbPayChannelFeeDto.getStatus());
        cbPayChannelFeeBo.setRemarks(cbPayChannelFeeDto.getRemarks());
        cbPayChannelFeeBo.setUpdateBy(cbPayChannelFeeDto.getUpdateBy());
        cbPayChannelFeeBo.setMaxMoney(cbPayChannelFeeDto.getMaxMoney());
        cbPayChannelFeeBo.setUpdateAt(cbPayChannelFeeDto.getUpdateAt());
        cbPayChannelFeeBo.setAbroadFixedMoney(cbPayChannelFeeDto.getAbroadFixedMoney());
        cbPayChannelFeeBo.setChannelId(cbPayChannelFeeDto.getChannelId());
        cbPayChannelFeeBo.setChargeType(cbPayChannelFeeDto.getChargeType());
        cbPayChannelFeeBo.setChargeValue(cbPayChannelFeeDto.getChargeValue());
        cbPayChannelFeeBo.setCreateAt(cbPayChannelFeeDto.getCreateAt());
        cbPayChannelFeeBo.setCreateBy(cbPayChannelFeeDto.getCreateBy());
        cbPayChannelFeeBo.setFixedMoney(cbPayChannelFeeDto.getFixedMoney());
        cbPayChannelFeeBo.setMinMoney(cbPayChannelFeeDto.getMinMoney());

        return cbPayChannelFeeBo;
    }

    /**
     * 修改渠道成本配置接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayChannelFeeDto 修改参数
     * @return Biz层请求参数信息
     */
    public static CbPayChannelFeeBo editParamConvert(CbPayChannelFeeDto cbPayChannelFeeDto) {
        CbPayChannelFeeBo cbPayChannelFeeBo = new CbPayChannelFeeBo();
        cbPayChannelFeeBo.setRecordId(cbPayChannelFeeDto.getRecordId());
        cbPayChannelFeeBo.setStatus(cbPayChannelFeeDto.getStatus());
        cbPayChannelFeeBo.setRemarks(cbPayChannelFeeDto.getRemarks());
        cbPayChannelFeeBo.setUpdateBy(cbPayChannelFeeDto.getUpdateBy());
        cbPayChannelFeeBo.setMaxMoney(cbPayChannelFeeDto.getMaxMoney());
        cbPayChannelFeeBo.setUpdateAt(cbPayChannelFeeDto.getUpdateAt());
        cbPayChannelFeeBo.setAbroadFixedMoney(cbPayChannelFeeDto.getAbroadFixedMoney());
        cbPayChannelFeeBo.setChannelId(cbPayChannelFeeDto.getChannelId());
        cbPayChannelFeeBo.setChargeType(cbPayChannelFeeDto.getChargeType());
        cbPayChannelFeeBo.setChargeValue(cbPayChannelFeeDto.getChargeValue());
        cbPayChannelFeeBo.setFixedMoney(cbPayChannelFeeDto.getFixedMoney());
        cbPayChannelFeeBo.setMinMoney(cbPayChannelFeeDto.getMinMoney());

        return cbPayChannelFeeBo;
    }

    /**
     * 查询渠道成本配置响应信息转换
     *
     * @param fiCbPayChannelFeeDo 查询结果
     * @return 响应信息
     */
    public static CbPayChannelFeeRespDto queryResultParamConvert(FiCbPayChannelFeeDo fiCbPayChannelFeeDo) {
        CbPayChannelFeeRespDto cbPayChannelFeeRespDto = new CbPayChannelFeeRespDto();
        cbPayChannelFeeRespDto.setRecordId(fiCbPayChannelFeeDo.getRecordId());
        cbPayChannelFeeRespDto.setStatus(fiCbPayChannelFeeDo.getStatus());
        cbPayChannelFeeRespDto.setRemarks(fiCbPayChannelFeeDo.getRemarks());
        cbPayChannelFeeRespDto.setUpdateBy(fiCbPayChannelFeeDo.getUpdateBy());
        cbPayChannelFeeRespDto.setMaxMoney(fiCbPayChannelFeeDo.getMaxMoney());
        cbPayChannelFeeRespDto.setUpdateAt(fiCbPayChannelFeeDo.getUpdateAt());
        cbPayChannelFeeRespDto.setAbroadFixedMoney(fiCbPayChannelFeeDo.getAbroadFixedMoney());
        cbPayChannelFeeRespDto.setChannelId(fiCbPayChannelFeeDo.getChannelId());
        cbPayChannelFeeRespDto.setChargeType(fiCbPayChannelFeeDo.getChargeType());
        cbPayChannelFeeRespDto.setChargeValue(fiCbPayChannelFeeDo.getChargeValue());
        cbPayChannelFeeRespDto.setFixedMoney(fiCbPayChannelFeeDo.getFixedMoney());
        cbPayChannelFeeRespDto.setMinMoney(fiCbPayChannelFeeDo.getMinMoney());
        cbPayChannelFeeRespDto.setCreateAt(fiCbPayChannelFeeDo.getCreateAt());

        return cbPayChannelFeeRespDto;
    }
}