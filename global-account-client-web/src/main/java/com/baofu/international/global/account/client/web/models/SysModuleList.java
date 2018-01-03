package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by luoping on 2017/11/22 0022.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysModuleList {

    private List<FixPhoneNoApplyForm> modules;

    private String currentPhoneNumber;
}
