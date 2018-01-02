package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.AreaInfoBiz;
import com.baofu.international.global.account.core.dal.model.AreaInfoDo;
import com.baofu.international.global.account.core.dal.model.CityInfoDo;
import com.baofu.international.global.account.core.dal.model.ProvinceInfoDo;
import com.baofu.international.global.account.core.manager.AreaInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
@Slf4j
public class AreaInfoBizImpl implements AreaInfoBiz {

    /**
     * 省市地区信息操作manager
     */
    @Autowired
    private AreaInfoManager areaInfoManager;

    /**
     * 查询省信息
     *
     * @param provinceId 省编码，可以为空
     * @return 省信息列表
     */
    @Override
    public List<ProvinceInfoDo> queryProvince(String provinceId) {
        return areaInfoManager.queryProvince(provinceId);
    }

    /**
     * 查询城市信息
     *
     * @param cityId 城市编码
     * @return 城市信息列表
     */
    @Override
    public List<CityInfoDo> queryCity(String cityId) {
        return areaInfoManager.queryCity(cityId);
    }

    /**
     * 查询地区信息
     *
     * @param areaId 地区编码
     * @return 地区信息列表
     */
    @Override
    public List<AreaInfoDo> queryArea(String areaId) {
        return areaInfoManager.queryArea(areaId);
    }
}
