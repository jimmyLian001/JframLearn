package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserAccountPaymentDetailBiz;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.convert.PaymentDetailBizConvert;
import com.baofu.international.global.account.core.biz.models.AccountDetailBo;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.model.PaymentDetailDo;
import com.baofu.international.global.account.core.dal.model.UserAccountBalDo;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.baofu.international.global.account.core.manager.TPayeePaymentDetailManager;
import com.baofu.international.global.account.core.manager.UserAccountBalManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 功能：用户自主注册平台收支明细处理
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserAccountPaymentDetailBizImpl implements UserAccountPaymentDetailBiz {

    /**
     * 宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 收款账户收支明细 Manager
     */
    @Autowired
    private TPayeePaymentDetailManager payeePaymentDetailManager;

    /**
     * 用户余额相关服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户账户相关
     */
    @Autowired
    private UserAccountBalManager userAccountBalManager;

    /**
     * 创建收款账户收支明细
     *
     * @param accountDetailBo 收款账户收支明细
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealPayeePaymentDetail(AccountDetailBo accountDetailBo) {

        //查询收款账户收支明细
        PaymentDetailDo detailDo = payeePaymentDetailManager.queryPaymentDetail(accountDetailBo.getBankRespNo(),
                accountDetailBo.getUserNo());
        //判断收款账户收支明细信息是否存在
        if (detailDo != null) {
            log.info("收款账户收支明细信息已存在，用户号:{},返回流水号:{}", accountDetailBo.getUserNo(), accountDetailBo.getBankRespNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190016);
        }
        //数据转换
        PaymentDetailDo paymentDetailDo = PaymentDetailBizConvert.toTPayeePaymentDetailDo(accountDetailBo);
        //更新账户可用余额
        UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(accountDetailBo.getUserNo(), accountDetailBo.getAccountNo());
        paymentDetailDo.setDetailId(orderIdManager.orderIdCreate());
        paymentDetailDo.setAccountNo(userAccountBalBo.getAccountNo());
        log.info("新增收款账户收支明细信息 {}", paymentDetailDo);
        payeePaymentDetailManager.addPaymentDetail(paymentDetailDo);
        //收入
        if (NumberDict.ONE == paymentDetailDo.getBalanceDirection()) {
            userAccountBalBo.setAccountBal((userAccountBalBo.getAccountBal().add(paymentDetailDo.getOrderAmt())).
                    setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
            userAccountBalBo.setAvailableAmt((userAccountBalBo.getAvailableAmt().add(paymentDetailDo.getOrderAmt())).
                    setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
            userAccountBalManager.modifyAccountBal(BeanCopyUtils.objectConvert(userAccountBalBo, UserAccountBalDo.class));
        }
    }
}
