package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.CardBinBiz;
import com.baofu.international.global.account.core.biz.convert.CardBinConvert;
import com.baofu.international.global.account.core.biz.models.CardBinInfoRespBo;
import com.baofu.international.global.account.core.common.enums.CardTypeEnum;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;
import com.baofu.international.global.account.core.manager.CardBinManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class CardBinBizImpl implements CardBinBiz {

    /**
     * 卡bin操作manager
     */
    @Autowired
    private CardBinManager cardBinManager;

    /**
     * 查询卡bin信息
     *
     * @param cardBin 卡bin
     * @return 卡bin信息
     */
    @Override
    public CardBinInfoRespBo queryCardBin(String cardBin) {
        return CardBinConvert.cardBinRespConvert(cardBinManager.queryCardBin(cardBin));
    }

    /**
     * 卡bin校验
     *
     * @param cardNo 卡号
     * @return 校验结果
     */
    @Override
    public void checkCardBin(String cardNo) {
        // 卡bin校验
        String cardBin = cardNo.substring(0, 6);
        BankCardBinInfoDo bankCardBinInfoDo = cardBinManager.queryCardBin(cardBin);
        log.info("卡bin查询结果：{}", bankCardBinInfoDo);

        if (bankCardBinInfoDo == null) {
            log.error("该卡信息不存在:{}", cardNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190400);
        }

        if (CardTypeEnum.CARD_TYPE_1.getCode() != Integer.parseInt(bankCardBinInfoDo.getCardType())) {
            log.error("不支持的卡类型:{}", cardNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190405);
        }
    }
}
