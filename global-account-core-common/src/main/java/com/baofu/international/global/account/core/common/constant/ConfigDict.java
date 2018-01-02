package com.baofu.international.global.account.core.common.constant;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * boc配置
 * <p>
 * 1.
 * </p>
 *
 * @author wukong
 * @version 1.0.0
 * @date 2017/11/4
 */
@Getter
@ToString
@RefreshScope
@Component
public class ConfigDict {

    /**
     * 发送短信用户名
     */
    @Value("${sms.user.name}")
    private String smsUserName;

    /**
     * 发送短信用户密码
     */
    @Value("${sms.user.pass}")
    private String smsUserPass;

    /**
     * 邮件发送人邮件地址
     */
    @Value("${email.user.name}")
    private String emailUserName;

    /**
     * 邮件发送人邮件密码
     */
    @Value("${email.user.pass}")
    private String emailUserPass;

    /**
     * 邮箱Host
     */
    @Value("${mail.smtp.host}")
    private String emailHost;

    /**
     * 邮箱Host
     */
    @Value("${mail.smtp.auth}")
    private String emailAuth;

    /**
     * 新颜开通的商户編號
     */
    @Value("${xinYan.memberId}")
    private Long xinYanMemberId;

    /**
     * 新颜开通的终端编号
     */
    @Value("${xinYan.terminal}")
    private Long xinYanTerminal;

    /**
     * 新颜证书私钥路径，服务器中的路径
     */
    @Value("${xinYan.private.key.path}")
    private String xinYanPrivateKeyPath;

    /**
     * 新颜证书私钥密码
     */
    @Value("${xinYan.private.key.pass}")
    private String xinYanPrivateKeyPass;

    /**
     * 新颜认证请求URL
     */
    @Value("${xinYan.reqUrl}")
    private String xinYanReqUrl;


    /**
     * 宝付开通的统一结汇商户編號
     */
    @Value("${settle.memberId}")
    private Long settleMemberId;

    /**
     * 宝付开通的统一结汇商户名称
     */
    @Value("${settle.member.name}")
    private String settleMemberName;

    /**
     * 宝付开通的统一结汇的终端编号
     */
    @Value("${settle.terminal}")
    private Long settleTerminal;

    /**
     * 宝付开通的统一结汇证书私钥路径，服务器中的路径
     */
    @Value("${settle.private.key.path}")
    private String settlePrivateKeyPath;

    /**
     * 宝付开通的统一结汇证书公钥路径，服务器中的路径
     */
    @Value("${settle.public.key.path}")
    private String settlePublicKeyPath;

    /**
     * 宝付开通的统一结汇证书私钥密码
     */
    @Value("${settle.private.key.pass}")
    private String settlePrivateKeyPass;

    /**
     * 宝付开通的统一结汇请求URL
     */
    @Value("${settle.reqUrl}")
    private String settleReqUrl;

    /**
     * 宝付代付请求接口
     */
    @Value("${transfer.req.url}")
    private String transferReqUrl;

    /**
     * 子账户转账到主账户费率
     */
    @Value("${withdraw_fee_rate}")
    private String withdrawFeeRate;

    /**
     * 手续费百分比
     */
    @Value("${payee.fee.rate}")
    private String payeeFeeRate;

    /**
     * 结汇订单明细金额查询汇率:计算订单金额不能超过45000
     */
    @Value("${ping_an_settle_channel_id}")
    private String pingAnSettleChannelId;

    /**
     * 商户编号
     */
    @Value("${payee.member.id}")
    private String payeeMemberId;

    /**
     * 转出账户号
     */
    @Value("${payee.source.acc.no}")
    private String sourceAccNo;

    /**
     * 目标账户号
     */
    @Value("${payee.dest.acc.no}")
    private String destAccNo;

    /**
     * 宝付wpre主账户
     */
    @Value("${master_acc_no}")
    private String masterAccNo;

    /**
     * 企业是否实名认证
     */
    @Value("${company_auth_flag}")
    private String companyAuthFlag;

    /**
     * 身份证二要素是否实名认证
     */
    @Value("${id_auth_flag}")
    private String idAuthFlag;

    /**
     * 银行卡三要素是否实名认证
     */
    @Value("${card_auth_flag}")
    private String cardAuthFlag;

    /**
     * wyre渠道号
     */
    @Value("${WYRE_CHANNEL_ID}")
    private String wyreChannelId;

    /**
     * 用户上传提现文件下载路径
     */
    @Value("${withdrawOrder.down.filePath}")
    private String withdrawDownLoadPath;

    /**
     * 商户FTP中IP地址
     */
    @Value("${member.ftp.ip}")
    private String memberFtpIp;

    /**
     * 商户FTP中IP端口
     */
    @Value("${member.ftp.port}")
    private String memberFtpPort;

    /**
     * 商户FTP中用户名
     */
    @Value("${member.ftp.user.name}")
    private String memberFtpUserName;

    /**
     * 商户FTP中用户名密码
     */
    @Value("${member.ftp.user.pass}")
    private String memberFtpUserPass;

    /**
     * 商户FTP中路徑
     */
    @Value("${member.ftp.path}")
    private String memberFtpPath;

    /**
     * 域名
     */
    @Value("${global.account.domain}")
    private String globalAccountDomain;

    /**
     * 访问gate域名
     */
    @Value("${GATE_HOST}")
    private String gateHost;

    /**
     * 提现实时汇率渠道
     */
    @Value("${boc_settle_channel_id}")
    private String bocSettleChannelId;

    /**
     * Wyre收款渠道费率
     */
    @Value("${Wyre.channel.fee.rate}")
    private String wyreChannelFeeRate;

    /**
     * 系统临时目录，所有的请使用这个临时目录，避免需要维护很多的文件夹配置
     */
    @Value("${system.temp.path}")
    private String systemTempPath;

    /**
     * Skyee 渠道编号
     */
    @Value("${skyee.channel.id}")
    private String skyeeChannelId;
}
