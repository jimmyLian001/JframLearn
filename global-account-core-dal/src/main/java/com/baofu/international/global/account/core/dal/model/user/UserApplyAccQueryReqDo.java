package com.baofu.international.global.account.core.dal.model.user;

import com.baofu.international.global.account.core.dal.model.PageBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、用户申请开通账户信息查询请求条件信息
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/17
 */
@Setter
@Getter
@ToString
public class UserApplyAccQueryReqDo extends PageBase {

    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 平台
     */
    private String storePlatform;

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 认证状态
     */
    private Integer realnameStatus;


    /**
     * 开始时间
     */
    private String begin;

    /**
     * 结束时间
     */
    private String end;
}
