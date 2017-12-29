package com.baofu.cbpayservice.biz.models;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwRemitReqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 备案明细
 * </p>
 * User: wanght Date:{DATE} ProjectName: asias-icpservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CgwRemitReqDetailDto {

    /**
     * 备案明细
     */
    private List<CgwRemitReqDto> cgwRemitReqDtoList;

    /**
     * 备案总金额
     */
    private BigDecimal remitSumAmt;

    /**
     * dfs文件id
     */
    private Long dfsFileId;
}
