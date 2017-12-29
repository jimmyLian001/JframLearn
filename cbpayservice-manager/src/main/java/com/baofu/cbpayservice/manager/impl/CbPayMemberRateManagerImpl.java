package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.MemberRateEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayMemberRateMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberRateDo;
import com.baofu.cbpayservice.manager.CbPayMemberRateManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * l、新增浮动汇率
 * 2、修改相应会员的汇率或状态
 * 3、查询浮动汇率
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
@Slf4j
@Repository
public class CbPayMemberRateManagerImpl implements CbPayMemberRateManager {

    /**
     * 会员浮动汇率信息Mapper
     */
    @Autowired
    private FiCbPayMemberRateMapper fiCbPayMemberRateMapper;

    /**
     * 新增浮动汇率
     *
     * @param fiCbPayMemberRateDo 插入数据对象
     */
    @Override
    public void addMemberRate(FiCbPayMemberRateDo fiCbPayMemberRateDo) {

        ParamValidate.checkUpdate(fiCbPayMemberRateMapper.addMemberRateDo(fiCbPayMemberRateDo),
                "浮动汇率新增异常");
    }

    /**
     * 根据record_id去更新会员浮动汇率的状态和汇率值
     *
     * @param fiCbPayMemberRateDo
     */
    @Override
    public void modifyMemberRate(FiCbPayMemberRateDo fiCbPayMemberRateDo) {

        ParamValidate.checkUpdate(fiCbPayMemberRateMapper.updateByRecordId(fiCbPayMemberRateDo),
                "浮动汇率更新异常");
    }

    /**
     * 根据记录编号查询会员的浮动汇率
     *
     * @param recordId 记录编号
     * @return
     */
    @Override
    public FiCbPayMemberRateDo queryMemberRate(Long recordId) {

        log.info("查询浮动汇率recordId参数为{}", recordId);
        FiCbPayMemberRateDo cbPayMemberRateDo = fiCbPayMemberRateMapper.selectByRecordId(recordId);
        if (cbPayMemberRateDo == null) {
            log.warn("无法根据该recordId：{},查询到该记录", recordId);
            throw new BizServiceException(MemberRateEnum.CBPYA_MEMBER_RATE_ERROR_002);
        }
        return cbPayMemberRateDo;
    }

    /**
     * 获取商户浮动汇率bp
     *
     * @param fiCbPayMemberRateDo 搜索参数
     * @return 返回结果
     */
    @Override
    public FiCbPayMemberRateDo queryMemberRateOne(FiCbPayMemberRateDo fiCbPayMemberRateDo) {

        return fiCbPayMemberRateMapper.selectMemberRateOne(fiCbPayMemberRateDo);
    }

    /**
     * 查询会员相应的币种浮动汇率列表
     *
     * @param fiCbPayMemberRateDo
     * @return
     */
    @Override
    public List<FiCbPayMemberRateDo> queryMemberRateList(FiCbPayMemberRateDo fiCbPayMemberRateDo) {
        return fiCbPayMemberRateMapper.selectMemberRateList(fiCbPayMemberRateDo);
    }
}
