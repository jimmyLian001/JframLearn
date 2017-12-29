package com.baofu.cbpayservice.common.util;

import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.system.commons.exception.BaseException;
import com.system.commons.exception.BizServiceException;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;

/**
 * ftp上传
 *
 * @author lIWEI
 * @Description
 * @since 2013-5-22
 */
@Slf4j
@Setter
@ToString
public final class FTPUtil {
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

    private FTPUtil() {
    }

    /**
     * @param url      参数
     * @param port     参数
     * @param ftpMode  参数
     * @param username 参数
     * @param pwd      参数
     * @return FTPUtil
     */
    public static FTPUtil getInstant(String url, String port, Integer ftpMode, String username, String pwd, Boolean isSSL) {

        FTPUtil ftp = new FTPUtil();
        ftp.setFtpUrl(url);
        ftp.setPort(port);
        ftp.setFtpMode(ftpMode);
        ftp.setUserName(username);
        ftp.setPassword(pwd);
        ftp.setIsSSL(isSSL);

        return ftp;
    }

    /**
     * @param url      参数
     * @param port     参数
     * @param ftpMode  参数
     * @param username 参数
     * @param pwd      参数
     * @return FTPUtil
     */
    public static FTPUtil getInstant(String url, String port, Integer ftpMode, String username, String pwd) {

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

        ftp.setDataTimeout(NumberConstants.SIXTY * NumberConstants.ONE_THOUSAND);
        ftp.setConnectTimeout(NumberConstants.SIXTY * NumberConstants.ONE_THOUSAND);
        // 连接FTP服务器
        if (StringUtils.isNotBlank(port)) {
            ftp.connect(ftpUrl, Integer.valueOf(port));
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
    public boolean uploadFile(String path, String filename, String content, String controlCharset) throws IOException {

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
    public boolean uploadFile(String path, String filename, byte[] bytes, String controlCharset) throws IOException {
        Boolean success = false;
        FTPClient ftp;
        ftp = initFTPClient();
        boolean flag = ftp.changeWorkingDirectory(path);
        if (flag) {

            if (ftpMode == 1) {
                ftp.enterRemotePassiveMode();
            } else if (ftpMode == NumberConstants.TWO) {
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
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException ioe) {
                log.error("关闭{}异常,异常原因{}", ftpUrl, ioe.getMessage());
            }
        }
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
            ftp.setControlEncoding("UTF-8");
            ftp.enterLocalPassiveMode();
            //转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            in = ftp.retrieveFileStream(fileName);
            if (in == null) {
                log.error("文件目录：{}.文件名称：{},文件未找到", remotePath, fileName);
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0210);
            }
            return toBytes(in);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("连接失败ftp:{}:{}失败，文件目录：{},文件名称：{}", ftpUrl, port, remotePath, fileName);
            log.error("FTP 文件下载失败", e);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0211);
        } finally {
            try {
                IOUtils.closeQuietly(in);
                if (ftp != null && ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                log.error("关闭ftp： {}失败,失败原因{}", ftpUrl, ioe.getMessage());
            }
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
    public boolean downloadFile(String remotePath, final String fileName, String toFilePath) {
        FTPClient ftp = null;
        InputStream in = null;
        OutputStream out = null;
        byte[] bytes = null;
        try {
            ftp = initFTPClient();
            ftp.setControlEncoding("UTF-8"); //这里设置编码
            ftp.changeWorkingDirectory(remotePath); //转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles(fileName);
            for (FTPFile ff : fs) {
                File localFile = new File(toFilePath + File.separatorChar + ff.getName());
                out = new FileOutputStream(localFile);
                ftp.retrieveFile(ff.getName(), out);
                return true;
            }
        } catch (Exception e) {
            log.error("连接失败ftp:{}:{}失败,失败原因{}", ftpUrl, port, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(in);
                if (ftp != null && ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                log.error("关闭ftp： {}失败,失败原因{}", ftpUrl, ioe.getMessage());
            }
        }
        return false;
    }


    /**
     * 获取文件byte
     *
     * @param input 输入流
     * @return byte
     */
    public byte[] toBytes(InputStream input) {
        byte[] data = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[NumberConstants.BYTE_NUM * NumberConstants.ONE_HUNDRED];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != NumberConstants.MINUS_ONE) {
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
