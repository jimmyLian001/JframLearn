package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserInfoCheckBiz;
import com.baofu.international.global.account.core.biz.UserPersonalBiz;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserPersonalDo;
import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.model.UserPersonalReqDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.baofu.international.global.account.core.manager.UserPersonalManager;
import com.baofu.international.global.account.core.service.convert.PersonInfoConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 个人用户信息接口
 * <p>
 * 1、更新个人用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class UserPersonalFacadeImpl implements UserPersonalFacade {

    /**
     * 个人用户信息操作服务接口
     */
    @Autowired
    private UserPersonalBiz personInfoBiz;

    /**
     * 个人认证信息操作manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 资质校验接口
     */
    @Autowired
    private UserInfoCheckBiz userInfoCheckBiz;

    /**
     * 新增个人用户信息
     *
     * @param userPersonalReqDto 个人认证信息
     * @param traceLogId         日志ID
     * @return 更新结果
     */
    @Override
    public Result<Boolean> addUserPersonal(UserPersonalReqDto userPersonalReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("添加认证个人信息,param:{}", userPersonalReqDto);

            personInfoBiz.addUserPersonal(PersonInfoConvert.updateUserPersonalConvert(userPersonalReqDto));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("添加认证个人信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("添加认证个人信息结果:{}", result);
        return result;
    }

    /**
     * 更新个人用户信息
     *
     * @param userPersonalReqDto 个人认证信息
     * @param traceLogId         日志ID
     * @return 更新结果
     */
    @Override
    public Result<Boolean> updateUserPersonal(UserPersonalReqDto userPersonalReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("更新个人用户信息,param:{}", userPersonalReqDto);

            personInfoBiz.updateUserPersonal(PersonInfoConvert.updateUserPersonalConvert(userPersonalReqDto));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("添加认证个人信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("添加认证个人信息结果:{}", result);
        return result;
    }

    /**
     * 根据userNo查询
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    @Override
    public Result<UserPersonalDto> findByUserNo(Long userNo, String traceLogId) {
        Result<UserPersonalDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据用户号查询认证个人信息,param:{}", userNo);
            UserPersonalDo personalInfoDo = userPersonalManager.selectInfoByUserNo(userNo);
            if (personalInfoDo != null) {
                personalInfoDo.setIdNo(personalInfoDo.getIdNo() == null ? null :
                        SecurityUtil.desDecrypt(personalInfoDo.getIdNo(), Constants.CARD_DES_KEY));
            }
            result = new Result<>(BeanCopyUtils.objectConvert(personalInfoDo, UserPersonalDto.class));
        } catch (Exception e) {
            log.error("根据用户号查询认证个人信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据用户号查询认证个人信息结果:{}", result);
        return result;
    }

    /**
     * 根据qualifiedNo查询
     *
     * @param qualifiedNo 资质编号
     * @return 用户信息
     */
    @Override
    public Result<UserPersonalDto> selectInfoByQualifiedNo(Long qualifiedNo, String traceLogId) {
        Result<UserPersonalDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据资质编号查询认证个人信息,param:{}", qualifiedNo);
            UserPersonalDo personalInfoDo = userPersonalManager.selectInfoByQualifiedNo(qualifiedNo);
            if (personalInfoDo != null) {
                personalInfoDo.setIdNo(personalInfoDo.getIdNo() == null ? null :
                        SecurityUtil.desDecrypt(personalInfoDo.getIdNo(), Constants.CARD_DES_KEY));
            }
            result = new Result<>(BeanCopyUtils.objectConvert(personalInfoDo, UserPersonalDto.class));
        } catch (Exception e) {
            log.error("根据资质编号查询认证个人信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据资质编号查询认证个人信息结果:{}", result);
        return result;
    }

    /**
     * 根据userInfoNo查询
     *
     * @param userInfoNo 资质编号
     * @return 用户信息
     */
    @Override
    public Result<UserPersonalDto> selectInfoByUserInfoNo(Long userInfoNo, String traceLogId) {
        Result<UserPersonalDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据用户信息编号查询认证个人信息,param:{}", userInfoNo);
            UserPersonalDo userPersonalDo = userPersonalManager.queryByUserInfoNo(userInfoNo);
            if (userPersonalDo == null) {
                log.error("未查询到个人用户数据");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190407);
            }

            UserPersonalDto userPersonalDto = new UserPersonalDto();
            BeanUtils.copyProperties(userPersonalDo, userPersonalDto);
            result = new Result<>(userPersonalDto);
        } catch (Exception e) {
            log.error("根据用户信息编号查询认证个人信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据用户信息编号查询认证个人信息结果:{}", result);
        return result;
    }
}
