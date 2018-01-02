package com.baofu.international.global.account.core.biz.convert;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwCreateUserReqDto;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.models.ApplyAccountBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountInfoBo;
import com.baofu.international.global.account.core.biz.models.ApplyAccountReqBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.constant.NumberStrDict;
import com.baofu.international.global.account.core.common.enums.RealNameStatusEnum;
import com.baofu.international.global.account.core.dal.model.*;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : yangjian  Date: 2017-11-05 ProjectName:  Version: 1.0
 */
@Slf4j
public final class ApplyAccountBizConvert {


    private ApplyAccountBizConvert() {

    }

    /**
     * @param applyAccountBo applyAccountBo
     * @param channelId      channelId
     * @return 返回结果
     */
    public static UserPayeeAccountApplyDo paramConvertToApply(ApplyAccountBo applyAccountBo, Long channelId) {
        UserPayeeAccountApplyDo userPayeeAccountApplyDo = new UserPayeeAccountApplyDo();
        userPayeeAccountApplyDo.setChannelId(channelId);
        userPayeeAccountApplyDo.setStatus(NumberDict.ZERO);
        if (applyAccountBo.getRealNameStatus() == RealNameStatusEnum.SUCCESS.getState()) {
            userPayeeAccountApplyDo.setStatus(NumberDict.ONE);
        }
        userPayeeAccountApplyDo.setCcy(applyAccountBo.getCcy());
        userPayeeAccountApplyDo.setUserNo(applyAccountBo.getUserNo());
        userPayeeAccountApplyDo.setStoreNo(applyAccountBo.getStoreNo());
        userPayeeAccountApplyDo.setApplyId(applyAccountBo.getApplyId());
        userPayeeAccountApplyDo.setApplyAccNo(applyAccountBo.getMemberAccNo());
        userPayeeAccountApplyDo.setQualifiedNo(applyAccountBo.getQualifiedNo());

        return userPayeeAccountApplyDo;
    }

    /**
     * @param applyAccountBo applyAccountBo
     * @return 返回结果
     */
    public static UserStoreDo paramConvertToStore(ApplyAccountBo applyAccountBo) {

        UserStoreDo userStoreDo = new UserStoreDo();
        userStoreDo.setCreateAt(new Date());
        userStoreDo.setUpdateAt(new Date());
        userStoreDo.setStoreState(NumberDict.THREE);
        userStoreDo.setTradeCcy(applyAccountBo.getCcy());
        userStoreDo.setUserNo(applyAccountBo.getUserNo());
        userStoreDo.setStoreNo(applyAccountBo.getStoreNo());
        userStoreDo.setStoreName(applyAccountBo.getStoreName());
        userStoreDo.setStorePlatform(applyAccountBo.getStorePlatform());
        userStoreDo.setStoreUrl(applyAccountBo.getAccountHolderWebsite());
        userStoreDo.setStoreExist(applyAccountBo.getStoreExist());
        userStoreDo.setManagementCategory(applyAccountBo.getManagementCategory());
        userStoreDo.setSellerId(applyAccountBo.getSellerId());
        userStoreDo.setSecretKey(applyAccountBo.getSecretKey());
        userStoreDo.setAwsAccessKey(applyAccountBo.getAwsAccessKey());
        return userStoreDo;
    }


