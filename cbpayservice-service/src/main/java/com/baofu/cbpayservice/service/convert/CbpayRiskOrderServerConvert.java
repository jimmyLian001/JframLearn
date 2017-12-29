package com.baofu.cbpayservice.service.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.member.manager.common.utils.DateUtils;
import com.baofu.cbpayservice.biz.models.CbPayVerifyReqBo;
import com.baofu.cbpayservice.biz.models.CbPayVerifyResBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.CbPayOrderRiskControlDo;
import com.baofu.cbpayservice.dal.models.CbPayVerifyCountDo;
import com.baofu.cbpayservice.dal.models.FiCbPayVerifyDo;
import com.baofu.cbpayservice.facade.models.CbPayOrderRiskControlReqDto;
import com.baofu.cbpayservice.facade.models.FiCbPayVerifyReqDto;

import java.util.Date;

/**
 * Service层参数转换
 * <p>
 * 1、风控订单审核接口请求参数转换成Biz层请求参数信息
 * </p>
 * User: wdj Date:2017/04/28 ProjectName: cbpays-ervice Version: 1.0
 */
public class CbpayRiskOrderServerConvert {

    /**
     * 将接口参数转成do参数
     *
     * @param cbPayOrderRiskControlReqDto 请求参数
     * @return 转换后的参数
     */
    public static CbPayOrderRiskControlDo toRiskControlDo(CbPayOrderRiskControlReqDto cbPayOrderRiskControlReqDto) {

        CbPayOrderRiskControlDo cbPayOrderRiskControlDo = new CbPayOrderRiskControlDo();
        cbPayOrderRiskControlDo.setOrderId(cbPayOrderRiskControlReqDto.getOrderId());
        cbPayOrderRiskControlDo.setMemberId(cbPayOrderRiskControlReqDto.getMemberId());
        cbPayOrderRiskControlDo.setMemberTransId(cbPayOrderRiskControlReqDto.getMemberTransId());
        cbPayOrderRiskControlDo.setFakeFlag(cbPayOrderRiskControlReqDto.getFakeFlag());
        cbPayOrderRiskControlDo.setWayBillFlag(cbPayOrderRiskControlReqDto.getWayBillFlag());
        cbPayOrderRiskControlDo.setArtifiStatus(cbPayOrderRiskControlReqDto.getArtifiStatus());
        cbPayOrderRiskControlDo.setRemarks(cbPayOrderRiskControlReqDto.getRemarks());
        cbPayOrderRiskControlDo.setUpdateBy(cbPayOrderRiskControlReqDto.getUpdateBy());

        return cbPayOrderRiskControlDo;
    }

    /**
     * 参数转换
     *
     * @param fileBatchNo   文件批次号
     * @param authCount     认证笔数
     * @param orderType     订单类型
     * @param authFailCount 认证失败笔数
     * @param authSucCount  认证成功笔数
     * @return CbPayVerifyCountDo
     */
    public static CbPayVerifyCountDo toCbPayVerifyCountDo(Long fileBatchNo, Integer authCount,
                                                          String orderType, Integer authFailCount,
                                                          Integer authSucCount, Long memberId, String memberName) {

        CbPayVerifyCountDo cbPayVerifyCountDo = new CbPayVerifyCountDo();
        cbPayVerifyCountDo.setFileBatchNo(fileBatchNo);
        cbPayVerifyCountDo.setVerifyCount(authCount);
        cbPayVerifyCountDo.setVerifyStatus(1);
        cbPayVerifyCountDo.setOrderType(Integer.parseInt(orderType));
        cbPayVerifyCountDo.setVerifyCountFail(authFailCount);
        cbPayVerifyCountDo.setVerifyCountSuc(authSucCount);
        cbPayVerifyCountDo.setUpdateBy(memberName);
        cbPayVerifyCountDo.setCreateBy(memberName);
        cbPayVerifyCountDo.setMemberId(memberId);
        return cbPayVerifyCountDo;
    }

