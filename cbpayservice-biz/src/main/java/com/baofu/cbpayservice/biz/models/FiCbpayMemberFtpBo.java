package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/7/24 ProjectName: cbpay-customs-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class FiCbpayMemberFtpBo {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * ftp地址
     */
    private String ftpIp;

    /**
     * FTP端口
     */
    private String ftpPort;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * ftp路径
     */
    private String ftpPath;

    /**
     * FTP模式
     */
    private Integer ftpMode;
}
