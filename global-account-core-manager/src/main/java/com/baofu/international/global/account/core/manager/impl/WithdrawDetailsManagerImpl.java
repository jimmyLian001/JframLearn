package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawDistributeMapper;
import com.baofu.international.global.account.core.dal.model.ChannelWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDetailsQueryDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDistributeDo;
import com.baofu.international.global.account.core.manager.WithdrawDetailsManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.commons.result.PageRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现明细管理后台查询Manager 实现
 * <p>
 * 1.用户提现明细 后台查询
 * 2.渠道提现明细 后台查询
 * 3.提现下发 后台查询
 * </p>
 *
 * @author dxy
 * @date 2017/11/21
 */
@Slf4j
@Repository
public class WithdrawDetailsManagerImpl implements WithdrawDetailsManager {


    /**
     * 提现明细查询mapper
     */
    @Autowired
    private UserWithdrawApplyMapper tUserWithdrawApplyMapper;

    /**
     * 提现下发查询mapper
     */
    @Autowired
    private UserWithdrawDistributeMapper tUserWithdrawDistributeMapper;


    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户提现明细查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    @Override
    public PageRespDTO<UserWithdrawDetailsDo> userWithdrawDetailsQuery(WithdrawDetailsQueryDo req) {

        PageHelper.startPage(req.getCurrentPage(), req.getPageCount(), "CREATE_AT desc");
        List<UserWithdrawDetailsDo> respList = tUserWithdrawApplyMapper.selectUserWithdrawDetails(req);
        PageInfo<UserWithdrawDetailsDo> page = new PageInfo<>(respList);
        return new PageRespDTO<>((int) page.getTotal(), req.getPageCount(), respList);

    }

    /**
     * 渠道提现明细查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    @Override
    public PageRespDTO<ChannelWithdrawDetailsDo> channelWithdrawDetailsQuery(WithdrawDetailsQueryDo req) {

        PageHelper.startPage(req.getCurrentPage(), req.getPageCount(), "D.CREATE_AT desc");
        List<ChannelWithdrawDetailsDo> respList = tUserWithdrawApplyMapper.selectChannelWithdrawDetails(req);
        for (ChannelWithdrawDetailsDo temp : respList) {
            if ("Wyre".equals(temp.getChannel()) || "WYRE".equals(temp.getChannel())) {
                temp.setBaoFuFeeRate(new BigDecimal(configDict.getWyreChannelFeeRate()));
                temp.setBaoFuFee(temp.getBaoFuFeeRate().multiply(temp.getWithdrawAmount()));
            }
        }
        PageInfo<ChannelWithdrawDetailsDo> page = new PageInfo<>(respList);
        return new PageRespDTO<>((int) page.getTotal(), req.getPageCount(), respList);

    }

    /**
     * 提现下发查询
     *
     * @param req 请求参数
     * @return PageRespDTO
     */
    @Override
    public PageRespDTO<WithdrawDistributeDo> withdrawDistributeQuery(WithdrawDetailsQueryDo req) {
        PageHelper.startPage(req.getCurrentPage(), req.getPageCount(), "D.CREATE_AT desc");
        List<WithdrawDistributeDo> respList = tUserWithdrawDistributeMapper.selectWithdrawDistribute(req);
        for (WithdrawDistributeDo tmp : respList) {
            tmp.setBankCardNo(SecurityUtil.desDecrypt(tmp.getBankCardNo(), Constants.CARD_DES_KEY));
        }
        PageInfo<WithdrawDistributeDo> page = new PageInfo<>(respList);
        return new PageRespDTO<>((int) page.getTotal(), req.getPageCount(), respList);
    }
}
