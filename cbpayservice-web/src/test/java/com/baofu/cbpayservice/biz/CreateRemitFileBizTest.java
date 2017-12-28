package com.baofu.cbpayservice.biz;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.models.CreateOrderDetailBo;
import com.baofu.cbpayservice.common.constants.Constants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/23 ProjectName: cbpayservice Version: 1.0
 */
public class CreateRemitFileBizTest extends BaseTest {

    @Autowired
    CreateRemitFileBiz createRemitFileBiz;

    /**
     * 电商
     */
    @Test
    public void electronicCommerce() {
        CreateOrderDetailBo createOrderDetailBo = new CreateOrderDetailBo();
        createOrderDetailBo.setCareerType("01");
        createOrderDetailBo.setFileBatchNo(123123L);
        createOrderDetailBo.setTraceLogId("erwerwerwe-ooo");
        createRemitFileBiz.electronicCommerce(createOrderDetailBo);
    }

    /**
     * 机票
     */
    @Test
    public void airTickets() {
        CreateOrderDetailBo createOrderDetailBo = new CreateOrderDetailBo();
        createOrderDetailBo.setCareerType("02");
        createOrderDetailBo.setFileBatchNo(123123L);
        createOrderDetailBo.setTraceLogId("erwerwerwe-ooo");
        createRemitFileBiz.airTickets(createOrderDetailBo);
    }

    /**
     * 酒店
     */
    @Test
    public void hotel() {
        CreateOrderDetailBo createOrderDetailBo = new CreateOrderDetailBo();
        createOrderDetailBo.setCareerType("02");
        createOrderDetailBo.setFileBatchNo(123123L);
        createOrderDetailBo.setTraceLogId("erwerwerwe-ooo");
        createRemitFileBiz.hotel(createOrderDetailBo);
    }

    /**
     * 留学
     */
    @Test
    public void studyAbroad() {
        CreateOrderDetailBo createOrderDetailBo = new CreateOrderDetailBo();
        createOrderDetailBo.setCareerType("02");
        createOrderDetailBo.setFileBatchNo(123123L);
        createOrderDetailBo.setTraceLogId("erwerwerwe-ooo");
        createRemitFileBiz.studyAbroad(createOrderDetailBo);
    }

    /**
     * 旅游
     */
    @Test
    public void tourism() {
        CreateOrderDetailBo createOrderDetailBo = new CreateOrderDetailBo();
        createOrderDetailBo.setCareerType("02");
        createOrderDetailBo.setFileBatchNo(123123L);
        createOrderDetailBo.setTraceLogId("erwerwerwe-ooo");
        createRemitFileBiz.tourism(createOrderDetailBo);
    }
}
