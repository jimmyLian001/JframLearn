package com.baofu.international.global.account.client.web.controller.auth;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.constant.AuthDict;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.common.util.DfsUtil;
import com.baofu.international.global.account.client.service.AreaInfoService;
import com.baofu.international.global.account.client.service.RealNameAuthService;
import com.baofu.international.global.account.client.service.UserBankCardService;
import com.baofu.international.global.account.client.service.models.OrgAuthReq;
import com.baofu.international.global.account.client.service.models.PersonAuthReq;
import com.baofu.international.global.account.client.web.convert.AreaInfoConvert;
import com.baofu.international.global.account.client.web.convert.RealNameAuthConvert;
import com.baofu.international.global.account.client.web.models.*;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.AreaRespDto;
import com.baofu.international.global.account.core.facade.model.CityRespDto;
import com.baofu.international.global.account.core.facade.model.ProvinceRespDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 实名认证
 * <p>
 *
 * @author : hetao Date:2017/11/04 ProjectName: account-client Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/auth")
public class RealNameAuthController {

    /**
     * 实名认证服务
     */
    @Autowired
    private RealNameAuthService realNameAuthService;

    /**
     * 实名认证服务
     */
    @Autowired
    private AreaInfoService areaInfoService;

    /**
     * 查询用户卡服务
     */
    @Autowired
    private UserBankCardService userBankCardService;

    /**
     * 个人实名认证新增页面
     *
     * @param requestType 请求类型 1:来自我的账户主页请求 2:来自点击申请开户请求
     * @return 个人实名认证页面
     */
    @RequestMapping(value = "/{requestType}/personAuthAddPage.do", method = RequestMethod.GET)
    public ModelAndView personAuthAddPage(@PathVariable(RequestDict.REQUEST_TYPE) String requestType) {
        ModelAndView modelAndView = new ModelAndView("/auth/personAuthAdd");
        modelAndView.addObject(RequestDict.REQUEST_TYPE, requestType);

        return modelAndView;
    }

