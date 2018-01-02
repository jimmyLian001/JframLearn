package com.test.biz;

import com.baofu.international.global.account.core.biz.external.CompanyAuthBizImpl;
import com.baofu.international.global.account.core.biz.models.CompanyAuthReqBo;
import com.system.commons.utils.DateUtil;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/6 ProjectName:account-core  Version: 1.0
 */
public class CompanyAuthBizTest extends Base {

    /**
     * 认证相关服务类
     */
    @Autowired
    private CompanyAuthBizImpl companyAuthBiz;

    /**
     * 企业认证测试
     */
    @Test
    public void idCardAuthTest() {
        CompanyAuthReqBo companyAuthReqBo = new CompanyAuthReqBo();
        companyAuthReqBo.setAuthApplyNo(Long.parseLong(DateUtil.getCurrent(DateUtil.fullPattern)));
        companyAuthReqBo.setFrCid("431128199209297612");
        companyAuthReqBo.setFrName("欧西涛");
        companyAuthReqBo.setEntName("王核桃大保健有限集团");
        System.out.println(companyAuthBiz.companyAuth(companyAuthReqBo));
    }

}
