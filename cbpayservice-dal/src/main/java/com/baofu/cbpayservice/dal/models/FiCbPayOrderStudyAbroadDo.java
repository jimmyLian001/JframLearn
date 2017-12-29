package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 跨境订单留学信息表
 * User: feng_jiang Date:2017/07/07
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayOrderStudyAbroadDo extends BaseDo {
    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 学校所在国家
     */
    private String schoolCountryCode;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 入学通知书编号
     */
    private String admissionNoticeId;

}