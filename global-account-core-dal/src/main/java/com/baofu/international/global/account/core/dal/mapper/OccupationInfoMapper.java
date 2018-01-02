package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.OccupationInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职业信息服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public interface OccupationInfoMapper {
    /**
     * 查询职业信息
     *
     * @param occupationId 职业编码，可以为空
     * @return 省信息列表
     */
    List<OccupationInfoDo> queryOccupation(@Param("occupationId") String occupationId);
}