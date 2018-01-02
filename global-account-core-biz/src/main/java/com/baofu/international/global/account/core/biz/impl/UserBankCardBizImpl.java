package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.CardBinBiz;
import com.baofu.international.global.account.core.biz.UserBankCardBiz;
import com.baofu.international.global.account.core.biz.convert.BankCardAuthConvert;
import com.baofu.international.global.account.core.biz.convert.UserBankCardServiceConvert;
import com.baofu.international.global.account.core.biz.external.UserAuthBizImpl;
import com.baofu.international.global.account.core.biz.models.AddCompanyBankCardBo;
import com.baofu.international.global.account.core.biz.models.AddPersonalBankCardBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户银行卡维护业务处理接口实现
 * <p>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
@Slf4j
@Service
public class UserBankCardBizImpl implements UserBankCardBiz {

    /**
     * 机构服务
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 个人银行卡认证
     */
    @Autowired
    private UserAuthBizImpl userAuthBiz;

    /**
     * 认证申请服务
     */
    @Autowired
    private BankAuthApplyManager bankAuthApplyManager;

    /**
     * 卡bin服务
     */
    @Autowired
    private CardBinManager cardBinManager;

    /**
     * 个人认证信息操作manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 用户银行卡相关服务
     */
    @Autowired
    private UserBankCardManager userBankCardManager;

    /**
     * 系统银行卡支持接口
     */
    @Autowired
    private TSysBankInfoManager sysBankInfoManager;

    /**
     * 卡bin服务
     */
    @Autowired
    private CardBinBiz cardBinBiz;

    /**
     * 用户申请添加个人、法人银行卡
     *
     * @param addPersonalBankCardBo 个人、法人银行卡添加
     */
    @Override
    public void addPersonalBank(AddPersonalBankCardBo addPersonalBankCardBo) {

        cardBinBiz.checkCardBin(addPersonalBankCardBo.getCardNo());
        List<UserLoginInfoDo> userLoginInfoList = userBankCardManager.queryUserInfoListByUserNo(addPersonalBankCardBo.getUserNo());
        if (userLoginInfoList.isEmpty()) {
            log.info(ErrorCodeEnum.ERROR_CODE_190204.getErrorDesc());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190204);
        }
        //银行卡校验
        bankCardNoCheck(addPersonalBankCardBo.getUserNo(), addPersonalBankCardBo.getCardNo());
        String idCardNo;
        if (userLoginInfoList.get(0).getUserType() == UserTypeEnum.PERSONAL.getType()) {
            UserPersonalDo userPersonalDo = userPersonalManager.selectInfoByUserNo(addPersonalBankCardBo.getUserNo());
            idCardNo = SecurityUtil.desDecrypt(userPersonalDo.getIdNo(), Constants.CARD_DES_KEY);
            if (userPersonalDo.getRealnameStatus() != NumberDict.TWO) {
                log.info(ErrorCodeEnum.ERROR_CODE_190215.getErrorDesc());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
            }
        } else if (userLoginInfoList.get(0).getUserType() == UserTypeEnum.ORG.getType()) {
            UserOrgDo tOrgInfoDo = userOrgManager.selectInfoByUserNo(addPersonalBankCardBo.getUserNo());
            if (tOrgInfoDo == null || tOrgInfoDo.getRealnameStatus() != NumberDict.TWO) {
                log.info(ErrorCodeEnum.ERROR_CODE_190215.getErrorDesc());
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
            }
            idCardNo = SecurityUtil.desDecrypt(tOrgInfoDo.getLegalIdNo(), Constants.CARD_DES_KEY);
        } else {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
        }
        // 卡bin校验
        String cardBin = addPersonalBankCardBo.getCardNo().substring(0, 6);
        BankCardBinInfoDo bankCardBinInfoDo = cardBinManager.queryCardBin(cardBin);
        log.info("卡bin查询结果：{}", bankCardBinInfoDo);

