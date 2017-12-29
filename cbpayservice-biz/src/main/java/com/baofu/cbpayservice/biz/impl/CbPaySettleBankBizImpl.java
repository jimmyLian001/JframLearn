package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPaySettleBankBiz;
import com.baofu.cbpayservice.biz.convert.CbPaySettleBankBizConvert;
import com.baofu.cbpayservice.biz.models.CbPaySettleBankBo;
import com.baofu.cbpayservice.biz.models.ModifySettleBankBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.BankSettleStatus;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleBankDo;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 多币种账户信息biz接口实现
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPaySettleBankBizImpl implements CbPaySettleBankBiz {

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager cbPaySettleBankManager;

    /**
     * 宝付订单号创建
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 添加多币种账户信息
     *
     * @param cbPaySettleBankBo 多币种账户biz参数
     * @return 记录编号
     */
    @Override
    public Long addSettleBank(CbPaySettleBankBo cbPaySettleBankBo) {

        log.info("call 添加币种结算账户参数={}", cbPaySettleBankBo);
        Long recordId = orderIdManager.orderIdCreate();
        //查询当前币种最大的子账户编号
        String maxEntityNo = getSettleBankMaxEntityNo(cbPaySettleBankBo.getMemberId(), cbPaySettleBankBo.getSettleCcy());

        FiCbPaySettleBankDo cbPaySettleBankDo = CbPaySettleBankBizConvert.toFiCbPaySettleBankDo(cbPaySettleBankBo, maxEntityNo, recordId);
        log.info("call 添加多币种账户Do层参数:{}", cbPaySettleBankDo);
        cbPaySettleBankManager.addSettleBank(cbPaySettleBankDo);

        return recordId;
    }

    /**
     * 查询当前币种最大的子账户编号
     *
     * @param memberId  商户编号
     * @param settleCcy 结算币种
     * @return 返回最大的子账户编号
     */
    private String getSettleBankMaxEntityNo(Long memberId, String settleCcy) {
        //查询当前币种最大的子账户编号
        String maxEntityNo = cbPaySettleBankManager.queryMemberMaxEntityNo(memberId, settleCcy);
        String memberNo = memberId + "";
        if (StringUtils.isBlank(maxEntityNo)) {
            maxEntityNo = memberNo;
        } else {
            if (StringUtils.equals(maxEntityNo, memberNo)) {
                maxEntityNo = memberNo + "1" + StringUtils.leftPad(String.valueOf(1), 4, "0");
            } else {
                maxEntityNo = memberNo + "1" + StringUtils.leftPad(String.valueOf(Integer.parseInt(maxEntityNo.substring(memberNo.length() + 1)) + 1), 4, "0");
            }
        }
        return maxEntityNo;
    }

    /**
     * 修改商户币种账户信息
     *
     * @param modifySettleBankBo 修改商户币种账户信息
     */
    @Override
    public void modifySettleBank(ModifySettleBankBo modifySettleBankBo) {

        //判断商户是否存在币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = cbPaySettleBankManager.selectByRecordId(modifySettleBankBo.getRecordId());
        if (fiCbPaySettleBankDo == null) {
            log.info("call 修改商户币种账户信息，用户={}、币种={}信息不存在，不能修改。", modifySettleBankBo.getMemberId(),
                    modifySettleBankBo.getSettleCcy());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        //判断商户修改的币种是否存在，存在不让修改
        if (!fiCbPaySettleBankDo.getSettleCcy().equals(modifySettleBankBo.getSettleCcy())) {
            List<FiCbPaySettleBankDo> cbPaySettleBankDos = cbPaySettleBankManager.selectBankAccsByMIdCcy(modifySettleBankBo.getMemberId(),
                    modifySettleBankBo.getSettleCcy());
            if (cbPaySettleBankDos != null || !cbPaySettleBankDos.isEmpty()) {
                log.info("call 修改商户币种账户信息，用户={}、币种={}信息已存在，不能修改。", modifySettleBankBo.getMemberId(),
                        modifySettleBankBo.getSettleCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00129);
            }
        }

        //修改商户币种账户信息
        FiCbPaySettleBankDo cbPaySettleBankDo = CbPaySettleBankBizConvert.toFiCbPaySettleBankDo(modifySettleBankBo);
        cbPaySettleBankManager.modifySettleBank(cbPaySettleBankDo);
    }

    /**
     * 判断用户是否开通该币种
     *
     * @param memberId       商户号
     * @param targetCurrency 币种
     */
    @Override
    public void checkMemberCcy(Long memberId, String targetCurrency) {

        //判断用户是否已添加该币种
        List<FiCbPaySettleBankDo> fiCbPaySettleBankDos = cbPaySettleBankManager.selectBankAccsByMIdCcy(memberId, targetCurrency);
        if (fiCbPaySettleBankDos == null || fiCbPaySettleBankDos.isEmpty()) {
            log.info("call 商户币种账户信息，用户={}、币种={}信息不存在。", memberId, targetCurrency);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00130);
        }

        //判断商户对应币种的账户是否可结算
        for (FiCbPaySettleBankDo fiCbPaySettleBankDo : fiCbPaySettleBankDos) {
            //判断是否可结算
            if (BankSettleStatus.YES_SETTLE.getCode() == fiCbPaySettleBankDo.getSettleStatus()) {
                return;
            }
        }
        log.info("call 商户币种账户信息，用户={}、币种={}、结算状态={},不可结算。", memberId, targetCurrency,
                BankSettleStatus.NO_SETTLE.getCode());
        throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00131);
    }

    /**
     * @param memberId  商户号
     * @param bankAccNo 银行账户编号
     * @param remitCcy  币种
     * @return 商户备案主体编号
     */
    @Override
    public String queryMerchantEntityNo(Long memberId, String bankAccNo, String remitCcy) {
        return cbPaySettleBankManager.selectMerchantEntityNo(memberId, bankAccNo, remitCcy);
    }

    /**
     * 判断是否加锁或加锁
     *
     * @param memberId 商户号
     * @param ccy      币种
     * @return 加锁结果
     */
    private boolean isLock(Long memberId, String ccy) {
        String value = redisManager.queryObjectByKey(Constants.CBPAY_CREATE_SETTLE_BANK_ACC + memberId + ":" + ccy);
        if (FlagEnum.TRUE.getCode().equals(value)) {
            return Boolean.FALSE;
        }
        Boolean lockFlag = redisManager.lockRedis(Constants.CBPAY_CREATE_SETTLE_BANK_ACC + memberId + ":" + ccy,
                FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
        if (!lockFlag) {
            log.info("商户：{},币种：{}锁住失败，无法创建汇款订单，请稍后重试", memberId, ccy);
            return Boolean.FALSE;
        }
        log.info("商户：{}锁定成功", memberId);
        return Boolean.TRUE;
    }

    /**
     * 解锁
     *
     * @param memberId 商户号
     * @param ccy      商户币种
     */
    private void unLock(Long memberId, String ccy) {
        redisManager.deleteObject(Constants.CBPAY_CREATE_SETTLE_BANK_ACC + memberId + ":" + ccy);
    }

}
