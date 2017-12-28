package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.NotifyBiz;
import com.baofu.cbpayservice.biz.convert.ApiCbPayRemitNotifyConvert;
import com.baofu.cbpayservice.biz.models.ApiRemitCbPayNotfiyBo;
import com.baofu.cbpayservice.common.enums.RemitBusinessTypeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.FiCbPayMemberApiRqstManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2016/11/1 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CacheTest extends BaseTest {


    /**
     * Ma终端缓存
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    @Autowired
    private FiCbPayMemberApiRqstManager fiCbPayMemberApiRqstManager;

    @Autowired
    private NotifyBiz notifyBiz;

    @Test
    public void memberMember() throws Exception {

        FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = fiCbPayMemberApiRqstManager.queryReqInfoByBussNo("1710100941000391358");

        FiCbPayRemittanceDo fiCbPayRemittanceDo = cbPayRemittanceManager.queryRemittanceOrder("1710100941000391358");

        FiCbPayRemittanceAdditionDo fiCbPayRemittanceAdditionDo = cbPayRemittanceManager.queryRemittanceAddition(
                "1710100941000391358", fiCbPayRemittanceDo.getMemberNo());

        ApiRemitCbPayNotfiyBo apiRemitCbPayNotfiyBo = ApiCbPayRemitNotifyConvert.convertApiRemitCbPayNotfiyBo(
                fiCbPayRemittanceDo, fiCbPayMemberApiRqstDo, fiCbPayRemittanceAdditionDo, 4, "");
        notifyBiz.notifyMember(String.valueOf(fiCbPayMemberApiRqstDo.getMemberId()), String.valueOf(fiCbPayMemberApiRqstDo.getTerminalId()),
                String.valueOf(fiCbPayMemberApiRqstDo.getNotifyUrl()), apiRemitCbPayNotfiyBo, fiCbPayMemberApiRqstDo.getMemberReqId(),
                RemitBusinessTypeEnum.REMIT_APPLY.getCode());
    }
}
