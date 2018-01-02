package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.AreaInfoDo;
import com.baofu.international.global.account.core.dal.model.CityInfoDo;
import com.baofu.international.global.account.core.dal.model.ProvinceInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省市地区信息服务
 * <p>
 * 1、查询省信息
 * 2、查询市信息
 * 3、查询地区信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public interface AreaInfoMapper {
    /**
     * 查询省信息
     *
     * @param provinceId 省编码，可以为空
     * @return 省信息列表
     */
    List<ProvinceInfoDo> queryProvince(@Param("provinceId") String provinceId);

    /**
     * 查询城市信息
     *
     * @param cityId 城市编码
     * @return 城市信息列表
     */
    List<CityInfoDo> queryCity(@Param("cityId") String cityId);

    /**
     * 查询地区信息
     *
     * @param areaId 地区编码
     * @return 地区信息列表
     */
    List<AreaInfoDo> queryArea(@Param("areaId") String areaId);
}