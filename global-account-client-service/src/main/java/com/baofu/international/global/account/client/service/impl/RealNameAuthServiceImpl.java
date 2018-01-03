package com.baofu.international.global.account.client.service.impl;

import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.common.util.DfsUtil;
import com.baofu.international.global.account.client.service.RealNameAuthService;
import com.baofu.international.global.account.client.service.convert.AccountAuthConvert;
import com.baofu.international.global.account.client.service.convert.OrgInfoConvert;
import com.baofu.international.global.account.client.service.convert.PersonInfoConvert;
import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.core.facade.AccountAuthFacade;
import com.baofu.international.global.account.core.facade.UserInfoCheckFacade;
import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.model.OrgAuthReqDto;
import com.baofu.international.global.account.core.facade.model.PersonalAuthReqDto;
import com.baofu.international.global.account.core.facade.model.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.model.UserPersonalReqDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 实名认证
 * <p>
 * </p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Service
@Slf4j
public class RealNameAuthServiceImpl implements RealNameAuthService {

    /**
     * 认证操作服务
     */
    @Autowired
    private AccountAuthFacade accountAuthFacade;

    /**
     * 个人认证操作服务
     */
    @Autowired
    private UserPersonalFacade userPersonalFacade;

    /**
     * 企业认证操作服务
     */
    @Autowired
    private UserOrgFacade userOrgFacade;

    /**
     * 用户信息校验
     */
    @Autowired
    private UserInfoCheckFacade userInfoCheckFacade;

