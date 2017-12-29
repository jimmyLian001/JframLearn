package com.baofu.cbpayservice.common.util;


import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * CSV PARSE
 * <p>
 * 1,读取CSV文件
 * 2，读取CSV文件流
 * </p>
 * User: 康志光 Date: 2017/9/14 ProjectName: cbpayservice Version: 1.0
 */
public final class CSVUtils {

    private CSVUtils() {
    }

    /**
     * 读取CSV文件
     *
     * @param fileName 文件路径
     * @param colCout  列数
     * @return 文件内容
     * @throws Exception 异常信息
     */
    public static List<Object[]> readCsvFile(String fileName, Integer colCout) {
        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        List<Object[]> listObj = Lists.newArrayList();
        //创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.EXCEL;
        try {
            //初始化FileReader object
            fileReader = new FileReader(fileName);
            //初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            //CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            //组装数据
            for (int i = 0; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
//                if (record.size() == 1 && StringUtils.isBlank(record.get(0))) {
//                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0007);
//                }
                Object[] objs = new Object[colCout];
                for (int i2 = 0; i2 < colCout; i2++) {
                    objs[i2] = convertToStr(record, i2);
                }
                listObj.add(objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileReader);
            IOUtils.closeQuietly(csvFileParser);
        }
        return listObj;
    }


    /**
     * 读取CSV文件流
     *
     * @param inputStream 文件流
     * @param colCount    列数
     * @param charsetName 字符集
     * @return 文件内容
     * @throws Exception 异常信息
     */
    public static List<Object[]> readCsvFile(InputStream inputStream, Integer colCount, String charsetName) {
        Reader fileReader = null;
        CSVParser csvFileParser = null;
        List<Object[]> listObj = Lists.newArrayList();
        //创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.EXCEL;
        try {
            //初始化FileReader object
            fileReader = new InputStreamReader(inputStream, charsetName);
            //初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            //CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            //组装数据
            for (int i = 0; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
//                if (record.size() == 1 && StringUtils.isBlank(record.get(0))) {
//                    throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0007);
//                }
                Object[] objs = new Object[colCount];
                for (int i2 = 0; i2 < colCount; i2++) {
                    objs[i2] = convertToStr(record, i2);
                }
                listObj.add(objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileReader);
            IOUtils.closeQuietly(csvFileParser);
        }
        return listObj;
    }

    /**
     * 值转换
     *
     * @param record 行记录
     * @param index  所在列
     * @return String value
     */
    private static String convertToStr(CSVRecord record, Integer index) {
        try {
            return record.get(index);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

}