    /**
     * 收款账户开户开户请求发送渠道对象转换
     *
     * @param applyAccountBo 业务请求对象
     * @param gateHost       访问gate域名，以/结尾
     * @param channelId      渠道id
     * @return cgwCreateUser
     */
    public static CgwCreateUserReqDto toCgwCreateUserReqDto(ApplyAccountBo applyAccountBo, String gateHost, Long channelId) {
        //获取营业执照和证件照url地址
        Calendar date = Calendar.getInstance();
        date.setTime(applyAccountBo.getBirthDay());
        CgwCreateUserReqDto cgwCreateUser = new CgwCreateUserReqDto();
        cgwCreateUser.setTraceLogId(MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        cgwCreateUser.setBfBatchId(applyAccountBo.getApplyId().toString());
        cgwCreateUser.setSsn(applyAccountBo.getIdNo());
        cgwCreateUser.setType(applyAccountBo.getMemberType());
        cgwCreateUser.setName(applyAccountBo.getMemberAccNo());
        cgwCreateUser.setFirstName(applyAccountBo.getFirstName());
        cgwCreateUser.setLastName(applyAccountBo.getLastName());
        cgwCreateUser.setBirthYear(String.valueOf(date.get(Calendar.YEAR)));
        cgwCreateUser.setBirthMonth(String.valueOf(date.get(Calendar.MONTH) + 1));
        cgwCreateUser.setBirthDay(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
        cgwCreateUser.setCountry(applyAccountBo.getCountry());
        cgwCreateUser.setPhoneNumber(applyAccountBo.getPhoneNumber());
        cgwCreateUser.setStreet1(applyAccountBo.getAddress());
        cgwCreateUser.setCity(applyAccountBo.getCity());
        cgwCreateUser.setState(applyAccountBo.getProvince());
        cgwCreateUser.setPostalCode(applyAccountBo.getPostalCode());
        cgwCreateUser.setCompanyId("");
        cgwCreateUser.setAccountHolderWebsite("");
        if (NumberStrDict.ONE.equals(applyAccountBo.getMemberType())) {
            cgwCreateUser.setCompanyId(gateHost + "/" + applyAccountBo.getUserNo() + "/" + applyAccountBo.getLicenseDfsId());
        }
        cgwCreateUser.setPassport(gateHost + "/" + applyAccountBo.getUserNo() + "/" + applyAccountBo.getPassportDfsId());
        cgwCreateUser.setCurrency(applyAccountBo.getCcy());
        cgwCreateUser.setMemberId(applyAccountBo.getUserNo().toString());
        cgwCreateUser.setChannelId(channelId.intValue());
        cgwCreateUser.setCompanyName(applyAccountBo.getCompanyName());
        cgwCreateUser.setAccountHolderWebsite(applyAccountBo.getAccountHolderWebsite());

        return cgwCreateUser;
    }

    /**
     * 个人参数转换
     *
     * @param personInfoDo  tPersonInfoDo
     * @param applyAccountBo applyAccountBo
     */
    public static void paramConvert(UserPersonalDo personInfoDo, ApplyAccountBo applyAccountBo) {

        applyAccountBo.setMemberType(NumberStrDict.TWO);
        applyAccountBo.setCountry("中国");
        applyAccountBo.setIdNo(personInfoDo.getIdNo());
        applyAccountBo.setCity(personInfoDo.getCity());
        applyAccountBo.setAddress(personInfoDo.getAddress());
        applyAccountBo.setProvince(personInfoDo.getProvince());
        applyAccountBo.setPostalCode(personInfoDo.getPostCode());
        applyAccountBo.setBirthDay(getBirthDate(personInfoDo.getIdNo()));
        applyAccountBo.setFirstName(getName(personInfoDo.getName(), NumberDict.ONE));
        applyAccountBo.setLastName(getName(personInfoDo.getName(), NumberDict.ZERO));
        applyAccountBo.setPhoneNumber(personInfoDo.getPhoneNumber());
        applyAccountBo.setMemberAccNo(personInfoDo.getEmail());
        applyAccountBo.setPassportDfsId(personInfoDo.getIdFrontDfsId());
        applyAccountBo.setIdType(String.valueOf(personInfoDo.getIdType()));
        applyAccountBo.setCompanyName(personInfoDo.getEnName());
        if (!StringUtils.isBlank(personInfoDo.getEmail())) {
            applyAccountBo.setMemberAccNo(personInfoDo.getEmail());
        }
        applyAccountBo.setRealNameStatus(personInfoDo.getRealnameStatus());

    }

    /**
     * 企业参数转换.
     *
     * @param tOrgInfoDo     tOrgInfoDo
     * @param applyAccountBo applyAccountBo
     */
    public static void paramConvert(UserOrgDo tOrgInfoDo, ApplyAccountBo applyAccountBo) {

        applyAccountBo.setMemberType(NumberStrDict.ONE);
        applyAccountBo.setCountry("中国");
        applyAccountBo.setCity(tOrgInfoDo.getCity());
        applyAccountBo.setIdNo(tOrgInfoDo.getIdNo());
        applyAccountBo.setAddress(tOrgInfoDo.getAddress());
        applyAccountBo.setCompanyName(tOrgInfoDo.getEnName());
        applyAccountBo.setProvince(tOrgInfoDo.getProvince());
        applyAccountBo.setMemberAccNo(tOrgInfoDo.getEmail());
        applyAccountBo.setPostalCode(tOrgInfoDo.getPostCode());
        applyAccountBo.setLicenseDfsId(tOrgInfoDo.getIdDfsId());
        applyAccountBo.setPhoneNumber(tOrgInfoDo.getPhoneNumber());
        applyAccountBo.setBirthDay(getBirthDate(tOrgInfoDo.getLegalIdNo()));
        applyAccountBo.setIdType(String.valueOf(tOrgInfoDo.getIdType()));
        applyAccountBo.setPassportDfsId(tOrgInfoDo.getLegalIdFrontDfsId());
        applyAccountBo.setFirstName(getName(tOrgInfoDo.getLegalName(), NumberDict.ONE));
        applyAccountBo.setLastName(getName(tOrgInfoDo.getLegalName(), NumberDict.ZERO));
        applyAccountBo.setRealNameStatus(tOrgInfoDo.getRealnameStatus());
    }

    /**
     * 获取中文名字的姓和名
     *
     * @param name  name
     * @param index index
     * @return 返回结果
     */
    private static String getName(String name, Integer index) {
        int len = name.length();
        if (index == NumberDict.ZERO) {
            return name.substring(NumberDict.ZERO, NumberDict.ONE);
        }
        return name.substring(NumberDict.ONE, len);
    }

    /**
     * 参数转换
     *
     * @param applyAccountReqBo applyAccountReqBo
     */
    public static ApplyAccountBo paramConvert(ApplyAccountReqBo applyAccountReqBo) {
        ApplyAccountBo resultBo = new ApplyAccountBo();
        resultBo.setCcy(applyAccountReqBo.getCcy());
        resultBo.setUserNo(applyAccountReqBo.getUserNo());
        resultBo.setSellerId(applyAccountReqBo.getSellerId());
        resultBo.setStoreName(applyAccountReqBo.getStoreName());
        resultBo.setSecretKey(applyAccountReqBo.getSecretKey());
        resultBo.setStoreExist(applyAccountReqBo.getStoreExist());
        resultBo.setQualifiedNo(applyAccountReqBo.getQualifiedNo());
        resultBo.setStorePlatform(applyAccountReqBo.getStorePlatform());
        resultBo.setAwsAccessKey(applyAccountReqBo.getAwsAccessKey());
        resultBo.setManagementCategory(applyAccountReqBo.getManagementCategory());

        return resultBo;
    }

    /**
     * 新增账户余额记录
     *
     * @param accountDo applyDo
     * @return 返回结果
     */
    public static UserAccountBalDo paramConvertAccBal(UserPayeeAccountDo accountDo) {

        UserAccountBalDo userAccountBalDo = new UserAccountBalDo();
        userAccountBalDo.setCcy(accountDo.getCcy());
        userAccountBalDo.setAccountNo(accountDo.getAccountNo());
        userAccountBalDo.setWaitAmt(BigDecimal.ZERO);
        userAccountBalDo.setAccountBal(BigDecimal.ZERO);
        userAccountBalDo.setAvailableAmt(BigDecimal.ZERO);
        userAccountBalDo.setChannelId(accountDo.getChannelId());
        userAccountBalDo.setWithdrawProcessAmt(BigDecimal.ZERO);
        userAccountBalDo.setUserNo(accountDo.getUserNo());
        userAccountBalDo.setAccountNo(accountDo.getAccountNo());

        return userAccountBalDo;
    }

    /**
     * 根据身份证号获取生日
     *
     * @param certNo certNo
     * @return date
     */
    public static Date getBirthDate(String certNo) {
        Date birthDay = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.datePattern);
            String birthDate = certNo.substring(NumberDict.SIX, NumberDict.FOURTEEN);
            birthDay = sdf.parse(birthDate);
        } catch (Exception e) {
            log.error("global-bizconvert-生日参数转换异常：", e);
        }
        return birthDay;
    }

