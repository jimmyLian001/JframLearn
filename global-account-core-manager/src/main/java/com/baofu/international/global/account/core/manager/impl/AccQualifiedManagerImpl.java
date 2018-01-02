package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.dal.mapper.AccQualifiedMapper;
import com.baofu.international.global.account.core.dal.model.AccQualifiedDo;
import com.baofu.international.global.account.core.manager.AccQualifiedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-22 ProjectName:  Version: 1.0
 */
@Component
public class AccQualifiedManagerImpl implements AccQualifiedManager {

    /**
     * 资质表
     */
    @Autowired
    private AccQualifiedMapper accQualifiedMapper;

    @Override
    public List<AccQualifiedDo> queryQualifiedByUserNo(Long userNo) {
        return accQualifiedMapper.selectByUserNo(userNo);
    }

    /**
     * 根据申请号查询资质信息
     *
     * @param qualifiedNo qualifiedNo
     * @return 返回结果
     */
    @Override
    public AccQualifiedDo queryQualifiedByAQualifiedNo(Long qualifiedNo) {
        return accQualifiedMapper.selectByQualified(qualifiedNo);
    }
}
