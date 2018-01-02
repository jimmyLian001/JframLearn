package com.baofu.international.global.account.core.service.impl.user;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.AccQualifiedMapper;
import com.baofu.international.global.account.core.dal.mapper.UserAccountApplyMapper;
import com.baofu.international.global.account.core.dal.mapper.UserInfoMapper;
import com.baofu.international.global.account.core.dal.mapper.UserPersonalMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.dal.model.user.EditUserReqDo;
import com.baofu.international.global.account.core.dal.model.user.UserQualifiedReqDo;
import com.baofu.international.global.account.core.dal.model.user.UserQualifiedRespDo;
import com.baofu.international.global.account.core.facade.model.UserPwdRespDto;
import com.baofu.international.global.account.core.facade.model.user.*;
import com.baofu.international.global.account.core.facade.user.UserFacade;
import com.baofu.international.global.account.core.manager.UserBankCardManager;
import com.baofu.international.global.account.core.manager.UserInfoManager;
import com.baofu.international.global.account.core.manager.UserPwdManager;
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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户客户对象描述信息
 * <p>
 * 1、查询个人用户
 * 2、根据用户号查询个人用户对象
 * </p>
 *
 * @author 陶伟超
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Service
public class UserServiceImpl implements UserFacade {

    /**
     * 个人用户服务
     */
    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 用户余额服务
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户密码状态更新服务
     */
    @Autowired
    private UserPwdManager userPwdManager;

    /**
     * 更新银行卡服务
     */
    @Autowired
    private UserBankCardManager userBankCardManager;

    /**
     * 资质信息分页查询服务
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 用户
     */
    @Autowired
    private UserPersonalMapper personInfoMapper;

    /**
     * 企业
     */
    @Autowired
    private OrgServiceImpl orgService;

    /**
     * 资质
     */
    @Autowired
    private AccQualifiedMapper accQualifiedMapper;

    @Autowired
    private UserAccountApplyMapper userPayeeAccountApplyMapper;

    /**
     * 查询个人用户
     *
     * @param userReq 用户请求对象
     * @param logId   日志id
     * @return 用户分页对象
     */
    @Override
    public Result<PageRespDTO<UserPersonalDto>> userQueryForPage(UserPersonalReqDto userReq, String logId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("查询个人用户信息分页查询请求参数：{}", userReq);
        Result<PageRespDTO<UserPersonalDto>> result = new Result<>();
        try {
            UserPersonalDo userPersonalDo = new UserPersonalDo();
            BeanUtils.copyProperties(userReq, userPersonalDo);
            userPersonalDo.setPageSize(NumberDict.TWENTY);
            userPersonalDo.setPageNo(userPersonalDo.getStartRow());
            //查询总数
            int count = userInfoManager.countAllByCondition(userPersonalDo);
            log.info("查询用户信息分页查询结果 Total：{}", count);
            if (count == NumberDict.ZERO) {
                result.setErrorMsg("查询结果为空");
                return result;
            }
            //查询列表
            List<UserPersonalDo> doList = userInfoManager.selectAllByCondition(userPersonalDo);
            log.info("查询用户信息结果 size：{}", doList.size());
            List<UserPersonalDto> userPersonals = Lists.newArrayList();
            for (UserPersonalDo userPersonalDoOne : doList) {
                UserPersonalDto userPersonalDto = new UserPersonalDto();
                BeanUtils.copyProperties(userPersonalDoOne, userPersonalDto);
                userPersonals.add(userPersonalDto);
            }
            result.setResult(new PageRespDTO<>(count, NumberDict.TWENTY, userPersonals));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("查询用户信息异常", e);
        }
        return result;
    }

