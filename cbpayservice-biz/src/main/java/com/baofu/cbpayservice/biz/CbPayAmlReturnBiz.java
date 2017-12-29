package com.baofu.cbpayservice.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwRemitResultDto;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceDo;

import java.util.List;

/**
 * 反洗钱渠道返回处理
 * <p>
 * User: 不良人 Date:2017/8/8 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayAmlReturnBiz {

    /**
     * 反洗钱全部成功处理
     *
     * @param fileUploadDoList    文件批次信息集合
     * @param fiCbPayRemittanceDo 汇款批次信息集合
     * @param updateBy            更新人
     */
    void amlSuccess(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo, String updateBy);

    /**
     * 反洗钱全部失败处理
     *
     * @param fileUploadDoList    文件批次信息集合
     * @param fiCbPayRemittanceDo 汇款批次信息集合
     * @param updateBy            更新人
     */
    void amlFail(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo, String updateBy);

    /**
     * 反洗钱部分成功处理
     *
     * @param fileUploadDoList       文件批次信息集合
     * @param fiCbPayRemittanceDo    汇款批次信息集合
     * @param cgwRemitBatchResultDto 渠道返回信息
     * @param updateBy               更新人
     */
    void portionSuccess(List<FiCbPayFileUploadDo> fileUploadDoList, FiCbPayRemittanceDo fiCbPayRemittanceDo,
                        CgwRemitResultDto cgwRemitBatchResultDto, String updateBy);

}
