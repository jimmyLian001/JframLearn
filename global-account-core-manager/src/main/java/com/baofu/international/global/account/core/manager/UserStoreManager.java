package com.baofu.international.global.account.core.manager;

import com.baofu.international.global.account.core.dal.model.UserStoreDo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 用户店铺信息相关操作Manager接口服务
 * <p>
 * 1、添加用户店铺信息
 * 2、查询用户店铺信息，返回用户店铺信息集合
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public interface UserStoreManager {

    /**
     * 添加用户店铺信息
     *
     * @param userStoreDo 店铺信息
     */
    void addUserStore(UserStoreDo userStoreDo);

    /**
     * 查询用户店铺信息
     *
     * @param userNo     用户编号
     * @param storeState 店铺状态
     * @return 用户店铺信息集合
     */
    List<UserStoreDo> queryUserStore(Long userNo, Integer storeState);

    /**
     * 查询用户店铺信息通过用户号和币种
     *
     * @param userNo 用户编号
     * @param ccy    币种
     * @return 用户店铺信息集合
     */
    List<UserStoreDo> queryUserStoreByCcy(Long userNo, String ccy);

    /**
     * 查询用户店铺信息分页
     *
     * @param userStoreDo 参数
     * @param pageNum     页面数
     * @param pageSize    页面条数
     * @return 用户店铺信息集合
     */
    Page<UserStoreDo> queryAllForPage(UserStoreDo userStoreDo, int pageNum, int pageSize);

    /**
     * @param storeNo storeNo
     * @return 返回结果
     */
    UserStoreDo queryByStoreNo(Long storeNo);

    /**
     * 更新店铺信息
     *
     * @param userStoreDo userStoreDo
     */
    void modifyUserStore(UserStoreDo userStoreDo);

    /**
     * @param sellerId 卖家编号
     * @return 返回结果
     */
    UserStoreDo checkSellerIdIsExist(String sellerId);
}