        if (bankCardBinInfoDo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190400);
        }
        //订单信息
        Long authApplyNo = orderIdManager.orderIdCreate();
        Long authRequestNo = orderIdManager.orderIdCreate();
        //三要素认证
        PersonAuthReqBo personAuthReqBo = new PersonAuthReqBo();
        personAuthReqBo.setAuthApplyNo(authApplyNo);
        personAuthReqBo.setIdCardNo(idCardNo);
        personAuthReqBo.setIdCardName(addPersonalBankCardBo.getCardHolder());
        personAuthReqBo.setAuthReqNo(authRequestNo);
        personAuthReqBo.setBankCardNo(addPersonalBankCardBo.getCardNo());
        IdCardAuthStateEnum idCardAuthStateEnum = userAuthBiz.bankCardAuth(personAuthReqBo);
        log.info("三要素认证结果：{}", idCardAuthStateEnum);
        int authStatus = idCardAuthStateEnum.getCode() == 0 ? AuthStatusEnum.AUTH_SUCCESS.getCode() : AuthStatusEnum.AUTH_FAIL.getCode();

        // 新增银行卡三要素认证记录
        bankAuthApplyManager.addAuthBank(
                BankCardAuthConvert.addAuthBankConvert(personAuthReqBo, authStatus, bankCardBinInfoDo.getCardType()));
        log.info("新增银行卡三要素认证记录成功");

        // 新增认证记录
        bankAuthApplyManager.addAuthApply(BankCardAuthConvert.addAuthApplyConvert(personAuthReqBo.getAuthApplyNo(), addPersonalBankCardBo.getUserNo(), authStatus,
                idCardAuthStateEnum.getDesc(), userLoginInfoList.get(0).getLoginNo(), userLoginInfoList.get(0).getUserType(), AuthMethodEnum.AUTH_PERSONAL.getCode()));
        log.info("新增银行卡三要素认证申请记录成功");

        if (idCardAuthStateEnum.getCode() != IdCardAuthStateEnum.AUTH_SUCCESS.getCode()) {
            log.info("银行卡认证失败");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190207);
        }
        UserBankCardInfoDo userBankCardInfoDo = UserBankCardServiceConvert.toTUserBankCardInfDo(addPersonalBankCardBo,
                authApplyNo, CardStateEnum.ACTIVATED.getCode());
        userBankCardInfoDo.setCardType(Integer.parseInt(bankCardBinInfoDo.getCardType()));
        userBankCardInfoDo.setBankName(bankCardBinInfoDo.getBankName());
        userBankCardInfoDo.setBankCode(bankCardBinInfoDo.getBankCode());
        //插入新增银行卡数据记录
        userBankCardManager.addUserBankCard(userBankCardInfoDo);
    }

    /**
     * 用户申请添加企业对公银行卡
     *
     * @param addCompanyBankCardBo 注册手机号修改用户申请信息
     */
    @Override
    public void addCompanyLegalPBC(AddCompanyBankCardBo addCompanyBankCardBo) {

        List<UserLoginInfoDo> userLoginInfoList = userBankCardManager.queryUserInfoListByUserNo(addCompanyBankCardBo.getUserNo());
        if (userLoginInfoList.isEmpty()) {
            log.info("未查询到用户信息");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190204);
        }
        SysBankInfoDo sysBankInfoDo = sysBankInfoManager.querySysBankInfo(addCompanyBankCardBo.getBankCode());
        //查询企业信息
        UserOrgDo tOrgInfoDo = userOrgManager.selectInfoByUserNo(addCompanyBankCardBo.getUserNo());
        if (tOrgInfoDo == null || tOrgInfoDo.getRealnameStatus() != NumberDict.TWO) {
            log.info("用户尚未进行实名认证，暂时无法添加银行卡");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
        }
        //银行卡校验
        bankCardNoCheck(addCompanyBankCardBo.getUserNo(), addCompanyBankCardBo.getCardNo());

        //生成银行卡记录信息
        UserBankCardInfoDo userBankCardInfoDo = UserBankCardServiceConvert.toTUserBankCardInfDo(addCompanyBankCardBo,
                orderIdManager.orderIdCreate(), CardStateEnum.ACTIVATED.getCode(), tOrgInfoDo);
        userBankCardInfoDo.setBankName(sysBankInfoDo.getBankName());
        //插入数据
        userBankCardManager.addUserBankCard(userBankCardInfoDo);
    }

    /**
     * 银行卡信息校验
     *
     * @param userNo     用户号
     * @param bankCardNo 银行卡号
     */
    private void bankCardNoCheck(Long userNo, String bankCardNo) {
        List<UserBankCardInfoDo> bankCardInfoList = userBankCardManager.selectUserBankCardByUserNo(userNo);
        for (UserBankCardInfoDo userBankCardInfoDo : bankCardInfoList) {
            //银行卡号解密
            String descBankCardNo = SecurityUtil.desDecrypt(userBankCardInfoDo.getCardNo(), Constants.CARD_DES_KEY);
            if (bankCardNo.equals(descBankCardNo) && userBankCardInfoDo.getState() != NumberDict.THREE) {
                log.info("用户已经添加过该银行卡");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190212);
            }
        }
    }

    /**
     * 删除银行卡
     *
     * @param userNo   用户编号
     * @param recordNo 记录编号
     */
    @Override
    public void delBankCard(Long userNo, Long recordNo) {

        List<UserLoginInfoDo> userLoginInfoList = userBankCardManager.queryUserInfoListByUserNo(userNo);
        if (userLoginInfoList.isEmpty()) {
            log.info("未查询到用户信息");
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190204);
        }
        userAuthCheck(userNo, userLoginInfoList.get(0).getUserType());
        UserBankCardInfoDo userBankCardInfoDo = userBankCardManager.selectUserBankCardByUserNo(userNo, recordNo);

        if (userBankCardInfoDo.getState() == CardStateEnum.FROZEN.getCode() || userBankCardInfoDo.getState() == CardStateEnum.INVALID.getCode()) {
            log.info("银行状态异常，无法删除，当前银行卡状态为：{}", userBankCardInfoDo.getState());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190209);
        }
        userBankCardManager.removeUserBankCard(userNo, recordNo);
    }

    /**
     * 实名认证校验
     *
     * @param userNo   用户号
     * @param userType 用户类型
     */
    public void userAuthCheck(Long userNo, int userType) {
        if (userType == UserTypeEnum.PERSONAL.getType()) {
            UserPersonalDo userPersonalDo = userPersonalManager.selectInfoByUserNo(userNo);
            if (userPersonalDo == null || userPersonalDo.getRealnameStatus() != NumberDict.TWO) {
                log.info("用户尚未进行实名认证，暂时无法删除银行卡");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
            }
        } else if (userType == UserTypeEnum.ORG.getType()) {
            UserOrgDo tOrgInfoDo = userOrgManager.selectInfoByUserNo(userNo);
            if (tOrgInfoDo == null || tOrgInfoDo.getRealnameStatus() != NumberDict.TWO) {
                log.info("用户尚未进行实名认证，暂时无法删除银行卡");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
            }
        } else {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190215);
        }
    }
}
