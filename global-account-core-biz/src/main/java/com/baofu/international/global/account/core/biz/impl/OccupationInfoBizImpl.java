package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.OccupationInfoBiz;
import com.baofu.international.global.account.core.dal.model.OccupationInfoDo;
import com.baofu.international.global.account.core.manager.OccupationInfoManager;
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
public class OccupationInfoBizImpl implements OccupationInfoBiz {

    /**
     * 职业信息操作manager
     */
    @Autowired
    private OccupationInfoManager occupationInfoManager;

    /**
     * 查询职业信息
     *
     * @param occupationId 职业编码，可以为空
     * @return 省信息列表
     */
    @Override
    public List<OccupationInfoDo> queryOccupation(String occupationId) {
        return occupationInfoManager.queryOccupation(occupationId);
    }
}
