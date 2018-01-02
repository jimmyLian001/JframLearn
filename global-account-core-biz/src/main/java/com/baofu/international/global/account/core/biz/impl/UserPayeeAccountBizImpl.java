package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.UserPayeeAccountBiz;
import com.baofu.international.global.account.core.biz.convert.UserStoreAccountConvert;
import com.baofu.international.global.account.core.biz.models.PageBo;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.biz.models.UserStoreAccountBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.AccApplyStatusEnum;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户店铺账户信息操作
 * <p>
 * 1、查询用户店铺收款账户信息
 * 2、查询用户店铺收款账户信息通过用户号，币种
 * 3、根据店铺信息获取账户信息
 * 4、查询用户店铺账户信息分页
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Component
public class UserPayeeAccountBizImpl implements UserPayeeAccountBiz {

    /**
     * 用户店铺信息
     */
    @Autowired
    private UserStoreManager userStoreManager;

    /**
     * 用户收款账户信息
     */
    @Autowired
    private UserAccountManager userAccountManager;

    /**
     * 账户相关操作
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 企业manager
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 个人manager
     */
    @Autowired
    private UserPersonalManager userPersonalManager;


    /**
     *
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 查询用户店铺收款账户信息
     *
     * @param userNo 用户账户号
     * @return 返回用户店铺收款账户信息
     */
    @Override
    public List<UserStoreAccountBo> queryUserStorePayAccount(Long userNo) {

        //用户店铺信息
        List<UserStoreDo> userStoreDoList = userStoreManager.queryUserStore(userNo, null);
        if (CollectionUtils.isEmpty(userStoreDoList)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190105);
        }
        return getUserStoreAccountBos(userNo, userStoreDoList);
    }

    /**
     * 查询用户店铺收款账户信息通过用户号，币种
     *
     * @param userNo 用户账户号
     * @param ccy    币种
     * @return 返回用户店铺收款账户信息
     */
    @Override
    public List<UserStoreAccountBo> queryUserStorePayAccount(Long userNo, String ccy) {
        //用户店铺信息
        List<UserStoreDo> userStoreDoList = userStoreManager.queryUserStoreByCcy(userNo, ccy);
        if (CollectionUtils.isEmpty(userStoreDoList)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190105);
        }
        return getUserStoreAccountBos(userNo, userStoreDoList);
    }

    /**
     * 根据店铺信息获取账户信息
     *
     * @param userNo          会员号
     * @param userStoreDoList 店铺列表
     * @return 返回店铺账户信息
     */
    private List<UserStoreAccountBo> getUserStoreAccountBos(Long userNo, List<UserStoreDo> userStoreDoList) {
        if (CollectionUtils.isEmpty(userStoreDoList)) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190105);
        }
        if (userNo == null) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190104);
        }
        List<UserStoreAccountBo> userStoreAccountBoList = Lists.newArrayList();
        for (UserStoreDo userStoreDo : userStoreDoList) {
            UserPayeeAccountApplyDo userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByStoreNo(userStoreDo.getStoreNo());
            UserPayeeAccountDo userPayeeAccountDo = null;
            UserAccountBalBo userAccountBalBo = null;
            if (userPayeeAccountApplyDo.getAccountNo() != null) {
                //查询用户收款账户信息
                userPayeeAccountDo = userAccountManager.queryUserAccount(userNo, userPayeeAccountApplyDo.getAccountNo());
                if (NumberDict.TWO == userPayeeAccountApplyDo.getStatus()) {
                    //查询账户余额
                    userAccountBalBo = userBalBiz.queryUserAccountBal(userNo, userPayeeAccountApplyDo.getAccountNo());
                }
            } //用户编号和银行账户查询，只能查询一个，List取值第一个即可
            UserStoreAccountBo userStoreAccountBo = UserStoreAccountConvert
                    .paramConvert(userStoreDo, userAccountBalBo, userPayeeAccountDo
                            , null, null, userPayeeAccountApplyDo);
            userStoreAccountBoList.add(userStoreAccountBo);
        }
        return userStoreAccountBoList;
    }

    /**
     * 查询用户店铺账户信息分页
     *
     * @param userStoreDo 参数
     * @param userType    用户类型
     * @param pageNum     页面数
     * @param pageSize    页面条数
     * @return 用户店铺信息集合
     */
    @Override
    public PageBo<UserStoreAccountBo> queryStoreAccountForPage(UserStoreDo userStoreDo, Integer userType, int pageNum, int pageSize) {

        //用户店铺信息
        Page<UserStoreDo> pageList = userStoreManager.queryAllForPage(userStoreDo, pageNum, pageSize);
        if (CollectionUtils.isEmpty(pageList.getResult())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190105);
        }
        List<UserStoreAccountBo> userStoreAccountBoList = Lists.newArrayList();
        for (UserStoreDo userStoreDo1 : pageList.getResult()) {
            UserOrgDo userOrgDo = null;
            UserPersonalDo userPersonalDo = null;
            UserPayeeAccountDo userPayeeAccountDo = null;
            UserAccountBalBo userAccountBalBo = null;
            UserPayeeAccountApplyDo userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByStoreNo(userStoreDo1.getStoreNo());

            if (userPayeeAccountApplyDo.getStatus() == AccApplyStatusEnum.SUCCESS.getCode()) {
                userPayeeAccountDo = userAccountManager.queryUserAccount(userStoreDo1.getUserNo(), userPayeeAccountApplyDo.getAccountNo());
                //查询账户余额
                userAccountBalBo = userBalBiz.queryUserAccountBal(userStoreDo1.getUserNo(), userPayeeAccountApplyDo.getAccountNo());
            }
            //查询用户信息
            if (userType == UserTypeEnum.ORG.getType()) {
                //查询企业信息
                userOrgDo = userOrgManager.selectInfoByQualifiedNo(userPayeeAccountApplyDo.getQualifiedNo());
            }
            if (userType == UserTypeEnum.PERSONAL.getType()) {
                //查询个人信息
                userPersonalDo = userPersonalManager.selectInfoByQualifiedNo(userPayeeAccountApplyDo.getQualifiedNo());
            }
            //用户编号和银行账户查询，只能查询一个，List取值第一个即可
            UserStoreAccountBo userStoreAccountBo = UserStoreAccountConvert.paramConvert(userStoreDo1, userAccountBalBo, userPayeeAccountDo, userOrgDo, userPersonalDo, userPayeeAccountApplyDo);
            userStoreAccountBoList.add(userStoreAccountBo);
        }

        PageBo<UserStoreAccountBo> pageBo = new PageBo<>();
        pageBo.setList(userStoreAccountBoList);
        pageBo.setTotalSize(pageList.getTotal());
        return pageBo;
    }
}
