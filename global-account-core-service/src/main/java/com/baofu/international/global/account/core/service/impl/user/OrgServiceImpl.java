package com.baofu.international.global.account.core.service.impl.user;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.AccQualifiedMapper;
import com.baofu.international.global.account.core.dal.mapper.UserInfoMapper;
import com.baofu.international.global.account.core.dal.mapper.UserOrgMapper;
import com.baofu.international.global.account.core.dal.model.AccQualifiedDo;
import com.baofu.international.global.account.core.dal.model.UserOrgDo;
import com.baofu.international.global.account.core.dal.model.UserInfoDo;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import com.baofu.international.global.account.core.facade.model.user.EditUserReqDto;
import com.baofu.international.global.account.core.facade.model.user.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.user.OrgFacade;
import com.baofu.international.global.account.core.manager.UserOrgManager;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 企业对象服务接口实现
 * <p>
 * 1、机构 service
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
@Slf4j
@Component
public class OrgServiceImpl implements OrgFacade {

    /**
     * 机构服务
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 企业用户查询服务
     */
    @Autowired
    private UserOrgMapper orgMapper;

    /**
     * 资质信息分页查询服务
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 资质
     */
    @Autowired
    private AccQualifiedMapper accQualifiedMapper;


    /**
     * 根据信息编号查询机构信息
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 机构对象
     */
    @Override
    public Result<UserOrgReqDto> findOrgByApplyNo(Long userInfoNo, String logId) {
        Result<UserOrgReqDto> results;
        log.info("call 根据会员号查询机构信息 userInfoNo：{}", userInfoNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        if (userInfoNo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_199002);
        }
        try {
            UserOrgDo tOrgInfoDo = orgMapper.selectByUserInfoNo(userInfoNo);
            UserOrgReqDto userOrgReq = new UserOrgReqDto();
            if (tOrgInfoDo != null) {
                BeanUtils.copyProperties(tOrgInfoDo, userOrgReq);
            }

            UserInfoDo userInfoDo = userInfoMapper.selectByApplyNo(userOrgReq.getUserInfoNo());
            if (userInfoDo != null) {
                userOrgReq.setUserNo(userInfoDo.getUserNo());
            }
            results = getUserOrgReqDtoResult(userOrgReq);
        } catch (Exception e) {
            log.error("call 根据会员号查询机构信息异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据会员号查询机构信息返回结果：{}", results);
        return results;

    }

    /**
     * 获取结果对象
     *
     * @param userOrgReqDto 用户请求对象
     * @return result 结果对象
     */
    private Result<UserOrgReqDto> getUserOrgReqDtoResult(UserOrgReqDto userOrgReqDto) {
        userOrgReqDto.setLegalIdNo(getIdCard(userOrgReqDto));
        return new Result<>(userOrgReqDto);
    }

    /**
     * 根据信息编号查询企业资质信息
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 机构对象
     */
    @Override
    public Result<UserOrgReqDto> findQualifiedByApplyNo(Long userInfoNo, String logId) {
        Result<UserOrgReqDto> results;
        log.info("call 根据会员号查询机构信息 userInfoNo：{}", userInfoNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        if (userInfoNo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_199002);
        }
        try {
            UserOrgDo tOrgInfoDo = orgMapper.selectByUserInfoNo(userInfoNo);
            UserOrgReqDto userOrgReqDto = new UserOrgReqDto();
            if (tOrgInfoDo != null) {
                BeanUtils.copyProperties(tOrgInfoDo, userOrgReqDto);
            }

            AccQualifiedDo accQualifiedDo = accQualifiedMapper.selectByApplyNo(userOrgReqDto.getUserInfoNo());
            if (accQualifiedDo != null) {
                userOrgReqDto.setUserNo(accQualifiedDo.getUserNo());
            }
            results = getUserOrgReqDtoResult(userOrgReqDto);
        } catch (Exception e) {
            log.error("call 根据会员号查询机构信息异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据会员号查询机构信息返回结果：{}", results);
        return results;

    }

    /**
     * 获取解密后的身份证
     *
     * @param userOrgReqDto 用户请求对象
     * @return 身份证
     */
    private String getIdCard(UserOrgReqDto userOrgReqDto) {
        //解密身份证
        String idNo = "";
        if (StringUtils.isNotBlank(userOrgReqDto.getLegalIdNo())) {
            idNo = SecurityUtil.desDecrypt(userOrgReqDto.getLegalIdNo(), Constants.CARD_DES_KEY);
        }
        return idNo;
    }

    /**
     * 查询企业用户分页信息
     *
     * @param userOrgReqDto 用户请求对象
     * @param logId         日志标记
     * @return 用户信息
     */
    @Override
    public Result<PageRespDTO> orgQueryForPage(UserOrgReqDto userOrgReqDto, String logId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("查询企业用户信息分页查询请求参数：{}", userOrgReqDto);
        Result<PageRespDTO> result;
        ParamValidate.validateNull(userOrgReqDto, ErrorCodeEnum.ERROR_CODE_190206);
        try {
            UserOrgDo tOrgInfoDo = new UserOrgDo();
            BeanUtils.copyProperties(userOrgReqDto, tOrgInfoDo);
            tOrgInfoDo.setPageSize(NumberDict.TWENTY);
            tOrgInfoDo.setPageNo(tOrgInfoDo.getStartRow());
            //查询总数
            int count = userOrgManager.countAllByCondition(tOrgInfoDo);
            log.info("查询企业信息分页查询结果 Total：{}", count);
            if (count == NumberDict.ZERO) {
                throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "企业信息查询为空");
            }
            //查询列表
            List<UserOrgDo> doList = userOrgManager.selectAllByCondition(tOrgInfoDo);
            log.info("查询用户信息结果 size：{}", doList.size());
            result = new Result<>(new PageRespDTO<>(count, NumberDict.TWENTY, BeanCopyUtils.listConvert(doList, UserOrgReqDto.class)));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("查询用户信息异常", e);
        }
        return result;
    }

    /**
     * 修改企业认证状态
     *
     * @param editUserReqDto 更新用户请求对象
     * @return true：修改成功 false:修改失败
     */
    @Override
    public Result<Boolean> modifyAuthStatus(EditUserReqDto editUserReqDto, String logId) {
        Result<Boolean> modResults;
        log.info("call 修改企业认证状态参数 ：请求对象:{}", editUserReqDto);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        ParamValidate.validateNull(editUserReqDto);
        try {
            EditUserReqDo editUserReqDo = new EditUserReqDo();
            BeanUtils.copyProperties(editUserReqDto, editUserReqDo);
            ParamValidate.checkUpdate(userOrgManager.modifyAuthStatus(editUserReqDo));
            modResults = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改企业认证状态异常:{}", e);
            modResults = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改企业认证状态返回结果：{}", modResults);
        return modResults;
    }

    /**
     * 根据用户编号查询所有资质信息
     *
     * @param userNo 用户编号
     * @param logId  日志ID
     * @return 返回结果
     */
    @Override
    public Result<List<UserOrgReqDto>> queryUserQualified(Long userNo, String logId) {
        Result<List<UserOrgReqDto>> result;
        log.info("call 根据用户编号查询所有资质信息 ：请求对象:{}", userNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            List<UserOrgDo> userOrgDos = orgMapper.selectInfoByUserNoList(userNo);
            List<UserOrgReqDto> userOrgReqDtoList = Lists.newArrayList();
            for (UserOrgDo userOrgDo : userOrgDos) {
                UserOrgReqDto userOrgReqDto = BeanCopyUtils.objectConvert(userOrgDo, UserOrgReqDto.class);
                userOrgReqDto.setLegalIdNo(SecurityUtil.desDecrypt(userOrgReqDto.getLegalIdNo(), Constants.CARD_DES_KEY));
                userOrgReqDtoList.add(userOrgReqDto);
            }
            result = new Result<>(userOrgReqDtoList);
        } catch (Exception e) {
            log.error("call 修改企业认证状态异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据用户编号查询所有资质信息返回结果：{}", result);
        return result;
    }
}
