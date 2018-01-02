package com.test.biz;

import com.baofu.international.global.account.core.biz.external.SettleApplyBizImpl;
import com.baofu.international.global.account.core.biz.models.SettleApplyBo;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/5 ProjectName:account-core  Version: 1.0
 */
public class SettleApplyBizTest extends Base {


    @Autowired
    private SettleApplyBizImpl settleApplyBiz;

    /**
     * API汇入申请测试
     */
    @Test(enabled = false)
    public void settleApplyTest() {

        SettleApplyBo settleApplyBo = new SettleApplyBo();
        settleApplyBo.setRemitReqNo("IRV992164233435");
        settleApplyBo.setOrderAmt(new BigDecimal("4490"));
        settleApplyBo.setOrderCcy("CNY");
        settleApplyBo.setPayeeAccount("10567835235134");
        settleApplyBo.setPayeeBankName("上海平安银行");
        settleApplyBo.setFileType("FTP");
        settleApplyBo.setRemitCountry("USA");
        settleApplyBo.setRemitAcc("32342299");
        settleApplyBo.setRemitName("BAOFOO TEST");
        settleApplyBo.setNotifyUrl("https://tcb.baofoo.com/test/notify.do");
        settleApplyBo.setVoucherFileName("");
        settleApplyBo.setDetailFileName("100024469_201711011627553173.txt");
        settleApplyBiz.settleApply(settleApplyBo);
    }

    /**
     * 汇入申请查询API测试
     */
    @Test
    public void settleApplyQueryTest() {
        settleApplyBiz.settleQuery("IRV992164233435");
    }
}
