package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 职业信息
 * <p>
 * @author : hetao Date:2017/11/05 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class OccupationInfo {
    /**
     * 职业id
     */
    private String occupationId;

    /**
     * 职业名称
     */
    private String occupationName;
}