    /**
     * 个人实名认证
     *
     * @param personAuthReq 个人实名请求参数
     */
    @Override
    public void personAuth(PersonAuthReq personAuthReq) throws IOException {

        log.info("个人用户信息基本校验,email:{},userInfoNo:{}", personAuthReq.getEmail(), personAuthReq.getUserInfoNo());
        Result<Boolean> result = userInfoCheckFacade.userInfoAddCheck(personAuthReq.getEmail(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("个人用户信息基本校验返回结果：{}", result);
        ResultUtil.handlerResult(result);

        PersonalAuthReqDto personalAuthReqDto = AccountAuthConvert.personalAuthConvert(personAuthReq);
        log.info("个人实名认证請求參數信息：{}", personalAuthReqDto);
        Result<Long> authResult = accountAuthFacade.personalAuthApply(personalAuthReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("个人实名认证响应信息：{}", authResult);
        ResultUtil.handlerResult(authResult);

        // 身份证正面照
        Long idFrontDfsId = checkAndUpload(personAuthReq.getIdFrontImage());
        // 身份证反面照
        Long idReverseDfsId = checkAndUpload(personAuthReq.getIdReverseImage());

        UserPersonalReqDto userPersonalReqDto = PersonInfoConvert.addPersonInfoConvert(personAuthReq,
                idFrontDfsId, idReverseDfsId, authResult.getResult());
        log.info("个人实名认证信息保存：{}", userPersonalReqDto);
        // 认证个人信息表
        Result<Boolean> updateResult = userPersonalFacade.addUserPersonal(userPersonalReqDto,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("个人实名认证信息结果返回：{}", updateResult);
        ResultUtil.handlerResult(updateResult);
    }

    /**
     * 个人实名认证信息更新
     *
     * @param personAuthReq 个人实名请求参数
     */
    @Override
    public void personAuthUpdate(PersonAuthReq personAuthReq) throws IOException {

        log.info("个人用户信息基本校验,email:{},userInfoNo:{}", personAuthReq.getEmail(), personAuthReq.getUserInfoNo());
        Result<Boolean> result = userInfoCheckFacade.userInfoModifyCheck(personAuthReq.getEmail(), personAuthReq.getUserInfoNo(),
                UserTypeEnum.PERSONAL.getType(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("个人用户信息基本校验返回结果：{}", result);
        ResultUtil.handlerResult(result);

        // 身份证正面照
        Long idFrontDfsId = checkAndUpload(personAuthReq.getIdFrontImage());
        // 身份证反面照
        Long idReverseDfsId = checkAndUpload(personAuthReq.getIdReverseImage());

        UserPersonalReqDto userPersonalReqDto = PersonInfoConvert.updatePersonInfoConvert(personAuthReq,
                idFrontDfsId, idReverseDfsId);

        log.info("个人实名认证信息更新保存：{}", userPersonalReqDto);
        // 认证个人信息表
        Result<Boolean> updateResult = userPersonalFacade.updateUserPersonal(userPersonalReqDto,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("个人实名认证信息更新结果返回：{}", updateResult);
        ResultUtil.handlerResult(updateResult);
    }

    /**
     * 企业实名认证
     *
     * @param orgAuthReq 企业实名请求信息
     */
    @Override
    public void orgAuth(OrgAuthReq orgAuthReq) throws IOException {

        if (orgAuthReq.getRequestType() == 3 || orgAuthReq.getRequestType() == 4) {
            log.info("企业用户信息基本校验,email:{},userInfoNo:{}", orgAuthReq.getEmail(), orgAuthReq.getUserInfoNo());
            Result<Boolean> result = userInfoCheckFacade.userInfoAddCheck(orgAuthReq.getEmail(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("企业用户信息基本校验返回结果：{}", result);
            ResultUtil.handlerResult(result);
        }

        OrgAuthReqDto orgAuthReqDto = AccountAuthConvert.orgAuthConvert(orgAuthReq);
        log.info("企业实名认证请求参数信息：{}", orgAuthReq);
        Result<Long> authResult = accountAuthFacade.orgAuthApply(orgAuthReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("企业实名认证响应信息：{}", authResult);
        ResultUtil.handlerResult(authResult);

        // 普通营业执照
        if (orgAuthReq.getLicenseType() == 2) {
            // 税务登记证照
            orgAuthReq.setTaxRegCertDfsId(checkAndUpload(orgAuthReq.getTaxRegCertImage()));
            // 组织机构代码证照
            orgAuthReq.setOrgCodeCertDfsId(checkAndUpload(orgAuthReq.getOrgCodeCertImage()));
        }
        // 身份证正面照
        orgAuthReq.setIdFrontDfsId(checkAndUpload(orgAuthReq.getIdFrontImage()));
        // 身份证反面照
        orgAuthReq.setIdReverseDfsId(checkAndUpload(orgAuthReq.getIdReverseImage()));
        // 营业执照
        orgAuthReq.setLicenseDfsId(checkAndUpload(orgAuthReq.getLicenseImage()));
        UserOrgReqDto userOrgReqDto = OrgInfoConvert.addOrgInfoConvert(orgAuthReq, authResult.getResult());
        log.info("企业实名认证信息新增请求参数：{}", userOrgReqDto);
        // 认证企业信息表
        Result<Boolean> addResult = userOrgFacade.addUserOrg(userOrgReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("企业实名认证信息新增响应参数：{}", addResult);

        ResultUtil.handlerResult(addResult);
    }

    /**
     * 更新企业实名认证信息
     *
     * @param orgAuthReq 企业实名请求信息
     */
    @Override
    public void orgAuthUpdate(OrgAuthReq orgAuthReq) throws IOException {

        log.info("企业用户信息基本校验,email:{},userInfoNo:{}", orgAuthReq.getEmail(), orgAuthReq.getUserInfoNo());
        Result<Boolean> result = userInfoCheckFacade.userInfoModifyCheck(orgAuthReq.getEmail(), orgAuthReq.getUserInfoNo(),
                UserTypeEnum.ORG.getType(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("企业用户信息基本校验返回结果：{}", result);
        ResultUtil.handlerResult(result);

        // 普通营业执照
        if (orgAuthReq.getLicenseType() == 2) {
            // 税务登记证照
            orgAuthReq.setTaxRegCertDfsId(checkAndUpload(orgAuthReq.getTaxRegCertImage()));
            // 组织机构代码证照
            orgAuthReq.setOrgCodeCertDfsId(checkAndUpload(orgAuthReq.getOrgCodeCertImage()));
        }
        // 身份证正面照
        orgAuthReq.setIdFrontDfsId(checkAndUpload(orgAuthReq.getIdFrontImage()));
        // 身份证反面照
        orgAuthReq.setIdReverseDfsId(checkAndUpload(orgAuthReq.getIdReverseImage()));
        // 营业执照
        orgAuthReq.setLicenseDfsId(checkAndUpload(orgAuthReq.getLicenseImage()));

        // 认证企业信息表
        Result<Boolean> addResult = userOrgFacade.updateUserOrg(OrgInfoConvert.updateOrgInfoConvert(orgAuthReq),
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("更新企业信息响应参数：{}", addResult);

        ResultUtil.handlerResult(addResult);
    }

    /**
     * 查询个人用户信息
     *
     * @param userInfoNo 信息编号
     * @return 查询结果
     */
    @Override
    public UserPersonalDto queryUserPersonal(Long userInfoNo) {
        log.info("查询个人用户信息请求参数：{}", userInfoNo);
        Result<UserPersonalDto> result = userPersonalFacade.selectInfoByUserInfoNo(userInfoNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询个人用户信息响应信息：{}", result);
        ResultUtil.handlerResult(result);
        UserPersonalDto userPersonalDto = new UserPersonalDto();
        if (result.isSuccess() || result.getResult() != null) {
            userPersonalDto = result.getResult();
        }
        return userPersonalDto;
    }

    /**
     * 查询企业用户信息
     *
     * @param userInfoNo 信息编号
     * @return 处理结果
     */
    @Override
    public OrgInfoRespDto queryUserOrg(Long userInfoNo) {
        log.info("查询企业用户信息请求参数：{}", userInfoNo);
        Result<OrgInfoRespDto> result = userOrgFacade.selectInfoByUserInfoNo(userInfoNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询企业用户信息响应信息：{}", result);
        ResultUtil.handlerResult(result);
        OrgInfoRespDto orgInfoRespDto = new OrgInfoRespDto();
        if (result.isSuccess() || result.getResult() != null) {
            orgInfoRespDto = result.getResult();
        }
        return orgInfoRespDto;
    }

    /**
     * 查询个人用户信息
     *
     * @param userNo 用户编号
     * @return 查询结果
     */
    @Override
    public UserPersonalDto queryUserPersonalByUserNo(Long userNo) {
        log.info("查询个人用户信息请求参数：{}", userNo);
        Result<UserPersonalDto> result = userPersonalFacade.findByUserNo(userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询个人用户信息响应信息：{}", result);
        ResultUtil.handlerResult(result);
        UserPersonalDto userPersonalDto = new UserPersonalDto();
        if (result.isSuccess() || result.getResult() != null) {
            userPersonalDto = result.getResult();
        }
        return userPersonalDto;
    }

    /**
     * 查询企业用户信息
     *
     * @param userNo 用户编号
     * @return 处理结果
     */
    @Override
    public OrgInfoRespDto queryUserOrgByUserNo(Long userNo) {
        log.info("查询企业用户信息请求参数：{}", userNo);
        Result<OrgInfoRespDto> result = userOrgFacade.findByUserNo(userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询企业用户信息响应信息：{}", result);
        ResultUtil.handlerResult(result);
        OrgInfoRespDto orgInfoRespDto = new OrgInfoRespDto();
        if (result.isSuccess() || result.getResult() != null) {
            orgInfoRespDto = result.getResult();
        }
        return orgInfoRespDto;
    }

    /**
     * 查询用户实名认证状态
     *
     * @param userNo   用户号
     * @param userType 用户类型
     * @return 返回认证状态 REALNAME_STATUS
     */
    @Override
    public int userAuthStatus(Long userNo, int userType) {

        if (UserTypeEnum.PERSONAL.getType() == userType) {
            return queryUserPersonalByUserNo(userNo).getRealnameStatus();
        } else {
            return queryUserOrgByUserNo(userNo).getRealnameStatus();
        }
    }

    /**
     * 文件上传
     *
     * @param multipartFile 文件信息
     * @return 上传结果
     * @throws IOException IOExcetpion
     */
    private Long checkAndUpload(MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return null;
        }
        log.info("上传文件名称:{}", multipartFile.getName());
        CommandResDTO commandResDTO = DfsUtil.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        log.info("上传文件响应结果:{}", commandResDTO);
        if (commandResDTO == null) {
            return null;
        }
        return commandResDTO.getFileId();
    }
}
