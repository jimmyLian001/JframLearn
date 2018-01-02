package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.FixPhoneNoApplyBo;
import com.baofu.international.global.account.core.biz.models.FixTelInfoQueryBo;
import com.baofu.international.global.account.core.biz.models.FixTelMessageCodeApplyBo;
import com.baofu.international.global.account.core.facade.model.FixPhoneNoApplyDto;
import com.baofu.international.global.account.core.facade.model.FixTelInfoQueryDto;
import com.baofu.international.global.account.core.facade.model.FixTelMessageCodeApplyDto;

/**
 * 用户绑定手机个人信息转换
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/4 ProjectName: account-core Version:1.0
 */
public final class UserTelConvert {

    private UserTelConvert() {

    }

    /**
     * 用户申请修改绑定手机个人信息转换
     *
     * @param fixPhoneNoApplyDto 用户申请修改绑定手机个人信息
     * @return fixPhoneNoApplyBo
     */
    public static FixPhoneNoApplyBo toFixPhoneNoApplyBo(FixPhoneNoApplyDto fixPhoneNoApplyDto) {
        FixPhoneNoApplyBo fixPhoneNoApplyBo = new FixPhoneNoApplyBo();
        fixPhoneNoApplyBo.setAfterFixPhoneNumber(fixPhoneNoApplyDto.getAfterFixPhoneNumber());
        fixPhoneNoApplyBo.setCurrentPhoneNumber(fixPhoneNoApplyDto.getCurrentPhoneNumber());
        fixPhoneNoApplyBo.setUserNo(Long.valueOf(fixPhoneNoApplyDto.getUserNo()));
        fixPhoneNoApplyBo.setLoginNo(fixPhoneNoApplyDto.getLoginNo());
        fixPhoneNoApplyBo.setLoginType(fixPhoneNoApplyDto.getLoginType());
        return fixPhoneNoApplyBo;
    }

    /**
     * 用户申请修改绑定手机个人信息转换
     *
     * @param fixTelInfoQueryDto 用户个人信息查询
     * @return fixPhoneNoApplyBo
     */
    public static FixTelInfoQueryBo toFixTelInfoQueryBo(FixTelInfoQueryDto fixTelInfoQueryDto) {
        FixTelInfoQueryBo fixTelInfoQueryBo = new FixTelInfoQueryBo();
        fixTelInfoQueryBo.setUserNo(Long.valueOf(fixTelInfoQueryDto.getUserNo()));
        return fixTelInfoQueryBo;
    }

    /**
     * 用户验证码信息对象转换
     *
     * @param fixTelMessageCodeApplyDto 短信息对象
     * @return fixPhoneNoAppyBo
     */
    public static FixTelMessageCodeApplyBo toFixTelMessageCodeApplyBo(FixTelMessageCodeApplyDto fixTelMessageCodeApplyDto) {
        FixTelMessageCodeApplyBo fixTelMessageCodeApplyBo = new FixTelMessageCodeApplyBo();
        fixTelMessageCodeApplyBo.setMessageCode(fixTelMessageCodeApplyDto.getMessageCode());
        fixTelMessageCodeApplyBo.setUserNo(fixTelMessageCodeApplyDto.getUserNo());
        fixTelMessageCodeApplyBo.setCurrentPhoneNumber(fixTelMessageCodeApplyDto.getCurrentPhoneNumber());
        fixTelMessageCodeApplyBo.setContent(fixTelMessageCodeApplyDto.getContent());
        fixTelMessageCodeApplyBo.setServiceType(fixTelMessageCodeApplyDto.getServiceType());
        return fixTelMessageCodeApplyBo;
    }
}
