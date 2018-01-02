package com.test.biz;

import com.baofu.international.global.account.core.biz.UserPwdBiz;
import com.baofu.international.global.account.core.biz.models.UserPwdReqBo;
import com.baofu.international.global.account.core.biz.models.UserPwdRespBo;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.test.frame.Base;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by Administrator on 2017/11/5 0005.
 */
public class UserPwdBizTest extends Base {

    @Autowired
    private UserPwdBiz userPwdBiz;

    @Test
    public void modifyLoginPwd() {
        UserPwdReqBo userPwdReqBo = new UserPwdReqBo();
        userPwdReqBo.setUserNo(1000018529l);
        userPwdReqBo.setOldPwd("111111");
        userPwdReqBo.setFirstPwd("222222");
        userPwdReqBo.setSecondPwd("222222");
        userPwdBiz.modifyLoginPwd(userPwdReqBo);
    }

    @Test
    public void findPwdInfo() {
        UserPwdRespBo userPwdRespBo = userPwdBiz.findPwdInfo(100018531l, 1);
        UserPwdRespDto userPwdRespDto = new UserPwdRespDto();
        BeanUtils.copyProperties(userPwdRespBo, userPwdRespDto);
        System.out.println(userPwdRespDto);
    }

}
