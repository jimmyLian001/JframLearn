package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 职业信息
 * <p>
 *
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class OccupationInfoDo implements Serializable {
    /**
     * 职业id
     */
    private String occupationId;

    /**
     * 职业名称
     */
    private String occupationName;
}
