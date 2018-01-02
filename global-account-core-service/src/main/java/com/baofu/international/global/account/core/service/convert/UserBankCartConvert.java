package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.dal.model.SysBankInfoDo;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 用户银行卡转换类描述信息
 * <p>
 * 1、用户银行卡转换类
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public class UserBankCartConvert {

    private UserBankCartConvert() {
    }

    /**
     * 将用户银行卡Do 转 DTO
     *
     * @param userBankCardInfoDo 用户银行卡Do
     * @return 用户银行卡DTO
     */
    public static UserBankCardInfoDto doConvertObj(UserBankCardInfoDo userBankCardInfoDo) {

        UserBankCardInfoDto userBankCardInfoDto = new UserBankCardInfoDto();
        userBankCardInfoDto.setRecordNo(userBankCardInfoDo.getRecordNo());
        userBankCardInfoDto.setId(userBankCardInfoDo.getId());
        userBankCardInfoDto.setUserNo(userBankCardInfoDo.getUserNo());
        userBankCardInfoDto.setAccType(userBankCardInfoDo.getAccType());
        userBankCardInfoDto.setCardHolder(userBankCardInfoDo.getCardHolder());
        userBankCardInfoDto.setCardNo(userBankCardInfoDo.getCardNo());
        userBankCardInfoDto.setBankName(userBankCardInfoDo.getBankName());
        userBankCardInfoDto.setCardType(userBankCardInfoDo.getCardType());
        userBankCardInfoDto.setState(userBankCardInfoDo.getState());
        userBankCardInfoDto.setRemarks(userBankCardInfoDo.getRemarks());
        userBankCardInfoDto.setCreateAt(userBankCardInfoDo.getCreateAt());
        userBankCardInfoDto.setCreateBy(userBankCardInfoDo.getCreateBy());
        userBankCardInfoDto.setUpdateBy(userBankCardInfoDo.getUpdateBy());
        userBankCardInfoDto.setUpdateAt(userBankCardInfoDo.getUpdateAt());
        userBankCardInfoDto.setBankCode(userBankCardInfoDo.getBankCode());

        return userBankCardInfoDto;
    }

    /**
     * 用户银行卡 DO 转 DTO
     *
     * @param userBankCardInfoList 用户银行卡列表
     * @return 用户银行卡DTO
     */
    public static List<UserBankCardInfoDto> convertList(List<UserBankCardInfoDo> userBankCardInfoList) {
        List<UserBankCardInfoDto> resList = Lists.newArrayList();
        for (UserBankCardInfoDo userBankCardInfoDo : userBankCardInfoList) {
            resList.add(UserBankCartConvert.doConvertObj(userBankCardInfoDo));
        }
        return resList;
    }

    /**
     * 转化为系统支持银行卡信息
     *
     * @param sysBankInfoDoList sysBankInfoDoList
     * @return resList
     */
    public static List<TSysBankInfoDto> convertToSysBankInfoList(List<SysBankInfoDo> sysBankInfoDoList) {
        List<TSysBankInfoDto> resList = Lists.newArrayList();
        for (SysBankInfoDo list : sysBankInfoDoList) {
            resList.add(UserBankCartConvert.doTSysBankInfoDto(list));
        }
        return resList;
    }

    /**
     * 将银行DO 转 DTO
     *
     * @param sysBankInfoDo 银行DO
     * @return 银行DTO
     */
    public static TSysBankInfoDto doTSysBankInfoDto(SysBankInfoDo sysBankInfoDo) {
        TSysBankInfoDto sysBankInfoDto = new TSysBankInfoDto();
        sysBankInfoDto.setBankAbbreName(sysBankInfoDo.getBankAbbreName());
        sysBankInfoDto.setBankCode(sysBankInfoDo.getBankCode());
        sysBankInfoDto.setBankName(sysBankInfoDo.getBankName());
        sysBankInfoDto.setState(sysBankInfoDo.getState());
        sysBankInfoDto.setIconCode(sysBankInfoDo.getIconCode());
        return sysBankInfoDto;
    }

}
