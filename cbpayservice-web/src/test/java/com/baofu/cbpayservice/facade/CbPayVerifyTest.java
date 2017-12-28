package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.CbPayVerifyBiz;
import com.baofu.cbpayservice.biz.models.CbPayVerifyReqBo;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;
import com.baofu.cbpayservice.common.util.ExcelReader;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述
 * <p>
 * </p>
 * User: 香克斯 Date:2016/11/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class CbPayVerifyTest extends BaseTest {

    @Autowired
    private CbPayVerifyBiz cbPayVerifyBiz;

    @Test
    public void idCardVerifyTest() throws Exception {
        String file = "E://20170913101957.xlsx";
        Long startTime = System.currentTimeMillis();
        System.out.println("XML读取数据开始。。。。。。。。。");
        ExcelReader reader = new ExcelReader();
        reader.process(new FileInputStream(file), 1, 17);
        List<Object[]> list = reader.getRowList();
        System.out.println("XML读取总共耗时：" + (System.currentTimeMillis() - startTime) + ",文件总数：" + list.size());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            Object[] objects = list.get(i);
            for (Object object : objects) {
                log.info("文件内容为:{}", object);
                stringBuilder.append(object).append("|||,");
            }
            CbPayVerifyReqBo cbPayVerifyReqBo = new CbPayVerifyReqBo();
            cbPayVerifyReqBo.setMemberId(1126499L);
            cbPayVerifyReqBo.setTransId(String.valueOf(objects[0]));
            cbPayVerifyReqBo.setIdCard(String.valueOf(objects[6]));
            cbPayVerifyReqBo.setIdName(String.valueOf(objects[5]));
            cbPayVerifyReqBo.setTransDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            cbPayVerifyReqBo.setTerminalId("33296");
            CbPayVerifyResBo result = cbPayVerifyBiz.idCardAuth(cbPayVerifyReqBo);
            stringBuilder.append(result.getDesc()).append("\n");

        }
        log.info("==============:{}", stringBuilder.toString());
        Files.write(stringBuilder.toString().getBytes(), new File("E://tmp/20170913101957.txt"));
    }
}
