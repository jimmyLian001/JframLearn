package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 经营类别信息
 * <p>
 * @author : hetao Date:2017/11/04 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class ManagementCategoryInfo {
    /**
     * 经营类别id
     */
    private String categoryId;

    /**
     * 经营类别名称
     */
    private String categoryName;
}
