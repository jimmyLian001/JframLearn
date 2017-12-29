package com.baofu.cbpayservice.biz.impl;


import com.baofu.cbpayservice.biz.CbPaySettleAccountBiz;
import com.baofu.cbpayservice.biz.models.CbPaySettleAccountBo;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPaySettleAccountDo;
import com.baofu.cbpayservice.manager.FiCbPaySettleAccountManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 结汇操作结汇账户管理业务逻辑实现接口实现
 * <p>
 * User: lian zd Date:2017/7/31 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service("CbPaySettleAccountBizImpl")
public class CbPaySettleAccountBizImpl implements CbPaySettleAccountBiz {

    /**
     * 创建宝付订内部编号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 结汇账户管理数据操作
     */
    @Autowired
    private FiCbPaySettleAccountManager fiCbPaySettleAccountManager;

    /**
     * 结汇账户管理新增账户信息
     *
     * @param cbPaySettleAccountBo 商户新增账户信息传入对象
     */
    @Override
    public Long addSettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo) {
        Long recordId = orderIdManager.orderIdCreate();
        Date now = new Date();
        FiCbPaySettleAccountDo fiCbPaySettleAccountDo = new FiCbPaySettleAccountDo();
        fiCbPaySettleAccountDo.setDeleteFlag(1);
        fiCbPaySettleAccountDo.setIncomeAccountName(cbPaySettleAccountBo.getIncomeAccountName());
        fiCbPaySettleAccountDo.setIncomeAccountNo(cbPaySettleAccountBo.getIncomeAccountNo());
        fiCbPaySettleAccountDo.setIncomeCountry(cbPaySettleAccountBo.getIncomeCountry());
        fiCbPaySettleAccountDo.setMemberId(cbPaySettleAccountBo.getMemberId());
        fiCbPaySettleAccountDo.setRecordId(recordId);
        fiCbPaySettleAccountDo.setCreateAt(now);
        fiCbPaySettleAccountDo.setCreateBy(cbPaySettleAccountBo.getCreateBy());
        fiCbPaySettleAccountDo.setUpdateAt(now);
        fiCbPaySettleAccountDo.setUpdateBy(cbPaySettleAccountBo.getCreateBy());
        log.info("结汇账户管理新增账户信息对象：{}", fiCbPaySettleAccountDo);

        //查询结汇账户信息
        List<FiCbPaySettleAccountDo> settleAccountDo = fiCbPaySettleAccountManager
                .listSettleAccount(fiCbPaySettleAccountDo);

        //如果结算账户信息已经存在直接返回 recordId
        if (settleAccountDo != null && settleAccountDo.size() > 0) {
            return settleAccountDo.get(0).getRecordId();
        }
        //结汇账户管理新增账户信息
        fiCbPaySettleAccountManager.addSettleAccount(fiCbPaySettleAccountDo);
        return recordId;
    }

    /**
     * 结汇账户管理修改账户信息
     *
     * @param cbPaySettleAccountBo 商户修改账户信息传入对象
     * @param recordId             商户修改账户信息标志ID
     */
    @Override
    public void modifySettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo, Long recordId) {
        FiCbPaySettleAccountDo fiCbPaySettleAccountDo = new FiCbPaySettleAccountDo();
        fiCbPaySettleAccountDo.setRecordId(recordId);
        fiCbPaySettleAccountDo.setIncomeAccountName(cbPaySettleAccountBo.getIncomeAccountName());
        fiCbPaySettleAccountDo.setIncomeAccountNo(cbPaySettleAccountBo.getIncomeAccountNo());
        fiCbPaySettleAccountDo.setIncomeCountry(cbPaySettleAccountBo.getIncomeCountry());
        fiCbPaySettleAccountDo.setDeleteFlag(1);
        fiCbPaySettleAccountDo.setUpdateBy(cbPaySettleAccountBo.getCreateBy());
        log.info("结汇账户管理修改账户信息对象：{}", fiCbPaySettleAccountDo);
        fiCbPaySettleAccountManager.modifySettleAccount(fiCbPaySettleAccountDo);
    }

    /**
     * 结汇账户管理删除账户信息
     *
     * @param cbPaySettleAccountBo 商户删除账户信息传入对象
     * @param recordId             商户删除账户信息标志ID
     */
    @Override
    public void deleteSettleAccount(CbPaySettleAccountBo cbPaySettleAccountBo, Long recordId) {
        FiCbPaySettleAccountDo fiCbPaySettleAccountDo = new FiCbPaySettleAccountDo();
        fiCbPaySettleAccountDo.setDeleteFlag(0);
        fiCbPaySettleAccountDo.setRecordId(recordId);
        fiCbPaySettleAccountDo.setUpdateBy(cbPaySettleAccountBo.getCreateBy());
        log.info("结汇账户管理删除账户信息对象：{}", fiCbPaySettleAccountDo);
        fiCbPaySettleAccountManager.modifySettleAccount(fiCbPaySettleAccountDo);
    }

    /**
     * 根据recordId查询该数据是否存在
     *
     * @param recordId 商户账户信息标志ID
     */
    public void checkSettleAccount(Long recordId) {
        FiCbPaySettleAccountDo fiCbPaySettleAccountDo = fiCbPaySettleAccountManager.queryRecordIdExist(recordId);
        if (fiCbPaySettleAccountDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00173);
        }
        log.info("结汇账户信息删除标志：{}", fiCbPaySettleAccountDo.getDeleteFlag());
        if (fiCbPaySettleAccountDo.getDeleteFlag() == 0) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00174);
        }
    }

}
