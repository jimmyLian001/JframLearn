package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserWithdrawOrderDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawOrderQueryDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWithdrawOrderMapper {

    /**
     * 批量查询提现订单明细
     *
     * @param userNo         用户号
     * @param memberTransIds 用户订单集合
     * @return 查询用户订单集合
     */
    List<String> selectMerchantTrans(@Param("userNo") Long userNo, @Param("memberTransIds") List<String> memberTransIds);

    /**
     * 批量插入数据库记录
     *
     * @param userWithdrawOrderDos 提现订单
     */
    void batchInsert(List<UserWithdrawOrderDo> userWithdrawOrderDos);

    /**
     * 根据提现订单明细编号查询订单明细信息
     *
     * @param userNo      用户编号
     * @param fileBatchNo 文件编号
     * @return 返回订单明细集合
     */
    List<UserWithdrawOrderQueryDo> selectOrderByUserNoAndFileNo(@Param("userNo") Long userNo,
                                                                @Param("fileBatchNo") Long fileBatchNo);
}