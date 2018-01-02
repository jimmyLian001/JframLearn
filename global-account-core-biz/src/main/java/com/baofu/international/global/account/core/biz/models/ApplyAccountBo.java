package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


/**
 * <p>
 * 境外账户申请
 * </p>
 * <p>
 * User: yangjian Date:2017/11/04 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class ApplyAccountBo {

    /**
     * 商户编号
     */
    private Long userNo;
    /**
     * 开户币种
     */
    private String ccy;
    /**
     * 申请编号
     */
    private Long applyId;
    /**
     * 商户类型
     */
    private String memberType;
    /**
     * 商户账户名称（邮箱）
     */
    private String memberAccNo;
    /**
     * 企业编号
     */
    private Long storeNo;

    /**
     * 商户网址
     */
    private String accountHolderWebsite;
    /**
     * 联系人姓
     */
    private String firstName;
    /**
     * 联系人名
     */
    private String lastName;
    /**
     * 护照
     */
    private String passport;

    /**
     * 联系人方式
     */
    private String phoneNumber;
    /**
     * 法人出生日期
     */
    private Date birthDay;
    /**
     * 企业地址
     */
    private String address;
    /**
     * 企业所在城市
     */
    private String city;
    /**
     * 企业所在省份
     */
    private String province;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 企业所在国家
     */
    private String country;
    /**
     * 营业执照DFS编号
     */
    private Long licenseDfsId;
    /**
     * 证件照DFS编号
     */
    private Long passportDfsId;
    /**
     * 证件号码
     */
    private String idNo;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 银行账户
     */
    private String bankAccountNumber;
    /**
     * 路由账户
     */
    private String routingNumber;
    /**
     * 钱包账户
     */
    private String userId;
    /**
     * 商户英文名
     */
    private String companyName;
    /**
     * 店铺英文名
     */
    private String storeName;
    /**
     * 店铺所在平台
     */
    private String storePlatform;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 店铺是否存在
     */
    private String storeExist;
    /**
     * 经营范围
     */
    private String managementCategory;
    /**
     * 卖家编号
     */
    private String sellerId;
    /**
     * 访问键编码
     */
    private String awsAccessKey;
    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 实名认证状态
     */
    private Integer realNameStatus;

    /**
     * 渠道编号
     */
    private Long channelId;
}
