package com.baofu.international.global.account.core.biz.convert;

import com.baofu.international.global.account.core.biz.models.CardBinInfoRespBo;
import com.baofu.international.global.account.core.dal.model.BankCardBinInfoDo;

/**
 * 卡bin信息转换
 * <p>
 * 1、查询卡bin结果转换
 * </p>
 *
 * @author : hetao Date: 2017/11/06 ProjectName: account-core Version: 1.0.0
 */
public final class CardBinConvert {

    private CardBinConvert() {

    }

    /**
     * 查询卡bin结果转换
     *
     * @param bankCardBinInfoDo 卡bin信息
     * @return 响应信息c
     */
    public static CardBinInfoRespBo cardBinRespConvert(BankCardBinInfoDo bankCardBinInfoDo) {
        if (bankCardBinInfoDo == null) {
            return null;
        }
        CardBinInfoRespBo cardBinInfoRespBo = new CardBinInfoRespBo();
        cardBinInfoRespBo.setBankCode(bankCardBinInfoDo.getBankCode());
        cardBinInfoRespBo.setBankName(bankCardBinInfoDo.getBankName());
        cardBinInfoRespBo.setCardBin(bankCardBinInfoDo.getCardBin());
        cardBinInfoRespBo.setCardLength(bankCardBinInfoDo.getCardLength());
        cardBinInfoRespBo.setCardName(bankCardBinInfoDo.getCardName());
        cardBinInfoRespBo.setCardType(bankCardBinInfoDo.getCardType());

        return cardBinInfoRespBo;
    }
}
