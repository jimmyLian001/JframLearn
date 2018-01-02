package com.baofu.international.global.account.core.facade;


import com.baofu.international.global.account.core.facade.model.PageDto;
import com.baofu.international.global.account.core.facade.model.StoreAccountReqDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 用户店铺账户信息操作
 * <p>
 * 1、根据用户编号 查询店铺收款账户信息
 * 2、根据用户编号币种查询店铺收款账户信息
 * 3、查询用户店铺账户信息分页
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
public interface UserStoreAccountFacade {

    /**
     * 根据用户编号查询店铺收款账户信息
     *
     * @param userNo     用户编号
     * @param traceLogId 日志编号
     * @return 返回店铺收款信息结合
     */
    Result<List<UserStoreAccountRespDto>> queryUserStoreAccount(Long userNo, String traceLogId);


    /**
     * 根据用户编号币种查询店铺收款账户信息
     *
     * @param userNo     用户编号
     * @param ccy        币种
     * @param traceLogId 日志编号
     * @return 返回店铺收款信息结合
     */
    Result<List<UserStoreAccountRespDto>> queryUserStoreAccount(Long userNo, String ccy, String traceLogId);

    /**
     * 查询用户店铺账户信息分页
     *
     * @param storeAccountReqDto 参数
     * @param pageNum            页面数
     * @param pageSize           页面条数
     * @return 用户店铺信息集合
     */
    Result<PageDto<UserStoreAccountsDto>> queryStoreAccountForPage(StoreAccountReqDto storeAccountReqDto, int pageNum, int pageSize, String traceLogId);

    /**
     * 根据用户店铺号查询店铺信息
     * add by yangjian
     *
     * @param userNo     用户编号
     * @param storeNo    店铺编号
     * @param traceLogId 日志ID
     * @return 结果
     */
    Result<StoreAccountInfoRepDto> queryStoreAccountInfo(Long userNo, Long storeNo, String traceLogId);

    /**
     * 根据用户店铺号查询店铺信息
     * add by yangjian
     *
     * @param reqDto reqDto
     * @return 结果
     */
    Result<Boolean> modifyStoreInfo(StoreInfoModifyReqDto reqDto, String traceLogId);
}
