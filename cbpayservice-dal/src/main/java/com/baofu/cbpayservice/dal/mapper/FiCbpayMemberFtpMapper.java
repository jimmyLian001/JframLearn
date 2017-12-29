package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库FTP信息
 * <p>
 * 1、添加FTP信息
 * 2、根据记录编号查询FTP信息
 * 3、根据记录编号更新表信息
 * </p>
 * User: 康志光　Date:2017/07/21 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbpayMemberFtpMapper {

    /**
     * 添加FTP信息
     *
     * @param fiCbpayMemberFtpDo FTP信息对象
     */
    int insert(FiCbpayMemberFtpDo fiCbpayMemberFtpDo);

    /**
     * 根据记录编号查询FTP信息
     *
     * @param recordId 记录编号
     * @return FiCbpayMemberFtpDo 返回订单总表信息
     */
    FiCbpayMemberFtpDo queryByRecordId(@Param("recordId") Long recordId);

    /**
     * 根据memberId 查询商户FTP地址
     *
     * @param memberId 商户号
     * @return FiCbpayMemberFtpDo 商户ftp信息
     */
    FiCbpayMemberFtpDo queryByMemberId(@Param("memberId") Long memberId);

    /**
     * 根据记录编号更新表信息
     *
     * @param fiCbpayMemberFtpDo
     * @return
     */
    int updateByRecordId(FiCbpayMemberFtpDo fiCbpayMemberFtpDo);
}
