package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserStoreDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStoreMapper {

    /**
     * 添加用户店铺信息
     *
     * @param userStoreDo 用户店铺信息
     * @return 返回受影响的行数
     */
    int insert(UserStoreDo userStoreDo);

    /**
     * 根据用户号查询店铺信息
     *
     * @param userNo     用户编号
     * @param storeState 店铺状态
     * @return 用户店铺信息集合
     */
    List<UserStoreDo> selectUserStore(@Param("userNo") Long userNo, @Param("storeState") Integer storeState);

    /**
     * 根据用户号查询店铺信息
     *
     * @param userStoreDo 参数
     * @return 用户店铺信息集合
     */
    List<UserStoreDo> selectAll(UserStoreDo userStoreDo);

    /**
     * 根据用户号查询店铺信息
     *
     * @param storeNo 参数
     * @return 用户店铺信息集合
     */
    UserStoreDo selectByStoreNo(Long storeNo);

    /**
     * 根据店铺号更新店铺信息
     *
     * @param userStoreDo userStoreDo
     * @return 更新结果
     */
    int updateStore(UserStoreDo userStoreDo);

    /**
     * 根据卖家编号查询店铺信息
     *
     * @param sellerId 卖家编号
     * @return 用户店铺信息集合
     */
    UserStoreDo selectBySellerId(String sellerId);
}