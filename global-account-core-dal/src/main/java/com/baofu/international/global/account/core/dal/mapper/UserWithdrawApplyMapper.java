package com.baofu.international.global.account.core.dal.mapper;


import com.baofu.international.global.account.core.dal.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：提现申请表
 */
public interface UserWithdrawApplyMapper {

    /**
     * 根据提现流水号查询
     *
     * @param withdrawId 申请订单号
     * @return 提现申请信息
     */
    UserWithdrawApplyDo selectByOrderId(Long withdrawId);

    /**
     * 插入数据
     *
     * @param userWithdrawApplyDo 插入数据对象
     * @return 影响行数
     */
    int insert(UserWithdrawApplyDo userWithdrawApplyDo);

    /**
     * 更新申请信息
     *
     * @param userWithdrawApplyDo 更新数据对象
     * @return 影响行数
     */
    int updateByOrderId(UserWithdrawApplyDo userWithdrawApplyDo);

    /**
     * 提现查询
     *
     * @param tradeQueryDo 查询条件封装参数
     * @return 结果
     */
    List<WithdrawalQueryDo> withdrawalQuery(TradeQueryDo tradeQueryDo);

    /**
     * 根据平台流水号批量修改商户提现申请的转账状态
     *
     * @param orderId       请求参数
     * @param withdrawState 提现状态
     * @return 影响行数
     */
    int updateUserWithdrawApplyState(@Param("orderIdList") List<Long> orderId,
                                     @Param("withdrawState") Integer withdrawState);

    /**
     * 根据时间查询
     *
     * @return 结果集
     */
    List<UserWithdrawApplyDo> selectByTime(Long channelId);

    /**
     * 功能：根据汇总批次号查询所有明细文件批次号
     *
     * @param orderId 转账汇总订单号
     * @return
     */
    List<UserWithdrawApplyDo> queryUserWithdrawByOrderId(@Param("orderId") Long orderId);

    /**
     * 功能：用户提现明细更新
     *
     * @param userWithdrawApplyDo 提现明细
     */
    int modifyWithdrawApplyDo(UserWithdrawApplyDo userWithdrawApplyDo);

    /**
     * 用户提现申请分页查询
     *
     * @param applyDo    请求参数
     * @param withdrawId 提现明细编号
     * @return 结果集
     */
    List<UserWithdrawApplyDo> selectUserWithdrawApplyPageInfo(@Param("applyDo") UserWithdrawApplyDo applyDo,
                                                              @Param("withdrawId") List<Long> withdrawId);

    /**
     * 用户提现明细后台查询
     *
     * @param reqQuery req
     * @return List
     */
    List<UserWithdrawDetailsDo> selectUserWithdrawDetails(WithdrawDetailsQueryDo reqQuery);

    /**
     * 渠道提现明细后台查询
     *
     * @param reqQuery req
     * @return List
     */
    List<ChannelWithdrawDetailsDo> selectChannelWithdrawDetails(WithdrawDetailsQueryDo reqQuery);
}