package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 1、分页信息
 * </p>
 *
 * @author : wuyazi
 * @version : 1.0.0
 * @date : 2017/11/08
 */
@Setter
@Getter
@ToString
public class PageDto<T> implements Serializable {

    /**
     * 集合信息
     */
    private List<T> list;

    /**
     * 总条数
     */
    private long totalSize;

}
