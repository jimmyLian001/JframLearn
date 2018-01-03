package com.baofu.international.global.account.client.web.convert;

import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.TradeQueryVo;
import com.baofu.international.global.account.core.facade.model.TradeQueryReqDto;
import com.baofu.international.global.account.core.facade.model.UserAccInfoReqDto;

/**
 * 交易参数转换
 *
 * @author 莫小阳  on 2017/11/7.
 */
public final class TradeQueryParamConvert {
    private TradeQueryParamConvert() {
    }


    /**
     * 参数转换  TradeQueryVo——》TradeQueryReqDto
     *
     * @param tradeQueryVo 交易查询条件参数
     * @param sessionVo    用户登录信息
     * @return 结果
     */
    public static TradeQueryReqDto convert(TradeQueryVo tradeQueryVo, SessionVo sessionVo) {

        TradeQueryReqDto tradeQueryReqDto = new TradeQueryReqDto();
        tradeQueryReqDto.setAccountType(tradeQueryVo.getAccountType());
        tradeQueryReqDto.setAccountNo(tradeQueryVo.getAccountNo());
        tradeQueryReqDto.setBeginTime(tradeQueryVo.getBeginTime());
        tradeQueryReqDto.setEndTime(tradeQueryVo.getEndTime());
        tradeQueryReqDto.setState("4".equals(tradeQueryVo.getState()) ? null : tradeQueryVo.getState());
        //1：入账 2：出账  3：提现查询
        if (tradeQueryVo.getFlag() == NumberDict.FIVE) {
            tradeQueryReqDto.setFlag("");
        } else {
            tradeQueryReqDto.setFlag(String.valueOf(tradeQueryVo.getFlag()));
        }
        tradeQueryReqDto.setUserNo(sessionVo.getUserNo());
        tradeQueryReqDto.setCurrPageNum(tradeQueryVo.getCurrPageNum() - 1);
        tradeQueryReqDto.setPageSize(tradeQueryVo.getPageSize());
        return tradeQueryReqDto;
    }


    /**
     * 请求参数转换
     *
     * @param sessionVo   用户信息
     * @param accountType 账户类型
     * @return 结果
     */
    public static UserAccInfoReqDto convertToUserAccInfoReqDto(SessionVo sessionVo, String accountType) {
        UserAccInfoReqDto userAccInfoReqDto = new UserAccInfoReqDto();
        userAccInfoReqDto.setUserNo(sessionVo.getUserNo());
        userAccInfoReqDto.setCcy(accountType);
        return userAccInfoReqDto;

    }
}
