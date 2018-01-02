package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;

import java.util.List;

/**
 * description:用户提现查询 Manager
 * <p/>
 * Created by liy on 2017/11/28 0028 ProjectName：account
 */
public interface UserWithdrawQueryManager {

    /**
     * 用户提现汇总查询
     *
     * @param sumDo 查询条件对象
     * @return 结果集
     */
    List<UserWithdrawSumDo> selectUserWithdrawSumPageInfo(UserWithdrawSumDo sumDo);


    /**
     * 用户提现申请分页查询
     *
     * @param applyDo    请求参数
     * @param withdrawId 提现明细编号
     * @return 结果集
     */
    List<UserWithdrawApplyDo> selectUserWithdrawApplyPageInfo(UserWithdrawApplyDo applyDo,
                                                              List<Long> withdrawId);

    /**
     * 根据用户号获取实名认证的用户名
     *
     * @param userNo 用户号
     * @return 用户名
     */
    String getRealNameByUserNo(Long userNo);
}
