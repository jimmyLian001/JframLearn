package com.baofu.international.global.account.core.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/18
 */
public class FileUtils {

    /**
     * 文件删除方法，可删除文件夹或文件
     *
     * @param path 路径
     * @return 返回是否删除成功
     * @throws IOException 异常
     */
    public static Boolean delete(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return Boolean.TRUE;
        }
        if (file.isFile()) {
            Files.delete(file.toPath());
            return Boolean.TRUE;
        }
        //声明目录下所有的文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            Files.delete(file.toPath());
            return Boolean.TRUE;
        }
        //遍历目录下所有的文件
        for (File file1 : files) {
            //把每个文件用这个方法进行迭代
            delete(file1.toString());
        }
        Files.delete(file.toPath());
        return Boolean.TRUE;
    }
}
