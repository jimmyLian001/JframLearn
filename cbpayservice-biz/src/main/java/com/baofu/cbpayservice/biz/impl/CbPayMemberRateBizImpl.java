package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayMemberRateBiz;
import com.baofu.cbpayservice.biz.convert.CbPayMemberBizConvert;
import com.baofu.cbpayservice.biz.models.CbPayMemberRateReqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberRateDo;
import com.baofu.cbpayservice.manager.CbPayMemberRateManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 1、新增浮动汇率
 * 2、修改浮动汇率
 * </p>
 * User: yangjian  Date: 2017-05-15 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Component
public class CbPayMemberRateBizImpl implements CbPayMemberRateBiz {

    /**
     * 会员浮动汇率
     */
    @Autowired
    private CbPayMemberRateManager cbPayMememberRateManager;

    /**
     * 订单号生成接口
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 新增浮动汇率
     *
     * @param cbPayMemberRateReqBo
     */
    @Override
    @Transactional
    public void addMemberRate(CbPayMemberRateReqBo cbPayMemberRateReqBo) {
        log.info("新增浮动汇率参数{}", cbPayMemberRateReqBo);
        //新增
        String[] ccyArray = cbPayMemberRateReqBo.getCcy().split("\\" + Constants.SPLIT_MARK);
        for (String ccy : ccyArray) {
            cbPayMemberRateReqBo.setRecordId(orderIdManager.orderIdCreate());
            cbPayMemberRateReqBo.setCcy(ccy);
            cbPayMememberRateManager.addMemberRate(CbPayMemberBizConvert.paramConvert(cbPayMemberRateReqBo));
        }
        log.info("新增浮动汇成功");
    }

    /**
     * 更新浮动汇率
     *
     * @param cbPayMemberRateReqBo
     */
    @Override
    public void modifyMemberRate(CbPayMemberRateReqBo cbPayMemberRateReqBo) {

        log.info("修改浮动汇率参数{}", cbPayMemberRateReqBo);
        //1、根据recordId查询是否存在
        cbPayMememberRateManager.queryMemberRate(cbPayMemberRateReqBo.getRecordId());
        //2、开始更新
        cbPayMememberRateManager.modifyMemberRate(CbPayMemberBizConvert.paramConvert(cbPayMemberRateReqBo));
    }

    /**
     * 获取商户浮动汇率bp
     *
     * @param cbPayMemberRateReqBo 搜索参数
     * @return 返回结果
     */
    @Override
    public CbPayMemberRateReqBo queryMemberRateOne(CbPayMemberRateReqBo cbPayMemberRateReqBo) {

        FiCbPayMemberRateDo memberRateDo = cbPayMememberRateManager.queryMemberRateOne(
                CbPayMemberBizConvert.paramConvert(cbPayMemberRateReqBo));
        if (memberRateDo == null) {
            log.warn("获取商户浮动汇率bp 根据会员ID、币种和业务类型查询汇率信息为空，查询请求参数信息：{}",
                    cbPayMemberRateReqBo);
            return null;
        }
        return CbPayMemberBizConvert.paramConvert(memberRateDo);
    }

    /**
     * 根据会员ID和币种查询汇率列表
     *
     * @param cbPayMemberRateReqBo
     * @return
     */
    @Override
    public List<CbPayMemberRateReqBo> queryMemberRateList(CbPayMemberRateReqBo cbPayMemberRateReqBo) {
        List<CbPayMemberRateReqBo> retList = new ArrayList<>();
        //此查询结果会在商户设置同一币种、同一有效截止时间情况下有多条记录
        List<FiCbPayMemberRateDo> rateList = cbPayMememberRateManager.queryMemberRateList(CbPayMemberBizConvert.paramConvert(cbPayMemberRateReqBo));
        if (rateList == null || rateList.size() <= 0) {
            log.warn("根据会员ID和币种查询汇率信息为空，查询请求参数信息：{}", cbPayMemberRateReqBo);
            return null;
        }
        for (FiCbPayMemberRateDo memberRateDo : rateList) {
            retList.add(CbPayMemberBizConvert.paramConvert(memberRateDo));
        }
        return retList;
    }
}
