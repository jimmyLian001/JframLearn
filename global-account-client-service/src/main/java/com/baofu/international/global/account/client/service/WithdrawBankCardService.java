package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.client.service.models.BankInfoDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 提现服务
 * <p>
 * 1.添加银行卡信息
 * 2.提现
 * 3.发送验证码
 * 4.验证验证码
 * 5.查询用户银行卡信息
 * 6.查询开户行列表
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
public interface WithdrawBankCardService {

    /**
     * 添加银行卡信息
     *
     * @param bankInfoDto 添加银行卡请求参数
     * @param userNo      用户号
     * @return 添加结果
     */
    Boolean addBankCard(BankInfoDto bankInfoDto, Long userNo);

    /**
     * 提现
     *
     * @param payPwd         支付密码
     * @param confirmDtoList 提现信息
     * @param userNo         用户编号
     * @param recordNo       银行卡记录编号
     */
    void userWithdrawConfirm(String payPwd, List<UserWithdrawDetailDto> confirmDtoList, Long userNo, String recordNo);

    /**
     * 发送验证码
     *
     * @param loginNo 登录号
     * @return 处理标识
     */
    SendCodeRespDto autoSendCode(String loginNo);

    /**
     * 验证验证码
     *
     * @param loginNo 用户号
     * @param code    验证码
     * @return 处理结果
     */
    void verifyCode(String loginNo, String code);

    /**
     * 查询用户银行卡信息
     *
     * @param userNo 用户号
     * @return 银行卡信息集合
     */
    List<UserBankCardInfoDto> getBankInfoList(Long userNo);

    /**
     * 查询开户行列表
     *
     * @param userNo 用户编号
     * @return 开户行列表
     */
    List<TSysBankInfoDto> findSysBankInfo(Long userNo);

    /**
     * 提现银行卡解绑卡发送验证码
     *
     * @param serviceType    业务类型
     * @param bankCardTailNo 银行卡尾号
     * @param loginNo        登录号
     * @return 处理标识
     */
    Result<SendCodeRespDto> bankCardAutoSendCode(String serviceType, String bankCardTailNo, String loginNo);
}
