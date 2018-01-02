package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.PageBo;
import com.baofu.international.global.account.core.biz.models.UserStoreAccountBo;
import com.baofu.international.global.account.core.dal.model.UserStoreDo;

import java.util.List;

/**
 * 用户收款账户相关Biz接口
 * <p>
 * 1、查询用户店铺收款账户信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public interface UserPayeeAccountBiz {

    /**
     * 查询用户店铺收款账户信息
     *
     * @param userNo 用户账户号
     * @return 返回用户店铺收款账户信息
     */
    List<UserStoreAccountBo> queryUserStorePayAccount(Long userNo);

    /**
     * 查询用户店铺收款账户信息通过用户号，币种
     *
     * @param userNo 用户账户号
     * @param ccy    币种
     * @return 返回用户店铺收款账户信息
     */
    List<UserStoreAccountBo> queryUserStorePayAccount(Long userNo, String ccy);


    /**
     * 查询用户店铺信息分页
     *
     * @param userStoreDo 参数
     * @param userType    用户类型
     * @param pageNum     页面数
     * @param pageSize    页面条数
     * @return 用户店铺信息集合
     */
    PageBo<UserStoreAccountBo> queryStoreAccountForPage(UserStoreDo userStoreDo, Integer userType, int pageNum, int pageSize);
}
