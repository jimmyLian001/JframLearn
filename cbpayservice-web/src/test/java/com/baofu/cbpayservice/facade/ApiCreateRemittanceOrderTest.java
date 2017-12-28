package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.facade.models.ApiCbPayRemittanceOrderReqDto;
import com.system.commons.utils.ParamValidate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * API创建汇款订单 测试类
 * <p>
 * Created by 莫小阳 on 2017/10/9.
 */
public class ApiCreateRemittanceOrderTest /*extends BaseTest */ {

    @Autowired
    private ApiCbPayRemittanceFacade apiCbPayRemittanceFacade;


    @Test
    public void test_01() {
        String s = "`-=[]\\';/.,~!@#$%^&*()_+{}|\":?<>王东江wdwdwd1212";

        String s1 = StringUtils.stringFilter(s);

        System.out.println(s1);
    }


    @Test
    public void testApiCbPayRemittanceFacade() {

        ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto = new ApiCbPayRemittanceOrderReqDto();
        apiCbPayRemittanceOrderReqDto.setMemberId(100018529L);
        apiCbPayRemittanceOrderReqDto.setRemitApplyNo("12345678123456781234567812345678123456781234567812345678123456781");
        apiCbPayRemittanceOrderReqDto.setOriginalAmt(new BigDecimal("5656.51"));
        apiCbPayRemittanceOrderReqDto.setOriginalCcy("CNY");
        apiCbPayRemittanceOrderReqDto.setBankAccName("8765465");
        apiCbPayRemittanceOrderReqDto.setBankName("987987");
        apiCbPayRemittanceOrderReqDto.setBankAccNo("345768");
        apiCbPayRemittanceOrderReqDto.setRemitCcy("CNY");
        apiCbPayRemittanceOrderReqDto.setFileBatchNo("1709291715000390171");
        apiCbPayRemittanceOrderReqDto.setNotifyUrl("www.baidu.com");
        apiCbPayRemittanceOrderReqDto.setTerminalId(100001204L);

        ParamValidate.validateParams(apiCbPayRemittanceOrderReqDto);

        System.out.println("成功");

        //apiCbPayRemittanceFacade.apiCreateRemittanceOrder(apiCbPayRemittanceOrderReqDto, MDC.get(SystemMarker.TRACE_LOG_ID));
    }


}