    /**
     * 根据用户号查询个人用户对象
     *
     * @param userInfoNo 会员号
     * @param logId      日志ID
     * @return 客户用户对象
     */
    @Override
    public Result<UserPersonalDto> findByApplyNo(Long userInfoNo, String logId) {
        Result<UserPersonalDto> userPersonalDtoResult;
        log.info("call 根据会员号查询个人用户信息 userInfoNo：{}", userInfoNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            ParamValidate.validateNull(userInfoNo);
            UserPersonalDo userPersonalDo = personInfoMapper.selectByUserInfoNo(userInfoNo);
            ParamValidate.validateNull(userPersonalDo, ErrorCodeEnum.ERROR_CODE_199001);

            UserPersonalDto userPersonalDto = new UserPersonalDto();
            BeanUtils.copyProperties(userPersonalDo, userPersonalDto);

            UserInfoDo userInfoDo = userInfoMapper.selectByApplyNo(userPersonalDo.getUserInfoNo());
            if (userInfoDo != null) {
                userPersonalDto.setUserNo(userInfoDo.getUserNo());
                //解密身份证
                String idNo = "";
                if (StringUtils.isNotEmpty(userPersonalDto.getIdNo())) {
                    idNo = SecurityUtil.desDecrypt(userPersonalDto.getIdNo(), Constants.CARD_DES_KEY);
                }
                userPersonalDto.setIdNo(idNo);
            }
            userPersonalDtoResult = new Result<>(userPersonalDto);
        } catch (Exception e) {
            log.error("call 根据会员号查询个人用户信息异常:{}", e);
            userPersonalDtoResult = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据会员号查询个人用户信息返回结果：{}", userPersonalDtoResult);
        return userPersonalDtoResult;
    }

    /**
     * 根据信息编号查询客户资质对象
     *
     * @param userInfoNo 信息编号
     * @param logId      日志ID
     * @return 客户用户对象
     */
    @Override
    public Result<UserPersonalDto> findQualifiedByApplyNo(Long userInfoNo, String logId) {
        Result<UserPersonalDto> userPersonalDtoResult;
        log.info("call 根据会员号查询个人用户信息 userInfoNo：{}", userInfoNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            ParamValidate.validateNull(userInfoNo);
            UserPersonalDo userPersonalDo = personInfoMapper.selectByUserInfoNo(userInfoNo);
            ParamValidate.validateNull(userPersonalDo, ErrorCodeEnum.ERROR_CODE_199001);

            UserPersonalDto userPersonalDto = new UserPersonalDto();
            BeanUtils.copyProperties(userPersonalDo, userPersonalDto);

            AccQualifiedDo accQualifiedDo = accQualifiedMapper.selectByApplyNo(userPersonalDo.getUserInfoNo());
            if (accQualifiedDo != null) {
                userPersonalDto.setUserNo(accQualifiedDo.getUserNo());
                //解密身份证
                String idNo = "";
                if (StringUtils.isNotBlank(userPersonalDto.getIdNo())) {
                    idNo = SecurityUtil.desDecrypt(userPersonalDto.getIdNo(), Constants.CARD_DES_KEY);
                }
                userPersonalDto.setIdNo(idNo);

            }
            userPersonalDtoResult = new Result<>(userPersonalDto);
        } catch (Exception e) {
            log.error("call 根据会员号查询个人用户信息异常:{}", e);
            userPersonalDtoResult = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据会员号查询个人用户信息返回结果：{}", userPersonalDtoResult);
        return userPersonalDtoResult;
    }

    /**
     * 修改认证状态
     *
     * @param editUserReqDto 更新用户请求对象
     * @return true：修改成功 false:修改失败
     */
    @Override
    public Result<Boolean> modifyAuthStatus(EditUserReqDto editUserReqDto, String logId) {
        Result<Boolean> modResults;
        log.info("call 修改认证状态参数 ：请求对象:{}", editUserReqDto);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            ParamValidate.validateParams(editUserReqDto);
            EditUserReqDo editUserReqDo = new EditUserReqDo();
            BeanUtils.copyProperties(editUserReqDto, editUserReqDo);
            userInfoManager.updateAuthStatus(editUserReqDo);
            modResults = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改认证状态异常:{}", e);
            modResults = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改认证状态返回结果：{}", modResults);
        return modResults;
    }

    /**
     * 修改用户支付登录密码状态
     *
     * @param userPwdRespDto 参数
     * @return 是否成功
     */
    @Override
    public Result<Boolean> updateUserPwd(UserPwdRespDto userPwdRespDto, String logId) {
        Result<Boolean> modResults;
        log.info("call 修改用户支付登录密码状态参数 ：请求对象:{}", userPwdRespDto);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            ParamValidate.validateParams(userPwdRespDto);
            UserPwdDo userPwdDo = new UserPwdDo();
            BeanUtils.copyProperties(userPwdRespDto, userPwdDo);
            ParamValidate.checkUpdate(userPwdManager.update(userPwdDo));
            modResults = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改用户支付登录密码状态异常:{}", e);
            modResults = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改用户支付登录密码状态返回结果：{}", modResults);
        return modResults;
    }

    /**
     * 更新银行卡状态
     *
     * @param userNo   会员号
     * @param state    银行卡状态
     * @param updateBy 更新人
     * @param logId    日志id
     */
    @Override
    public Result<Boolean> updateBankCartStatus(Long userNo, Integer state, String updateBy, String logId) {
        Result<Boolean> modResults;
        log.info("call 更新银行卡状态参数 ：请求userNo:{} state:{} updateBy:{}", userNo, state, updateBy);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            ParamValidate.checkUpdate(userBankCardManager.updateBankCartState(userNo, state, updateBy),
                    "更新银行卡状态失败");
            modResults = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 更新银行卡状态状态异常:{}", e);
            modResults = ExceptionUtils.getResponse(e);
        }
        log.info("call 更新银行卡状态返回结果：{}", modResults);
        return modResults;
    }

    /**
     * 查询用户资质分页信息
     *
     * @param userQualifiedReqDto 用户资质请求对象
     * @param logId               日志标记
     * @return 用户信息
     */
    @Override
    public Result<PageRespDTO<UserQualifiedDto>> userQualifiedVerifyQueryForPage(UserQualifiedReqDto userQualifiedReqDto, String logId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        Result<PageRespDTO<UserQualifiedDto>> result = new Result<>();
        UserQualifiedReqDo userQualifiedReqDo = BeanCopyUtils.objectConvert(userQualifiedReqDto, UserQualifiedReqDo.class);
        userQualifiedReqDo.setPageSize(NumberDict.TWENTY);
        userQualifiedReqDo.setPageNo(userQualifiedReqDo.getStartRow());
        try {
            //查询总数
            int count = userInfoMapper.countAllByCondition(userQualifiedReqDo);
            log.info("查询用户资质信息分页查询结果 Total：{}", count);
            if (count < 1) {
                throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "用户资质信息为空");
            }
            //查询列表
            List<UserQualifiedRespDo> doList = userInfoMapper.selectAllByCondition(userQualifiedReqDo);
            log.info("查询用户资质信息结果 size：{}", doList.size());
            result.setResult(new PageRespDTO<>(count, NumberDict.TWENTY, BeanCopyUtils.listConvert(doList, UserQualifiedDto.class)));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("查询用户资质信息异常", e);
        }
        return result;
    }

