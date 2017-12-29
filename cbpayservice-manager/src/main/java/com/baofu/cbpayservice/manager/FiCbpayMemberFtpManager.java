package com.baofu.cbpayservice.manager;


import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;

/**
 * 数据库FTP信息
 * <p>
 * 1、添加FTP信息
 * 2、根据记录编号查询FTP信息
 * 3、根据记录编号更新表信息
 * </p>
 * User: 康志光　Date:2017/07/21 ProjectName: cbpayservice Version: 1.0
 */
public interface FiCbpayMemberFtpManager {

    /**
     * 添加FTP信息
     *
     * @param fiCbpayMemberFtpDo FTP信息对象
     */
    void create(FiCbpayMemberFtpDo fiCbpayMemberFtpDo);

    /**
     * 根据记录编号查询FTP信息
     *
     * @param recordId 记录编号
     * @return FiCbpayMemberFtpDo 返回订单总表信息
     */
    FiCbpayMemberFtpDo searchByRecordId(Long recordId);

    /**
     * 根据商户号查询FTP信息
     *
     * @param memberId 记录编号
     * @return FiCbpayMemberFtpDo 返回订单总表信息
     */
    FiCbpayMemberFtpDo searchByMemberId(Long memberId);

    /**
     * 根据记录编号更新表信息
     *
     * @param fiCbpayMemberFtpDo 商户ftp信息
     */
    void modifyByRecordId(FiCbpayMemberFtpDo fiCbpayMemberFtpDo);
}
