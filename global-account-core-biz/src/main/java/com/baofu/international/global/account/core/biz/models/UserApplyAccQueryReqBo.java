package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Skyee申请下载信息请求参数
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/26 0026
 */
@Setter
@Getter
@ToString
public class UserApplyAccQueryReqBo {

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

    /**
     * 申请记录编号：下载请求成功后，会生成此记录编号
     */
    private Long recordId;

}
