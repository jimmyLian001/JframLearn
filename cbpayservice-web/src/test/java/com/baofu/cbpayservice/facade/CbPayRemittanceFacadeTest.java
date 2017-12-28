package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.models.CbPaySettleOrderValidateBo;
import com.baofu.cbpayservice.biz.validation.ValidationUtil;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceAuditReqDto;
import com.baofu.cbpayservice.facade.models.CbPayRemittanceOrderReqV2Dto;
import com.system.commons.result.Result;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2016/11/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CbPayRemittanceFacadeTest extends BaseTest {

    @Autowired
    private CbPayRemittanceFacade cbPayRemittanceFacade;

    @Test
    public void audit() {
        CbPayRemittanceAuditReqDto reqDto = new CbPayRemittanceAuditReqDto();
        reqDto.setAuditBy("核桃");
        reqDto.setAuditStatus("TRUE");
        reqDto.setMemberId(100018529L);
        reqDto.setBatchNo("1213241798");
        Result<Boolean> result = cbPayRemittanceFacade.auditRemittanceOrder(reqDto, UUID.randomUUID().toString());
        log.info("call 汇款 Result:{}", result);
    }

    @Test
    public void dateCheckTest() {
        CbPaySettleOrderValidateBo cbPaySettleOrderValidateBo = new CbPaySettleOrderValidateBo();
        cbPaySettleOrderValidateBo.setMemberTransDate("2017-07-14 14:40:59");
        cbPaySettleOrderValidateBo.setMemberTransId("174783Ca114453");
        cbPaySettleOrderValidateBo.setPayeeIdType("2");
        cbPaySettleOrderValidateBo.setPayeeIdNo("1234567899876543210");
        cbPaySettleOrderValidateBo.setPayeeName("李四");
        cbPaySettleOrderValidateBo.setPayeeAccNo("bef2179eb0e98fb29e");
        cbPaySettleOrderValidateBo.setOrderCcy("USD");
        cbPaySettleOrderValidateBo.setOrderAmt("10");
        cbPaySettleOrderValidateBo.setGoodsName("123");
        cbPaySettleOrderValidateBo.setGoodsPrice("1");
        cbPaySettleOrderValidateBo.setGoodsNum("1");
        StringBuilder validateStr = new StringBuilder();
        validateStr.append(ParamValidate.validateParams(cbPaySettleOrderValidateBo, Constants.SPLIT_MARK));
        //log.info("call 证件类型校验测试:{}",Integer.parseInt(cbPaySettleOrderValidateBo.getPayeeIdType()));

        ValidationUtil.idNumberCheck(cbPaySettleOrderValidateBo.getPayeeIdType(), cbPaySettleOrderValidateBo.getPayeeIdNo(), validateStr);

        log.info("错误信息:{}", validateStr);
    }

    @Test
    public void verifyMerchantTest() {
        CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto = new CbPayRemittanceOrderReqV2Dto();
        cbPayRemittanceOrderReqV2Dto.setMemberId(new Long(100019009));
        cbPayRemittanceOrderReqV2Dto.setEntityNo("10001900910001");
        cbPayRemittanceOrderReqV2Dto.setCreateBy("dxy");
        //cbPayRemittanceOrderReqV2Dto.setTargetCcy("JPY");
        cbPayRemittanceOrderReqV2Dto.setTargetCcy("GBP");
        List<Long> list = new LinkedList<>();
        list.add(1707031625000000366L);
        cbPayRemittanceOrderReqV2Dto.setBatchFileIdList(list);

        Result<Boolean> result = cbPayRemittanceFacade.createRemittanceOrderV2(cbPayRemittanceOrderReqV2Dto, UUID.randomUUID().toString());
        log.info("创建汇款订单:{}", result);
    }
}
