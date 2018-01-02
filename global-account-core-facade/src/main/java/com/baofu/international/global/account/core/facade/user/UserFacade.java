package com.baofu.international.global.account.core.facade.user;

import com.baofu.international.global.account.core.facade.model.user.UserOrgQualifiedDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalQualifiedDto;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.baofu.international.global.account.core.facade.model.user.*;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;

/**
 * 个人用户查询接口
 * <p>
 * 1、个人用户查询接口
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
public interface UserFacade {

    /**
     * 查询个人用户分页信息
     *
     * @param userPersonalDto 用户请求对象
     * @param logId           日志标记
     * @return 用户信息
     */
    Result<PageRespDTO<UserPersonalDto>> userQueryForPage(UserPersonalReqDto userPersonalDto, String logId);

    /**
     * 根据信息编号查询客户用户对象
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 客户用户对象
     */
    Result<UserPersonalDto> findByApplyNo(Long userInfoNo, String logId);

    /**
     * 根据信息编号查询客户资质对象
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 客户用户对象
     */
    Result<UserPersonalDto> findQualifiedByApplyNo(Long userInfoNo, String logId);

    /**
     * 修改认证状态
     *
     * @param editUserReqDto 更新用户请求对象
     * @return true：修改成功 false:修改失败
     */
    Result<Boolean> modifyAuthStatus(EditUserReqDto editUserReqDto, String logId);

    /**
     * 修改用户支付登录密码状态
     *
     * @param userPwdRespDto 参数
     * @return int 更新数量
     */
    Result<Boolean> updateUserPwd(UserPwdRespDto userPwdRespDto, String logId);

    /**
     * 更新银行卡状态
     *
     * @param userNo   会员号
     * @param state    银行卡状态
     * @param updateBy 更新人
     * @param logId    日志id
     * @return 返回更新结果
     */
    Result<Boolean> updateBankCartStatus(Long userNo, Integer state, String updateBy, String logId);

    /**
     * 查询用户资质分页信息
     *
     * @param userQualifiedReqDto 用户资质请求对象
     * @param logId               日志标记
     * @return 用户信息
     */
    Result<PageRespDTO<UserQualifiedDto>> userQualifiedVerifyQueryForPage(UserQualifiedReqDto userQualifiedReqDto, String logId);

    /**
     * 根据资质编号获取个人用户资质信息
     *
     * @param qNo   资质Id
     * @param logId 日志ID
     * @return 个人用户资质信息
     */
    Result<UserPersonalQualifiedDto> findUserByQualifiedNo(Long qNo, String logId);

    /**
     * 根据资质编号获取企业用户资质信息
     *
     * @param qNo   资质Id
     * @param logId 日志ID
     * @return 企业用户资质信息
     */
    Result<UserOrgQualifiedDto> findOrgByQualifiedNo(Long qNo, String logId);

    /**
     * 查询用户余额
     *
     * @param userNo 用户编号
     * @param ccy    币种
     * @param logId  日志ID
     * @return 返回结果
     */
    Result<UserAccountBalDto> findAccountBalByKeys(Long userNo, String ccy, String logId);

}
