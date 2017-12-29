package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FiCbpayMemberFtpDo extends BaseDo {

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