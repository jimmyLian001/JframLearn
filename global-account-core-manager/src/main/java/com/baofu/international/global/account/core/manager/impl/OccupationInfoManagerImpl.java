package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.OccupationInfoMapper;
import com.baofu.international.global.account.core.dal.model.OccupationInfoDo;
import com.baofu.international.global.account.core.manager.OccupationInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 职业信息服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Component
public class OccupationInfoManagerImpl implements OccupationInfoManager {

    /**
     * 个人认证信息操作mapper
     */
    @Autowired
    private OccupationInfoMapper tOccupationInfoMapper;

    /**
     * 查询职业信息
     *
     * @param occupationId 职业编码，可以为空
     * @return 省信息列表
     */
    @Override
    public List<OccupationInfoDo> queryOccupation(String occupationId) {
        return tOccupationInfoMapper.queryOccupation(occupationId);
    }
}
