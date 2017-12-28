package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.facade.models.res.ExchangeRateQueryRespDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
public class CbPaySelectFeeFacadeTest extends BaseTest {

    /**
     * 汇率查询接口
     */
    @Autowired
    private CbPaySelectFeeFacade cbPaySelectFeeFacade;

    /**
     * 测试查汇接口
     */
    @Test
    public void exchangeRateQuery() {
        Result<ExchangeRateQueryRespDto> result = cbPaySelectFeeFacade.exchangeRateQuery(100018529L, "USD", UUID.randomUUID().toString());
        log.info("call 返回结果：{}", result.getResult());
    }

}
