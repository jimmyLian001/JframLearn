package com.baofu.cbpayservice.common.util;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileCharsetDetectorUtil {
    private static boolean found = false;
    private static String encoding = null;

    public static void main(String[] argv) throws Exception {
        File file1 = new File("C:\\test1.txt");

        System.out.println("文件编码:" + new FileCharsetDetectorUtil().getFileEncoding(file1));
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
     * @throws Exception
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
     * @throws Exception
     */
    private static String getFileEncoding(InputStream in, nsDetector det) throws Exception {
        // The Notify() will be called when a matching charset is found.
        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                encoding = charset;
                found = true;
            }
        });

        BufferedInputStream imp = new BufferedInputStream(in);
        byte[] buf = new byte[1024];
        int len;
        boolean done;
        boolean isAscii = false;

        while ((len = imp.read(buf, 0, buf.length)) != -1) {
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