package com.test.facade;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import com.baofu.international.global.account.core.facade.WithdrawDetailsQueryFacade;
import com.baofu.international.global.account.core.facade.model.ChannelWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDetailsReqDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDistributeRespDto;
import com.baofu.international.global.account.core.manager.CardBinManager;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.slf4j.MDC;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;

/**
 * @author dxy  on 2017/11/17.
 */
public class WithdrawDetailsQueryFacadeTest extends Base {

    @Autowired
    private WithdrawDetailsQueryFacade withdrawDetailsQueryFacade;


    @Autowired
    private CardBinManager cardBinManager;


    @Test
    public void test_01() {
        BankCardBinInfoDo bankCardBinInfoDo = cardBinManager.queryCardBin("621399011");
    }


    @Test
    public void withdrawDetailsQueryTest() {

        try {
            WithdrawDetailsReqDto req = new WithdrawDetailsReqDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            req.setStartDate(sdf.parse("2017-10-04 00:00:00"));
            req.setEndDate(sdf.parse("2017-11-29 00:00:00"));
            req.setPageCount(20);
            Result<PageRespDTO<UserWithdrawDetailsRespDto>> pageDataRespDtoResult = withdrawDetailsQueryFacade.userWithdrawDetailsQuery(req, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            System.out.println(pageDataRespDtoResult);
        } catch (Exception e) {

        }
    }

    @Test
    public void channelWithdrawDetailsQueryTest() {

        try {
            WithdrawDetailsReqDto req = new WithdrawDetailsReqDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            req.setStartDate(sdf.parse("2017-11-01 00:00:00"));
            req.setEndDate(sdf.parse("2017-11-30 23:59:59"));
            req.setCcy("USD");
            req.setPageCount(20);
            Result<PageRespDTO<ChannelWithdrawDetailsRespDto>> pageDataRespDtoResult = withdrawDetailsQueryFacade.channelWithdrawDetailsQuery(req, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            System.out.println(pageDataRespDtoResult);
        } catch (Exception e) {

        }
    }

    @Test
    public void withdrawDistributeQueryTest() {

        try {
            WithdrawDetailsReqDto req = new WithdrawDetailsReqDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            req.setStartDate(sdf.parse("2017-11-01 00:00:00"));
            req.setEndDate(sdf.parse("2017-11-29 00:00:00"));
            req.setPageCount(20);
            Result<PageRespDTO<WithdrawDistributeRespDto>> pageDataRespDtoResult = withdrawDetailsQueryFacade.withdrawDistributeQuery(req, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            System.out.println(pageDataRespDtoResult);
        } catch (Exception e) {

        }
    }


}
