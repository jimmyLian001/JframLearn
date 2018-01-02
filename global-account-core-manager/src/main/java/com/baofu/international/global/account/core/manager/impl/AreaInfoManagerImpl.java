package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.AreaInfoMapper;
import com.baofu.international.global.account.core.dal.model.AreaInfoDo;
import com.baofu.international.global.account.core.dal.model.CityInfoDo;
import com.baofu.international.global.account.core.dal.model.ProvinceInfoDo;
import com.baofu.international.global.account.core.manager.AreaInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 个人信息服务
 * <p>
 * 1、添加认证个人信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Component
public class AreaInfoManagerImpl implements AreaInfoManager {

    /**
     * 个人认证信息操作mapper
     */
    @Autowired
    private AreaInfoMapper tAreaInfoMapper;

    /**
     * 查询省信息
     *
     * @param provinceId 省编码，可以为空
     * @return 省信息列表
     */
    @Override
    public List<ProvinceInfoDo> queryProvince(String provinceId) {
        return tAreaInfoMapper.queryProvince(provinceId);
    }

    /**
     * 查询城市信息
     *
     * @param cityId 城市编码
     * @return 城市信息列表
     */
    @Override
    public List<CityInfoDo> queryCity(String cityId) {
        return tAreaInfoMapper.queryCity(cityId);
    }

    /**
     * 查询地区信息
     *
     * @param areaId 地区编码
     * @return 地区信息列表
     */
    @Override
    public List<AreaInfoDo> queryArea(String areaId) {
        return tAreaInfoMapper.queryArea(areaId);
    }
}
