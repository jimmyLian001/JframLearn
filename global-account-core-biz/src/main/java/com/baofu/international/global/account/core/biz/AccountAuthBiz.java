package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.OrgAuthReqBo;
import com.baofu.international.global.account.core.biz.models.PersonAuthReqBo;

/**
 * 认证操作接口
 * <p>
 * 1、身份证二要素认证
 * 2、银行卡三要素认证
 * </p>
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
public interface AccountAuthBiz {

    /**
     * 身份证二要素认证
     *
     * @param personAuthReqBo 个人信息
     * @return 响应结果
     */
    Long personalAuthApply(PersonAuthReqBo personAuthReqBo);

    /**
     * 银行卡三要素认证
     *
     * @param personAuthReqBo 个人信息
     * @return 响应结果
     */
    Long orgAuthApply(OrgAuthReqBo personAuthReqBo);
}
