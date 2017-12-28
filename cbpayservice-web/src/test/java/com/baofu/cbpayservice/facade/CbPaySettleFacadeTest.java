package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.facade.models.ReceiverAuditDto;
import com.baofu.cbpayservice.facade.models.SettleIncomeApplyDto;
import com.baofu.cbpayservice.facade.models.SettlePaymentFileUploadDto;
import com.system.commons.result.Result;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 描述
 * <p>
 * 1、方法备注
 * </p>
 * User: 香克斯 Date:2017/9/10 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CbPaySettleFacadeTest extends BaseTest {

    /**
     * 收结汇相关处理服务
     */
    @Autowired
    private CbPaySettleFacade cbPaySettleFacade;

    /**
     * 收汇到账通知清算审核
     */
    @Test
    public void receiveAuditTest() {
        ReceiverAuditDto receiverAuditDto = new ReceiverAuditDto();
        receiverAuditDto.setOrderId(1709081653000569492L);
        receiverAuditDto.setMemberId(100018529L);
        receiverAuditDto.setCmAuditState(20);
        Result<Boolean> result = cbPaySettleFacade.receiveAudit(receiverAuditDto, UUID.randomUUID().toString());
        log.info("收汇到账清算审核响应参数信息：{}", result);
    }

    /**
     * 结汇API测试
     */
    @Test
    public void settleIncomeApplyAPITest() {
        SettleIncomeApplyDto settleIncomeApplyDto = new SettleIncomeApplyDto();
        settleIncomeApplyDto.setMemberId(100018529L);
        settleIncomeApplyDto.setSettleDFSId(102130940L);
        settleIncomeApplyDto.setPaymentFileId("102130941");
        settleIncomeApplyDto.setIncomeNo(DateUtil.getCurrent());
        settleIncomeApplyDto.setOrderAmt(new BigDecimal("361846.66"));
        settleIncomeApplyDto.setOrderCcy("USD");
        settleIncomeApplyDto.setIncomeAccount("15000076018258");
        settleIncomeApplyDto.setIncomeAccountName("有棵树电子商务有限公司");
        settleIncomeApplyDto.setIncomeBankName("上海平安");
        settleIncomeApplyDto.setRemittanceAcc("006-391-62460889");
        settleIncomeApplyDto.setRemittanceCountry("HKG");
        settleIncomeApplyDto.setRemittanceName("YKS Electronic Commerce Co. Ltd.");
        settleIncomeApplyDto.setFileName("结汇明细10.24.xlsx");
        settleIncomeApplyDto.setMemberName("有棵树电子商务有限公司");
        Result<Long> result = cbPaySettleFacade.settleIncomeApplyAPI(settleIncomeApplyDto, UUID.randomUUID().toString());
        log.info("收汇API申请：{}", result);
    }

    @Test
    public void settlementResultQuery() {
        cbPaySettleFacade.settlementResultQuery(100018657L, "FB6yHR1b", UUID.randomUUID().toString());
    }

    /**
     * 运营线下上传汇款凭证接口测试
     */
    @Test
    public void operationSettlePaymentUploadTest() {
        SettlePaymentFileUploadDto dto = new SettlePaymentFileUploadDto();
        dto.setPaymentFileId(102130941L);
        dto.setMemberId(100019158L);
        dto.setIncomeNo("201512515");
        dto.setApplyNo(1710311621000274547L);
        Result<Boolean> result = cbPaySettleFacade.operationSettlePaymentUpload(dto, "19900529");
        log.info("结汇申请汇款凭证下载结果：{}", result);
    }
}
