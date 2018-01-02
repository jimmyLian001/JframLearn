package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.ApplyAccountInfoBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountReqBo;
import com.baofu.international.global.account.core.facade.model.ApplyAccountRepDto;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-05 ProjectName:  Version: 1.0
 */
public final class ApplyAccountServiceConvert {

    private ApplyAccountServiceConvert() {
    }

    /**
     * 参数转换
     *
     * @param list list
     * @return 返回结果
     */
    public static List<ApplyAccountRepDto> paramConvert(List<ApplyAccountInfoBo> list) {

        List<ApplyAccountRepDto> result = Lists.newArrayList();
        for (ApplyAccountInfoBo applyAccountInfoBo : list) {
            ApplyAccountRepDto applyAccountRepDto = new ApplyAccountRepDto();
            applyAccountRepDto.setName(applyAccountInfoBo.getName());
            applyAccountRepDto.setEnName(applyAccountInfoBo.getEnName());
            applyAccountRepDto.setLegalNo(applyAccountInfoBo.getLegalNo());
            applyAccountRepDto.setRealNameStatus(applyAccountInfoBo.getRealNameStatus());
            applyAccountRepDto.setUserInfoNo(applyAccountInfoBo.getUserInfoNo());
            applyAccountRepDto.setQualifiedNo(applyAccountInfoBo.getQualifiedNo());
            result.add(applyAccountRepDto);
        }
        return result;
    }

    /**
     * 转换请求参数
     *
     * @param applyAccountReqDto applyAccountReqDto
     * @return 返回结果
     */
    public static ApplyAccountReqBo paramConvert(ApplyAccountReqDto applyAccountReqDto) {

        ApplyAccountReqBo applyAccountReqBo = new ApplyAccountReqBo();
        applyAccountReqBo.setUserNo(applyAccountReqDto.getUserNo());
        applyAccountReqBo.setCcy(applyAccountReqDto.getCcy());
        applyAccountReqBo.setStoreName(applyAccountReqDto.getStoreName());
        applyAccountReqBo.setStorePlatform(applyAccountReqDto.getStorePlatform());
        applyAccountReqBo.setUserType(applyAccountReqDto.getUserType());
        applyAccountReqBo.setStoreExist(applyAccountReqDto.getStoreExist());
        applyAccountReqBo.setQualifiedNo(applyAccountReqDto.getQualifiedNo());
        applyAccountReqBo.setManagementCategory(applyAccountReqDto.getManagementCategory());
        applyAccountReqBo.setSecretKey(applyAccountReqDto.getSecretKey());
        applyAccountReqBo.setAwsAccessKey(applyAccountReqDto.getAwsAccessKey());
        applyAccountReqBo.setSellerId(applyAccountReqDto.getSellerId());
        applyAccountReqBo.setUserType(applyAccountReqDto.getUserType());

        return applyAccountReqBo;
    }
}
