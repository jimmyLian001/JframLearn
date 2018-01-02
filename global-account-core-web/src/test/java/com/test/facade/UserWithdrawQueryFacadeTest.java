package com.test.facade;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.facade.UserWithdrawQueryFacade;
import com.baofu.international.global.account.core.facade.model.UserWithdrawApplyQueryReqDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawSumQueryReqDto;
import com.test.frame.Base;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * description:用户提现查询接口Test
 * <p/>
 * Created by liy on 2017/11/22 0022 ProjectName：account
 */
public class UserWithdrawQueryFacadeTest extends Base {

    /**
     * 用户提现查询接口
     */
    @Autowired
    private UserWithdrawQueryFacade queryFacade;

    /**
     *
     */
    @Test
    public void userWithdrawSumQueryTest() {

        UserWithdrawSumQueryReqDto sumQueryReqDto = new UserWithdrawSumQueryReqDto();
        sumQueryReqDto.setPageNo(1);
        sumQueryReqDto.setPageSize(20);
        sumQueryReqDto.setBeginTime("2017-11-01 00:00:00");
        sumQueryReqDto.setEndTime("2017-11-31 00:00:00");
        queryFacade.userWithdrawSumQuery(sumQueryReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }

    /**
     * 用户提现申请查询
     */
    @Test
    public void userWithdrawApplyQueryTest() {

        UserWithdrawApplyQueryReqDto applyQueryReqDto = new UserWithdrawApplyQueryReqDto();
        applyQueryReqDto.setWithdrawBatchId(1711271602000183255L);
//        applyQueryReqDto.setOrderId(1711151005000001824L);
        applyQueryReqDto.setPageNo(0);
        applyQueryReqDto.setPageSize(20);
        queryFacade.userWithdrawApplyQuery(applyQueryReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
    }
}
