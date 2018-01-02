package com.baofu.international.global.account.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip文件压缩工具类
 * <p>
 * 1、文件或文件夹压缩
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/11
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZipCompressUtils {

    /**
     * 文件或文件夹压缩
     *
     * @param zipFileName    压缩之后存放的路径
     * @param sourceFileName 源文件地址
     * @throws Exception 抛出的异常
     */
    public static void zip(String zipFileName, String sourceFileName) throws Exception {
        log.info("文件压缩开始，压缩之后存放的路径：{},源文件地址:{}", zipFileName, sourceFileName);
        long startTime = System.currentTimeMillis();
        try (
                //创建zip输出流
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(Paths.get(zipFileName))))
        ) {
            File sourceFile = new File(sourceFileName);
            //调用函数
            compress(out, sourceFile, sourceFile.getName());
        }
        log.info("文件压缩结束，总耗时：{}", (System.currentTimeMillis() - startTime));
    }

    /**
     * 文件压缩
     *
     * @param out        文件输出流
     * @param sourceFile 源文件
     * @param base       源文件名称
     * @throws Exception 抛出异常
     */
    private static void compress(ZipOutputStream out, File sourceFile, String base) throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] files = sourceFile.listFiles();

            //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (files == null || files.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
            } else {
                //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (File file : files) {
                    compress(out, file, base + "/" + file.getName());
                }
            }
        } else {
            //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry(base));
            try (
                    InputStream fos = Files.newInputStream(Paths.get(sourceFile.toURI()));
                    BufferedInputStream bis = new BufferedInputStream(fos)
            ) {
                int tag;
                //将源文件写入到zip文件中
                while ((tag = bis.read()) != -1) {
                    out.write(tag);
                }
            }
        }
    }

    /**
     * ZIP 工具类测试
     *
     * @param args 测试信息
     */
    public static void main(String[] args) throws Exception {
        ZipCompressUtils.zip("E:/test.zip", "E://tmp");
    }
}
