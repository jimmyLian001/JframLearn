package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.dal.model.user.UserCompanyRespDO;
import com.baofu.international.global.account.core.facade.model.user.UserCompanyRespDTO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 用户转换类描述信息
 * <p>
 * 1、用户转换类
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public class UserConvert {

    private UserConvert() {
    }

    /**
     * 将企业用户DO 转成 DTO
     *
     * @param userCompanyRespDO 企业用户 DO
     * @return 企业用户响应DTO
     */
    public static UserCompanyRespDTO doConvertObj(UserCompanyRespDO userCompanyRespDO) {
        if (userCompanyRespDO == null) {
            return null;
        }
        UserCompanyRespDTO userCompanyRespDTO = new UserCompanyRespDTO();
        userCompanyRespDTO.setUserNo(userCompanyRespDO.getUserNo());
        userCompanyRespDTO.setName(userCompanyRespDO.getName());
        userCompanyRespDTO.setBeginTime(userCompanyRespDO.getBeginTime());
        userCompanyRespDTO.setEndTime(userCompanyRespDO.getEndTime());
        userCompanyRespDTO.setPhoneNumber(userCompanyRespDO.getPhoneNumber());
        userCompanyRespDTO.setEmail(userCompanyRespDO.getEmail());
        userCompanyRespDTO.setRealNameStatus(userCompanyRespDO.getRealNameStatus());
        userCompanyRespDTO.setOpenAccStatus(userCompanyRespDO.getOpenAccStatus());
        userCompanyRespDTO.setCreateAt(userCompanyRespDO.getCreateAt());
        return userCompanyRespDTO;
    }

    /**
     * 企业用户 list Do --> RespDTO
     *
     * @param listDo 请求参数
     * @return 结果集
     */
    public static List<UserCompanyRespDTO> doConvertList(List<UserCompanyRespDO> listDo) {

        List<UserCompanyRespDTO> resultList = Lists.newArrayList();
        for (UserCompanyRespDO userCompanyRespDO : listDo) {
            resultList.add(doConvertObj(userCompanyRespDO));
        }
        return resultList;
    }
}
