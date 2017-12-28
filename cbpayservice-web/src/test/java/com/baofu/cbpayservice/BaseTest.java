package com.baofu.cbpayservice;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.system.commons.utils.DateUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * User: suqier Date:2016/10/26 ProjectName: asias-icpaygate Version: 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class BaseTest {

    public CommandResDTO upload(String path,String fileName){
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setFileName(fileName);
        insertReqDTO.setOrgCode("CBPAY");
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        insertReqDTO.setRemark("结汇文件校验错误信息");//备注信息
        return DfsClient.upload(new File(path), insertReqDTO);
    }
}