    /**
     * 转成新颜请求参数
     *
     * @param fiCbPayVerifyDo 参数信息
     * @return 转换结果
     */
    public static CbPayVerifyReqBo toCbPayVerifyResBo(FiCbPayVerifyDo fiCbPayVerifyDo, String terminalId, Long memberId) {

        CbPayVerifyReqBo cbPayVerifyReqBo = new CbPayVerifyReqBo();
        cbPayVerifyReqBo.setIdCard(SecurityUtil.desDecrypt(fiCbPayVerifyDo.getIdCard(), Constants.CARD_DES_PASSWD));
        cbPayVerifyReqBo.setIdName(fiCbPayVerifyDo.getIdName());
        cbPayVerifyReqBo.setMemberId(memberId);
        cbPayVerifyReqBo.setTerminalId(terminalId);
        cbPayVerifyReqBo.setTransDate(DateUtils.pareDate(new Date(), DateUtils.DATE_TIME_FORMAT_NO_SPLIT));
        cbPayVerifyReqBo.setTransId(fiCbPayVerifyDo.getTransId());
        return cbPayVerifyReqBo;
    }

    public static void toFiCbPayVerifyDo(FiCbPayVerifyDo fiCbPayVerifyDo, CbPayVerifyResBo cbPayVerifyResBo,
                                         String memberName, String terminalId, Long memberIdOfAuth, String orderType) {

        fiCbPayVerifyDo.setTransNo(cbPayVerifyResBo.getTransNo() == null ? "" : cbPayVerifyResBo.getTransNo());
        fiCbPayVerifyDo.setTransId(cbPayVerifyResBo.getTransId() == null ? "" : cbPayVerifyResBo.getTransId());
        fiCbPayVerifyDo.setDesc(cbPayVerifyResBo.getDesc() == null ? "" : cbPayVerifyResBo.getDesc());
        fiCbPayVerifyDo.setFeeFlag(cbPayVerifyResBo.getFeeFlag() == null ? "N" : cbPayVerifyResBo.getFeeFlag());
        fiCbPayVerifyDo.setCode(cbPayVerifyResBo.getCode());
        fiCbPayVerifyDo.setTerminalId(StringUtils.isBlank(fiCbPayVerifyDo.getTerminalId()) ? terminalId : fiCbPayVerifyDo.getTerminalId());
        fiCbPayVerifyDo.setMemberId(fiCbPayVerifyDo.getMemberId() == null ? memberIdOfAuth : fiCbPayVerifyDo.getMemberId());
        fiCbPayVerifyDo.setCreateBy(memberName);
        fiCbPayVerifyDo.setUpdateBy(memberName);
        fiCbPayVerifyDo.setIdCard(fiCbPayVerifyDo.getIdCard());
        fiCbPayVerifyDo.setTransDate(DateUtils.pareDate(new Date(), DateUtils.DATE_TIME_FORMAT_NO_SPLIT));
        // 0：购汇订单   1：结汇订单
        fiCbPayVerifyDo.setSource("0".equals(orderType) ? 0 : 1);
    }

    /**
     * 单笔认证参数装换
     *
     * @param fiCbPayVerifyReqDto 请求信息
     * @param terminalId          商户终端号
     * @param memberIdOfAuth      商户号
     * @return 结果
     */
    public static CbPayVerifyReqBo toCbPaySingleVerifyResBo(FiCbPayVerifyReqDto fiCbPayVerifyReqDto, String terminalId,
                                                            Long memberIdOfAuth) {

        CbPayVerifyReqBo cbPayVerifyReqBo = new CbPayVerifyReqBo();
        cbPayVerifyReqBo.setIdCard(fiCbPayVerifyReqDto.getIdCard());
        cbPayVerifyReqBo.setIdName(fiCbPayVerifyReqDto.getIdName());
        cbPayVerifyReqBo.setMemberId(memberIdOfAuth);
        cbPayVerifyReqBo.setTerminalId(terminalId);
        cbPayVerifyReqBo.setTransDate(DateUtils.pareDate(new Date(), DateUtils.DATE_TIME_FORMAT_NO_SPLIT));
        cbPayVerifyReqBo.setTransId(fiCbPayVerifyReqDto.getMemberTransId());
        return cbPayVerifyReqBo;
    }


