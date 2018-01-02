package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.dal.model.AreaInfoDo;
import com.baofu.international.global.account.core.dal.model.CityInfoDo;
import com.baofu.international.global.account.core.dal.model.ProvinceInfoDo;
import com.baofu.international.global.account.core.facade.model.AreaRespDto;
import com.baofu.international.global.account.core.facade.model.CityRespDto;
import com.baofu.international.global.account.core.facade.model.ProvinceRespDto;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

/**
 * 省市地区信息服务
 * <p>
 * 1、查询省信息
 * 2、查询市信息
 * 3、查询地区信息
 * </p>
 *
 * @author : wanght  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public final class AreaInfoRespConvert {

    private AreaInfoRespConvert() {

    }

    /**
     * 查询省信息结果转换
     *
     * @param list 省信息
     * @return 响应信息
     */
    public static List<ProvinceRespDto> provinceRespConvert(List<ProvinceInfoDo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<ProvinceRespDto> respDtoList = Lists.newArrayList();
        for (ProvinceInfoDo provinceInfoDo : list) {
            ProvinceRespDto provinceRespDto = new ProvinceRespDto();
            BeanUtils.copyProperties(provinceInfoDo, provinceRespDto);
            respDtoList.add(provinceRespDto);
        }

        return respDtoList;
    }

    /**
     * 查询市信息结果转换
     *
     * @param list 市信息
     * @return 响应信息
     */
    public static List<CityRespDto> cityRespConvert(List<CityInfoDo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<CityRespDto> respDtoList = Lists.newArrayList();
        for (CityInfoDo cityInfoDo : list) {
            CityRespDto cityRespDto = new CityRespDto();
            BeanUtils.copyProperties(cityInfoDo, cityRespDto);
            respDtoList.add(cityRespDto);
        }

        return respDtoList;
    }

    /**
     * 查询地区信息结果转换
     *
     * @param list 地区信息
     * @return 响应信息
     */
    public static List<AreaRespDto> areaRespConvert(List<AreaInfoDo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<AreaRespDto> respDtoList = Lists.newArrayList();
        for (AreaInfoDo tAreaInfoDo : list) {
            AreaRespDto areaRespDto = new AreaRespDto();
            BeanUtils.copyProperties(tAreaInfoDo, areaRespDto);
            respDtoList.add(areaRespDto);
        }

        return respDtoList;
    }
}
