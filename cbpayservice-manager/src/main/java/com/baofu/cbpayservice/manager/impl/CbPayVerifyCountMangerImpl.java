package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbPayVerifyCountMapper;
import com.baofu.cbpayservice.dal.models.CbPayVerifyCountDo;
import com.baofu.cbpayservice.manager.CbPayVerifyCountManger;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 莫小阳 on 2017/5/31.
 */
@Slf4j
@Service
public class CbPayVerifyCountMangerImpl implements CbPayVerifyCountManger {

    /**
     * 实名认证服务接口
     */
    @Autowired
    private FiCbPayVerifyCountMapper fiCbPayVerifyCountMapper;


    @Override
    public void addCbPayVerifyCount(CbPayVerifyCountDo cbPayVerifyCount) {
        ParamValidate.checkUpdate(fiCbPayVerifyCountMapper.insert(cbPayVerifyCount), "新增实名认证信息异常");
    }

    @Override
    public CbPayVerifyCountDo queryVerifyCounByFileBatchNo(Long fileBathNo) {
        return fiCbPayVerifyCountMapper.selectVerifyCounByFileBatchNo(fileBathNo);
    }

    @Override
    public void updateVerifyCount(CbPayVerifyCountDo cbPayVerifyCountDo) {
        ParamValidate.checkUpdate(fiCbPayVerifyCountMapper.updateVerifyCount(cbPayVerifyCountDo), "更新实名认证信息异常");
    }

}
