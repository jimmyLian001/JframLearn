package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.dal.model.OccupationInfoDo;
import com.baofu.international.global.account.core.facade.model.OccupationRespDto;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

/**
 * 职业信息服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public final class OccupationInfoRespConvert {

    private OccupationInfoRespConvert() {

    }

    /**
     * 查询职业信息结果转换
     *
     * @param list 职业信息
     * @return 响应信息
     */
    public static List<OccupationRespDto> occupationRespConvert(List<OccupationInfoDo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<OccupationRespDto> respDtoList = Lists.newArrayList();
        for (OccupationInfoDo occupationInfoDo : list) {
            OccupationRespDto occupationRespDto = new OccupationRespDto();
            BeanUtils.copyProperties(occupationInfoDo, occupationRespDto);
            respDtoList.add(occupationRespDto);
        }

        return respDtoList;
    }
}
