package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.AccountBalanceRespBo;
import com.baofu.cbpayservice.biz.models.BatchRemitBo;
import com.baofu.cbpayservice.biz.models.BatchRemitTrialBo;
import com.baofu.cbpayservice.biz.models.RemitFileBo;

import java.util.List;

/**
 * 批量汇款接口
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public interface BatchRemitBiz {

    /**
     * @param memberId 商户号
     * @return 账户余额
     */
    AccountBalanceRespBo queryBalance(Long memberId);

    /**
     * 批量汇款文件上传
     *
     * @param remitFileBo 文件参数
     * @return 文件批次号
     */
    Long batchRemitFileUpload(RemitFileBo remitFileBo);

    /**
     * 批量汇款创建文件批次
     *
     * @param batchRemitBo 汇款文件批次参数
     * @param flag         标识
     * @param key          锁key
     * @return 文件批次号
     */
    Long createFileBatch(BatchRemitBo batchRemitBo, Boolean flag, String key);

    /**
     * 试算
     *
     * @param batchRemitBos 试算参数
     * @return 结果
     */
    List<BatchRemitTrialBo> batchRemitTrial(List<BatchRemitBo> batchRemitBos);

    /**
     * 创建失败汇款订单
     *
     * @param batchRemitBo 汇款订单请求信息
     */
    void createErrorRemitOrder(BatchRemitBo batchRemitBo);
}
