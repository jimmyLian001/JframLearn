package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * description：用户提现查询转换类
 * <p/>
 *
 * @author : liy on 2017/11/22
 * @version : 1.0.0
 */
public class UserWithdrawQueryConvert {

    private UserWithdrawQueryConvert() {

    }

    /**
     * 用户提现汇总查询转换类 ReqDto-->Do
     *
     * @param queryReqDto 请求参数
     * @return 结果集
     */
    public static UserWithdrawSumDo toUserWithdrawSumDo(UserWithdrawSumQueryReqDto queryReqDto) {

        UserWithdrawSumDo sumDo = new UserWithdrawSumDo();
        sumDo.setWithdrawBatchId(queryReqDto.getRemittanceSerialNo());
        sumDo.setChannelName(queryReqDto.getOverseasChannel());
        sumDo.setWithdrawCcy(queryReqDto.getCcy());
        sumDo.setBeginTime(queryReqDto.getBeginTime());
        sumDo.setEndTime(queryReqDto.getEndTime());
        sumDo.setTransferState(queryReqDto.getTransferState());
        sumDo.setPageNo(queryReqDto.getPageNo());
        sumDo.setPageSize(queryReqDto.getPageSize());
        return sumDo;
    }

    /**
     * 用户提现汇总返回转换类 pageData-->RespDto
     *
     * @param pageData 请求参数
     * @return 结果集
     */
    public static PageDataRespDto convertToSumList(Page<UserWithdrawSumDo> pageData) {

        PageDataRespDto<UserWithdrawSumQueryRespDto> pageDataRespDto = new PageDataRespDto<>();
        ArrayList<UserWithdrawSumQueryRespDto> list = Lists.newArrayList();
        for (UserWithdrawSumDo withdrawSumDo : pageData.getResult()) {
            UserWithdrawSumQueryRespDto tradeAccQueryRespDto = convertToDto(withdrawSumDo);
            list.add(tradeAccQueryRespDto);
        }
        pageDataRespDto.setResultList(list);
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 用户提现汇总结果转换类  do-->RespDto
     *
     * @param sumDo 请求参数
     * @return 结果集
     */
    public static UserWithdrawSumQueryRespDto convertToDto(UserWithdrawSumDo sumDo) {
        if (sumDo == null) {
            return null;
        }
        UserWithdrawSumQueryRespDto queryRespDto = new UserWithdrawSumQueryRespDto();
        queryRespDto.setWithdrawBatchId(sumDo.getWithdrawBatchId());
        queryRespDto.setChannelName(sumDo.getChannelName());
        queryRespDto.setWithdrawAmt(sumDo.getWithdrawAmt());
        queryRespDto.setWithdrawCcy(sumDo.getWithdrawCcy());
        queryRespDto.setDestAccNo(sumDo.getDestAccNo());
        queryRespDto.setTransferState(sumDo.getTransferState());
        queryRespDto.setCreateAt(sumDo.getCreateAt());
        queryRespDto.setDomesticChannel("平安");
        return queryRespDto;
    }

    /**
     * 用户提现申请查询转换类 ReqDto-->Do
     *
     * @param reqDto     请求参数
     * @param withdrawId 提现明细编号
     * @return 结果集
     */
    public static UserWithdrawApplyDo toUserWithdrawApplyDo(UserWithdrawApplyQueryReqDto reqDto, List<Long> withdrawId) {

        if (withdrawId.isEmpty()) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190020);
        }
        UserWithdrawApplyDo applyDo = new UserWithdrawApplyDo();
        applyDo.setUserNo(reqDto.getUserNo());
        applyDo.setWithdrawState(reqDto.getState());
        applyDo.setWithdrawId(reqDto.getOrderId());
        applyDo.setPageNo(reqDto.getPageNo());
        applyDo.setPageSize(reqDto.getPageSize());
        return applyDo;
    }

    /**
     * 用户提现申请查询返回转换类 pageData-->RespDto
     *
     * @param pageData 请求参数
     * @return 结果集
     */
    public static PageDataRespDto convertToApplyList(Page<UserWithdrawApplyDo> pageData) {

        PageDataRespDto<UserWithdrawApplyQueryRespDto> pageDataRespDto = new PageDataRespDto<>();
        ArrayList<UserWithdrawApplyQueryRespDto> list = Lists.newArrayList();
        for (UserWithdrawApplyDo applyDo : pageData.getResult()) {
            UserWithdrawApplyQueryRespDto applyQueryRespDto = convertToDto(applyDo);
            list.add(applyQueryRespDto);
        }
        pageDataRespDto.setResultList(list);
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 用户提现申请查询结果转换类  do-->RespDto
     *
     * @param applyDo 请求参数
     * @return 结果集
     */
    public static UserWithdrawApplyQueryRespDto convertToDto(UserWithdrawApplyDo applyDo) {

        if (applyDo == null) {
            return null;
        }
        UserWithdrawApplyQueryRespDto applyQueryRespDto = new UserWithdrawApplyQueryRespDto();
        applyQueryRespDto.setWithdrawId(applyDo.getWithdrawId());
        applyQueryRespDto.setUserNo(applyDo.getUserNo());
        //Do 用修改人来存储实名认证用户名
        applyQueryRespDto.setUserName(applyDo.getUpdateBy());
        applyQueryRespDto.setWithdrawAmt(applyDo.getWithdrawAmt());
        applyQueryRespDto.setWithdrawCcy(applyDo.getWithdrawCcy());
        applyQueryRespDto.setWithdrawFee(applyDo.getWithdrawFee());
        applyQueryRespDto.setTransferRate(applyDo.getTransferRate());
        applyQueryRespDto.setDestAmt(applyDo.getDestAmt());
        applyQueryRespDto.setSettleFee(applyDo.getSettleFee());
        if (applyDo.getDestAmt() != null && applyDo.getSettleFee() != null) {
            applyQueryRespDto.setArrivalAmt(applyDo.getDestAmt().subtract(applyDo.getSettleFee()));
        }
        applyQueryRespDto.setCreateAt(applyDo.getCreateAt());
        applyQueryRespDto.setWithdrawState(applyDo.getWithdrawState());
        applyQueryRespDto.setWithdrawAt(applyDo.getWithdrawAt());
        return applyQueryRespDto;
    }
}
