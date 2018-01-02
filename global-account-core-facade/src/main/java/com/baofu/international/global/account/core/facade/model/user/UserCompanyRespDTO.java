package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业用户响应对象 DTO
 * <p>
 * 1、企业用户响应对象
 * </p>
 *
 * @author 陶伟超
 * @version : 1.0
 * @date : 2017/11/29
 */
@Getter
@Setter
@ToString
public class UserCompanyRespDTO implements Serializable {

    /**
     * 会员号
     */
    private String userNo;
    /**
     * 会员姓名
     */
    private String name;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 实名状态（0-未认证；1-认证中 2-已认证 默认为未认证）
     */
    private String realNameStatus;
    /**
     * 账户是否开通（0-未开通；1-开通）
     */
    private String openAccStatus;
    /**
     * 创建时间
     */
    private Date createAt;

}
