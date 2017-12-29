package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.facade.models.BatchRemitDto;
import com.baofu.cbpayservice.facade.models.BatchRemitTrialDto;
import com.baofu.cbpayservice.facade.models.ProxyCustomsDto;
import com.baofu.cbpayservice.facade.models.res.AccountBalanceRespDto;
import com.baofu.cbpayservice.facade.models.res.BatchRemitTrialRespDto;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量汇款测试类
 * <p>
 * Created by 莫小阳 on 2017/10/20.
 */
@Slf4j
public class BatchRemitFacadeTest extends BaseTest {

    @Autowired
    private BatchRemitFacade batchRemitFacade;

    /**
     * 测试账户余额
     */
    @Test
    public void testBatchRemitFacade() {
        Result<AccountBalanceRespDto> accountBalanceDtoResult = batchRemitFacade.queryBalance(100019040L,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("账户余额：{}", accountBalanceDtoResult);
    }

    /**
     * 测试批量汇款上传文件
     */
    @Test
    public void batchRemitFileUpload() {

        ProxyCustomsDto customsDto = new ProxyCustomsDto();
        customsDto.setMemberId(100018529L);
        customsDto.setFileName("batch.xls");
        customsDto.setFileType("3");
        customsDto.setCreateBy("terry");
        customsDto.setDfsFileId(this.upload("G://123.xls", "batch.xls").getFileId());
        Result<Long> result = batchRemitFacade.batchRemitFileUpload(customsDto, MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 测试批量汇款上传文件：{}", result);
    }

    /**
     * 测试批量汇款
     */
    @Test
    public void batchRemit() {
        List<BatchRemitDto> batchRemitDtoList = Lists.newArrayList();
        BatchRemitDto remitDto = new BatchRemitDto();
        remitDto.setCreateBy("terry");
        remitDto.setEntityNo("10001852910005");
        remitDto.setMemberId(100018529L);
        remitDto.setTargetCcy("USD");
        remitDto.setRemitAmt(new BigDecimal("30"));
        remitDto.setFileName("文件1");
        batchRemitDtoList.add(remitDto);

        BatchRemitDto remitDto2 = new BatchRemitDto();
        remitDto2.setCreateBy("terry");
        remitDto2.setEntityNo("10001852910005");
        remitDto2.setMemberId(100018529L);
        remitDto2.setTargetCcy("USD");
        remitDto2.setRemitAmt(new BigDecimal("360"));
        remitDto2.setFileName("文件2");
        batchRemitDtoList.add(remitDto2);

        Result<Boolean> result = batchRemitFacade.batchRemit(batchRemitDtoList, MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 测试批量汇款：{}", result);
    }

    /**
     * 批量汇款试算
     */
    @Test
    public void trial() {
        List<BatchRemitTrialDto> trialDtoList = Lists.newArrayList();
        BatchRemitTrialDto remitDto = new BatchRemitTrialDto();
        remitDto.setCreateBy("terry");
        remitDto.setEntityNo("10001852910001");
        remitDto.setMemberId(100018529L);
        remitDto.setTargetCcy("USD");
        remitDto.setRemitAmt(new BigDecimal("100"));
        remitDto.setAccountInfo("123123:qweqweqw");
        trialDtoList.add(remitDto);
        Result<List<BatchRemitTrialRespDto>> result = batchRemitFacade.trial(trialDtoList,
                MDC.get(SystemMarker.TRACE_LOG_ID));
        log.info("call 批量汇款试算：{}", result);
    }
}
