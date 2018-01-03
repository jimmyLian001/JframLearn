package com.baofu.international.global.account.client.common.util;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 公共工具类
 * <p>
 * </p>
 * @author 蒋文哲
 * @date : 2017/3/30
 */
@Slf4j
public final class DfsUtil {

    private DfsUtil() {

    }

    /**
     * 文件上传
     *
     * @param inputStream 文件信息
     * @param fileName    文件名
     * @return 上传结果
     */
    public static CommandResDTO upload(InputStream inputStream, String fileName) {
        try {
            InsertReqDTO insertReqDTO = new InsertReqDTO();
            //文件名
            insertReqDTO.setFileName(fileName);
            //机构编码
            insertReqDTO.setOrgCode("GLOBAL");
            //文件组（参照枚举类FileGroup 不同文件组存放时效不同，对账文件存放90天）
            insertReqDTO.setFileGroup(FileGroup.PRODUCT);
            //文件日期
            insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));

            insertReqDTO.setRemark("代理跨境结算服务贸易文件校验错误信息");
            return DfsClient.upload(inputStream, insertReqDTO);
        } catch (Exception e) {
            log.error("文件上传异常", e);
            return null;
        }
    }

    /**
     * DFS下载信息
     *
     * @param fileId 文件ID
     * @return 结果集
     */
    public static String getDownloadUrl(long fileId) {

        log.info("call [DFS下载信息] Param：{}", fileId);

        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(fileId);
        reqDTO.setOrgCode("GLOBAL");
        try {
            return DfsClient.getDownloadUri(reqDTO);
        } catch (Exception e) {
            log.error("call 【DFS下载信息】 Exception", e);
            return null;
        }
    }
}
