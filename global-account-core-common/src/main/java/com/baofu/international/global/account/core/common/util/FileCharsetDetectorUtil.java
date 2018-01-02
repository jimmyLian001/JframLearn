package com.baofu.international.global.account.core.common.util;

import com.baofu.international.global.account.core.common.constant.NumberDict;
import org.mozilla.intl.chardet.nsDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 获取文件编码格式
 *
 * @author 莫小阳
 */
public final class FileCharsetDetectorUtil {

    /**
     * 标志
     */
    private static boolean found = false;

    /**
     * 编码
     */
    private static String encoding = null;

    private FileCharsetDetectorUtil() {
    }

    /**
     * 传入一个文件(File)对象，检查文件编码
     *
     * @param file File对象实例
     * @return 文件编码，若无，则返回null
     * @throws Exception 异常
     */
    public static String getFileEncoding(File file) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        return getFileEncoding(inputStream, new nsDetector());
    }

    /**
     * 传入一个文件(File)对象，检查文件编码
     *
     * @param in 文件流
     * @return 文件编码，若无，则返回null
     * @throws Exception 异常
     */
    public static String getFileEncoding(InputStream in) throws Exception {
        return getFileEncoding(in, new nsDetector());
    }

    /**
     * <pre>
     * 获取文件的编码
     * @param file
     *            File对象实例
     * @param languageHint
     *            语言提示区域代码 @see #nsPSMDetector ,取值如下：
     *             1 : Japanese
     *             2 : Chinese
     *             3 : Simplified Chinese
     *             4 : Traditional Chinese
     *             5 : Korean
     *             6 : Dont know(default)
     * </pre>
     *
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式(不确定的时候，返回可能的字符编码序列)；若无，则返回null
     * @throws Exception 异常
     */
    public static String getFileEncoding(File file, int languageHint) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        return getFileEncoding(inputStream, new nsDetector(languageHint));
    }

    /**
     * 获取文件的编码
     *
     * @param in  文件流
     * @param det nsDetector
     * @return 字符集
     * @throws Exception 异常
     */
    private static String getFileEncoding(InputStream in, nsDetector det) throws Exception {
        // The Notify() will be called when a matching charset is found.
        det.Init(charset -> {
            encoding = charset;
            found = true;
        });

        BufferedInputStream imp = new BufferedInputStream(in);
        byte[] buf = new byte[NumberDict.BYTE_NUM];
        int len;
        boolean done;
        boolean isAscii = false;

        while ((len = imp.read(buf, 0, buf.length)) != NumberDict.MINUS_ONE) {
            // Check if the stream is only ascii.
            isAscii = det.isAscii(buf, len);
            if (isAscii) {
                break;
            }
            // DoIt if non-ascii and not done yet.
            done = det.DoIt(buf, len, false);
            if (done) {
                break;
            }
        }
        imp.close();
        det.DataEnd();

        if (isAscii) {
            encoding = "ASCII";
            found = true;
        }

        if (!found) {
            String[] prob = det.getProbableCharsets();
            //这里将可能的字符集组合起来返回
            for (int i = 0; i < prob.length; i++) {
                if (i == 0) {
                    encoding = prob[i];
                } else {
                    encoding += "," + prob[i];
                }
            }

            if (prob.length > 0) {
                // 在没有发现情况下,也可以只取第一个可能的编码,这里返回的是一个可能的序列
                return encoding;
            } else {
                return null;
            }
        }
        return encoding;
    }
}