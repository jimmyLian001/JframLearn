package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.facade.models.RemitOrderApiQueryDto;
import com.baofu.cbpayservice.facade.models.res.RemitDetailsQueryRespDto;
import com.baofu.cbpayservice.facade.models.res.RemitOrderQueryRespDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/9/25 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class RemittanceApiQueryFacadeTest extends BaseTest {

    /**
     * 汇款API查询服务
     */
    @Autowired
    RemittanceApiQueryFacade remittanceApiQueryFacade;

    @Test
   public void remittanceOrderQuery(){

        RemitOrderApiQueryDto dto = new RemitOrderApiQueryDto();
        dto.setBatchNo("1708301534000004777");
        dto.setMemberId(100018529L);

        Result<RemitOrderQueryRespDto> result = remittanceApiQueryFacade.remittanceOrderQuery(dto, UUID.randomUUID().toString());
        log.info("call 汇款API查询汇款订单信息：{}", result.getResult());

   }

    @Test
    public void orderDetailsUploadQueryTest(){

        RemitOrderApiQueryDto dto = new RemitOrderApiQueryDto();
        dto.setBatchNo("1708301534000004777");
        dto.setMemberId(100018529L);

        Result<RemitDetailsQueryRespDto> result = remittanceApiQueryFacade.orderDetailsUploadQuery(dto, UUID.randomUUID().toString());
        log.info("call API订单明细上传查询：{}", result.getResult());

    }
}
