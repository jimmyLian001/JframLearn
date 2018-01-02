package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.FixPhoneNoApplyBo;
import com.baofu.international.global.account.core.biz.models.FixTelInfoQueryBo;
import com.baofu.international.global.account.core.biz.models.FixTelMessageCodeApplyBo;
import com.baofu.international.global.account.core.biz.models.UserRegisterTelInfoBo;

/**
 * 用户注册手机号维护业务处理接口
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public interface AccountPhoneNoBiz {

    /**
     * 用户申请修改绑定手机号业务逻辑
     *
     * @param fixPhoneNoApplyBo 注册手机号修改用户申请信息
     */
    void reviseTel(FixPhoneNoApplyBo fixPhoneNoApplyBo);

    /**
     * 用户注册个人信息查询
     *
     * @param fixTelInfoQueryBo 用户号
     * @return UserRegisterTelInfoVo
     */
    UserRegisterTelInfoBo getUserRegisterTelInfo(FixTelInfoQueryBo fixTelInfoQueryBo);

    /**
     * 短信验证码发送业务
     *
     * @param fixTelMessageCodeApplyBo 发送信息
     */
    void sendMessageCode(FixTelMessageCodeApplyBo fixTelMessageCodeApplyBo);
}
