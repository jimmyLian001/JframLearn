package com.test.biz;

import com.baofu.international.global.account.core.biz.UserWithdrawCbPayDealBiz;
import com.baofu.international.global.account.core.biz.models.UserSettleApplyRespBo;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述:商户提现测试
 * </p>
 * User: feng_jiang  Date: 2017/11/10 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawCbPayDealBizTest extends Base {

    /**
     * 用户提现与跨境API交互服务
     */
    @Autowired
    private UserWithdrawCbPayDealBiz userWithdrawCbPayDealBiz;


    /**
     * 发起结汇申请API请求
     */
    @Test
    public void processSettleAPITest() {
        Long withdrawBatchId = 1711170900000002315L;
        String fileName = "test.xls";
        userWithdrawCbPayDealBiz.processSettleAPI(withdrawBatchId, fileName);
    }

    /**
     * 处理结汇申请API请求
     */
    @Test
    public void dealSettleAPIResultTest() {
        UserSettleApplyRespBo userSettleApplyRespBo = new UserSettleApplyRespBo();
        userSettleApplyRespBo.setRemitReqNo("1711241305000181639");
        userSettleApplyRespBo.setStatus("2");
        userWithdrawCbPayDealBiz.dealSettleAPIResult(userSettleApplyRespBo);
    }
}
