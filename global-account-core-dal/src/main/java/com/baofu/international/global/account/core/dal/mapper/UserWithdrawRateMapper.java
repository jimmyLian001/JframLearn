package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.WithdrawRateReqDo;
import com.baofu.international.global.account.core.dal.model.WithdrawRateRespDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yimo on 2017/11/23.
 */
public interface UserWithdrawRateMapper {
    List<WithdrawRateRespDo> searchWithdrawRateList(WithdrawRateReqDo reqDo);

    void updateByOrderId(WithdrawRateReqDo withdrawRateReqDo);

    void addWithdrawRate(WithdrawRateReqDo withdrawRateReqDo);

    /**
     * 根据用户号、币种查询用户设置有效费率
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return
     */
    WithdrawRateRespDo queryUserSetWithdrawRate(@Param("userNo") Long userNo, @Param("ccy") String ccy);
}