    /**
     * 根据资质编号获取个人用户资质信息
     *
     * @param qNo   资质Id
     * @param logId 日志ID
     * @return 个人用户资质信息
     */
    @Override
    public Result<UserPersonalQualifiedDto> findUserByQualifiedNo(Long qNo, String logId) {
        Result<UserPersonalQualifiedDto> userPersonalDtoResult;
        log.info("call 根据资质编号查询个人用户资质信息 QualifiedNo：{}", qNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            UserPersonalDto userPersonalDto = findQualifiedByApplyNo(qNo, logId).getResult();
            UserPersonalQualifiedDto userPersonalQualifiedDto = new UserPersonalQualifiedDto();
            if (userPersonalDto != null) {
                BeanUtils.copyProperties(userPersonalDto, userPersonalQualifiedDto);
            }
            userPersonalDtoResult = new Result<>(userPersonalQualifiedDto);
        } catch (Exception e) {
            log.error("call 根据资质编号查询个人用户资质信息异常:{}", e);
            userPersonalDtoResult = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据资质编号查询个人用户资质信息返回结果：{}", userPersonalDtoResult);
        return userPersonalDtoResult;
    }

    /**
     * 根据资质编号获取企业用户资质信息
     *
     * @param qNo   资质Id
     * @param logId 日志ID
     * @return 企业用户资质信息
     */
    @Override
    public Result<UserOrgQualifiedDto> findOrgByQualifiedNo(Long qNo, String logId) {
        Result<UserOrgQualifiedDto> orgQualifiedDtoResult;
        log.info("call 根据资质编号查询企业用户资质信息 QualifiedNo：{}", qNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            UserOrgReqDto userOrgReqDto = orgService.findQualifiedByApplyNo(qNo, logId).getResult();
            UserOrgQualifiedDto userOrgQualifiedDto = new UserOrgQualifiedDto();
            if (userOrgReqDto != null) {
                BeanUtils.copyProperties(userOrgReqDto, userOrgQualifiedDto);
            }
            orgQualifiedDtoResult = new Result<>(userOrgQualifiedDto);
        } catch (Exception e) {
            log.error("call 根据资质编号查询企业用户资质信息异常:{}", e);
            orgQualifiedDtoResult = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据资质编号查询企业用户资质信息返回结果：{}", orgQualifiedDtoResult);
        return orgQualifiedDtoResult;
    }

    /**
     * 查询用户余额
     *
     * @param userNo 用户编号
     * @param ccy    币种
     * @param logId  日志ID
     * @return 返回结果
     */
    @Override
    public Result<UserAccountBalDto> findAccountBalByKeys(Long userNo, String ccy, String logId) {
        Result<UserAccountBalDto> result;
        log.info("call 查询用户余额 userNo：{},ccy：{}", userNo, ccy);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            List<UserPayeeAccountApplyDo> userPayeeAccountApplyDos = userPayeeAccountApplyMapper.selectByUserNo(userNo, ccy);
            UserAccountBalDto userAccountBalDto = new UserAccountBalDto();
            userAccountBalDto.setUserNo(userNo);
            userAccountBalDto.setCcy(ccy);
            for (UserPayeeAccountApplyDo userPayeeAccountApplyDo : userPayeeAccountApplyDos) {
                if (userPayeeAccountApplyDo.getStatus() != 2) {
                    continue;
                }
                UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(userPayeeAccountApplyDo.getUserNo(),
                        userPayeeAccountApplyDo.getAccountNo());
                userAccountBalDto.setAccountBal(userAccountBalDto.getAccountBal().add(userAccountBalBo.getAccountBal()));
                userAccountBalDto.setAvailableAmt(userAccountBalDto.getAccountBal().add(userAccountBalBo.getAccountBal()));
                userAccountBalDto.setWaitAmt(userAccountBalDto.getWaitAmt().add(userAccountBalBo.getWaitAmt()));
                userAccountBalDto.setWithdrawProcessAmt(userAccountBalDto.getWithdrawProcessAmt().add(userAccountBalBo.getWithdrawProcessAmt()));
            }
            result = new Result<>(userAccountBalDto);
        } catch (Exception e) {
            log.error("call 查询用户余额:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户余额：{}", result);
        return result;
    }
}
