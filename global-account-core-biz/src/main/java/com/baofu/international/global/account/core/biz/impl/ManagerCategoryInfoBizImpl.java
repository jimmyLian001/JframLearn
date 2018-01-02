package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.ManagerCategoryInfoBiz;
import com.baofu.international.global.account.core.dal.model.ManagerCategoryInfoDo;
import com.baofu.international.global.account.core.manager.ManagerCategoryInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职业信息服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Service
@Slf4j
public class ManagerCategoryInfoBizImpl implements ManagerCategoryInfoBiz {

    /**
     * 职业信息操作manager
     */
    @Autowired
    private ManagerCategoryInfoManager managerCategoryInfoManager;

    /**
     * 查询经营类别信息
     *
     * @param categoryId 类别编码，可以为空
     * @return 经营类别信息列表
     */
    @Override
    public List<ManagerCategoryInfoDo> queryCategory(String categoryId) {
        return managerCategoryInfoManager.queryCategory(categoryId);
    }
}
