package com.baofu.international.global.account.core.facade.model.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by luoping on 2017/11/23 0023.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserInfoDto extends com.baofu.international.global.account.core.facade.model.user.BaseDTO {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 申请编号
     */
    private Long userInfoNo;
}
