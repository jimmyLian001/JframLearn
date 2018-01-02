package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.CardBinInfoRespBo;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * 2,卡bin校验
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
public interface CardBinBiz {

    /**
     * 查询卡bin信息
     *
     * @param cardBin 卡bin
     * @return 卡bin信息
     */
    CardBinInfoRespBo queryCardBin(String cardBin);

    /**
     * 卡bin校验
     *
     * @param cardNo 卡号
     */
    void checkCardBin(String cardNo);
}
