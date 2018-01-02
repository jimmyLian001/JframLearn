package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.dal.mapper.UserStoreMapper;
import com.baofu.international.global.account.core.dal.model.UserStoreDo;
import com.baofu.international.global.account.core.manager.UserStoreManager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户店铺信息相关操作Manager接口服务
 * <p>
 * 1、添加用户店铺信息
 * 2、查询用户店铺信息，返回用户店铺信息集合
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Repository
public class UserStoreManagerImpl implements UserStoreManager {

    /**
     * 用户店铺信息Mapper
     */
    @Autowired
    private UserStoreMapper userStoreMapper;

    /**
     * 添加用户店铺信息
     *
     * @param userStoreDo 店铺信息
     */
    @Override
    public void addUserStore(UserStoreDo userStoreDo) {

        ParamValidate.checkUpdate(userStoreMapper.insert(userStoreDo), "添加用户店铺信息异常");
    }

    /**
     * 查询用户店铺信息
     *
     * @param userNo 用户编号
     * @return 用户店铺信息集合
     */
    @Override
    public List<UserStoreDo> queryUserStore(Long userNo, Integer storeState) {

        return userStoreMapper.selectUserStore(userNo, storeState);
    }

    /**
     * 查询用户店铺信息通过用户号和币种
     *
     * @param userNo 用户编号
     * @param ccy    币种
     * @return 用户店铺信息集合
     */
    @Override
    public List<UserStoreDo> queryUserStoreByCcy(Long userNo, String ccy) {
        UserStoreDo userStoreDo = new UserStoreDo();
        userStoreDo.setUserNo(userNo);
        userStoreDo.setTradeCcy(ccy);
        return userStoreMapper.selectAll(userStoreDo);
    }

    /**
     * 查询用户店铺信息分页
     *
     * @param userStoreDo 参数
     * @param pageNum     页面数
     * @param pageSize    页面条数
     * @return 用户店铺信息集合
     */
    @Override
    public Page<UserStoreDo> queryAllForPage(UserStoreDo userStoreDo, int pageNum, int pageSize) {
        PageHelper.offsetPage(pageNum, pageSize);
        return (Page<UserStoreDo>) userStoreMapper.selectAll(userStoreDo);
    }

    /**
     * @param storeNo storeNo
     * @return 返回结果
     */
    @Override
    public UserStoreDo queryByStoreNo(Long storeNo) {
        return userStoreMapper.selectByStoreNo(storeNo);
    }

    /**
     * 更新店铺信息
     *
     * @param userStoreDo userStoreDo
     */
    @Override
    public void modifyUserStore(UserStoreDo userStoreDo) {

        ParamValidate.checkUpdate(userStoreMapper.updateStore(userStoreDo), "更新店铺信息失败");
    }

    /**
     * @param sellerId 卖家编号
     * @return 返回结果
     */
    @Override
    public UserStoreDo checkSellerIdIsExist(String sellerId) {
        UserStoreDo userStoreDo = userStoreMapper.selectBySellerId(sellerId);
        if (userStoreDo != null) {
            log.info("卖家编号信息已被使用, 卖家编号：{}", sellerId);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290015, "卖家编号信息已被使用");
        }
        return userStoreDo;
    }
}

