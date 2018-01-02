package com.baofu.international.global.account.core.dal.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 * 1、用户申请开通收款账户查询列表信息
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
public class UserApplyAccQueryRespDo {

    /**
     * 用户申请编号
     */
    private Long applyId;

    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 所属平台
     */
    private String storePlatform;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * key
     */
    private String awsAccessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 营业执照文件DFS编号
     */
    private Long idDfsId;

    /**
     * 组织机构文件DFS编号
     */
    private Long orgCodeCertDfsId;

    /**
     * 身份证正面照DFS编号
     */
    private Long legalIdFrontDfsId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 渠道編號
     */
    private Long channelId;

    /**
     * 实名状态
     */
    private Integer realnameStatus;

    /**
     * 申請狀態
     */
    private Integer applyStatus;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 反面
     */
    private Long idReverseDfsId;

    /**
     * 税务
     */
    private Long taxRegistrationCertDfsId;
}
