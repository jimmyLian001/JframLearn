package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.CardBinInfoRespBo;
import com.baofu.international.global.account.core.facade.model.CardBinInfoRespDto;

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
     * @param cardBinInfoRespBo 卡bin信息
     * @return 响应信息
     */
    public static CardBinInfoRespDto cardBinRespConvert(CardBinInfoRespBo cardBinInfoRespBo) {
        if (cardBinInfoRespBo == null) {
            return null;
        }
        CardBinInfoRespDto cardBinInfoRespDto = new CardBinInfoRespDto();
        cardBinInfoRespDto.setBankCode(cardBinInfoRespBo.getBankCode());
        cardBinInfoRespDto.setBankName(cardBinInfoRespBo.getBankName());
        cardBinInfoRespDto.setCardBin(cardBinInfoRespBo.getCardBin());
        cardBinInfoRespDto.setCardLength(cardBinInfoRespBo.getCardLength());
        cardBinInfoRespDto.setCardName(cardBinInfoRespBo.getCardName());
        cardBinInfoRespDto.setCardType(cardBinInfoRespBo.getCardType());

        return cardBinInfoRespDto;
    }
}
