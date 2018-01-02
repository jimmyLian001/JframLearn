package com.test.biz;

import com.baofu.international.global.account.core.biz.external.UserAuthBizImpl;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
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
public class UserAuthBizTest extends Base {

    /**
     * 认证相关服务类
     */
    @Autowired
    private UserAuthBizImpl userAuthBiz;

    /**
     * 身份证二要素认证测试
     */
    @Test
    public void idCardAuthTest() {
        PersonAuthReqBo personAuthReqBo = new PersonAuthReqBo();
        personAuthReqBo.setAuthApplyNo(Long.parseLong(DateUtil.getCurrent(DateUtil.fullPattern)));
        personAuthReqBo.setIdCardNo("350321198710061241");
        personAuthReqBo.setIdCardName("欧西涛");
        userAuthBiz.idCardAuth(personAuthReqBo);
    }

    /**
     * 身份证三要素认证测试
     */
    @Test
    public void bankCardAuthTest() {
        PersonAuthReqBo personAuthReqBo = new PersonAuthReqBo();
        personAuthReqBo.setAuthApplyNo(Long.parseLong(DateUtil.getCurrent(DateUtil.fullPattern)));
        personAuthReqBo.setIdCardNo("431128199209297612");
        personAuthReqBo.setIdCardName("欧西涛");
        personAuthReqBo.setBankCardNo("6212261001070232381");
        System.out.println(userAuthBiz.bankCardAuth(personAuthReqBo));
    }
}
