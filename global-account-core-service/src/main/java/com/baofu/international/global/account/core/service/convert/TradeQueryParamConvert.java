package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.model.SubAccTradeDetailDo;
import com.baofu.international.global.account.core.dal.model.TradeAccQueryDo;
import com.baofu.international.global.account.core.dal.model.TradeQueryDo;
import com.baofu.international.global.account.core.dal.model.WithdrawalQueryDo;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 交易查询参数转换
 *
 * @author 莫小阳  on 2017/11/7.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TradeQueryParamConvert {

    /**
     * tradeQueryReqDto——》TradeQueryDo
     *
     * @param tradeQueryReqDto 交易查询条件信息
     * @return 结果
     */
    public static TradeQueryDo convertToDo(TradeQueryReqDto tradeQueryReqDto) {
        if (tradeQueryReqDto == null) {
            return null;
        }
        TradeQueryDo tradeQueryDo = new TradeQueryDo();
        tradeQueryDo.setUserNo(tradeQueryReqDto.getUserNo());
        tradeQueryDo.setFlag(tradeQueryReqDto.getFlag());
        tradeQueryDo.setAccountType(tradeQueryReqDto.getAccountType());
        tradeQueryDo.setAccountNo(tradeQueryReqDto.getAccountNo());
        tradeQueryDo.setBeginTime(tradeQueryReqDto.getBeginTime());
        tradeQueryDo.setEndTime(tradeQueryReqDto.getEndTime());
        tradeQueryDo.setState(tradeQueryReqDto.getState());
        return tradeQueryDo;
    }


    /**
     * 参数转换   List<TradeAccQueryDo> ——》List<TradeAccQueryRespDto>
     *
     * @param pageData 结果参数
     * @return 结果
     */
    public static PageDataRespDto convertToList(Page<TradeAccQueryDo> pageData) {
        PageDataRespDto<TradeAccQueryRespDto> pageDataRespDto = new PageDataRespDto<>();
        pageDataRespDto.setResultList(BeanCopyUtils.listConvert(pageData.getResult(), TradeAccQueryRespDto.class));
        //当前页
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 参数转换 List<WithdrawalQueryDo> ——》List<WithdrawalQueryRespDto>
     *
     * @param pageData 结果
     * @return 结果
     */
    public static PageDataRespDto withdrawalConvertToList(Page<WithdrawalQueryDo> pageData) {

        PageDataRespDto<WithdrawalQueryRespDto> pageDataRespDto = new PageDataRespDto<>();

        pageDataRespDto.setResultList(BeanCopyUtils.listConvert(pageData.getResult(), WithdrawalQueryRespDto.class));
        //当前页
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 参数转换
     *
     * @param pageData 参数
     * @return 结果
     */
    public static PageDataRespDto subAccTradeDetailConvertToList(Page<SubAccTradeDetailDo> pageData) {

        PageDataRespDto<SubAccTradeDetailRespDto> pageDataRespDto = new PageDataRespDto<>();
        ArrayList<SubAccTradeDetailRespDto> list = Lists.newArrayList();
        for (SubAccTradeDetailDo subAccTradeDetailDo : pageData.getResult()) {
            SubAccTradeDetailRespDto subAccTradeDetailRespDto = convertSubAccTradeDetailDto(subAccTradeDetailDo);
            list.add(subAccTradeDetailRespDto);
        }
        pageDataRespDto.setResultList(list);
        //当前页
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 参数转换
     *
     * @param subAccTradeDetailDo 参数
     * @return 结果
     */
    public static SubAccTradeDetailRespDto convertSubAccTradeDetailDto(SubAccTradeDetailDo subAccTradeDetailDo) {
        if (subAccTradeDetailDo == null) {
            return null;
        }
        SubAccTradeDetailRespDto subAccTradeDetailRespDto = new SubAccTradeDetailRespDto();
        subAccTradeDetailRespDto.setTradeId(subAccTradeDetailDo.getTradeId());
        subAccTradeDetailRespDto.setUserNo(subAccTradeDetailDo.getUserNo());
        subAccTradeDetailRespDto.setUserName(subAccTradeDetailDo.getUserName());
        subAccTradeDetailRespDto.setBankAccNo(subAccTradeDetailDo.getBankAccNo());
        subAccTradeDetailRespDto.setTradeTime(DateUtil.format(subAccTradeDetailDo.getTradeTime(), DateUtil.settlePattern));
        subAccTradeDetailRespDto.setCcy(subAccTradeDetailDo.getCcy());
        subAccTradeDetailRespDto.setTradeInAccMoney(subAccTradeDetailDo.getTradeInAccMoney());
        subAccTradeDetailRespDto.setTradeOutAccMoney(subAccTradeDetailDo.getTradeOutAccMoney());
        return subAccTradeDetailRespDto;
    }
}
