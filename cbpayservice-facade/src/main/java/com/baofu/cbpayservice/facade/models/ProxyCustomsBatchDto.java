package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 跨境汇款汇款明细文件支持批量上传参数传入对象
 * <p/>
 * User: lian zd Date:2017/10/25 ProjectName: cbpayservice Version:1.0
 */
@Getter
@Setter
@ToString
public class ProxyCustomsBatchDto implements Serializable {

    /**
     * 跨境汇款汇款明细文件集合
     */
    @NotNull(message = "跨境汇款汇款明细文件集合不能为空")
    @Size(min = 1,message = "跨境汇款汇款明细文件集合size不能为0")
    private List<ProxyCustomsDto> proxyCustomsDtoList;

}
