package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 合法证件类型信息
 * <p>
 * @author : hetao Date:2017/11/04 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class IdTypeInfo {
    /**
     * 证件类型id
     */
    private String typeId;

    /**
     * 证件类型名称
     */
    private String typeName;
}
