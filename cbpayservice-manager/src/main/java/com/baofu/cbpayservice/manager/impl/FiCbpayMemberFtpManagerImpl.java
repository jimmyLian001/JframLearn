package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.dal.mapper.FiCbpayMemberFtpMapper;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import com.baofu.cbpayservice.manager.FiCbpayMemberFtpManager;
import com.system.commons.utils.ParamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 数据库FTP信息
 * <p>
 * 1、添加FTP信息
 * 2、根据记录编号查询FTP信息
 * 3、根据记录编号更新表信息
 * </p>
 * User: 康志光　Date:2017/07/21 ProjectName: cbpayservice Version: 1.0
 */
@Repository
public class FiCbpayMemberFtpManagerImpl implements FiCbpayMemberFtpManager {

    @Autowired
    private FiCbpayMemberFtpMapper fiCbpayMemberFtpMapper;


    /**
     * 添加FTP信息
     *
     * @param fiCbpayMemberFtpDo FTP信息对象
     */
    @Override
    public void create(FiCbpayMemberFtpDo fiCbpayMemberFtpDo) {
        ParamValidate.checkUpdate(fiCbpayMemberFtpMapper.insert(fiCbpayMemberFtpDo), "新增FTP信息失败");
    }

    /**
     * 根据记录编号查询FTP信息
     *
     * @param recordId 记录编号
     * @return FiCbpayMemberFtpDo 返回订单总表信息
     */
    @Override
    public FiCbpayMemberFtpDo searchByRecordId(Long recordId) {
        return fiCbpayMemberFtpMapper.queryByRecordId(recordId);
    }

    /**
     * 根据商户号查询FTP信息
     *
     * @param memberId 记录编号
     * @return FiCbpayMemberFtpDo 返回订单总表信息
     */
    @Override
    public FiCbpayMemberFtpDo searchByMemberId(Long memberId) {
        return fiCbpayMemberFtpMapper.queryByMemberId(memberId);
    }

    /**
     * 根据记录编号更新表信息
     *
     * @param fiCbpayMemberFtpDo
     * @return
     */
    @Override
    public void modifyByRecordId(FiCbpayMemberFtpDo fiCbpayMemberFtpDo) {
        ParamValidate.checkUpdate(fiCbpayMemberFtpMapper.updateByRecordId(fiCbpayMemberFtpDo), "更新FTP表信息失败");
    }
}
