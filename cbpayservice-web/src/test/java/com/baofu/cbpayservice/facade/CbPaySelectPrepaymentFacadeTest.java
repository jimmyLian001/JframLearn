package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.CbPayCmBiz;
import com.baofu.cbpayservice.biz.CbPaySettlePrepaymentBiz;
import com.baofu.cbpayservice.biz.models.CbPaySettleBo;
import com.baofu.cbpayservice.dal.models.CbPaySettlePrepaymentDo;
import com.baofu.cbpayservice.facade.models.CbPaySettlePrepaymentDto;
import com.baofu.cbpayservice.manager.FiCbPaySettlePrepaymentManager;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
public class CbPaySelectPrepaymentFacadeTest extends BaseTest {

    @Autowired
    private CbPaySettlePrepaymentBiz cbPaySettlePrepaymentBiz;

    @Autowired
    private CbPaySettlePrepaymentFacade cbPaySettlePrepaymentFacade;

    @Autowired
    private FiCbPaySettlePrepaymentManager fiCbPaySettlePrepaymentManager;

    /**
     * 计费服务
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    @Test
    public void prepaymentApply() {

        cbPaySettlePrepaymentFacade.prepaymentApply("whdxnJNo", UUID.randomUUID().toString());
    }

    @Test
    public void prepaymentVerify() {
        cbPaySettlePrepaymentFacade.prepaymentVerify(1709041035000006380L, 2, "test", UUID.randomUUID().toString());
    }

    @Test
    public void auto() {
        cbPaySettlePrepaymentBiz.autoSettlePrepay(100018529l, "whdxnJNo");
    }

    @Test
    public void recharge() {
        CbPaySettleBo cbPaySettleBo = new CbPaySettleBo();
        cbPaySettleBo.setMemberId(100018529l);
        cbPaySettleBo.setChannelId(1200921001l);
        cbPaySettleBo.setSettleCcy("CNY");
        cbPaySettleBo.setMemberSettleAmt(new BigDecimal("632.00"));
        cbPaySettleBo.setOrderId(1708212111000000900l);
        cbPayCmBiz.prepaymentAccRecharge(cbPaySettleBo);
    }


    @Test
    public void modifyByIncomeNo() {
        CbPaySettlePrepaymentDo cbPaySettlePrepaymentDo = new CbPaySettlePrepaymentDo();
        cbPaySettlePrepaymentDo.setIncomeNo("whdxnJNo");
        cbPaySettlePrepaymentDo.setPreStatus(2); // 2- 垫资已返还
        fiCbPaySettlePrepaymentManager.modifyByIncomeNo(cbPaySettlePrepaymentDo);
    }

    @Test
    public void calculateSettleAmt() {
        Result<CbPaySettlePrepaymentDto> result = cbPaySettlePrepaymentFacade.calculateSettleAmt("HH23003778", UUID.randomUUID().toString());
        System.out.println(result);
    }


}
