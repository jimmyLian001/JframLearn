package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWithdrawSumMapper {

    /**
     * 新增汇总记录
     *
     * @param userWithdrawSumDo 参数
     * @return 影响条数
     */
    int insertUserWithdrawSum(UserWithdrawSumDo userWithdrawSumDo);

    /**
     * 根据平台流水号修改汇总信息的转账状态
     *
     * @param withdrawBatchId 平台流水号
     * @param transferState   转账状态
     * @param remarks         备注
     * @return 影响条数
     */
    int updateWithdrawSum(@Param("withdrawBatchId") Long withdrawBatchId,
                          @Param("transferState") Integer transferState,
                          @Param("remarks") String remarks);


    /**
     * 根据汇总流水号查询汇总信息
     *
     * @param withdrawBatchId 平台流水号
     * @return 影响条数
     */
    UserWithdrawSumDo queryTUserWithdrawSumDoByBatch(@Param("withdrawBatchId") Long withdrawBatchId);

    /**
     * 查询用户提现汇总分页信息
     *
     * @param sumDo 请求参数
     * @return 结果集
     */
    List<UserWithdrawSumDo> selectUserWithdrawSumPageInfo(UserWithdrawSumDo sumDo);
}