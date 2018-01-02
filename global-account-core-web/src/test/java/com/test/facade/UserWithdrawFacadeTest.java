package com.test.facade;

import com.baofu.international.global.account.core.biz.LockBiz;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.facade.UserWithdrawFacade;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawFeeDetailDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawFeeDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 1、 用户提现测试
 * </p>
 * User: feng_jiang  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawFacadeTest extends Base {

    @Autowired
    private UserWithdrawFacade userWithdrawFacade;

    /**
     * redis锁服务
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 用户前台提现账户展示
     */
    @Test
    public void queryWithdrawAccountInfoTest() {
        Long userNo = 5182171211000049318L;
        Result<List<WithdrawAccountRespDto>> list = userWithdrawFacade.queryWithdrawAccountInfo(userNo, UUID.randomUUID().toString());
        if (list.isSuccess()) {
            System.out.println("====================" + list.getResult());
        }
    }

    /**
     * 计算用户提现试算
     */
    @Test
    public void withdrawCashFeeTest() {
        UserWithdrawFeeDto userWithdrawFeeDto = new UserWithdrawFeeDto();
        userWithdrawFeeDto.setUserNo(5181171127000017088L);
        List<UserWithdrawFeeDetailDto> dtoList = new ArrayList<>();
        UserWithdrawFeeDetailDto model = new UserWithdrawFeeDetailDto();
        model.setAccountNo(5181171127000017088L);
        model.setWithdrawAmt(new BigDecimal("55"));
        model.setStoreNo("150");
        model.setWithdrawCcy("USD");
//        model.setWalletId("WA-69NBX334XQB");
        dtoList.add(model);
        userWithdrawFeeDto.setUserWithdrawFeeDetailDtoList(dtoList);
        Result<List<WithdrawCashFeeRespDto>> list = userWithdrawFacade.withdrawCashFee(userWithdrawFeeDto, UUID.randomUUID().toString());
        if (list.isSuccess()) {
            System.out.println("====================" + list.getResult());
        }
    }

    /**
     * 用户前台提现
     */
    @Test
    public void userWithdrawCashTest() {
        UserWithdrawDto userWithdrawDto = new UserWithdrawDto();
        userWithdrawDto.setUserNo(5182171211000049318L);
        userWithdrawDto.setBankCardRecordNo(1711271421000183034L);
        userWithdrawDto.setCreateBy("测试");
        List<UserWithdrawDetailDto> list = Lists.newArrayList();
        UserWithdrawDetailDto dto = new UserWithdrawDetailDto();
//        dto.setWithdrawAccNo("5090000718");
        dto.setWithdrawAmt(new BigDecimal(55));
        dto.setWithdrawCcy("USD");
//        dto.setWalletId("WA-69NBX334XQB");
        dto.setStoreNo(150L);
        list.add(dto);
        userWithdrawDto.setUserWithdrawDetailDtoList(list);
//        lockBiz.unLock(Constants.GLOBAL_ACCOUNT_WITHDRAW_CASH + userWithdrawDto.getUserNo() + ":" + dto.getWithdrawCcy()
//                + ":" + dto.getAccountNo());

        lockBiz.unLock("GLOBAL:ACCOUNT:WITHDRAW:CASH:5182171211000049318:USD:6183171212000011806");
//        Result<Boolean> result = userWithdrawFacade.userWithdrawCash(userWithdrawDto, UUID.randomUUID().toString());
//        if(result.isSuccess()){
//            System.out.println("提现申请成功");
//        }
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtil.desEncrypt("988777666", Constants.CARD_DES_KEY));
    }
}