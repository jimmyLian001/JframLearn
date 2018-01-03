package com.baofu.international.global.account.client.web.controller;

import com.alibaba.fastjson.util.IOUtils;
import com.baofoo.Response;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.core.DfsException;
import com.baofoo.dfs.client.enums.ErrorCode;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.international.global.account.client.common.assist.RedisManagerImpl;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.RedisDict;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 图片下载公共服务接口
 * <p>
 * 1、对外提供的图片下载地址，渠道放访问
 * 2、DFS下载
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@Slf4j
@Controller
@RequestMapping("/common/")
public class ImageDownloadController {

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    /**
     * 对外提供的图片下载地址，渠道放访问
     *
     * @param response  HttpServletResponse
     * @param out       OutputStream
     * @param userNo    用户号
     * @param dfsFileId 文件下载id
     */
    @RequestMapping("image/download/{userNo}/{dfsFileId}")
    public void certificateDownload(HttpServletResponse response, OutputStream out,
                                    @PathVariable("userNo") String userNo, @PathVariable("dfsFileId") String dfsFileId) {
        log.info("下载凭证用户号：{},DFS文件ID:{}", userNo, dfsFileId);
        try {
            String redisKey = RedisDict.GLOBAL_ACCOUNT_IMAGE + userNo + ":" + dfsFileId;

            String redisValue = redisManager.queryObjectByKey(redisKey);
            if (StringUtils.isNoneBlank(redisValue)) {
                download(Long.parseLong(dfsFileId), response, out);
            } else {
                log.info("凭证下载查询信息异常：{}", redisKey);
                throw new BizServiceException(CommonErrorCode.UNEXPECTED_ERROR, "文件已过时，下载文件失败");
            }

        } catch (Exception e) {
            log.error("call 【DFS下载】 Exception", e);
        }
    }

    /**
     * DFS下载
     *
     * @param fileId   文件ID
     * @param response HttpServletResponse
     * @param out      OutputStream
     */
    private void download(long fileId, HttpServletResponse response, OutputStream out) {

        try {
            //参数转换
            QueryReqDTO reqDTO = convert(fileId);

            Response res = SocketUtil.sendMessage(reqDTO);
            if (!res.isSuccess()) {
                throw new DfsException(ErrorCode.GET_FILE_INFO_ERROR, res.getErrorMsg());
            }
            CommandResDTO resDTO = (CommandResDTO) res.getResult();
            byte[] bytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(resDTO.getFileName().getBytes(CommonDict.GBK), StandardCharsets.ISO_8859_1));
            response.setContentType("application/octet-stream");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            log.info("call 【DFS下载】Param:{} 文件下载成功", fileId);
        } catch (Exception e) {
            log.error("call 【DFS下载】 Exception", e);
        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 参数转换 QueryReqDTO
     *
     * @param fileId 文件ID
     * @return 结果集
     */
    private static QueryReqDTO convert(long fileId) {

        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(fileId);
        reqDTO.setOperation(Operation.QUERY);
        return reqDTO;
    }

}
