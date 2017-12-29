package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


/**
 * 结汇账户参数
 * <p>
 * User: lian zd Date:2017/7/31 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayAddReceivableAccountBo {

    /**
     * 商户编号
     */
    private Long memberId;
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
    private String companyId;

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

}
