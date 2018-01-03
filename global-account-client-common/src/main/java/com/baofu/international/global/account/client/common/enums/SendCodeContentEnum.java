package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信验证码内容
 *
 * @author :luoping
 * @version :1.0.0
 * @date :2017/11/23 0023.
 */
@Getter
@AllArgsConstructor
public enum SendCodeContentEnum {

    /**
     * 设置支付密码
     */
    SET_PAY_PWD_MSG("1", "宝付国际跨境收款-设置支付密码", "验证码：#code#，您正在宝付国际跨境收款平台设置支付密码,请勿将验证码告诉他人，客服热线：021-68819999-8636", "global_account:set_pay_password:", 600000L),
    /**
     * 手机短信修改支付密码
     */
    UPDATE_PAY_PWD_MSG("2", "宝付国际跨境收款-重置支付密码", "验证码：#code#，您正在宝付国际跨境收款平台重置支付密码,请勿将验证码告诉他人，客服热线：021-68819999-8636", "global_account:modify_pay_password:", 600000L),
    /**
     * 绑定手机号
     */
    BIND_MOBILE_PHONE_MSG("3", "宝付国际跨境收款-绑定手机号", "验证码：#code#，您正在宝付国际跨境收款平台绑定手机号,请勿将验证码告诉他人，客服热线：021-68819999-8636", "global_account:set_mobile:", 600000L),
    /**
     * 修改绑定手机
     */
    UPDATE_MOBILE_PHONE_MSG("4", "宝付国际跨境收款-修改绑定手机号", "验证码：#code#，您正在宝付国际跨境收款平台修改绑定手机号,请勿将验证码告诉他人，客服热线：021-68819999-8636", "global_account:modify_mobile:", 600000L),
    /**
     * 登录密码
     */
    UPDATE_LOGIN_PWD_MSG("5", "宝付国际跨境收款-重置登录密码", "验证码：#code#，您正在宝付国际跨境收款平台重置登录密码，请勿将验证码告诉他人！客服热线：021-68819999-8636", "global_account:reset_login_password:", 600000L),
    /**
     * 修改安全保护问题
     */
    UPDATE_QUESTION_MSG("6", "宝付国际跨境收款-重置安全保护问题", "验证码：#code#，您正在宝付国际跨境收款平台修改安全保护问题，请勿将验证码告诉他人！客服热线：021-68819999-8636", "global_account:modify_answer_password:", 600000L),

    /**
     * 绑定提现银行卡
     */
    ADD_BANK_CARD("7", "宝付国际跨境收款-新增提现银行卡", "验证码：#code#,您正在宝付国际跨境收款平台新增提现银行卡，请勿将验证码告诉他人！客服热线：021-68819999-8636", "global_account:add_bank_card:", 600000L),

    /**
     * 提现银行卡解绑
     */
    REMOVE_BANK_CARD("8", "宝付国际跨境收款-删除提现银行卡", "验证码：#code#,您正在宝付国际跨境收款平台删除尾号：#bankCardTailNo#的提现银行卡，请勿将验证码告诉他人！客服热线：021-68819999-8636", "global_account:remove_bank_card:", 600000L),
    ;
    /**
     * 业务类型
     */
    private String busiType;
    /**
     * 标题
     */
    private String title;
    /**
     * 短信内容
     */
    private String msgContent;
    /**
     * key
     */
    private String redisKey;
    /**
     * timeout
     */
    private Long timeout;


    /**
     * 获取WYre用户状态
     *
     * @param type type
     * @return UserTypeEnum
     */
    public static SendCodeContentEnum getContentMsg(String type) {
        for (SendCodeContentEnum item : SendCodeContentEnum.values()) {
            if (item.getBusiType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
