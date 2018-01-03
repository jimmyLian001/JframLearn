package com.baofu.international.global.account.client.web.convert;

import com.baofu.international.global.account.client.web.models.AreaInfo;
import com.baofu.international.global.account.client.web.models.CityInfo;
import com.baofu.international.global.account.client.web.models.ProvinceInfo;
import com.baofu.international.global.account.core.facade.model.AreaRespDto;
import com.baofu.international.global.account.core.facade.model.CityRespDto;
import com.baofu.international.global.account.core.facade.model.ProvinceRespDto;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

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
public final class AreaInfoConvert {

    private AreaInfoConvert() {

    }

    /**
     * 查询省信息结果转换
     *
     * @param list 省信息
     * @return 响应信息
     */
    public static List<ProvinceInfo> provinceInfoConvert(List<ProvinceRespDto> list) {
        List<ProvinceInfo> respDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) {
            return respDtoList;
        }
        for (ProvinceRespDto provinceRespDto : list) {
            ProvinceInfo provinceInfo = new ProvinceInfo();
            BeanUtils.copyProperties(provinceRespDto, provinceInfo);
            respDtoList.add(provinceInfo);
        }

        return respDtoList;
    }

    /**
     * 查询市信息结果转换
     *
     * @param list 市信息
     * @return 响应信息
     */
    public static List<CityInfo> cityInfoConvert(List<CityRespDto> list) {
        List<CityInfo> respDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) {
            return respDtoList;
        }
        for (CityRespDto cityRespDto : list) {
            CityInfo cityInfo = new CityInfo();
            BeanUtils.copyProperties(cityRespDto, cityInfo);
            respDtoList.add(cityInfo);
        }

        return respDtoList;
    }

    /**
     * 查询地区信息结果转换
     *
     * @param list 地区信息
     * @return 响应信息
     */
    public static List<AreaInfo> areaInfoConvert(List<AreaRespDto> list) {
        List<AreaInfo> respDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) {
            return respDtoList;
        }
        for (AreaRespDto areaRespDto : list) {
            AreaInfo areaInfo = new AreaInfo();
            BeanUtils.copyProperties(areaRespDto, areaInfo);
            respDtoList.add(areaInfo);
        }

        return respDtoList;
    }
}