    /**
     * 企业参数转换
     *
     * @param userOrgDo  userOrgDo
     * @param qualifiedNo qualifiedNo
     * @return
     */
    public static ApplyAccountInfoBo paramConvert(UserOrgDo userOrgDo, Long qualifiedNo) {

        ApplyAccountInfoBo applyAccountInfoBo = new ApplyAccountInfoBo();

        applyAccountInfoBo.setName(userOrgDo.getName());
        applyAccountInfoBo.setEnName(userOrgDo.getEnName());
        applyAccountInfoBo.setRealNameStatus(userOrgDo.getRealnameStatus());
        applyAccountInfoBo.setLegalNo(userOrgDo.getLegalIdNo());
        applyAccountInfoBo.setUserInfoNo(userOrgDo.getUserInfoNo());
        applyAccountInfoBo.setQualifiedNo(qualifiedNo);

        return applyAccountInfoBo;
    }

    /**
     * 个人参数转换
     *
     * @param personalDo  personalDo
     * @param qualifiedNo qualifiedNo
     * @return
     */
    public static ApplyAccountInfoBo paramConvert(UserPersonalDo personalDo, Long qualifiedNo) {

        ApplyAccountInfoBo applyAccountInfoBo = new ApplyAccountInfoBo();
        applyAccountInfoBo.setName(personalDo.getName());
        applyAccountInfoBo.setEnName(personalDo.getEnName());
        applyAccountInfoBo.setLegalNo(personalDo.getIdNo());
        applyAccountInfoBo.setUserInfoNo(personalDo.getUserInfoNo());
        applyAccountInfoBo.setRealNameStatus(personalDo.getRealnameStatus());
        applyAccountInfoBo.setQualifiedNo(qualifiedNo);

        return applyAccountInfoBo;
    }
}
