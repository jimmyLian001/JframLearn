package com.test.facade;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.mapper.PaymentDetailMapper;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import com.baofu.international.global.account.core.dal.model.TradeAccQueryDo;
import com.baofu.international.global.account.core.facade.TradeQueryFacade;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.facade.model.TradeQueryReqDto;
import com.baofu.international.global.account.core.facade.model.UserAccInfoReqDto;
import com.baofu.international.global.account.core.facade.model.UserStoreInfoRespDto;
import com.baofu.international.global.account.core.manager.CardBinManager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author 莫小阳  on 2017/11/8.
 */
public class TradeQueryFacadeTest extends Base {

    @Autowired
    private TradeQueryFacade tradeQueryFacade;

    /**
     * 交易查询 Mapper
     */
    @Autowired
    private PaymentDetailMapper tPaymentDetailMapper;

    @Autowired
    private CardBinManager cardBinManager;


    @Test
    public void test_01() {
        BankCardBinInfoDo bankCardBinInfoDo = cardBinManager.queryCardBin("621399011");
    }


    @Test
    public void queryWalletIdByCcyTest() {
        UserAccInfoReqDto userAccInfoReqDto = new UserAccInfoReqDto();
        userAccInfoReqDto.setCcy("USD");
        userAccInfoReqDto.setUserNo(1711091923000416644L);
        Result<List<UserStoreInfoRespDto>> listResult = tradeQueryFacade.queryUserStoreByCcy(userAccInfoReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        System.out.println(listResult);
    }


    @Test
    public void test() {
        PageHelper.offsetPage(1 * 5, 5);
        Page<TradeAccQueryDo> pageResult = (Page<TradeAccQueryDo>) tPaymentDetailMapper.tradeAccQuery(null);
        List<TradeAccQueryDo> result = pageResult.getResult();
        System.out.println(result);
    }


    @Test
    public void tradeAccQueryTest() {
        TradeQueryReqDto tradeQueryReqDto = new TradeQueryReqDto();
        tradeQueryReqDto.setUserNo(5182171228000012268L);
        tradeQueryReqDto.setFlag("1");
        tradeQueryReqDto.setPageSize(1000);
        tradeQueryReqDto.setCurrPageNum(0);
        tradeQueryReqDto.setBeginTime("2017-11-28 00:00:00");
        tradeQueryReqDto.setEndTime("2017-12-28 23:59:59");
        Result<PageDataRespDto> pageDataRespDtoResult = tradeQueryFacade.tradeAccQuery(tradeQueryReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        PageDataRespDto result = pageDataRespDtoResult.getResult();
        System.out.println(result);
    }

    @Test
    public void withdrawalQueryTest() {
        TradeQueryReqDto tradeQueryReqDto = new TradeQueryReqDto();
        tradeQueryReqDto.setFlag("3");
        tradeQueryReqDto.setUserNo(1711131714000418753L);
        tradeQueryReqDto.setCurrPageNum(0);
        tradeQueryReqDto.setPageSize(10);
        Result<PageDataRespDto> pageDataRespDtoResult = tradeQueryFacade.withdrawalQuery(tradeQueryReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        System.out.println(pageDataRespDtoResult);
    }


}