    /**
     * 单笔实名认证参数转换
     *
     * @param fiCbPayVerifyReqDto 请求参数
     * @param cbPayVerifyResBo    相应参数
     * @param terminalId          商户终端号
     * @param memberIdOfAuth      商户ID
     * @return 结果
     */
    public static FiCbPayVerifyDo toFiCbPaySingleVerifyDo(FiCbPayVerifyReqDto fiCbPayVerifyReqDto,
                                                          CbPayVerifyResBo cbPayVerifyResBo, String terminalId, Long memberIdOfAuth) {
        FiCbPayVerifyDo fiCbPayVerifyDo = new FiCbPayVerifyDo();
        fiCbPayVerifyDo.setOrderId(fiCbPayVerifyReqDto.getOrderId());
        fiCbPayVerifyDo.setTransId(cbPayVerifyResBo.getTransId() == null ? "" : cbPayVerifyResBo.getTransId());
        fiCbPayVerifyDo.setMemberId(fiCbPayVerifyReqDto.getMemberId());
        fiCbPayVerifyDo.setTerminalId(terminalId);
        fiCbPayVerifyDo.setTransDate(DateUtils.pareDate(new Date(), DateUtils.DATE_TIME_FORMAT_NO_SPLIT));
        fiCbPayVerifyDo.setIdCard(SecurityUtil.desEncrypt(fiCbPayVerifyReqDto.getIdCard(),
                Constants.CARD_DES_PASSWD));
        fiCbPayVerifyDo.setIdName(fiCbPayVerifyReqDto.getIdName());
        fiCbPayVerifyDo.setTransNo(cbPayVerifyResBo.getTransNo() == null ? "" : cbPayVerifyResBo.getTransNo());
        fiCbPayVerifyDo.setDesc(cbPayVerifyResBo.getDesc() == null ? "" : cbPayVerifyResBo.getDesc());
        fiCbPayVerifyDo.setFeeFlag(cbPayVerifyResBo.getFeeFlag() == null ? "N" : cbPayVerifyResBo.getFeeFlag());
        fiCbPayVerifyDo.setCode(cbPayVerifyResBo.getCode());
        fiCbPayVerifyDo.setCreateBy(fiCbPayVerifyReqDto.getMemberName());
        fiCbPayVerifyDo.setUpdateBy(fiCbPayVerifyReqDto.getMemberName());
        fiCbPayVerifyDo.setSource("0".equals(fiCbPayVerifyReqDto.getOrderType()) ? 0 : ("1".equals(fiCbPayVerifyReqDto.getOrderType()) ? 1 : 2));
        return fiCbPayVerifyDo;
    }

    /**
     * 认证失败的情况：认证数量加1     失败数量加1     成功数量不变
     * 认证成功的情况：认证数量加1     失败数量不变     成功数量加1
     *
     * @param cbPayVerifyCountDo
     */
    public static CbPayVerifyCountDo toChangeDate(CbPayVerifyCountDo cbPayVerifyCountDo, Integer flag, String memberName) {

        cbPayVerifyCountDo.setVerifyCount(cbPayVerifyCountDo.getVerifyCount() + 1);
        if (flag < 0 || flag == 1) {
            cbPayVerifyCountDo.setVerifyCountFail(cbPayVerifyCountDo.getVerifyCountFail() + 1);
        } else {
            cbPayVerifyCountDo.setVerifyCountSuc(cbPayVerifyCountDo.getVerifyCountSuc() + 1);
        }
        cbPayVerifyCountDo.setUpdateBy(memberName);
        cbPayVerifyCountDo.setVerifyStatus(1);
        return cbPayVerifyCountDo;
    }

    /**
     * 组装结果
     *
     * @param cbPayVerifyCountDo 原始数据
     * @param authSucCount       认证成功数量
     * @param authFailCount      认证失败数量
     * @param authCount          需要认证的数量
     * @param memberName         商户名称
     * @return 结果
     */
    public static CbPayVerifyCountDo toChangeNeedData(CbPayVerifyCountDo cbPayVerifyCountDo, int authSucCount,
                                                      int authFailCount, int authCount, String memberName) {
        cbPayVerifyCountDo.setVerifyCount(cbPayVerifyCountDo.getVerifyCount() + authCount);
        cbPayVerifyCountDo.setVerifyCountFail(cbPayVerifyCountDo.getVerifyCountFail() + authFailCount);
        cbPayVerifyCountDo.setVerifyCountSuc(cbPayVerifyCountDo.getVerifyCountSuc() + authSucCount);
        cbPayVerifyCountDo.setUpdateBy(memberName);
        cbPayVerifyCountDo.setVerifyStatus(1);
        return cbPayVerifyCountDo;
    }
}
