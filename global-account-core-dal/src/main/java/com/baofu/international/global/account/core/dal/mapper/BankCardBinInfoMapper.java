package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 莫小阳
 */
public interface BankCardBinInfoMapper {

    /**
     * @param record record
     * @return 结果
     */
    int insert(BankCardBinInfoDo record);

    /**
     * 根据卡bin查询信息
     *
     * @param cardBin 卡 bin
     * @return 结果
     */
    List<BankCardBinInfoDo> queryListByCardBin(@Param("cardBin") String cardBin);

}