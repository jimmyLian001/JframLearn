package com.baofu.international.global.account.core.biz;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/13 ProjectName:account-core  Version: 1.0
 */
public interface SettleFileProcessBiz {

    /**
     * 生成结汇申请文件
     *
     * @param sumBatchNo 汇总批次号
     * @return 返回生成的文件名称
     */
    String createSettleFile(Long sumBatchNo);

    /**
     * 结汇申请文件上传至FTP服务器中
     *
     * @param fileName 本地文件名称
     * @return 返回是否上传成功
     */
    Boolean uploadFtp(String fileName);
}
