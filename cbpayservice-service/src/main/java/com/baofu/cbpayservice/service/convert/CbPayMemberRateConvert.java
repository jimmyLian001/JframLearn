package com.baofu.cbpayservice.service.convert;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofoo.dfs.client.util.DateUtil;
import com.baofu.cbpayservice.biz.models.CbPayMemberRateReqBo;
import com.baofu.cbpayservice.common.enums.RateSetTypeEnum;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateAddDto;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateModifyDto;
import com.baofu.cbpayservice.facade.models.CbPayMemberRateQueryDto;
import com.baofu.cbpayservice.facade.models.ExchangeRateResultDto;
import com.baofu.cbpayservice.facade.models.res.CbPayMemberRateResDto;

/**
 * <p>
 * FiCbPayMemberRate参数转换
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
public final class CbPayMemberRateConvert {

    private CbPayMemberRateConvert() {

    }

    /**
     * 查询参数转换
     *
     * @param cbPayMemberRateQueryDto 请求信息
     * @return CbPayMemberRateReqBo
     */
    public static CbPayMemberRateReqBo paramConvert(CbPayMemberRateQueryDto cbPayMemberRateQueryDto) {
        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setRecordId(cbPayMemberRateQueryDto.getRecordId());
        return cbPayMemberRateReqBo;
    }

    /**
     * 查询参数转换
     *
     * @param cbPayMemberRateAddDto 请求信息
     * @return CbPayMemberRateReqBo
     */
    public static CbPayMemberRateReqBo paramConvert(CbPayMemberRateAddDto cbPayMemberRateAddDto) {

        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setMemberId(cbPayMemberRateAddDto.getMemberId());
        cbPayMemberRateReqBo.setCcy(cbPayMemberRateAddDto.getCcy());
        cbPayMemberRateReqBo.setCreateBy(cbPayMemberRateAddDto.getCreateBy());
        cbPayMemberRateReqBo.setUpdateBy(cbPayMemberRateAddDto.getUpdateBy());
        cbPayMemberRateReqBo.setBusinessType(Integer.parseInt(cbPayMemberRateAddDto.getBusinessType()));                    //业务类型
        cbPayMemberRateReqBo.setRateSetType(Integer.parseInt(cbPayMemberRateAddDto.getRateSetType()));                      //浮动值设置方式
        cbPayMemberRateReqBo.setBeginDate(DateUtil.parse(cbPayMemberRateAddDto.getBeginDate(), DateUtil.settlePattern));     //有效开始时间
        cbPayMemberRateReqBo.setEndDate(DateUtil.parse(cbPayMemberRateAddDto.getEndDate(), DateUtil.settlePattern));         //有效结束时间
        cbPayMemberRateReqBo.setStatus(cbPayMemberRateAddDto.getStatus());                                                  //状态
        if ((RateSetTypeEnum.BP.getCode() + "").equals(cbPayMemberRateAddDto.getRateSetType().trim())) { //bp
            cbPayMemberRateReqBo.setMemberRate(null);
            cbPayMemberRateReqBo.setMemberRateBp(cbPayMemberRateAddDto.getMemberRateBp());
        } else if ((RateSetTypeEnum.PERCENTAGE.getCode() + "").equals(cbPayMemberRateAddDto.getRateSetType().trim())) { //百分比
            cbPayMemberRateReqBo.setMemberRateBp(null);
            cbPayMemberRateReqBo.setMemberRate(cbPayMemberRateAddDto.getMemberRate());
        }
        return cbPayMemberRateReqBo;
    }

    /**
     * 更新参数转换
     *
     * @param cbPayMemberRateModifyDto 请求参数
     * @return CbPayMemberRateReqBo
     */
    public static CbPayMemberRateReqBo paramConvert(CbPayMemberRateModifyDto cbPayMemberRateModifyDto) {
        CbPayMemberRateReqBo cbPayMemberRateReqBo = new CbPayMemberRateReqBo();
        cbPayMemberRateReqBo.setRecordId(cbPayMemberRateModifyDto.getRecordId());
        cbPayMemberRateReqBo.setStatus(cbPayMemberRateModifyDto.getStatus());                                               //状态
        cbPayMemberRateReqBo.setUpdateBy(cbPayMemberRateModifyDto.getUpdateBy());
        cbPayMemberRateReqBo.setRateSetType(Integer.parseInt(cbPayMemberRateModifyDto.getRateSetType().trim()));            //浮动值设置方式
        cbPayMemberRateReqBo.setBeginDate(DateUtil.parse(cbPayMemberRateModifyDto.getBeginDate(), DateUtil.settlePattern));  //有效开始时间
        cbPayMemberRateReqBo.setEndDate(DateUtil.parse(cbPayMemberRateModifyDto.getEndDate(), DateUtil.settlePattern));      //有效结束时间
        if ((RateSetTypeEnum.BP.getCode() + "").equals(cbPayMemberRateModifyDto.getRateSetType().trim())) { //bp
            cbPayMemberRateReqBo.setMemberRateBp(cbPayMemberRateModifyDto.getMemberRateBp());
            cbPayMemberRateReqBo.setMemberRate(null);
        } else if ((RateSetTypeEnum.PERCENTAGE.getCode() + "").equals(cbPayMemberRateModifyDto.getRateSetType().trim())) { //百分比
            cbPayMemberRateReqBo.setMemberRateBp(null);
            cbPayMemberRateReqBo.setMemberRate(cbPayMemberRateModifyDto.getMemberRate());
        }
        return cbPayMemberRateReqBo;
    }

    /**
     * 更新参数转换
     *
     * @param cbPayMemberRateReqBo 请求参数
     * @return CbPayMemberRateReqBo
     */
    public static CbPayMemberRateResDto paramConvert(CbPayMemberRateReqBo cbPayMemberRateReqBo) {
        CbPayMemberRateResDto cbPayMemberRateResDto = new CbPayMemberRateResDto();
        cbPayMemberRateResDto.setRecordId(cbPayMemberRateReqBo.getRecordId());
        cbPayMemberRateResDto.setMemberId(cbPayMemberRateReqBo.getMemberId());
        cbPayMemberRateResDto.setCcy(cbPayMemberRateReqBo.getCcy());
        cbPayMemberRateResDto.setMemberRate(cbPayMemberRateReqBo.getMemberRate());
        cbPayMemberRateResDto.setStatus(cbPayMemberRateReqBo.getStatus());
        cbPayMemberRateResDto.setId(cbPayMemberRateReqBo.getId());
        cbPayMemberRateResDto.setCreateAt(cbPayMemberRateReqBo.getCreateAt());
        cbPayMemberRateResDto.setCreateBy(cbPayMemberRateReqBo.getCreateBy());
        cbPayMemberRateResDto.setUpdateAt(cbPayMemberRateReqBo.getUpdateAt());
        cbPayMemberRateResDto.setUpdateBy(cbPayMemberRateReqBo.getUpdateBy());
        return cbPayMemberRateResDto;
    }

    /**
     * 参数转换
     *
     * @param exchangeRate 请求参数
     * @return ExchangeRateResultDto
     */
    public static ExchangeRateResultDto paramConvert(CgwExchangeRateResultDto exchangeRate) {
        if (exchangeRate == null) {
            return null;
        }
        ExchangeRateResultDto exchangeRateResultDto = new ExchangeRateResultDto();
        exchangeRateResultDto.setSourceCurrency(exchangeRate.getSourceCurrency());  //源币种
        exchangeRateResultDto.setTargetCurrency(exchangeRate.getTargetCurrency());  //目标币种
        exchangeRateResultDto.setBuyRateOfCcy(exchangeRate.getBuyRateOfCcy());      //现汇买入价  结汇汇率
        exchangeRateResultDto.setSellRateOfCcy(exchangeRate.getSellRateOfCcy());    //现汇卖出价  购汇汇率
        exchangeRateResultDto.setBuyRateOfCash(exchangeRate.getBuyRateOfCash());    //现钞买入价
        exchangeRateResultDto.setSellRateOfCash(exchangeRate.getSellRateOfCash());  //现钞卖出价
        exchangeRateResultDto.setUpdateDate(exchangeRate.getUpdateDate());          //更新时间
        return exchangeRateResultDto;
    }
}
