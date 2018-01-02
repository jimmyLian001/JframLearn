package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.ManagerCategoryInfoMapper;
import com.baofu.international.global.account.core.dal.model.ManagerCategoryInfoDo;
import com.baofu.international.global.account.core.manager.ManagerCategoryInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 经营类别信息服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Component
public class ManagerCategoryInfoManagerImpl implements ManagerCategoryInfoManager {

    /**
     * 经营类别信息操作mapper
     */
    @Autowired
    private ManagerCategoryInfoMapper tManagerCategoryInfoMapper;

    /**
     * 查询经营类别信息
     *
     * @param categoryId 类别编码，可以为空
     * @return 经营类别信息列表
     */
    @Override
    public List<ManagerCategoryInfoDo> queryCategory(String categoryId) {
        return tManagerCategoryInfoMapper.queryCategory(categoryId);
    }
}
