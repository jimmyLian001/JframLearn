package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.dal.model.ManagerCategoryInfoDo;
import com.baofu.international.global.account.core.facade.model.ManagerCategoryRespDto;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

/**
 * 经营类别信息服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public final class ManagerCategoryInfoRespConvert {

    private ManagerCategoryInfoRespConvert() {

    }

    /**
     * 查询经营类别信息结果转换
     *
     * @param list 经营类别信息
     * @return 响应信息
     */
    public static List<ManagerCategoryRespDto> categoryRespConvert(List<ManagerCategoryInfoDo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<ManagerCategoryRespDto> respDtoList = Lists.newArrayList();
        for (ManagerCategoryInfoDo managerCategoryInfoDo : list) {
            ManagerCategoryRespDto managerCategoryRespDto = new ManagerCategoryRespDto();
            BeanUtils.copyProperties(managerCategoryInfoDo, managerCategoryRespDto);
            respDtoList.add(managerCategoryRespDto);
        }

        return respDtoList;
    }
}
