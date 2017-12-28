package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.facade.models.res.CbPayFeeRespDto;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 商务前台重构测试类
 * <p/>
 * User: lian zd Date:2017/9/20 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CbPayRemittanceRebuildTest extends BaseTest {

    /**
     * redis 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 代理跨境结算接口
     */
    @Autowired
    private ProxyCustomsFacade proxyCustomsFacade;

    @Autowired
    CbPaySelectFeeFacade cbPaySelectFeeFacade;

    /**
     * 测试商户前台发起汇款金额校验
     */
    @Test
    public void getTotalAmountCheckResult() {

        ProxyTotalAmountCheckDto proxySeriesDto = new ProxyTotalAmountCheckDto();
        proxySeriesDto.setMemberId(100018529L);
        proxySeriesDto.setCreateBy("TieShow");
        List<Long> fileBatchList = new ArrayList<>();
        fileBatchList.add(1710231028000002403L);//电商
        fileBatchList.add(1710201943000002202L);//电商
        fileBatchList.add(1710201845000002183L);//旅游
        proxySeriesDto.setFileBatchNoList(fileBatchList);
        proxySeriesDto.setTotalAmount(new BigDecimal("2400"));
        Result<RemitTotalAmountCheckRespDto> result = proxyCustomsFacade.proxyCustomTotalAmountCheck(proxySeriesDto, "19900529");
        log.info("文件上传接口返回结果:{}", result);
    }

    /**
     * 测试商户前台发起汇款汇率试算
     */
    @Test
    public void preCalculateFeeTest() {

        CbPayCalculateFeeDto feeDto = new CbPayCalculateFeeDto();
        feeDto.setMemberId(100018529L);
        feeDto.setTargetCcy("USD");
        feeDto.setTotalAmount(new BigDecimal("2400"));
        List<Long> fileBatchList = new ArrayList<>();
        fileBatchList.add(1710231028000002403L);//电商
        fileBatchList.add(1710201943000002202L);//电商
        feeDto.setFileBatchNoList(fileBatchList);
        feeDto.setEntityNo("10001852910001");
        Result<CbPayFeeRespDto> result = cbPaySelectFeeFacade.preCalculateFee(feeDto, "19900529");
        log.info("文件上传接口返回结果:{}", result.getResult());
    }

    /**
     * 文件上传进度与速度测试
     */
    @Test
    public void fileUpLoadTest() {
        ProxyCustomsDto proxyCustomsDto = new ProxyCustomsDto();
        proxyCustomsDto.setMemberId(100018529L);
        proxyCustomsDto.setCreateBy("TieShow");
        //dfs 10749222L 金额 6900 电商行业订单明细表.xls
        //10750833L 金额 1197800
        //10751711L 金额 4184100  文件商户订单号已存在
        //10816716L 金额 4184100
        //10778854L 金额 2000 电商行业订单明细表(1).xls
        proxyCustomsDto.setDfsFileId(10749222L);//6900
        proxyCustomsDto.setFileName("电商行业订单明细表.xls");
        proxyCustomsDto.setFileType("0");
        Result<Long> result = proxyCustomsFacade.proxyCustoms(proxyCustomsDto, "19900529");
        log.info("文件上传接口返回结果:{}", result);
    }

    /**
     * 批量文件上传接口
     */
    @Test
    public void batchFileUpLoadTest() {
        ProxyCustomsBatchDto proxyCustomsBatchDto = new ProxyCustomsBatchDto();
        List<ProxyCustomsDto> list = new ArrayList<>();

        ProxyCustomsDto proxyCustomsDto1 = new ProxyCustomsDto();
        proxyCustomsDto1.setMemberId(100018529L);
        proxyCustomsDto1.setCreateBy("TieShou");
        proxyCustomsDto1.setDfsFileId(10816716L);
        proxyCustomsDto1.setFileName("电商行业订单明细表.xls");
        proxyCustomsDto1.setFileType("0");

        ProxyCustomsDto proxyCustomsDto2 = new ProxyCustomsDto();
        proxyCustomsDto2.setMemberId(100018529L);
        proxyCustomsDto2.setCreateBy("TieShou");
        proxyCustomsDto2.setDfsFileId(10778854L);
        proxyCustomsDto2.setFileName("电商行业订单明细表(1)");
        proxyCustomsDto2.setFileType("0");

        list.add(proxyCustomsDto1);
        list.add(proxyCustomsDto2);
        proxyCustomsBatchDto.setProxyCustomsDtoList(list);
        Result<Map> result = proxyCustomsFacade.proxyCustomsBatch(proxyCustomsBatchDto, "19900529");
        log.info("文件上传接口返回结果:{}", result.getResult());
    }

    /**
     * 批量文件上传文件解析结果测试
     */
    @Test
    public void batchFileUpLoadResultTest() {
        List<Long> list = new ArrayList<>();
        list.add(1710261441000412459L);
        list.add(1710261441000412460L);
        Result<List<FiCbPayFileUploadRespDto>> result = proxyCustomsFacade.proxyCustomsBatchQuery(list, "19900529");
        log.info("文件上传接口返回结果:{}", result.getResult());
    }

    /**
     * 查询文件校验进度
     */
    @Test
    public void queryProcessingResult() {
        for (int i = 0; i < 1000; i++) {
            Object value = redisManager.queryObjectByKey("1710251011000374036");
            log.info("文件校验查询结果：{}", value);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试redisModify
     */
    @Test
    public void redisModifyTest() {
        redisManager.insertObject("jimmy0001", "jimmy19900529", 500);
        Object value = redisManager.queryObjectByKey("jimmy19900529");
        log.info("查询redis结果：{}", value);
        redisManager.insertObject("jimmy0002", "jimmy19900529");
        redisManager.modifyTimeOut("jimmy19900529", 500L);
        Object value1 = redisManager.queryObjectByKey("jimmy19900529");
        log.info("查询redis结果：{}", value1);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object value2 = redisManager.queryObjectByKey("jimmy19900529");
        log.info("查询redis结果：{}", value2);
    }

}
