package com.baofu.international.global.account.client.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JSP 路径
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUrlDict {

    /**
     * 错误业务地址
     */
    public static final String ERROR_PAGE = "common/404";

    /**
     * 用户提现申请
     */
    public static final String USER_WITHDRAW_APPLY = "account/withdrawals-step1";

    /**
     * 提现申请成功界面
     */
    public static final String WITHDRAW_APPLY_RESULT = "withdrawals/withdrawalsStep2";


    /**
     * 帮助中心首页
     */
    public static final String HELP_CENTER_PAGE = "help/helpcenter";

    /**
     * 提现银行卡首页路径返回
     */
    public static final String BANK_CARD_INDEX_PAGE = "personalCenter/withdrawBankCard/bankCardIndex";

    /**
     * 个人添加提现银行卡首页路径返回
     */
    public static final String BANK_CARD_ADD_PERSONAL_INDEX_PAGE = "personalCenter/withdrawBankCard/personalAddBankCardPage";

    /**
     * 提现银行卡首页路径返回
     */
    public static final String BANK_CARD_ADD_ENTERPRISE_INDEX_PAGE = "personalCenter/withdrawBankCard/enterpriseAddBankCardPage";

    /**
     * 提现银行卡首页路径返回
     */
    public static final String BANK_CARD_REMOVE_INDEX_PAGE = "personalCenter/withdrawBankCard/deleteBankCard";

    /**
     * 用户协议 路径
     */
    public static final String TO_AGREE_CONTENT_PAGE = "agree/agreement";

    /**
     * 重置登录密码首页
     */
    public static final String RESET_LOGIN_KEY_PAGE = "personalCenter/forgotpwd/reset-loginPwd-index";

    /**
     * 美元账户详情
     */
    public static final String ACCOUNT_DETAIL_USD = "account/account-usd-details";

    /**
     * 欧元账户详情
     */
    public static final String ACCOUNT_DETAIL_EUR = "account/account-eur-details";

    /**
     * 用户资质管理
     */
    public static final String QUALIFIED_MANAGER = "qualified/qualified-manager";

    /**
     * 用户资质操作成功页面
     */
    public static final String QUALIFIED_OPERATION_SUCCESS = "qualified/qualified-success";

    /**
     * 绑定店铺实例
     */
    public static final String BIND_STORE_EXAMPLE = "help/bind-store-";

}
