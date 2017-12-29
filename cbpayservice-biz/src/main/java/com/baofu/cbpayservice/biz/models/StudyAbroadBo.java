package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 留学对象
 * <p>
 * User: 不良人 Date:2017/1/11 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class StudyAbroadBo {

    /**
     * 学校所在国家
     */
    private String studyCounty;

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
    private String studentNumber;

    /**
     * 入学通知书编号
     */
    private String studentOfferNumber;
}
