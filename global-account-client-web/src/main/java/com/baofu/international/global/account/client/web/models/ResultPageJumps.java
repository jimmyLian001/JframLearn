package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by luoping on 2017/11/22 0022.
 */
@Getter
@Setter
@ToString
public class ResultPageJumps {
    /**
     * 手机号或邮箱
     */
    private String loginNo;
    /**
     * 结果路径
     */
    private String resultUrl;
}
