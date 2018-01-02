package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.AccQualifiedDo;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-22 ProjectName:  Version: 1.0
 */
public interface AccQualifiedManager {

    /**
     * 根据用户号查询资质信息
     *
     * @param userNo userNo
     * @return
     */
    List<AccQualifiedDo> queryQualifiedByUserNo(Long userNo);

    /**
     * 根据申请号查询资质信息
     *
     * @param qualifiedNo qualifiedNo
     * @return 返回结果
     */
    AccQualifiedDo queryQualifiedByAQualifiedNo(Long qualifiedNo);
}
