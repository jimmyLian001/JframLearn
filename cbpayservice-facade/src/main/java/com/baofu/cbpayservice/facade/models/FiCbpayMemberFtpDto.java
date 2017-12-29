package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 商户FTP信息参数
 * <p>
 * User: 康志光 Date:2017/7/21 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class FiCbpayMemberFtpDto implements Serializable {

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
