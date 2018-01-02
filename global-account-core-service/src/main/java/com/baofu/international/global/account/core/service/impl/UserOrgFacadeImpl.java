package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserOrgBiz;
import com.baofu.international.global.account.core.biz.models.OrgInfoReqBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.model.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业信息API服务
 * <p>
 * 1、更新企业用户信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class UserOrgFacadeImpl implements UserOrgFacade {

    /**
     * 企业认证信息操作服务接口
     */
    @Autowired
    private UserOrgBiz userOrgBiz;

    /**
     * 企业认证信息操作manager
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 更新企业用户信息
     *
     * @param userOrgReqDto 企业用户信息
     * @param traceLogId    日志ID
     * @return 更新结果
     */
    @Override
    public Result<Boolean> addUserOrg(UserOrgReqDto userOrgReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("更新企业用户信息,param:{}", userOrgReqDto);

            userOrgBiz.addUserOrg(BeanCopyUtils.objectConvert(userOrgReqDto, OrgInfoReqBo.class));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("更新企业用户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("更新企业用户信息结果:{}", result);
        return result;
    }

    /**
     * 更新企业用户信息
     *
     * @param userOrgReqDto 企业用户信息
     * @param traceLogId    日志ID
     * @return 更新结果
     */
    @Override
    public Result<Boolean> updateUserOrg(UserOrgReqDto userOrgReqDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("更新企业用户信息,param:{}", userOrgReqDto);

            OrgInfoReqBo orgInfoReqBo = BeanCopyUtils.objectConvert(userOrgReqDto, OrgInfoReqBo.class);

            userOrgBiz.updateUserOrg(orgInfoReqBo);

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("更新企业用户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("更新企业用户信息结果:{}", result);
        return result;
    }

    /**
     * 根据用户号查询企业用户Do
     *
     * @param userNo     会员号
     * @param traceLogId 日志ID
     * @return 企业用户Dto
     */
    @Override
    public Result<OrgInfoRespDto> findByUserNo(Long userNo, String traceLogId) {
        Result<OrgInfoRespDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据用户号获取企业用户信息,param:{}", userNo);
            UserOrgDo userOrgDo = userOrgManager.selectInfoByUserNo(userNo);
            if (userOrgDo != null) {
                userOrgDo.setLegalIdNo(userOrgDo.getLegalIdNo() == null ? null :
                        SecurityUtil.desDecrypt(userOrgDo.getLegalIdNo(), Constants.CARD_DES_KEY));
            }
            result = new Result<>(BeanCopyUtils.objectConvert(userOrgDo, OrgInfoRespDto.class));
        } catch (Exception e) {
            log.error("根据用户号获取企业用户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据用户号获取企业用户信息结果:{}", result);
        return result;
    }

    /**
     * 根据用户号查询企业用户Do
     *
     * @param qualifiedNo 会员号
     * @param traceLogId  日志ID
     * @return 企业用户Dto
     */
    @Override
    public Result<OrgInfoRespDto> selectInfoByQualifiedNo(Long qualifiedNo, String traceLogId) {
        Result<OrgInfoRespDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据资质编号获取企业用户信息,param:{}", qualifiedNo);
            UserOrgDo userOrgDo = userOrgManager.selectInfoByQualifiedNo(qualifiedNo);
            if (userOrgDo != null) {
                userOrgDo.setLegalIdNo(userOrgDo.getLegalIdNo() == null ? null :
                        SecurityUtil.desDecrypt(userOrgDo.getLegalIdNo(), Constants.CARD_DES_KEY));
            }
            result = new Result<>(BeanCopyUtils.objectConvert(userOrgDo, OrgInfoRespDto.class));
        } catch (Exception e) {
            log.error("根据资质编号获取企业用户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据资质编号获取企业用户信息结果:{}", result);
        return result;
    }

    /**
     * 根据userInfoNo查询
     *
     * @param userInfoNo 信息编号
     * @param traceLogId 日志ID
     * @return 企业用户Do
     */
    @Override
    public Result<OrgInfoRespDto> selectInfoByUserInfoNo(Long userInfoNo, String traceLogId) {
        Result<OrgInfoRespDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据资质编号获取企业用户信息,param:{}", userInfoNo);

            UserOrgDo userOrgDo = userOrgManager.queryByUserInfoNo(userInfoNo);
            if (userOrgDo == null) {
                log.error("未查询到企业用户数据");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190407);
            }
            OrgInfoRespDto orgInfoRespDto = BeanCopyUtils.objectConvert(userOrgDo, OrgInfoRespDto.class);
            orgInfoRespDto.setTaxRegistrationCertificateDfsId(userOrgDo.getTaxRegistrationCertDfsId());
            orgInfoRespDto.setOrgCodeCertificateDfsId(userOrgDo.getOrgCodeCertDfsId());

            result = new Result<>(orgInfoRespDto);
        } catch (Exception e) {
            log.error("根据资质编号获取企业用户信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据资质编号获取企业用户信息结果:{}", result);
        return result;
    }
}
