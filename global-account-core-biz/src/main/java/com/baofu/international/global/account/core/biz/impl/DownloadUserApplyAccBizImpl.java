package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofu.international.global.account.core.biz.DownloadUserApplyAccBiz;
import com.baofu.international.global.account.core.biz.models.UserApplyAccQueryReqBo;
import com.baofu.international.global.account.core.common.constant.*;
import com.baofu.international.global.account.core.common.enums.FlagEnum;
import com.baofu.international.global.account.core.common.enums.SystemEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.common.util.FileUtils;
import com.baofu.international.global.account.core.common.util.ZipCompressUtils;
import com.baofu.international.global.account.core.dal.mapper.UserAccountApplyMapper;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryReqDo;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryRespDo;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 下载Skyee开户信息实现类
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/26 0026
 */
@Slf4j
@Service
public class DownloadUserApplyAccBizImpl implements DownloadUserApplyAccBiz {

    /**
     * 证件照信息临时存放目录
     */
    private static final String FILE_TEMP_PATH = "/客户证件信息/";

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * Redis操作实现
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 用户申请开通账户Mapper操作类
     */
    @Autowired
    private UserAccountApplyMapper userAccountApplyMapper;


    /**
     * 异步下载Skyee开户信息数据
     *
     * @param userApplyAccQueryReqBo 下载数据请求参数
     */
    @Override
    public void downloadUserApplyAcc(UserApplyAccQueryReqBo userApplyAccQueryReqBo) {

        //下载之后的文件夹目录
        String source = configDict.getSystemTempPath() + FILE_TEMP_PATH;
        Boolean lockFlag = Boolean.FALSE;
        try {
            log.info("call 异步下载用戶申請開通賬戶信息,请求参数：{}", userApplyAccQueryReqBo);
            lockFlag = redisManager.lockRedis(RedisDict.PHOTO_DOWNLOAD_KEY, FlagEnum.TRUE.getCode(), Constants.TIME_OUT);
            if (!lockFlag) {
                throw new BizServiceException(CommonErrorCode.REQ_PARAM_VALUE_OUT_OF_LIMIT_RANG, "系统正在处理下载文件操作，请稍后重试");
            }
            //参数信息转换
            UserApplyAccQueryReqDo userApplyAccQueryReqDo = BeanCopyUtils.objectConvert(userApplyAccQueryReqBo,
                    UserApplyAccQueryReqDo.class);
            //查詢結果
            List<UserApplyAccQueryRespDo> list = userAccountApplyMapper.selectAccApplyList(userApplyAccQueryReqDo);
            if (CollectionUtils.isEmpty(list)) {
                throw new BizServiceException(CommonErrorCode.QUERY_RESULT_NULL, "异步下载用户申请开通账户信息为空");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("申请ID").append(Constants.SPLIT_SYMBOL).append("用户号").
                    append(Constants.SPLIT_SYMBOL).append("用户名").append(Constants.SPLIT_SYMBOL).
                    append("币种").append(Constants.SPLIT_SYMBOL).append("站点").append(Constants.SPLIT_SYMBOL).
                    append("渠道").append(Constants.SPLIT_SYMBOL).append("审核状态").append(Constants.SPLIT_SYMBOL).
                    append("店铺名称").append(Constants.SPLIT_SYMBOL).append("卖家编号").append(Constants.SPLIT_SYMBOL).
                    append("访问编码").append(Constants.SPLIT_SYMBOL).append("秘钥");
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                stringBuilder.append("\n");
            } else {
                stringBuilder.append("\r\n ");
            }
            //文件下载
            for (UserApplyAccQueryRespDo userApplyAccQueryRespDo : list) {
                if (UserTypeEnum.ORG.getType() == userApplyAccQueryRespDo.getUserType()) {
                    photoDownload(userApplyAccQueryRespDo);
                }
                //转换成CSV内容
                stringBuilder.append(csvFileContent(userApplyAccQueryRespDo));
            }
            //压缩文件名称
            String fileName = DateUtil.getCurrent();
            //生产CSV文件
            Files.write(new File(source + fileName + CommonDict.LOCAL_FILE_SUFFIX_CSV).toPath(),
                    stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            log.info("call 异步所有文件下載完成，准备开始压缩并上传至DFS服务器");
            String zipPath = configDict.getSystemTempPath() + fileName + Constants.ZIP;

            //ZIP 压缩
            ZipCompressUtils.zip(zipPath, source);

            Long dfsId = fileUpload(fileName + Constants.ZIP, zipPath);
            log.info("call 文件压缩完成并上传DFS完成，DFS编号为：{}", dfsId);

            // 插入redis缓存
            redisManager.insertObject(String.valueOf(dfsId), String.valueOf(userApplyAccQueryReqBo.getRecordId()));
        } catch (Exception e) {
            log.error("call 异步下载用户开户信息数据异常：{}", e);
        } finally {
            try {
                if (lockFlag) {
                    //删除缓存文件
                    FileUtils.delete(source);
                    //删除redis标识
                    redisManager.deleteObject(RedisDict.PHOTO_DOWNLOAD_KEY);
                }
            } catch (Exception e) {
                log.error("文件删除失败，异常原因：", e);
            }
        }
    }


    /**
     * 证件照片下载
     *
     * @param userApplyAccQueryRespDo 下载内容
     * @throws IOException 抛出IO异常
     */
    private void photoDownload(UserApplyAccQueryRespDo userApplyAccQueryRespDo) throws IOException {
        QueryReqDTO queryReqDTO = new QueryReqDTO();
        //文件路徑   目标路径 + 用户号+'_'+申请编号
        String fileDownloadPath = configDict.getSystemTempPath() + FILE_TEMP_PATH + userApplyAccQueryRespDo.getUserNo() + "_" + userApplyAccQueryRespDo.getApplyId();
        log.info("call 开始下载证件信息，存放路径：{}", fileDownloadPath);
        File file = new File(fileDownloadPath);
        if (!file.exists()) {
            Files.createDirectories(Paths.get(file.toURI()));
        }
        try {
            log.info("call 开始下载证件信息：{}", userApplyAccQueryRespDo);
            // 下载组织机构证件照
            if (userApplyAccQueryRespDo.getOrgCodeCertDfsId() != null && userApplyAccQueryRespDo.getOrgCodeCertDfsId() > NumberDict.ONE) {
                queryReqDTO.setFileId(userApplyAccQueryRespDo.getOrgCodeCertDfsId());
                DfsClient.download(queryReqDTO, fileDownloadPath);
                log.info("call 下载组织结构成功");
            }
            //下载营业执照处理
            if (userApplyAccQueryRespDo.getIdDfsId() != null && userApplyAccQueryRespDo.getIdDfsId() > NumberDict.ONE) {
                queryReqDTO.setFileId(userApplyAccQueryRespDo.getIdDfsId());
                DfsClient.download(queryReqDTO, fileDownloadPath);
                log.info("call 下载营业执照处理成功");
            }
            //法人证件照 正面
            if (userApplyAccQueryRespDo.getLegalIdFrontDfsId() != null && userApplyAccQueryRespDo.getLegalIdFrontDfsId() > NumberDict.ONE) {
                queryReqDTO.setFileId(userApplyAccQueryRespDo.getLegalIdFrontDfsId());
                DfsClient.download(queryReqDTO, fileDownloadPath);
                log.info("call 下载法人证件照 正面成功");
            }
            //法人证件照 反面
            if (userApplyAccQueryRespDo.getIdReverseDfsId() != null && userApplyAccQueryRespDo.getIdReverseDfsId() > NumberDict.ONE) {
                queryReqDTO.setFileId(userApplyAccQueryRespDo.getIdReverseDfsId());
                DfsClient.download(queryReqDTO, fileDownloadPath);
                log.info("call 下载法人证件照 反面成功");
            }

            //税务
            if (userApplyAccQueryRespDo.getTaxRegistrationCertDfsId() != null && userApplyAccQueryRespDo.getTaxRegistrationCertDfsId() > NumberDict.ONE) {
                queryReqDTO.setFileId(userApplyAccQueryRespDo.getTaxRegistrationCertDfsId());
                DfsClient.download(queryReqDTO, fileDownloadPath);
                log.info("call 下载税务证件照 反面成功");
            }

        } catch (Exception e) {
            log.error("文件下載异常，异常信息：{}", e);
        }
    }


    /**
     * CSV文件内容组装
     *
     * @param userApplyAccQueryRespDo 查询的结果信息
     * @return 返回结果
     */
    private StringBuilder csvFileContent(UserApplyAccQueryRespDo userApplyAccQueryRespDo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(userApplyAccQueryRespDo.getApplyId())).append("\t").append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(String.valueOf(userApplyAccQueryRespDo.getUserNo())).append("\t").append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getUserName())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getCcy())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getStorePlatform())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(String.valueOf(userApplyAccQueryRespDo.getChannelId())).append("\t").append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getRealState(userApplyAccQueryRespDo.getRealnameStatus())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getStoreName())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getSellerId())).append("\t").append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getAwsAccessKey())).append(Constants.SPLIT_SYMBOL);
        stringBuilder.append(getFinalValue(userApplyAccQueryRespDo.getSecretKey()));
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            stringBuilder.append("\n");
        } else {
            stringBuilder.append("\r\n ");
        }
        return stringBuilder;
    }

    /**
     * 参数转换
     *
     * @param realnameStatus 状态  0-未认证；1-认证中 2-已认证 3-认证失败 默认为未认证',
     * @return
     */
    private String getRealState(Integer realnameStatus) {
        String result = "";
        if (realnameStatus != null) {
            if (realnameStatus == 0) {
                result = "未认证";
            } else if (realnameStatus == 1) {
                result = "认证中";
            } else if (realnameStatus == 2) {
                result = "已认证";
            } else if (realnameStatus == 3) {
                result = "认证失败";
            }
        }
        return result;
    }


    /**
     * 文件压缩完成之后上传至DFS 服务并放回DFS编号
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return 返回DFS编号
     */
    private Long fileUpload(String fileName, String filePath) {
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setFileGroup(FileGroup.ONE_DAY);
        insertReqDTO.setFileName(fileName);
        insertReqDTO.setOrgCode(SystemEnum.SYSTEM_NAME.getCode());
        insertReqDTO.setFilePath(filePath);
        insertReqDTO.setFileDate(DateUtil.getCurrent());
        return DfsClient.upload(insertReqDTO).getFileId();
    }

    /**
     * 字符串转换
     *
     * @param str 需要判断的字符串
     */
    public String getFinalValue(String str) {
        String result;
        if (StringUtils.isEmpty(str)) {
            result = "";
        } else {
            result = str;
        }
        return result;
    }

}
