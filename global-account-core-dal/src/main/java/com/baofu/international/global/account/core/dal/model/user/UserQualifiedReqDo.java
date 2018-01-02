package com.baofu.international.global.account.core.dal.model.user;

import com.baofu.international.global.account.core.dal.model.PageBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户资质请求对象
 * <p>
 * description: 用户资质请求对象
 * <p/>
 * Created by  陶伟超 on 2017/11/7 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class UserQualifiedReqDo extends PageBase {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户类型
     */
    private Integer userType;


}
