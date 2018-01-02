package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.CommonDict;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.system.commons.exception.BaseException;
import com.system.commons.exception.BizServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ftp操作工具类
 * <p>
 * 1、FTP初始化
 * 2、FTP初始化重发方法
 * 3、初始化FTP连接
 * 4、上传文件到FTP服务器
 * 5、上传文件到FTP服务器重载方法
 * 6、取得某个目录下某个文件
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FtpUtil {
    /**
     * FTP服务器端口
     */
    private String port = null;
    /**
     * FTP服务器hostname
     */
    private String ftpUrl = null;
    /**
     * FTP模式
     */
    private Integer ftpMode = null;
    /**
     * FTP登录账号
     */
    private String userName = null;
    /**
     * FTP登录密码
     */
    private String password = null;

    /**
     * 是否使用加密传输协议
     */
    private Boolean isSSL;

    /**
     * FTP初始化
     *
     * @param url      参数
     * @param port     参数
     * @param ftpMode  参数
     * @param username 参数
     * @param pwd      参数
     * @return FtpUtil
     */
    public static FtpUtil getInstant(String url, String port, Integer ftpMode, String username, String pwd, Boolean isSSL) {

        FtpUtil ftp = new FtpUtil();
        ftp.setFtpUrl(url);
        ftp.setPort(port);
        ftp.setFtpMode(ftpMode);
        ftp.setUserName(username);
        ftp.setPassword(pwd);
        ftp.setIsSSL(isSSL);

        return ftp;
    }

    /**
     * FTP初始化重发方法
     *
     * @param url      参数
     * @param port     参数
     * @param ftpMode  参数
     * @param username 参数
     * @param pwd      参数
     * @return FtpUtil
     */
    public static FtpUtil getInstant(String url, String port, Integer ftpMode, String username, String pwd) {

        return getInstant(url, port, ftpMode, username, pwd, Boolean.FALSE);
    }

    /**
     * 初始化FTP连接
     *
     * @return FTPClient
     * @throws IOException 异常
     */
    private FTPClient initFTPClient() throws IOException {
        int reply;
        FTPClient ftp;
        if (isSSL) {
            ftp = new FTPSClient("SSL");
        } else {
            ftp = new FTPClient();
        }

        ftp.setDataTimeout(NumberDict.SIXTY * NumberDict.ONE_THOUSAND);
        ftp.setConnectTimeout(NumberDict.SIXTY * NumberDict.ONE_THOUSAND);
        // 连接FTP服务器
        if (StringUtils.isNotBlank(port)) {
            ftp.connect(ftpUrl, Integer.parseInt(port));
        } else {
            ftp.connect(ftpUrl);
        }
        // 登录(如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器)
        ftp.login(userName, password);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            log.error("连接ftp服务器异常：服务器地址:{}", ftpUrl);
        }
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        // 设置二进制传输方式
        ftp.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param path           FTP服务器保存目录(/home/buliangren/zj)
     * @param filename       FTP上保存的文件名
     * @param content        　 	文件内容
     * @param controlCharset 上传到FTP服务器设置字符集
     * @return 成功返回true，否则返回false
     * @throws IOException 异常
     */
    public Boolean uploadFile(String path, String filename, String content, String controlCharset) throws IOException {

        return uploadFile(path, filename, content.getBytes(controlCharset), controlCharset);
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param path           FTP服务器保存目录(/home/buliangren/zj)
     * @param filename       FTP上保存的文件名
     * @param bytes          　 	文件内容
     * @param controlCharset 上传到FTP服务器设置字符集
     * @return 成功返回true，否则返回false
     * @throws IOException 异常
     */
    public Boolean uploadFile(String path, String filename, byte[] bytes, String controlCharset) throws IOException {
        Boolean success = Boolean.FALSE;
        FTPClient ftp;
        ftp = initFTPClient();
        boolean flag = ftp.changeWorkingDirectory(path);
        if (flag) {

            if (ftpMode == 1) {
                ftp.enterRemotePassiveMode();
            } else if (ftpMode == NumberDict.TWO) {
                ftp.enterLocalPassiveMode();
            }

            ftp.setControlEncoding(controlCharset);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            InputStream input = new ByteArrayInputStream(bytes);
            success = ftp.storeFile(filename, input);
            log.info("上传结果：{}", success);
        } else {
            log.error("ftp目录{}不存在,服务器地址：{}", path, ftpUrl);
        }
        ftp.logout();
        ftpConnectClose(ftp);
        return success;
    }

    /**
     * 取得某个目录下某个文件
     *
     * @param remotePath 获取路径
     * @param fileName   文件名
     * @return byte
     */
    public byte[] getFileContent(String remotePath, final String fileName) {
        FTPClient ftp = null;
        InputStream in = null;
        try {
            ftp = initFTPClient();
            //这里设置编码
            ftp.setControlEncoding(CommonDict.UTF_8);
            ftp.enterLocalPassiveMode();
            //转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            in = ftp.retrieveFileStream(fileName);
            if (in == null) {
                log.error("文件目录：{}.文件名称：{},文件未找到", remotePath, fileName);
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190400);
            }
            return toBytes(in);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("连接失败ftp:{}:{}失败，文件目录：{},文件名称：{}", ftpUrl, port, remotePath, fileName);
            log.error("FTP 文件下载失败", e);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290012);
        } finally {
            IOUtils.closeQuietly(in);
            ftpConnectClose(ftp);
        }
    }

    /**
     * 取得某个目录下某个文件
     *
     * @param remotePath 获取路径
     * @param fileName   文件名
     * @param toFilePath 路径
     * @return boolean
     */
    public Boolean downloadFile(String remotePath, final String fileName, String toFilePath) {
        FTPClient ftp = null;
        OutputStream out = null;
        try {
            ftp = initFTPClient();
            //这里设置编码
            ftp.setControlEncoding("UTF-8");
            //转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] ftpFiles = ftp.listFiles(fileName);
            if (ftpFiles == null || ftpFiles.length < 1) {
                log.error("FTP下載文件，文件路徑：{}，文件名：{}未找到", remotePath, fileName);
                return Boolean.FALSE;
            }
            out = Files.newOutputStream(Paths.get(toFilePath + File.separatorChar, ftpFiles[0].getName()));
            ftp.retrieveFile(ftpFiles[0].getName(), out);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("连接失败ftp:{}:{}失败,失败原因{}", ftpUrl, port, e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
            ftpConnectClose(ftp);
        }
        return Boolean.FALSE;
    }

    /**
     * FTP 退出关闭
     *
     * @param ftp ftp对象信息
     */
    private void ftpConnectClose(FTPClient ftp) {
        try {

            if (ftp != null && ftp.isConnected()) {
                ftp.disconnect();
            }
        } catch (IOException ioe) {
            log.error("关闭ftp： {}失败,失败原因{}", ftpUrl, ioe.getMessage());
        }
    }


    /**
     * 获取文件byte
     *
     * @param input 输入流
     * @return byte
     */
    private byte[] toBytes(InputStream input) {
        byte[] data = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[NumberDict.BYTE_NUM * NumberDict.ONE_HUNDRED];
            int numBytesRead;
            while ((numBytesRead = input.read(buf)) != NumberDict.MINUS_ONE) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            log.error("文件不存在:{}", ex1);
        } catch (IOException ex1) {
            log.error("文件IO异常:{}", ex1);
        }
        return data;
    }

}
