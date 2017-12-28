package com.baofu.cbpayservice.facade;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.ProxyCustomsValidateBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.util.Excelutil;
import com.baofu.cbpayservice.facade.models.RemitFileUploadDto;
import com.system.commons.utils.ParamValidate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/9/28 ProjectName: cbpayservice Version: 1.0
 */
public class FileUploadFacadeTest extends BaseTest {

    /**
     * 上传文件服务
     */
    @Autowired
    private FileUploadFacade fileUploadFacade;

    @Test
    public void remitFileUploadTest() {

//        RemitFileUploadDto fileUploadDto = new RemitFileUploadDto();
//        fileUploadDto.setMemberId("100018529");
//        fileUploadDto.setMemberReqId(UUID.randomUUID().toString().substring(0,28));
//        fileUploadDto.setNotifyUrl("http://localhost:8099/test");
//        fileUploadDto.setOrderFileName("123.xls");
//        fileUploadDto.setTerminalId("100001204");
//        fileUploadDto.setDfsFileId(10721074L);
//        fileUploadFacade.remitFileUpload(fileUploadDto, UUID.randomUUID().toString());

        try {

            String path = "E:\\FTX2017120110559196.xls";
//            String path = "E:\\1.xlsx";
//            String path = "E:\\2.xlsx";
            InputStream inputStream = new FileInputStream(new File(path));
            List<Object[]> list = Excelutil.getDataFromExcel(inputStream, 0, 0, 20, "FTX2017120110559196.xls");
            Long start = System.currentTimeMillis();
            for (int i = 0; i < list.size(); i++) {
                if (i < 5) {
                    continue;
                }
                ProxyCustomsValidateBo proxyCustomsValidateBo = ProxyCustomConvert.excelServiceradeCustomBos(list.get(i));
                String string = ParamValidate.validateParams(proxyCustomsValidateBo, Constants.SPLIT_MARK);
                System.out.print("第11222次：" + string + "\n");
            }
            System.out.print("总共时间：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
