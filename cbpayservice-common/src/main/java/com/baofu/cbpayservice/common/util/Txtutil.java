package com.baofu.cbpayservice.common.util;

import com.baofu.cbpayservice.common.constants.Constants;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;


/**
 * 描述
 * <p>
 * </p>
 * User: 不良人 Date:2017/1/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class Txtutil {

    /**
     * 读取excel中的数据 返回一个list
     *
     * @param inputStream 文件流
     * @return 读取之后List
     * @throws Exception
     */
    public static List<Object[]> getDataFromTxt(InputStream inputStream, Integer collCount, String fileName) {
        List<Object[]> list = Lists.newArrayList();
        BufferedReader reader = null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        try {
            if ("txt".equals(suffix)) {
                reader = new BufferedReader(new InputStreamReader(inputStream, Constants.UTF8));
                String reline;
                while ((reline = reader.readLine()) != null) {
                    String[] copyStrArr = Arrays.copyOf(reline.split("\\$\\|\\$"), 18);
                    list.add(copyStrArr.length == 1 && StringUtil.isBlank(copyStrArr[0]) ? new Object[collCount] : copyStrArr);
                }

            }
        } catch (Exception e) {
            log.info("解析TxT失败，异常信息 ——> {}", e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
        }
        return list;
    }

    /**
     * 读取excel中的数据 返回一个list
     *
     * @param file 文件流
     * @return 读取之后List
     * @throws Exception
     */
    public static List<Object[]> getDataFromTxt(File file, Integer collCount, String fileName) {
        List<Object[]> list = Lists.newArrayList();
        BufferedReader reader = null;
        InputStream inputStream = null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        try {
            if ("txt".equals(suffix)) {
                inputStream = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(inputStream, Constants.UTF8));
                String reline;
                while ((reline = reader.readLine()) != null) {
                    String[] copyStrArr = Arrays.copyOf(reline.split("\\$\\|\\$"), 18);
                    list.add(copyStrArr.length == 1 && StringUtil.isBlank(copyStrArr[0]) ? new Object[collCount] : copyStrArr);
                }

            }
        } catch (Exception e) {
            log.info("解析TxT失败，异常信息 ——> {}", e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
        }
        return list;
    }

}