    /**
     * 个人实名认证修改页面
     *
     * @param userInfoNo 用户信息编号
     * @return model
     */
    @RequestMapping(value = "/personAuthEditPage.do", method = RequestMethod.GET)
    public ModelAndView personAuthEditPage(String userInfoNo) {
        ModelAndView model = new ModelAndView("/auth/personAuthEdit");
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            UserPersonalDto userPersonalDto = realNameAuthService.queryUserPersonal(Long.valueOf(userInfoNo));

            UserBankCardInfoDto userBankCardInfoDto = userBankCardService.queryUserDefaultBankCard(sessionVo.getUserNo(),
                    userPersonalDto.getBankCardRecordNo());

            model.addObject("userPersonalInfo", userPersonalDto);
            model.addObject("userBankCardInfo", userBankCardInfoDto);

            model.addObject("idFrontImageUrl", DfsUtil.getDownloadUrl(userPersonalDto.getIdFrontDfsId()));
            model.addObject("idReverseImageUrl", DfsUtil.getDownloadUrl(userPersonalDto.getIdReverseDfsId()));
        } catch (Exception e) {
            log.error("个人实名更新查询异常：{}", e);
        }
        return model;
    }

    /**
     * 企业实名认证新增页面
     *
     * @param requestType 请求类型 1:来自我的账户主页请求 2:来自点击申请开户请求 3:来自新增资质请求
     * @return 企业实名认证页面
     */
    @RequestMapping(value = "/{requestType}/orgAuthAddPage.do", method = RequestMethod.GET)
    public ModelAndView orgAuthAddPage(@PathVariable(RequestDict.REQUEST_TYPE) String requestType) {
        ModelAndView modelAndView = new ModelAndView("/auth/orgAuthAdd");
        modelAndView.addObject(RequestDict.REQUEST_TYPE, requestType);
        modelAndView.addObject("multiLicenseType", 1);
        return modelAndView;
    }

    /**
     * 企业认证更新页面
     * <p>
     * requestType等于3时为持有人信息更新
     *
     * @param userInfoNo 用户信息编号
     * @return model
     */
    @RequestMapping(value = "/{requestType}/orgAuthEditPage.do", method = RequestMethod.GET)
    public ModelAndView orgAuthEditPage(@RequestParam("userInfoNo") String userInfoNo,
                                        @PathVariable(required = false, value = RequestDict.REQUEST_TYPE) String requestType) {
        ModelAndView model = new ModelAndView("/auth/orgAuthEdit");

        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();

            OrgInfoRespDto orgInfoRespDto = realNameAuthService.queryUserOrg(Long.valueOf(userInfoNo));

            OrgInfoRespDto realNameDto = realNameAuthService.queryUserOrgByUserNo(sessionVo.getUserNo());
            model.addObject(RequestDict.REQUEST_TYPE, requestType);
            model.addObject("userOrgInfo", orgInfoRespDto);
            // 新增资质更新
            model.addObject("realNameFlag", 0);

            model.addObject("idFrontImageUrl", DfsUtil.getDownloadUrl(orgInfoRespDto.getLegalIdFrontDfsId()));
            model.addObject("idReverseImageUrl", DfsUtil.getDownloadUrl(orgInfoRespDto.getLegalIdReverseDfsId()));
            model.addObject("licenseImageUrl", DfsUtil.getDownloadUrl(orgInfoRespDto.getIdDfsId()));

            // 实名更新
            if (orgInfoRespDto.getUserInfoNo().doubleValue() == realNameDto.getUserInfoNo().doubleValue()) {
                model.addObject("realNameFlag", 1);
            }

            // 普通营业执照
            if (orgInfoRespDto.getIdType() == NumberDict.TWO) {
                model.addObject("taxRegCertImageUrl", DfsUtil.getDownloadUrl(orgInfoRespDto.getTaxRegistrationCertificateDfsId()));
                model.addObject("orgCodeCertImageUrl", DfsUtil.getDownloadUrl(orgInfoRespDto.getOrgCodeCertificateDfsId()));
            } else {
                model.addObject("taxRegCertImageUrl", "");
                model.addObject("orgCodeCertImageUrl", "");
            }

        } catch (Exception e) {
            log.error("查询异常：{}", e);
        }
        return model;
    }

    /**
     * 查询省列表信息
     *
     * @return 查询结果
     */
    @ResponseBody
    @RequestMapping(value = "/initProvince.do", method = RequestMethod.POST)
    public List<ProvinceInfo> initProvince() {
        List<ProvinceInfo> provinceInfoList = Lists.newArrayList();
        try {
            log.info("查询省列表信息");
            // 查询省列表信息
            List<ProvinceRespDto> respDtoList = areaInfoService.queryProvince(null, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            provinceInfoList = AreaInfoConvert.provinceInfoConvert(respDtoList);

        } catch (Exception e) {
            log.error("查询省列表信息异常！", e);
        }
        return provinceInfoList;
    }

    /**
     * 查询城市列表信息
     *
     * @param request 请求
     * @return 查询结果
     */
    @ResponseBody
    @RequestMapping(value = "/initCity.do", method = RequestMethod.POST)
    public List<CityInfo> initCity(HttpServletRequest request) {
        List<CityInfo> cityInfoList = Lists.newArrayList();
        try {
            log.info("查询城市列表信息");
            String provinceId = request.getParameter("provinceId").split("_")[0];
            // 查询城市列表信息
            List<CityRespDto> respDtoList = areaInfoService.queryCity(provinceId, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            cityInfoList = AreaInfoConvert.cityInfoConvert(respDtoList);

        } catch (Exception e) {
            log.error("查询城市列表信息异常！", e);
        }
        return cityInfoList;
    }

    /**
     * 查询地区列表信息
     *
     * @param request 请求
     * @return 查询结果
     */
    @ResponseBody
    @RequestMapping(value = "/initArea.do", method = RequestMethod.POST)
    public List<AreaInfo> initArea(HttpServletRequest request) {
        List<AreaInfo> areaInfoList = Lists.newArrayList();
        try {
            log.info("查询地区列表信息");
            // 查询地区列表信息
            String cityId = request.getParameter("cityId").split("_")[0];
            List<AreaRespDto> respDtoList = areaInfoService.queryArea(cityId, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            areaInfoList = AreaInfoConvert.areaInfoConvert(respDtoList);

        } catch (Exception e) {
            log.error("查询地区列表信息异常！", e);
        }
        return areaInfoList;
    }

    /**
     * 个人实名认证
     *
     * @param personAuthVo 个人实名form数据
     * @param request      请求
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "/personAuthAdd.do", method = RequestMethod.POST)
    public AjaxResult<String> personAuthAdd(PersonAuthVo personAuthVo, HttpServletRequest request) {
        AjaxResult<String> ajaxResult;
        try {
            log.info("个人实名认证开始：{}", personAuthVo);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            // 身份证正面照
            MultipartFile idFrontImage = multipartRequest.getFile(AuthDict.REQ_ID_FRONT_IMAGE);
            if (CommonDict.IMAGE_FILE_SIZE < idFrontImage.getSize()) {
                log.error("身份证正面照文件超过2M：{}", idFrontImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800004);
            }

            // 身份证反面照
            MultipartFile idReverseImage = multipartRequest.getFile(AuthDict.REQ_ID_REVERSE_IMAGE);
            if (CommonDict.IMAGE_FILE_SIZE < idReverseImage.getSize()) {
                log.error("身份证反面照文件超过2M：{}", idReverseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800005);
            }
            PersonAuthReq personAuthReq = RealNameAuthConvert.personAuthParamConvert(personAuthVo, idFrontImage, idReverseImage);

            SessionVo sessionVo = SessionUtil.getSessionVo();
            personAuthReq.setUserNo(sessionVo.getUserNo());
            personAuthReq.setLoginNo(sessionVo.getLoginNo());

            realNameAuthService.personAuth(personAuthReq);
            ajaxResult = new AjaxResult<>(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("个人实名认证异常！", e);
            ajaxResult = new AjaxResult<>(NumberDict.ONE, ExceptionUtils.getErrorMsg(e));
        }
        log.info("个人实名认证返回结果：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 个人实名认证更新
     *
     * @param personAuthVo 个人实名form数据
     * @param request      请求
     */
    @ResponseBody
    @RequestMapping(value = "/personAuthEdit.do", method = RequestMethod.POST)
    public AjaxResult<String> personAuthEdit(PersonAuthVo personAuthVo, HttpServletRequest request) {
        AjaxResult<String> ajaxResult;
        try {
            log.info("个人实名认证更新开始：{}", personAuthVo);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            // 身份证正面照
            MultipartFile idFrontImage = multipartRequest.getFile(AuthDict.REQ_ID_FRONT_IMAGE);
            if (!fileCheck(idFrontImage)) {
                log.info("身份证正面照超过2M：{}", idFrontImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800004);
            }

            // 身份证反面照
            MultipartFile idReverseImage = multipartRequest.getFile(AuthDict.REQ_ID_REVERSE_IMAGE);
            if (!fileCheck(idReverseImage)) {
                log.info("身份证反面照超过2M：{}", idReverseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800005);
            }

            PersonAuthReq personAuthReq = RealNameAuthConvert.personAuthUpdateParamConvert(personAuthVo, idFrontImage, idReverseImage);

            SessionVo sessionVo = SessionUtil.getSessionVo();
            personAuthReq.setUserNo(sessionVo.getUserNo());
            personAuthReq.setLoginNo(sessionVo.getLoginNo());

            realNameAuthService.personAuthUpdate(personAuthReq);
            ajaxResult = new AjaxResult<>(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("个人实名认证更新异常！", e);
            ajaxResult = new AjaxResult<>(NumberDict.ONE, ExceptionUtils.getErrorMsg(e));
        }
        log.info("个人实名认证更新返回结果：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 企业实名认证
     *
     * @param orgAuthVo 企业实名form数据
     * @param request   请求
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "/orgAuthAdd.do", method = RequestMethod.POST)
    public AjaxResult<String> orgAuthAdd(OrgAuthVo orgAuthVo, HttpServletRequest request) {
        AjaxResult<String> ajaxResult;
        try {
            log.info("企业实名认证开始:{}.", orgAuthVo);

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 营业执照
            MultipartFile licenseImage = multipartRequest.getFile(AuthDict.REQ_LICENSE_IMAGE);
            if (CommonDict.IMAGE_FILE_SIZE < licenseImage.getSize()) {
                log.info("营业执照超过规定大小：{}", licenseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800006);
            }

            // 普通营业执照
            if (orgAuthVo.getMultiLicenseType() == 2) {
                // 税务登记证照片
                MultipartFile taxRegCertImage = multipartRequest.getFile(AuthDict.REQ_TAX_REG_CERT_IMAGE);
                if (CommonDict.IMAGE_FILE_SIZE < taxRegCertImage.getSize()) {
                    log.info("税务登记证照超过规定大小：{}", taxRegCertImage.getSize());
                    throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800007);
                }

                // 组织机构代码证照片
                MultipartFile orgCodeCertImage = multipartRequest.getFile(AuthDict.REQ_ORG_CODE_CERT_IMAGE);
                if (CommonDict.IMAGE_FILE_SIZE < orgCodeCertImage.getSize()) {
                    log.info("组织机构代码证照超过规定大小：{}", orgCodeCertImage.getSize());
                    throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800008);
                }
            }

            // 身份证正面照
            MultipartFile idFrontImage = multipartRequest.getFile(AuthDict.REQ_ID_FRONT_IMAGE);
            if (CommonDict.IMAGE_FILE_SIZE < idFrontImage.getSize()) {
                log.info("身份证正面照超过规定大小：{}", idFrontImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800004);
            }

            // 身份证反面照
            MultipartFile idReverseImage = multipartRequest.getFile(AuthDict.REQ_ID_REVERSE_IMAGE);
            if (CommonDict.IMAGE_FILE_SIZE < idReverseImage.getSize()) {
                log.info("身份证反面照超过规定大小：{}", idReverseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800005);
            }

            OrgAuthReq orgAuthReq = RealNameAuthConvert.orgAuthParamConvert(orgAuthVo, idFrontImage, idReverseImage,
                    multipartRequest.getFile(AuthDict.REQ_ORG_CODE_CERT_IMAGE), multipartRequest.getFile(AuthDict.REQ_TAX_REG_CERT_IMAGE),
                    licenseImage);

            SessionVo sessionVo = SessionUtil.getSessionVo();
            orgAuthReq.setUserNo(sessionVo.getUserNo());
            orgAuthReq.setLoginNo(sessionVo.getLoginNo());

            realNameAuthService.orgAuth(orgAuthReq);

            ajaxResult = new AjaxResult<>(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("企业实名认证开始异常！", e);
            ajaxResult = new AjaxResult<>(NumberDict.ONE, ExceptionUtils.getErrorMsg(e));
        }
        log.info("企业实名认证结果返回：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 企业实名认证更新
     *
     * @param orgAuthVo 企业实名form数据
     * @param request   请求
     */
    @ResponseBody
    @RequestMapping(value = "/orgAuthEdit.do", method = RequestMethod.POST)
    public AjaxResult<String> orgAuthEdit(OrgAuthVo orgAuthVo, HttpServletRequest request) {
        AjaxResult<String> ajaxResult;
        try {
            log.info("企业实名认证更新开始:{}.", orgAuthVo);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 营业执照
            MultipartFile licenseImage = multipartRequest.getFile(AuthDict.REQ_LICENSE_IMAGE);
            if (!fileCheck(licenseImage)) {
                log.info("营业执照超过规定大小：{}", licenseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800006);
            }
            // 普通营业执照
            MultipartFile taxRegCertImage = null;
            MultipartFile orgCodeCertImage = null;
            if (orgAuthVo.getMultiLicenseType() == NumberDict.TWO) {
                // 税务登记证照片
                taxRegCertImage = multipartRequest.getFile(AuthDict.REQ_TAX_REG_CERT_IMAGE);
                if (!fileCheck(taxRegCertImage)) {
                    log.info("税务登记证照超过规定大小：{}", taxRegCertImage.getSize());
                    throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800007);
                }
                orgCodeCertImage = multipartRequest.getFile(AuthDict.REQ_ORG_CODE_CERT_IMAGE);
                // 组织机构代码证照片
                if (!fileCheck(orgCodeCertImage)) {
                    log.info("组织机构代码证照超过规定大小：{}", orgCodeCertImage.getSize());
                    throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800008);
                }
            }

            // 身份证正面照
            MultipartFile idFrontImage = multipartRequest.getFile(AuthDict.REQ_ID_FRONT_IMAGE);
            if (!fileCheck(idFrontImage)) {
                log.info("身份证正面照超过规定大小：{}", idFrontImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800004);
            }

            // 身份证反面照
            MultipartFile idReverseImage = multipartRequest.getFile(AuthDict.REQ_ID_REVERSE_IMAGE);
            if (!fileCheck(idReverseImage)) {
                log.info("身份证反面照超过规定大小：{}", idReverseImage.getSize());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800005);
            }

            OrgAuthReq orgAuthReq = RealNameAuthConvert.orgAuthUpdateParamConvert(orgAuthVo, idFrontImage, idReverseImage,
                    orgCodeCertImage, taxRegCertImage, licenseImage);

            SessionVo sessionVo = SessionUtil.getSessionVo();
            orgAuthReq.setUserNo(sessionVo.getUserNo());
            orgAuthReq.setLoginNo(sessionVo.getLoginNo());

            realNameAuthService.orgAuthUpdate(orgAuthReq);
            ajaxResult = new AjaxResult<>(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("企业实名认证更新异常！", e);
            ajaxResult = new AjaxResult<>(NumberDict.ONE, ExceptionUtils.getErrorMsg(e));
        }
        log.info("企业实名认证更新结果返回：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 用户实名认证状态查询
     *
     * @return 返回查询结果
     */
    @ResponseBody
    @RequestMapping(value = "userRealNameStatusQuery", method = RequestMethod.POST)
    public AjaxResult<Map<String, Integer>> userRealNameStatusQuery() {
        AjaxResult<Map<String, Integer>> ajaxResult;
        try {
            //查询用户信息
            SessionVo sessionVo = SessionUtil.getSessionValue(CommonDict.SESSION_KEY);
            Map<String, Integer> hashMap = Maps.newHashMap();
            hashMap.put("userType", sessionVo.getUserType());
            //认证状态
            Integer realNameStatus;
            //查询是否实名认证状态
            if (UserTypeEnum.PERSONAL.getType() == sessionVo.getUserType()) {
                realNameStatus = realNameAuthService.queryUserPersonalByUserNo(sessionVo.getUserNo()).getRealnameStatus();
            } else {
                realNameStatus = realNameAuthService.queryUserOrgByUserNo(sessionVo.getUserNo()).getRealnameStatus();
            }
            hashMap.put("realNameStatus", realNameStatus);
            ajaxResult = new AjaxResult<>(NumberDict.ZERO, null, hashMap);
        } catch (Exception e) {
            ajaxResult = new AjaxResult<>(NumberDict.ONE, ExceptionUtils.getErrorMsg(e));
            log.error("用户实名认证状态查询异常：", e);
        }
        log.info("用户实名认证状态查询返回结果：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 文件校验
     * 1、可以为空
     * 2、小于2M
     *
     * @param file 文件
     * @return 校验结果
     */
    private Boolean fileCheck(MultipartFile file) {
        return file == null || file.getSize() < CommonDict.IMAGE_FILE_SIZE;
    }
}
